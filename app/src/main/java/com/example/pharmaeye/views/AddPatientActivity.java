package com.example.pharmaeye.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.pharmaeye.R;
import com.example.pharmaeye.databinding.ActivityAddPatientBinding;
import com.example.pharmaeye.models.Patient;
import com.example.pharmaeye.viewmodels.PatientViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class AddPatientActivity extends AppCompatActivity {

    ActivityAddPatientBinding binding;

    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    // Adapter for the spinner
    private ArrayAdapter<String> spinnerAdaptor;

    // Data Source for Spinner
    private ArrayList<String> genders;

    private PatientViewModel patientViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // configure bindings
        binding = ActivityAddPatientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Getting view model instance
        this.patientViewModel = PatientViewModel.getInstance(this.getApplication());

        // Initializing the genders(ArrayList) and adding the value
        this.genders = new ArrayList<>();
        Collections.addAll(genders,"Select gender","Male","Female","Other");

        // Initializing & Populating the data(genders) in to the spinnerAdaptor
        this.spinnerAdaptor = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,this.genders);

        this.spinnerAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Setting the default value for the spinner
        this.binding.genderSpinner.setSelection(0);

        // Binding the spinner with the adapter
        this.binding.genderSpinner.setAdapter(this.spinnerAdaptor);

        // Setting the Date picker for DOB
        initDatePicker();
        dateButton = this.binding.datePickerButton;
        dateButton.setText(getTodaysDate());

        // click handlers
        this.binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("TAG","Add Patient button clicked");
                // 1. Get the data from the form fields
                savePatient();

            }
        });

        // click handler for date picker

        this.binding.datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ABC","Date picker clicked");
                openDatePicker(view);
            }
        });

    }

    // Helper Functions
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    private void savePatient(){

        String patientName = binding.etPatientName.getText().toString();
        String patientDOB = binding.datePickerButton.getText().toString();
        Date DOB = null;
        try {
             DOB=new SimpleDateFormat("MMM-dd-yyyy").parse(patientDOB);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String patientEmail = binding.etEmailAddress.getText().toString();
        String patientGender = binding.genderSpinner.getSelectedItem().toString();
        String address = binding.etPostalAddress.getText().toString();
        String city = binding.etAddressCity.getText().toString();
        String province = binding.etAddressProvince.getText().toString();
        String healthCardNumber = binding.etHealthCardNo.getText().toString();
        String phoneNumber = binding.etPatientPhone.getText().toString();

        //TODO: Validate patient info

        // 2. Create an patient objects
        Patient patientToBeSaved = new Patient(patientName,patientEmail,patientGender,DOB,
                phoneNumber,address,city,province,healthCardNumber);

        // 3. Use the Repository function to insert the Patient into the Firestore(database)
        patientViewModel.addPatient(patientToBeSaved);

    }


}







