package pe.edu.unmsm.sistemas.segsil.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
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
import pe.edu.unmsm.sistemas.segsil.activities.control.MenuControlActivity;
import pe.edu.unmsm.sistemas.segsil.activities.silabus.MenuSilabusActivity;
import pe.edu.unmsm.sistemas.segsil.pojos.Perfil;
import pe.edu.unmsm.sistemas.segsil.pojos.Persona;
import pe.edu.unmsm.sistemas.segsil.pojos.Usuario;

public class BienvenidoActivity extends AppCompatActivity {

    private Button btnCerrarSesion;
    private CardView cvGestionarUsuarios;
    private CardView cvRegistrarSilabus;
    private CardView cvRegistrarAvance;
    private CardView cvControlarAvance;
    private TextView txtNombreUsuario;
    private TextView txtCargando;
    private String nombre;
    private String apellido;
    private FirebaseUser currentUser;



    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private final String TAG = "FIREBASE DATABASE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenido);

        btnCerrarSesion = (Button) findViewById(R.id.bienvenido_btnCerrarSesion);
        txtNombreUsuario = (TextView) findViewById(R.id.bienvenido_txtNombreUsuario);
        txtCargando = (TextView) findViewById(R.id.bienvenido_txtCargando);

        cvControlarAvance = (CardView) findViewById(R.id.bienvenido_cvControlAvance);
        cvGestionarUsuarios = (CardView) findViewById(R.id.bienvenido_cvGestionarUsuarios);
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
                    startActivity(intent);
                }
            }
        });
        cvControlarAvance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser != null) {
                    String idUsuario = currentUser.getEmail().substring(0,currentUser.getEmail().indexOf("@"));
                    Intent intent = new Intent(BienvenidoActivity.this, MenuControlActivity.class);
                    intent.putExtra("id",idUsuario);
                    intent.putExtra("nombre", nombre);
                    intent.putExtra("apellido", apellido);
                    startActivity(intent);
                }
            }
        });
    }

    public void updateUI(FirebaseUser user){
        if(user != null){
            String idUsuario = user.getEmail().substring(0,user.getEmail().indexOf("@"));

            DocumentReference docRef1 = db.collection("personas").document(idUsuario);
            docRef1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Persona persona = documentSnapshot.toObject(Persona.class);
                    nombre = persona.getNombres();
                    apellido = persona.getApellidos();
                    txtNombreUsuario.setText(nombre + " " + apellido);
                }
            });

            DocumentReference docRef2 = db.collection("usuarios").document(idUsuario);
            docRef2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Usuario usuario = documentSnapshot.toObject(Usuario.class);
                    Perfil perfil = usuario.getPerfil();
                    if(perfil.isAdministrador()) cvGestionarUsuarios.setVisibility(View.VISIBLE);
                    if(perfil.isDecanato() || perfil.isDirector_ss() || perfil.isDirector_sw()) cvControlarAvance.setVisibility(View.VISIBLE);
                    if(perfil.isCoordinador()) cvRegistrarSilabus.setVisibility(View.VISIBLE);
                    if (perfil.isProfesor() || perfil.isDelegado()) cvRegistrarAvance.setVisibility(View.VISIBLE);
                    btnCerrarSesion.setVisibility(View.VISIBLE);
                    txtCargando.setVisibility(View.GONE);
                }
            });
        }
    }

    public void cerrarSesion(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿DESEA CERRAR SESIÓN?");
        builder.setNegativeButton("CANCELAR", null);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                firebaseAuth.getInstance().signOut();
                Intent intent = new Intent(BienvenidoActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                dialog.dismiss();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
