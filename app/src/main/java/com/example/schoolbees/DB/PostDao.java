package com.example.schoolbees.DB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.MapInfo;
import androidx.room.Query;
import androidx.room.Update;

import com.example.schoolbees.Post;
import com.example.schoolbees.User;

import java.util.List;

@Dao
public interface PostDao {

    @Insert
    void insert(Post... posts);

    @Update
    void update(Post... posts);

    @Delete
    void delete(Post post);

    @Query("SELECT * FROM " + AppDataBase.POST_TABLE)
    List<Post> getAllPosts();

    @Query("SELECT * FROM " + AppDataBase.POST_TABLE)
    LiveData<List<Post>> getAllLivePosts();   // added live data

    @Query("SELECT * FROM " + AppDataBase.POST_TABLE + " WHERE mPostId = :postId ")
    Post getPostByPostId(int postId);

    @Query("SELECT * FROM " + AppDataBase.POST_TABLE + " WHERE mPostname = :postname ")
    List<Post> getPostByPostname(String postname);

    @Query("SELECT * FROM " + AppDataBase.POST_TABLE + " WHERE mPostname = :postname ")
    Post getOnePostByPostname(String postname);


    @Query("SELECT * FROM " + AppDataBase.POST_TABLE + " WHERE mUserNumber = :userId ")
    List<Post> getPostBymUserId(int userId);

}


