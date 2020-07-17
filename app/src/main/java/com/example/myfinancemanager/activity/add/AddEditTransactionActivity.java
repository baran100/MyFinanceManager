package com.example.myfinancemanager.activity.add;

import androidx.appcompat.widget.Toolbar;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.myfinancemanager.BaseActivity;
import com.example.myfinancemanager.R;
import com.example.myfinancemanager.activity.viewModel.AddEditViewModel;
import com.example.myfinancemanager.activity.viewModel.BaseViewModel;
import com.sardari.daterangepicker.customviews.DateRangeCalendarView;
import com.sardari.daterangepicker.dialog.DatePickerDialog;
import com.sardari.daterangepicker.utils.PersianCalendar;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import ir.esfandune.calculatorlibe.CalculatorDialog;


public class AddEditTransactionActivity extends BaseActivity {

    EditText etTransactionAmount;
    ImageView imgCalculator;
    TextView tvTransactionDate,tvTransactionTime;
    PersianCalendar calendar;


    @Override
    protected Class<? extends BaseViewModel> getViewModelClass() {
        return AddEditViewModel.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_add_edit_transaction);

        Toolbar toolbar = findViewById(R.id.toolbar_add);
        if (getSupportActionBar() == null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        etTransactionAmount = findViewById(R.id.etTransactionAmount);
        tvTransactionDate = findViewById(R.id.tvTransactionDate);
        tvTransactionTime = findViewById(R.id.tvTransactionTime);

        imgCalculator = findViewById(R.id.imgCalculator);
        imgCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalculator(imgCalculator);
            }
        });

        calendar = new PersianCalendar();
        String date = calendar.getPersianDay()+" "+ calendar.getPersianMonthName()+" "+ calendar.getPersianYear();
        tvTransactionDate.setText(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        tvTransactionTime.setText(currentTime);
        tvTransactionTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddEditTransactionActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        tvTransactionTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        tvTransactionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddEditTransactionActivity.this);
                datePickerDialog.setSelectionMode(DateRangeCalendarView.SelectionMode.Single);
                //datePickerDialog.setEnableTimePicker(true);
                datePickerDialog.setShowGregorianDate(true);
                datePickerDialog.setTextSizeTitle(10.0f);
                datePickerDialog.setTextSizeWeek(12.0f);
                datePickerDialog.setTextSizeDate(14.0f);
                datePickerDialog.setCanceledOnTouchOutside(true);
                datePickerDialog.setOnSingleDateSelectedListener(new DatePickerDialog.OnSingleDateSelectedListener() {
                    @Override
                    public void onSingleDateSelected(PersianCalendar date) {
                        tvTransactionDate.setText(date.getPersianShortDate());
                        tvTransactionTime.setText(date.getPersianLongDateAndTime());

                    }
                });

                datePickerDialog.showDialog();
            }
        });


        addFab(R.layout.fab_don, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddEditTransactionActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard,menu);
        MenuItem menuItemEdit = menu.findItem(R.id.menu_home_tip);
        menuItemEdit.setVisible(false);
        return true;
    }

    public void showCalculator(View view) {
        int value = 0;
        try {
            value = Integer.parseInt(etTransactionAmount.getText().toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        new CalculatorDialog(this) {
            @Override
            public void onResult(String result) {

                NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
                double number = 0;
                try {
                    number =nf.parse(result).doubleValue();
                    etTransactionAmount.setText(number+ "");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }.setValue(value).showDIalog();
    }

}