package com.example.schoolbees.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.schoolbees.LoginActivity;
import com.example.schoolbees.Post;
import com.example.schoolbees.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Post.class}, version = 7)
public abstract class AppDataBase extends RoomDatabase{

    public static final String DATABASE_NAME = "schoolbees.DB";
    public static final String USER_TABLE2 = "USER_TABLE2";  //sign in info
    public static final String POST_TABLE = "POST_TABLE";

    //added for repository
//    static final ExecutorService databaseWriteExecutor =
//            Executors.newFixedThreadPool(Integer.parseInt(POST_TABLE));
    public abstract UserDao getUserDao();
    public abstract PostDao getPostDao();
    private static volatile AppDataBase instance;
    private static final Object LOCK = new Object();


public static AppDataBase getInstance(Context context){
    if(instance == null){
        synchronized (LOCK){
            if(instance == null){
                instance = Room.databaseBuilder(context.getApplicationContext(),
                AppDataBase.class,
                DATABASE_NAME).fallbackToDestructiveMigration().build(); //added fallbackToDestructiveMigration()
            }
        }
    }
    return instance;
}
}
