package com.example.onlineshopping.ui;

import androidx.appcompat.app.*;
import android.*;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshopping.R;
import com.example.onlineshopping.user.DBTables;

public class SignUp extends AppCompatActivity {

    EditText fName, lName, emailUp, pass, phone, favColor;
    Button signUp, birthdate;
    TextView birth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fName = (EditText) findViewById(R.id.firstNameSignUp);
        lName = (EditText) findViewById(R.id.lastNameSignUp);
        emailUp = (EditText) findViewById(R.id.emailSignUP);
        pass = (EditText) findViewById(R.id.passwordSignUp);
        phone = (EditText) findViewById(R.id.phoneSignUp);
        favColor = (EditText) findViewById(R.id.favourite);
        signUp = (Button) findViewById(R.id.btnRegister);
        birthdate = (Button) findViewById(R.id.btnBirthDate);
        birth = (TextView) findViewById(R.id.birthtxt);

        String data = getIntent().getStringExtra("date");
       String path = getIntent().getStringExtra("path");
        //if (path.equals("Calender"))
        //{
            fName.setText(getIntent().getStringExtra("fName"));
            phone.setText(getIntent().getStringExtra("phone"));
            lName.setText(getIntent().getStringExtra("lName"));
            pass.setText(getIntent().getStringExtra("pass"));
            favColor.setText(getIntent().getStringExtra("favColor"));
            emailUp.setText(getIntent().getStringExtra("emailUp"));
        //}
        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp.this, Calender.class);
                i.putExtra("fName",fName.getText().toString());
                i.putExtra("phone",phone.getText().toString());
                i.putExtra("lName",lName.getText().toString());
                i.putExtra("pass",pass.getText().toString());
                i.putExtra("favColor",favColor.getText().toString());
                i.putExtra("emailUp",emailUp.getText().toString());
                startActivity(i);
            }
        });
        if (data != null) {
            birth.setText(data);
        }



    }

    public void signup(View view) {


        String usernamef = fName.getText().toString();
        String usernamel = lName.getText().toString();
        String userpassword = pass.getText().toString();
        String useremail = emailUp.getText().toString();
        String usercolor = favColor.getText().toString();

        final DBTables reg = new DBTables(this);


        boolean check = reg.checkmail(emailUp.getText().toString());
        if (check == true) {
            boolean insert = reg.addnewuser(fName.getText().toString()
                    , lName.getText().toString(), emailUp.getText().toString()
                    , pass.getText().toString(), phone.getText().toString()
                    , favColor.getText().toString()
                    , birth.getText().toString());
            if (insert == true) {
                Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();

            }
        } else {
            Toast.makeText(getApplicationContext(), "Email Already Exist", Toast.LENGTH_LONG).show();
        }

    }
}