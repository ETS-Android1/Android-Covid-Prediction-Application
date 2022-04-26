// Main Activity
package com.msd.group9_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

// Fields and Views for the Class
public class MainActivity extends AppCompatActivity {
    LinearLayout medicine, faq, bmi, vaccination, prediction, pharmacyList; // Linear Layout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        medicine = findViewById(R.id.btnMedicine); // Medicine
        faq = findViewById(R.id.btnFaqs);   // FAQ
        bmi = findViewById(R.id.btnBmi);   // BMI
        vaccination = findViewById(R.id.btnVaccine);  // Vaccination
        prediction = findViewById(R.id.btnPredict);   // Prediction
        pharmacyList = findViewById(R.id.btnPharmacy); // PharmacyList

        medicine.setOnClickListener(v -> medicineReminder(v));
        faq.setOnClickListener(v -> faq(v));
        bmi.setOnClickListener(v -> bmi(v));
        vaccination.setOnClickListener(v -> vaccination(v));
        prediction.setOnClickListener(v -> prediction(v));
        pharmacyList.setOnClickListener(v -> pharmacyList(v));
    }

    public void medicineReminder(View v){
        Intent explicit = new Intent(MainActivity.this, RemindersActivity.class);
        startActivity(explicit );
    }

    public void faq(View v){
        Intent explicit = new Intent(MainActivity.this, FaqActivity.class);
        startActivity(explicit );
    }

    public void bmi(View v){
        Intent explicit = new Intent(MainActivity.this, BmiActivity.class);
        startActivity(explicit );
    }

    public void vaccination(View v){
        Intent explicit = new Intent(MainActivity.this, VaccinationActivity.class);
        startActivity(explicit );
    }

    public void prediction(View v){
        Intent explicit = new Intent(MainActivity.this, CovidPrediction.class);
        startActivity(explicit );
    }

    public void pharmacyList(View v){
        Intent explicit = new Intent(MainActivity.this, PharmacyListActivity.class);
        startActivity(explicit );
    }
}