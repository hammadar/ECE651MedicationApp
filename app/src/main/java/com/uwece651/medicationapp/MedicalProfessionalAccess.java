package com.uwece651.medicationapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.DatePicker;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class MedicalProfessionalAccess extends AppCompatActivity {
    // Public Variables


    public static final ArrayList<String> medicationNames = new ArrayList<String>();
    public static final ArrayList<String> medicationIDs = new ArrayList<String>();
    public static final ArrayList<String> patientsOfAssignedDoctor = new ArrayList<String>();
    public static final ArrayList<String> patientsOfAssignedDoctorUID = new ArrayList<String>();



    // Private Variables
    private Button retrievePatientInfoButton;
    private Button addNewMedicationButton;
    private Button savePatientDataButton;
    private Button addNewPatientButton;
    private Spinner patientNameDropdown;
    private FirebaseAuth mAuth;

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
    LocalDate startDate;
    LocalDate endDate;
    String dailyFrequencyValue;
    String timeBetweenIntakeValue;
    String currentUserName="";

    private int numberOfMedications=0;

    List<EditText> medicationNameEditTextList = new ArrayList<EditText>();
    List<CheckBox> dayCheckboxList = new ArrayList<CheckBox>();
    List<Spinner> timesPerDaySpinnerList = new ArrayList<Spinner>();
    List<EditText> timeBetweenIntakeEditTextList = new ArrayList<EditText>();
    List<CalendarView> calendarList = new ArrayList<CalendarView>();
    List<EditText> startDateEditTextList = new ArrayList<EditText>();
    List<EditText> endDateEditTextList = new ArrayList<EditText>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        currentUserName=currentUser.getDisplayName();

        setContentView(R.layout.activity_medical_professional_access);

        retrievePatientInfoButton= findViewById(R.id.retrievePatientInfo);
        addNewMedicationButton= findViewById(R.id.addNewMedication);
        savePatientDataButton= findViewById(R.id.savePatientData);
        addNewPatientButton= findViewById(R.id.addNewPatient);
        Button signOutButton = findViewById(R.id.signOutButton);
        patientNameDropdown=findViewById(R.id.patientId);

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
                try {
                    addNewMedication(v);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        savePatientDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePatientData();
            }
        });

        addNewPatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewPatient();
            }
        });


        signOutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                signOut();
            }
        });

        getMedicationData();
        getAssignedPatients();
    }

    public void getMedicationData(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference medicationDb = db.collection("MedicationData");

        medicationDb.whereGreaterThan("medicationID", "")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()) {

                                                   Integer counter = 0;
                                                   for (QueryDocumentSnapshot document : task.getResult()) {
                                                       medicationNames.add((String) document.get("genericName"));
                                                       medicationIDs.add((String) document.get("medicationID"));
                                                       Log.d("DB", "Record " + counter + ": Name " + medicationNames.get(counter));
                                                       counter++;
                                                   }
                                               } else {
                                                   Log.d("DB", "Error getting documents: ", task.getException());
                                               }
                                           }
                                       }
                );
    }

    public void getAssignedPatients(){
        String DoctorID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference patientDb = db.collection("Patients");
        Log.d("DB", "Doctor ID is " + DoctorID);
        patientDb.whereEqualTo("assignedDoctor", DoctorID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()) {

                                                   for (QueryDocumentSnapshot document : task.getResult()) {
                                                       patientsOfAssignedDoctor.add((String) document.get("name"));
                                                       patientsOfAssignedDoctorUID.add((String) document.get("uid"));
                                                       updatePatientList();
                                                   }
                                                   for(String obj:patientsOfAssignedDoctor)
                                                       Log.d("PatientDB", "Found Patient Name " + obj);
                                               } else {
                                                   Log.d("PatientDB", "Error getting patient list: ", task.getException());
                                               }
                                           }
                                       }
                );
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

        //Start/End date for medication
        TableRow tr4 = new TableRow(this);
        LinearLayout ll4= new LinearLayout(this);

        TextView tv4 = new TextView(this);
        tv4.setText("Start Date:");

        final EditText et2 = new EditText(this);
        et2.setWidth(600);
        et2.setText(startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-d")));
        startDateEditTextList.add(et2);



        et2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                DatePickerDialog startDatePicker;
                startDatePicker = new DatePickerDialog(MedicalProfessionalAccess.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        et2.setText(year + "-" + month + "-" + dayOfMonth);
                    }
                }, year, month, day);
                startDatePicker.show();

            }
        });

        TableRow tr5 = new TableRow(this);
        LinearLayout ll5 = new LinearLayout(this);

        TextView tv5 = new TextView(this);
        tv5.setText("End Date:");

        final EditText et3 = new EditText(this);
        et3.setWidth(600);
        et3.setText(endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-d")));
        endDateEditTextList.add(et3);

        et3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                DatePickerDialog endDatePicker;
                endDatePicker = new DatePickerDialog(MedicalProfessionalAccess.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        et3.setText(year + "-" + month + "-" + dayOfMonth);
                    }
                }, year, month, day);
                endDatePicker.show();

            }
        });

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

            ll4.addView(tv4);
            ll4.addView(et2);
            tr4.addView(ll4);
            tl.addView(tr4);

            ll5.addView(tv5);
            ll5.addView(et3);
            tr5.addView(ll5);
            tl.addView(tr5);



    }

    public void retrievePatientInfo(){
        if (patientNameDropdown.getSelectedItem().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Select a Patient", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            String patient_name = patientNameDropdown.getSelectedItem().toString();
            Log.d("MedicalProfAccess","patient_name= " + patient_name);
            String patient_id=getUID(patient_name);

            Log.d("MedicalProfAccess","patientID = " + patient_id);

            retrieveAssociatedPrescriptions(patient_id);
        }
    }

    public void addNewMedication(View view) throws InterruptedException {

        numberOfMedications++;

        if(prescription_ids == null){
            prescription_ids = new ArrayList<>();
        }

        /* TODO we shouldn't create the prescription ID until we click 'save' */
        /* UUID uuid = UUID.randomUUID();
           String randomUUIDString = uuid.toString(); */

        prescription_ids.add(UUID.randomUUID().toString());
        medication_ids = appArrayHandling.add(medication_ids, UUID.randomUUID().toString());
        schedule_ids = appArrayHandling.add(schedule_ids, UUID.randomUUID().toString());


        TableLayout tl = findViewById(R.id.medicationDataTableLayout);

        //Medication Name
        TableRow tr = new TableRow(this);

        LinearLayout ll= new LinearLayout(this);
        TextView tv = new TextView(this);
        tv.setText("Medication Name:");

        AutoCompleteTextView et= new AutoCompleteTextView(this);
        et.setWidth(500);
        medicationNameEditTextList.add(et);
        // Add autocomplete to the edittext
        //Nothing special, create database reference.

//        Log.d("DB", "ID 1" + medicationIDs[1] + "Name 1" + medicationNames[1]);
//        Log.d("DB", "ID 1" + medicationIDs[2] + "Name 1" + medicationNames[2]);
        //Create a new ArrayAdapter with your context and the simple layout for the dropdown menu provided by Android
        ArrayAdapter<String> autoComplete = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, medicationNames); // , fruits);
        //Child the root before all the push() keys are found and add a ValueEventListener()
        // AutoCompleteTextView actv = new AutoCompleteTextView(this);
        et.setThreshold(1);
        et.setAdapter(autoComplete);



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
        TableRow tr4 = new TableRow(this);
        LinearLayout ll4= new LinearLayout(this);

        TextView tv4 = new TextView(this);
        tv4.setText("Start Date:");

        final EditText et2 = new EditText(this);
        et2.setWidth(600);
        startDateEditTextList.add(et2);



        et2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                DatePickerDialog startDatePicker;
                startDatePicker = new DatePickerDialog(MedicalProfessionalAccess.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        et2.setText(year + "-" + month + "-" + dayOfMonth);
                    }
                }, year, month, day);
                startDatePicker.show();

            }
        });

        TableRow tr5 = new TableRow(this);
        LinearLayout ll5 = new LinearLayout(this);

        TextView tv5 = new TextView(this);
        tv5.setText("End Date:");

        final EditText et3 = new EditText(this);
        et3.setWidth(600);
        endDateEditTextList.add(et3);

        et3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                DatePickerDialog endDatePicker;
                endDatePicker = new DatePickerDialog(MedicalProfessionalAccess.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        et3.setText(year + "-" + month + "-" + dayOfMonth);
                    }
                }, year, month, day);
                endDatePicker.show();

            }
        });

        //CalendarView calendar = new CalendarView(this);



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

        ll4.addView(tv4);
        ll4.addView(et2);
        tr4.addView(ll4);
        tl.addView(tr4);

        ll5.addView(tv5);
        ll5.addView(et3);
        tr5.addView(ll5);
        tl.addView(tr5);

    }

    public void savePatientData(){

        String patient_name = patientNameDropdown.getSelectedItem().toString();
        String patient_Id=getUID(patient_name);

        //Need to get UID based on name


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
        LocalDate startDate;
        LocalDate endDate;


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

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
            startDate = LocalDate.parse(startDateEditTextList.get(i).getText(), formatter);
            endDate = LocalDate.parse(endDateEditTextList.get(i).getText(), formatter);

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

            MedicationData medData = new MedicationData(getMedicationID(medication_Name));

            Log.d("Med ID", getMedicationID(medication_Name));
            medData.setBrandName(medication_Name);
            storeMedicationData(medData);

            MedicationSchedule medSchedule = new MedicationSchedule(schedule_ids[i]);
            Log.d("schedule_id: ", schedule_ids[i]);
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
            Log.d("prescription_id: ", prescription_ids.get(i));
            prescriptionData.setMedicationID(medData.getMedicationID());
            prescriptionData.setScheduleID(medSchedule.getScheduleID());
            prescriptionData.setMedicationName(medication_Name);
            prescriptionData.setStartDate(startDate);
            prescriptionData.setEndDate(endDate);


            storePrescriptionData(prescriptionData);

            savePatientPrescriptions(patient_Id);

            //TODO Store medication/schedule ids in array in prescriptionData object. Done - HR

        }

    }

    public void storeMedicationData(MedicationData medData) {
        //FirebaseFirestore db = FirebaseFirestore.getInstance();

        //CollectionReference MedicationData = db.collection("MedicationData");
        //MedicationData.document(medData.getMedicationID()).set(medData);
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



                        if (prescription_ids != null) {
                            for (int i = 0; i < prescription_ids.size(); i++) {
                                Log.d("prescriptionID", prescription_ids.get(i));
                            }

                            prescriptions = new PrescriptionData[prescription_ids.size()];
                            for (int i = 0; i < prescription_ids.size(); i++) {
                                Log.d("RAP", "running " + i + " time\n");
                                getPrescriptionData(prescription_ids.get(i));
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "No prescriptions saved", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getApplicationContext(), "No prescriptions available for that patient.", Toast.LENGTH_SHORT).show();
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

    public void setClassVariables(final PrescriptionData prescription) {
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
                        endDate = prescription.getEndDate();
                        startDate = prescription.getStartDate();
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

    public void updatePatientList(){
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, patientsOfAssignedDoctor);
        patientNameDropdown.setAdapter(arrayAdapter);
    }

    public void addNewPatient() {

        Log.d("Registration", "CurrentUser display name = " + currentUserName);
    }

    public String getUID(String name){
        for (int i =0; i < patientsOfAssignedDoctor.size(); i++){
            int isMatch = name.compareToIgnoreCase(patientsOfAssignedDoctor.get(i));
            if(isMatch == 0){
                return patientsOfAssignedDoctorUID.get(i);
            }
        }

        return "";
    }

    public String getMedicationID(String medication_name){
        for (int i =0; i < medicationNames.size(); i++){
            int isMatch = medicationNames.get(i).compareToIgnoreCase(medication_name);
            if(isMatch == 0){
                return medicationIDs.get(i);
            }
        }
        return "";
    }

}


