package com.example.mobilproje;
import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import static androidx.core.app.ActivityCompat.requestPermissions;


public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


            if (Settings.System.canWrite(context)) {
                Toast.makeText(context, "Alarm is Working!", Toast.LENGTH_LONG).show();

                Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                if (alarmUri == null) {
                    alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                }

                Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
                ringtone.play();
            } else {
                Toast.makeText(context, "Alarm is working!", Toast.LENGTH_SHORT).show();
                Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                if (alarmUri == null) {
                    alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                }

                Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
                ringtone.play();
            }
        }
}
