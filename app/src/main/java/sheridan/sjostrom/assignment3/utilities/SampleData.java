package sheridan.sjostrom.assignment3.utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import sheridan.sjostrom.assignment3.database.NoteEntity;

public class SampleData {

    private static final String SAMPLE_TITLE_1 = "Title1";
    private static final String SAMPLE_TITLE_2 = "Title2";
    private static final String SAMPLE_TITLE_3 = "Title3";

    private static final String SAMPLE_TEXT_1 = "A simple note";
    private static final String SAMPLE_TEXT_2 = "A note with a \n line feed";
    private static final String SAMPLE_TEXT_3 = "This is another sample note with a lot of text. This is another sample note with a lot of text. This is another sample note with a lot of text";

    private static Date getDate(int diff) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.MILLISECOND, diff);
        return cal.getTime();
    }

    public static List<NoteEntity> getNotes() {
        List<NoteEntity> notes = new ArrayList<>();
        notes.add(new NoteEntity(1, getDate(0), SAMPLE_TITLE_1, SAMPLE_TEXT_1));
        notes.add(new NoteEntity(2, getDate(-1), SAMPLE_TITLE_2, SAMPLE_TEXT_2));
        notes.add(new NoteEntity(3, getDate(-2), SAMPLE_TITLE_3, SAMPLE_TEXT_3));
        return notes;
    }
}
