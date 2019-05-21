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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import fragment.DaysIntervalFragment;
import fragment.DaysNumberFragment;
import fragment.DaysPickerFragment;
import fragment.QuantityFragment;
import fragment.TimePickerFragment;

public class AddMedicament extends AppCompatActivity implements QuantityFragment.OnInputListener{

    private Toolbar toolbar;
    private EditText inputName;
    private TextInputLayout inputLayoutName;
    private Button btnAdd;
    private Spinner spnReminder;
    public static Button timePicker;
    private TextView scheduleDate;
    private Button quantity;
    private RadioButton numbOfDay;
    private RadioButton intervalDays;
    private RadioButton rdbDayPicker;

    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;

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
        timePicker = findViewById(R.id.timePicker);
        quantity = findViewById(R.id.quantity);
        numbOfDay = findViewById(R.id.numberOfDays);
        intervalDays = findViewById(R.id.daysInterval);
        rdbDayPicker = findViewById(R.id.specificDays);

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

        quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuantityFragment dialogQuantity = new QuantityFragment();
                Bundle data = new Bundle();
                data.putString("quantity", quantity.getText().toString().split(" ")[1]);
                dialogQuantity.setArguments(data);
                dialogQuantity.show(getSupportFragmentManager(), "QuantityFragment");
            }
        });


        spnReminder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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
                                scheduleDate.setText(year+"/"+month+"/"+day);
                            }
                        }, year, month, dayOfMonth);
//                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
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


    private void submitForm() {
        if (!validateName()) {
            return;
        }

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

    public void showTimePickerDialog(View view) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void sendInput(String input) {
        String mInput = input;

        quantity.setText(mInput);

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
