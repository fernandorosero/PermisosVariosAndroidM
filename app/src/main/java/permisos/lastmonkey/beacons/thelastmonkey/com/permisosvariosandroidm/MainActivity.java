package permisos.lastmonkey.beacons.thelastmonkey.com.permisosvariosandroidm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    private static final int MI_PERMISO_CALL_PHONE = 1;
    private static final int MI_PERMISO_EXTERNAL_STORAGE = 1;
    Button btnLlamada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLlamada = (Button) findViewById(R.id.btnLlamada);
        btnLlamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:1234"));

                if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED ||
                   ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    Log.i("Permisos", "El permiso está denegado y se tendría que mostrar para activar desde aPP");
                    Toast.makeText(getApplicationContext(), "Recuerde tener activado los permisos de llamada",
                            Toast.LENGTH_SHORT).show();

                }

                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED) {
                    //YA LO CANCELE Y SOLICITO NUEVAMENTE LOS PERMISOS
                    Log.i("Permisos:" , "Entra nuevamente");
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CALL_PHONE)
                         || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Log.i("Permisos:","Aqui vuelve a solicitar los permisos");
                        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Atención!!")
                                .setContentText("Debes otorgar permisos para realizar la llamada!")
                                .setConfirmText("Solicitar Permiso")
                                .setCancelText("Cancelar")
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.cancel();
                                    }
                                })
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.cancel();
                                        ActivityCompat.requestPermissions(MainActivity.this,
                                                new String[]{Manifest.permission.CALL_PHONE,
                                                             Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                             MI_PERMISO_CALL_PHONE);

                                    }
                                })
                                .show();
                    } else {
                        //PRIMERA VEZ
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MI_PERMISO_CALL_PHONE);
                    }
                }else{
                    Log.i("Permisos:", "Aquí realiza el intent");
                    startActivity(callIntent);
                }

            }
        });

    }
}
