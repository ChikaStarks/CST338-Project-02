package com.example.schoolbees;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.schoolbees.DB.AppDataBase;
import com.example.schoolbees.DB.UserDao;
import com.example.schoolbees.databinding.ActivityLoginBinding;

import java.util.List;


public class LoginActivity extends AppCompatActivity {
    private EditText mUsernameField;
    private EditText mPasswordField;
    private Button mButton;
    private Button MainPageButton1;
    private UserDao mUserDao;
    private String mUsername;
    private String mPassword;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        wireupDisplay();
        getDatabase();
    }// end of onCreate

    private void wireupDisplay(){
        mUsernameField = findViewById(R.id.editTextLoginUserName);
        mPasswordField = findViewById(R.id.editTextLoginPassword);
        mButton = findViewById(R.id.button3); //sign in button
        MainPageButton1 = findViewById(R.id.button7);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromDisplay();
                if (checkForUserInDatabase()){
                    if (!validatePassword()){
                        Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent = LandingPage.intentFactory(getApplicationContext(), mUser.getUserId());
                        startActivity(intent);
                    }
                };
            }
        });

        MainPageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
    }

    private boolean validatePassword(){
        return mUser.getPassword().equals(mPassword);
    }

    private void getValuesFromDisplay(){
        mUsername = mUsernameField.getText().toString();
        mPassword = mPasswordField.getText().toString();
    }

    private boolean checkForUserInDatabase(){
        List<User> users = mUserDao.getAllUsers();
        if (users.size() <= 0) {
            User testUser = new User("false", "testuser1", "testuser1"); // Normal User
            User adminUser = new User("true", "admin2", "admin2");  // Admin user
            User adminUserTest = new User("true", "admin3", "admin3");
            mUserDao.insert(testUser);
            mUserDao.insert(adminUser);
            mUserDao.insert(adminUserTest);
        }
        mUser = mUserDao.getUserByUsername(mUsername);
        if(mUser == null){
            Toast.makeText(this, "no user " + mUsername + " found", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    private void getDatabase(){
        mUserDao = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDao();
    }

    public void openMainActivity() {
        Intent intent3 = new Intent(this, MainActivity.class);
        startActivity(intent3);
    }
}