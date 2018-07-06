package pe.edu.unmsm.sistemas.segsil.activities.control;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import pe.edu.unmsm.sistemas.segsil.R;
import pe.edu.unmsm.sistemas.segsil.activities.LoginActivity;
import pe.edu.unmsm.sistemas.segsil.activities.avance.MenuAvanceActivity;
import pe.edu.unmsm.sistemas.segsil.activities.avance.RegistrarAvanceActivity;
import pe.edu.unmsm.sistemas.segsil.holders.CursoHolder;
import pe.edu.unmsm.sistemas.segsil.holders.GrupoHolder;
import pe.edu.unmsm.sistemas.segsil.pojos.Curso;
import pe.edu.unmsm.sistemas.segsil.pojos.Grupo;
import pe.edu.unmsm.sistemas.segsil.pojos.Perfil;
import pe.edu.unmsm.sistemas.segsil.pojos.Perfiles;

public class MenuVerificarAvanceActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    RecyclerView recyclerView;
    String TAG = "FIRESTORE";
    Toolbar myToolbar;
    String idUsuario;
    AutoCompleteTextView busqueda;
    Spinner spFiltro;
    Query query;
    LinearLayout lytSeleccionar;
    int perfil;
    FirestoreRecyclerAdapter adapter;
    FirestoreRecyclerOptions<Grupo> opciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_verificar_avance);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.menu_verificar_avance_recycler);
        busqueda = (AutoCompleteTextView) findViewById(R.id.menu_verificar_avance_search);
        lytSeleccionar = (LinearLayout) findViewById(R.id.menu_verificar_avance_layout_seleccionar);
        spFiltro = (Spinner) findViewById(R.id.menu_verificar_avance_spFiltro);
        busqueda.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(50)});
        busqueda.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    ocultarTeclado(lytSeleccionar);
                    lytSeleccionar.requestFocus();
                    return true;
                }
                return false;
            }
        });

        busqueda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(perfil == Perfiles.DECANO) {
                    query = db.collection("grupos")
                            .whereEqualTo("nombreCurso", busqueda.getText().toString());
                } else if (perfil == Perfiles.DIRECTOR_SISTEMAS) {
                    query = db.collection("grupos")
                            .whereEqualTo("eap", "SS")
                            .whereEqualTo("nombreCurso", busqueda.getText().toString());
                } else if (perfil == Perfiles.DIRECTOR_SOFTWARE){
                    query = db.collection("grupos")
                            .whereEqualTo("eap", "SW")
                            .whereEqualTo("nombreCurso", busqueda.getText().toString());
                }
                cargarRecycler();
                ocultarTeclado(lytSeleccionar);
                lytSeleccionar.requestFocus();
            }
        });

        spFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        lytSeleccionar.setVisibility(View.GONE);
                        busqueda.setText("");
                        if(perfil == Perfiles.DECANO) {query = db.collection("grupos");}
                        else if (perfil == Perfiles.DIRECTOR_SISTEMAS) {
                            query = db.collection("grupos").whereEqualTo("eap", "SS");
                        } else if (perfil == Perfiles.DIRECTOR_SOFTWARE){
                            query = db.collection("grupos").whereEqualTo("eap", "SW");
                        }
                        cargarRecycler();
                        break;
                    case 1:
                        lytSeleccionar.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Bundle bundle = getIntent().getExtras();
        idUsuario = bundle.getString("id");
        perfil = bundle.getInt("perfil");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("VERIFICAR AVANCE");
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if(perfil == Perfiles.DECANO) {query = db.collection("grupos");}
        else if (perfil == Perfiles.DIRECTOR_SISTEMAS) {
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
                                ArrayAdapter<String> adapterArray = new ArrayAdapter<String>(MenuVerificarAvanceActivity.this, R.layout.layout_item_busqueda,cursos);
                                busqueda.setAdapter(adapterArray);

                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        } else if (perfil == Perfiles.DIRECTOR_SOFTWARE){
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
                                ArrayAdapter<String> adapterArray = new ArrayAdapter<String>(MenuVerificarAvanceActivity.this, R.layout.layout_item_busqueda,cursos);
                                busqueda.setAdapter(adapterArray);
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }

        opciones = new FirestoreRecyclerOptions.Builder<Grupo>().setQuery(query, Grupo.class).build();
        adapter =  new  FirestoreRecyclerAdapter < Grupo , GrupoHolder> (opciones) {
            @Override
            public void onBindViewHolder(GrupoHolder holder, int position, Grupo model) {
                final Grupo g = model;
                holder.setHolderTxtEap(model.getEap().toString());
                holder.setHolderTxtNombre(model.getNombreCurso());
                holder.setHolderTxtNumero(model.getNumero()+"");
                holder.setHolderTxtTipo(model.getTipo()+"");
                holder.getCardView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent =  new Intent(MenuVerificarAvanceActivity.this,VerificarAvanceActivity.class);
                        intent.putExtra("nombre",g.getNombrePlan1());
                        intent.putExtra("eap",g.getEap());
                        intent.putExtra("numero",g.getNumero());
                        intent.putExtra("ciclo",g.getCiclo());
                        intent.putExtra("tipo",g.getTipo());
                        intent.putExtra("idGrupo",g.getId());
                        intent.putExtra("profesor",g.getNomProfesor());
                        intent.putExtra("curso",g.getCodCurso());
                        intent.putExtra("perfil",perfil);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public GrupoHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext()).inflate(R.layout.item_grupo, group, false);
                return new GrupoHolder(view);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_cerrar_sesion:
                cerrarSesion();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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

    public void cargarRecycler(){
        opciones = new FirestoreRecyclerOptions.Builder<Grupo>().setQuery(query, Grupo.class).build();
        adapter =  new  FirestoreRecyclerAdapter < Grupo , GrupoHolder> (opciones) {
            @Override
            public void onBindViewHolder(GrupoHolder holder, int position, Grupo model) {
                final Grupo g = model;
                holder.setHolderTxtEap(model.getEap().toString());
                holder.setHolderTxtNombre(model.getNombreCurso());
                holder.setHolderTxtNumero(model.getNumero()+"");
                holder.setHolderTxtTipo(model.getTipo()+"");
                holder.getCardView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent =  new Intent(MenuVerificarAvanceActivity.this,VerificarAvanceActivity.class);
                        intent.putExtra("nombre",g.getNombreCurso());
                        intent.putExtra("eap",g.getEap());
                        intent.putExtra("numero",g.getNumero());
                        intent.putExtra("ciclo",g.getCiclo());
                        intent.putExtra("tipo",g.getTipo());
                        intent.putExtra("idGrupo",g.getId());
                        intent.putExtra("profesor",g.getNomProfesor());
                        intent.putExtra("curso",g.getCodCurso());
                        intent.putExtra("perfil",perfil);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public GrupoHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext()).inflate(R.layout.item_grupo, group, false);
                return new GrupoHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.stopListening();
        adapter.startListening();
    }
}
