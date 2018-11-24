package pe.edu.unmsm.sistemas.segsil.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import pe.edu.unmsm.sistemas.segsil.R;
import pe.edu.unmsm.sistemas.segsil.activities.admin.AdminActivity;
import pe.edu.unmsm.sistemas.segsil.activities.admin.PantallaAdmin;
import pe.edu.unmsm.sistemas.segsil.activities.avance.MenuAvanceActivity;
import pe.edu.unmsm.sistemas.segsil.activities.control.MenuVerificarAvanceActivity;
import pe.edu.unmsm.sistemas.segsil.activities.control.MenuVerificarSilabusActivity;
import pe.edu.unmsm.sistemas.segsil.activities.silabus.MenuSilabusActivity;
import pe.edu.unmsm.sistemas.segsil.pojos.Perfil;
import pe.edu.unmsm.sistemas.segsil.pojos.Perfiles;
import pe.edu.unmsm.sistemas.segsil.pojos.Persona;
import pe.edu.unmsm.sistemas.segsil.pojos.Usuario;

public class BienvenidoActivity extends AppCompatActivity {

    private ImageView btnCerrarSesion;
    private CardView cvBaseDatos;
    private CardView cvRegistrarSilabus, cvRegistrarAvance;
    private CardView cvVerificarAvance, cvVerificarSilabus;

    private TextView txtNombreUsuario, txtIdUsuario, txtCargando;
    private String nombre, apellido;
    private FirebaseUser currentUser;

    Perfil perfil;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    private final String TAG = "SEGUIMIENTO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenido);

