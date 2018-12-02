package com.seawolf.simplenotification;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    private int mYear, mMonth, mDay, mHour, mMinute;
    private CheckBox recurringCheckbox;
    private EditText estimatedTimeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button setDateButton = findViewById(R.id.mainSetDateButton);
        Button setHourButton = findViewById(R.id.mainSetHourButton);
        recurringCheckbox = findViewById(R.id.mainRecurringCheckBox);
        Button addToCalendar = findViewById(R.id.mainAddToCalendarButton);
        estimatedTimeText = findViewById(R.id.mainEstimatedText);

        setDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDate();
            }
        });

        setHourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickTime();
            }
        });

        addToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCalendar();
            }
        });


    }

    void pickDate(){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    void pickTime(){
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    void addToCalendar(){
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.Events.TITLE, "Your title");
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "Your description");

        GregorianCalendar calDate = new GregorianCalendar(mYear, mMonth, mDay,mHour,mMinute);
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                calDate.getTimeInMillis());
        long estimatedTimeInMs = Integer.parseInt(estimatedTimeText.getText().toString())*60000;
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                calDate.getTimeInMillis()+estimatedTimeInMs);
        if(recurringCheckbox.isChecked()){
            //TODO: Get day of the week, add it to string, pass it as argument
            //intent.putExtra(CalendarContract.Events.RRULE, "FREQ=WEEKLY;COUNT=11;WKST=SU;BYDAY=TU,TH");
        }

        intent.putExtra(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE);
        startActivity(intent);
    }
}