/**
 * App Title: School Bees
 *
 * Abstract: This app is for the project 2 assignment (CST338)
 * Author: Chika Starks
 * Date: 2023-12-15
 */

package com.example.schoolbees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button logInButton; //LogIn Button (id: button)
    private Button newAccountButton; // Create New Account Button (id: button2)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logInButton = findViewById(R.id.button);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });
        newAccountButton = findViewById(R.id.button2);
        newAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateAccount();
            }
        });
    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void openCreateAccount() {
            Intent intent2 = new Intent(this, CreateAccount.class);
            startActivity(intent2);
        }
}