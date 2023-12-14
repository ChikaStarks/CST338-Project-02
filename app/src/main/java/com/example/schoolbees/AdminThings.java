package com.example.schoolbees;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolbees.DB.AppDataBase;
import com.example.schoolbees.DB.PostDao;
import com.example.schoolbees.DB.UserDao;
import com.example.schoolbees.databinding.ActivityAdminThingsBinding;

import java.util.List;

public class AdminThings extends AppCompatActivity {

    ActivityAdminThingsBinding mActivityAdminThingsBinding;

    private UserDao mUserDao;
    private PostDao mPostDao;
    private int mUserId = -1;
    private static final String USER_ID_KEY = "com.example.schoolbees.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.schoolbees.PREFERENCES_KEY";
    private SharedPreferences mPreferences = null;
    private User mUser;
    private Post mPost;

    private String mUsername;

    private String mPostname;

    private TextView mDisplayUsers;
    private TextView mDisplayPosts;

    private Button goBackButton;

    private Button SignOutButton; //logout button

    List<Post> mPostList;
    List<User> mUserList;

    LiveData<List<Post>> mLiveData; // tired to use Live data, didn't have enough time.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_things);

        mActivityAdminThingsBinding = ActivityAdminThingsBinding.inflate(getLayoutInflater());
        setContentView(mActivityAdminThingsBinding.getRoot());

        wireupdisplay();
        getUserDatabase();
        getPostDatabase();
        displayUsers();
        displayPosts();





    }//end of onCreate

    private void wireupdisplay() {
        mDisplayPosts = mActivityAdminThingsBinding.textView17;

        mDisplayPosts.setMovementMethod(new ScrollingMovementMethod());

        mDisplayUsers = mActivityAdminThingsBinding.textView15;
        mDisplayUsers.setMovementMethod(new ScrollingMovementMethod());

        //Sign Out Button
        SignOutButton = findViewById(R.id.SignOutButton);

        SignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        goBackButton = findViewById(R.id.AThingsButtonBack);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToPreviousPage();
            }
        });

    }

    public void displayUsers() {
        mUserList = mUserDao.getAllUsers();
            StringBuilder sb = new StringBuilder();
            for (User user : mUserList) {
                sb.append(user.toString());
                mDisplayUsers.setText(sb.toString());
        }
    }

    public void displayPosts(){
        mPostList = mPostDao.getAllPosts();
        StringBuilder sb = new StringBuilder();
        for (Post post : mPostList){
            sb.append(post.toString());
            mDisplayPosts.setText(sb.toString());
        }
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

    private void getUserDatabase() {
        mUserDao = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build().getUserDao();
    }

    private void getPostDatabase() {
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

//maybe don't need this page