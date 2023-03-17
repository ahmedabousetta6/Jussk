package com.example.onlineshopping.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;

import com.example.onlineshopping.R;


public class Calender extends AppCompatActivity {
    CalendarView calendarView;
    String fName, phone, lName, pass, favColor,emailUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        fName=getIntent().getStringExtra("fName");
        phone=getIntent().getStringExtra("phone");
        lName=getIntent().getStringExtra("lName");
        pass=getIntent().getStringExtra("pass");
        favColor=getIntent().getStringExtra("favColor");
        emailUp=getIntent().getStringExtra("emailUp");
        calendarView=findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date=year+ "-" +month+ "-" +dayOfMonth;
                Intent i=new Intent(Calender.this,SignUp.class);
                i.putExtra("date",date);
                i.putExtra("path", "Calender");
                i.putExtra("fName",fName);
                i.putExtra("lName",lName);
                i.putExtra("pass",pass);
                i.putExtra("favColor",favColor);
                i.putExtra("emailUp",emailUp);
                i.putExtra("phone" , phone);
                startActivity(i);
            }
        });
    }
}