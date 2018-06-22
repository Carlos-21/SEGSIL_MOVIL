package pe.edu.unmsm.sistemas.segsil.activities.avance;

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
import pe.edu.unmsm.sistemas.segsil.activities.silabus.MenuSilabusActivity;
import pe.edu.unmsm.sistemas.segsil.activities.silabus.RegistrarSilabusActivity;
import pe.edu.unmsm.sistemas.segsil.holders.CursoHolder;
import pe.edu.unmsm.sistemas.segsil.holders.GrupoHolder;
import pe.edu.unmsm.sistemas.segsil.pojos.Curso;
import pe.edu.unmsm.sistemas.segsil.pojos.Grupo;

public class MenuAvanceActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    TextView txtProfesor;
    RecyclerView recyclerView;
    String TAG = "FIRESTORE";
    Toolbar myToolbar;
    String idProfesor;
    FirestoreRecyclerAdapter adapter;
    FirestoreRecyclerOptions<Grupo> opciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_avance);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.avance_recycler);
        txtProfesor = (TextView)findViewById(R.id.avance_txtProfesor);

        Bundle bundle = getIntent().getExtras();
        txtProfesor.setText("Profesor: "+ bundle.getString("nombre") + " " + bundle.getString("apellido"));
        idProfesor = bundle.getString("id");

        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("REGISTRAR AVANCE");
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Query query = db.collection("grupos").whereEqualTo("codProfesor",idProfesor);
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
                        Intent intent =  new Intent(MenuAvanceActivity.this,RegistrarAvanceActivity.class);
                        intent.putExtra("nombre",g.getNombrePlan1());
                        intent.putExtra("eap",g.getEap());
                        intent.putExtra("numero",g.getNumero());
                        intent.putExtra("ciclo",g.getCiclo());
                        intent.putExtra("tipo",g.getTipo());
                        intent.putExtra("idGrupo",g.getId());
                        intent.putExtra("profesor",g.getNomProfesor());
                        intent.putExtra("curso",g.getCodCurso());
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
        builder.setMessage("¿DESEA CERRAR SESIÓN?");
        builder.setNegativeButton("CANCELAR", null);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                firebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MenuAvanceActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
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
