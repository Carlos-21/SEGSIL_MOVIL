package pe.edu.unmsm.sistemas.segsil.activities.silabus;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.BreakIterator;

import pe.edu.unmsm.sistemas.segsil.R;
import pe.edu.unmsm.sistemas.segsil.activities.admin.PantallaAdmin;
import pe.edu.unmsm.sistemas.segsil.activities.admin.Revision;
import pe.edu.unmsm.sistemas.segsil.fragments.silabus.ResumenFragment;
import pe.edu.unmsm.sistemas.segsil.fragments.silabus.SemanasFragment;
import pe.edu.unmsm.sistemas.segsil.fragments.silabus.UnidadesFragment;

public class PantallaCoordinador extends AppCompatActivity implements View.OnClickListener{

    Toolbar myToolbar;
    FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    Button btn_Unidades, btn_Semanas, btn_Resumen;
    TextView txt_Bienvenido;
    private static final String TAG = "SEGUIMIENTO";
    String idCurso, idCoordinador;
    String nombreCoordinador, apellidoCoordinador, escuela, nombreCurso;
    int ciclo, indice_fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_coordinador);


        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btn_Unidades = (Button)findViewById(R.id.btn_Unidades);
        btn_Semanas = (Button)findViewById(R.id.btn_Semanas);
        btn_Resumen = (Button)findViewById(R.id.btn_Resumen);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            idCurso = bundle.getString("id_curso");
            nombreCurso = bundle.getString("nombre_curso");
            escuela = bundle.getString("eap_curso");
            ciclo = bundle.getInt("ciclo_curso");

            idCoordinador = bundle.getString("id_coordinador");
            nombreCoordinador = bundle.getString("nombre_coordinador");
            apellidoCoordinador = bundle.getString("apellido_coordinador");
            Log.v(TAG,idCurso + " - " + idCoordinador);
        }

        txt_Bienvenido = (TextView)findViewById(R.id.txt_Bienvenido);
        txt_Bienvenido.setText("Coordinador: "+ nombreCoordinador + " " + apellidoCoordinador);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("REGISTRAR SILABUS");
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        String subtitulo = "";
        if(bundle.getString("eap_curso").equals("SS")) subtitulo = subtitulo + "Sistemas - ";
        else subtitulo = subtitulo + "Software - ";
        subtitulo = subtitulo + "Ciclo " + ciclo + "  " + nombreCurso;

        myToolbar.setSubtitle(subtitulo);


        btn_Unidades.setOnClickListener(this);
        btn_Semanas.setOnClickListener(this);
        btn_Resumen.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Unidades:
                //Codigo para mostrar la pantalla de Unidades
                indice_fragment=1;
                nuevoIntent(indice_fragment);
                break;

            case R.id.btn_Semanas:
                //Codigo para mostrar la pantalla de Semanas
                indice_fragment = 2;
                nuevoIntent(indice_fragment);
                break;

            case R.id.btn_Resumen:
                //Codigo para mostrar la pantalla de Semanas
                indice_fragment = 3;
                nuevoIntent(indice_fragment);
                break;
            default:
                break;
        }
    }
    //Revisar la logica de mostrar

    public void nuevoIntent(int vista){
        Intent intent = new Intent(PantallaCoordinador.this, RegistrarSilabusActivity.class);
        intent.putExtra("nombre_curso",nombreCurso);
        intent.putExtra("eap_curso",escuela);
        intent.putExtra("ciclo_curso",ciclo);
        intent.putExtra("id_curso",idCurso);
        intent.putExtra("id_coordinador",idCoordinador);
        intent.putExtra("nombre_coordinador",nombreCoordinador);
        intent.putExtra("apellido_coordinador", apellidoCoordinador);
        intent.putExtra("fragment",vista);
        Log.v(TAG,"Pantalla Coordinador" );
        startActivity(intent);
    }
}
