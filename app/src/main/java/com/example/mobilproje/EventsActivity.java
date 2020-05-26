package com.example.mobilproje;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class EventsActivity extends AppCompatActivity {
    Button saveBtn;
    TextView showEventsTextView;
    TextView showEventsTextView2;
    Button editBtn;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        showEventsTextView2 = findViewById(R.id.showEvents2);
        showEventsTextView = findViewById(R.id.showEvents);
        readCalendars();
        readEvents();
        editBtn = findViewById(R.id.EditButton);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(EventsActivity.this, editBtn);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.event_menu, popup.getMenu());


                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("DELETE")){
                            deleteAll();
                            Toast.makeText(getApplicationContext(),"ALL ALARMS DELETED!",Toast.LENGTH_SHORT).show();
                            save();
                            return true;
                        }
                        else if(item.getTitle().equals("ADD")){
                            Intent myIntent = new Intent(v.getContext(), EditActivity.class);
                            startActivityForResult(myIntent, 0);
                            return true;
                        }
                        else if(item.getTitle().equals("SHARE")){
                            shareTwitter(message);
                        }
                        return true;
                    }
                });

                popup.show();//showing popup menu

            }
        });//closing the setOnClickListener method




        saveBtn = findViewById(R.id.SaveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

    }


    private void deleteAll(){
            try{
                FileOutputStream fileOutputStream = openFileOutput("Calendars.txt",MODE_PRIVATE);
                //FileOutputStream fileOutputStream=new FileOutputStream(new File("Calendars.txt"),true);
                String newLine = "";
                fileOutputStream.write(newLine.getBytes());
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        try{
            FileOutputStream fileOutputStream = openFileOutput("Events.txt",MODE_PRIVATE);
            //FileOutputStream fileOutputStream=new FileOutputStream(new File("Calendars.txt"),true);
            String newLine = "";
            fileOutputStream.write(newLine.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readCalendars(){
        try{
            FileInputStream fileInputStream = openFileInput("Calendars.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String lines;
            while((lines = bufferedReader.readLine()) != null){
                stringBuffer.append(lines+"\n");
            }
            showEventsTextView2.setText(stringBuffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void save(){
        Intent i = getBaseContext().getPackageManager().
                getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
    private void readEvents(){
        try{
            FileInputStream fileInputStream = openFileInput("Events.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String lines;
            while((lines = bufferedReader.readLine()) != null){
                stringBuffer.append(lines+"\n");
            }
            message = stringBuffer.toString();
            showEventsTextView.setText(stringBuffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void shareTwitter(String message) {
        Intent tweetIntent = new Intent(Intent.ACTION_SEND);
        tweetIntent.putExtra(Intent.EXTRA_TEXT, "This is a Test.");
        tweetIntent.setType("text/plain");

        PackageManager packManager = getPackageManager();
        List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(tweetIntent, PackageManager.MATCH_DEFAULT_ONLY);

        boolean resolved = false;
        for (ResolveInfo resolveInfo : resolvedInfoList) {
            if (resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")) {
                tweetIntent.setClassName(
                        resolveInfo.activityInfo.packageName,
                        resolveInfo.activityInfo.name);
                resolved = true;
                break;
            }
        }
        if (resolved) {
            startActivity(tweetIntent);
        } else {
            Intent i = new Intent();
            i.putExtra(Intent.EXTRA_TEXT, message);
            i.setAction(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://twitter.com/intent/tweet?text=" + urlEncode(message)));
            startActivity(i);
            Toast.makeText(this, "Twitter app isn't found", Toast.LENGTH_LONG).show();
        }
    }

    private String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.deleteItem:
//                deleteAll();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}
