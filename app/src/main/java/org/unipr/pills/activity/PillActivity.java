package org.unipr.pills.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.unipr.pills.R;
import org.unipr.pills.adapter.ReminderAdapter;
import org.unipr.pills.database.Database;

import de.hdodenhof.circleimageview.CircleImageView;

import org.unipr.pills.model.PillDataResult;
import org.unipr.pills.model.ReminderDataResult;

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
    Integer pillId;
    String pillName;


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
            PillDataResult pill;
            pill = db.getPillByName(pillNam);
            pillId = pill.getId();
            pillName = pill.getPillName();
            initReminderList(pillId);
            image.setImageResource(pill.getPhotoId());
            txName.setText(pill.getPillName());
            txReminder.setText(pill.getReminderTimes());
            txDuration.setText(pill.getFrequency());
            txStart.setText("From " + dateFormat.format(pill.getStart()));


        } catch (Exception e) {
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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_delete, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                this.finish();
                return true;

            case R.id.action_delete:
                try {
                    new AlertDialog.Builder(this)
                            .setTitle("Delete")
                            .setMessage("Are you sure you want to delete this pill?")


                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    db.delete(pillId);
                                    goToMainActivity();

                                }
                            })

                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                } catch (Exception e) {

                }
                return true;

            case R.id.action_edit:
                Intent intent = new Intent(this, UpdateMedicament.class);
                intent.putExtra("pillId", pillId);
                intent.putExtra("pillName", pillName);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void goToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));

    }

    public void initReminderList(Integer id) {
        try {

            ArrayList<ReminderDataResult> reminderDataResults = db.getReminders(id);
            for (ReminderDataResult reminder : reminderDataResults) {
                String text = reminder.getHour() + ":" + reminder.getMinutes() + " " + reminder.getQuantity() + " pill(s)";
                pillReminderData.add(text);
            }

            db.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
