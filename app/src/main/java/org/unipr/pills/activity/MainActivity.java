package org.unipr.pills.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.unipr.pills.R;
import org.unipr.pills.adapter.PillHomeAdapter;
import org.unipr.pills.database.Database;
import de.hdodenhof.circleimageview.CircleImageView;
import org.unipr.pills.fragment.HomeFragment;
import org.unipr.pills.fragment.PillboxFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView navigation;
    Toolbar toolbar;
    TextView selectDate, txtNoMeds;
    DatePickerDialog datePickerDialog;
    ImageView imgSelectDate;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    public static String[] monthName = {"Jan", "Feb",
            "Mar", "Apr", "May", "Jun", "Jul",
            "Aug", "Sep", "Oct", "Nov",
            "Dec"};

    private RecyclerView recyclerView;
    private PillHomeAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    private ArrayList<String> pillNameData = new ArrayList<String>();
    private ArrayList<Integer> pillPhotoData = new ArrayList<Integer>();
    private ArrayList<String> pillTime = new ArrayList<String>();

    TextView txtPillHomeName;
    CircleImageView imgPillHome;
    TextView txtPillTime;

    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConstraintLayout layout =  findViewById(R.id.container);
        AlphaAnimation animation = new AlphaAnimation(0.0f , 1.0f ) ;
        animation.setFillAfter(true);
        animation.setDuration(1200);
        layout.startAnimation(animation);

        loadFragment(new HomeFragment());

        txtPillHomeName = findViewById(R.id.txPillHomeName);
        imgPillHome = findViewById(R.id.imgPillHome);
        txtPillTime = findViewById(R.id.txtTimeHome);
        navigation = findViewById(R.id.bottom_navigation);
        toolbar = findViewById(R.id.toolbar);
        selectDate = findViewById(R.id.btnDate);
        imgSelectDate = findViewById(R.id.imgSelectDate);
        txtNoMeds = findViewById(R.id.txtNoMeds);
        setSupportActionBar(toolbar);

        Date date = Calendar.getInstance().getTime();
        initPillList(date.toString());
        recyclerView = findViewById(R.id.recycler_view_home);
        adapter = new PillHomeAdapter(MainActivity.this, pillPhotoData, pillNameData, pillTime);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        if(pillNameData.isEmpty())
            txtNoMeds.setText("No Meds");

        navigation.setOnNavigationItemSelectedListener(this);

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocale("sq");
//                selectDate();
            }
        });

        imgSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }
    public void selectDate() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(MainActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        selectDate.setText(monthName[month] + " " + day);
                        String date = year + "/" + (month+1) + "/" + day;
                        initPillList(date);

                    }
                }, year, month, dayOfMonth);
//                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = new HomeFragment();
                recyclerView.setVisibility(View.VISIBLE);
                break;

            case R.id.navigation_pillbox:
                fragment = new PillboxFragment();
                recyclerView.setVisibility(View.GONE);
                break;

            case R.id.navigation_add:
                Intent intent = new Intent(MainActivity.this, AddMedicament.class);
                startActivity(intent);
                break;
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public void initPillList(String date){
        pillNameData.clear();
        pillTime.clear();
        pillPhotoData.clear();

        pillPhotoData.add(2131230814);
        pillNameData.add("Aspirin");
        pillTime.add("08:00");

        pillPhotoData.add(2131230814);
        pillNameData.add("Insulin");
        pillTime.add("12:15");

        pillPhotoData.add(2131230814);
        pillNameData.add("Paracetamol");
        pillTime.add("18:30");

        pillPhotoData.add(2131230814);
        pillNameData.add("Andoll");
        pillTime.add("20:30");

    }
}
