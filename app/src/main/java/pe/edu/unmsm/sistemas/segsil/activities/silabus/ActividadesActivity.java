package pe.edu.unmsm.sistemas.segsil.activities.silabus;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.edu.unmsm.sistemas.segsil.R;
import pe.edu.unmsm.sistemas.segsil.adapters.ActividadAdapter;

import pe.edu.unmsm.sistemas.segsil.pojos.Actividad;
import pe.edu.unmsm.sistemas.segsil.pojos.Tema;

public class ActividadesActivity extends AppCompatActivity {
    TextInputEditText edtNombre;
    Button btnGuardar;
    RecyclerView recyclerView;
    FloatingActionButton fab;
    ArrayList<String> actividades;
    ActividadAdapter actividadAdapter;
    String idCurso;
    String action;
    String nombreTema;
    int numeroTema;
    String TAG = "FIRESTORE";
    int numeroSemana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividades);

        idCurso = getIntent().getExtras().getString("idCurso");
        numeroSemana = getIntent().getExtras().getInt("numeroSemana");
        action = getIntent().getExtras().getString("action");
        nombreTema = getIntent().getExtras().getString("nombreTema");
        numeroTema = getIntent().getExtras().getInt("numeroTema");

        Toolbar toolbar = (Toolbar) findViewById(R.id.registrar_tema_actividades_toolbar);
        edtNombre = (TextInputEditText) findViewById(R.id.registrar_tema_actividades_edtNombre);
        final TextInputLayout lytNombre = (TextInputLayout) findViewById(R.id.registrar_tema_actividades_lytNombre);
        recyclerView = (RecyclerView) findViewById(R.id.registrar_tema_actividades_recycler);
        fab = (FloatingActionButton) findViewById(R.id.registrar_tema_actividades_fab);
        btnGuardar = (Button) findViewById(R.id.registrar_tema_actividades_btnGuardar);

        setSupportActionBar(toolbar);
        toolbar.setSubtitle("SEMANA " + numeroSemana);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        edtNombre.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(30)});
        edtNombre.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    ocultarTeclado(lytNombre);
                    lytNombre.requestFocus();
                    return true;
                }
                return false;
            }
        });
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        actividades =  new ArrayList<>();
        actividadAdapter =  new ActividadAdapter(ActividadesActivity.this,actividades);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(actividadAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ActividadesActivity.this);
                final View dialogView = getLayoutInflater().inflate(R.layout.dialog_ingresar_actividad, null);
                final TextInputEditText edtNombreActividad = dialogView.findViewById(R.id.dialog_actividad_edtActividad);
                builder.setView(dialogView);
                builder.setNegativeButton("CANCELAR", null);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        actividades.add(edtNombreActividad.getText().toString());
                        actividadAdapter.notifyDataSetChanged();
                    }
                });
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(action != null) {
                    FirebaseFirestore.getInstance().collection("silabus").document(idCurso).
                            collection("semanas").document(numeroSemana+"").
                            collection("temas").document(numeroTema+"").
                            update("nombre",edtNombre.getText().toString());

                } else {
                    FirebaseFirestore.getInstance().collection("silabus").document(idCurso).
                            collection("semanas").document(numeroSemana+"").
                            collection("temas")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        List<Tema> temas = new ArrayList<>();
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            temas.add(document.toObject(Tema.class));
                                        }
                                        int num = temas.size() + 1;
                                        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("silabus").document(idCurso)
                                                .collection("semanas").document(numeroSemana+"").collection("temas").document(num+"");
                                        documentReference.set(new Tema(num, edtNombre.getText().toString(), actividades));
                                        Map<String, Object> data = new HashMap<>();
                                        data.put("llenado", true);
                                        FirebaseFirestore.getInstance().collection("silabus").document(idCurso)
                                                .collection("semanas").document(numeroSemana+"").set(data, SetOptions.merge());
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                }
                finish();
            }
        });

        if(action != null) {
            edtNombre.setText(nombreTema);

            FirebaseFirestore.getInstance().collection("silabus").document(idCurso).collection("semanas").document(numeroSemana+"").collection("temas").document(numeroTema + "").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()){
                        Tema tema = documentSnapshot.toObject(Tema.class);
//                        Toast.makeText(getApplicationContext(),"Actividades" +tema.getActividades().size(), Toast.LENGTH_SHORT).show();

                        if(tema.getActividades() != null) {
                            for (String actividad : tema.getActividades()){
                                actividades.add(actividad);
                            }
                            actividadAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }

    }
    public void ocultarTeclado(View view){
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
