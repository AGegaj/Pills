package org.unipr.pills;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import adapter.PillHomeAdapter;
import adapter.PillboxAdapter;
import database.Database;
import de.hdodenhof.circleimageview.CircleImageView;
import fragment.HomeFragment;
import fragment.PillboxFragment;

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

    RecyclerView recyclerView;
    PillHomeAdapter adapter;
    RecyclerView.LayoutManager layoutManager;


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
        initPillList(date);
//        recyclerView = findViewById(R.id.recycler_view_home);
//        adapter = new PillHomeAdapter(this, pillPhotoData, pillNameData, pillTime);
//        recyclerView.setAdapter(adapter);
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);

        navigation.setOnNavigationItemSelectedListener(this);

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate();
            }
        });

        imgSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate();
            }
        });

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
                break;

            case R.id.navigation_pillbox:
                fragment = new PillboxFragment();
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

    public void initPillList(Date date){

    }
}
