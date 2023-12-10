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

    ActivityLoginBinding mLoginBinding;
    private EditText mUsernameField;
    private EditText mPasswordField;

    private Button mButton;
    private Button MainPageButton1;

    private UserDao mUserDao;

    private String mUsername;
    private String mPassword;

    private int mUserID = -1;

    private SharedPreferences mPreferences = null;

    private User mUser;

    private Menu mOptionMenu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        wireupDisplay();
        getDatabase();

//        mLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
//        setContentView(mLoginBinding.getRoot());

        //main page button
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
    public void openLandingPage() {
        Intent intent = new Intent(this, LandingPage.class);
        startActivity(intent);
    }

//    public void extractingUserName(){
//        Intent openSetPin = new Intent(this, LandingPage.class);
//        String name =  mUsernameField.getText().toString();
//// Add the data to the intent using the a key
//        openSetPin.putExtra("name_key", name);
//        startActivity(openSetPin);
//    }








    //
//    private final String CREDENTIAL_SHARED_PREF = "our_shared_pref";
//
//    public LoginActivity(Button mainPageButton1, EditText username, EditText password, Button signinButton) {
//        this.MainPageButton1 = mainPageButton1;
//        this.username = username;
//        this.password = password;
//        this.signinButton = signinButton;
//    }
//
//    public Button getMainPageButton1() {
//        return MainPageButton1;
//    }
//
//    public void setMainPageButton1(Button mainPageButton1) {
//        MainPageButton1 = mainPageButton1;
//    }
//
//    public EditText getUsername() {
//        return username;
//    }
//
//    public void setUsername(EditText username) {
//        this.username = username;
//    }
//
//    public EditText getPassword() {
//        return password;
//    }
//
//    public void setPassword(EditText password) {
//        this.password = password;
//    }
//
//    public Button getSigninButton() {
//        return signinButton;
//    }
//
//    public void setSigninButton(Button signinButton) {
//        this.signinButton = signinButton;
//    }
//
//    public String getCREDENTIAL_SHARED_PREF() {
//        return CREDENTIAL_SHARED_PREF;
//    }
//
//    public int getLogId() {
//        return mLogId;
//    }
//
//    public void setLogId(int logId) {
//        mLogId = logId;
//    }
//
//    @Override
//    public String toString() {
//        return "LoginActivity{" +
//                "MainPageButton1=" + MainPageButton1 +
//                ", username=" + username +
//                ", password=" + password +
//                ", signinButton=" + signinButton +
//                ", CREDENTIAL_SHARED_PREF='" + CREDENTIAL_SHARED_PREF + '\'' +
//                '}';
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        username = findViewById(R.id.editTextText);
//        password = findViewById(R.id.editTextNumberPassword);
//        signinButton = findViewById(R.id.button3);
//        MainPageButton1 = findViewById(R.id.button7);
//
//        MainPageButton1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openMainActivity();
//            }
//        });
//
//        signinButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                SharedPreferences sharedPref = getSharedPreferences(CREDENTIAL_SHARED_PREF, Context.MODE_PRIVATE);
//                String strUsername = sharedPref.getString("Username", null);
//                String strPassword = sharedPref.getString("Password", null);
//
//                String username_from_ed = username.getText().toString();
//                String password_from_ed = password.getText().toString();
//
//                if (strUsername != null && username_from_ed != null && strUsername.equalsIgnoreCase(username_from_ed)) {
//                    if (strPassword != null && password_from_ed != null && strPassword.equalsIgnoreCase(password_from_ed)) {
//                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
//                    }
//                }else{
//                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//        public void openMainActivity() {
//            Intent intent3 = new Intent(this, MainActivity.class);
//            startActivity(intent3);
//        }
//    AppDataBase.AppDatabase db = Room.databaseBuilder(getApplicationContext(),
//            AppDataBase.AppDatabase.class, "database-name").build();
//    UserDao userDao = db.userDao();
//    List<LoginActivity> users = userDao.getAll();

}