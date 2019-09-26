package sheridan.sjostrom.assignment3;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.PrimaryKey;
import butterknife.BindView;
import butterknife.ButterKnife;
import sheridan.sjostrom.assignment3.database.DateConverter;
import sheridan.sjostrom.assignment3.database.NoteEntity;
import sheridan.sjostrom.assignment3.utilities.Constants;
import sheridan.sjostrom.assignment3.viewmodel.EditorViewModel;

import java.util.Calendar;
import java.util.Date;

import static sheridan.sjostrom.assignment3.utilities.Constants.ABOUT_FRAGMENT;
import static sheridan.sjostrom.assignment3.utilities.Constants.CONFIRM_FRAGMENT;
import static sheridan.sjostrom.assignment3.utilities.Constants.DATE_PICKER_FRAGMENT;
import static sheridan.sjostrom.assignment3.utilities.Constants.EDITING_KEY;
import static sheridan.sjostrom.assignment3.utilities.Constants.NOTE_ID_KEY;

public class EditorActivity extends AppCompatActivity implements DatePickerFragment.DateSetListener, ConfirmFragment.ConfirmListener{

    @BindView(R.id.note_title)
    TextView mTitleView;

    @BindView(R.id.note_description)
    TextView mDescriptionView;

    @BindView(R.id.date_value)
    TextView mDateView;

    Date mDate;
    private boolean mConfirmed;

    private EditorViewModel mViewModel;
    private boolean mNewNote, mEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            mEditing = savedInstanceState.getBoolean(EDITING_KEY);
            mDate = (Date) savedInstanceState.getSerializable(Constants.DATE_KEY);
        } else {
            mDate = new Date();
        }

        mDateView.setText(DateFormat.getLongDateFormat(this).format(mDate));

        ImageButton editDateButton = findViewById(R.id.edit_date_button);
        editDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment fragment = DatePickerFragment.getInstance(mDate);
                fragment.show(getSupportFragmentManager(), DATE_PICKER_FRAGMENT);
            }
        });
        initViewModel();
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(EditorViewModel.class);
        mViewModel.mLiveNote.observe(this, new Observer<NoteEntity>() {
            @Override
            public void onChanged(NoteEntity noteEntity) {
                if (noteEntity != null && !mEditing) {
                    mTitleView.setText(noteEntity.getTitle());
                    mDescriptionView.setText(noteEntity.getDescription());
                    mDateView.setText(java.text.DateFormat.getDateInstance().format(noteEntity.getDate()));
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setTitle(R.string.new_note);
            mNewNote = true;
        } else {
            setTitle(R.string.edit_note);
            int noteId = extras.getInt(NOTE_ID_KEY);
            mViewModel.loadData(noteId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNewNote) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_editor, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            saveAndReturn();
            return true;
        } else if (item.getItemId() == R.id.action_delete) {
            ConfirmFragment confirmFragment
                    = ConfirmFragment.newInstance(0, getString(R.string.confirm_message));
            confirmFragment.show(getSupportFragmentManager(), CONFIRM_FRAGMENT);
        } else if (item.getItemId() == R.id.action_about) {
            AboutFragment aboutFragment = new AboutFragment().newInstance();
            aboutFragment.show(getSupportFragmentManager(), ABOUT_FRAGMENT);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfirmed(int dialogID) {
        mConfirmed = true;
        mViewModel.deleteNote();
        finish();
    }

    @Override
    public void onBackPressed() {
        saveAndReturn();
    }

    private void saveAndReturn() {
        mViewModel.saveNote(mDate, mTitleView.getText().toString(), mDescriptionView.getText().toString());
        finish();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        outState.putSerializable(Constants.DATE_KEY, mDate);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDateSet(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        calendar.set(year, month, day);
        mDate = calendar.getTime();
        mDateView.setText(DateFormat.getLongDateFormat(this).format(mDate));
    }
}
