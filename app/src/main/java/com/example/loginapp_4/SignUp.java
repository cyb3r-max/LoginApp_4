package com.example.loginapp_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText edtRegEmail, edtRegUsername, edtRegPassword;
    Button btnRegSubmit;
    DatabaseHelper dbHelper;
    User user;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtRegEmail = findViewById(R.id.edtRegEmail);
        edtRegUsername = findViewById(R.id.edtRegUsername);
        textView = findViewById(R.id.tv);
        edtRegPassword = findViewById(R.id.edtRegPassword);
        btnRegSubmit = findViewById(R.id.btnRegSubmit);

        user = new User();
        dbHelper=new DatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.isOpen();
        btnRegSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String UserName = edtRegUsername.getText().toString();
        String UserEmail = edtRegEmail.getText().toString();
        String UserPass = edtRegPassword.getText().toString();

        user.setUsername(UserName);
        user.setEmail(UserEmail);
        user.setPassword(UserPass);

        long rowID = dbHelper.insertUser(user);

        if (rowID>0){
            Toast.makeText(getApplicationContext(),"Registration Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignUp.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(),"Registration Failed", Toast.LENGTH_SHORT).show();
        }

    }
}