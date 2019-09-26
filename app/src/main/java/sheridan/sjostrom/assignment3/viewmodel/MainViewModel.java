package sheridan.sjostrom.assignment3.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import sheridan.sjostrom.assignment3.database.AppRepository;
import sheridan.sjostrom.assignment3.database.NoteEntity;

public class MainViewModel extends AndroidViewModel {

    public LiveData<List<NoteEntity>> mNotes;
    private AppRepository mRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(application.getApplicationContext());
        mNotes = mRepository.mNotes;
    }

    public void addSampleData() {
        mRepository.addSampleData();
    }

    public void deleteAllNotes() {
        mRepository.deleteAllNotes();
    }
}
