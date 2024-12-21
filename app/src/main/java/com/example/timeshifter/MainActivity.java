package com.example.timeshifter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.timeshifter.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerSourceTimeZone, spinnerDestTimeZone;
    private Button btnConvert;
    private TextView convertedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerSourceTimeZone = findViewById(R.id.spinnerSourceTimeZone);
        spinnerDestTimeZone = findViewById(R.id.spinnerDestTimeZone);
        btnConvert = findViewById(R.id.btnConvert);
        convertedTime = findViewById(R.id.convertedTime);

        // Load time zones into spinners
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.time_zone_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSourceTimeZone.setAdapter(adapter);
        spinnerDestTimeZone.setAdapter(adapter);

        // Set a click listener on the convert button
        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sourceTimeZone = spinnerSourceTimeZone.getSelectedItem().toString();
                String destTimeZone = spinnerDestTimeZone.getSelectedItem().toString();

                // Convert time
                String converted = convertTime(sourceTimeZone, destTimeZone);
                convertedTime.setText("Converted Time: " + converted);
            }
        });
    }

    private String convertTime(String sourceTimeZone, String destTimeZone) {
        try {
            // Get the current time in GMT
            TimeZone sourceTZ = TimeZone.getTimeZone(sourceTimeZone);
            TimeZone destTZ = TimeZone.getTimeZone(destTimeZone);

            Calendar calendar = Calendar.getInstance(sourceTZ);
            Date date = calendar.getTime();

            // Set up the formatter to display the time
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(destTZ);

            // Format the date in the destination time zone
            return sdf.format(date);

        } catch (Exception e) {
            e.printStackTrace();
            return "Error in conversion";
        }
    }
}
