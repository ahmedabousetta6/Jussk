package com.example.onlineshopping.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.onlineshopping.R;
import com.example.onlineshopping.user.DBTables;

public class Forget extends AppCompatActivity {

    ImageView fPassword;
    EditText fEmail,fFavColor;
    TextView txtPass , pass_reset;
    Button reset_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        fPassword = (ImageView)findViewById(R.id.imageForgot);
        fEmail = (EditText)findViewById(R.id.forgotEmail);
        fFavColor = (EditText)findViewById(R.id.colorForgot);
        txtPass = (TextView)findViewById(R.id.passwordtxt);
        pass_reset = (TextView)findViewById(R.id.passwordTextView);
        reset_password = (Button)findViewById(R.id.btnResetPassword);

        final DBTables database=new DBTables(this);

        reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String x=database.getpassword(fEmail.getText().toString(),fFavColor.getText().toString());
                pass_reset.setText(x);
            }
        });
    }
}