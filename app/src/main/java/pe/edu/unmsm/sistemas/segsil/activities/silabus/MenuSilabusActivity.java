package pe.edu.unmsm.sistemas.segsil.activities.silabus;

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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import pe.edu.unmsm.sistemas.segsil.activities.BienvenidoActivity;
import pe.edu.unmsm.sistemas.segsil.activities.LoginActivity;
import pe.edu.unmsm.sistemas.segsil.holders.CursoHolder;
import pe.edu.unmsm.sistemas.segsil.R;
import pe.edu.unmsm.sistemas.segsil.pojos.Curso;
import pe.edu.unmsm.sistemas.segsil.pojos.Semana;

public class MenuSilabusActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    TextView txtCoordinador;
    RecyclerView recyclerView;
    String TAG = "FIRESTORE";
    Toolbar myToolbar;
    String idCoordinador;
    FirestoreRecyclerAdapter adapter;
    FirestoreRecyclerOptions<Curso> opciones;
    boolean acceder = true;

    String nombreCoordinador, apellidoCoordinador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_silabus);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.silabus_recycler);
        txtCoordinador = (TextView)findViewById(R.id.silabus_txtCoordinador);

        Bundle bundle = getIntent().getExtras();
        nombreCoordinador = bundle.getString("nombre");
        apellidoCoordinador = bundle.getString("apellido");
        txtCoordinador.setText("Coordinador: "+ nombreCoordinador + " " + apellidoCoordinador);
        idCoordinador = bundle.getString("id");

        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("REGISTRAR SILABUS");
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Query query = db.collection("cursos").whereEqualTo("idCoordinador",idCoordinador);
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

        opciones = new FirestoreRecyclerOptions.Builder<Curso>().setQuery(query, Curso.class).build();
        adapter =  new  FirestoreRecyclerAdapter < Curso , CursoHolder> (opciones) {
            @Override
            public void onBindViewHolder(CursoHolder holder, int position, Curso model) {
                final Curso c = model;
                holder.setHolderTxtEap(model.getEap().toString());
                holder.setHolderTxtNombre1(model.getNombrePlan1());
                holder.setHolderTxtNombre2(model.getNombrePlan2());
                holder.getCardView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!c.isSilabus()){
                            accedeRegistrarSilabus(c);
                        }else{
                            mostrarMensaje("EL SILABUS YA SE REGISTRO COMO COMPLETO, ¿DESDE CAMBIAR EL ESTADO A INCOMPLETO PARA HACER MODIFICACIONES?",c);
                        }

                    }
                });
            }

            @Override
            public CursoHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext()).inflate(R.layout.item_curso, group, false);
                return new CursoHolder(view);
            }
        };

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    public void accedeRegistrarSilabus(Curso c){
        Map<String, Object> data = new HashMap<>();
        data.put("silabus", false);
        FirebaseFirestore.getInstance().collection("cursos").document(c.getId()).set(data, SetOptions.merge());
        //Intent intent =  new Intent(MenuSilabusActivity.this,RegistrarSilabusActivity.class);
        Intent intent =  new Intent(MenuSilabusActivity.this,PantallaCoordinador.class);
        intent.putExtra("nombre_curso",c.getNombreCurso());
        intent.putExtra("eap_curso",c.getEap());
        intent.putExtra("ciclo_curso",c.getCiclo());
        intent.putExtra("id_curso",c.getId());
        intent.putExtra("id_coordinador",idCoordinador);
        intent.putExtra("nombre_coordinador",nombreCoordinador);
        intent.putExtra("apellido_coordinador", apellidoCoordinador);
        startActivity(intent);
    }

    public void mostrarMensaje(String s, final Curso c){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(s);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                accedeRegistrarSilabus(c);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("CANCELAR",null);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
            @SuppressLint("NewApi")
            public void onClick(DialogInterface dialog, int id) {
                firebaseAuth.getInstance().signOut();
                finishAffinity();
                dialog.dismiss();
                Intent intent = new Intent(MenuSilabusActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
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
