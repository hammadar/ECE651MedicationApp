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

public class MedicalProfessionalAccess extends AppCompatActivity {
    private Button retrievePatientInfoButton;
    private Button addNewMedicationButton;
    private Button savePatientDataButton;
    private EditText patientId, medicationName, timeBetweenIntake;
    private Spinner timeBetweenIntakeUnits,dailyFrequency;
    private CheckBox mondayCheckbox,tuesdayCheckbox,wednesdayCheckbox,thursdayCheckbox,fridayCheckbox,saturdayCheckbox,sundayCheckbox;

    private TextView medicationNameLabelObj, weeklyFrequencyLabelObj,dailyFrequencyLabelObj,timeBetweenIntakeLabelObj;
    private LinearLayout checkboxLayout;

    private int numberOfMedications=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_professional_access);

        medicationNameLabelObj=findViewById(R.id.medicationNameLabel);
        weeklyFrequencyLabelObj=findViewById(R.id.weeklyFrequencyLabel);
        dailyFrequencyLabelObj=findViewById(R.id.dailyFrequencyLabel);
        timeBetweenIntakeLabelObj=findViewById(R.id.dailyFrequencyLabel);

        retrievePatientInfoButton= findViewById(R.id.retrievePatientInfo);
        addNewMedicationButton= findViewById(R.id.addNewMedication);
        savePatientDataButton= findViewById(R.id.savePatientData);

        patientId=findViewById(R.id.patientId);
        medicationName=findViewById(R.id.medicationNameTextBox);
        timeBetweenIntake=findViewById(R.id.timeBetweenIntakeTextBox);

        timeBetweenIntakeUnits=findViewById(R.id.timeBetweenIntakeUnitsDropBox);
        dailyFrequency=findViewById(R.id.dailyFrequencyDropBox);

        checkboxLayout=findViewById(R.id.checkBoxLayout);
        mondayCheckbox= findViewById(R.id.mondayCheckBox);
        tuesdayCheckbox= findViewById(R.id.tuesdayCheckBox);
        wednesdayCheckbox= findViewById(R.id.wednesdayCheckBox);
        thursdayCheckbox= findViewById(R.id.thursdayCheckBox);
        fridayCheckbox= findViewById(R.id.fridayCheckBox);
        saturdayCheckbox= findViewById(R.id.saturdayCheckBox);
        sundayCheckbox= findViewById(R.id.sundayCheckBox);



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


        TableLayout tl = findViewById(R.id.patientDataTableLayout);

        //Medication Name
        TableRow tr = new TableRow(this);

        LinearLayout ll= new LinearLayout(this);
        TextView tv = new TextView(this);
        tv.setText("Medication "+numberOfMedications+ " Name:");
        tv.setHeight(medicationNameLabelObj.getHeight());
        tv.setWidth(medicationNameLabelObj.getWidth());

        EditText et= new EditText(this);
        et.setWidth(500);

        //Weekly Frequency
        TableRow tr1 = new TableRow(this);
        LinearLayout ll1= new LinearLayout(this);

        ll1.setOrientation(LinearLayout.HORIZONTAL);
//        ll1.setMinimumHeight(checkboxLayout.getHeight());
//        ll1.setMinimumWidth(checkboxLayout.getWidth());
        TextView tv1 = new TextView(this);
        tv1.setText("Weekly Frequency:");
//        tv.setHeight(weeklyFrequencyLabelObj.getHeight());
//        tv.setWidth(weeklyFrequencyLabelObj.getWidth());

