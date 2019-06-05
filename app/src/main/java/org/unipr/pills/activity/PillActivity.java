package org.unipr.pills.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
    TextView txtPillReminderTime, txtDescription;

    private ArrayList<String> pillReminderData = new ArrayList<String>();
    private CircleImageView image;
    private TextView txName;
    private TextView txReminder, txDuration, txStart;
    private Database db;
    Integer pillId;
    String pillName, description;
    String url = "https://en.wikipedia.org/wiki/";



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
        txtDescription = findViewById(R.id.txtDescription);

        txtPillReminderTime = findViewById(R.id.txtPillReminderTime);
        recyclerView = findViewById(R.id.recycler_view_reminders);


        Bundle extras = getIntent().getExtras();
        String pillNam = extras.getString("pillName");
        url += pillNam;
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

            PillDescription pillDescription = new PillDescription();
            pillDescription.execute();

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
                            .setTitle(R.string.delete)
                            .setMessage(R.string.deleteMsg)


                            .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    db.delete(pillId);
                                    goToMainActivity();

                                }
                            })

                            .setNegativeButton(R.string.CANCEL, null)
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

    public class PillDescription extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog = new ProgressDialog(PillActivity.this);

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Document document = Jsoup.connect(url).userAgent(
                        "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
                        .get();

                Element paragraph = document.select("p").first();
                if(paragraph.text().isEmpty())
                    paragraph=document.select("p").get(1);

                description = paragraph.text().replaceAll("\\[[0-9]+\\]", "");
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            if(description == null || description.isEmpty())
                txtDescription.setText(R.string.txtInfoPill);
            else
                txtDescription.setText(description);

            progressDialog.cancel();
        }
    }
}
