package com.example.alicee_pc.machine;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Settings extends AppCompatActivity {

    Button add, sett_delete;
    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        add = (Button) findViewById(R.id.addmac);
        sett_delete = (Button) findViewById(R.id.deletemac);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Settings.this, AddMachine.class);
                startActivity(i);
            }
        });

        myDb = new DatabaseHelper(this);
        sett_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder aBuilder = new AlertDialog.Builder(Settings.this);
                View aView = getLayoutInflater().inflate(R.layout.dailog_delete, null);
                final EditText ed_id = (EditText) aView.findViewById(R.id.edid);
                Button b = (Button) aView.findViewById(R.id.delete);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!ed_id.getText().toString().isEmpty()) {
                           // String mn = ed_mn.getText().toString();
                            //Toast.makeText(Settings.this, mn, Toast.LENGTH_SHORT).show();
                            Integer deletedRow = myDb.deleteData(ed_id.getText().toString());
                            if (deletedRow > 0)
                                Toast.makeText(Settings.this, "data deleted", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(Settings.this, "Data not deleted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Settings.this, "fill empty field", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                aBuilder.setView(aView);
                AlertDialog dialoge = aBuilder.create();
                dialoge.show();
            }
        });
    }
}
