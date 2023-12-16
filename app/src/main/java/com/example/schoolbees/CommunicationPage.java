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
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolbees.DB.AppDataBase;
import com.example.schoolbees.DB.ContactDao;
import com.example.schoolbees.DB.PostDao;
import com.example.schoolbees.DB.UserDao;
import com.example.schoolbees.databinding.ActivityCommunicationPageBinding;

import java.util.List;

public class CommunicationPage extends AppCompatActivity {
    ActivityCommunicationPageBinding mActivityCommunicationPageBinding;

    private UserDao mUserDao;
    private PostDao mPostDao;
    private ContactDao mContactDao;
    private Button goBackButton;
    private Button SignOutButton; //logout button
    private TextView mDisplay;
    private static final String USER_ID_KEY = "com.example.schoolbees.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.schoolbees.PREFERENCES_KEY";
    private SharedPreferences mPreferences = null;
    private int mUserId = -1;
    private int mPostId;
    private Contact mContactID;
    List<Post> mPostList;
    List<Contact> mContactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication_page);
        mActivityCommunicationPageBinding = ActivityCommunicationPageBinding.inflate(getLayoutInflater());
        setContentView(mActivityCommunicationPageBinding.getRoot());

        mDisplay = mActivityCommunicationPageBinding.textView20;
        mDisplay.setMovementMethod(new ScrollingMovementMethod());

        wiredupdisplay();
        getContactDatabase();
        getPostDatabase();
        showAllCommunication();
    }//end of onCreate

    private void wiredupdisplay(){
        //Sign Out Button
        SignOutButton = findViewById(R.id.signOutButton3);

        SignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        goBackButton = findViewById(R.id.buttonBack3);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToPreviousPage();
            }
        });
    }

    private void showAllCommunication(){
        mContactList = mContactDao.getAllContactInfos();
        mDisplay.setText(mContactList.toString().replace("[", "")
                .replace("]", "").replace(",", ""));
    }

    private void goBackToPreviousPage() {
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

    private void clearUserFromIntent() {
        getIntent().putExtra(USER_ID_KEY, -1);
    }

    private void clearUserFromPref() {
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

    private void addUserToPreference(int userId) {
        if (mPreferences == null) {
            getPrefs();
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(USER_ID_KEY, userId);
        editor.apply();
    }

    private void getContactDatabase() {
        mContactDao = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build().getContactDao();
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