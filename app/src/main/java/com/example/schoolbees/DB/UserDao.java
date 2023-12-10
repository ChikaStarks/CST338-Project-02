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

//    @Insert
//    void insert(Post... posts);
//
//    @Update
//    void update(Post... posts);
//
//    @Delete
//    void delete(Post post);

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE2)
    List<User> getAllUsers();

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE2 + " WHERE mUserName = :username ")
    User getUserByUsername(String username);

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE2 + " WHERE mUserId = :userId")
    User getUserByUserId(int userId);

    // need to check this error
//    @Query("SELECT * FROM " + AppDataBase.POST_TABLE)
//    List<Post> getAllPosts();
//
//    @Query("SELECT * FROM " + AppDataBase.POST_TABLE + " WHERE mPostId = :postId ")
//    User getPostByPostId(int postId);
//
//    @Query("SELECT * FROM " + AppDataBase.POST_TABLE + " WHERE mPostName = :postname ")
//    User getPostByPostname(String postname);



}