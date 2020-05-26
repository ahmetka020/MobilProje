package com.example.mobilproje;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.PopupWindow;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity {

    private Button startAlarmBtn;
    private Button settingsBtn;
    private TimePickerDialog timePickerDialog;
    final static int REQUEST_CODE = 1;
    private Button ActivityListButton;
    String alarmDate;
    int pDay;
    int pMonth;
    int pYear;
    PopupWindow popUp;
    ArrayList<Calendar> calendars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        final SharedPreferences pref = this.getSharedPreferences("pref",Context.MODE_PRIVATE);
//        int themeName = pref.getInt("ThemeKey",1);
//        if (themeName == 1){
//            setTheme(R.style.ThemeOverlay_AppCompat_Dark_ActionBar);
//        }
//        else{
//            setTheme(R.style.Theme_AppCompat_Light);
//        }

        setContentView(R.layout.activity_main);
        final CalendarView calendarView = findViewById(R.id.calendarView);

        ActivityListButton = findViewById(R.id.activtyListButton);
        ActivityListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent myIntent = new Intent(v.getContext(), EventsActivity.class);
                    startActivityForResult(myIntent, 0);
            }
        });


        popUp = new PopupWindow(this);
        calendars = new ArrayList<>();
        startAlarmBtn = (Button)findViewById(R.id.activityAddButton);
        startAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPickerDialog(false);
            }
        });

        settingsBtn = findViewById(R.id.settingsButton);
        settingsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), SettingsActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView arg0, int year, int month,
                                            int date) {
                alarmDate = date + "." + month +  "." + year;
                pDay = date;
                pMonth = month;
                pYear = year;

            }
        });






    }

    private void openPickerDialog(boolean is24hour) {

        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(
                MainActivity.this,
                onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                is24hour);
        timePickerDialog.setTitle("Set Alarm");

        timePickerDialog.show();
    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener
            = new TimePickerDialog.OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();
            //calSet.set(Calendar.DAY);
            calSet.set(Calendar.YEAR,pYear);
            calSet.set(Calendar.MONTH,pMonth);
            calSet.set(Calendar.DAY_OF_MONTH,pDay);
            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if(calSet.compareTo(calNow) <= 0){

                calSet.add(Calendar.DATE, 1);
            }
            calendars.add(calSet);
            setAlarm(calSet);
        }};

    private void setAlarm(Calendar alarmCalender){
        Toast.makeText(getApplicationContext(),"Alarm Set!",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), REQUEST_CODE, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmCalender.getTimeInMillis(), pendingIntent);
        writeCalendar(alarmCalender);
    }

    private void writeCalendar(Calendar calendar){
        try{
            FileOutputStream fileOutputStream = openFileOutput("Calendars.txt",MODE_APPEND);
            //FileOutputStream fileOutputStream=new FileOutputStream(new File("Calendars.txt"),true);
            byte[] byteArr = calendar.getTime().toString().getBytes();
            String newLine = "\n";
            fileOutputStream.write(newLine.getBytes());
            fileOutputStream.write(byteArr);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}