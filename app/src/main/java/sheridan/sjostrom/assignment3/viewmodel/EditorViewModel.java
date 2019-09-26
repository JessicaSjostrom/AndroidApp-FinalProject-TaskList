package sheridan.sjostrom.assignment3.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import sheridan.sjostrom.assignment3.database.AppRepository;
import sheridan.sjostrom.assignment3.database.NoteEntity;

public class EditorViewModel extends AndroidViewModel {

    public MutableLiveData<NoteEntity> mLiveNote = new MutableLiveData<>();
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public EditorViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadData(int noteId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                NoteEntity note = mRepository.getNoteById(noteId);
                mLiveNote.postValue(note);
            }
        });
    }

    public void saveNote(Date date, String noteTitle, String noteDescription) {
        NoteEntity note = mLiveNote.getValue();
        if (note == null) {
            if (TextUtils.isEmpty(noteTitle.trim())) {
                return;
            }
            note = new NoteEntity(date, noteTitle.trim(), noteDescription.trim());
        } else {
            note.setDate(date);
            note.setTitle(noteTitle);
            note.setDescription(noteDescription);
        }
        mRepository.insertNote(note);
    }

    public void deleteNote() {
        mRepository.deleteNote(mLiveNote.getValue());
    }
}
