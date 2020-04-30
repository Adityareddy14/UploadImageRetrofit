package com.example.samplecsv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void export(View view) {
        //generate data
        StringBuilder data = new StringBuilder();
        data.append("Time,Distance");
        for (int i = 0; i < 5; i++) {
            data.append("\n" + String.valueOf(i) + "," + String.valueOf(i * i));
        }

        SQLiteDatabase myDatabase = this.openOrCreateDatabase("Employees", MODE_PRIVATE, null);
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS employee (name VARCHAR,id INT(3), email VARCHAR, phone INT(10), dept VARCHAR)");
        myDatabase.execSQL("INSERT INTO employee (name,id,email,phone,dept) VALUES ('aditya',123,'aditya@gmail.com',70361696,'developer')");
        myDatabase.execSQL("INSERT INTO employee (name,id,email,phone,dept) VALUES ('bharath',124,'bharath@gmail.com',89337443,'sales')");
        myDatabase.execSQL("INSERT INTO employee (name,id,email,phone,dept) VALUES ('akash',121,'akash@gmail.com',74636433,'finance')");
        myDatabase.execSQL("INSERT INTO employee (name,id,email,phone,dept) VALUES ('charan',120,'aditya@gmail.com',95374673,'finance')");
        myDatabase.execSQL("INSERT INTO employee (name,id,email,phone,dept) VALUES ('rajesh',129,'rajesh@gmail.com',95689586,'purchase')");
        myDatabase.execSQL("INSERT INTO employee (name,id,email,phone,dept) VALUES ('suresh',128,'suresh@gmail.com',77468789,'security')");
        myDatabase.execSQL("INSERT INTO employee (name,id,email,phone,dept) VALUES ('harshith',126,'harshith@gmail.com',98989789,'developer')");
        myDatabase.execSQL("INSERT INTO employee (name,id,email,phone,dept) VALUES ('ram',127,'ram@gmail.com',88656886,'marketing')");

        Cursor c = myDatabase.rawQuery("SELECT * FROM employee", new String[]{});

        StringBuilder sb = new StringBuilder();
        for(int i=0;i<c.getColumnNames().length;i++){
            sb.append(c.getColumnName(i) + "," );
        }

        sb.append("\n");

        String[] mySecondStringArray = new String[c.getColumnNames().length];
        while (c.moveToNext()) {
            String arrStr[] = null;
            for (int i = 0; i < c.getColumnNames().length; i++) {
                mySecondStringArray[i] = c.getString(i);
            }
            for (int i = 0; i < mySecondStringArray.length; i++) {
                String nextElement = mySecondStringArray[i];
                for (int j = 0; j < nextElement.length(); j++) {
                    char nextChar = nextElement.charAt(j);
                    sb.append(nextChar);
                }
                sb.append(",");
            }
            sb.append("\n");
        }


        try {

            FileOutputStream out = openFileOutput("data.csv", Context.MODE_PRIVATE);
            out.write((sb.toString()).getBytes());
            Context context = getApplicationContext();
            Log.e("fileloc:",getFilesDir().toString());
            File filelocation = new File(getFilesDir(), "data.csv");
            Uri path = FileProvider.getUriForFile(context, "com.example.exportcsv.fileprovider", filelocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM, path);
            startActivity(Intent.createChooser(fileIntent, "Send mail"));
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