        btnCerrarSesion = (ImageView) findViewById(R.id.bienvenido_btnCerrarSesion);
        txtNombreUsuario = (TextView) findViewById(R.id.bienvenido_txtNombreUsuario);
        txtIdUsuario = (TextView) findViewById(R.id.bienvenido_txtIdUsuario);
        txtCargando = (TextView) findViewById(R.id.bienvenido_txtCargando);
        cvVerificarAvance = (CardView) findViewById(R.id.bienvenido_cvVerificarAvance);
        cvVerificarSilabus = (CardView) findViewById(R.id.bienvenido_cvVerificarSilabus);
        cvBaseDatos = (CardView) findViewById(R.id.bienvenido_cvBaseDatos);
        cvRegistrarAvance = (CardView) findViewById(R.id.bienvenido_cvRegistrarAvance);
        cvRegistrarSilabus = (CardView) findViewById(R.id.bienvenido_cvRegistrarSilabus);


        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        updateUI(currentUser);

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
            }
        });

        //Muestra el modulo de Administrador
        cvBaseDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser != null){
                    String idUsuario = currentUser.getEmail().substring(0,currentUser.getEmail().indexOf("@"));
                    //Intent intent = new Intent(BienvenidoActivity.this, AdminActivity.class);
                    Intent intent = new Intent(BienvenidoActivity.this, PantallaAdmin.class);
                    intent.putExtra("id",idUsuario);
                    intent.putExtra("nombre",nombre);
                    intent.putExtra("apellido",apellido);
                    Log.v(TAG,"--> Selección: CARGA DE DATOS" );
                    startActivity(intent);
                }
            }
        });

        //Muestra el modulo de Registro de Silabus
        cvRegistrarSilabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser != null){
                    String idUsuario = currentUser.getEmail().substring(0,currentUser.getEmail().indexOf("@"));
                    Intent intent = new Intent(BienvenidoActivity.this, MenuSilabusActivity.class);
                    intent.putExtra("id",idUsuario);
                    intent.putExtra("nombre",nombre);
                    intent.putExtra("apellido",apellido);
                    Log.v(TAG,"--> Selección: REGISTRAR SILABUS" );
                    startActivity(intent);
                }
            }
        });

        //Muestra el modulo de Registro de Avance de Silabus
        cvRegistrarAvance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser != null) {
                    String idUsuario = currentUser.getEmail().substring(0,currentUser.getEmail().indexOf("@"));
                    Intent intent = new Intent(BienvenidoActivity.this, MenuAvanceActivity.class);
                    intent.putExtra("id",idUsuario);
                    intent.putExtra("nombre", nombre);
                    intent.putExtra("apellido", apellido);
                    Log.v(TAG,"--> Selección: REGISTRAR AVANCE" );
                    if (perfil.isProfesor())
                        intent.putExtra("perfil", Perfiles.PROFESOR);
                    if (perfil.isDelegado())
                        intent.putExtra("perfil", Perfiles.DELEGADO);
                    startActivity(intent);
                }
            }
        });

        //Muestra el modulo de Verificar Avance de Silabus
        cvVerificarAvance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser != null) {
                    String idUsuario = currentUser.getEmail().substring(0,currentUser.getEmail().indexOf("@"));
                    Intent intent = new Intent(BienvenidoActivity.this, MenuVerificarAvanceActivity.class);
                    intent.putExtra("id",idUsuario);
                    intent.putExtra("nombre", nombre);
                    intent.putExtra("apellido", apellido);
                    Log.v(TAG,"--> Selección: VERIFICAR AVANCE" );
                    if (perfil.isDecanato())
                        intent.putExtra("perfil", Perfiles.DECANO);
                    if (perfil.isDirector_ss())
                        intent.putExtra("perfil", Perfiles.DIRECTOR_SISTEMAS);
                    if (perfil.isDirector_sw())
                        intent.putExtra("perfil", Perfiles.DIRECTOR_SOFTWARE);
                    startActivity(intent);
                }
            }
        });

        //Muestra el modulo de Verificar Registro de Silabus
        cvVerificarSilabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser != null) {
                    String idUsuario = currentUser.getEmail().substring(0,currentUser.getEmail().indexOf("@"));
                    Intent intent = new Intent(BienvenidoActivity.this, MenuVerificarSilabusActivity.class);
                    intent.putExtra("id",idUsuario);
                    intent.putExtra("nombre", nombre);
                    intent.putExtra("apellido", apellido);
                    Log.v(TAG,"--> Selección: VERIFICAR SILABUS" );
                    if (perfil.isDecanato())
                        intent.putExtra("perfil", Perfiles.DECANO);
                    if (perfil.isDirector_ss())
                        intent.putExtra("perfil", Perfiles.DIRECTOR_SISTEMAS);
                    if (perfil.isDirector_sw())
                        intent.putExtra("perfil", Perfiles.DIRECTOR_SOFTWARE);
                    startActivity(intent);
                }
            }
        });
    }

    //Obtener los datos del usuario ingresado y muestra sus opciones segun su perfil
    public void updateUI(FirebaseUser user){
        if(user != null){
            final String idUsuario = user.getEmail().substring(0,user.getEmail().indexOf("@"));
            DocumentReference docRef2 = db.collection("usuarios").document(idUsuario);
            docRef2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Usuario usuario = documentSnapshot.toObject(Usuario.class);
                    perfil = usuario.getPerfil();
                    nombre = usuario.getNombres();
                    apellido = usuario.getApellidos();
                    txtIdUsuario.setText(idUsuario);

                    if(perfil.isAdministrador()){
                        cvBaseDatos.setVisibility(View.VISIBLE);
                        txtNombreUsuario.setText("Bienvenido\n " + "\nADMINISTRADOR");
                    }

                    if(perfil.isDelegado()){
                        cvRegistrarAvance.setVisibility(View.VISIBLE);
                        txtNombreUsuario.setText("Bienvenido\n " + (nombre + " " + apellido).toUpperCase() +"\nALUMNO");
                    }

                    if(perfil.isProfesor() && perfil.isCoordinador()){
                        cvRegistrarAvance.setVisibility(View.VISIBLE);
                        cvRegistrarSilabus.setVisibility(View.VISIBLE);
                        txtNombreUsuario.setText("Bienvenido\n " + (nombre + " " + apellido).toUpperCase() +"\nCOORDINADOR DE CURSO");
                    }

                    if(perfil.isProfesor() && !perfil.isCoordinador()){
                        cvRegistrarAvance.setVisibility(View.VISIBLE);
                        txtNombreUsuario.setText("Bienvenido\n " + (nombre + " " + apellido).toUpperCase() +"\nPROFESOR");
                    }

                    if(perfil.isDecanato() || perfil.isDirector_ss() || perfil.isDirector_sw()){
                        cvVerificarAvance.setVisibility(View.VISIBLE);
                        cvVerificarSilabus.setVisibility(View.VISIBLE);
                        if(perfil.isDecanato())
                            txtNombreUsuario.setText("Bienvenido\n " + (nombre + " " + apellido).toUpperCase() +"\nDecano");
                        if(perfil.isDirector_sw())
                            txtNombreUsuario.setText("Bienvenido\n " + (nombre + " " + apellido).toUpperCase() +"\nDirector de Escuela Software");
                        if(perfil.isDirector_ss())
                            txtNombreUsuario.setText("Bienvenido\n " + (nombre + " " + apellido).toUpperCase() +"\nDirector de Escuela Sistemas");
                    }
                    btnCerrarSesion.setVisibility(View.VISIBLE);
                    txtCargando.setVisibility(View.GONE);
                }
            });
        }
    }

    public void cerrarSesion(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setMessage("¿DESEA CERRAR SESIÓN?");
        dialogo.setNegativeButton("CANCELAR", null);
        dialogo.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @SuppressLint("NewApi")
            public void onClick(DialogInterface dialog, int which) {
                String id = currentUser.getEmail().substring(0,currentUser.getEmail().indexOf("@"));
                Log.v(TAG,"--> SESION CERRADA, USUARIO: " + id );
                firebaseAuth.getInstance().signOut();
                finishAffinity();
                dialog.dismiss();
                Intent intent = new Intent(BienvenidoActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dialogo.show();
    }

    @SuppressLint("NewApi")
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            cerrarSesion();
        }
        return super.onKeyDown(keyCode, event);
    }
}

