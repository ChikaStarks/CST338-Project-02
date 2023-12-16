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
import android.widget.Toast;

import com.example.schoolbees.DB.AppDataBase;
import com.example.schoolbees.DB.PostDao;
import com.example.schoolbees.DB.UserDao;
import com.example.schoolbees.databinding.ActivityLoginBinding;
import com.example.schoolbees.databinding.ActivityUserPostBinding;

import java.util.List;


public class UserPost extends AppCompatActivity {
    ActivityUserPostBinding mUserPostBinding;
    private PostDao mPostDao;
    private int mUserId = -1;
    private static final String USER_ID_KEY = "com.example.schoolbees.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.schoolbees.PREFERENCES_KEY";
    private SharedPreferences mPreferences = null;
    private Button SignOutButton; //logout button
    private Button goBackButton;
    private EditText mtitleField;
    private EditText mdescriptionField;
    private EditText mpriceField;
    private EditText mlocationField;
    private EditText mcontactField;
    private Button mpostAdButton;
    private String mPostname;
    private String mDescription;
    private String mPrice;
    private String mLocation;
    private String mContact;
    List<Post> mPostList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_post);

        mUserPostBinding = ActivityUserPostBinding.inflate(getLayoutInflater());
        setContentView(mUserPostBinding.getRoot());
        wireupDisplay();
        getDatabase();
    }//end of on create

    private void wireupDisplay() {
        mpostAdButton = findViewById(R.id.buttonPost);
        SignOutButton = findViewById(R.id.SignOutButton);
        goBackButton = findViewById(R.id.UpostButtonBack);
        mtitleField = mUserPostBinding.titlePost;
        mdescriptionField = mUserPostBinding.description;
        mpriceField = mUserPostBinding.price;
        mlocationField = mUserPostBinding.editTextLocation;
        mcontactField = mUserPostBinding.editTextContact;

        mpostAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromDisplay();
                if (checkInput()) {
                    String postname = mtitleField.getText().toString();
                    String description = mdescriptionField.getText().toString();
                    String price = mpriceField.getText().toString();
                    String location = mlocationField.getText().toString();
                    String contact = mcontactField.getText().toString();

                    Post newPost = new Post(postname, description, price, location, contact);

                    mPostDao.insert(newPost);
                    //Log.d("String", postname + description + price + location + contact); //this is to check output in Logcat
                    openPostSearch();
                }
            }
        });

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

    private void getValuesFromDisplay(){
        mPostname = mtitleField.getText().toString();
        mDescription = mdescriptionField.getText().toString();
        mPrice = mpriceField.getText().toString();
        mLocation = mlocationField.getText().toString();
        mContact = mcontactField.getText().toString();
    }
    public void openPostSearch(){
        Intent intent = new Intent(this, PostSearch.class);
        startActivity(intent);
    }

    private void addUserToPreference(int userId) {
        if (mPreferences == null) {
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

    public void MainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private boolean checkInput() {
        List<Post> posts = mPostDao.getAllPosts();
        String postname = mtitleField.getText().toString();
        String contact = mcontactField.getText().toString();
        if (postname.isEmpty()){
            Toast.makeText(this, "Please enter the title", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (contact.isEmpty()) {
            Toast.makeText(this, "Please enter your contact info.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}