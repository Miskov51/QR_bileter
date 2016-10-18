package kmisiuk.qr_bileter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by kmisiuk on 2016-10-15.
 */

public class MainActivity extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //otwieranie widoku od razu przy starcie
    }

    public void startQRscan(View v) {
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    public void sqlSettings(View v) {
        startActivity(new Intent(MainActivity.this, sql_main_menu.class));
    }

    public void sqlManipulator(View v) {
        startActivity(new Intent(MainActivity.this, SQL_manipulator.class));

    }

    public void QuitApp(View v) {
    this.finishAffinity();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //mScannerView.stopCamera(); //zatrzymanie kamery, jest zakomentowane bo psuje przełącznie activity
    }

    @Override
    public void handleResult(Result result) {
        Log.w("handleResult", result.getText());
        AlertDialog.Builder bulider = new AlertDialog.Builder((this));
        bulider.setTitle("Scan result");
        bulider.setMessage(result.getText());
        AlertDialog alertDialog = bulider.create();
        alertDialog.show();

        mScannerView.stopCamera();
        setContentView(R.layout.activity_main);

        //powrót do skanowania
        //mScannerView.resumeCameraPreview(this); //zakomentowane aby skan wykonał się raz
    }
}
