package com.example.alicee_pc.machine;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BinDetail extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,AdapterView.OnItemSelectedListener {
    DatabaseHelper myDb;

    EditText bname, bcap, alert, mobno_alert, mailid_alert, warning, caution;
    Button btnAdd, view_details;

    TextView tvname, tvid;

    Spinner bunit;

    String b_unit,mname,mid;


    ArrayList unit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bin_detail);

        myDb = new DatabaseHelper(this);

        bname = (EditText) findViewById(R.id.bname);
        bcap = (EditText) findViewById(R.id.bcap);
        alert = (EditText) findViewById(R.id.alert);
        mobno_alert = (EditText) findViewById(R.id.mobno_alert);
        mailid_alert = (EditText) findViewById(R.id.mailid_alert);
        warning = (EditText) findViewById(R.id.warning);
        caution = (EditText) findViewById(R.id.caution);
        btnAdd = (Button) findViewById(R.id.submit);
        view_details = (Button) findViewById(R.id.view_details);
        //AddDetails();

        demo();

        viewAll();




        tvname = (TextView) findViewById(R.id.tvname);
        tvid = (TextView) findViewById(R.id.tvid);

        Bundle bu;
        bu = getIntent().getExtras();
        tvname.setText(bu.getString("mname"));
        tvid.setText(bu.getString("mid"));

        mname=tvname.getText().toString();
        mid=tvid.getText().toString();


        bunit = (Spinner) findViewById(R.id.bunit);
        bunit.setOnItemSelectedListener(this);
        unit = new ArrayList();
        unit.add("kg");
        unit.add("ltr");
        unit.add("gm");
        unit.add("kiloLitre");

        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, unit);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bunit.setAdapter(ad);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bin_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_camera) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            Intent i=new Intent(BinDetail.this,Home.class);
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

            Intent i=new Intent(BinDetail.this,ListMyMachine.class);
            startActivity(i);

        } else if (id == R.id.nav_manage) {
            Intent i= new Intent(BinDetail.this,Settings.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        b_unit = unit.get(position).toString().trim();
        //Toast.makeText(this, b_unit, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
    public void demo(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bname.getText().toString().isEmpty() || bcap.getText().toString().isEmpty() || b_unit.isEmpty() || alert.getText().toString().isEmpty() || warning.getText().toString().isEmpty() || caution.getText().toString().isEmpty()){
                    Toast.makeText(BinDetail.this, "EMPTY", Toast.LENGTH_SHORT).show();
                }else{
                    //Toast.makeText(BinDetail.this, "Done", Toast.LENGTH_SHORT).show();
                    AddDetails();
                }
            }
        });

    }

    public void AddDetails() {

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(bname.getText().toString(), bcap.getText().toString(), alert.getText().toString(), mobno_alert.getText().toString(), mailid_alert.getText().toString(), warning.getText().toString(), caution.getText().toString(),mname,mid,b_unit);
                if (isInserted == true)
                    Toast.makeText(BinDetail.this, "Bin Added Successfully", Toast.LENGTH_SHORT).show();

                else
                    Toast.makeText(BinDetail.this, "Machine already exists", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(BinDetail.this, ListMyMachine.class);
                startActivity(i);

            }


        });
    }
    public void viewAll() {
        view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if (res.getCount() == 0) {
                    showMsg("Error", "No data found");
                    return;
                }
                StringBuilder buffer = new StringBuilder();
                while (res.moveToNext()) {
                    buffer.append("B_NAME : " + res.getString(0) + "\n");
                    buffer.append("B_CAPACITY : " + res.getString(1) + "\n");
                    buffer.append("B_ALERT : " + res.getString(2) + "\n");
                    buffer.append("MOB_NO : " + res.getString(3) + "\n");
                    buffer.append("MAILID : " + res.getString(4) + "\n");
                    buffer.append("WARNING : " + res.getString(5) + "\n");
                    buffer.append("CAUTION : " + res.getString(6) + "\n");
                    buffer.append("MACH_NAME : " + res.getString(7) + "\n");
                    buffer.append("MACH_ID : " + res.getString(8) + "\n");
                    buffer.append("UNIT : " + res.getString(9) + "\n\n");
                }
                showMsg("Data", buffer.toString());
            }
        });
    }

    public void showMsg(String title,String msg){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();

    }
}