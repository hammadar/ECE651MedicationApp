package com.uwece651.medicationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
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

//import java.time.LocalDate;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Semaphore;

import java.text.SimpleDateFormat;

import static java.lang.Thread.sleep;

public class MedicalProfessionalAccess extends AppCompatActivity {
    // Public Variables


    public static final ArrayList<String> medicationNames = new ArrayList<String>();
    public static final ArrayList<String> medicationIDs = new ArrayList<String>();
    public static final ArrayList<String> patientsOfAssignedDoctor = new ArrayList<String>();
    public static final ArrayList<String> patientsOfAssignedDoctorUID = new ArrayList<String>();
    public static final ArrayList<String> unassignedPatients = new ArrayList<String>();
    public static final ArrayList<String> unassignedPatientsUID = new ArrayList<String>();



    // Private Variables
    private Button retrievePatientInfoButton;
    private Button addNewMedicationButton;
    private Button savePatientDataButton;
    private Button addNewPatientButton;
    private Spinner patientNameDropdown;
    private Spinner newPatientNameDropdown;
    private Button addPatientButton;
    private TextView signOutButton;
    private FirebaseAuth mAuth;

    //for storing retrieved values
    List<String> prescription_ids;
    PrescriptionData[] prescriptions; //previous two are static for each patient. Items below will change for each prescription - HR
    List<String> medication_ids;
    List<String> schedule_ids;
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
    String currentUserName="";
    int prescriptionDBSize;

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
        assert currentUser != null;
        currentUserName=currentUser.getDisplayName();

        setContentView(R.layout.activity_medical_professional_access);

        retrievePatientInfoButton= findViewById(R.id.retrievePatientInfo);
        addNewMedicationButton= findViewById(R.id.addNewMedication);
        savePatientDataButton= findViewById(R.id.savePatientData);
        addNewPatientButton= findViewById(R.id.addNewPatient);
        signOutButton = findViewById(R.id.signOutButton);
        patientNameDropdown=findViewById(R.id.patientId);
        newPatientNameDropdown=findViewById(R.id.newpatientID);
        addPatientButton=findViewById(R.id.addPatient);

        retrievePatientInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearPreviousData();
                retrievePatientInfo();
                addNewMedicationButton.setVisibility(View.VISIBLE);


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
                try {
                    savePatientData();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        addNewPatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewPatient();
            }
        });

        addPatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPatient();
            }
        });

        patientNameDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                clearPreviousData();
                newPatientNameDropdown.setVisibility(View.INVISIBLE);
                addPatientButton.setVisibility(View.INVISIBLE);
                addNewMedicationButton.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(getApplicationContext(), "Patient was not Selected", Toast.LENGTH_SHORT).show();
            }

        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                signOut();
            }
        });

        signOutButton.bringToFront();

        getMedicationData();
        getAssignedPatients();
        getUnassignedPatients();
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
                                                   medicationNames.clear();
                                                   medicationIDs.clear();
                                                   int counter = 0;
                                                   for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                                       medicationNames.add((String) document.get("brandName"));
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
        Log.d("PatientDB", "Doctor ID is " + DoctorID);
        patientDb.whereEqualTo("assignedDoctor", DoctorID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()) {
                                                   patientsOfAssignedDoctor.clear();
                                                   patientsOfAssignedDoctorUID.clear();
                                                   for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                                       Object patient_name = document.get("name");
                                                       if (patient_name != null) {
                                                           patientsOfAssignedDoctor.add((String) document.get("name"));
                                                           patientsOfAssignedDoctorUID.add((String) document.get("uid"));
                                                       }
                                                   }
                                                   updatePatientList();
                                                   for(String obj:patientsOfAssignedDoctor)
                                                       Log.d("PatientDB", "Found Patient Name " + obj);
                                               } else {
                                                   Log.d("PatientDB", "Error getting patient list: ", task.getException());
                                               }
                                           }
                                       }
                );
    }

    public void getUnassignedPatients(){
        String DoctorID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference patientDb = db.collection("Patients");
        Log.d("UnassignedDB", "Doctor ID is " + DoctorID);
        patientDb.whereEqualTo("assignedDoctor", null)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()) {
                                                   unassignedPatients.clear();
                                                   unassignedPatientsUID.clear();
                                                   for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                                       Object patient_name = document.get("name");
                                                       if (patient_name != null) {
                                                           Log.d("UnassignedDB", "Found non-null Named unassigned user.");
                                                           unassignedPatients.add((String) document.get("name"));
                                                           unassignedPatientsUID.add((String) document.get("uid"));
                                                       }
                                                   }
                                                   updateNewPatientList();
                                                   for(String obj:unassignedPatients)
                                                       Log.d("UnassignedDB", "Found Patient Name " + obj);
                                               } else {
                                                   Log.d("UnassignedDB", "Error getting patient list: ", task.getException());
                                               }
                                           }
                                       }
                );
    }

    public void displayRetrievedData(){




        TableLayout tl = findViewById(R.id.medicationDataTableLayout);

        //Medication Name
        TableRow tr = new TableRow(this);

        LinearLayout ll= new LinearLayout(this);
        TextView tv = new TextView(this);
        tv.setText("Medication Name:");

        AutoCompleteTextView et= new AutoCompleteTextView(this);
        et.setWidth(500);
        et.setText(medication_Name);
        medicationNameEditTextList.add(et);

        // Add autocomplete to the edittext
        ArrayAdapter<String> autoComplete = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, medicationNames); // , fruits);
        //Child the root before all the push() keys are found and add a ValueEventListener()
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
        et2.setText(new SimpleDateFormat("yyyy-MM-d").format(startDate));
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
                        String month_str;
                        String day_str;
                        if (month < 9) {
                            month_str = "0"+ Integer.toString(month+1);
                        } else {
                            month_str = Integer.toString(month+1);
                        }

                        if (dayOfMonth < 9) {
                            day_str = "0" + Integer.toString(dayOfMonth);
                        } else {
                            day_str = Integer.toString(dayOfMonth);
                        }
                        et2.setText(year + "-" + month_str + "-" + day_str);
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
        et3.setText((new SimpleDateFormat("yyyy-MM-d").format(endDate)));
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
                        String month_str;
                        String day_str;
                        if (month < 9) {
                            month_str = "0"+ Integer.toString(month+1);
                        } else {
                            month_str = Integer.toString(month+1);
                        }

                        if (dayOfMonth < 9) {
                            day_str = "0" + Integer.toString(dayOfMonth);
                        } else {
                            day_str = Integer.toString(dayOfMonth);
                        }
                        et3.setText(year + "-" + month_str + "-" + day_str);
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

            // Prescription Start Date
            ll4.addView(tv4);
            ll4.addView(et2);
            tr4.addView(ll4);
            tl.addView(tr4);

            // Prescription End Date
            ll5.addView(tv5);
            ll5.addView(et3);
            tr5.addView(ll5);
            tl.addView(tr5);

    }

    public void clearPreviousData() {
        TableLayout tl = findViewById(R.id.medicationDataTableLayout);
        tl.removeAllViews();
        medicationNameEditTextList.clear();
        dayCheckboxList.clear();
        timesPerDaySpinnerList.clear();
        timeBetweenIntakeEditTextList.clear();
        calendarList.clear();
        endDateEditTextList.clear();
        startDateEditTextList.clear();
    }

    public void retrievePatientInfo(){
        if (patientNameDropdown.getSelectedItem().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Select a Patient", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            String patient_name = patientNameDropdown.getSelectedItem().toString();
            String patient_id=getUID(patient_name);
            Log.d("MedicalProfAccess","patient_name= " + patient_name);
            Log.d("MedicalProfAccess","patientID = " + patient_id);
            retrieveAssociatedPrescriptions(patient_id);
        }
    }

    public void addNewMedication(View view) throws InterruptedException {

        /* TODO we shouldn't create the prescription ID until we click 'save' */

        //prescription_ids.add(UUID.randomUUID().toString());
        //medication_ids = appArrayHandling.add(medication_ids, RandomGenerator.randomGenerator(20));
        //schedule_ids = appArrayHandling.add(schedule_ids, RandomGenerator.randomGenerator(20));


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
        ArrayAdapter<String> autoComplete = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, medicationNames); // , fruits);
        //Child the root before all the push() keys are found and add a ValueEventListener()
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
                        String month_str;
                        String day_str;
                        if (month < 9) {
                            month_str = "0"+ Integer.toString(month+1);
                        } else {
                            month_str = Integer.toString(month+1);
                        }

                        if (dayOfMonth < 10) {
                            day_str = "0" + Integer.toString(dayOfMonth);
                        } else {
                            day_str = Integer.toString(dayOfMonth);
                        }
                        et2.setText(year + "-" + month_str + "-" + day_str);
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
                        String month_str;
                        String day_str;
                        if (month < 9) {
                            month_str = "0"+ Integer.toString(month+1);
                        } else {
                            month_str = Integer.toString(month+1);
                        }

                        if (dayOfMonth < 10) {
                            day_str = "0" + Integer.toString(dayOfMonth);
                        } else {
                            day_str = Integer.toString(dayOfMonth);
                        }
                        et3.setText(year + "-" + month_str + "-" + day_str);
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

        // Calendar Start Date
        ll4.addView(tv4);
        ll4.addView(et2);
        tr4.addView(ll4);
        tl.addView(tr4);

        // Calendar End Date
        ll5.addView(tv5);
        ll5.addView(et3);
        tr5.addView(ll5);
        tl.addView(tr5);

    }

    public void savePatientData() throws ParseException {

        String patient_name = patientNameDropdown.getSelectedItem().toString();
        if (patient_name.isEmpty()) {
            Toast.makeText(getApplicationContext(), "The selected Patient is not valid", Toast.LENGTH_SHORT).show();
            return;
        }
        String patient_Id=getUID(patient_name);
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
        Date startDate;
        Date endDate;

        boolean saveError = false;

        for (int i = 0; i < medicationNameEditTextList.size(); i++) {
            if (prescription_ids == null) {
                prescription_ids = new ArrayList<>();
            }
            medication_Name = medicationNameEditTextList.get(i).getText().toString();
            if (medication_Name.isEmpty() || medication_Name.equals(" ")) {
                Toast.makeText(getApplicationContext(), "You must enter a medication name.", Toast.LENGTH_SHORT).show();
                saveError = true;
                continue;
            }
            String MedicationID = getMedicationID(medication_Name);
            if (MedicationID == null || MedicationID.equals("")) {
                // If the MedicationID is not found for the medication name, do not save the record and exit from the loop.
                Toast.makeText(getApplicationContext(), "Medication " + medication_Name + " was not found in the Database. Correct and try again.", Toast.LENGTH_LONG).show();
                saveError = true;
                continue;
            }
            Log.d("Med ID", MedicationID);

            isSundayChecked=dayCheckboxList.get(i*7).isChecked();
            isMondayChecked=dayCheckboxList.get(i*7 +1).isChecked();
            isTuesdayChecked=dayCheckboxList.get(i*7 + 2).isChecked();
            isWednesdayChecked=dayCheckboxList.get(i*7 + 3).isChecked();
            isThursdayChecked=dayCheckboxList.get(i*7 + 4).isChecked();
            isFridayChecked=dayCheckboxList.get(i*7 + 5).isChecked();
            isSaturdayChecked=dayCheckboxList.get(i*7 + 6).isChecked();

            if (!isSundayChecked && !isMondayChecked && !isTuesdayChecked && !isWednesdayChecked && !isThursdayChecked && !isFridayChecked && !isSaturdayChecked) {
                Toast.makeText(getApplicationContext(), "Please select at least one day of the week to schedule.", Toast.LENGTH_LONG).show();
                saveError = true;
                continue;
            }

            dailyFrequencyValue=timesPerDaySpinnerList.get(i).getSelectedItem().toString();
            if (dailyFrequencyValue.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please indicate the medication frequency per day.", Toast.LENGTH_LONG).show();
                saveError = true;
                continue;
            }
            timeBetweenIntakeValue=timeBetweenIntakeEditTextList.get(i).getText().toString();
            if (timeBetweenIntakeValue.isEmpty() && !dailyFrequencyValue.equals("1")) {
                Toast.makeText(getApplicationContext(), "Please indicate the time between medications as there are " + dailyFrequencyValue + ".", Toast.LENGTH_LONG).show();
                saveError = true;
                continue;
            }
            Date today = new Date();
            today = setTime(today, 0, 0, 0, 0);
            String startDateText = startDateEditTextList.get(i).getText().toString();
            if (startDateText.equals("")) {
                Toast.makeText(getApplicationContext(), "Prescription start date is a required field.", Toast.LENGTH_LONG).show();
                saveError = true;
                continue;
            }
            startDate = new  SimpleDateFormat("yyyy-MM-d").parse(startDateText);
            if (i >= prescriptionDBSize) {
                assert startDate != null;
                //Log.d("DATE", today.toString() + " vs " + startDate.toString());
                if (startDate.compareTo(today) < 0 ){
                    Toast.makeText(getApplicationContext(), "The start date for a new prescription cannot be earlier than today.", Toast.LENGTH_LONG).show();
                    saveError = true;
                    continue;
                }
            }
            String endDateText = endDateEditTextList.get(i).getText().toString();
            if (endDateText.equals("")) {
                Toast.makeText(getApplicationContext(), "Prescription end date is a required field.", Toast.LENGTH_LONG).show();
                saveError = true;
                continue;
            }
            endDate = new  SimpleDateFormat("yyyy-MM-d").parse(endDateText);
            assert endDate != null;
            if (endDate.compareTo(startDate) < 0) {
                Toast.makeText(getApplicationContext(), "The end date must be later than the start date.", Toast.LENGTH_LONG).show();
                saveError = true;
                continue;
            }

            if (i >= prescriptionDBSize) {
                prescription_ids.add(UUID.randomUUID().toString());
                medication_ids.add(UUID.randomUUID().toString());
                schedule_ids.add(UUID.randomUUID().toString());
            }

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


            MedicationSchedule medSchedule = new MedicationSchedule(schedule_ids.get(i));
            Log.d("schedule_id: ", schedule_ids.get(i));
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
            prescriptionData.setMedicationID(MedicationID);
            prescriptionData.setScheduleID(medSchedule.getScheduleID());
            prescriptionData.setMedicationName(medication_Name);
            prescriptionData.setStartDate(startDate);
            prescriptionData.setEndDate(endDate);

            storePrescriptionData(prescriptionData);
            savePatientPrescriptions(patient_Id);

        }
        // Check if nothing fetched
        if (medicationNameEditTextList.size() == 0) {
            Toast.makeText(getApplicationContext(), "No Prescriptions Loaded", Toast.LENGTH_SHORT).show();
            saveError = true;
        }
        if (! saveError) {
            Toast.makeText(getApplicationContext(), "Prescriptions Saved", Toast.LENGTH_SHORT).show();
            prescriptionDBSize = prescription_ids.size();
        }

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
        prescription_ids = new ArrayList<>();
        medication_ids = new ArrayList<>();
        schedule_ids = new ArrayList<>();

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    if (document.exists()) {
                        prescription_ids = (List<String>)document.get("associatedPrescriptions");//retrievedPatient.getAssociatedPrescriptions();

                        if (prescription_ids != null) {
                            prescriptionDBSize = prescription_ids.size();
                            for (int i = 0; i < prescriptionDBSize; i++) {
                                Log.d("prescriptionID", prescription_ids.get(i));
                            }

                            prescriptions = new PrescriptionData[prescription_ids.size()];
                            for (int i = 0; i < prescriptionDBSize; i++) {
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
                        Toast.makeText(getApplicationContext(), "A prescription assigned to that patient is no longer in the DB.", Toast.LENGTH_SHORT).show();
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
        Log.d("GPD", "ID: " + medication_id + " - Name: "+ medication_Name);

        if(medication_ids == null){
            medication_ids = new ArrayList<>();
        }
        if(schedule_ids == null){
            schedule_ids = new ArrayList<>();
        }
        medication_ids.add(medication_id);
        schedule_ids.add(schedule_id);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference scheduleDb = db.collection("MedicationSchedule");
        DocumentReference docRef = scheduleDb.document(schedule_id);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        medication_Name = prescription.getMedicationName();
                        medication_Name = prescription.getMedicationName();
                        startDate = prescription.getStartDate();
                        endDate = prescription.getEndDate();
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

    public void updatePatientWithDoctorID(String PatientID, String DoctorID) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference patientsDb = db.collection("Patients");
        DocumentReference docRef = patientsDb.document(PatientID);
        docRef.update("assignedDoctor", DoctorID);
        // remove name and UID from the array and update it back to the UI
    }

    public void updatePatientList(){
        patientNameDropdown.setAdapter(null);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, patientsOfAssignedDoctor);
        patientNameDropdown.setAdapter(arrayAdapter);
    }

    public void updateNewPatientList(){
        newPatientNameDropdown.setAdapter(null);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, unassignedPatients);
        newPatientNameDropdown.setAdapter(arrayAdapter);
    }

    public void addNewPatient() {
        newPatientNameDropdown.setVisibility(View.VISIBLE);
        addPatientButton.setVisibility(View.VISIBLE);
        Log.d("UI", "Displaying Add Patient Fields");
    }

    public void addPatient() {
        // update patient ID with current assigned doctor id.
        if (newPatientNameDropdown.getSelectedItem().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Select a Patient", Toast.LENGTH_SHORT).show();
        } else {
            String NewPatientName = newPatientNameDropdown.getSelectedItem().toString();
            String NewPatientID = getUnassignedUID(NewPatientName);
            String DoctorID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
            Log.d("patientDB", "Assigning Doctor" + DoctorID + "to patient" + NewPatientID);
            updatePatientWithDoctorID(NewPatientID, DoctorID);
            removeFromUnassignedPatientList(NewPatientName);
            removeFromUnassignedPatientUIDList(NewPatientID);
            AddToPatientUIDList(NewPatientID);
            AddToPatientList(NewPatientName);
            // Update the doctor's dropdown with the new patient.
            updatePatientList();
            updateNewPatientList();
            newPatientNameDropdown.setVisibility(View.INVISIBLE);
            addPatientButton.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), NewPatientName + " is now assigned as your patient.", Toast.LENGTH_SHORT).show();
        }
    }

    public String getUnassignedUID(String name){
        for (int i =0; i < unassignedPatients.size(); i++){
            int isMatch = name.compareToIgnoreCase(unassignedPatients.get(i));
            if(isMatch == 0){
                return unassignedPatientsUID.get(i);
            }
        }

        return "";
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

    public void removeFromUnassignedPatientList(String elem) {
        for (int i =0; i < unassignedPatients.size(); i++){
            int isMatch = unassignedPatients.get(i).compareToIgnoreCase(elem);
            if(isMatch == 0) {
                unassignedPatients.remove(i);
            }
        }
    }

    public void removeFromUnassignedPatientUIDList(String elem) {
        for (int i =0; i < unassignedPatientsUID.size(); i++){
            int isMatch = unassignedPatientsUID.get(i).compareToIgnoreCase(elem);
            if(isMatch == 0) {
                unassignedPatientsUID.remove(i);
            }
        }
    }

    public void AddToPatientUIDList(String elem) {
                patientsOfAssignedDoctorUID.add(elem);
    }

    public void AddToPatientList(String elem) {
                patientsOfAssignedDoctor.add(elem);
    }

    public static Date setTime( final Date date, final int hourOfDay, final int minute, final int second, final int ms )
    {
        final GregorianCalendar gc = new GregorianCalendar();
        gc.setTime( date );
        gc.set( Calendar.HOUR_OF_DAY, hourOfDay );
        gc.set( Calendar.MINUTE, minute );
        gc.set( Calendar.SECOND, second );
        gc.set( Calendar.MILLISECOND, ms );
        return gc.getTime();
    }
}


