package com.example.schoolbees;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Entity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schoolbees.DB.AppDataBase;
import com.example.schoolbees.DB.ContactDao;
import com.example.schoolbees.DB.UserDao;
import com.example.schoolbees.databinding.ActivityContactInfoBinding;


public class ContactInfo extends AppCompatActivity {

    ActivityContactInfoBinding mActivityContactInfoBinding;

    private ContactDao mContactDao;
    private UserDao mUserDao;
    private String mNameInput;
    private String mEmailInput;

    private Contact mContact;
    private int mPhoneInput = -1;
    private int mPhoneInputLength;
    EditText mNameInputField;
    EditText mEmailInputField;
    EditText mPhoneInputField;

    private User mUser;

    Button mSendContactInfoButton;

    private int mContactId = -1;

    Button mCancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        mActivityContactInfoBinding = ActivityContactInfoBinding.inflate(getLayoutInflater());
        setContentView(mActivityContactInfoBinding.getRoot());

        getContactDatabase();
        getDatabase();
        wireupDisplay();

    }//end of onCreate


    private void wireupDisplay() {
        mNameInputField = mActivityContactInfoBinding.editTextText4;
        mEmailInputField = mActivityContactInfoBinding.editTextTextEmailAddress;
        mPhoneInputField = mActivityContactInfoBinding.editTextPhone;
        mSendContactInfoButton = mActivityContactInfoBinding.button11;

        mSendContactInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromDisplay();
                  if(checkInput()){
                    sendButtonFunction();
                }

            }
        });



    }


    private void showInquiryConfirmation() {
        Toast.makeText(this, "Your contact info has been sent to the poster.", Toast.LENGTH_SHORT).show();
    }

    private void getValuesFromDisplay(){
        mNameInput = mNameInputField.getText().toString();
        mEmailInput = mEmailInputField.getText().toString();
        try {
        mPhoneInput = Integer.parseInt(mPhoneInputField.getText().toString());}
        catch (NumberFormatException e) {
        }
    }


    private boolean checkInput() {
        if (mNameInput.isEmpty()) {
            Toast.makeText(this, "Please enter your name.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mEmailInput.isEmpty()) {
            Toast.makeText(this, "Please enter your email address.", Toast.LENGTH_SHORT).show();
            return false;
        }

        mPhoneInputLength = mPhoneInputField.getText().toString().length();
        if (mPhoneInputLength == 0) {
            Toast.makeText(this, "Please enter your phone number.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void sendButtonFunction(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage("Are you sure to send your info?");
        alertBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    String name = mNameInputField.getText().toString();
                    String email = mEmailInputField.getText().toString();
                    int phone = Integer.parseInt(mPhoneInputField.getText().toString());

                    Contact newContact = new Contact(name, email, phone);
                    mContactDao.insert(newContact);
                    showInquiryConfirmation();
                    goBacktoPostSearchPage();

                }

                catch (NumberFormatException e){
                    Log.d("Int", "Unable to convert to string.");

                }
            }
        });

        alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertBuilder.create().show();
    }




    private void goBacktoPostSearchPage() {
        Intent intent = new Intent(this, PostSearch.class);
        startActivity(intent);
    }

    private void getDatabase() {
        mUserDao = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build().getUserDao();
    }

    private void getContactDatabase(){
        mContactDao = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .getContactDao();
    }

    private void usernameBinding(){

    }

    private void goBackToPostSearchPage(){
        Intent intent = new Intent (this, PostSearch.class);
        startActivity(intent);
    }


}