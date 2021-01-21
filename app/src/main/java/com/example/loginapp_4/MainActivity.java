package com.example.loginapp_4;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    TextInputEditText edtUname, edtPassword;
    Button btnLogin, btnCreateAccount;

    Button langSwitch;

    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadLocale();


        edtUname = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);

        langSwitch = findViewById(R.id.changeLang);

        langSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangedLanguage();
            }
        });

        dbHelper=new DatabaseHelper(MainActivity.this);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = edtUname.getText().toString();
                String password = edtPassword.getText().toString();

                Boolean result = dbHelper.findUser(userName, password);
                if (result ==true){
                    Intent intent = new Intent(MainActivity.this, UserHomeActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Worng UserID or Password",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });
    }

    private void showChangedLanguage() {
        final String[] listLanguage = {"English", "Bengali"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder.setTitle("Choose Language");
        mBuilder.setSingleChoiceItems(listLanguage, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0){
                    setLocal("en");
                    recreate();
                }
                else if (i == 1){
                    setLocal("bn");
                    langSwitch.setText("বাংলা");
                    recreate();
                }
                dialogInterface.dismiss();
            }
        });
        AlertDialog mDialouge = mBuilder.create();
        mDialouge.show();;
    }

    private void setLocal(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor  editor = getSharedPreferences("Language_Settings", MODE_PRIVATE).edit();
        editor.putString("Language", lang);
        editor.apply();
    }

    public void loadLocale(){
        SharedPreferences preferences = getSharedPreferences("Language_Settings", Activity.MODE_PRIVATE);
        String language = preferences.getString("Language","");
        setLocal(language);
    }

}