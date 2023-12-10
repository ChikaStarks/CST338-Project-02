package com.example.schoolbees;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schoolbees.DB.AppDataBase;
import com.example.schoolbees.DB.UserDao;

import java.util.List;
import java.util.Map;

public class CreateAccount extends AppCompatActivity {

    private Button MainPageButton2; //Main Page Button
    private EditText createUsername;
    private EditText createPass;
    private EditText confirmPass;
    private Button createButton;

    private UserDao mUserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        wireupDisplay();
        getDatabase();


        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = createUsername.getText().toString();
                String password = createPass.getText().toString();

                if (checkUserName() && checkPassword() ){
                    User newUser = new User("false", name, password);
                    mUserDao.insert(newUser);
                    openLoginActivity();
                }

            }
        });

        MainPageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

    }

    private void wireupDisplay() {
        MainPageButton2 = findViewById(R.id.button9);
        createUsername = findViewById(R.id.editTextText2);
        createPass = findViewById(R.id.editTextNumberPassword2);
        confirmPass = findViewById(R.id.editTextNumberPassword3);
        createButton = findViewById(R.id.button8);
    }


    public void openMainActivity() {
        Intent intent4 = new Intent(this, MainActivity.class);
        startActivity(intent4);
    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void getDatabase() {
        mUserDao = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDao();
    }

    private void getValuesFromDisplay() {
        String strPassword = createPass.getText().toString();
        String strConfirmationPassword = confirmPass.getText().toString();
        String strUsername = createUsername.getText().toString();
    }


    public boolean checkUserName() {
        List<User> users = mUserDao.getAllUsers();
        String name = createUsername.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter the username.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (name.equals("admin2") || name.equals("testuser1")){
            Toast.makeText(this, "This username \"" + name + "\" has been taken.", Toast.LENGTH_SHORT).show();
            return false;
        }
        for (User user : users) {
            if (user.getUserName().equals(name)) {
                Toast.makeText(this, "This username \"" + name + "\" has been taken.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private boolean checkPassword() {
        String password = createPass.getText().toString();
        String confPassword = confirmPass.getText().toString();
        if (password.isEmpty()) {
            Toast.makeText(this, "Please enter the password.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (confPassword.isEmpty()) {
            Toast.makeText(this, "Please enter the confirmation password.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!password.equals(confPassword)) {
            Toast.makeText(this, "The password doesn't match.", Toast.LENGTH_SHORT).show();
            return false;
        }
        Toast.makeText(this, "Success! Your user has been created.", Toast.LENGTH_SHORT).show();
        return true;
    }
}
