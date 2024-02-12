package com.mv.runningpacecalculator;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner distancesSpinner;
    ArrayList<String> distances;
    FloatingActionButton floatingActionButton;
    FloatingActionButton switchFAB;
    EditText hoursEditText;
    EditText minutesEditText;
    EditText secondsEditText;
    TextView resultView;
    TextView paceOrTime;
    TextView colon1;

    boolean defaultMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        distances = new ArrayList<>();
        distances.add("1 mile"); distances.add("1k"); distances.add("3k"); distances.add("5k"); distances.add("10k"); distances.add("12k"); distances.add("9 miles"); distances.add("13.1 miles"); distances.add("26.2 miles");
        distancesSpinner = findViewById(R.id.distancesSpinner);
        distancesSpinner.setAdapter(new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, distances));
        distancesSpinner.setSelection(3);

        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);
        switchFAB = findViewById(R.id.switchFAB);
        switchFAB.setOnClickListener(this);

        resultView = findViewById(R.id.resultView);
        paceOrTime = findViewById(R.id.paceOrTime);
        colon1 = findViewById(R.id.colon1);

        hoursEditText = findViewById(R.id.hoursEditText);
        hoursEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 1) {
                    if (Integer.parseInt(String.valueOf(s)) > 60) {
                        hoursEditText.setText("");
                        Toast.makeText(getBaseContext(), "You cannot have a time over 60 hours", Toast.LENGTH_SHORT).show();
                    } else {
                        minutesEditText.requestFocus();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        hoursEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() { @Override public void onFocusChange(View v, boolean hasFocus) { if (hasFocus) hoursEditText.setText(""); }});

        minutesEditText = findViewById(R.id.minutesEditText);
        minutesEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 1) {
                    if (Integer.parseInt(String.valueOf(s)) > 59) {
                        minutesEditText.setText("");
                        Toast.makeText(getBaseContext(), "You cannot have a time over 59 minutes, move to the hours field", Toast.LENGTH_SHORT).show();
                    } else {
                        secondsEditText.requestFocus();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        minutesEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() { @Override public void onFocusChange(View v, boolean hasFocus) { if (hasFocus) minutesEditText.setText(""); }});

        secondsEditText = findViewById(R.id.secondsEditText);
        secondsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 1) {
                    if (Integer.parseInt(String.valueOf(s)) > 59) {
                        secondsEditText.setText("");
                        Toast.makeText(getBaseContext(), "You cannot have a time over 59 seconds, move to the minutes field", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        secondsEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() { @Override public void onFocusChange(View v, boolean hasFocus) { if (hasFocus) secondsEditText.setText(""); }});
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                if (((!String.valueOf(hoursEditText.getText()).equals("")) || (!defaultMode)) & (!String.valueOf(minutesEditText.getText()).equals("")) & (!String.valueOf(secondsEditText.getText()).equals(""))) {
                    int minutes = Integer.parseInt(minutesEditText.getText().toString());
                    int seconds = Integer.parseInt(secondsEditText.getText().toString());
                    double distance = 1;
                    switch (distances.get(distancesSpinner.getSelectedItemPosition())) {
                        case "1 mile": distance = 1; break;
                        case "1k": distance = 0.62; break;
                        case "3k": distance = 1.86; break;
                        case "5k": distance = 3.11; break;
                        case "10k": distance = 6.21; break;
                        case "12k": distance = 7.46; break;
                        case "9 miles": distance = 9; break;
                        case "13.1 miles": distance = 13.1; break;
                        case "26.2 miles": distance = 26.2; break;
                    }

                    if (defaultMode) {
                        int hours = Integer.parseInt(hoursEditText.getText().toString());
                        int goalSeconds = hours * 3600 + minutes * 60 + seconds;
                        double paceSeconds = goalSeconds / distance;
                        String pace1 = "00";
                        String pace2 = "00";
                        if (paceSeconds > 3599) {
                            Toast.makeText(getBaseContext(), "Pace too high", Toast.LENGTH_SHORT).show();
                        } else {
                            pace1 = String.valueOf((int) paceSeconds / 60);
                            pace2 = String.valueOf((int) paceSeconds % 60);
                            if (pace2.length() == 1) {
                                pace2 = "0" + pace2;
                            }
                        }

                        resultView.setText(pace1 + ":" + pace2);
                        break;
                    } else {
                        double paceSeconds = ((minutes * 60) + seconds) * distance;
                        String pace1 = String.valueOf((int) paceSeconds / 3600);
                        String pace2 = String.valueOf((int) (paceSeconds / 60) - (Integer.parseInt(pace1) * 60));
                        String pace3 = String.valueOf((int) paceSeconds % 60);

                        if (pace2.length() == 1) pace2 = "0" + pace2;
                        if (pace3.length() == 1) pace3 = "0" + pace3;
                        if (Integer.parseInt(pace1) < 1) {
                            if (Integer.parseInt(pace2) < 1) {
                                resultView.setText("00:" + pace3);
                                break;
                            } else {
                                resultView.setText(pace2 + ":" + pace3);
                                break;
                            }
                        } else {
                            resultView.setText(pace1 + ":" + pace2 + ":" + pace3);
                            break;
                        }
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.switchFAB:
                if (defaultMode) {
                    defaultMode = false;
                    paceOrTime.setText(R.string.pace);
                    hoursEditText.setVisibility(View.GONE);
                    colon1.setVisibility(View.GONE);
                } else {
                    defaultMode = true;
                    paceOrTime.setText(R.string.time);
                    hoursEditText.setVisibility(View.VISIBLE);
                    colon1.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}
