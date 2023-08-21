package com.example.pharmaeye.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Patient implements Parcelable {

    private String id;
    private String name;
    private String email;
    private String gender;
    private Date DOB;
    private String phoneNumber;
    private String postalAddress;
    private String city;
    private String province;
    private String healthCardNumber;
    private Date createdOn;

    public Patient(String name, String email, String gender, Date DOB,
                   String phoneNumber, String postalAddress, String city, String province,
                   String healthCardNumber) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.DOB = DOB;
        this.phoneNumber = phoneNumber;
        this.postalAddress = postalAddress;
        this.city = city;
        this.province = province;
        this.healthCardNumber = healthCardNumber;
        this.createdOn = new Date();
    }

    protected Patient(Parcel in) {
        id = in.readString();
        name = in.readString();
        email = in.readString();
        gender = in.readString();
        phoneNumber = in.readString();
        postalAddress = in.readString();
        city = in.readString();
        province = in.readString();
        healthCardNumber = in.readString();
    }

    public static final Creator<Patient> CREATOR = new Creator<Patient>() {
        @Override
        public Patient createFromParcel(Parcel in) {
            return new Patient(in);
        }

        @Override
        public Patient[] newArray(int size) {
            return new Patient[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public Date getDOB() {
        return DOB;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getId() {
        return id;
    }

    public String getHealthCardNumber() {
        return healthCardNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(gender);
        parcel.writeString(phoneNumber);
        parcel.writeString(postalAddress);
        parcel.writeString(city);
        parcel.writeString(province);

    }
}
