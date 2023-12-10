package com.example.schoolbees;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolbees.DB.AppDataBase;
import com.example.schoolbees.DB.PostDao;
import com.example.schoolbees.DB.UserDao;
import com.example.schoolbees.databinding.ActivityUserCheckBinding;
import com.example.schoolbees.databinding.ActivityUserPostBinding;

import java.util.List;

public class UserCheck extends AppCompatActivity {
//TODO delete this page. replaced with PostSearch
    ActivityUserCheckBinding mActivityUserCheckBinding;

    private PostDao mPostDao;
    private static final String POST_ID_KEY = "com.example.schoolbees.postIDKey";
    private static final String POST_PREFERENCES_KEY = "com.example.schoolbees.POST_PREFERENCES_KEY";


    private int mUserId = -1;

    private Post mPost;
    private static final String USER_ID_KEY = "com.example.schoolbees.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.schoolbees.PREFERENCES_KEY";

    private SharedPreferences mPreferences = null;

    private SharedPreferences mPostPreferences = null;
    private User mUser;

    private Button goBackButton;

    private Button SignOutButton; //logout button

    private TextView titleString;
    private EditText mtitleField;
    private EditText mdescriptionField;
    private EditText mpriceField;
    private EditText mlocationField;
    private EditText mcontactField;

    private String mPostname;
    private String mDescription;
    private String mPrice;
    private String mLocation;
    private String mContact;

    private Button interestedButton;
    private Button reportButton;
    private int mPostId;

    List<Post> mPostList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_check);

//        mActivityUserCheckBinding = ActivityUserCheckBinding.inflate(getLayoutInflater());
//        setContentView(mActivityUserCheckBinding.getRoot());

        wireupDisplay();
        getDatabase();
    //    addPostToPreference(mPostId);
       // getValuesFromDisplay(mPostId); // for binding
      //  refreshDisplay(mPostId); //for binding
     //   checkForPost();
     //   addPostToPreference(mPostId);
//        showList(mPostId);
        returnInput(mPostId);
      //  showStrings();
       // listEachField(mPost);


        //Sign Out Button
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

    }//end of onCreate

    private void wireupDisplay(){
        SignOutButton = findViewById(R.id.SignOutButton);
        goBackButton = findViewById(R.id.UcheckButtonBack);
//        mtitleField = mActivityUserCheckBinding.editTextTitle2;
//        mdescriptionField = mActivityUserCheckBinding.editTextDescription;
//        mpriceField = mActivityUserCheckBinding.editTextPrice;
//        mlocationField = mActivityUserCheckBinding.editTextLocation;
//        mcontactField = mActivityUserCheckBinding.editTextContact;

    }




//    private void checkForPost(){
//        mPostId = getIntent().getIntExtra(POST_ID_KEY, -1);
//        if (mPostId != -1){
//            return;
//        }
//        SharedPreferences preferences = this.getSharedPreferences(POST_ID_KEY, Context.MODE_PRIVATE);
//        mPostId = mPostPreferences.getInt(POST_ID_KEY, -1);
//        if(mPostPreferences == null){
//            getPostPrefs();
//        }
//
//        Intent intent = UserPost.intentFactory(this);
//        startActivity(intent);
//    }

    private void addPostToPreference(int postId){
        if (mPostPreferences == null){
            getPostPrefs();
        }
        SharedPreferences.Editor editor = mPostPreferences.edit();
        editor.putInt(POST_ID_KEY, postId);
        editor.apply();
    }



    //for binding
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

  //  public void showStrings() {
    public boolean showStrings(){

        if (mPost != null) {

//            String postname = mtitleField.getText().toString();
//            String description = mdescriptionField.getText().toString();
//            String price = mpriceField.getText().toString();
//            String location = mlocationField.getText().toString();
//            String contact = mcontactField.getText().toString();

            TextView postname = findViewById(R.id.editTextTitle2);;
            //postname.setText("test string");
            postname.setText(mPost.getPostname());

            TextView description = findViewById(R.id.editTextDescription);
            description.setText(mPost.getDescription());

            TextView price = findViewById(R.id.editTextPrice);
            price.setText(mPost.getPrice());

            TextView location = findViewById(R.id.editTextLocation);
            location.setText(mPost.getLocation());

            TextView contact = findViewById(R.id.editTextContact);
            contact.setText(mPost.getContact());
        }
        return false;
    }




    private void returnInput (int postId){
        mPost = mPostDao.getPostByPostId(postId);
       // addPostToPreference(postId);

        showStrings();
        //Log.d("String", mPost.getPostName() + mPost.getDescription() + mPost.getPrice() + mPost.getLocation() + mPost.getContact());
    }






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

    private void getDatabase() {
        mPostDao = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build().getPostDao();
    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    private void getPostPrefs() {
        mPostPreferences = this.getSharedPreferences(POST_PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    public void MainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public static Intent intentFactory(Context applicationContext, int postId) {
        Intent intent = new Intent(applicationContext, UserCheck.class);
        intent.putExtra(POST_ID_KEY, postId);

        return intent;
    }

}///get the same post id