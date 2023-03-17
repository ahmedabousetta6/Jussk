package com.example.onlineshopping.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshopping.R;
import com.example.onlineshopping.admin.Admin;
import com.example.onlineshopping.user.DBTables;
import com.example.onlineshopping.user.Products;

public class MainActivity extends AppCompatActivity {

    Button signUpButton,loginButton , button;
    EditText emailEditText, passwordEditText;
    TextView forgetText;
    CheckBox rememberCheck;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String KEY_REMEMBER ="remember";
    private static final String KEY_EMAIL ="email";
    private static final String KEY_PW ="password";
    private  static String PREF_NAME = "myPref_File";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        button = findViewById(R.id.button);
        emailEditText = (EditText)findViewById(R.id.emailLogin);
        passwordEditText = (EditText)findViewById(R.id.passwordLogin);
        loginButton = (Button)findViewById(R.id.btnLogin);
        signUpButton = (Button)findViewById(R.id.btnSignUp);
        forgetText = (TextView)findViewById(R.id.forgotTextView);
        rememberCheck = (CheckBox)findViewById(R.id.check);
        sharedPreferences = getSharedPreferences(PREF_NAME , MODE_PRIVATE);
        String user = sharedPreferences.getString("username","");
        String pass = sharedPreferences.getString("password", "");
        emailEditText.setText(user);
        passwordEditText.setText(pass);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this , Admin.class);
                startActivity(i);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     Intent intent = new Intent(MainActivity.this , SignUp.class);
                     startActivity(intent);
            }
        });

        final DBTables login=new DBTables(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if(rememberCheck.isChecked())
                {
                    StoredDataUsingSharedPref(username,password);
                }
                else if (!rememberCheck.isChecked())
                {
                    StoredDataUsingSharedPref("", "");
                }
                if (emailEditText.getText().toString().equals("admin") && passwordEditText.getText().toString().equals("11")) {
                    Intent intent = new Intent(MainActivity.this, Admin.class);
                    startActivity(intent);
                } else {
                    boolean x = login.loginuser(emailEditText.getText().toString(), passwordEditText.getText().toString());
                    if (x == true) {
                        Intent intent = new Intent(MainActivity.this, Products.class);
                        intent.putExtra("email", emailEditText.getText().toString());
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Enter a valid Email and Password", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
        forgetText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Forget.class);
                startActivity(i);
            }
        });

    }

    private void StoredDataUsingSharedPref(String username, String password) {
        SharedPreferences.Editor editor = getSharedPreferences(PREF_NAME,MODE_PRIVATE).edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();
    }


}