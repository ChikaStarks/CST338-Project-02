package com.example.schoolbees;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.schoolbees.DB.AppDataBase;

@Entity(tableName = AppDataBase.CONTACT_TABLE)
public class Contact {
    @PrimaryKey(autoGenerate = true)
    private int mPostId;

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
