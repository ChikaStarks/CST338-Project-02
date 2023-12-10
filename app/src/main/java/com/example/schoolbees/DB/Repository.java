//package com.example.schoolbees.DB;
//
//import android.app.Application;
//
//import androidx.lifecycle.LiveData;
//
//import com.example.schoolbees.Post;
//
//import java.util.List;
//
//public class Repository {
//
//    private PostDao mPostDao;
//    private LiveData<List<Post>> mAllPosts;
//
//    Repository(Application application) {
//        AppDataBase db = AppDataBase.getInstance(application);
//        mPostDao = db.getPostDao();
//        mAllPosts = mPostDao.getAllLivePosts();
//    }
//
//    // Room executes all queries on a separate thread.
//    // Observed LiveData will notify the observer when the data has changed.
//    LiveData<List<Post>> getAllPosts() {
//        return mAllPosts;
//    }
//
//    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
//    // that you're not doing any long running operations on the main thread, blocking the UI.
//    void insert(Post post) {
//        AppDataBase.databaseWriteExecutor.execute(() -> {
//            mPostDao.insert(post);
//        });
//    }
//
//}
