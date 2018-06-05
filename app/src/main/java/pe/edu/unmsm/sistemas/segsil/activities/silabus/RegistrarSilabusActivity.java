package pe.edu.unmsm.sistemas.segsil.activities.silabus;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import pe.edu.unmsm.sistemas.segsil.R;
import pe.edu.unmsm.sistemas.segsil.fragments.ResumenFragment;
import pe.edu.unmsm.sistemas.segsil.fragments.SemanasFragment;
import pe.edu.unmsm.sistemas.segsil.fragments.UnidadesFragment;

public class RegistrarSilabusActivity extends AppCompatActivity {
    TextView btnAnterior;
    TextView btnSiguiente;
    Toolbar toolbar;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    String idCurso;
    int fragmentActual = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_silabus);

        btnAnterior = (TextView) findViewById(R.id.registrar_silabus_btnAnterior);
        btnSiguiente = (TextView) findViewById(R.id.registrar_silabus_btnSiguiente);
        toolbar = (Toolbar) findViewById(R.id.registrar_silabus_toolbar);


        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        idCurso = bundle.getString("id_curso");
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.action_bar_title_layout);
        ((TextView) findViewById(R.id.action_bar_titulo)).setText(bundle.getString("nombre_curso"));

        getSupportActionBar().setTitle("");
        String subtitulo = "";
        if(bundle.getString("eap_curso").equals("SS")) subtitulo = subtitulo + "Sistemas - ";
        else subtitulo = subtitulo + "Software - ";
        subtitulo = subtitulo + "Ciclo " + bundle.getInt("ciclo_curso");
        ((TextView) findViewById(R.id.action_bar_subtitulo)).setText(subtitulo);
//        getSupportActionBar().setSubtitle(subtitulo);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentActual +1 <= 3){
                    fragmentActual++;
                    setFragment(fragmentActual,1);
                }
            }
        });
        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentActual -1 >= 1){
                    fragmentActual--;
                    setFragment(fragmentActual,0);
                }
            }
        });
        setFragment(1,1);
    }

    public void setFragment(int numeroFragment, int direccion){

        FragmentManager fragmentManager =  getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
        if(direccion > 0){
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        }else{
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
        }
        switch (numeroFragment){
            case 1:
                btnAnterior.setVisibility(View.GONE);
                btnSiguiente.setVisibility(View.VISIBLE);
                UnidadesFragment unidadesFragment =  new UnidadesFragment(idCurso,RegistrarSilabusActivity.this);
                fragmentTransaction.replace(R.id.registrar_silabus_fragment,unidadesFragment);
                break;
            case 2:
                btnSiguiente.setText("Siguiente");
                btnAnterior.setVisibility(View.VISIBLE);
                btnSiguiente.setVisibility(View.VISIBLE);
                SemanasFragment semanasFragment =  new SemanasFragment(idCurso,RegistrarSilabusActivity.this);
                fragmentTransaction.replace(R.id.registrar_silabus_fragment, semanasFragment);
                break;
            case 3:
                btnSiguiente.setText("Finalizar");
                btnAnterior.setVisibility(View.VISIBLE);
                btnSiguiente.setVisibility(View.VISIBLE);
                ResumenFragment resumenFragment =  new ResumenFragment(idCurso,RegistrarSilabusActivity.this);
                fragmentTransaction.replace(R.id.registrar_silabus_fragment,resumenFragment);
                break;
        }
        fragmentTransaction.commit();
    }
}
