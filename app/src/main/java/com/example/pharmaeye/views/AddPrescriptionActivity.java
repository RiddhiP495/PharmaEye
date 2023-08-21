package com.example.pharmaeye.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.pharmaeye.R;
import com.example.pharmaeye.databinding.ActivityAddPrescriptionBinding;

import com.example.pharmaeye.models.DrugOuterClass;
import com.example.pharmaeye.models.Drugs;
import com.example.pharmaeye.models.DrugsContainer;
import com.example.pharmaeye.models.Prescription;
import com.example.pharmaeye.networks.RetrofitClient;
import com.example.pharmaeye.viewmodels.PrescriptionViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPrescriptionActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ActivityAddPrescriptionBinding binding;
    private final String TAG = this.getClass().getCanonicalName();
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private ArrayList<String> drugsList;
    private ArrayAdapter<String> adapter;
    private PrescriptionViewModel prescriptionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityAddPrescriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //setting date picker for due date
        initDatePicker();
        dateButton = this.binding.datePickerButton;
        dateButton.setText(getTodaysDate());

        //click handler for date picker button
        this.binding.datePickerButton.setOnClickListener(this);
        this.binding.btnAddPres.setOnClickListener(this);
        //setting spinner item for the drug list

        this.drugsList = new ArrayList<>();
        this.adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, this.drugsList);
        this.adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.binding.spnDrugList.setAdapter(this.adapter);
        this.getDrugsList("DFG");
        this.binding.spnDrugList.setOnItemSelectedListener(this);

        this.prescriptionViewModel = PrescriptionViewModel.getInstance(this.getApplication());

    }
    //Creating retrofit call for the spinner to populate with the drug name
    private void getDrugsList(String DFG){
        Call<DrugOuterClass> drugContainer = RetrofitClient.getInstance().getApi().retrieveDrugList(DFG);
        try{
            drugContainer.enqueue(new Callback<DrugOuterClass>() {
                @Override
                public void onResponse(Call<DrugOuterClass> call, Response<DrugOuterClass> response) {
                    if (response.code() == 200) {
                        DrugOuterClass mainResponse = response.body();
                        Log.e(TAG, "onResponse: Received response" + mainResponse.toString());
                        Log.e(TAG, "onResponse: Received response" + mainResponse.getMinConcept().toString());
                        if (mainResponse.getMinConcept().getDrugArrayList().isEmpty()) {
                            Log.e(TAG, "onResponse: No categories received");
                        } else {
                            drugsList.clear();
                            for (int i = 0; i < mainResponse.getMinConcept().getDrugArrayList().size(); i++) {
                                Log.d(TAG, "onResponse: CategoryList objects " + mainResponse.getMinConcept().getDrugArrayList().get(i).toString());
                                drugsList.add(mainResponse.getMinConcept().getDrugArrayList().get(i).getName());
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }else {
                        Log.e(TAG, "onResponse: Unsuccessful response " + response.code() + response.errorBody() );
                    }
                    call.cancel();
                }
                @Override
                public void onFailure(Call<DrugOuterClass> call, Throwable t) {
                    call.cancel();
                    Log.e(TAG, "onFailure: Failed to get the List from API" + t.getLocalizedMessage() );
                }
            });
        }catch (Exception ex){
            Log.e(TAG, "getDrugList: Cannot retrieve category list " + ex.getLocalizedMessage() );
        }
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.datePickerButton:{
                    Log.d(TAG,"Date Picker clicked");
                    openDatePicker(view);

                }
                case R.id.btnAddPres:{
                    Log.d(TAG,"Add Prescription Pressed");
                    this.savePrescription();

                }
            }
        }
    }

    private void savePrescription(){
            String drugName = binding.spnDrugList.getSelectedItem().toString();
            String dueDate = binding.datePickerButton.getText().toString();
            Date dueBy = null;
            try {
                dueBy = new SimpleDateFormat("MM-dd-yyyy").parse(dueDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String quantity = binding.etQuantity.getText().toString();
                    Prescription prescriptionToBeSaved = new Prescription(drugName,quantity,dueBy);
                    prescriptionViewModel.addPrescription(prescriptionToBeSaved);
    }
    //Helper Functions
    private String getTodaysDate(){
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

        int style = AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().getMaxDate();
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());


    }
    private  String makeDateString(int day,int month, int year){
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //set spinner item selection
        Log.e(TAG,"onSpinner Click:" + this.drugsList.get(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}