package com.example.schoolbees.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.schoolbees.Contact;

import java.util.List;

@Dao
public interface ContactDao {
    @Insert
    void insert (Contact... contacts);

    @Update
    void update (Contact... contacts);

    @Delete
    void delete(Contact contact);

    @Query("SELECT * FROM " + AppDataBase.CONTACT_TABLE)
    List<Contact> getAllContactInfos();

//    @Query("SELECT * FROM " + AppDataBase.CONTACT_TABLE + " WHERE mContactId = :contactId ")
//    Contact getContactInfoByContactId(int contactId);
//
//    @Query("SELECT * FROM " + AppDataBase.CONTACT_TABLE + " WHERE mContactId = :contactId ")
//    List<Contact> getAllContactInfosByContactId(int contactId);

    @Query("SELECT * FROM " + AppDataBase.CONTACT_TABLE + " WHERE mPostId = :postId ")
    Contact getContactInfoByPostId(int postId);

//    @Query("SELECT * FROM " + AppDataBase.CONTACT_TABLE + " WHERE mPostId = :postId ")
//    Contact getContactInfoBy(int postId);

    @Query("SELECT * FROM " + AppDataBase.CONTACT_TABLE + " WHERE mPostId = :postId ")
    List<Contact> getAllContactInfosByPostId(int postId);


}
