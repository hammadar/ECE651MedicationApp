package com.uwece651.medicationapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button medicalProfessionalAccessButton;
    private Button patientAccessButton;
    private TextView registrationLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        medicalProfessionalAccessButton= findViewById(R.id.medicalProfessionalAccessButton);
        patientAccessButton= findViewById(R.id.patientAccessButton);
        registrationLink= findViewById(R.id.registrationLink);

        medicalProfessionalAccessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMedicalProfessionalAccessPage();
            }
        });

        patientAccessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPatientAccessPage();
            }
        });

        registrationLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegistrationPage();
            }
        });
    }

    public void openMedicalProfessionalAccessPage() {
        Intent intent = new Intent(this, MedicalProfessionalAccess.class);
        startActivity(intent);
    }

    public void openPatientAccessPage() {
        Intent intent = new Intent(this, PatientAccess.class);
        startActivity(intent);
    }

    public void openRegistrationPage() {
        Intent intent = new Intent(this, RegistrationPage.class);
        startActivity(intent);
    }
}
