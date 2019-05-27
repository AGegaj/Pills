package org.unipr.pills;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
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
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import database.Database;
import de.hdodenhof.circleimageview.CircleImageView;
import fragment.DaysIntervalFragment;
import fragment.DaysNumberFragment;
import fragment.DaysPickerFragment;
import fragment.QuantityFragment;
import fragment.TimePickerFragment;
import model.PillRegister;

public class AddMedicament extends AppCompatActivity {

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
    private RadioButton numbOfDay;
    private RadioButton intervalDays;
    private RadioButton rdbDayPicker;
    private RadioGroup rdbGrDuration, rdbGrFrequency;
    private LinearLayout timeReminder, timeReminder2, timeReminder3, timeReminder4, timeReminder5;
    private LinearLayout timeReminder6, timeReminder7, timeReminder8, timeReminder9, timeReminder10;
    private LinearLayout timeReminder11, timeReminder12;
    private Database db;

    DatePickerDialog datePickerDialog;
    Integer photoId;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    private CircleImageView imgCapsule, imgTablet, imgLiquid, imgInjection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicament);
        inputName = findViewById(R.id.input_name);
        inputLayoutName = findViewById(R.id.input_layout_name);
        btnAdd = findViewById(R.id.btn_addMed);
        toolbar = findViewById(R.id.toolbarAdd);
        spnReminder = findViewById(R.id.spinner_reminder);
        scheduleDate = findViewById(R.id.scheduleDate);
        numbOfDay = findViewById(R.id.numberOfDays);
        intervalDays = findViewById(R.id.daysInterval);
        rdbDayPicker = findViewById(R.id.specificDays);
        rdbGrDuration = findViewById(R.id.rdbGrDuration);
        rdbGrFrequency = findViewById(R.id.rdbGrFrequency);
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


        imgCapsule.setBorderColor(getColor(R.color.colorAccent));
        imgCapsule.setBorderWidth(3);
        imgCapsule.setCircleBackgroundColor(getColor(R.color.colorAccent));
        photoId = imgCapsule.getId();

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
                DaysPickerFragment daysPicker = new DaysPickerFragment();
                daysPicker.show(getSupportFragmentManager(), "DaysPickerFragment");
            }
        });

        intervalDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaysIntervalFragment intervalFragment = new DaysIntervalFragment();
                intervalFragment.show(getSupportFragmentManager(), "DaysIntervalFragment");
            }
        });

        numbOfDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaysNumberFragment dayNumbFragment = new DaysNumberFragment();
                dayNumbFragment.show(getSupportFragmentManager(), "DaysNumberFragment");
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
        toolbar.setTitle("Add Medicament");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main);
            }
        });

        scheduleDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(AddMedicament.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                scheduleDate.setText(year + "/" + month + "/" + day);
                            }
                        }, year, month, dayOfMonth);
//                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

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
                photoId = img.getId();
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
                photoId = img.getId();
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
                photoId = img.getId();
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
                photoId = img.getId();
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
        }
    }

    private void submitForm() {
        if (!validateName()) {
            return;
        }

        db = new Database(this);

        String pillName = inputName.getText().toString();
        String start = scheduleDate.getText().toString();
        int selectedId = rdbGrDuration.getCheckedRadioButtonId();
        RadioButton radioButtonDuration = findViewById(selectedId);
        String duration;

        if (radioButtonDuration.getText().toString().equals("Continuous"))
            duration = "Continuous";
        else
            duration = "1";

        String textReminder = spnReminder.getSelectedItem().toString();
        System.err.println(pillName);
        System.err.println(start);
        System.err.println(radioButtonDuration.getText().toString());
        System.err.println(textReminder);
        System.err.println(duration);
        System.err.println(photoId);
        String frequency;
        String status = "ACTIVE";


        Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_SHORT).show();
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
