package com.example.mobilproje;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends Activity {
    Button saveBtn;
    Button changeBtn;
    Switch switchBtn;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences pref = this.getPreferences(Context.MODE_PRIVATE);
        int themeName = pref.getInt("ThemeKey",1);
        if (themeName == 1){
            setTheme(R.style.ThemeOverlay_AppCompat_Dark_ActionBar);
        }
        else{
            setTheme(R.style.Theme_AppCompat_Light);
        }
        setContentView(R.layout.activity_settings);
        changeBtn = findViewById(R.id.changeRingtone);
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
//                Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(),uri);
//                ringtone.play();
                Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                if (alarmUri == null)
                {
                    alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                }
                RingtoneManager.setActualDefaultRingtoneUri(getApplicationContext(),1,alarmUri);
                Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), alarmUri);
                ringtone.play();
            }
        });

        switchBtn = findViewById(R.id.modeSwitch);
        if (themeName == 0){
            switchBtn.setChecked(true);
        }

        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    SharedPreferences.Editor editor = pref.edit(); //SharedPreferences'a kayıt eklemek için editor oluşturuyoruz
                    editor.putInt("ThemeKey",0); //int değer ekleniyor
                    editor.commit(); //Kayıt

                }
                else{
                    SharedPreferences.Editor editor = pref.edit(); //SharedPreferences'a kayıt eklemek için editor oluşturuyoruz
                    editor.putInt("ThemeKey",1); //int değer ekleniyor
                    editor.commit(); //Kayıt
                }

            }
        });
        saveBtn = findViewById(R.id.SaveButton);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getBaseContext().getPackageManager().
                        getLaunchIntentForPackage(getBaseContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });


    }
}
