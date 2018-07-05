package pe.edu.unmsm.sistemas.segsil.activities.silabus;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import pe.edu.unmsm.sistemas.segsil.R;
import pe.edu.unmsm.sistemas.segsil.holders.TemaHolder;
import pe.edu.unmsm.sistemas.segsil.pojos.Tema;

public class TemaActivity extends AppCompatActivity {

    String idCurso;
    String nombreCurso;
    int numero;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    Toolbar toolbar;
    TextView txtSemana;
    TextView txtNombreCurso;
    FirestoreRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tema);

        recyclerView =  (RecyclerView) findViewById(R.id.registrar_temas_recycler);
        fab =  (FloatingActionButton) findViewById(R.id.registrar_temas_fab);
        toolbar =  (Toolbar) findViewById(R.id.registrar_temas_toolbar);
        txtSemana = (TextView) findViewById(R.id.registrar_temas_txtSemana);
        txtNombreCurso = (TextView) findViewById(R.id.registrar_temas_txtNombreCurso);


        idCurso = getIntent().getExtras().getString("curso");
        numero = getIntent().getExtras().getInt("semana");
        nombreCurso = getIntent().getExtras().getString("nombreCurso");

        txtSemana.setText("SEMANA " + numero);
        txtNombreCurso.setText(nombreCurso);

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Query query = FirebaseFirestore.getInstance().collection("silabus").document(idCurso).collection("semanas").document(numero+"").collection("temas");

        FirestoreRecyclerOptions<Tema> options = new FirestoreRecyclerOptions.Builder<Tema>()
                .setQuery(query, Tema.class).build();

        adapter = new FirestoreRecyclerAdapter<Tema, TemaHolder>(options) {
            @Override
            public void onBindViewHolder(final TemaHolder holder, int position, final Tema model) {
                holder.setTxtTema(model.getNumero() + "." + model.getNombre());
                String actividades = "";
                int j = 1;
                for (String a : model.getActividades()){
                    if(j == 1){
                        actividades = actividades + a ;
                    }else{
                        actividades = actividades + "\n" + a ;
                    }
                    j++;
                }

                holder.setTxtActividades(actividades);
                holder.getCv().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }

            @Override
            public TemaHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext()).inflate(R.layout.item_tema, group, false);
                return new TemaHolder(view);
            }
        };

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recyclerView.getAdapter().getItemCount() < 2){
                    Intent intent = new Intent(TemaActivity.this, ActividadesActivity.class);
                    intent.putExtra("idCurso",idCurso);
                    intent.putExtra("numeroSemana",numero);
                    startActivity(intent);
                }else{
                    Toast.makeText(TemaActivity.this, "Ya alcanzó el máximo número de temas por semana", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
