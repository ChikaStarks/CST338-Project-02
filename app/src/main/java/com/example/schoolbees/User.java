package com.example.schoolbees;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.schoolbees.DB.AppDataBase;

import java.util.Map;

@Entity(tableName = AppDataBase.USER_TABLE2)  //user entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private int mUserId;
    private String mUserName;
    private String mPassword;
    private String mIsAdmin;

    public User(String isAdmin, String userName, String password) {
        mIsAdmin = isAdmin;
        mUserName = userName;
        mPassword = password;

    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getIsAdmin() {
        return mIsAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        mIsAdmin = isAdmin;
    }

    @Override
    public String toString() {
        return "User ID: " + mUserId + "\n" +
                "Username: '" + mUserName + "\n" +
                "IsAdmin: '" + mIsAdmin + "\n" +
                "********************" + "\n" + "\n";
    }
}
