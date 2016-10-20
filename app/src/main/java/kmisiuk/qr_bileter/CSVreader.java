package kmisiuk.qr_bileter;

/**
 * Created by kmisiuk on 2016-10-20.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;



public class CSVreader extends AppCompatActivity {
    InputStream plikCSV;

    String[] liniaCSV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csv);
        plikCSV = getResources().openRawResource(R.raw.sample);

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
