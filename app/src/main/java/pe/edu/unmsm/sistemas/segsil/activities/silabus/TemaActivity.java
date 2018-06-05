package pe.edu.unmsm.sistemas.segsil.activities.silabus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import pe.edu.unmsm.sistemas.segsil.R;

public class TemaActivity extends AppCompatActivity {

    String idCurso;
    int numero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tema);

        idCurso = getIntent().getExtras().getString("curso");
        numero = getIntent().getExtras().getInt("semana");

        Toast.makeText(this, idCurso + " + " + numero, Toast.LENGTH_SHORT).show();

    }
}
