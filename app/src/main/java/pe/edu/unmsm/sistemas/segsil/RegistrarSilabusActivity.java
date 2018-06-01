package pe.edu.unmsm.sistemas.segsil;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistrarSilabusActivity extends AppCompatActivity {
    TextView btnAnterior;
    TextView btnSiguiente;
    TextView txtTitulo;
    TextView txtSubtitulo;
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
        txtTitulo = (TextView) findViewById(R.id.registrar_silabus_txtTitulo);
        txtSubtitulo = (TextView) findViewById(R.id.registrar_silabus_txtSubtitulo);


        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        idCurso = bundle.getString("id_curso");

        txtTitulo.setText(bundle.getString("nombre_curso"));

        String subtitulo = "";
        if(bundle.getString("eap_curso").equals("SS")) subtitulo = subtitulo + "Sistemas - ";
        else subtitulo = subtitulo + "Software - ";
        subtitulo = subtitulo + "Ciclo " + bundle.getInt("ciclo_curso");
        txtSubtitulo.setText(subtitulo);


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
                fragmentTransaction.replace(R.id.registrar_silabus_fragment,semanasFragment);
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
