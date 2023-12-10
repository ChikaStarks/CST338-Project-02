package com.example.schoolbees;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolbees.DB.AppDataBase;
import com.example.schoolbees.DB.PostDao;
import com.example.schoolbees.DB.UserDao;
import com.example.schoolbees.databinding.ActivityPostSearchBinding;

import org.w3c.dom.Text;

import java.util.List;

public class PostSearch extends AppCompatActivity {

    ActivityPostSearchBinding mActivityPostSearchBinding;

    private SharedPreferences mPreferences = null;
    private Button SignOutButton; //logout button
    private Button goBackButton;
    private int mUserId = -1;
    private static final String USER_ID_KEY = "com.example.schoolbees.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.schoolbees.PREFERENCES_KEY";
    private UserDao mUserDao;
    private EditText mSearchField;
    private Button mSearchButton;
    private TextView mResultDisplay;

    private EditText mEnterIDField;
    private Button mInterestedButton;
    private Button mReportButton;

    private String mPostname;

    private Post mPost;
    private PostDao mPostDao;

    List<Post> mPostList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_search);

        mActivityPostSearchBinding = ActivityPostSearchBinding.inflate(getLayoutInflater());
        setContentView(mActivityPostSearchBinding.getRoot());


        wireupdisplay();
        getDatabase();






    }//end of onCreate

    private void wireupdisplay(){
        SignOutButton = findViewById(R.id.signOutButton4);
        goBackButton = findViewById(R.id.buttonBack5);
        mSearchField = mActivityPostSearchBinding.editTextText;
        mSearchButton = mActivityPostSearchBinding.button10;
        mEnterIDField = mActivityPostSearchBinding.editTextText3;
        mReportButton = mActivityPostSearchBinding.buttonReport2;
        mInterestedButton = mActivityPostSearchBinding.buttonInterested2;

        //TODO eEnterID, mReport, mInterested
        mResultDisplay = mActivityPostSearchBinding.textView22;

        mResultDisplay.setMovementMethod(new ScrollingMovementMethod());


        SignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToPreviousPage();
            }
        });

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                getValuesFromDisplay();
                if (checkMatch()) {
                    mPostList = mPostDao.getAllPosts();
                    StringBuilder sb = new StringBuilder();
                    for (Post post : mPostList){
                        sb.append(post.toString());

                     //   mResultDisplay.setText(mPost.toString()); //this works
                        //mResultDisplay.setText(mPost.getPostname()); //this works! >> leaving this for my referene
                        //mResultDisplay.setText(R.string.hello); // this works! >> leaving this for my referene
                    }mResultDisplay.setText(sb.toString());
                }
            }
        });

    }

    private boolean checkMatch(){
        mPost = mPostDao.getPostByPostname(mPostname);
        if(mPost == null){
            Toast.makeText(this, "no post " + mPostname + " found", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void getValuesFromDisplay(){
        mPostname = mSearchField.getText().toString();
    }

    private boolean checkPostsInDatabase () {
        mPostList = mPostDao.getAllPosts();
        if (!mPostList.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Post post : mPostList) {
                sb.append(post.toString());

            }
            mResultDisplay.setText(sb.toString());

        } else {
            mResultDisplay.setText("No matching result.");

        } return false;
    }

    private boolean checkForMatch(){
        return mPost.getPostname().equals(mPostname);
    }

    private void displayResult(int postId){
        mPost = mPostDao.getPostByPostId(postId);
        showInDisplayField();
    }

    public boolean showInDisplayField() {
        mPostList = mPostDao.getAllPosts();
        if(mPost != null){
           // TextView text = findViewById(R.id.textView6);
            mResultDisplay.setText(mPost.getPostname());
        }
        return false;
    }

    private void display(){
        mPostList = mPostDao.getAllPosts();

    }

//    private void getValuesFromDisplay(int postId){
//        mPostname = mtitleField.getText().toString();
//        mDescription = mdescriptionField.getText().toString();
//        mPrice = mpriceField.getText().toString();
//        mLocation = mlocationField.getText().toString();
//        mContact = mcontactField.getText().toString();
//    }
//
//    private void refreshDisplay (int postId) {
//        //mPostList = mPostDao.getAllPosts();
//        mtitleField.setText(mPost.getPostname());;
//        mdescriptionField.setText(mPost.getDescription());
//        mpriceField.setText(mPost.getPrice());
//        mlocationField.setText(mPost.getLocation());
//        mcontactField.setText(mPost.getContact());
//
//    }

    private void goBackToPreviousPage(){
        Intent intent = new Intent(this, LandingPage.class);
        startActivity(intent);
    }

    private void logoutUser() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage(R.string.log_out);

        alertBuilder.setPositiveButton(getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearUserFromIntent();
                        clearUserFromPref();
                        mUserId = -1;
                        checkForUser();
                        MainActivity();
                    }
                });
        alertBuilder.setNegativeButton(getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        alertBuilder.create().show();
    }

    private void clearUserFromIntent(){
        getIntent().putExtra(USER_ID_KEY, -1);
    }

    private void clearUserFromPref(){
        addUserToPreference(-1);
        Toast.makeText(this, "User cleared", Toast.LENGTH_SHORT).show();
    }

    private void checkForUser() {
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);
        if (mUserId != -1) {
            return;
        }
        SharedPreferences preferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        mUserId = mPreferences.getInt(USER_ID_KEY, -1);
        if (mPreferences == null) {
            getPrefs();
        }
    }

    private void addUserToPreference(int userId){
        if (mPreferences == null){
            getPrefs();
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(USER_ID_KEY, userId);
        editor.apply();
    }

//    private void getDatabase() {
//        mUserDao = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
//                .allowMainThreadQueries()
//                .build().getUserDao();
//    }

    private void getDatabase() {
        mPostDao = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build().getPostDao();
    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    public void MainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}