package kmisiuk.qr_bileter;

/**
 * Created by kmisiuk on 2016-10-20.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;


public class CSVreader extends AppCompatActivity {
    InputStream plikCSV;

    String[] liniaCSV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csv);
        plikCSV = getResources().openRawResource(R.raw.sample);
    }

    public void csvBack(View v){
        startActivity(new Intent(CSVreader.this, sql_main_menu.class));
    }

    public void listaPlikow(View v){
        ListView list ;
        ArrayAdapter<String> adapter ;


            list = (ListView) findViewById(R.id.listaPlikow);

            String cars[] = {"1", "2", "3", "4","5", "6", "7", "8", "9", "10", "11", "12", "13"};

            ArrayList<String> carL = new ArrayList<String>();
            carL.addAll( Arrays.asList(cars) );

            adapter = new ArrayAdapter<String>(this, R.layout.row, carL);

            list.setAdapter(adapter);
    }

    public void ladowanieCSV(View v){
    BufferedReader reader = new BufferedReader(new InputStreamReader(plikCSV));
        try {
            String csvLine;
            DBAdapter myDB; //tworzenie zmiennej do trzymania instancji
            myDB = new DBAdapter(this); //tworzenie instancji, this jest wymagane żeby odnosiło się do "tego frameworka" ale nie wiem dlaczego
            myDB.open();
            while ((csvLine = reader.readLine()) != null) {



                liniaCSV=csvLine.split(";");
                try{

                   // Log.v("Wiersz",""+liniaCSV[0]+" ; "+liniaCSV[1]) ;
                    myDB.insertRow(Long.valueOf(liniaCSV[1]),"");


                }catch (Exception e){
                    Log.e("Unknown fuck",e.toString());
                }
            }
            myDB.close();
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }



    }
}
