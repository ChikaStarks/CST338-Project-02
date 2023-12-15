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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolbees.DB.AppDataBase;
import com.example.schoolbees.DB.ContactDao;
import com.example.schoolbees.DB.PostDao;
import com.example.schoolbees.DB.ReportDao;
import com.example.schoolbees.DB.UserDao;
import com.example.schoolbees.databinding.ActivityPostSearchBinding;

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

    private int mPostId;

    private TextView mResultDisplay;

    private EditText mEnterIDField;
    private Button mInterestedButton;
    private Button mReportButton;

    private String matchedName;

    private String mPostname;

    private Post mPost;
    private Post mPostID;

    private int mPostIdLength;

    private PostDao mPostDao;

    private ReportDao mReportDao;


    private ContactDao mContactDao;

    List<Post> mPostList;


    private String mPostIDString;

    private int mSearchIdInputInt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_search);

        mActivityPostSearchBinding = ActivityPostSearchBinding.inflate(getLayoutInflater());
        setContentView(mActivityPostSearchBinding.getRoot());

        wireupdisplay();
        getDatabase();
        getReportDatabase();
        getContactDatabase();


    }//end of onCreate

    private void wireupdisplay() {
        SignOutButton = findViewById(R.id.signOutButton4);
        goBackButton = findViewById(R.id.buttonBack5);
        mSearchField = mActivityPostSearchBinding.editTextText;
        mSearchButton = mActivityPostSearchBinding.button10;
        mEnterIDField = mActivityPostSearchBinding.editTextText3;
        mReportButton = mActivityPostSearchBinding.buttonReport2;
        mInterestedButton = mActivityPostSearchBinding.buttonInterested2;

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
                getValuesFromDisplay();
                refreshDisplay();
                checkMatch();
                matchedName();
            }
        });

        mReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchPostId()){
                    reportPostButtonFunction();
                }

            }
        });

        mInterestedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchPostId()) {
                    interestedButtonFunction();
                }
            }
        });
    }

    private boolean checkMatch() {
        mPost = mPostDao.getOnePostByPostname(mPostname);
        mPostList = mPostDao.getPostByPostname(mPostname);
        if (mPostname.isEmpty()) {
            Toast.makeText(this, "Please enter keyword.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mPost == null) {
            Toast.makeText(this, "No matching post \"" + mPostname + "\" found.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void matchedName() {
        mPostList = mPostDao.getPostByPostname(mPostname);
        mResultDisplay.setText(mPostList.toString().replace("[", "")
                .replace("]", "").replace(",", ""));
//        mPost = mPostDao.getPostByPostname(mPostname);
//        mResultDisplay.setText(mPost.toString());
    }
    //reference: https://www.w3schools.com/sql/sql_select.asp


    private void refreshDisplay(){
        mResultDisplay.setText(null);
    }

    private void getValuesFromDisplay(){
        mPostname = mSearchField.getText().toString();
    }

    private void reportPostButtonFunction(){

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage("Do you want to report this post ID: " + mPostID.getPostId() + ", Post Title: "
                + mPostID.getPostname() + "?");

        alertBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // Integer post_id = mPostID.getPostId();
                String postname = mPostID.getPostname();
                String description = mPostID.getDescription();
                String price = mPostID.getPrice();
                String location = mPostID.getLocation();
                String contact = mPostID.getContact();

                Report newReport = new Report (postname, description, price, location, contact);
                mReportDao.insert(newReport);
                showReportConfirmation();
            }
        });

        alertBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        alertBuilder.create().show();

    }

    private void interestedButtonFunction(){

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage("Do you want to send your contact info for this post ID: " + mPostID.getPostId() + ", Post Title: "
                + mPostID.getPostname() + "?");

        alertBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Integer post_id = mPostID.getPostId();

                        goToContactInfoPage();
                    }
                });

        alertBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        alertBuilder.create().show();

    }

    private void showReportConfirmation(){
        Toast.makeText(this, "The post has been reported!", Toast.LENGTH_SHORT).show();
    }


    private boolean searchPostId(){
        mPostIdLength = mEnterIDField.getText().length();

        Log.d("post", mEnterIDField.getText().toString());
        mPostID = mPostDao.getPostByPostId(mPostId);
        if(mPostIdLength == 0){
            Toast.makeText(this, "Please enter the Post ID.", Toast.LENGTH_SHORT).show();
            return false;
        }

        mPostId = Integer.parseInt(mEnterIDField.getText().toString());
        mPostID = mPostDao.getPostByPostId(mPostId);
        if (mPostID == null){
            Toast.makeText(this, "No matching Post ID \"" + mPostId +
                    "\" is found.", Toast.LENGTH_SHORT).show();
            return false;
        }
//        try{
//            Log.d("ID","check mPostID " + mPostID); //testing purpose only
//            mPostId = Integer.parseInt(mEnterIDField.getText().toString());
//            if (mPostID == null){
//                Toast.makeText(this, "No matching Post ID \"" + mPostId +
//                        "\" is found.", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        }catch (NumberFormatException e){
//            Log.d("Int", "Unable to convert to string.");
//            Toast.makeText(this, "Please enter a numerical value only.", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        mPostId = Integer.parseInt(mEnterIDField.getText().toString());

        return true;
    }

    private void goBackToPreviousPage(){
        Intent intent = new Intent(this, LandingPage.class);
        startActivity(intent);
    }

    private void goToContactInfoPage(){
        Intent intent = new Intent(this, ContactInfo.class);
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
        if(mPreferences == null){
            getPrefs();
        }

        mUserId = mPreferences.getInt(USER_ID_KEY, -1);
        if (mUserId != -1) {
            return;
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

    private void getReportDatabase() {
        mReportDao = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build().getReportDao();
    }

    private void getContactDatabase() {
        mContactDao = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build().getContactDao();
    }


    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    public void MainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}