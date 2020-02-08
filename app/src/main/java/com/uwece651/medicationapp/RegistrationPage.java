package com.uwece651.medicationapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistrationPage extends AppCompatActivity {
    private boolean isDoctorRole=false;
    private Button submitButton;
    private TextView userNameTextView;
    private RadioButton patientButton;
    private RadioButton professionalButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        submitButton= (Button) findViewById(R.id.submitRegistrationInfo);
        userNameTextView = findViewById(R.id.userDisplayNameText);
        patientButton = findViewById(R.id.radio_patient);
        patientButton.toggle();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userNameTextView.setText(user.getDisplayName());

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRegistrationInfo();
            }
        });

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_med_prof:
                if (checked) {
                    isDoctorRole = true;
                    break;
                }
            case R.id.radio_patient:
                if (checked) {
                    isDoctorRole = false;
                    break;
                }
        }
        Log.d("RegistrationActivity","isDoctorRole = " + isDoctorRole);
    }

    public void submitRegistrationInfo() {
        /*if (nameData.getText().toString().isEmpty() || emailData.getText().toString().isEmpty() || passwordData.getText().toString().isEmpty() ) {
            Toast.makeText(getApplicationContext(), "Enter the Data", Toast.LENGTH_SHORT).show();
        } else {
            //TODO: Hook up database set with data AND user role to set priviliges
            Toast.makeText(getApplicationContext(), "Name -  " + nameData.getText().toString() + " \n" + "Password -  " + passwordData.getText().toString()
                    + " \n" + "E-Mail -  " + emailData.getText().toString() + " \n" + "IsDoctor = " + isDoctorRole, Toast.LENGTH_SHORT).show();
        }*/
        String type;
        PersonalInformation user;
        RadioButton patientButton = findViewById(R.id.radio_patient);
        if (patientButton.isChecked()) { type = "Patient"; } else { type = "Doctor";}

        user = new PersonalInformation(FirebaseAuth.getInstance().getCurrentUser().getUid());
        user.setName(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        user.setType(type);
        registerUser(user);

    }



    public void registerUser(PersonalInformation user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference Users = db.collection("Users");
        Users.document(user.getUID()).set(user);
        goToSignedInActivity(user.getType());
    }

    public void goToSignedInActivity (String type) {

    }



}


