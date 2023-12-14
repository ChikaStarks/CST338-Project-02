package com.example.schoolbees;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.schoolbees.DB.AppDataBase;

@Entity(tableName = AppDataBase.REPORT_TABLE)
public class Report {
    @PrimaryKey(autoGenerate = true)
    private int mPostId;
    private String mPostname;
    private String mDescription;
    private String mPrice;
    private String mLocation;
    private String mContact;

    public Report(String postname, String description, String price, String location, String contact) {

        mPostname = postname;
        mDescription = description;
        mPrice = price;
        mLocation = location;
        mContact = contact;
    }


    public String getPostname() {
        return mPostname;
    }

    public void setPostname(String postname) {
        mPostname = postname;
    }

    public int getPostId() {
        return mPostId;
    }

    public void setPostId(int postId) {
        mPostId = postId;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getContact() {
        return mContact;
    }

    public void setContact(String contact) {
        mContact = contact;
    }

    @Override
    public String toString() {
        return "Report ID: " + mPostId + "\n" +
                "Post Title: " + mPostname + "\n" +
                "Description: " + mDescription + "\n" +
                "Price: " + mPrice + "\n" +
                "Location: " + mLocation + "\n" +
                "Contact Info: " + mContact + "\n" +
                "********************" + "\n" + "\n";
    }

}
