package com.mediomelon.lectorqr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btnScanner)
    Button btnScanner;
    @BindView(R.id.txtCode)
    TextView txtCode;
    @BindView(R.id.btnGenerarQR)
    Button btnGenerateQR;
    @BindView(R.id.edtCode)
    EditText edtCode;
    @BindView(R.id.imgCodeQR)
    ImageView imgCodeQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        btnScanner.setOnClickListener(this);
        btnGenerateQR.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                txtCode.setText("codigo: " + result.getContents());
            } else
                txtCode.setText("Error al scanear");
        }else
            super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnScanner) {
            IntentIntegrator scanner = new IntentIntegrator(MainActivity.this);
            scanner.setPrompt("Coloque un codigo de barras en el interior del rectangulo del visor para escanear");
            scanner.setCameraId(0);
            scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
            scanner.setBeepEnabled(false);
            scanner.initiateScan();

        }
        if (view.getId() == R.id.btnGenerarQR) {
            String value = edtCode.getText().toString().trim();
            BarcodeEncoder codeQR = new BarcodeEncoder();

            if (!value.isEmpty()) {
                try {
                    Bitmap bitmap = codeQR.encodeBitmap(value, BarcodeFormat.QR_CODE, 400, 400);
                    imgCodeQR.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            } else
                edtCode.setError("Required");


        }
    }
}
