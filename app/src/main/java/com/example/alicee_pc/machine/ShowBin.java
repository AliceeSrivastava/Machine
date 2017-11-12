package com.example.alicee_pc.machine;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ShowBin extends AppCompatActivity {

    TextView machine_name,bin_name,bin_capacity,unit,alert,warning,caution;
    DatabaseHelper dh;

    ProgressBar prg,prg_war,prg_cau;
Button view;

    String bname,bcap,bunit,balert,bwarning,bcaution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bin);

        prg= (ProgressBar) findViewById(R.id.prg);
        prg_war= (ProgressBar) findViewById(R.id.prg_war);
        prg_cau= (ProgressBar) findViewById(R.id.prg_cau);
        view= (Button) findViewById(R.id.View);


        machine_name= (TextView) findViewById(R.id.machine_name);
        bin_name= (TextView) findViewById(R.id.bin_name);
        bin_capacity= (TextView) findViewById(R.id.bin_capacity);
        unit= (TextView) findViewById(R.id.unit);
        alert= (TextView) findViewById(R.id.alert);
        warning= (TextView) findViewById(R.id.warning);
        caution= (TextView) findViewById(R.id.caution);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPrg();
            }
        });

        Bundle bu;
        bu=getIntent().getExtras();
        machine_name.setText(bu.getString("name"));

        Button b= (Button) findViewById(R.id.home);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ShowBin.this,Home.class);
                startActivity(i);
            }
        });


        dh=new DatabaseHelper(this);
        populate();

        bin_name.setText(bname);
        bin_capacity.setText(bcap);
        unit.setText(bunit);
        alert.setText(balert);
        warning.setText(bwarning);
        caution.setText(bcaution);


    }

    private void populate() {

        String Mach_name=machine_name.getText().toString();

       Cursor c= dh.getData(Mach_name);

        if(c!=null && c.moveToFirst()){
            do{


                bname=c.getString(c.getColumnIndex(DatabaseHelper.COL_1));
                bcap=c.getString(c.getColumnIndex(DatabaseHelper.COL_2));
                bunit=c.getString(c.getColumnIndex(DatabaseHelper.COL_10));
                balert=c.getString(c.getColumnIndex(DatabaseHelper.COL_3));
                bwarning=c.getString(c.getColumnIndex(DatabaseHelper.COL_6));
                bcaution=c.getString(c.getColumnIndex(DatabaseHelper.COL_7));

            }while (c.moveToNext());

        }
    }
    public void setPrg(){
        String al=alert.getText().toString();
        String war=warning.getText().toString();
        String cau=caution.getText().toString();

        prg.setProgress(Integer.parseInt(al));

        prg_war.setProgress(Integer.parseInt(war));

        prg_cau.setProgress(Integer.parseInt(cau));
    }
}
