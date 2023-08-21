package com.example.pharmaeye.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.pharmaeye.models.Prescription;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrescriptionRepository {
    private final String TAG = this.getClass().getCanonicalName();
    private final FirebaseFirestore DB;
    private final String COLLECTION_PATIENT = "patients";
    private final String COLLECTION_EMPLOYEE = "employees";
    private final String COLLECTION_PRESCRIPTION = "prescription";

    private final String FIELD_DRUGNAME = "drugName";
    private final String FIELD_QUANTITY = "quantity";
    private final String FIELD_DUEDATE = "dueDate";

    public String loggedInEmployeeEmail = "pharmaem@email.com";
    public MutableLiveData<List<Prescription>> allPrescription = new MutableLiveData<>();

    public PrescriptionRepository(){
        DB = FirebaseFirestore.getInstance();
    }

    //TODO: ADD prescription to specific patient
    //TODO-- Change hierarchy as per data saved
    public void addPrescription(Prescription prescriptionToAdd){
        try{
            Map<String, Object> data = new HashMap<>();
            data.put(FIELD_DRUGNAME,prescriptionToAdd.getDrugName());
            data.put(FIELD_QUANTITY,prescriptionToAdd.getQuantity());
            data.put(FIELD_DUEDATE,prescriptionToAdd.getDueBy());



            DB.collection(COLLECTION_EMPLOYEE)
                    .document(loggedInEmployeeEmail)
                    .collection(COLLECTION_PATIENT)
                    .document()
                    .collection(COLLECTION_PRESCRIPTION)
                    .add(data)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.e(TAG,"onSuccess: Document successfully created with ID" + documentReference.getId());

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("TAG", "onFailure: Error while creating document" + e.getLocalizedMessage() );
                        }
                    });
        }catch (Exception ex){
            Log.e("TAG", "addPrescription: " + ex.getLocalizedMessage());

        }
    }

    //TODO-- Change hierarchy as per data saved
    public void getPrescription(){
        try{
            DB.collection(COLLECTION_EMPLOYEE)
                    .document(loggedInEmployeeEmail)
                    .collection(COLLECTION_PATIENT)
                    .document()
                    .collection(COLLECTION_PRESCRIPTION)
                    .orderBy(FIELD_DRUGNAME, Query.Direction.ASCENDING)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<Prescription> prescriptionList = new ArrayList<>();
                            if(queryDocumentSnapshots.isEmpty()){
                                Log.e(TAG,"onSuccess: No data retrieved");

                            }else{
                                Log.e(TAG, "onSuccess: queryDocumentSnapshots" + queryDocumentSnapshots.getDocumentChanges() );
                                for(DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()){
                                    Prescription currentPrescription = documentChange.getDocument().toObject(Prescription.class);
                                    currentPrescription.setId(documentChange.getDocument().getId());
                                    prescriptionList.add(currentPrescription);
                                    Log.e(TAG,"onSuccess: Current Prescription " + currentPrescription.toString());
                                }
                                allPrescription.postValue(prescriptionList);
                            }
                        }
                    });
        }catch (Exception ex){
            Log.e(TAG, "getAllPrescriptions : " + ex.getLocalizedMessage());
        }
    }

    //TODO-- Change hierarchy as per data saved
    public void updatePrescription(Prescription prescriptionToUpdate) {

        Map<String, Object> data = new HashMap<>();
        data.put(FIELD_DRUGNAME, prescriptionToUpdate.getDrugName());
        data.put(FIELD_QUANTITY, prescriptionToUpdate.getQuantity());
        data.put(FIELD_DUEDATE, prescriptionToUpdate.getDueBy());
        try {
            DB.collection(COLLECTION_EMPLOYEE)
                    .document(loggedInEmployeeEmail)
                    .collection(COLLECTION_PATIENT)
                    .document()
                    .collection(COLLECTION_PRESCRIPTION).document(prescriptionToUpdate.getId())
                    .update(data)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.e(TAG, "onSuccess: document successfully updated");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "onFailure: Unable to update document" + e.getLocalizedMessage());
                        }
                    });
        } catch (Exception ex) {
            Log.e(TAG, "updatePrescription: " + ex.getLocalizedMessage() );
        }
    }

    //TODO-- Change hierarchy as per data saved
    public void deletePrescription(String docID){
        try {
            DB.collection(COLLECTION_EMPLOYEE)
                    .document(loggedInEmployeeEmail)
                    .collection(COLLECTION_PATIENT)
                    .document()
                    .collection(COLLECTION_PRESCRIPTION)
                    .document(docID)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.e(TAG, "onSuccess: document successfully deleted");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "onFailure: Unable to delete document" + e.getLocalizedMessage());
                        }
                    });
        }catch (Exception ex){
            Log.e(TAG, "deletePrescription: " + ex.getLocalizedMessage() );
        }
    }
}
