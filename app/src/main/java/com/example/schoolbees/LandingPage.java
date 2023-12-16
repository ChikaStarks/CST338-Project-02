package com.example.schoolbees;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.mediarouter.media.RouteListingPreference;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolbees.DB.AppDataBase;
import com.example.schoolbees.DB.UserDao;
import com.example.schoolbees.databinding.ActivityLandingPageBinding;
import com.example.schoolbees.databinding.ActivityMainBinding;

import java.util.List;

public class LandingPage extends AppCompatActivity {

    ActivityLandingPageBinding mActivityLandingPageBinding;
    private static final String USER_ID_KEY = "com.example.schoolbees.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.schoolbees.PREFERENCES_KEY";
    private Button postButton; //button4
    private Button checkButton; //button5
    private Button manageButton; //button6
    private Button SignOutButton; //logout button
    private Button mAdminThings; //Admin things button
    private Button goBackButton;
    private Button mDeleteUser;
    private Button mReport;
    private String mUsername;
    private UserDao mUserDao;
    private int mUserId = -1;
    private SharedPreferences mPreferences = null;
    private User mUser;
    private TextView mAdmin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        getDatabase();
        checkForUser();
        addUserToPreference(mUserId);
        loginUser(mUserId);

        mDeleteUser = findViewById(R.id.DeleteUser);
        mDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser(mUserId);
            }
        });

        //Admin button visibility
        mAdmin = findViewById(R.id.textView_admin);
        if(mUser != null && mUser.getUserName().equals("admin2")) {
            mAdmin.setVisibility(View.VISIBLE);
        }else{
            mAdmin.setVisibility(View.GONE);
        }

        mAdminThings = findViewById(R.id.AdminThingsButton);
        if(mUser != null && mUser.getUserName().equals("admin2") || mUser != null && mUser.getUserName().equals("admin3")) {
            mAdminThings.setVisibility(View.VISIBLE);
        }else{
            mAdminThings.setVisibility(View.GONE);
        }

        mReport = findViewById(R.id.button14);
        if(mUser != null && mUser.getUserName().equals("admin2") || mUser != null && mUser.getUserName().equals("admin3")) {
            mReport.setVisibility(View.VISIBLE);
        }else{
            mReport.setVisibility(View.GONE);
        }

        //Sign Out Button
        SignOutButton = findViewById(R.id.SignOutButton);
        SignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        //Post Button
        postButton = findViewById(R.id.button4);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserPost();
            }
        });

        //Check Button
        checkButton = findViewById(R.id.button5);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPostSearch();
            }
        });

        manageButton = findViewById(R.id.button6);
        manageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserManage();
            }
        });

        mAdminThings = findViewById(R.id.AdminThingsButton);
        mAdminThings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAdminThingsPage();
            }
        });

        mReport = findViewById(R.id.button14);
        mReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openReportPage();
            }
        });

        goBackButton = findViewById(R.id.buttonBack);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToPreviousPage();
            }
        });
    }

    public void openPostSearch(){
        Intent intent = new Intent(this, PostSearch.class);
        startActivity(intent);
    }

    private void goBackToPreviousPage(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void openUserPost() {
        Intent intent = new Intent(this, UserPost.class);
        startActivity(intent);
    }

    public void openUserManage(){
        Intent intent = new Intent(this, CommunicationPage.class);
        startActivity(intent);
    }

    private void loginUser (int userId){
        mUser = mUserDao.getUserByUserId(userId);
        addUserToPreference(userId);
        welcomeUserName();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(mUser != null){
            MenuItem item = menu.findItem(R.id.userMenuLogout);
            item.setTitle(mUser.getUserName());
        }
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean welcomeUserName() {
        if(mUser != null){
            TextView text = findViewById(R.id.textView6);
            text.setText(mUser.getUserName());
        }
        return false;
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
        mUserDao = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build().getUserDao();
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
        Intent intent = LoginActivity.intentFactory(this);
        startActivity(intent);
    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
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

    public void openMainActivity() {
        Intent intent3 = new Intent(this, MainActivity.class);
        startActivity(intent3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.userMenuLogout) {
            logoutUser();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void clearUserFromIntent(){
        getIntent().putExtra(USER_ID_KEY, -1);
    }

    private void clearUserFromPref(){
        addUserToPreference(-1);
        Toast.makeText(this, "User cleared", Toast.LENGTH_SHORT).show();
    }

    public static Intent intentFactory(Context context, int userId) {
        Intent intent = new Intent(context, LandingPage.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }

    public void MainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openAdminThingsPage(){
        Intent intent = new Intent(this, AdminThings.class);
        startActivity(intent);
    }

    public void openReportPage(){
        Intent intent = new Intent(this, ActivePost.class);
        startActivity(intent);
    }

    private void removeUserFromPreference(String username){
        if (mPreferences == null){
            getPrefs();
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.remove(username);
        editor.apply();
    }

    public void deleteUser(int userId){
        AlertDialog.Builder alertbuilder = new AlertDialog.Builder(LandingPage.this);
        alertbuilder.setMessage("Are you sure to delete your user account?");
        alertbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mUser.getIsAdmin().equals("true")){
                        Toast.makeText(LandingPage.this, "An admin account cannot be deleted.", Toast.LENGTH_SHORT).show();
                    }else{
                    Toast.makeText(LandingPage.this, "The account has been deleted.", Toast.LENGTH_SHORT).show();
                    mUser = mUserDao.getUserByUserId(userId);
                    mUserDao.delete(mUser);
                    openMainActivity();
                    }
            }
        });
        alertbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertbuilder.create().show();
    }
}