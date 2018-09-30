package pe.edu.unmsm.sistemas.segsil.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
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
    private CardView cvGestionarUsuarios;
    private CardView cvRegistrarSilabus;
    private CardView cvRegistrarAvance;
    private CardView cvVerificarAvance;
    private CardView cvVerificarSilabus;
    private TextView txtNombreUsuario;
    private TextView txtIdUsuario;
    private TextView txtCargando;
    private String nombre;
    private String apellido;
    private FirebaseUser currentUser;


    Perfil perfil;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private final String TAG = "FIREBASE DATABASE";


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

        cvGestionarUsuarios = (CardView) findViewById(R.id.bienvenido_cvGestionarUsuarios);
//
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

        cvGestionarUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser != null){
                    String idUsuario = currentUser.getEmail().substring(0,currentUser.getEmail().indexOf("@"));
                    Intent intent = new Intent(BienvenidoActivity.this, AdminActivity.class);
                    intent.putExtra("id",idUsuario);
                    intent.putExtra("nombre",nombre);
                    intent.putExtra("apellido",apellido);
                    startActivity(intent);
                }
            }
        });

        cvRegistrarSilabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser != null){
                    String idUsuario = currentUser.getEmail().substring(0,currentUser.getEmail().indexOf("@"));
                    Intent intent = new Intent(BienvenidoActivity.this, MenuSilabusActivity.class);
                    intent.putExtra("id",idUsuario);
                    intent.putExtra("nombre",nombre);
                    intent.putExtra("apellido",apellido);
                    startActivity(intent);
                }
            }
        });
        cvRegistrarAvance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser != null) {
                    String idUsuario = currentUser.getEmail().substring(0,currentUser.getEmail().indexOf("@"));
                    Intent intent = new Intent(BienvenidoActivity.this, MenuAvanceActivity.class);
                    intent.putExtra("id",idUsuario);
                    intent.putExtra("nombre", nombre);
                    intent.putExtra("apellido", apellido);
                    if (perfil.isProfesor())
                        intent.putExtra("perfil", Perfiles.PROFESOR);
                    if (perfil.isDelegado())
                        intent.putExtra("perfil", Perfiles.DELEGADO);
                    startActivity(intent);
                }
            }
        });
        cvVerificarAvance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser != null) {
                    String idUsuario = currentUser.getEmail().substring(0,currentUser.getEmail().indexOf("@"));
                    Intent intent = new Intent(BienvenidoActivity.this, MenuVerificarAvanceActivity.class);
                    intent.putExtra("id",idUsuario);
                    intent.putExtra("nombre", nombre);
                    intent.putExtra("apellido", apellido);
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
        cvVerificarSilabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser != null) {
                    String idUsuario = currentUser.getEmail().substring(0,currentUser.getEmail().indexOf("@"));
                    Intent intent = new Intent(BienvenidoActivity.this, MenuVerificarSilabusActivity.class);
                    intent.putExtra("id",idUsuario);
                    intent.putExtra("nombre", nombre);
                    intent.putExtra("apellido", apellido);
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
                    txtIdUsuario.setText(idUsuario);
                    if(perfil.isAdministrador()){
                        cvGestionarUsuarios.setVisibility(View.VISIBLE);
//                        cvRegistrarSilabus.setVisibility(View.VISIBLE);
//                        cvRegistrarAvance.setVisibility(View.VISIBLE);
//                        cvVerificarAvance.setVisibility(View.VISIBLE);
                    }
                    if(perfil.isDecanato() || perfil.isDirector_ss() || perfil.isDirector_sw()){
                        cvVerificarAvance.setVisibility(View.VISIBLE);
                        cvVerificarSilabus.setVisibility(View.VISIBLE);
                        if(perfil.isDecanato())txtNombreUsuario.setText("Bienvenido\n " + (nombre + " " + apellido).toUpperCase() +"\nDecano");
                        if(perfil.isDirector_sw())txtNombreUsuario.setText("Bienvenido\n " + (nombre + " " + apellido).toUpperCase() +"\nDirector de Escuela Software");
                        if(perfil.isDirector_ss())txtNombreUsuario.setText("Bienvenido\n " + (nombre + " " + apellido).toUpperCase() +"\nDirector de Escuela Sistemas");
                    }
                    if(perfil.isCoordinador()) {
                        cvRegistrarSilabus.setVisibility(View.VISIBLE);
                        txtNombreUsuario.setText("Bienvenido\n " + (nombre + " " + apellido).toUpperCase() +"\nCoordinador de Curso");
                    }
                    if (perfil.isProfesor() || perfil.isDelegado()) {
                        cvRegistrarAvance.setVisibility(View.VISIBLE);
                        if(perfil.isProfesor())txtNombreUsuario.setText("Bienvenido\n " + (nombre + " " + apellido).toUpperCase() +"\nProfesor de Curso");
                        if(perfil.isDelegado())txtNombreUsuario.setText("Bienvenido\n " + (nombre + " " + apellido).toUpperCase() +"\nDelegado de curso");
                    }
                    btnCerrarSesion.setVisibility(View.VISIBLE);
                    txtCargando.setVisibility(View.GONE);
                }
            });
        }
    }

    public void cerrarSesion(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿DESEA CERRAR SESIÓN Y SALIR DE LA APP?");
        builder.setNegativeButton("CANCELAR", null);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @SuppressLint("NewApi")
            public void onClick(DialogInterface dialog, int id) {
                firebaseAuth.getInstance().signOut();
                finishAffinity();
                dialog.dismiss();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
