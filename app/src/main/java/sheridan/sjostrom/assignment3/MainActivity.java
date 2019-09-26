package sheridan.sjostrom.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sheridan.sjostrom.assignment3.database.NoteEntity;
import sheridan.sjostrom.assignment3.ui.NotesAdapter;
import sheridan.sjostrom.assignment3.viewmodel.MainViewModel;

import static sheridan.sjostrom.assignment3.utilities.Constants.ABOUT_FRAGMENT;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private List<NoteEntity> notesData = new ArrayList<>();
    private NotesAdapter mAdapter;
    private MainViewModel mViewModel;

    @OnClick(R.id.fab)
    void fabClickHandler() {
        Intent intent = new Intent(this, EditorActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();

    }

    private void initViewModel() {
        final Observer<List<NoteEntity>> notesObserver = new Observer<List<NoteEntity>>(){
            @Override
            public void onChanged(@Nullable List<NoteEntity> noteEntities) {
                notesData.clear();
                notesData.addAll(noteEntities);
                if (mAdapter == null) {
                    mAdapter = new NotesAdapter(notesData, MainActivity.this);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            }
        };

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mViewModel.mNotes.observe(this, notesObserver);
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutmanager);

        mAdapter = new NotesAdapter(notesData, this);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration divider = new DividerItemDecoration(mRecyclerView.getContext(), layoutmanager.getOrientation());
        mRecyclerView.addItemDecoration(divider);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_sample_data) {
            addSampleData();
            return true;
        } else if (id == R.id.action_delete_all) {
            deleteAllNotes();
            return true; 
        } else if (id == R.id.action_about) {
            AboutFragment aboutFragment = new AboutFragment().newInstance();
            aboutFragment.show(getSupportFragmentManager(), ABOUT_FRAGMENT);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAllNotes() {
        mViewModel.deleteAllNotes();

    }

    private void addSampleData() {
        mViewModel.addSampleData();

    }
}
