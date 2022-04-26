// Vaccination Activity
package com.msd.group9_project;

// To find the Vaccination Dates Periodically

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// Class With Various buttons and fields for Vaccine Reminder
public class VaccinationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TextView displayTV, tvFirstDose, tvSecondDose, tvNextDosageHeader, tvNextDosageDate;
    private Dialog dialog;

    RadioGroup rgFirstDose, rgSecondDose;
    RadioButton rbModernaVaccine, rbJJVaccine,rbPfizer;
    TextInputEditText tieFirstDose, tieSecondDose;
    boolean jjSelected, rbSecondDoseSelectedYes, isFirstDoseDateSelected;
    String firstDoseTimeStamp;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination);


        tvFirstDose = findViewById(R.id.tv_first_dose);
        tvSecondDose = findViewById(R.id.tv_seconddose);

        rgFirstDose = findViewById(R.id.rg_firstdose);
        rgSecondDose = findViewById(R.id.rg_seconddose);

        rbModernaVaccine = findViewById(R.id.rb_vaccine_moderna);
        rbJJVaccine = findViewById(R.id.rb_vaccine_jj);
        rbPfizer = findViewById(R.id.rb_vaccine_pfizer);


        tvNextDosageHeader = findViewById(R.id.tv_next_dosage_header);
        tvNextDosageDate = findViewById(R.id.tv_next_dosage_date);

        tieFirstDose = findViewById(R.id.tie_firstdose);

        tieFirstDose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mCalendar = Calendar.getInstance();

               DatePickerDialog.OnDateSetListener mDateSetListener =
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view, int year,
                                                  int month, int dayOfMonth) {

                                mCalendar.set(Calendar.YEAR, year);
                                mCalendar.set(Calendar.MONTH, month);
                                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
                                String timestamp = simpleDate.format(mCalendar.getTime());

                                myEdit.putString("firstDoseDate", timestamp);
                                tieFirstDose.setText(timestamp);
                                myEdit.putBoolean("isFirstDoseDateSelected", true);
                                if(sharedPreferences.getBoolean("isSecondDoseDateSelected", false)){
                                    myEdit.putString("secondDoseDate", "");
                                    myEdit.putBoolean("isSecondDoseDateSelected", false);
                                    tieSecondDose.setText("");
                                    rbSecondDoseSelectedYes = false;
                                }
                                myEdit.commit();
                                doNextDoseDateCalculation(mCalendar, simpleDate);
                            }
                        };
                DatePickerDialog  StartTime = new DatePickerDialog(VaccinationActivity.this,mDateSetListener,mCalendar.get(Calendar.YEAR),mCalendar.get(Calendar.MONTH),mCalendar.get(Calendar.DAY_OF_MONTH));
                StartTime.show();
            }
        });

        tieSecondDose = findViewById(R.id.tie_second_dose);
        tieSecondDose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mCalendar = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener mDateSetListener =
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view, int year,
                                                  int month, int dayOfMonth) {

                                mCalendar.set(Calendar.YEAR, year);
                                mCalendar.set(Calendar.MONTH, month);
                                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
                                String timestamp = simpleDate.format(mCalendar.getTime());
                                if(sharedPreferences.getBoolean("isFirstDoseDateSelected", false)){
                                    myEdit.putString("secondDoseDate", timestamp);
                                    myEdit.putBoolean("isSecondDoseDateSelected", true);
                                    tieSecondDose.setText(timestamp);
                                    rbSecondDoseSelectedYes = true;
                                    myEdit.commit();
                                }else{
                                    Toast.makeText(getApplicationContext(), "Please Select First Dose Date", Toast.LENGTH_SHORT).show();
                                }
                                doNextDoseDateCalculation(mCalendar,simpleDate);
                            }
                        };
                DatePickerDialog  StartTime = new DatePickerDialog(VaccinationActivity.this,mDateSetListener,mCalendar.get(Calendar.YEAR),mCalendar.get(Calendar.MONTH),mCalendar.get(Calendar.DAY_OF_MONTH));
                StartTime.show();
            }
        });
        ImageView imgBack =findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        initSharedPreferences();
    }


    private void initSharedPreferences() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        myEdit = sharedPreferences.edit();
        myEdit.clear().commit();
    }
