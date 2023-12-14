package com.example.schoolbees.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.schoolbees.Post;
import com.example.schoolbees.Report;

import java.util.List;

@Dao
public interface ReportDao {
    @Insert
    void insert (Report... reports);

    @Update
    void update (Report... reports);

    @Delete
    void delete (Report report);

    @Query("SELECT * FROM " + AppDataBase.REPORT_TABLE)
    List<Report> getAllReports();

    @Query("SELECT * FROM " + AppDataBase.REPORT_TABLE + " WHERE mPostId = :postId ")
    Report getReportByPostId(int postId);

    @Query("SELECT * FROM " + AppDataBase.REPORT_TABLE + " WHERE mPostId = :postId ")
    List<Report> getAllReportByPostId(int postId);


}
