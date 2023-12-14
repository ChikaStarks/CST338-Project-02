package com.example.schoolbees.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

//import com.example.schoolbees.LoginActivity;
import com.example.schoolbees.Post;
import com.example.schoolbees.User;
//import com.example.schoolbees.UserPost;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User user);


    @Query("SELECT * FROM " + AppDataBase.USER_TABLE2)
    List<User> getAllUsers();

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE2 + " WHERE mUserName = :username ")
    User getUserByUsername(String username);

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE2 + " WHERE mUserId = :userId")
    User getUserByUserId(int userId);


}