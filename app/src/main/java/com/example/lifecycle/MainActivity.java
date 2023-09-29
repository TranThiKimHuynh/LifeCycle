package com.example.lifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private int duration = Toast.LENGTH_SHORT;
    private Button btnExit;
    private EditText txtColorSelected;
    private TextView txtSpyBox;
    private  LinearLayout myScreen;
    private  String PREFNAME ="myPrefFile1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("LifeCycle");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_sun);

        txtColorSelected =(EditText)findViewById(R.id.txtMsg);
        btnExit= (Button) findViewById(R.id.btnExit);
        txtSpyBox =(TextView)findViewById(R.id.txtSpy);
        myScreen=(LinearLayout) findViewById(R.id.myScreen1);

        btnExit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtColorSelected.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String chosenColor = s.toString().toLowerCase(Locale.US);
                txtSpyBox.setText(chosenColor);
                setBackgroundColor(chosenColor,myScreen);


            }
        });
        context = getApplicationContext();
        Toast.makeText(context,"onCreate", duration).show();
    }
    private void setBackgroundColor(String chosenColor, LinearLayout myScreen){
        if(chosenColor.contains("thai")) myScreen.setBackgroundColor(getResources().getColor(R.color.red));
        if(chosenColor.contains("triet")) myScreen.setBackgroundColor(getResources().getColor(R.color.green));
        if(chosenColor.contains("yen")) myScreen.setBackgroundColor(getResources().getColor(R.color.blue));
        if(chosenColor.contains("vu")) myScreen.setBackgroundColor(getResources().getColor(R.color.white));
        if(chosenColor.contains("huynh")) myScreen.setBackgroundColor(getResources().getColor(R.color.yellow));

    }

    @Override
    protected void onStart() {
        super.onStart();
        updateMeUsingSavedStateData();
        Toast.makeText(context, "onStart", duration).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(context, "onStop", duration).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(context, "onDestroy", duration).show();

    }

    @Override
    protected void onPause() {
        super.onPause();
        String chosenColor = txtSpyBox.getText().toString();
        saveStateData(chosenColor);
        Toast.makeText(context, "onPause", duration).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(context, "onRestart", duration).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(context, "onStop", duration).show();
    }

    private void saveStateData(String chosenColor){
        SharedPreferences myPrefContainer = getSharedPreferences(PREFNAME, Activity.MODE_PRIVATE);

        SharedPreferences.Editor myPrefEditor = myPrefContainer.edit();
        String key ="chosenBackgroundColor", value =txtSpyBox.getText().toString();
        myPrefEditor.putString(key, value);
        myPrefEditor.commit();

    }

    private void updateMeUsingSavedStateData(){
        SharedPreferences myPrefContainer =getSharedPreferences(PREFNAME,Activity.MODE_PRIVATE);
        String key ="chosenBackgroundColor";
        String defaultValue = "white";
        if((myPrefContainer!=null)&& myPrefContainer.contains(key)){
            String color = myPrefContainer.getString(key, defaultValue);
            setBackgroundColor(color,myScreen);
        }
    }
}