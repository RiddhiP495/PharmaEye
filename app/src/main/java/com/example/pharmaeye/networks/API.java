package com.example.pharmaeye.networks;

import com.example.pharmaeye.models.DrugOuterClass;
import com.example.pharmaeye.models.Drugs;
import com.example.pharmaeye.models.DrugsContainer;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {

    //https://rxnav.nlm.nih.gov/REST/Prescribe/allconcepts.json?tty=DFG
    String BASE_URL = "https://rxnav.nlm.nih.gov/REST/";

    //HTTP request --- GET Request
    @GET("./Prescribe/allconcepts.json")
    Call<DrugOuterClass> retrieveDrugList(@Query("tty") String DFG);

}
