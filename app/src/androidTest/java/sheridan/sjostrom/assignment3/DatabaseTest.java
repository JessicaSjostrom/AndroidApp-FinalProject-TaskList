package sheridan.sjostrom.assignment3;

import android.content.Context;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import sheridan.sjostrom.assignment3.database.AppDatabase;
import sheridan.sjostrom.assignment3.database.NoteDao;
import sheridan.sjostrom.assignment3.database.NoteEntity;
import sheridan.sjostrom.assignment3.utilities.SampleData;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    public static final String TAG = "JUnit";
    private AppDatabase mDb;
    private NoteDao mDao;

    @Before
    public void createDb(){
        Context context = ApplicationProvider.getApplicationContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mDao = mDb.noteDao();
        Log.i(TAG,"createDb");
    }

    @After
    public void closeDb() {
        mDb.close();
        Log.i(TAG, "closeDb");
    }

    @Test
    public void createAndRetrieveNotes() {
        mDao.insertAll(SampleData.getNotes());
        int count = mDao.getCount();
        Log.i(TAG, "createAndRetrieveNotes: count = " + count);
        assertEquals(SampleData.getNotes().size(), count);
    }

    @Test
    public void compareString() {
        mDao.insertAll(SampleData.getNotes());
        NoteEntity original = SampleData.getNotes().get(0);
        NoteEntity fromDb = mDao.getNoteById(1);
        assertEquals(original.getTitle(), fromDb.getTitle());
        assertEquals(1, fromDb.getId());
    }


}
