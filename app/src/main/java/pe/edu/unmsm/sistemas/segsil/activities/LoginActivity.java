package pe.edu.unmsm.sistemas.segsil.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import pe.edu.unmsm.sistemas.segsil.R;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class LoginActivity extends AppCompatActivity {
    private Button btnIngresar;
    private TextInputEditText edtUsuario;
    private TextInputEditText edtPassword;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private final String TAG = "SEGUIMIENTO";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(isConnected()){
            //cargarDialogoConectado();
            Log.v(TAG," --> Permiso, Tiene conexion a internet");
        }else{
            cargarDialogoNoConectado();
            Log.v(TAG,"--> Permiso, NO tiene conexion a internet");
        }

        if(verificaPermiso()){
            Toast.makeText(LoginActivity.this, "PERMISO DE LECTURA Y ESCRITURA", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(LoginActivity.this, "NO TIENE PERMISOS DE ESCRITURA NI LECTURA", Toast.LENGTH_SHORT).show();
            Log.v(TAG," -> Permiso, NO acepto los permisos de lectura y escritura");
        }

        btnIngresar = (Button) findViewById(R.id.login_btnIngresar);
        edtPassword = (TextInputEditText) findViewById(R.id.login_edtPassword);
        edtUsuario = (TextInputEditText) findViewById(R.id.login_edtUsuario);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtUsuario.getText().toString().trim().equals("") && !edtPassword.getText().toString().trim().equals("")){
                    iniciarSesion(edtUsuario.getText().toString(),edtPassword.getText().toString());
                }else{
                    Toast.makeText(LoginActivity.this, "DEBE INGRESAR LOS DATOS SOLICITADOS", Toast.LENGTH_SHORT).show();
                    Log.v(TAG," --> ERROR EN USUARIO O CONTRASEÑA");
                }

            }
        });

    }


    //Metodo para verificar si nuestro movil esta conectado a internet
    //----------------------------------------------------------------------------------------------
    public boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if(netinfo!=null &&  netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if ((wifi != null && wifi.isConnectedOrConnecting()) || (mobile != null && mobile.isConnectedOrConnecting()))
                return true;
            else
                return false;
        }else {
            return false;
        }
    }

    private void cargarDialogoConectado(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(LoginActivity.this);
        //dialogo.setTitle("Verificando Conexion");
        dialogo.setMessage("Tiene conexion a Internet" + '\n' + "Presione OK para Continuar");
        dialogo.setCancelable(false);
        dialogo.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogo.show();
    }

    private void cargarDialogoNoConectado() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(LoginActivity.this);
        //dialogo.setTitle("Verificando Conexion");
        dialogo.setMessage("No tiene conexion a Internet" + '\n' + "Presione OK para Salir");
        dialogo.setCancelable(false);
        dialogo.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        });
        dialogo.show();
    }
    //----------------------------------------------------------------------------------------------



    //Iniciar Sesion del Usuario
    //----------------------------------------------------------------------------------------------
    public void iniciarSesion(String usuario, String password){
        firebaseAuth.signInWithEmailAndPassword(usuario+"@sistemas.edu.pe",password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //SESION INICIADA
                            FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
                            String id = user.getEmail().substring(0,user.getEmail().indexOf("@"));
                            Log.v(TAG,"--> SESION INICIADA, USUARIO: " + id );
                            sesionActiva(user);
                        } else {
                            //ERROR AL INICIAR SESION
                            Log.w(TAG, " --> signInWithEmail: failure " + '\n' + task.getException());
                            Toast.makeText(LoginActivity.this, "USUARIO O CONTRASEÑA INCORRECTA", Toast.LENGTH_SHORT).show();
                            sesionActiva(null);
                        }
                    }
                });
    }
    //----------------------------------------------------------------------------------------------

    //Salir de la Aplicacion
    //----------------------------------------------------------------------------------------------
    @SuppressLint("NewApi")
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Está seguro que desea SALIR de la aplicación?")
                    .setTitle("Aviso")
                    .setCancelable(false)
                    .setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                    .setPositiveButton("Sí",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    finishAffinity();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        return super.onKeyDown(keyCode, event);
    }
    //----------------------------------------------------------------------------------------------

    public void sesionActiva(FirebaseUser user){
        if(user != null){
            Intent intent = new Intent(LoginActivity.this, BienvenidoActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        sesionActiva(currentUser);
    }

    //Metodo para verificar solcitar permisos de lectura y escritura
    //----------------------------------------------------------------------------------------------
    @SuppressLint("NewApi")
    public boolean verificaPermiso() {
        if (checkSelfPermission(WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "--> Permiso, Solicitar Permiso de Lectura y Escritura");
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
            return false;
        } else {
            Log.v(TAG, "-> Permiso, Acepto los permisos de lectura y escritura");
            return true;
        }
    }
    //----------------------------------------------------------------------------------------------

}
