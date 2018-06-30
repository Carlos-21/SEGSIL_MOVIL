package pe.edu.unmsm.sistemas.segsil.activities.control;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

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
    Query query;
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

        if(perfil == Perfiles.DECANO) query = db.collection("grupos");
        else if (perfil == Perfiles.DIRECTOR_SISTEMAS) query = db.collection("grupos").whereEqualTo("eap", "SS");
        else if (perfil == Perfiles.DIRECTOR_SOFTWARE)  query = db.collection("grupos").whereEqualTo("eap", "SW");
        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        opciones = new FirestoreRecyclerOptions.Builder<Grupo>().setQuery(query, Grupo.class).build();
        adapter =  new  FirestoreRecyclerAdapter < Grupo , GrupoHolder> (opciones) {
            @Override
            public void onBindViewHolder(GrupoHolder holder, int position, Grupo model) {
                final Grupo g = model;
                holder.setHolderTxtEap(model.getEap().toString());
                holder.setHolderTxtNombre(model.getNombrePlan1());
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
}
