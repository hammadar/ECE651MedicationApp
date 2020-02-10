package com.uwece651.medicationapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MedicalProfessionalAccess extends AppCompatActivity {
    private Button retrievePatientInfoButton;
    private Button addNewMedicationButton;
    private Button savePatientDataButton;
    private EditText patientId;

    private int numberOfMedications=0;

    List<EditText> medicationNameEditTextList = new ArrayList<EditText>();
    List<CheckBox> dayCheckboxList = new ArrayList<CheckBox>();
    List<Spinner> timesPerDaySpinnerList = new ArrayList<Spinner>();
    List<EditText> timeBetweenIntakeEditTextList = new ArrayList<EditText>();
    List<Spinner> intakeUnitsSpinnerList = new ArrayList<Spinner>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_professional_access);

        retrievePatientInfoButton= findViewById(R.id.retrievePatientInfo);
        addNewMedicationButton= findViewById(R.id.addNewMedication);
        savePatientDataButton= findViewById(R.id.savePatientData);

        patientId=findViewById(R.id.patientId);

        retrievePatientInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrievePatientInfo();
            }
        });

        addNewMedicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewMedication(v);
            }
        });

        savePatientDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePatientData();
            }
        });
    }

    public void retrievePatientInfo(){
        if (patientId.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter a patient ID", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            Toast.makeText(getApplicationContext(), "Patient ID: " + patientId.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void addNewMedication(View view){
        numberOfMedications++;

        TableLayout tl = findViewById(R.id.medicationDataTableLayout);

        //Medication Name
        TableRow tr = new TableRow(this);

        LinearLayout ll= new LinearLayout(this);
        TextView tv = new TextView(this);
        tv.setText("Medication "+numberOfMedications+ " Name:");

        EditText et= new EditText(this);
        et.setWidth(500);
        medicationNameEditTextList.add(et);

        //Weekly Frequency
        TableRow tr1 = new TableRow(this);
        LinearLayout ll1= new LinearLayout(this);

        ll1.setOrientation(LinearLayout.HORIZONTAL);

        TextView tv1 = new TextView(this);
        tv1.setText("Days:");


        CheckBox cb = new CheckBox(this);
        cb.setText("S");
        cb.setGravity(Gravity.CENTER);
        dayCheckboxList.add(cb);
//        cb.setBackgroundResource(R.drawable.checkbox_weekday);

        CheckBox cb1 = new CheckBox(this);
        cb1.setText("M");
        cb1.setGravity(Gravity.CENTER);
        dayCheckboxList.add(cb1);
//        cb1.setButtonDrawable(R.drawable.checkbox_weekday);

        CheckBox cb2 = new CheckBox(this);
        cb2.setText("T");
        cb2.setGravity(Gravity.CENTER);
        dayCheckboxList.add(cb2);
//        cb2.setButtonDrawable(R.drawable.checkbox_weekday);

        CheckBox cb3 = new CheckBox(this);
        cb3.setText("W");
        cb3.setGravity(Gravity.CENTER);
        dayCheckboxList.add(cb3);
//        cb3.setButtonDrawable(R.drawable.checkbox_weekday);

        CheckBox cb4 = new CheckBox(this);
        cb4.setText("T");
        cb4.setGravity(Gravity.CENTER);
        dayCheckboxList.add(cb4);
//        cb4.setButtonDrawable(R.drawable.checkbox_weekday);

        CheckBox cb5 = new CheckBox(this);
        cb5.setText("F");
        cb5.setGravity(Gravity.CENTER);
        dayCheckboxList.add(cb5);
//        cb5.setButtonDrawable(R.drawable.checkbox_weekday);

        CheckBox cb6 = new CheckBox(this);
        cb6.setText("S");
        cb6.setGravity(Gravity.CENTER);
        dayCheckboxList.add(cb6);
//        cb6.setButtonDrawable(R.drawable.checkbox_weekday);

        //Daily Frequency
        TableRow tr2 = new TableRow(this);
        LinearLayout ll2= new LinearLayout(this);

        TextView tv2 = new TextView(this);
        tv2.setText("Times per day:");

        Spinner dailyFrequencySpinner = new Spinner(this);
        final String[] dailyFrequencyArray = getResources().getStringArray(R.array.medication_daily_frequency);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, dailyFrequencyArray);
        dailyFrequencySpinner.setAdapter(arrayAdapter);
        timesPerDaySpinnerList.add(dailyFrequencySpinner);

        //Time between intake
        TableRow tr3 = new TableRow(this);
        LinearLayout ll3= new LinearLayout(this);

        TextView tv3 = new TextView(this);
        tv3.setText("Times between intake:");

        EditText et1= new EditText(this);
        et1.setWidth(150);
        timeBetweenIntakeEditTextList.add(et1);

        Spinner intakeTimeUnitsSpinner = new Spinner(this);
        final String[] intakeTimeUnitsArray = getResources().getStringArray(R.array.time_units);

        ArrayAdapter intakeUnitsArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, intakeTimeUnitsArray);
        intakeTimeUnitsSpinner.setAdapter(intakeUnitsArrayAdapter);
        intakeUnitsSpinnerList.add(intakeTimeUnitsSpinner);

        //Medication Name
        ll.addView(tv);
        ll.addView(et);
        tr.addView(ll);
        tl.addView(tr);

        //Weekly Frequency
        ll1.addView(tv1);
        ll1.addView(cb);
        ll1.addView(cb1);
        ll1.addView(cb2);
        ll1.addView(cb3);
        ll1.addView(cb4);
        ll1.addView(cb5);
        ll1.addView(cb6);
        tr1.addView(ll1);
        tl.addView(tr1);

        //Daily Frequency
        ll2.addView(tv2);
        ll2.addView(dailyFrequencySpinner);
        tr2.addView(ll2);
        tl.addView(tr2);

        //Time between intake
        ll3.addView(tv3);
        ll3.addView(et1);
        ll3.addView(intakeTimeUnitsSpinner);

        tr3.addView(ll3);
        tl.addView(tr3);

        //TODO: 27/01/20 Map medicationId with label maybe so we can keep track of medications



    }

    public void savePatientData(){

        String patient_Id=patientId.getText().toString();
        String medication_Name;
        Boolean isMondayChecked;
        Boolean isTuesdayChecked;
        Boolean isWednesdayChecked;
        Boolean isThursdayChecked;
        Boolean isFridayChecked;
        Boolean isSaturdayChecked;
        Boolean isSundayChecked;
        String dailyFrequencyValue;
        String timeBetweenIntakeValue;
        String timeBetweenIntakeUnitsValue;

        for (int i = 0; i < medicationNameEditTextList.size(); i++) {
            medication_Name=medicationNameEditTextList.get(i).getText().toString();

            isSundayChecked=dayCheckboxList.get(i*7).isChecked();
            isMondayChecked=dayCheckboxList.get(i*7 +1).isChecked();
            isTuesdayChecked=dayCheckboxList.get(i*7 + 2).isChecked();
            isWednesdayChecked=dayCheckboxList.get(i*7 + 3).isChecked();
            isThursdayChecked=dayCheckboxList.get(i*7 + 4).isChecked();
            isFridayChecked=dayCheckboxList.get(i*7 + 5).isChecked();
            isSaturdayChecked=dayCheckboxList.get(i*7 + 6).isChecked();

            dailyFrequencyValue=timesPerDaySpinnerList.get(i).getSelectedItem().toString();
            timeBetweenIntakeValue=timeBetweenIntakeEditTextList.get(i).getText().toString();
            timeBetweenIntakeUnitsValue=intakeUnitsSpinnerList.get(i).getSelectedItem().toString();

            Log.d("MedicalProfAccess","patient_Id = " + patient_Id);
            Log.d("MedicalProfAccess","medication_Name = " + medication_Name);
            Log.d("MedicalProfAccess","isMondayChecked = " + isMondayChecked);
            Log.d("MedicalProfAccess","isTuesdayChecked = " + isTuesdayChecked);
            Log.d("MedicalProfAccess","isWednesdayChecked = " + isWednesdayChecked);
            Log.d("MedicalProfAccess","isThursdayChecked = " + isThursdayChecked);
            Log.d("MedicalProfAccess","isFridayChecked = " + isFridayChecked);
            Log.d("MedicalProfAccess","isSaturdayChecked = " + isSaturdayChecked);
            Log.d("MedicalProfAccess","isSundayChecked = " + isSundayChecked);
            Log.d("MedicalProfAccess","dailyFrequencyValue = " + dailyFrequencyValue);
            Log.d("MedicalProfAccess","timeBetweenIntakeValue = " + timeBetweenIntakeValue);
            Log.d("MedicalProfAccess","timeBetweenIntakeUnitsValue = " + timeBetweenIntakeUnitsValue);
            Log.d("MedicalProfAccess","\n\n");

        }


//        PrescriptionData data= new PrescriptionData
//                (
//                       // patient_Id,
//                        /*medication_Name,
//                        isMondayChecked,
//                        isTuesdayChecked,
//                        isWednesdayChecked,
//                        isThursdayChecked,
//                        isFridayChecked,
//                        isSaturdayChecked,
//                        isSundayChecked,
//                        dailyFrequencyValue,
//                        timeBetweenIntakeValue,
//                        timeBetweenIntakeUnitsValue*/
//                );



    }
}
