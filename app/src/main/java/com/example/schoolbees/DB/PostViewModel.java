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
//public class PostViewModel extends AppDataBase{
//
//    private Repository mRepository;
//
//    private final LiveData<List<Post>> mAllPosts;
//
//    public PostViewModel (Application application) {
//        super(application);
//        mRepository = new WordRepository(application);
//        mAllWords = mRepository.getAllWords();
//    }
//
//    LiveData<List<Word>> getAllWords() { return mAllWords; }
//
//    public void insert(Word word) { mRepository.insert(word); }
//}
