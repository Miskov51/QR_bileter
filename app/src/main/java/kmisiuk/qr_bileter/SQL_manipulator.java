package kmisiuk.qr_bileter;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class SQL_manipulator extends AppCompatActivity {

    DBAdapter myDB; //tworzenie zmiennej do trzymania instancji


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_manipulator);
        openDB();
    }

    private void openDB() {
        myDB = new DBAdapter(this); //tworzenie instancji, this jest wymagane żeby odnosiło się do tego frameworka ale nie wiem dlaczego
        myDB.open();

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        closeDB();
    }

    private void closeDB() {
        myDB.close();
    }

    private void displayText(String message){
        TextView textView = (TextView) findViewById(R.id.textDisplay);
        textView.setText(message);

    }

    public void onclick_DodajWpis(View v){
        DateFormat df = new SimpleDateFormat(" d.MM.yyyy, HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());
        displayText("Dodano wpis");
        Random r = new Random();
        long randomQR = r.nextInt(8000000) + 1436775;
        long NewID = myDB.insertRow(randomQR,date);

        /*//dwa poniższe wpisy pozwalają wyświetlić oktualnie dodany rekord w chwili kliknięcia
        Cursor kursor = myDB.getRow(NewID);
        onClick_WyswietlWpisy(kursor);
        */
    }


    public void onClick_WyczyscWszystko(View v){
        displayText("Wyczyszczono baze");
        myDB.deleteAll();
    }

    public void onClick_WyswietlWpisy(View v){
        displayText("Wyswietlanie wpisów");

        Cursor kursor = myDB.getAllRows();
        WyswpietlWpisyBazy(kursor);
    }

    private void WyswpietlWpisyBazy(Cursor kursor) {
        String tresc = "-------------------------- dane z bazy --------------------------\n";

        //przestawianie kursora na poczatek (o ile są dane bo inaczej nie będzie się dało stąd if)
        if(kursor.moveToFirst()){
            do {
                int id = kursor.getInt(0);
                String name = kursor.getString(1);
                String StudentNumber = kursor.getString(2);

                //składanie odczytanych danych do kupy
                tresc += "ID=" + id + "   QR=" + name + "   Aktywacja=" + StudentNumber +"\n";
            }while(kursor.moveToNext()); //wyjdzie z pętli gdy kursor nie ma możliwości przejścia dalej
        }
        kursor.close(); //podobno trzeba zamykać kursor po użyciu inaczej jest "resource leak"
        displayText(tresc);
    }



    public void manipulator_back(View v){
        startActivity(new Intent(SQL_manipulator.this, sql_main_menu.class));
    }
}
