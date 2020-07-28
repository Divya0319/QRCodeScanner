package com.assignment.scanhomebarcode;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

public class MainActivity extends AppCompatActivity {

    private static final int RC_CAMERA = 344;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initiateScan();

    }

    private void initiateScan() {
        Intent cameraIntent = new Intent(this, BarcodeCaptureActivity.class);
        startActivityForResult(cameraIntent, RC_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CommonStatusCodes.SUCCESS && requestCode == RC_CAMERA) {
            if (data == null) return;
            Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
            final String scanResult;
            if (barcode != null) {
                scanResult = barcode.displayValue;
                if (scanResult == null) return;
                onQrCodeScanned(scanResult);
            }

        }
    }

    private void onQrCodeScanned(String result) {
        Log.d("QR scanned:", result);
        runOnUiThread(() -> showDropDown(result));

    }

    private void showDropDown(String qrData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select an option for " + qrData);
        String[] options = {"---Select---", "Collected", "Available", "Not Available"};
        builder.setItems(options, (dialog, which) -> {
            dialog.dismiss();
            switch (which) {
                case 0:
                    break;
                case 1:
                    onCollectedSelected();
                    break;
                case 2:
                    onAvailableSelected();
                    break;
                case 3:
                    onNotAvailableSelected();
                    break;
            }
        });
        builder.show();
    }

    private void onNotAvailableSelected() {
        Toast.makeText(this, "Not Available Selected", Toast.LENGTH_LONG).show();
    }

    private void onAvailableSelected() {
        Toast.makeText(this, "Available Selected", Toast.LENGTH_LONG).show();
    }

    private void onCollectedSelected() {
        Toast.makeText(this, "Collected Selected", Toast.LENGTH_LONG).show();
    }

}