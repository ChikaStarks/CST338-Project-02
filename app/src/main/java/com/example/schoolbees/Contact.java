package com.example.schoolbees;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.schoolbees.DB.AppDataBase;

@Entity(tableName = AppDataBase.CONTACT_TABLE)
public class Contact {
//    @PrimaryKey(autoGenerate = true)
//    private int mContactId;
@PrimaryKey(autoGenerate = true)
    private int mPostId;

    //private int mPostId;

    private int mUserId;

    private String mName;
    private String mEmail;
    private int mPhone;


    public Contact(String name, String email, int phone) {
        mName = name;
        mEmail = email;
        mPhone = phone;
    }

    public int getPostId() {
        return mPostId;
    }

    public void setPostId(int postId) {
        mPostId = postId;
    }

//    public int getContactId() {
//        return mContactId;
//    }
//
//    public void setContactId(int contactId) {
//        mContactId = contactId;
//    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public int getPhone() {
        return mPhone;
    }

    public void setPhone(int phone) {
        mPhone = phone;
    }

    @Override
    public String toString() {
        return "Post ID: " + mPostId + "\n" +
                "Name: " + mName + "\n" +
                "Email Address: " + mEmail + "\n" +
                "Phone Number: " + mPhone + "\n" +
                "********************" + "\n" + "\n";
    }
}
