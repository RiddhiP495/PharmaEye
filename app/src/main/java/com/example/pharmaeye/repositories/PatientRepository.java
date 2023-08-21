package com.example.pharmaeye.repositories;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.pharmaeye.models.Patient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientRepository {

    /**
     * Repository classes are responsible for the following tasks:
     *
     * Exposing data to the rest of the app.
     * Centralizing changes to the data.
     * Resolving conflicts between multiple data sources.
     * Abstracting sources of data from the rest of the app.
     * Containing business logic.
     */

    // Access a Cloud Firestore instance
    private final FirebaseFirestore DB;

    private final String COLLECTION_PATIENT = "patients";
    private final String COLLECTION_EMPLOYEE = "employees";

    private final String FIELD_NAME = "name";
    private final String FIELD_EMAIL = "email";
    private final String FIELD_GENDER = "gender";
    private final String FIELD_DOB = "date_of_birth";
    private final String FIELD_PHONE = "phone_number";
    private final String FIELD_ADDRESS = "address";
    private final String FIELD_CITY = "city";
    private final String FIELD_PROVINCE = "province";
    private final String FIELD_HEALTH_CARD = "health_card_number";


    public String loggedInEmployeeEmail = "pharmaem@email.com";

    public PatientRepository() {
        DB = FirebaseFirestore.getInstance();
    }

    public void addPatient(Patient patientToAdd){
        try{
            Map<String, Object> data = new HashMap<>();
            data.put(FIELD_NAME, patientToAdd.getName());
            data.put(FIELD_EMAIL, patientToAdd.getEmail());
            data.put(FIELD_GENDER, patientToAdd.getGender());
            data.put(FIELD_DOB, patientToAdd.getDOB());
            data.put(FIELD_PHONE, patientToAdd.getPhoneNumber());
            data.put(FIELD_ADDRESS, patientToAdd.getPostalAddress());
            data.put(FIELD_CITY, patientToAdd.getCity());
            data.put(FIELD_PROVINCE, patientToAdd.getCity());
            data.put(FIELD_HEALTH_CARD, patientToAdd.getHealthCardNumber());

            //option - 2
            // add():- create a new document with randomly generated ID
            DB.collection(COLLECTION_EMPLOYEE)
                    .document(loggedInEmployeeEmail)
                    .collection(COLLECTION_PATIENT)
                    .add(data)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.e("TAG", "onSuccess: Document successfully created with ID "
                                    + documentReference.getId() );
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("TAG", "onFailure: Error while creating document" + e.getLocalizedMessage() );
                }
            });

        }catch (Exception ex){
            Log.e("TAG", "addPatient: " + ex.getLocalizedMessage() );
        }

    }

}
