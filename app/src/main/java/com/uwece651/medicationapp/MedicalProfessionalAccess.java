package com.uwece651.medicationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MedicalProfessionalAccess extends AppCompatActivity {
    private Button retrievePatientInfoButton;
    private Button addNewMedicationButton;
    private Button savePatientDataButton;
    private EditText patientId;

    //for storing retrieved values
    String medication_id;
    String schedule_id;
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

    private int numberOfMedications=0;

    List<EditText> medicationNameEditTextList = new ArrayList<EditText>();
    List<CheckBox> dayCheckboxList = new ArrayList<CheckBox>();
    List<Spinner> timesPerDaySpinnerList = new ArrayList<Spinner>();
    List<EditText> timeBetweenIntakeEditTextList = new ArrayList<EditText>();
//    List<Spinner> intakeUnitsSpinnerList = new ArrayList<Spinner>();
    List<CalendarView> calendarList = new ArrayList<CalendarView>();



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

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            CollectionReference PrescriptionDataDB = db.collection("PrescriptionData");
            CollectionReference MedicationDataDB = db.collection("MedicationData");
            CollectionReference MedicationScheduleDB = db.collection("MedicationSchedule");



            String prescriptionID=patientId.getText().toString();

            Log.d("MedicalProfAccess","prescriptionID = " + prescriptionID);

            DocumentReference docRef= PrescriptionDataDB.document(prescriptionID);
            Log.d("MedicalProfAccess","HERE 4");
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    Log.d("MedicalProfAccess","HERE 3");
                    if (task.isSuccessful()) {
                        Log.d("MedicalProfAccess","HERE 2");
                        DocumentSnapshot document = task.getResult();
                        Log.d("MedicalProfAccess","HERE 5");
                        if (document.exists()) {
                            PrescriptionData prescription_data = document.toObject(PrescriptionData.class);
                            medication_id=prescription_data.getMedicationID();
                            schedule_id=prescription_data.getScheduleID();

                            Log.d("MedicalProfAccess","medication_id = " + medication_id);
                            Log.d("MedicalProfAccess","schedule_id = " + schedule_id);

                        }
                        else{
                            Log.d("MedicalProfAccess","HERE 1");
                        }
                    } else {
                        Log.d("MedicalProfAccess", "get failed with ", task.getException());
                    }
                }
            });





//            docRef= MedicationDataDB.document(medication_id);
//            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                    if (task.isSuccessful()) {
//                        DocumentSnapshot document = task.getResult();
//                        if (document.exists()) {
//                            MedicationData medication_data = document.toObject(MedicationData.class);
//                            medication_Name=medication_data.getMedicationID();
//                        }
//                    } else {
//                        Log.d("MedicalProfAccess", "get failed with ", task.getException());
//                    }
//                }
//            });
//
//            docRef= MedicationScheduleDB.document(schedule_id);
//            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                    if (task.isSuccessful()) {
//                        DocumentSnapshot document = task.getResult();
//                        if (document.exists()) {
//                            MedicationSchedule schedule_data = document.toObject(MedicationSchedule.class);
//                            isMondayChecked=schedule_data.getMondayChecked();;
//                            isTuesdayChecked=schedule_data.getTuesdayChecked();;
//                            isWednesdayChecked=schedule_data.getWednesdayChecked();;
//                            isThursdayChecked=schedule_data.getThursdayChecked();;
//                            isFridayChecked=schedule_data.getFridayChecked();;
//                            isSaturdayChecked=schedule_data.getSaturdayChecked();;
//                            isSundayChecked=schedule_data.getSundayChecked();;
//                            dailyFrequencyValue=schedule_data.getDailyFrequency();;
//                            timeBetweenIntakeValue=schedule_data.getHoursFrequency();;
//                        }
//                    } else {
//                        Log.d("MedicalProfAccess", "get failed with ", task.getException());
//                    }
//                }
//            });


            //TODO for loop  number of prescriptions in patient class?

            TableLayout tl = findViewById(R.id.medicationDataTableLayout);

            //Medication Name
            TableRow tr = new TableRow(this);

            LinearLayout ll= new LinearLayout(this);
            TextView tv = new TextView(this);
            tv.setText("Medication "+medication_id+ " Name:");

            EditText et= new EditText(this);
            et.setWidth(500);
            et.setText(medication_Name);
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
            cb.setChecked(isSundayChecked);
            dayCheckboxList.add(cb);

            CheckBox cb1 = new CheckBox(this);
            cb1.setText("M");
            cb1.setGravity(Gravity.CENTER);
            cb1.setChecked(isMondayChecked);
            dayCheckboxList.add(cb1);

            CheckBox cb2 = new CheckBox(this);
            cb2.setText("T");
            cb2.setGravity(Gravity.CENTER);
            cb2.setChecked(isTuesdayChecked);
            dayCheckboxList.add(cb2);

            CheckBox cb3 = new CheckBox(this);
            cb3.setText("W");
            cb3.setGravity(Gravity.CENTER);
            cb3.setChecked(isWednesdayChecked);
            dayCheckboxList.add(cb3);

            CheckBox cb4 = new CheckBox(this);
            cb4.setText("T");
            cb4.setGravity(Gravity.CENTER);
            cb4.setChecked(isThursdayChecked);
            dayCheckboxList.add(cb4);

            CheckBox cb5 = new CheckBox(this);
            cb5.setText("F");
            cb5.setGravity(Gravity.CENTER);
            cb5.setChecked(isFridayChecked);
            dayCheckboxList.add(cb5);

            CheckBox cb6 = new CheckBox(this);
            cb6.setText("S");
            cb6.setGravity(Gravity.CENTER);
            cb6.setChecked(isSaturdayChecked);
            dayCheckboxList.add(cb6);

            //Daily Frequency
            TableRow tr2 = new TableRow(this);
            LinearLayout ll2= new LinearLayout(this);

            TextView tv2 = new TextView(this);
            tv2.setText("Times per day:");

            Spinner dailyFrequencySpinner = new Spinner(this);
            final String[] dailyFrequencyArray = getResources().getStringArray(R.array.medication_daily_frequency);

            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, dailyFrequencyArray);
            dailyFrequencySpinner.setAdapter(arrayAdapter);
            int spinnerposition = arrayAdapter.getPosition(dailyFrequencyValue);
            dailyFrequencySpinner.setSelection(spinnerposition);
            timesPerDaySpinnerList.add(dailyFrequencySpinner);

            //Time between intake
            TableRow tr3 = new TableRow(this);
            LinearLayout ll3= new LinearLayout(this);

            TextView tv3 = new TextView(this);
            tv3.setText("Hours between intake:");

            EditText et1= new EditText(this);
            et1.setWidth(150);
            et1.setText(timeBetweenIntakeValue);
            timeBetweenIntakeEditTextList.add(et1);

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

            tr3.addView(ll3);
            tl.addView(tr3);



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
        tv3.setText("Hours between intake:");

        EditText et1= new EditText(this);
        et1.setWidth(150);
        timeBetweenIntakeEditTextList.add(et1);

