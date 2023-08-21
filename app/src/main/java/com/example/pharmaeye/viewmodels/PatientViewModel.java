package com.example.pharmaeye.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.pharmaeye.models.Patient;
import com.example.pharmaeye.repositories.PatientRepository;

public class PatientViewModel extends AndroidViewModel {
    private final PatientRepository repository = new PatientRepository();
    private static PatientViewModel instance;


    public PatientViewModel(@NonNull Application application) {
        super(application);
    }

    public static PatientViewModel getInstance(Application application){
        if (instance == null){
            instance = new PatientViewModel(application);
        }
        return instance;
    }

    public PatientRepository getPatientRepository(){
        return this.repository;
    }

    public void addPatient(Patient patient){
        this.repository.addPatient(patient);
    }
}
