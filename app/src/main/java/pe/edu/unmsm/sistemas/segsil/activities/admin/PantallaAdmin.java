package pe.edu.unmsm.sistemas.segsil.activities.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import pe.edu.unmsm.sistemas.segsil.R;

public class PantallaAdmin extends AppCompatActivity implements View.OnClickListener{

    private Toolbar myToolbar;
    FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    Button btn_usuario, btn_curso, btn_grupo;
    TextView txt_Bienvenido;

    String idCurso, idCoordinador;
    private static final String TAG = "SEGUIMIENTO";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_admin);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btn_usuario = (Button)findViewById(R.id.btn_usuario);
        btn_curso = (Button)findViewById(R.id.btn_curso);
        btn_grupo = (Button)findViewById(R.id.btn_grupo);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            idCurso = bundle.getString("idCurso");
            idCoordinador = bundle.getString("idCoordinador");
            Log.v(TAG,idCurso + " - " + idCoordinador);
        }

        txt_Bienvenido = (TextView)findViewById(R.id.txt_Bienvenido);
        txt_Bienvenido.setText(txt_Bienvenido.getText() + "\nAdministrador");

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("ADMINISTRACION DATA");
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_usuario.setOnClickListener(this);
        btn_curso.setOnClickListener(this);
        btn_grupo.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_usuario:
                // code for button when user clicks btnOne.
                nuevoIntent("usuarios");
                break;

            case R.id.btn_curso:
                // do your code
                nuevoIntent("cursos");
                break;

            case R.id.btn_grupo:
                // do your code
                nuevoIntent("grupos");
                break;

            default:
                break;
        }
    }

    public void nuevoIntent(String tabla){
        Intent intent = new Intent(PantallaAdmin.this, Revision.class);
        intent.putExtra("nombreTabla",tabla);
        Log.v(TAG,"Pantalla Admin" );
        startActivity(intent);
    }
}
