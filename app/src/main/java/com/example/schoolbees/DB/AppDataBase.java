package com.example.schoolbees.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.schoolbees.Contact;
import com.example.schoolbees.Post;
import com.example.schoolbees.Report;
import com.example.schoolbees.User;

@Database(entities = {User.class, Post.class, Report.class, Contact.class}, version = 12)
public abstract class AppDataBase extends RoomDatabase{

    public static final String DATABASE_NAME = "schoolbees.DB";
    public static final String USER_TABLE2 = "USER_TABLE2";  //sign in info
    public static final String POST_TABLE = "POST_TABLE";
    public static final String REPORT_TABLE = "REPORT_TABLE";


    public static final String CONTACT_TABLE = "CONTACT_TABLE";

    //added for repository
//    static final ExecutorService databaseWriteExecutor =
//            Executors.newFixedThreadPool(Integer.parseInt(POST_TABLE));
    public abstract UserDao getUserDao();
    public abstract PostDao getPostDao();
    public abstract ReportDao getReportDao();

    public abstract ContactDao getContactDao();
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
