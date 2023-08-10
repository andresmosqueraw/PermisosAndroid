package co.edu.uniempresarial.permisos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.Manifest;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 77;
    private TextView tvDactilar;
    private TextView tvCamera;
    private TextView tvBluetooth;
    private TextView tvExternalWriteStorage;
    private TextView tvRealStorage;
    private TextView tvInternet;
    private Button btnCheckPermission;
    private Button btnRequestPermission;

    private String[] permissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callViews();

        // 5. Enlazar el boton de check
        btnCheckPermission.setOnClickListener(this::checkPermissionsVoid);
        btnRequestPermission.setOnClickListener(this::requestPermissionsVoid);

        permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.USE_BIOMETRIC
        };
    }

    private boolean checkHasPermissions(){
        for(String e: permissions){
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), e) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void requestPermissionsVoid(View view){
        if (!checkHasPermissions()) {
            ActivityCompat.requestPermissions(MainActivity.this, permissions, REQUEST_CODE);
        }
    }

    // 7. Gestionar los permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED ||
                    grantResults[1] != PackageManager.PERMISSION_GRANTED ||
                    grantResults[2] != PackageManager.PERMISSION_GRANTED ||
                    grantResults[3] != PackageManager.PERMISSION_GRANTED ||
                    grantResults[4] != PackageManager.PERMISSION_GRANTED ||
                    grantResults[5] != PackageManager.PERMISSION_GRANTED) {

                new AlertDialog.Builder(this)
                        .setTitle("Permissions")
                        .setMessage("You denied permission" + "\n" +
                                "Allow all permission at setting->Permissions")
                        .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                        Uri.fromParts("package", getPackageName(), null));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No, exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        }).create().show();
            }
        }
    }

    public void callViews(){
        tvDactilar = findViewById(R.id.tv_dactilar);
        tvCamera = findViewById(R.id.tv_camera);
        tvBluetooth = findViewById(R.id.tv_bluetooth);
        tvExternalWriteStorage = findViewById(R.id.tv_external_write_storage);
        tvRealStorage = findViewById(R.id.tv_real_storage);
        tvInternet = findViewById(R.id.tv_internet);
        btnCheckPermission = findViewById(R.id.btn_check_permission);
        btnRequestPermission = findViewById(R.id.btn_request_permission);
        // Deshabilitar boton
        btnRequestPermission.setEnabled(false);
    }

    // 4. Metodo informativo sobre si hay permisos
    private void checkPermissionsVoid(View view){
        int statusCamera = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        int statusBluetooth = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH);
        int statusWES = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int statusRES = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int statusInternet = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET);
        int statusDactilar = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.USE_BIOMETRIC);

        tvCamera.setText("Status Camera: " + statusCamera);
        tvBluetooth.setText("Status Bluetooth: " + statusBluetooth);
        tvExternalWriteStorage.setText("Status Write External Storage: " + statusWES);
        tvRealStorage.setText("Status Read External Storage: " + statusRES);
        tvInternet.setText("Status Internet: " + statusInternet);
        tvDactilar.setText("Status Dactilar: " + statusDactilar);

        btnRequestPermission.setEnabled(true);
    }


}