//        Spinner intakeTimeUnitsSpinner = new Spinner(this);
//        final String[] intakeTimeUnitsArray = getResources().getStringArray(R.array.time_units);
//
//        ArrayAdapter intakeUnitsArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, intakeTimeUnitsArray);
//        intakeTimeUnitsSpinner.setAdapter(intakeUnitsArrayAdapter);
//        intakeUnitsSpinnerList.add(intakeTimeUnitsSpinner);

        //Start/End date for medication
//        TableRow tr4 = new TableRow(this);
//        LinearLayout ll4= new LinearLayout(this);
//
//        TextView tv4 = new TextView(this);
//        tv4.setText("Duration of prescription:");
//
//        CalendarView calendar = new CalendarView(this);



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
//        ll3.addView(intakeTimeUnitsSpinner);
//
        tr3.addView(ll3);
        tl.addView(tr3);

        //calendar
//        ll4.addView(tv4);
//        ll4.addView(calendar);
//        tr4.addView(ll4);
//
//        tl.addView(tr4);


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

        PrescriptionData prescriptionData= new PrescriptionData(patient_Id);
        //prescriptionData.setAssignedByDoctorName(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

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
            //timeBetweenIntakeUnitsValue=intakeUnitsSpinnerList.get(i).getSelectedItem().toString();

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
            //Log.d("MedicalProfAccess","timeBetweenIntakeUnitsValue = " + timeBetweenIntakeUnitsValue);
            Log.d("MedicalProfAccess","\n\n");

            MedicationData medData = new MedicationData(Integer.toString(i));
            medData.setBrandName(medication_Name);
            storeMedicationData(medData);

            MedicationSchedule medSchedule = new MedicationSchedule(Integer.toString(i));
            medSchedule.setMondayChecked(isMondayChecked);
            medSchedule.setTuesdayChecked(isTuesdayChecked);
            medSchedule.setWednesdayChecked(isWednesdayChecked);
            medSchedule.setThursdayChecked(isThursdayChecked);
            medSchedule.setFridayChecked(isFridayChecked);
            medSchedule.setSaturdayChecked(isSaturdayChecked);
            medSchedule.setSundayChecked(isSundayChecked);
            medSchedule.setDailyFrequency(dailyFrequencyValue);
            medSchedule.setHoursFrequency(timeBetweenIntakeValue);
            storeMedicationSchedule(medSchedule);

            prescriptionData.setMedicationID(medData.getMedicationID());
            prescriptionData.setScheduleID(medSchedule.getScheduleID());
            storePrescriptionData(prescriptionData);

            //TODO Store medication/schedule ids in array in prescriptionData object

        }

    }

    public void storeMedicationData(MedicationData medData) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference MedicationData = db.collection("MedicationData");
        MedicationData.document(medData.getMedicationID()).set(medData);
    }

    public void storeMedicationSchedule(MedicationSchedule medSchedule) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference MedicationSchedule = db.collection("MedicationSchedule");
        MedicationSchedule.document(medSchedule.getScheduleID()).set(medSchedule);
    }

    public void storePrescriptionData(PrescriptionData prescriptionData) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference PrescriptionData = db.collection("PrescriptionData");
        PrescriptionData.document(prescriptionData.getPrescriptionID()).set(prescriptionData);
    }
}
