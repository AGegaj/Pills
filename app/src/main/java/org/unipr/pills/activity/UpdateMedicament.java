package org.unipr.pills.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.unipr.pills.R;
import org.unipr.pills.database.Database;
import de.hdodenhof.circleimageview.CircleImageView;
import org.unipr.pills.fragment.DaysIntervalFragment;
import org.unipr.pills.fragment.DaysNumberFragment;
import org.unipr.pills.fragment.DaysPickerFragment;
import org.unipr.pills.fragment.QuantityFragment;
import org.unipr.pills.fragment.TimePickerFragment;
import org.unipr.pills.model.PillDataResult;
import org.unipr.pills.model.PillRegister;
import org.unipr.pills.model.ReminderDataResult;
import org.unipr.pills.model.ReminderRegister;

public class UpdateMedicament extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText inputName;
    private TextInputLayout inputLayoutName;
    private Button btnAdd;
    private Spinner spnReminder;
    private Button timePicker, timePicker2, timePicker3, timePicker4, timePicker5, timePicker6;
    private Button timePicker7, timePicker8, timePicker9, timePicker10, timePicker11, timePicker12;
    private TextView scheduleDate;
    private Button quantity, quantity2, quantity3, quantity4, quantity5, quantity6, quantity7;
    private Button quantity8, quantity9, quantity10, quantity11, quantity12;
    private RadioButton rdbContinuous, numbOfDay;
    private RadioButton intervalDays;
    private RadioButton rdbDayPicker, rdbEveryDay;
    private RadioGroup rdbGrDuration, rdbGrFrequency;
    private LinearLayout timeReminder, timeReminder2, timeReminder3, timeReminder4, timeReminder5;
    private LinearLayout timeReminder6, timeReminder7, timeReminder8, timeReminder9, timeReminder10;
    private LinearLayout timeReminder11, timeReminder12;
    private Database db;
    private TextView daysnum, txtDaysInterval, txtSpecificDays;

    DatePickerDialog datePickerDialog;
    Integer photoId;
    int year;
    int month;
    int dayOfMonth;
    int count;
    Integer pId;
    String pillNam;
    List<Integer> reminderIdList;
    Calendar calendar;
    private CircleImageView imgCapsule, imgTablet, imgLiquid, imgInjection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_medicament);

        inputName = findViewById(R.id.input_name);
        inputLayoutName = findViewById(R.id.input_layout_name);
        btnAdd = findViewById(R.id.btn_addMed);
        toolbar = findViewById(R.id.toolbarAdd);
        spnReminder = findViewById(R.id.spinner_reminder);
        scheduleDate = findViewById(R.id.scheduleDate);
        numbOfDay = findViewById(R.id.numberOfDays);
        rdbContinuous = findViewById(R.id.continuous);
        intervalDays = findViewById(R.id.daysInterval);
        txtDaysInterval = findViewById(R.id.txtDaysInterval);
        txtSpecificDays = findViewById(R.id.txtSpecificDays);
        rdbDayPicker = findViewById(R.id.specificDays);
        rdbGrDuration = findViewById(R.id.rdbGrDuration);
        rdbGrFrequency = findViewById(R.id.rdbGrFrequency);
        rdbEveryDay = findViewById(R.id.everyDay);
        imgCapsule = findViewById(R.id.imgCapsule);
        imgTablet = findViewById(R.id.imgTablet);
        imgLiquid = findViewById(R.id.imgLiquid);
        quantity = findViewById(R.id.quantity);
        quantity2 = findViewById(R.id.quantity2);
        quantity3 = findViewById(R.id.quantity3);
        quantity4 = findViewById(R.id.quantity4);
        quantity5 = findViewById(R.id.quantity5);
        quantity6 = findViewById(R.id.quantity6);
        quantity7 = findViewById(R.id.quantity7);
        quantity8 = findViewById(R.id.quantity8);
        quantity9 = findViewById(R.id.quantity9);
        quantity10 = findViewById(R.id.quantity10);
        quantity11 = findViewById(R.id.quantity11);
        quantity12 = findViewById(R.id.quantity12);
        timePicker = findViewById(R.id.timePicker);
        timePicker2 = findViewById(R.id.timePicker2);
        timePicker3 = findViewById(R.id.timePicker3);
        timePicker4 = findViewById(R.id.timePicker4);
        timePicker5 = findViewById(R.id.timePicker5);
        timePicker6 = findViewById(R.id.timePicker6);
        timePicker7 = findViewById(R.id.timePicker7);
        timePicker8 = findViewById(R.id.timePicker8);
        timePicker9 = findViewById(R.id.timePicker9);
        timePicker10 = findViewById(R.id.timePicker10);
        timePicker11 = findViewById(R.id.timePicker11);
        timePicker12 = findViewById(R.id.timePicker12);
        imgInjection = findViewById(R.id.imgInjection);
        timeReminder = findViewById(R.id.timeReminder);
        timeReminder2 = findViewById(R.id.timeReminder2);
        timeReminder3 = findViewById(R.id.timeReminder3);
        timeReminder4 = findViewById(R.id.timeReminder4);
        timeReminder5 = findViewById(R.id.timeReminder5);
        timeReminder6 = findViewById(R.id.timeReminder6);
        timeReminder7 = findViewById(R.id.timeReminder7);
        timeReminder8 = findViewById(R.id.timeReminder8);
        timeReminder9 = findViewById(R.id.timeReminder9);
        timeReminder10 = findViewById(R.id.timeReminder10);
        timeReminder11 = findViewById(R.id.timeReminder11);
        timeReminder12 = findViewById(R.id.timeReminder12);
        daysnum = findViewById(R.id.daysnum);

        Bundle extras = getIntent().getExtras();
        pillNam = extras.getString("pillName");
        pId = extras.getInt("pillId");


        imgCapsule.setBorderColor(getColor(R.color.colorAccent));
        imgCapsule.setBorderWidth(3);
        imgCapsule.setCircleBackgroundColor(getColor(R.color.colorAccent));
        photoId = getResources().getIdentifier("capsule",
                "drawable", getPackageName());

        imgCapsule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBorder(imgCapsule);
            }
        });

        imgTablet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBorder(imgTablet);
            }
        });

        imgInjection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBorder(imgInjection);
            }
        });

        imgLiquid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBorder(imgLiquid);
            }
        });

        rdbDayPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDaysInterval.setVisibility(View.INVISIBLE);
                DaysPickerFragment daysPicker = new DaysPickerFragment();
                daysPicker.setRdbEveryDay(rdbEveryDay);
                daysPicker.setTxtSpecificDays(txtSpecificDays);
                daysPicker.show(getSupportFragmentManager(), "DaysPickerFragment");
            }
        });

        intervalDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSpecificDays.setVisibility(View.INVISIBLE);
                DaysIntervalFragment intervalFragment = new DaysIntervalFragment();
                intervalFragment.setTxtDaysInterval(txtDaysInterval);
                intervalFragment.setRdbEveryDay(rdbEveryDay);
                intervalFragment.show(getSupportFragmentManager(), "DaysIntervalFragment");
            }
        });

        rdbContinuous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daysnum.setVisibility(View.INVISIBLE);
            }
        });
        numbOfDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaysNumberFragment dayNumbFragment = new DaysNumberFragment();
                dayNumbFragment.setDaysnum(daysnum);
                dayNumbFragment.setRdbContinuous(rdbContinuous);
                dayNumbFragment.show(getSupportFragmentManager(), "DaysNumberFragment");
            }
        });

        rdbEveryDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDaysInterval.setVisibility(View.INVISIBLE);
                txtSpecificDays.setVisibility(View.INVISIBLE);
            }
        });


        spnReminder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                showReminders(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setTitle(R.string.editt);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(UpdateMedicament.this)
                        .setTitle(R.string.quit)
                        .setMessage(R.string.quitMessage)

                        .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(main);

                            }
                        })

                        .setNegativeButton(R.string.CANCEL, null)
                        .show();
            }
        });

        scheduleDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(UpdateMedicament.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                scheduleDate.setText(year + "/" + (month + 1) + "/" + day);
                            }
                        }, year, month, dayOfMonth);
