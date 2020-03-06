package com.uwece651.medicationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.UUID;

@SuppressLint("Registered")
public class MedicationDataView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_data_entry);
        ImageView IV_addMedication = findViewById(R.id.addNewMedication);
        IV_addMedication.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                IV_addToDB();
            }});
    }

    public void IV_addToDB() {
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        MedicationData medicationData= new MedicationData(randomUUIDString);
        EditText BrandNameText = (EditText) findViewById(R.id.BrandNameText);
        medicationData.setBrandName(BrandNameText.getText().toString());
        EditText GenericNameText = (EditText) findViewById(R.id.GenericNameText);
        medicationData.setGenericName(GenericNameText.getText().toString());
        EditText UsedForText = (EditText) findViewById(R.id.UsedForText);
        medicationData.setUsedFor(UsedForText.getText().toString());
        EditText InstructionsText = (EditText) findViewById(R.id.InstructionsText);
        medicationData.setInstructions(InstructionsText.getText().toString());
        EditText MissedDoseText = (EditText) findViewById(R.id.MissedDoseText);
        medicationData.setMissedDose(MissedDoseText.getText().toString());
        EditText SideEffectsText = (EditText) findViewById(R.id.SideEffectsText);
        medicationData.setSideEffects(SideEffectsText.getText().toString());
        EditText PrecautionText = (EditText) findViewById(R.id.PrecautionsText);
        medicationData.setPrecautions(PrecautionText.getText().toString());
        EditText InteractionsText = (EditText) findViewById(R.id.InteractionsText);
        medicationData.setDrugInteractions(InteractionsText.getText().toString());
        EditText OverdoseText = (EditText) findViewById(R.id.OverdoseText);
        medicationData.setOverdose(OverdoseText.getText().toString());
        EditText NotesText = (EditText) findViewById(R.id.NotesText);
        medicationData.setNotes(NotesText.getText().toString());
        EditText StorageText = (EditText) findViewById(R.id.StorageText);
        medicationData.setStorage(StorageText.getText().toString());
        EditText ContraindicationsText = (EditText) findViewById(R.id.ContraindicationsText);
        medicationData.setContraindications(ContraindicationsText.getText().toString());
        storeMedicationData(medicationData);
        TextView SuccessMsg = (TextView) findViewById(R.id.textSuccessMsg);
        SuccessMsg.append("New Medication has been saved to DB!");

        /* Clear all */
        BrandNameText.setText("");
        GenericNameText.setText("");
        UsedForText.setText("");
        InstructionsText.setText("");
        MissedDoseText.setText("");
        SideEffectsText.setText("");
        PrecautionText.setText("");
        InteractionsText.setText("");
        OverdoseText.setText("");
        NotesText.setText("");
        StorageText.setText("");
        ContraindicationsText.setText("");
    }

    public void storeMedicationData(MedicationData medData) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference MedicationData = db.collection("MedicationData");
        MedicationData.document(medData.getMedicationID()).set(medData);
    }

    public void clearAll() {

    }

}
