package com.example.schoolbees;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Entity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schoolbees.DB.AppDataBase;
import com.example.schoolbees.DB.ContactDao;
import com.example.schoolbees.databinding.ActivityContactInfoBinding;


public class ContactInfo extends AppCompatActivity {

    ActivityContactInfoBinding mActivityContactInfoBinding;

    private ContactDao mContactDao;
    private String mNameInput;
    private String mEmailInput;
    private int mPhoneInput;
    private int mPhoneInputLength;
    EditText mNameInputField;
    EditText mEmailInputField;
    EditText mPhoneInputField;

    Button mSendContactInfoButton;

    Button mCancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        mActivityContactInfoBinding = ActivityContactInfoBinding.inflate(getLayoutInflater());
        setContentView(mActivityContactInfoBinding.getRoot());

        getContactDatabase();
        wireupDisplay();

    }//end of onCreate


    private void wireupDisplay() {
        mNameInputField = mActivityContactInfoBinding.editTextText4;
        mEmailInputField = mActivityContactInfoBinding.editTextTextEmailAddress;
        mPhoneInputField = mActivityContactInfoBinding.editTextPhone;
        mSendContactInfoButton = mActivityContactInfoBinding.button11;
        mCancelButton = mActivityContactInfoBinding.button12;

        mSendContactInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromDisplay();
                if(checkInput()){
                    sendButtonFunction();
                }

            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelButtonFunction();
            }
        });

    }


    private void showInquiryConfirmation() {
        Toast.makeText(this, "Your contact info has been sent to the poster.", Toast.LENGTH_SHORT).show();
    }

    private void getValuesFromDisplay(){
        mNameInput = mNameInputField.getText().toString();
        mEmailInput = mEmailInputField.getText().toString();
        mPhoneInput = Integer.parseInt(mPhoneInputField.getText().toString());
    }
    //mPostIdLength = mEnterIDField.getText().length();
    private boolean checkInput(){
        if (mNameInput.isEmpty()){
            Toast.makeText(this, "Please enter your name.", Toast.LENGTH_SHORT).show();
            return false;
        }
       //TODO something is wrong with email input check
        mPhoneInputLength = mPhoneInputField.getText().length();
        if(mPhoneInputLength == 0){
            if (mEmailInput.isEmpty()){
                Toast.makeText(this,"Please enter at least one of your contact info.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
       // if((mNameInput.isEmpty()) && mEmailInput.isEmpty())
        return true;
    }

    //        mPostId = Integer.parseInt(mEnterIDField.getText().toString());
//        mPostIdLength = mEnterIDField.getText().length();

    private void sendButtonFunction(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage("Are you sure to send your info?");
        alertBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = mNameInputField.getText().toString();
                String email = mEmailInputField.getText().toString();
                int phone = Integer.parseInt(mPhoneInputField.getText().toString());

                Contact newContact = new Contact(name, email, phone);
                mContactDao.insert(newContact);
                showInquiryConfirmation();
            }
        });

        alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertBuilder.create().show();
    }

    private void cancelButtonFunction() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage("Are you sure to cancel?");

        alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                goBacktoPostSearchPage();
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

    private void getContactDatabase(){
        mContactDao = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .getContactDao();
    }


}