//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.leftMargin = 10;


        CheckBox cb = new CheckBox(this);
        cb.setText("S");
        cb.setGravity(Gravity.CENTER);
        cb.setButtonDrawable(sundayCheckbox.getBackground());

        CheckBox cb1 = new CheckBox(this);
        cb1.setText("M");
        cb1.setGravity(Gravity.CENTER);
        cb1.setButtonDrawable(sundayCheckbox.getBackground());

        CheckBox cb2 = new CheckBox(this);
        cb2.setText("T");
        cb2.setGravity(Gravity.CENTER);
        cb2.setButtonDrawable(sundayCheckbox.getBackground());

        CheckBox cb3 = new CheckBox(this);
        cb3.setText("W");
        cb3.setGravity(Gravity.CENTER);
        cb3.setButtonDrawable(sundayCheckbox.getBackground());

        CheckBox cb4 = new CheckBox(this);
        cb4.setText("T");
        cb4.setGravity(Gravity.CENTER);
        cb4.setButtonDrawable(sundayCheckbox.getBackground());

        CheckBox cb5 = new CheckBox(this);
        cb5.setText("F");
        cb5.setGravity(Gravity.CENTER);
        cb5.setButtonDrawable(sundayCheckbox.getBackground());

        CheckBox cb6 = new CheckBox(this);
        cb6.setText("S");
        cb6.setGravity(Gravity.CENTER);
        cb6.setButtonDrawable(sundayCheckbox.getBackground());

        //Daily Frequency
        TableRow tr2 = new TableRow(this);
        LinearLayout ll2= new LinearLayout(this);

        TextView tv2 = new TextView(this);
        tv2.setText("Times per day:");
//        tv2.setHeight(dailyFrequencyLabelObj.getHeight());
//        tv2.setWidth(dailyFrequencyLabelObj.getWidth());

        Spinner dailyFrequencySpinner = new Spinner(this);
//        dailyFrequencySpinner.setMinimumHeight(dailyFrequency.getHeight());
//        dailyFrequencySpinner.setMinimumWidth(dailyFrequency.getWidth());
        final String[] dailyFrequencyArray = getResources().getStringArray(R.array.medication_daily_frequency);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, dailyFrequencyArray);
        dailyFrequencySpinner.setAdapter(arrayAdapter);

        //Time between intake
        TableRow tr3 = new TableRow(this);
        LinearLayout ll3= new LinearLayout(this);

        TextView tv3 = new TextView(this);
        tv3.setText("Times between intake:");
//        tv3.setHeight(timeBetweenIntakeLabelObj.getHeight());
//        tv3.setWidth(timeBetweenIntakeLabelObj.getWidth());

        EditText et1= new EditText(this);
        et1.setWidth(150);
//        et1.setHeight(timeBetweenIntake.getHeight());

        Spinner intakeTimeUnitsSpinner = new Spinner(this);
//        intakeTimeUnitsSpinner.setMinimumHeight(timeBetweenIntakeUnits.getHeight());
//        intakeTimeUnitsSpinner.setMinimumWidth(timeBetweenIntakeUnits.getWidth());
        final String[] intakeTimeUnitsArray = getResources().getStringArray(R.array.time_units);

        ArrayAdapter intakeUnitsArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, intakeTimeUnitsArray);
        intakeTimeUnitsSpinner.setAdapter(intakeUnitsArrayAdapter);







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
        String medication_Name=medicationName.getText().toString();
        Boolean isMondayChecked=mondayCheckbox.isChecked();
        Boolean isTuesdayChecked=tuesdayCheckbox.isChecked();
        Boolean isWednesdayChecked=wednesdayCheckbox.isChecked();
        Boolean isThursdayChecked=thursdayCheckbox.isChecked();
        Boolean isFridayChecked=fridayCheckbox.isChecked();
        Boolean isSaturdayChecked=saturdayCheckbox.isChecked();
        Boolean isSundayChecked=sundayCheckbox.isChecked();
        String dailyFrequencyValue=dailyFrequency.getSelectedItem().toString();
        String timeBetweenIntakeValue=timeBetweenIntake.getText().toString();
        String timeBetweenIntakeUnitsValue=timeBetweenIntakeUnits.getSelectedItem().toString();

        PrescriptionData data= new PrescriptionData
                (
                        patient_Id//,
                        /*medication_Name,
                        isMondayChecked,
                        isTuesdayChecked,
                        isWednesdayChecked,
                        isThursdayChecked,
                        isFridayChecked,
                        isSaturdayChecked,
                        isSundayChecked,
                        dailyFrequencyValue,
                        timeBetweenIntakeValue,
                        timeBetweenIntakeUnitsValue*/
                );

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

    }
}
