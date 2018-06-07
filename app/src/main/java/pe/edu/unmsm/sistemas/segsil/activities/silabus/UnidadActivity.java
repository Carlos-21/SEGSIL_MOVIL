package pe.edu.unmsm.sistemas.segsil.activities.silabus;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import pe.edu.unmsm.sistemas.segsil.util.NumericKeyBoardTransformationMethod;
import pe.edu.unmsm.sistemas.segsil.R;
import pe.edu.unmsm.sistemas.segsil.pojos.Semana;
import pe.edu.unmsm.sistemas.segsil.pojos.Silabus;
import pe.edu.unmsm.sistemas.segsil.pojos.Unidad;

public class UnidadActivity extends AppCompatActivity {

    TextView txtNumero;
    TextInputEditText edtNombre;
    TextInputEditText edtSemanas;
    Button btnRegistrar;
    String idCurso;
    final String TAG = "FIRESTORE";
    int numero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unidad);


        idCurso = getIntent().getExtras().getString("curso");
        numero = getIntent().getExtras().getInt("numero");

        txtNumero = (TextView) findViewById(R.id.registrar_unidad_txtNumero);
        edtNombre = (TextInputEditText) findViewById(R.id.registrar_unidad_edtNombre);
        edtSemanas = (TextInputEditText) findViewById(R.id.registrar_unidad_edtSemanas);
        btnRegistrar = (Button) findViewById(R.id.registrar_unidad_btnRegistrar);

        txtNumero.setText("REGISTRAR UNIDAD N° " + numero);
        edtSemanas.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        edtSemanas.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        edtNombre.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30),new InputFilter.AllCaps()});

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    FirebaseFirestore.getInstance().collection("silabus").document(idCurso).set(new Silabus(idCurso));
                    FirebaseFirestore.getInstance().collection("silabus").document(idCurso).collection("unidades").document(numero+"")
                            .set(new Unidad(numero,edtNombre.getText().toString(),Integer.parseInt(edtSemanas.getText().toString())));
                    FirebaseFirestore.getInstance().collection("silabus").document(idCurso).collection("semanas")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        List<Semana> semanas = new ArrayList<>();
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d(TAG, document.getId() + " => " + document.getData());
                                            semanas.add(document.toObject(Semana.class));
                                        }
                                        int num = Integer.parseInt(edtSemanas.getText().toString());
                                        for (int i = 1; i <= num; i++) {
                                            int n = semanas.size() + i;
                                            if (n > 7) n++;
                                            FirebaseFirestore.getInstance().collection("silabus").document(idCurso)
                                                    .collection("semanas").document(n +"").set(new Semana(n,numero,edtNombre.getText().toString(),false));
                                        }

                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                finish();
            }
        });


    }
}
