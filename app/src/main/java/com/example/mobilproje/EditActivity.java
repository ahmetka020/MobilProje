package com.example.mobilproje;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class EditActivity extends AppCompatActivity {
    EditText nameEditText;
    EditText addressEditText;
    EditText startDateEditText;
    EditText endDateEditText;
    EditText detailsEditText;
    Button editSaveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_activity);
        nameEditText = findViewById(R.id.NameEditText);
        addressEditText = findViewById(R.id.AddressEditText);
        startDateEditText = findViewById(R.id.StartDateEditText);
        endDateEditText = findViewById(R.id.EndDateEditText);
        detailsEditText = findViewById(R.id.DetailsEditText);
        editSaveBtn = findViewById(R.id.EditSaveBtn);

        editSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable name = nameEditText.getText();
                Editable address = addressEditText.getText();
                Editable startDate = startDateEditText.getText();
                Editable endDate = endDateEditText.getText();
                Editable details = detailsEditText.getText();
                Event e = new Event();
                e.setName(name);
                e.setAddress(address);
                e.setDetail(details);
                e.setStartDate(startDate);
                e.setEndDate(endDate);
                writeEvents(e);


                Intent myIntent = new Intent(v.getContext(), EventsActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }

    private void writeEvents(Event e){
        try{
            FileOutputStream fileOutputStream = openFileOutput("Events.txt",MODE_APPEND);
            String event = e.getName() +"\n" + e.getStartDate() + "-" + e.getEndDate() + "\n" + e.getAddress() + "\n" +e.getDetail() + "\n--------------";
            String newLine = "\n";
            byte[] byteArr = event.getBytes();
            fileOutputStream.write(newLine.getBytes());
            fileOutputStream.write(byteArr);
            fileOutputStream.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void deleteEvents(){

    }
}
