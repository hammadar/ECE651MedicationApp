package com.uwece651.medicationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class PatientAccess extends AppCompatActivity {

    private EditText patientId;
    private FirebaseAuth mAuth;

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
    String currentUserName;

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
        signOutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                signOut();
            }
        });
        retrieveMyInfoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                retrievePatientInfo();
            }
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

    public void displayRetrievedData(){


        //TODO for loop  number of prescriptions in patient class?

        TableLayout tl = findViewById(R.id.patientMedDataTableLayout);

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

    public void retrievePatientInfo(){

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            Log.d("PatientAccess", "currentUser.getUid() = " + currentUser.getUid());
            CollectionReference Users = db.collection("Users");
            DocumentReference docRef = Users.document(currentUser.getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            PersonalInformation user = document.toObject(PersonalInformation.class);
                            currentUserName = user.getName().toString().toLowerCase();

                            Log.d("PatientAccess","currentUserName = " + currentUserName);

                            FirebaseFirestore db = FirebaseFirestore.getInstance();

                            CollectionReference PrescriptionDataDB = db.collection("PrescriptionData");

                            DocumentReference docRef= PrescriptionDataDB.document(currentUserName);
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        PrescriptionData prescription_data = document.toObject(PrescriptionData.class);
                                        medication_id=prescription_data.getMedicationID();
                                        schedule_id=prescription_data.getScheduleID();

                                        Log.d("PatientAccess","medication_id = " + medication_id);
                                        Log.d("PatientAccess","schedule_id = " + schedule_id);

                                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                                        CollectionReference MedicationDataDB = db.collection("MedicationData");

                                        DocumentReference docRef= MedicationDataDB.document(medication_id);
                                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot document = task.getResult();
                                                if (document.exists()) {
                                                    MedicationData medication_data = document.toObject(MedicationData.class);
                                                    medication_Name=medication_data.getBrandName();

                                                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                                                    CollectionReference MedicationScheduleDB = db.collection("MedicationSchedule");

                                                    DocumentReference docRef= MedicationScheduleDB.document(schedule_id);
                                                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot document = task.getResult();
                                                            if (document.exists()) {
                                                                MedicationSchedule schedule_data = document.toObject(MedicationSchedule.class);
                                                                isMondayChecked=schedule_data.getMondayChecked();
                                                                isTuesdayChecked=schedule_data.getTuesdayChecked();
                                                                isWednesdayChecked=schedule_data.getWednesdayChecked();
                                                                isThursdayChecked=schedule_data.getThursdayChecked();
                                                                isFridayChecked=schedule_data.getFridayChecked();
                                                                isSaturdayChecked=schedule_data.getSaturdayChecked();
                                                                isSundayChecked=schedule_data.getSundayChecked();
                                                                dailyFrequencyValue=schedule_data.getDailyFrequency();
                                                                timeBetweenIntakeValue=schedule_data.getHoursFrequency();

                                                                displayRetrievedData();
                                                            }
                                                        } else {
                                                            Log.d("PatientAccess", "get failed with ", task.getException());
                                                        }
                                                        }
                                                    });
                                                }
                                            } else {
                                                Log.d("PatientAccess", "get failed with ", task.getException());
                                            }
                                            }
                                        });

                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Invalid Patient ID", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } else {
                                    Log.d("PatientAccess", "get failed with ", task.getException());
                                }
                                }
                            });
                        } else {
                            Intent registerIntent = new Intent(getBaseContext(), RegistrationPage.class);
                            startActivity(registerIntent);
                        }
                    } else {
                        Log.d("PatientAccess", "2 get failed with ", task.getException());
                    }
                }
            });
        }
    }
}

