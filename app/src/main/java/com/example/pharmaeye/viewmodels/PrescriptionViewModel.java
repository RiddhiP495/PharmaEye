package com.example.pharmaeye.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.pharmaeye.models.Patient;
import com.example.pharmaeye.models.Prescription;
import com.example.pharmaeye.repositories.PatientRepository;
import com.example.pharmaeye.repositories.PrescriptionRepository;

import java.util.List;

public class PrescriptionViewModel extends AndroidViewModel {
    private final PrescriptionRepository repository = new PrescriptionRepository();
    private static PrescriptionViewModel instance;
    public MutableLiveData<List<Prescription>> allPrescriptions;

    public PrescriptionViewModel(@NonNull Application application){
        super(application);
    }

    public static PrescriptionViewModel getInstance(Application application){
        if(instance == null){
            instance = new PrescriptionViewModel(application);
        }
        return instance;
    }

    public PrescriptionRepository getRepository(){
        return this.repository;
    }

    public void addPrescription(Prescription prescription){
        this.repository.addPrescription(prescription);
    }

    public void getAllPrescription(){
        this.repository.getPrescription();
        this.allPrescriptions = this.repository.allPrescription;
    }

    public void updatePrescription(Prescription prescriptionToUpdate){
        this.repository.updatePrescription(prescriptionToUpdate);
    }

    public void deletePrescription(String docID){
        this.repository.deletePrescription(docID);
    }
}