// Radio Button For Vaccines
    public void onRadioButtonClicked(View view) {
        // Is the view now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which RadioButton was clicked
        switch (view.getId()) {
            case R.id.rb_vaccine_moderna:
                Log.d("rb_vaccine_moderna", "rb_vaccine_moderna");
                if (checked) {
                    Log.d("rb_vaccine_moderna", "rb_vaccine_moderna");

                    jjSelected = false;

                    tvFirstDose.setVisibility(View.VISIBLE);
                    rgFirstDose.setVisibility(View.VISIBLE);
                    tvSecondDose.setVisibility(View.GONE);
                    rgSecondDose.setVisibility(View.GONE);
                    tieFirstDose.setVisibility(View.GONE);
                    tieSecondDose.setVisibility(View.GONE);

                    rgFirstDose.clearCheck();
                    rgSecondDose.clearCheck();
                    tieFirstDose.setText("");
                    tieSecondDose.setText("");
                    tvNextDosageDate.setText("");
                    rbSecondDoseSelectedYes = false;
                    myEdit.putString("firstDoseDate", "");
                    myEdit.putBoolean("isFirstDoseDateSelected", false);
                    myEdit.putString("secondDoseDate", "");
                    myEdit.putBoolean("isSecondDoseDateSelected", false);
                    myEdit.commit();
                }
                break;

            case R.id.rb_vaccine_pfizer:
                Log.d("rb_vaccine_pfizer", "rb_vaccine_pfizer");
                if (checked) {
                    Log.d("rb_vaccine_pfizer", "rb_vaccine_pfizer");

                    jjSelected = false;

                    tvFirstDose.setVisibility(View.VISIBLE);
                    rgFirstDose.setVisibility(View.VISIBLE);
                    tvSecondDose.setVisibility(View.GONE);
                    rgSecondDose.setVisibility(View.GONE);
                    tieFirstDose.setVisibility(View.GONE);
                    tieSecondDose.setVisibility(View.GONE);

                    rgFirstDose.clearCheck();
                    rgSecondDose.clearCheck();
                    tieFirstDose.setText("");
                    tieSecondDose.setText("");
                    tvNextDosageDate.setText("");
                    rbSecondDoseSelectedYes = false;
                    myEdit.putString("firstDoseDate", "");
                    myEdit.putBoolean("isFirstDoseDateSelected", false);
                    myEdit.putString("secondDoseDate", "");
                    myEdit.putBoolean("isSecondDoseDateSelected", false);
                    myEdit.commit();
                }
                break;

            case R.id.rb_vaccine_jj:
                if (checked) {
                    jjSelected = true;

                    tvFirstDose.setVisibility(View.VISIBLE);
                    rgFirstDose.setVisibility(View.VISIBLE);
                    tvSecondDose.setVisibility(View.GONE);
                    rgSecondDose.setVisibility(View.GONE);
                    tieFirstDose.setVisibility(View.VISIBLE);
                    tieSecondDose.setVisibility(View.GONE);

                    rgFirstDose.clearCheck();
                    rgSecondDose.clearCheck();
                    tieFirstDose.setText("");
                    tieSecondDose.setText("");
                    tvNextDosageDate.setText("");
                    rbSecondDoseSelectedYes = false;
                    myEdit.putString("firstDoseDate", "");
                    myEdit.putBoolean("isFirstDoseDateSelected", false);
                    myEdit.putString("secondDoseDate", "");
                    myEdit.putBoolean("isSecondDoseDateSelected", false);
                    myEdit.commit();
                }
                break;

            case R.id.rb_firstdose_yes:
                if (checked && jjSelected) {
                    tvSecondDose.setVisibility(View.GONE);
                    rgSecondDose.setVisibility(View.GONE);
                    tieFirstDose.setVisibility(View.VISIBLE);
                    tieSecondDose.setVisibility(View.GONE);

                } else {
                    tvSecondDose.setVisibility(View.VISIBLE);
                    rgSecondDose.setVisibility(View.VISIBLE);
                    tieFirstDose.setVisibility(View.VISIBLE);
                    tieSecondDose.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.rb_firstdose_no:
                tvSecondDose.setVisibility(View.GONE);
                rgSecondDose.setVisibility(View.GONE);
                tieFirstDose.setVisibility(View.GONE);
                tieSecondDose.setVisibility(View.GONE);
                break;

            case R.id.rb_seconddose_yes:
//                rbSecondDoseSelectedYes = true;
                tieSecondDose.setVisibility(View.VISIBLE);
                break;

            case R.id.rb_seconddose_no:
                rbSecondDoseSelectedYes = false;
                tieSecondDose.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

    }


    private void doNextDoseDateCalculation(Calendar mCalendar, SimpleDateFormat simpleDate) {
        if (rbSecondDoseSelectedYes) {
            if (rbModernaVaccine.isChecked()) {
                mCalendar.add(Calendar.DAY_OF_MONTH, 28);
                tvNextDosageDate.setText("You can have your boost dose after 28 days :" + simpleDate.format(mCalendar.getTime()));
            } else if(rbJJVaccine.isChecked()) {
                mCalendar.add(Calendar.MONTH, 2);
                tvNextDosageDate.setText("You can have your boost dose after 2 months :" + simpleDate.format(mCalendar.getTime()));
            }else{
                mCalendar.add(Calendar.DAY_OF_MONTH, 28);
                tvNextDosageDate.setText("You can have your boost dose after 28 days :" + simpleDate.format(mCalendar.getTime()));
            }

            tvNextDosageDate.setVisibility(View.VISIBLE);
            tvNextDosageHeader.setVisibility(View.VISIBLE);

        } else {
            String dtStart = sharedPreferences.getString("firstDoseDate", "");
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            Date date = new Date();

            try {
                date = format.parse(dtStart);
                System.out.println(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            mCalendar.setTime(date);

            tvNextDosageDate.setVisibility(View.VISIBLE);
            tvNextDosageHeader.setVisibility(View.VISIBLE);
            if (rbModernaVaccine.isChecked()) {
                mCalendar.add(Calendar.DAY_OF_MONTH, 28);
                String timestampSecondDose = simpleDate.format(mCalendar.getTime());
                tvNextDosageDate.setText("You can have your 2nd dose after 28 days :" + timestampSecondDose);
            } else if(rbJJVaccine.isChecked()) {
                mCalendar.add(Calendar.MONTH, 2);
                String timestampSecondDose = simpleDate.format(mCalendar.getTime());
                tvNextDosageDate.setText("You can have your Booster dose after 2 months :" + timestampSecondDose);
            }else{
                mCalendar.add(Calendar.DAY_OF_MONTH, 21);
                String timestampSecondDose = simpleDate.format(mCalendar.getTime());
                tvNextDosageDate.setText("You can have your 2nd dose after 21 days :" + timestampSecondDose);
            }   
        }
    }
}
