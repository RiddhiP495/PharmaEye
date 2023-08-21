
package com.example.pharmaeye.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.pharmaeye.R;
import com.example.pharmaeye.adapters.PatientAdapter;
import com.example.pharmaeye.models.Patient;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    // define the data source for the adapter
    private ArrayList<Patient> patientsList = new ArrayList<Patient>();
    private PatientAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        // Configuring the RecyclerView
        // 1. Add data to our data source
        // 2. Create an instance of the adapter
        // 3. Configure the recyclerview to use the adapter

    }
}