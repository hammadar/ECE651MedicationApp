package com.uwece651.medicationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MedicalProfessionalAccess extends AppCompatActivity {
    private Button retrievePatientInfoButton;
    private Button addNewMedicationButton;
    private Button savePatientDataButton;
    private EditText patientId;

    //for storing retrieved values
    List<String> prescription_ids;
    PrescriptionData[] prescriptions; //previous two are static for each patient. Items below will change for each prescription - HR
    String[] medication_ids;
    String[] schedule_ids;
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
    Patient currentPatient;

    private int numberOfMedications=0;

    List<EditText> medicationNameEditTextList = new ArrayList<EditText>();
    List<CheckBox> dayCheckboxList = new ArrayList<CheckBox>();
    List<Spinner> timesPerDaySpinnerList = new ArrayList<Spinner>();
    List<EditText> timeBetweenIntakeEditTextList = new ArrayList<EditText>();
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
                //displayRetrievedData();
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

    public void displayRetrievedData(){


        //TODO for loop  number of prescriptions in patient class?


            //setClassVariables(prescription);


            TableLayout tl = findViewById(R.id.medicationDataTableLayout);

            //Medication Name
            TableRow tr = new TableRow(this);

            LinearLayout ll= new LinearLayout(this);
            TextView tv = new TextView(this);
            tv.setText("Medication Name:");

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

    public void retrievePatientInfo(){
        if (patientId.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter a Patient ID", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            String patient_id=patientId.getText().toString();

            Log.d("MedicalProfAccess","patientID = " + patient_id);

            retrieveAssociatedPrescriptions(patient_id);
        }
    }

    public void addNewMedication(View view){

        numberOfMedications++;

        if(prescription_ids == null){
            prescription_ids = new ArrayList<>();
        }

        prescription_ids.add(UUID.randomUUID().toString());
        medication_ids = appArrayHandling.add(medication_ids, UUID.randomUUID().toString());
        schedule_ids = appArrayHandling.add(schedule_ids, UUID.randomUUID().toString());

        TableLayout tl = findViewById(R.id.medicationDataTableLayout);

        //Medication Name
        TableRow tr = new TableRow(this);

        LinearLayout ll= new LinearLayout(this);
        TextView tv = new TextView(this);
        tv.setText("Medication Name:");

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

        CheckBox cb1 = new CheckBox(this);
        cb1.setText("M");
        cb1.setGravity(Gravity.CENTER);
        dayCheckboxList.add(cb1);

        CheckBox cb2 = new CheckBox(this);
        cb2.setText("T");
        cb2.setGravity(Gravity.CENTER);
        dayCheckboxList.add(cb2);

        CheckBox cb3 = new CheckBox(this);
        cb3.setText("W");
        cb3.setGravity(Gravity.CENTER);
        dayCheckboxList.add(cb3);

        CheckBox cb4 = new CheckBox(this);
        cb4.setText("T");
        cb4.setGravity(Gravity.CENTER);
        dayCheckboxList.add(cb4);

        CheckBox cb5 = new CheckBox(this);
        cb5.setText("F");
        cb5.setGravity(Gravity.CENTER);
        dayCheckboxList.add(cb5);

        CheckBox cb6 = new CheckBox(this);
        cb6.setText("S");
        cb6.setGravity(Gravity.CENTER);
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
        timesPerDaySpinnerList.add(dailyFrequencySpinner);

        //Time between intake
        TableRow tr3 = new TableRow(this);
        LinearLayout ll3= new LinearLayout(this);

        TextView tv3 = new TextView(this);
        tv3.setText("Hours between intake:");

        EditText et1= new EditText(this);
        et1.setWidth(150);
        timeBetweenIntakeEditTextList.add(et1);

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
        tr3.addView(ll3);
        tl.addView(tr3);

        //calendar
//        ll4.addView(tv4);
//        ll4.addView(calendar);
//        tr4.addView(ll4);
//
//        tl.addView(tr4);

    }

    public void savePatientData(){

        String patient_Id=patientId.getText().toString();
        String prescriptionID;
        String medicationID;
        String scheduleID;
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
        PrescriptionData prescriptionData;


        //PrescriptionData prescriptionData= new PrescriptionData(prescriptionID);
        //TODO Need to figure out this registration issue
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

            Log.d("MedicalProfAccess","patient_Id = " + patient_Id);
            Log.d("MedicalProfAccess", "prescription_id =" + prescription_ids.get(i));
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
            Log.d("MedicalProfAccess","\n\n");

            MedicationData medData = new MedicationData(medication_ids[i]);
            Log.d("Med ID", medication_ids[i]);
            medData.setBrandName(medication_Name);
            storeMedicationData(medData);

            MedicationSchedule medSchedule = new MedicationSchedule(schedule_ids[i]);
            Log.d("Sched ID", schedule_ids[i]);
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

            prescriptionData = new PrescriptionData(prescription_ids.get(i));
            Log.d("Pres ID", prescription_ids.get(i));
            prescriptionData.setMedicationID(medData.getMedicationID());
            prescriptionData.setScheduleID(medSchedule.getScheduleID());
            prescriptionData.setMedicationName(medication_Name);
            storePrescriptionData(prescriptionData);

            savePatientPrescriptions(patient_Id);

            //TODO Store medication/schedule ids in array in prescriptionData object. Done - HR

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

    public void retrieveAssociatedPrescriptions(String patientID) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference patientDb = db.collection("Patients");
        DocumentReference docRef= patientDb.document(patientID);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //Patient retrievedPatient = document.toObject(Patient.class);
                        //Log.d("retrievedPatient", retrievedPatient.getUid());
                        prescription_ids = (List<String>)document.get("associatedPrescriptions");//retrievedPatient.getAssociatedPrescriptions();
                        //List<String> retrievedIDs = (List<String>)document.get("associatedPrescriptions");

                        for (int i = 0; i < prescription_ids.size(); i++) {
                            Log.d("prescriptionID", prescription_ids.get(i));
                        }

                        if (prescription_ids != null) {
                            prescriptions = new PrescriptionData[prescription_ids.size()];
                            for (int i = 0; i < prescription_ids.size(); i++) {
                                Log.d("RAP", "running " + i + " time\n");
                                getPrescriptionData(prescription_ids.get(i));
                            }
                        }


                    } else {
                        Log.d("Pat. Prescr.", "get failed with ", task.getException());
                            }
                }
                        };



        });
    }

    public void getPrescriptionData(String prescriptionID) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference prescriptionDb = db.collection("PrescriptionData");
        DocumentReference docRef = prescriptionDb.document(prescriptionID);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        PrescriptionData prescription = document.toObject(PrescriptionData.class);
                        prescriptions = appArrayHandling.add(prescriptions, prescription);
                        Log.d("GPD", "running with " + prescription.getPrescriptionID() + "\n");
                        setClassVariables(prescription);

                    } else {
                        Log.d("Prescrip.", "get failed with ", task.getException());
                    }
                }
            };



        });

    }

    public void savePatientPrescriptions(String patientID) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference patientsDb = db.collection("Patients");
        DocumentReference docRef = patientsDb.document(patientID);

        docRef.update("associatedPrescriptions", prescription_ids);


    }

    public void setClassVariables(PrescriptionData prescription) {
        medication_id = prescription.getMedicationID();
        schedule_id = prescription.getScheduleID();
        medication_Name = prescription.getMedicationName();

        medication_ids = appArrayHandling.add(medication_ids, medication_id);
        schedule_ids = appArrayHandling.add(schedule_ids, schedule_id);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference scheduleDb = db.collection("MedicationSchedule");
        DocumentReference docRef = scheduleDb.document(schedule_id);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        MedicationSchedule schedule = document.toObject(MedicationSchedule.class);
                        isMondayChecked = schedule.getMondayChecked();
                        isTuesdayChecked = schedule.getTuesdayChecked();
                        isWednesdayChecked = schedule.getWednesdayChecked();
                        isThursdayChecked = schedule.getThursdayChecked();
                        isFridayChecked = schedule.getFridayChecked();
                        isSaturdayChecked = schedule.getSaturdayChecked();
                        isSundayChecked = schedule.getSundayChecked();
                        dailyFrequencyValue = schedule.getDailyFrequency();
                        timeBetweenIntakeValue = schedule.getHoursFrequency();
                        displayRetrievedData();


                    } else {
                        Log.d("Schedule", "get failed with ", task.getException());
                    }
                }
            };



        });
    }

    public void signOut() {
        AuthUI.getInstance().signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent fireBaseUIIntent = new Intent(getBaseContext(), FirebaseUIActivity.class);
                            startActivity(fireBaseUIIntent);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
                            builder.setMessage("Sign out unsuccessful. Please try again.");
                            builder.setTitle("Sign Out Failed");
                        }
                    }
                });
    }
}
