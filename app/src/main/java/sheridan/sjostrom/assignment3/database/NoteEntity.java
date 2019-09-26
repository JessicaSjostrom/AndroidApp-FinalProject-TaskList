package sheridan.sjostrom.assignment3.database;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class NoteEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date date;
    private String title;
    private String description;

    @Ignore
    public NoteEntity() {
    }

    public NoteEntity(int id, Date date, String title, String description) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.description = description;
    }

    @Ignore
    public NoteEntity(Date date, String title, String description) {
        this.date = date;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "NoteEntity{" +
                "id=" + id +
                ", date=" + date +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
