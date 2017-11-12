package com.example.alicee_pc.machine;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListMyMachine extends AppCompatActivity implements AdapterView.OnItemClickListener{



    ListView lv;
    ArrayList arr_list=null;
    DatabaseHelper dh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_my_machine);

        dh=new DatabaseHelper(this);
        lv= (ListView) findViewById(R.id.lv);
        lv.setOnItemClickListener(this);

        arr_list=new ArrayList();
        Cursor c=dh.getAllData();


        if(c!=null && c.moveToFirst())
        {
            do{
                String mach_name=c.getString(c.getColumnIndex(DatabaseHelper.COL_8));
                Bin b=new Bin();
                b.setMname(mach_name);

                arr_list.add(b);


            }while(c.moveToNext());

            ArrayAdapter ad=new ArrayAdapter(this,android.R.layout.select_dialog_singlechoice,arr_list);
            lv.setAdapter(ad);

        }



    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String mname=arr_list.get(position).toString();

        Intent i=new Intent(ListMyMachine.this,ShowBin.class);

        i.putExtra("name",mname);

        startActivity(i);

       // Toast.makeText(this, mname, Toast.LENGTH_SHORT).show();






    }
}