//                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        setTimePickerListener();

        setQuantityListener();

        fillFields(pId, pillNam);

        inputName.addTextChangedListener(new MyTextWatcher(inputName));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

    }

    public void setBorder(CircleImageView img) {
        switch (img.getId()) {
            case R.id.imgCapsule:
                imgCapsule.setBorderColor(getColor(R.color.colorAccent));
                imgCapsule.setBorderWidth(3);
                imgCapsule.setCircleBackgroundColor(getColor(R.color.colorAccent));
                imgTablet.setBorderWidth(0);
                imgLiquid.setBorderWidth(0);
                imgInjection.setBorderWidth(0);
                imgTablet.setCircleBackgroundColor(255);
                imgInjection.setCircleBackgroundColor(255);
                imgLiquid.setCircleBackgroundColor(255);
                photoId = getResources().getIdentifier("capsule",
                        "drawable", getPackageName());
                break;

            case R.id.imgTablet:
                imgTablet.setBorderColor(getColor(R.color.colorAccent));
                imgTablet.setBorderWidth(3);
                imgTablet.setCircleBackgroundColor(getColor(R.color.colorAccent));
                imgCapsule.setBorderWidth(0);
                imgLiquid.setBorderWidth(0);
                imgInjection.setBorderWidth(0);
                imgCapsule.setCircleBackgroundColor(255);
                imgInjection.setCircleBackgroundColor(255);
                imgLiquid.setCircleBackgroundColor(255);
                photoId = getResources().getIdentifier("tablet",
                        "drawable", getPackageName());
                break;

            case R.id.imgInjection:
                imgInjection.setBorderColor(getColor(R.color.colorAccent));
                imgInjection.setBorderWidth(3);
                imgInjection.setCircleBackgroundColor(getColor(R.color.colorAccent));
                imgTablet.setBorderWidth(0);
                imgLiquid.setBorderWidth(0);
                imgCapsule.setBorderWidth(0);
                imgTablet.setCircleBackgroundColor(255);
                imgCapsule.setCircleBackgroundColor(255);
                imgLiquid.setCircleBackgroundColor(255);
                photoId = getResources().getIdentifier("injection",
                        "drawable", getPackageName());
                break;

            case R.id.imgLiquid:
                imgLiquid.setBorderColor(getColor(R.color.colorAccent));
                imgLiquid.setBorderWidth(3);
                imgLiquid.setCircleBackgroundColor(getColor(R.color.colorAccent));
                imgTablet.setBorderWidth(0);
                imgCapsule.setBorderWidth(0);
                imgInjection.setBorderWidth(0);
                imgTablet.setCircleBackgroundColor(255);
                imgInjection.setCircleBackgroundColor(255);
                imgCapsule.setCircleBackgroundColor(255);
                photoId = getResources().getIdentifier("liquid",
                        "drawable", getPackageName());
                break;
        }
    }

    public void showReminders(int position) {
        if (position == 0) {
            timeReminder2.setVisibility(LinearLayout.GONE);
            timeReminder3.setVisibility(LinearLayout.GONE);
            timeReminder4.setVisibility(LinearLayout.GONE);
            timeReminder5.setVisibility(LinearLayout.GONE);
            timeReminder6.setVisibility(LinearLayout.GONE);
            timeReminder7.setVisibility(LinearLayout.GONE);
            timeReminder8.setVisibility(LinearLayout.GONE);
            timeReminder9.setVisibility(LinearLayout.GONE);
            timeReminder10.setVisibility(LinearLayout.GONE);
            timeReminder11.setVisibility(LinearLayout.GONE);
            timeReminder12.setVisibility(LinearLayout.GONE);
            count = 1;
        } else if (position == 1) {
            timeReminder2.setVisibility(LinearLayout.VISIBLE);
            timeReminder3.setVisibility(LinearLayout.GONE);
            timeReminder4.setVisibility(LinearLayout.GONE);
            timeReminder5.setVisibility(LinearLayout.GONE);
            timeReminder6.setVisibility(LinearLayout.GONE);
            timeReminder7.setVisibility(LinearLayout.GONE);
            timeReminder8.setVisibility(LinearLayout.GONE);
            timeReminder9.setVisibility(LinearLayout.GONE);
            timeReminder10.setVisibility(LinearLayout.GONE);
            timeReminder11.setVisibility(LinearLayout.GONE);
            timeReminder12.setVisibility(LinearLayout.GONE);
            count = 2;
        } else if (position == 2) {
            timeReminder2.setVisibility(LinearLayout.VISIBLE);
            timeReminder3.setVisibility(LinearLayout.VISIBLE);
            timeReminder4.setVisibility(LinearLayout.GONE);
            timeReminder5.setVisibility(LinearLayout.GONE);
            timeReminder6.setVisibility(LinearLayout.GONE);
            timeReminder7.setVisibility(LinearLayout.GONE);
            timeReminder8.setVisibility(LinearLayout.GONE);
            timeReminder9.setVisibility(LinearLayout.GONE);
            timeReminder10.setVisibility(LinearLayout.GONE);
            timeReminder11.setVisibility(LinearLayout.GONE);
            timeReminder12.setVisibility(LinearLayout.GONE);
            count = 3;
        } else if (position == 3) {
            timeReminder2.setVisibility(LinearLayout.VISIBLE);
            timeReminder3.setVisibility(LinearLayout.VISIBLE);
            timeReminder4.setVisibility(LinearLayout.VISIBLE);
            timeReminder5.setVisibility(LinearLayout.GONE);
            timeReminder6.setVisibility(LinearLayout.GONE);
            timeReminder7.setVisibility(LinearLayout.GONE);
            timeReminder8.setVisibility(LinearLayout.GONE);
            timeReminder9.setVisibility(LinearLayout.GONE);
            timeReminder10.setVisibility(LinearLayout.GONE);
            timeReminder11.setVisibility(LinearLayout.GONE);
            timeReminder12.setVisibility(LinearLayout.GONE);
            count = 4;
        } else if (position == 4) {
            timeReminder2.setVisibility(LinearLayout.VISIBLE);
            timeReminder3.setVisibility(LinearLayout.VISIBLE);
            timeReminder4.setVisibility(LinearLayout.VISIBLE);
            timeReminder5.setVisibility(LinearLayout.VISIBLE);
            timeReminder6.setVisibility(LinearLayout.GONE);
            timeReminder7.setVisibility(LinearLayout.GONE);
            timeReminder8.setVisibility(LinearLayout.GONE);
            timeReminder9.setVisibility(LinearLayout.GONE);
            timeReminder10.setVisibility(LinearLayout.GONE);
            timeReminder11.setVisibility(LinearLayout.GONE);
            timeReminder12.setVisibility(LinearLayout.GONE);
            count = 5;
        } else if (position == 5) {
            timeReminder2.setVisibility(LinearLayout.VISIBLE);
            timeReminder3.setVisibility(LinearLayout.VISIBLE);
            timeReminder4.setVisibility(LinearLayout.VISIBLE);
            timeReminder5.setVisibility(LinearLayout.VISIBLE);
            timeReminder6.setVisibility(LinearLayout.VISIBLE);
            timeReminder7.setVisibility(LinearLayout.GONE);
            timeReminder8.setVisibility(LinearLayout.GONE);
            timeReminder9.setVisibility(LinearLayout.GONE);
            timeReminder10.setVisibility(LinearLayout.GONE);
            timeReminder11.setVisibility(LinearLayout.GONE);
            timeReminder12.setVisibility(LinearLayout.GONE);
            count = 6;
        } else if (position == 6) {
            timeReminder2.setVisibility(LinearLayout.VISIBLE);
            timeReminder3.setVisibility(LinearLayout.VISIBLE);
            timeReminder4.setVisibility(LinearLayout.VISIBLE);
            timeReminder5.setVisibility(LinearLayout.VISIBLE);
            timeReminder6.setVisibility(LinearLayout.VISIBLE);
            timeReminder7.setVisibility(LinearLayout.VISIBLE);
            timeReminder8.setVisibility(LinearLayout.GONE);
            timeReminder9.setVisibility(LinearLayout.GONE);
            timeReminder10.setVisibility(LinearLayout.GONE);
            timeReminder11.setVisibility(LinearLayout.GONE);
            timeReminder12.setVisibility(LinearLayout.GONE);
            count = 7;
        } else if (position == 7) {
            timeReminder2.setVisibility(LinearLayout.VISIBLE);
            timeReminder3.setVisibility(LinearLayout.VISIBLE);
            timeReminder4.setVisibility(LinearLayout.VISIBLE);
            timeReminder5.setVisibility(LinearLayout.VISIBLE);
            timeReminder6.setVisibility(LinearLayout.VISIBLE);
            timeReminder7.setVisibility(LinearLayout.VISIBLE);
            timeReminder8.setVisibility(LinearLayout.VISIBLE);
            timeReminder9.setVisibility(LinearLayout.GONE);
            timeReminder10.setVisibility(LinearLayout.GONE);
            timeReminder11.setVisibility(LinearLayout.GONE);
            timeReminder12.setVisibility(LinearLayout.GONE);
            count = 8;
        } else if (position == 8) {
            timeReminder2.setVisibility(LinearLayout.VISIBLE);
            timeReminder3.setVisibility(LinearLayout.VISIBLE);
            timeReminder4.setVisibility(LinearLayout.VISIBLE);
            timeReminder5.setVisibility(LinearLayout.VISIBLE);
            timeReminder6.setVisibility(LinearLayout.VISIBLE);
            timeReminder7.setVisibility(LinearLayout.VISIBLE);
            timeReminder8.setVisibility(LinearLayout.VISIBLE);
            timeReminder9.setVisibility(LinearLayout.VISIBLE);
            timeReminder10.setVisibility(LinearLayout.GONE);
            timeReminder11.setVisibility(LinearLayout.GONE);
            timeReminder12.setVisibility(LinearLayout.GONE);
            count = 9;
        } else if (position == 9) {
            timeReminder2.setVisibility(LinearLayout.VISIBLE);
            timeReminder3.setVisibility(LinearLayout.VISIBLE);
            timeReminder4.setVisibility(LinearLayout.VISIBLE);
            timeReminder5.setVisibility(LinearLayout.VISIBLE);
            timeReminder6.setVisibility(LinearLayout.VISIBLE);
            timeReminder7.setVisibility(LinearLayout.VISIBLE);
            timeReminder8.setVisibility(LinearLayout.VISIBLE);
            timeReminder9.setVisibility(LinearLayout.VISIBLE);
            timeReminder10.setVisibility(LinearLayout.VISIBLE);
            timeReminder11.setVisibility(LinearLayout.GONE);
            timeReminder12.setVisibility(LinearLayout.GONE);
            count = 10;
        } else if (position == 10) {
            timeReminder2.setVisibility(LinearLayout.VISIBLE);
            timeReminder3.setVisibility(LinearLayout.VISIBLE);
            timeReminder4.setVisibility(LinearLayout.VISIBLE);
            timeReminder5.setVisibility(LinearLayout.VISIBLE);
            timeReminder6.setVisibility(LinearLayout.VISIBLE);
            timeReminder7.setVisibility(LinearLayout.VISIBLE);
            timeReminder8.setVisibility(LinearLayout.VISIBLE);
            timeReminder9.setVisibility(LinearLayout.VISIBLE);
            timeReminder10.setVisibility(LinearLayout.VISIBLE);
            timeReminder11.setVisibility(LinearLayout.VISIBLE);
            timeReminder12.setVisibility(LinearLayout.GONE);
            count = 11;
        } else if (position == 11) {
            timeReminder2.setVisibility(LinearLayout.VISIBLE);
            timeReminder3.setVisibility(LinearLayout.VISIBLE);
            timeReminder4.setVisibility(LinearLayout.VISIBLE);
            timeReminder5.setVisibility(LinearLayout.VISIBLE);
            timeReminder6.setVisibility(LinearLayout.VISIBLE);
            timeReminder7.setVisibility(LinearLayout.VISIBLE);
            timeReminder8.setVisibility(LinearLayout.VISIBLE);
            timeReminder9.setVisibility(LinearLayout.VISIBLE);
            timeReminder10.setVisibility(LinearLayout.VISIBLE);
            timeReminder11.setVisibility(LinearLayout.VISIBLE);
            timeReminder12.setVisibility(LinearLayout.VISIBLE);
            count = 12;
        }
    }

    private void submitForm() {
        if (!validateName()) {
            return;
        }
        try {
            db = new Database(this);
            PillRegister pillRegister = new PillRegister();

            pillRegister.setPillName(inputName.getText().toString());
            pillRegister.setPhotoId(photoId);
            Date startDate = new SimpleDateFormat("yyyy/MM/dd").parse(scheduleDate.getText().toString());
            pillRegister.setStart(startDate);
            RadioButton radioButtonDuration = findViewById(rdbGrDuration.getCheckedRadioButtonId());
            String duration;

            if (radioButtonDuration.getText().toString().equals("Continuous"))
                duration = "Continuous";
            else
                duration = daysnum.getText().toString();

            RadioButton radioButtonFrequency = findViewById(rdbGrFrequency.getCheckedRadioButtonId());
            String frequency;
            if (radioButtonFrequency.getText().toString().equals("Every day"))
                frequency = "Everyday";
            else if (radioButtonFrequency.getText().toString().equals("Specific days of week"))
                frequency = txtSpecificDays.getText().toString();
            else
                frequency = txtDaysInterval.getText().toString();

            pillRegister.setDuration(duration);
            pillRegister.setFrequency(frequency);
            pillRegister.setReminder(spnReminder.getSelectedItem().toString());
            pillRegister.setStatus("ACTIVE");

            db.updatePill(pillRegister, pId);
            db.deleteReminder(pId);

            List<ReminderRegister> reminderRegisterList = getReminders(count);
            for (int i = 0; i < reminderRegisterList.size(); i++)
                db.addReminder(reminderRegisterList.get(i), pillNam);
            db.close();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            Toast.makeText(getApplicationContext(), "Updated!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            System.err.println(e);
        }

    }

    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void showTimePickerDialog(Button timePicker) {

        DialogFragment newFragment = new TimePickerFragment();
        ((TimePickerFragment) newFragment).setTimePicker(timePicker);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public List<ReminderRegister> getReminders(int count) {
        List<ReminderRegister> reminderRegisterList = new ArrayList<>();

        if (count == 1) {
            reminderRegisterList.add(getReminderRegister(timePicker, quantity));
        }
        if (count == 2) {
            reminderRegisterList.add(getReminderRegister(timePicker, quantity));
            reminderRegisterList.add(getReminderRegister(timePicker2, quantity2));
        }
        if (count == 3) {
            reminderRegisterList.add(getReminderRegister(timePicker, quantity));
            reminderRegisterList.add(getReminderRegister(timePicker2, quantity2));
            reminderRegisterList.add(getReminderRegister(timePicker3, quantity3));
        }
        if (count == 4) {
            reminderRegisterList.add(getReminderRegister(timePicker, quantity));
            reminderRegisterList.add(getReminderRegister(timePicker2, quantity2));
            reminderRegisterList.add(getReminderRegister(timePicker3, quantity3));
            reminderRegisterList.add(getReminderRegister(timePicker4, quantity4));
        }
        if (count == 5) {
            reminderRegisterList.add(getReminderRegister(timePicker, quantity));
            reminderRegisterList.add(getReminderRegister(timePicker2, quantity2));
            reminderRegisterList.add(getReminderRegister(timePicker3, quantity3));
            reminderRegisterList.add(getReminderRegister(timePicker4, quantity4));
            reminderRegisterList.add(getReminderRegister(timePicker5, quantity5));

        }
        if (count == 6) {
            reminderRegisterList.add(getReminderRegister(timePicker, quantity));
            reminderRegisterList.add(getReminderRegister(timePicker2, quantity2));
            reminderRegisterList.add(getReminderRegister(timePicker3, quantity3));
            reminderRegisterList.add(getReminderRegister(timePicker4, quantity4));
            reminderRegisterList.add(getReminderRegister(timePicker5, quantity5));
            reminderRegisterList.add(getReminderRegister(timePicker6, quantity6));

        }
        if (count == 7) {
            reminderRegisterList.add(getReminderRegister(timePicker, quantity));
            reminderRegisterList.add(getReminderRegister(timePicker2, quantity2));
            reminderRegisterList.add(getReminderRegister(timePicker3, quantity3));
            reminderRegisterList.add(getReminderRegister(timePicker4, quantity4));
            reminderRegisterList.add(getReminderRegister(timePicker5, quantity5));
            reminderRegisterList.add(getReminderRegister(timePicker6, quantity6));
            reminderRegisterList.add(getReminderRegister(timePicker7, quantity7));

        }
        if (count == 8) {
            reminderRegisterList.add(getReminderRegister(timePicker, quantity));
            reminderRegisterList.add(getReminderRegister(timePicker2, quantity2));
            reminderRegisterList.add(getReminderRegister(timePicker3, quantity3));
            reminderRegisterList.add(getReminderRegister(timePicker4, quantity4));
            reminderRegisterList.add(getReminderRegister(timePicker5, quantity5));
            reminderRegisterList.add(getReminderRegister(timePicker6, quantity6));
            reminderRegisterList.add(getReminderRegister(timePicker7, quantity7));
            reminderRegisterList.add(getReminderRegister(timePicker8, quantity8));

        }
        if (count == 9) {
            reminderRegisterList.add(getReminderRegister(timePicker, quantity));
            reminderRegisterList.add(getReminderRegister(timePicker2, quantity2));
            reminderRegisterList.add(getReminderRegister(timePicker3, quantity3));
            reminderRegisterList.add(getReminderRegister(timePicker4, quantity4));
            reminderRegisterList.add(getReminderRegister(timePicker5, quantity5));
            reminderRegisterList.add(getReminderRegister(timePicker6, quantity6));
            reminderRegisterList.add(getReminderRegister(timePicker7, quantity7));
            reminderRegisterList.add(getReminderRegister(timePicker8, quantity8));
            reminderRegisterList.add(getReminderRegister(timePicker9, quantity9));

        }
        if (count == 10) {
            reminderRegisterList.add(getReminderRegister(timePicker, quantity));
            reminderRegisterList.add(getReminderRegister(timePicker2, quantity2));
            reminderRegisterList.add(getReminderRegister(timePicker3, quantity3));
            reminderRegisterList.add(getReminderRegister(timePicker4, quantity4));
            reminderRegisterList.add(getReminderRegister(timePicker5, quantity5));
            reminderRegisterList.add(getReminderRegister(timePicker6, quantity6));
            reminderRegisterList.add(getReminderRegister(timePicker7, quantity7));
            reminderRegisterList.add(getReminderRegister(timePicker8, quantity8));
            reminderRegisterList.add(getReminderRegister(timePicker9, quantity9));
            reminderRegisterList.add(getReminderRegister(timePicker10, quantity10));

        }
        if (count == 11) {
            reminderRegisterList.add(getReminderRegister(timePicker, quantity));
            reminderRegisterList.add(getReminderRegister(timePicker2, quantity2));
            reminderRegisterList.add(getReminderRegister(timePicker3, quantity3));
            reminderRegisterList.add(getReminderRegister(timePicker4, quantity4));
            reminderRegisterList.add(getReminderRegister(timePicker5, quantity5));
            reminderRegisterList.add(getReminderRegister(timePicker6, quantity6));
            reminderRegisterList.add(getReminderRegister(timePicker7, quantity7));
            reminderRegisterList.add(getReminderRegister(timePicker8, quantity8));
            reminderRegisterList.add(getReminderRegister(timePicker9, quantity9));
            reminderRegisterList.add(getReminderRegister(timePicker10, quantity10));
            reminderRegisterList.add(getReminderRegister(timePicker11, quantity11));

        }
        if (count == 12) {
            reminderRegisterList.add(getReminderRegister(timePicker, quantity));
            reminderRegisterList.add(getReminderRegister(timePicker2, quantity2));
            reminderRegisterList.add(getReminderRegister(timePicker3, quantity3));
            reminderRegisterList.add(getReminderRegister(timePicker4, quantity4));
            reminderRegisterList.add(getReminderRegister(timePicker5, quantity5));
            reminderRegisterList.add(getReminderRegister(timePicker6, quantity6));
            reminderRegisterList.add(getReminderRegister(timePicker7, quantity7));
            reminderRegisterList.add(getReminderRegister(timePicker8, quantity8));
            reminderRegisterList.add(getReminderRegister(timePicker9, quantity9));
            reminderRegisterList.add(getReminderRegister(timePicker10, quantity10));
            reminderRegisterList.add(getReminderRegister(timePicker11, quantity11));
            reminderRegisterList.add(getReminderRegister(timePicker12, quantity12));

        }
        return reminderRegisterList;
    }

    public ReminderRegister getReminderRegister(Button timePicker, Button quantity) {
        ReminderRegister reminderRegister = new ReminderRegister();
        Integer hour = Integer.parseInt(timePicker.getText().toString().split(":")[0]);
        Integer minutes = Integer.parseInt(timePicker.getText().toString().split(":")[1]);
        reminderRegister.setHour(hour);
        reminderRegister.setMinutes(minutes);
        reminderRegister.setQuantity(quantity.getText().toString().split(" ")[1]);

        return reminderRegister;
    }

    public void setTimePickerListener() {
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(timePicker);
            }
        });
        timePicker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(timePicker2);
            }
        });
        timePicker3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(timePicker3);
            }
        });
        timePicker4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(timePicker4);
            }
        });
        timePicker5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(timePicker5);
            }
        });
        timePicker6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(timePicker6);
            }
        });
        timePicker7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(timePicker7);
            }
        });
        timePicker8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(timePicker8);
            }
        });
        timePicker9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(timePicker9);
            }
        });
        timePicker10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(timePicker10);
            }
        });
        timePicker11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(timePicker11);
            }
        });
        timePicker12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(timePicker12);
            }
        });

    }

    public void setQuantityListener() {
        quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuantityFragment dialogQuantity = new QuantityFragment();
                Bundle data = new Bundle();
                dialogQuantity.setTakeQuantity(quantity);
                data.putString("quantity", quantity.getText().toString().split(" ")[1]);
                dialogQuantity.setArguments(data);
                dialogQuantity.show(getSupportFragmentManager(), "QuantityFragment");
            }
        });

        quantity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuantityFragment dialogQuantity = new QuantityFragment();
                Bundle data = new Bundle();
                dialogQuantity.setTakeQuantity(quantity2);
                data.putString("quantity", quantity2.getText().toString().split(" ")[1]);
                dialogQuantity.setArguments(data);
                dialogQuantity.show(getSupportFragmentManager(), "QuantityFragment");
            }
        });
        quantity3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuantityFragment dialogQuantity = new QuantityFragment();
                Bundle data = new Bundle();
                dialogQuantity.setTakeQuantity(quantity3);
                data.putString("quantity", quantity3.getText().toString().split(" ")[1]);
                dialogQuantity.setArguments(data);
                dialogQuantity.show(getSupportFragmentManager(), "QuantityFragment");
            }
        });
        quantity4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuantityFragment dialogQuantity = new QuantityFragment();
                Bundle data = new Bundle();
                dialogQuantity.setTakeQuantity(quantity4);
                data.putString("quantity", quantity4.getText().toString().split(" ")[1]);
                dialogQuantity.setArguments(data);
                dialogQuantity.show(getSupportFragmentManager(), "QuantityFragment");
            }
        });
        quantity5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuantityFragment dialogQuantity = new QuantityFragment();
                Bundle data = new Bundle();
                dialogQuantity.setTakeQuantity(quantity5);
                data.putString("quantity", quantity5.getText().toString().split(" ")[1]);
                dialogQuantity.setArguments(data);
                dialogQuantity.show(getSupportFragmentManager(), "QuantityFragment");
            }
        });
        quantity6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuantityFragment dialogQuantity = new QuantityFragment();
                Bundle data = new Bundle();
                dialogQuantity.setTakeQuantity(quantity6);
                data.putString("quantity", quantity6.getText().toString().split(" ")[1]);
                dialogQuantity.setArguments(data);
                dialogQuantity.show(getSupportFragmentManager(), "QuantityFragment");
            }
        });

        quantity7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuantityFragment dialogQuantity = new QuantityFragment();
                Bundle data = new Bundle();
                dialogQuantity.setTakeQuantity(quantity7);
                data.putString("quantity", quantity7.getText().toString().split(" ")[1]);
                dialogQuantity.setArguments(data);
                dialogQuantity.show(getSupportFragmentManager(), "QuantityFragment");
            }
        });
        quantity8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuantityFragment dialogQuantity = new QuantityFragment();
                Bundle data = new Bundle();
                dialogQuantity.setTakeQuantity(quantity8);
                data.putString("quantity", quantity8.getText().toString().split(" ")[1]);
                dialogQuantity.setArguments(data);
                dialogQuantity.show(getSupportFragmentManager(), "QuantityFragment");
            }
        });
        quantity9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuantityFragment dialogQuantity = new QuantityFragment();
                Bundle data = new Bundle();
                dialogQuantity.setTakeQuantity(quantity9);
                data.putString("quantity", quantity9.getText().toString().split(" ")[1]);
                dialogQuantity.setArguments(data);
                dialogQuantity.show(getSupportFragmentManager(), "QuantityFragment");
            }
        });
        quantity10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuantityFragment dialogQuantity = new QuantityFragment();
                Bundle data = new Bundle();
                dialogQuantity.setTakeQuantity(quantity10);
                data.putString("quantity", quantity10.getText().toString().split(" ")[1]);
                dialogQuantity.setArguments(data);
                dialogQuantity.show(getSupportFragmentManager(), "QuantityFragment");
            }
        });
        quantity11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuantityFragment dialogQuantity = new QuantityFragment();
                Bundle data = new Bundle();
                dialogQuantity.setTakeQuantity(quantity11);
                data.putString("quantity", quantity11.getText().toString().split(" ")[1]);
                dialogQuantity.setArguments(data);
                dialogQuantity.show(getSupportFragmentManager(), "QuantityFragment");
            }
        });
        quantity12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuantityFragment dialogQuantity = new QuantityFragment();
                Bundle data = new Bundle();
                dialogQuantity.setTakeQuantity(quantity12);
                data.putString("quantity", quantity12.getText().toString().split(" ")[1]);
                dialogQuantity.setArguments(data);
                dialogQuantity.show(getSupportFragmentManager(), "QuantityFragment");
            }
        });

    }

    public void fillFields(Integer id, String name) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            db = new Database(this);
            PillDataResult pill;
            pill = db.getPillByName(name);
            inputName.setText(pill.getPillName());
            Integer imageId = pill.getPhotoId();

            scheduleDate.setText(dateFormat.format(pill.getStart()));


            if (imageId == getResources().getIdentifier("capsule",
                    "drawable", getPackageName())) {
                setBorder(imgCapsule);
                photoId = imageId;
            } else if (imageId == getResources().getIdentifier("liquid",
                    "drawable", getPackageName())) {
                photoId = imageId;
                setBorder(imgLiquid);
            } else if (imageId == getResources().getIdentifier("tablet",
                    "drawable", getPackageName())) {
                setBorder(imgTablet);
                photoId = imageId;
            } else if (imageId == getResources().getIdentifier("injection",
                    "drawable", getPackageName())) {
                photoId = imageId;
                setBorder(imgInjection);
            }

            if (pill.getDuration().equals("Continuous"))
                rdbContinuous.setChecked(true);
            else {
                numbOfDay.setChecked(true);
                daysnum.setVisibility(View.VISIBLE);
                daysnum.setText(pill.getDuration());
            }

            String frequencyPill = pill.getFrequency();
            if (frequencyPill.equals("Everyday")) {
                rdbEveryDay.setChecked(true);
            } else if (frequencyPill.split(" ")[0].equals("every")) {
                intervalDays.setChecked(true);
                txtDaysInterval.setText(frequencyPill);
                txtDaysInterval.setVisibility(View.VISIBLE);
            } else {
                txtSpecificDays.setText(frequencyPill);
                txtSpecificDays.setVisibility(View.VISIBLE);
                rdbDayPicker.setChecked(true);

            }
            List<ReminderDataResult> reminderDataResultList = db.getReminders(id);
            for (int i = 0; i < spnReminder.getCount(); i++) {
                if (spnReminder.getItemAtPosition(i).toString().equalsIgnoreCase(pill.getReminderTimes())) {
                    spnReminder.setSelection(i);
                    setReminder(reminderDataResultList, i);
                }
            }



        } catch (Exception e) {
            System.err.println(e);
        }

    }

    public void setReminder(List<ReminderDataResult> reminder, int i) {
        if (i == 0) {
            timePicker.setText(reminder.get(0).getHour() + ":" + reminder.get(0).getMinutes());
            quantity.setText("Take " + reminder.get(0).getQuantity());
            reminderIdList.add(reminder.get(0).getId());
        } else if (i == 1) {
            timePicker.setText(reminder.get(0).getHour() + ":" + reminder.get(0).getMinutes());
            quantity.setText("Take " + reminder.get(0).getQuantity());
            timePicker2.setText(reminder.get(1).getHour() + ":" + reminder.get(1).getMinutes());
            quantity2.setText("Take " + reminder.get(1).getQuantity());

        } else if (i == 2) {
            timePicker.setText(reminder.get(0).getHour() + ":" + reminder.get(0).getMinutes());
            quantity.setText("Take " + reminder.get(0).getQuantity());
            timePicker2.setText(reminder.get(1).getHour() + ":" + reminder.get(1).getMinutes());
            quantity2.setText("Take " + reminder.get(1).getQuantity());
            timePicker3.setText(reminder.get(2).getHour() + ":" + reminder.get(2).getMinutes());
            quantity3.setText("Take " + reminder.get(2).getQuantity());
        } else if (i == 3) {
            timePicker.setText(reminder.get(0).getHour() + ":" + reminder.get(0).getMinutes());
            quantity.setText("Take " + reminder.get(0).getQuantity());
            timePicker2.setText(reminder.get(1).getHour() + ":" + reminder.get(1).getMinutes());
            quantity2.setText("Take " + reminder.get(1).getQuantity());
            timePicker3.setText(reminder.get(2).getHour() + ":" + reminder.get(2).getMinutes());
            quantity3.setText("Take " + reminder.get(2).getQuantity());
            timePicker4.setText(reminder.get(3).getHour() + ":" + reminder.get(3).getMinutes());
            quantity4.setText("Take " + reminder.get(3).getQuantity());
        } else if (i == 4) {
            timePicker.setText(reminder.get(0).getHour() + ":" + reminder.get(0).getMinutes());
            quantity.setText("Take " + reminder.get(0).getQuantity());
            timePicker2.setText(reminder.get(1).getHour() + ":" + reminder.get(1).getMinutes());
            quantity2.setText("Take " + reminder.get(1).getQuantity());
            timePicker3.setText(reminder.get(2).getHour() + ":" + reminder.get(2).getMinutes());
            quantity3.setText("Take " + reminder.get(2).getQuantity());
            timePicker4.setText(reminder.get(3).getHour() + ":" + reminder.get(3).getMinutes());
            quantity4.setText("Take " + reminder.get(3).getQuantity());
            timePicker5.setText(reminder.get(4).getHour() + ":" + reminder.get(4).getMinutes());
            quantity5.setText("Take " + reminder.get(4).getQuantity());
        } else if (i == 5) {
            timePicker.setText(reminder.get(0).getHour() + ":" + reminder.get(0).getMinutes());
            quantity.setText("Take " + reminder.get(0).getQuantity());
            timePicker2.setText(reminder.get(1).getHour() + ":" + reminder.get(1).getMinutes());
            quantity2.setText("Take " + reminder.get(1).getQuantity());
            timePicker3.setText(reminder.get(2).getHour() + ":" + reminder.get(2).getMinutes());
            quantity3.setText("Take " + reminder.get(2).getQuantity());
            timePicker4.setText(reminder.get(3).getHour() + ":" + reminder.get(3).getMinutes());
            quantity4.setText("Take " + reminder.get(3).getQuantity());
            timePicker5.setText(reminder.get(4).getHour() + ":" + reminder.get(4).getMinutes());
            quantity5.setText("Take " + reminder.get(4).getQuantity());
            timePicker6.setText(reminder.get(5).getHour() + ":" + reminder.get(5).getMinutes());
            quantity6.setText("Take " + reminder.get(5).getQuantity());
        } else if (i == 6) {
            timePicker.setText(reminder.get(0).getHour() + ":" + reminder.get(0).getMinutes());
            quantity.setText("Take " + reminder.get(0).getQuantity());
            timePicker2.setText(reminder.get(1).getHour() + ":" + reminder.get(1).getMinutes());
            quantity2.setText("Take " + reminder.get(1).getQuantity());
            timePicker3.setText(reminder.get(2).getHour() + ":" + reminder.get(2).getMinutes());
            quantity3.setText("Take " + reminder.get(2).getQuantity());
            timePicker4.setText(reminder.get(3).getHour() + ":" + reminder.get(3).getMinutes());
            quantity4.setText("Take " + reminder.get(3).getQuantity());
            timePicker5.setText(reminder.get(4).getHour() + ":" + reminder.get(4).getMinutes());
            quantity5.setText("Take " + reminder.get(4).getQuantity());
            timePicker6.setText(reminder.get(5).getHour() + ":" + reminder.get(5).getMinutes());
            quantity6.setText("Take " + reminder.get(5).getQuantity());
            timePicker7.setText(reminder.get(6).getHour() + ":" + reminder.get(6).getMinutes());
            quantity7.setText("Take " + reminder.get(6).getQuantity());
        } else if (i == 7) {
            timePicker.setText(reminder.get(0).getHour() + ":" + reminder.get(0).getMinutes());
            quantity.setText("Take " + reminder.get(0).getQuantity());
            timePicker2.setText(reminder.get(1).getHour() + ":" + reminder.get(1).getMinutes());
            quantity2.setText("Take " + reminder.get(1).getQuantity());
            timePicker3.setText(reminder.get(2).getHour() + ":" + reminder.get(2).getMinutes());
            quantity3.setText("Take " + reminder.get(2).getQuantity());
            timePicker4.setText(reminder.get(3).getHour() + ":" + reminder.get(3).getMinutes());
            quantity4.setText("Take " + reminder.get(3).getQuantity());
            timePicker5.setText(reminder.get(4).getHour() + ":" + reminder.get(4).getMinutes());
            quantity5.setText("Take " + reminder.get(4).getQuantity());
            timePicker6.setText(reminder.get(5).getHour() + ":" + reminder.get(5).getMinutes());
            quantity6.setText("Take " + reminder.get(5).getQuantity());
            timePicker7.setText(reminder.get(6).getHour() + ":" + reminder.get(6).getMinutes());
            quantity7.setText("Take " + reminder.get(6).getQuantity());
            timePicker8.setText(reminder.get(7).getHour() + ":" + reminder.get(7).getMinutes());
            quantity8.setText("Take " + reminder.get(7).getQuantity());
        } else if (i == 8) {
            timePicker.setText(reminder.get(0).getHour() + ":" + reminder.get(0).getMinutes());
            quantity.setText("Take " + reminder.get(0).getQuantity());
            timePicker2.setText(reminder.get(1).getHour() + ":" + reminder.get(1).getMinutes());
            quantity2.setText("Take " + reminder.get(1).getQuantity());
            timePicker3.setText(reminder.get(2).getHour() + ":" + reminder.get(2).getMinutes());
            quantity3.setText("Take " + reminder.get(2).getQuantity());
            timePicker4.setText(reminder.get(3).getHour() + ":" + reminder.get(3).getMinutes());
            quantity4.setText("Take " + reminder.get(3).getQuantity());
            timePicker5.setText(reminder.get(4).getHour() + ":" + reminder.get(4).getMinutes());
            quantity5.setText("Take " + reminder.get(4).getQuantity());
            timePicker6.setText(reminder.get(5).getHour() + ":" + reminder.get(5).getMinutes());
            quantity6.setText("Take " + reminder.get(5).getQuantity());
            timePicker7.setText(reminder.get(6).getHour() + ":" + reminder.get(6).getMinutes());
            quantity7.setText("Take " + reminder.get(6).getQuantity());
            timePicker8.setText(reminder.get(7).getHour() + ":" + reminder.get(7).getMinutes());
            quantity8.setText("Take " + reminder.get(7).getQuantity());
            timePicker9.setText(reminder.get(8).getHour() + ":" + reminder.get(8).getMinutes());
            quantity9.setText("Take " + reminder.get(8).getQuantity());
        } else if (i == 9) {
            timePicker.setText(reminder.get(0).getHour() + ":" + reminder.get(0).getMinutes());
            quantity.setText("Take " + reminder.get(0).getQuantity());
            timePicker2.setText(reminder.get(1).getHour() + ":" + reminder.get(1).getMinutes());
            quantity2.setText("Take " + reminder.get(1).getQuantity());
            timePicker3.setText(reminder.get(2).getHour() + ":" + reminder.get(2).getMinutes());
            quantity3.setText("Take " + reminder.get(2).getQuantity());
            timePicker4.setText(reminder.get(3).getHour() + ":" + reminder.get(3).getMinutes());
            quantity4.setText("Take " + reminder.get(3).getQuantity());
            timePicker5.setText(reminder.get(4).getHour() + ":" + reminder.get(4).getMinutes());
            quantity5.setText("Take " + reminder.get(4).getQuantity());
            timePicker6.setText(reminder.get(5).getHour() + ":" + reminder.get(5).getMinutes());
            quantity6.setText("Take " + reminder.get(5).getQuantity());
            timePicker7.setText(reminder.get(6).getHour() + ":" + reminder.get(6).getMinutes());
            quantity7.setText("Take " + reminder.get(6).getQuantity());
            timePicker8.setText(reminder.get(7).getHour() + ":" + reminder.get(7).getMinutes());
            quantity8.setText("Take " + reminder.get(7).getQuantity());
            timePicker9.setText(reminder.get(8).getHour() + ":" + reminder.get(8).getMinutes());
            quantity9.setText("Take " + reminder.get(8).getQuantity());
            timePicker10.setText(reminder.get(9).getHour() + ":" + reminder.get(9).getMinutes());
            quantity10.setText("Take " + reminder.get(9).getQuantity());
        } else if (i == 10) {
            timePicker.setText(reminder.get(0).getHour() + ":" + reminder.get(0).getMinutes());
            quantity.setText("Take " + reminder.get(0).getQuantity());
            timePicker2.setText(reminder.get(1).getHour() + ":" + reminder.get(1).getMinutes());
            quantity2.setText("Take " + reminder.get(1).getQuantity());
            timePicker3.setText(reminder.get(2).getHour() + ":" + reminder.get(2).getMinutes());
            quantity3.setText("Take " + reminder.get(2).getQuantity());
            timePicker4.setText(reminder.get(3).getHour() + ":" + reminder.get(3).getMinutes());
            quantity4.setText("Take " + reminder.get(3).getQuantity());
            timePicker5.setText(reminder.get(4).getHour() + ":" + reminder.get(4).getMinutes());
            quantity5.setText("Take " + reminder.get(4).getQuantity());
            timePicker6.setText(reminder.get(5).getHour() + ":" + reminder.get(5).getMinutes());
            quantity6.setText("Take " + reminder.get(5).getQuantity());
            timePicker7.setText(reminder.get(6).getHour() + ":" + reminder.get(6).getMinutes());
            quantity7.setText("Take " + reminder.get(6).getQuantity());
            timePicker8.setText(reminder.get(7).getHour() + ":" + reminder.get(7).getMinutes());
            quantity8.setText("Take " + reminder.get(7).getQuantity());
            timePicker9.setText(reminder.get(8).getHour() + ":" + reminder.get(8).getMinutes());
            quantity9.setText("Take " + reminder.get(8).getQuantity());
            timePicker10.setText(reminder.get(9).getHour() + ":" + reminder.get(9).getMinutes());
            quantity10.setText("Take " + reminder.get(9).getQuantity());
            timePicker11.setText(reminder.get(10).getHour() + ":" + reminder.get(10).getMinutes());
            quantity11.setText("Take " + reminder.get(10).getQuantity());
        } else if (i == 11) {
            timePicker.setText(reminder.get(0).getHour() + ":" + reminder.get(0).getMinutes());
            quantity.setText("Take " + reminder.get(0).getQuantity());
            timePicker2.setText(reminder.get(1).getHour() + ":" + reminder.get(1).getMinutes());
            quantity2.setText("Take " + reminder.get(1).getQuantity());
            timePicker3.setText(reminder.get(2).getHour() + ":" + reminder.get(2).getMinutes());
            quantity3.setText("Take " + reminder.get(2).getQuantity());
            timePicker4.setText(reminder.get(3).getHour() + ":" + reminder.get(3).getMinutes());
            quantity4.setText("Take " + reminder.get(3).getQuantity());
            timePicker5.setText(reminder.get(4).getHour() + ":" + reminder.get(4).getMinutes());
            quantity5.setText("Take " + reminder.get(4).getQuantity());
            timePicker6.setText(reminder.get(5).getHour() + ":" + reminder.get(5).getMinutes());
            quantity6.setText("Take " + reminder.get(5).getQuantity());
            timePicker7.setText(reminder.get(6).getHour() + ":" + reminder.get(6).getMinutes());
            quantity7.setText("Take " + reminder.get(6).getQuantity());
            timePicker8.setText(reminder.get(7).getHour() + ":" + reminder.get(7).getMinutes());
            quantity8.setText("Take " + reminder.get(7).getQuantity());
            timePicker9.setText(reminder.get(8).getHour() + ":" + reminder.get(8).getMinutes());
            quantity9.setText("Take " + reminder.get(8).getQuantity());
            timePicker10.setText(reminder.get(9).getHour() + ":" + reminder.get(9).getMinutes());
            quantity10.setText("Take " + reminder.get(9).getQuantity());
            timePicker11.setText(reminder.get(10).getHour() + ":" + reminder.get(10).getMinutes());
            quantity11.setText("Take " + reminder.get(10).getQuantity());
            timePicker12.setText(reminder.get(11).getHour() + ":" + reminder.get(11).getMinutes());
            quantity12.setText("Take " + reminder.get(11).getQuantity());
        }

    }


    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_name:
                    validateName();
                    break;

            }
        }
    }
}
