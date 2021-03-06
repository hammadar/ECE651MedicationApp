package com.uwece651.medicationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
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

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatientAccess extends AppCompatActivity {

    private EditText patientId;
    private FirebaseAuth mAuth;
    private GoogleCalendarModule googleCalendarModule;
    private Calendar calendar = Calendar.getInstance();

    //for storing retrieved values
    //for storing retrieved values
    List<String> prescription_ids;
    MedicationSchedule[] schedules;
    PrescriptionData[] prescriptions; //previous three are static for each patient. Items below will change for each prescription - HR
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
    Date startDate;
    Date endDate;
    String dailyFrequencyValue;
    String timeBetweenIntakeValue;
    String currentUID;

    private int numberOfMedications=0;

    List<EditText> medicationNameEditTextList = new ArrayList<EditText>();
    List<CheckBox> dayCheckboxList = new ArrayList<CheckBox>();
    List<Spinner> timesPerDaySpinnerList = new ArrayList<Spinner>();
    List<EditText> timeBetweenIntakeEditTextList = new ArrayList<EditText>();
    List<CalendarView> calendarList = new ArrayList<CalendarView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_patient_access);
        Button signOutButton = findViewById(R.id.signOutButton);
        Button retrieveMyInfoButton = findViewById(R.id.retrieveMyInfo);
        Button calendarButton = findViewById(R.id.addToCalendarButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                signOut();
            }
        });

        googleCalendarModule = new GoogleCalendarModule(getBaseContext(), calendar);

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prescriptions != null && schedules != null) {
                    for (int i = 0; i < prescriptions.length; i++) {

                            Log.d("Cal on Click", "running the " + Integer.toString(i) + " time\n");
                            //Log.d("Cal Sch ID", schedules[i].getScheduleID());
                            Log.d("Cal Pres ID", prescriptions[i].getPrescriptionID());
                            googleCalendarModule.addReminderInCalendar(prescriptions[i], schedules[i]);

                    }
                }
            }
        });
        // Methods to run before page load
        clearPreviousData();
        retrievePatientInfo();
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

    /* TODO migrate code to new view medication page */
    //public void PB_addMedication() {
    //    Intent intent = new Intent(getBaseContext(), MedicationDataView.class);
    //    startActivity(intent);
    // }

    public void displayRetrievedData(){


        TableLayout tl = findViewById(R.id.patientMedDataTableLayout);

        //Medication Name
        TableRow tr = new TableRow(this);

        LinearLayout ll= new LinearLayout(this);
        TextView tv = new TextView(this);
        tv.setText("Medication Name: ");


        EditText et= new EditText(this);
        et.setWidth(500);
        et.setText(medication_Name);
        et.setFocusable(false);
        et.setClickable(false);
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
        cb.setFocusable(false);
        cb.setClickable(false);
        dayCheckboxList.add(cb);


        CheckBox cb1 = new CheckBox(this);
        cb1.setText("M");
        cb1.setGravity(Gravity.CENTER);
        cb1.setChecked(isMondayChecked);
        cb1.setFocusable(false);
        cb1.setClickable(false);
        dayCheckboxList.add(cb1);


        CheckBox cb2 = new CheckBox(this);
        cb2.setText("T");
        cb2.setGravity(Gravity.CENTER);
        cb2.setChecked(isTuesdayChecked);
        cb2.setFocusable(false);
        cb2.setClickable(false);
        dayCheckboxList.add(cb2);


        CheckBox cb3 = new CheckBox(this);
        cb3.setText("W");
        cb3.setGravity(Gravity.CENTER);
        cb3.setChecked(isWednesdayChecked);
        cb3.setFocusable(false);
        cb3.setClickable(false);
        dayCheckboxList.add(cb3);


        CheckBox cb4 = new CheckBox(this);
        cb4.setText("T");
        cb4.setGravity(Gravity.CENTER);
        cb4.setChecked(isThursdayChecked);
        cb4.setFocusable(false);
        cb4.setClickable(false);
        dayCheckboxList.add(cb4);


        CheckBox cb5 = new CheckBox(this);
        cb5.setText("F");
        cb5.setGravity(Gravity.CENTER);
        cb5.setChecked(isFridayChecked);
        cb5.setFocusable(false);
        cb5.setClickable(false);
        dayCheckboxList.add(cb5);


        CheckBox cb6 = new CheckBox(this);
        cb6.setText("S");
        cb6.setGravity(Gravity.CENTER);
        cb6.setChecked(isSaturdayChecked);
        cb6.setFocusable(false);
        cb6.setClickable(false);
        dayCheckboxList.add(cb6);


        //Daily Frequency
        TableRow tr2 = new TableRow(this);
        LinearLayout ll2= new LinearLayout(this);

        TextView tv2 = new TextView(this);
        tv2.setText("Times per day:");

        Spinner dailyFrequencySpinner = new Spinner(this);
        final String[] dailyFrequencyArray = getResources().getStringArray(R.array.medication_daily_frequency);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dailyFrequencyArray);
        dailyFrequencySpinner.setAdapter(arrayAdapter);
        int spinnerposition = arrayAdapter.getPosition(dailyFrequencyValue);
        dailyFrequencySpinner.setSelection(spinnerposition);
        dailyFrequencySpinner.setFocusable(false);
        dailyFrequencySpinner.setClickable(false);
        dailyFrequencySpinner.setEnabled(false);
        timesPerDaySpinnerList.add(dailyFrequencySpinner);



        //Time between intake
        TableRow tr3 = new TableRow(this);
        LinearLayout ll3= new LinearLayout(this);

        TextView tv3 = new TextView(this);
        tv3.setText("Hours between intake: ");

        TextView tv7= new TextView(this);
        tv7.setWidth(150);
        tv7.setText(timeBetweenIntakeValue);
        tv7.setTypeface(null, Typeface.BOLD);

        TableRow tr4 = new TableRow(this);
        LinearLayout ll4= new LinearLayout(this);

        TextView tv4 = new TextView(this);
        tv4.setText("Start Date: ");

        final TextView tv8 = new TextView(this);
        tv8.setWidth(600);
        tv8.setText(new SimpleDateFormat("yyyy-MM-d").format(startDate));
        tv8.setTypeface(null, Typeface.BOLD);

        TableRow tr5 = new TableRow(this);
        LinearLayout ll5 = new LinearLayout(this);

        TextView tv5 = new TextView(this);
        tv5.setText("End Date: ");

        final TextView tv9 = new TextView(this);
        tv9.setWidth(600);
        tv9.setText(new SimpleDateFormat("yyyy-MM-d").format(endDate));
        tv9.setTypeface(null, Typeface.BOLD);

        /*TableRow tr6 = new TableRow(this);
        LinearLayout ll6 = new LinearLayout(this);

        TextView tv10 = new TextView(this);
        tv10.setText("Time Between Doses: ");

        EditText et1= new EditText(this);
        et1.setWidth(150);
        et1.setText(timeBetweenIntakeValue);
        et1.setFocusable(false);
        et1.setClickable(false);
        timeBetweenIntakeEditTextList.add(et1);*/


        //Medication Name
        ll.addView(tv);
        ll.addView(et);
        tr.addView(ll);
        tr.setPadding(0,60,0,0);
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
        ll3.addView(tv7);

        tr3.addView(ll3);
        tl.addView(tr3);

        // Prescription Start Date
        ll4.addView(tv4);
        ll4.addView(tv8);
        tr4.addView(ll4);
        tl.addView(tr4);

        // Prescription End Date
        ll5.addView(tv5);
        ll5.addView(tv9);
        tr5.addView(ll5);
        tl.addView(tr5);

    }

    public void clearPreviousData() {
        TableLayout tl = findViewById(R.id.patientMedDataTableLayout);
        tl.removeAllViews();
    }

    public void retrievePatientInfo(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Log.d("PatientAccess","CurrentUser ID = " + currentUser.getUid());
        retrieveAssociatedPrescriptions(currentUser.getUid());
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
                        prescription_ids = (List<String>) document.get("associatedPrescriptions");//retrievedPatient.getAssociatedPrescriptions();
                        if (prescription_ids != null) {
                            for (int i = 0; i < prescription_ids.size(); i++) {
                                Log.d("prescriptionID", prescription_ids.get(i));
                            }
                            if (prescription_ids.size() != 0) {
                                for (int i = 0; i < prescription_ids.size(); i++) {
                                    Log.d("RAP", "running " + i + " time\n");
                                    getPrescriptionData(prescription_ids.get(i));
                                }
                            }
                        } else {
                            Log.d("Pat. Prescr.", "fetched 0 records ", task.getException());
                            Toast.makeText(getApplicationContext(),"No Prescriptions Found under your ID",Toast.LENGTH_SHORT).show();
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
                        Log.d("GPD", prescription.getPrescriptionID());
                        Log.d("GPD", "running with " + prescriptions[0].getPrescriptionID() + "\n");
                        setClassVariables(prescription);

                    } else {
                        Log.d("Prescrip.", "get failed with ", task.getException());
                    }
                }
            };
        });
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
                        schedules = appArrayHandling.add(schedules, schedule);
                        Log.d("SetVar SchID", schedules[0].getScheduleID());
                        endDate = prescription.getEndDate();
                        startDate = prescription.getStartDate();
                        medication_Name = prescription.getMedicationName();

                        displayRetrievedData();


                    } else {
                        Log.d("Schedule", "get failed with ", task.getException());
                    }
                }
            };



        });
    }
}

