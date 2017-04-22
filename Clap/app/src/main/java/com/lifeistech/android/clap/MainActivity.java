package com.lifeistech.android.clap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    int repeat = 1;

    ImageButton button;
    Spinner spinner;

    Clap clapInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (ImageButton) findViewById(R.id.imgBtn);
        button.setOnClickListener((View.OnClickListener) this);
        spinner = (Spinner) findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(this);

        clapInstance = new Clap(this.getApplicationContext());

        String[] strArray = new String[5];
        strArray[0] = "バンッ！";
        strArray[1] = "バンバンッ！";
        strArray[2] = "バンバンバンッ！";
        strArray[3] = "4回";
        strArray[4] = "5回";

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item,strArray
        );
        arrayAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinner.setAdapter(arrayAdapter);

        clapInstance = new Clap(this.getApplicationContext());
    }

    @Override
    public void  onItemSelected(AdapterView<?>parent, View v, int pos, long id) {
        repeat = pos + 1;
    }

    @Override
    public void  onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public  void  onClick(View v) {
        clapInstance.repeatPlay(repeat);
    }
}
