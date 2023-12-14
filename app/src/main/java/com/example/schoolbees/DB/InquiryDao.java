package com.example.schoolbees.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.schoolbees.Inquiry;
import com.example.schoolbees.Post;
import com.example.schoolbees.Report;

import java.util.List;

@Dao
public interface InquiryDao {

    @Insert
    void insert (Inquiry... inquiries);

    @Update
    void update (Inquiry... inquiries);

    @Delete
    void delete(Inquiry inquiry);

    @Query("SELECT * FROM " + AppDataBase.INQUIRY_TABLE)
    List<Inquiry> getAllInquiries();

    @Query("SELECT * FROM " + AppDataBase.INQUIRY_TABLE + " WHERE mPostId = :postId ")
    Inquiry getInquiryByPostId(int postId);

    @Query("SELECT * FROM " + AppDataBase.INQUIRY_TABLE + " WHERE mPostId = :postId ")
    List<Inquiry> getAllInquiriesByPostId(int postId);


}
