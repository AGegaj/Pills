package org.unipr.pills;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import adapter.ReminderAdapter;
import database.Database;
import de.hdodenhof.circleimageview.CircleImageView;
import model.PillDataResult;
import model.ReminderDataResult;

public class PillActivity extends AppCompatActivity {

    Toolbar toolbarPill;
    RecyclerView recyclerView;
    ReminderAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    TextView txtPillReminderTime;

    private ArrayList<String> pillReminderData = new ArrayList<String>();
    private CircleImageView image;
    private TextView txName;
    private TextView txReminder, txDuration, txStart;
    private Database db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pill);

        toolbarPill = findViewById(R.id.toolbarPill);
        image = findViewById(R.id.imagePill);
        txName = findViewById(R.id.txtPill);
        txReminder = findViewById(R.id.txtPillReminder);
        txDuration = findViewById(R.id.txtPillDuration);
        txStart = findViewById(R.id.fromSchedule);

        txtPillReminderTime = findViewById(R.id.txtPillReminderTime);
        recyclerView = findViewById(R.id.recycler_view_reminders);


        Bundle extras = getIntent().getExtras();
        String pillNam = extras.getString("pillName");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            db = new Database(this);
            PillDataResult pill ;
            pill = db.getPillByName(pillNam);
            Integer id = pill.getId();
            initReminderList(id);
            image.setImageResource(pill.getPhotoId());
            txName.setText(pill.getPillName());
            txReminder.setText(pill.getReminderTimes());
            txDuration.setText(pill.getFrequency());
            txStart.setText("From "+dateFormat.format(pill.getStart()));


        }catch (Exception e){
            System.err.println(e);
        }

        adapter = new ReminderAdapter(this, pillReminderData);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        setSupportActionBar(toolbarPill);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable arrow = getResources().getDrawable(R.drawable.back);
        getSupportActionBar().setHomeAsUpIndicator(arrow);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void initReminderList(Integer id){
        try {

            ArrayList<ReminderDataResult> reminderDataResults = db.getReminders(id);
            for (ReminderDataResult reminder : reminderDataResults) {
                String text = reminder.getHour()+":"+reminder.getMinutes()+" "+reminder.getQuantity()+" pill(s)";
                pillReminderData.add(text);
            }

            db.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
