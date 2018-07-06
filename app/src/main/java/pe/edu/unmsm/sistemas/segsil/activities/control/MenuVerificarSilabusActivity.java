package pe.edu.unmsm.sistemas.segsil.activities.control;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.content.Context;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.content.res.Resources.Theme;

import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import pe.edu.unmsm.sistemas.segsil.R;
import pe.edu.unmsm.sistemas.segsil.activities.silabus.ActividadesActivity;
import pe.edu.unmsm.sistemas.segsil.holders.CursoSilabusHolder;
import pe.edu.unmsm.sistemas.segsil.pojos.Curso;
import pe.edu.unmsm.sistemas.segsil.pojos.Perfiles;
import pe.edu.unmsm.sistemas.segsil.pojos.Persona;

public class MenuVerificarSilabusActivity extends AppCompatActivity {

    String idUsuario;
    int perfil;
    Query query;
    FirestoreRecyclerAdapter adapter;
    FirestoreRecyclerOptions<Curso> opciones;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    RecyclerView recyclerView;
    String TAG = "FIRESTORE";
    Button mostrarTodo;

    AutoCompleteTextView busqueda;
    LinearLayout lytSeleccionar;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_verificar_silabus);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        recyclerView = (RecyclerView) findViewById(R.id.verificar_silabus_recycler);
        busqueda = (AutoCompleteTextView)findViewById(R.id.verificar_silabus_search);
        lytSeleccionar = (LinearLayout) findViewById(R.id.verificar_silabus_seleccionar);
        mostrarTodo = (Button) findViewById(R.id.verificar_silabus_btnMostrarTodo);

        Bundle bundle = getIntent().getExtras();
        idUsuario = bundle.getString("id");
        perfil = bundle.getInt("perfil");

        busqueda.setFilters(new InputFilter[]{new InputFilter.LengthFilter(40),new InputFilter.AllCaps()});

        // Setup spinner
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new MyAdapter(
                toolbar.getContext(),
                new String[]{
                        "Ingeniería de Sistemas (SS)",
                        "Ingeniería de Software (SW)"
                }));


        mostrarTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                busqueda.setText("");
                int position = spinner.getSelectedItemPosition();
                switch (position){
                    case 0:
                        query = db.collection("cursos").whereEqualTo("eap", "SS");cargarRecycler();
                        break;
                    case 1:
                        query = db.collection("cursos").whereEqualTo("eap", "SW");cargarRecycler();
                        break;
                }
            }
        });

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Toast.makeText(MenuVerificarSilabusActivity.this, "Sistemas", Toast.LENGTH_SHORT).show();
                        query = db.collection("cursos").whereEqualTo("eap", "SS");cargarRecycler();
                        query.get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            List<String> cursos = new ArrayList<String>();
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Log.d("CARGANDO COMPLETE", document.getId() + " => " + document.getData());
                                                cursos.add(document.toObject(Curso.class).getNombreCurso());
                                            }
                                            ArrayAdapter<String> adapterArray = new ArrayAdapter<String>(MenuVerificarSilabusActivity.this, R.layout.layout_item_busqueda,cursos);
                                            busqueda.setAdapter(adapterArray);
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                    case 1:
                        Toast.makeText(MenuVerificarSilabusActivity.this, "Software", Toast.LENGTH_SHORT).show();
                        query = db.collection("cursos").whereEqualTo("eap", "SW");cargarRecycler();
                        query.get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            List<String> cursos = new ArrayList<String>();
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Log.d("CARGANDO COMPLETE", document.getId() + " => " + document.getData());
                                                cursos.add(document.toObject(Curso.class).getNombreCurso());
                                            }
                                            ArrayAdapter<String> adapterArray = new ArrayAdapter<String>(MenuVerificarSilabusActivity.this, R.layout.layout_item_busqueda,cursos);
                                            busqueda.setAdapter(adapterArray);
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        busqueda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(perfil == Perfiles.DECANO) {
                    query = db.collection("cursos")
                            .whereEqualTo("nombreCurso", busqueda.getText().toString());
                } else if (perfil == Perfiles.DIRECTOR_SISTEMAS) {
                    query = db.collection("cursos")
                            .whereEqualTo("eap", "SS")
                            .whereEqualTo("nombreCurso", busqueda.getText().toString());
                } else if (perfil == Perfiles.DIRECTOR_SOFTWARE){
                    query = db.collection("cursos")
                            .whereEqualTo("eap", "SW")
                            .whereEqualTo("nombreCurso", busqueda.getText().toString());
                }
                cargarRecycler();
                ocultarTeclado(lytSeleccionar);
                lytSeleccionar.requestFocus();
            }
        });
        if(perfil == Perfiles.DECANO) {query = db.collection("cursos");}
        else if (perfil == Perfiles.DIRECTOR_SISTEMAS) {
            spinner.setSelection(0);
            spinner.setEnabled(false);
            query = db.collection("cursos").whereEqualTo("eap", "SS");
            query.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<String> cursos = new ArrayList<String>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("CARGANDO COMPLETE", document.getId() + " => " + document.getData());
                                    cursos.add(document.toObject(Curso.class).getNombreCurso());
                                }
                                ArrayAdapter<String> adapterArray = new ArrayAdapter<String>(MenuVerificarSilabusActivity.this, R.layout.layout_item_busqueda,cursos);
                                busqueda.setAdapter(adapterArray);

                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        } else if (perfil == Perfiles.DIRECTOR_SOFTWARE){
            spinner.setSelection(1);
            spinner.setEnabled(false);
            query = db.collection("cursos").whereEqualTo("eap", "SW");
            query.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<String> cursos = new ArrayList<String>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("CARGANDO COMPLETE", document.getId() + " => " + document.getData());
                                    cursos.add(document.toObject(Curso.class).getNombreCurso());
                                }
                                ArrayAdapter<String> adapterArray = new ArrayAdapter<String>(MenuVerificarSilabusActivity.this, R.layout.layout_item_busqueda,cursos);
                                busqueda.setAdapter(adapterArray);
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }

        opciones = new FirestoreRecyclerOptions.Builder<Curso>().setQuery(query, Curso.class).build();
        adapter =  new  FirestoreRecyclerAdapter < Curso , CursoSilabusHolder> (opciones) {
            @Override
            public void onBindViewHolder(CursoSilabusHolder holder, int position, Curso model) {
                final Curso g = model;
                holder.setHolderTxtEap(model.getEap().toString());
                holder.setHolderTxtNombreCurso(model.getNombreCurso());
                holder.setHolderTxtNombreCoordinador(model.getNombreCoordinador());
                holder.getCardView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (g.isSilabus()){
                            Intent intent = new Intent(MenuVerificarSilabusActivity.this,VerificarSilabusActivity.class);
                            intent.putExtra("idCurso", g.getId());
                            startActivity(intent);
                        }else{
                            mostrarDatosCoordinador(g.getIdCoordinador());
                        }
                    }
                });
            }

            @Override
            public CursoSilabusHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext()).inflate(R.layout.item_curso_silabus, group, false);
                return new CursoSilabusHolder(view);
            }
        };
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void ocultarTeclado(View view){
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



    private static class MyAdapter extends ArrayAdapter<String> implements ThemedSpinnerAdapter {
        private final ThemedSpinnerAdapter.Helper mDropDownHelper;

        public MyAdapter(Context context, String[] objects) {
            super(context, android.R.layout.simple_list_item_1, objects);
            mDropDownHelper = new ThemedSpinnerAdapter.Helper(context);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                // Inflate the drop down using the helper's LayoutInflater
                LayoutInflater inflater = mDropDownHelper.getDropDownViewInflater();
                view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            } else {
                view = convertView;
            }

            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setText(getItem(position));

            return view;
        }

        @Override
        public Theme getDropDownViewTheme() {
            return mDropDownHelper.getDropDownViewTheme();
        }

        @Override
        public void setDropDownViewTheme(Theme theme) {
            mDropDownHelper.setDropDownViewTheme(theme);
        }
    }

    public void cargarRecycler(){
        opciones = new FirestoreRecyclerOptions.Builder<Curso>().setQuery(query, Curso.class).build();
        adapter =  new  FirestoreRecyclerAdapter < Curso , CursoSilabusHolder> (opciones) {
            @Override
            public void onBindViewHolder(CursoSilabusHolder holder, int position, Curso model) {
                final Curso g = model;
                holder.setHolderTxtEap(model.getEap().toString());
                holder.setHolderTxtNombreCurso(model.getNombreCurso());
                holder.setHolderTxtNombreCoordinador(model.getNombreCoordinador());
                holder.getCardView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (g.isSilabus()){
                            Intent intent = new Intent(MenuVerificarSilabusActivity.this,VerificarSilabusActivity.class);
                            intent.putExtra("idCurso", g.getId());
                            startActivity(intent);
                        }else{
                            mostrarDatosCoordinador(g.getIdCoordinador());
                        }
                    }
                });
            }

            @Override
            public CursoSilabusHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext()).inflate(R.layout.item_curso_silabus, group, false);
                return new CursoSilabusHolder(view);
            }
        };
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.stopListening();
        adapter.startListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void mostrarDatosCoordinador(String idCoordinador){
        DocumentReference docRef = db.collection("personas").document(idCoordinador);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Persona persona = documentSnapshot.toObject(Persona.class);
                final AlertDialog.Builder builder = new AlertDialog.Builder(MenuVerificarSilabusActivity.this);
                final View dialogView = getLayoutInflater().inflate(R.layout.layout_dialog_coordinador, null);
                TextView txtNombre = dialogView.findViewById(R.id.dialog_coordinador_txtNombre);
                TextView txtCorreo = dialogView.findViewById(R.id.dialog_coordinador_txtCorreo);
                TextView txtTelefono = dialogView.findViewById(R.id.dialog_coordinador_txtTelefono);
                txtNombre.setText("Nombres: " + persona.getNombres() + " " + persona.getApellidos());
                txtCorreo.setText("Correo: " + persona.getCorreo());
                txtTelefono.setText("Telefono: " + persona.getTelefono());
                builder.setView(dialogView);
                builder.setTitle("SILABUS NO REGISTRADO O INCOMPLETO!");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }
}
