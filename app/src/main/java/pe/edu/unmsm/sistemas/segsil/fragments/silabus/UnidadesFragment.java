package pe.edu.unmsm.sistemas.segsil.fragments.silabus;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import pe.edu.unmsm.sistemas.segsil.R;
import pe.edu.unmsm.sistemas.segsil.activities.silabus.UnidadActivity;
import pe.edu.unmsm.sistemas.segsil.holders.UnidadHolder;
import pe.edu.unmsm.sistemas.segsil.pojos.Unidad;


/**
 * A simple {@link Fragment} subclass.
 */
public class UnidadesFragment extends Fragment {

    String idCurso;
    Context context;
    FirestoreRecyclerAdapter adapter;
    RecyclerView recyclerView;
    FloatingActionButton fab;
    String TAG = "FIRESTORE";
    Query query;

    public UnidadesFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public UnidadesFragment(String idCurso, Context context) {
        this.idCurso = idCurso;
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_unidades, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.unidades_fragment_recycler);
        fab = (FloatingActionButton) rootView.findViewById(R.id.unidades_fragment_fab);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        query = FirebaseFirestore.getInstance().collection("silabus").document(idCurso).collection("unidades");

        FirestoreRecyclerOptions<Unidad> options = new FirestoreRecyclerOptions.Builder<Unidad>()
                .setQuery(query, Unidad.class).build();

        adapter = new FirestoreRecyclerAdapter<Unidad, UnidadHolder>(options) {
            @Override
            public void onBindViewHolder(UnidadHolder holder, int position, Unidad model) {
                holder.setNombre(model.getNombre());
                holder.setNumero(model.getNumero() + "");
                holder.setSemanas(model.getSemanas() + "");
            }

            @Override
            public UnidadHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext()).inflate(R.layout.item_unidad, group, false);
                return new UnidadHolder(view);
            }
        };

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    int numeroSemanas = 0;
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        Unidad unidad = document.toObject(Unidad.class);
                                        numeroSemanas = numeroSemanas + unidad.getSemanas();
                                    }
                                    if(numeroSemanas < 14){
                                        Intent intent =  new Intent(context, UnidadActivity.class);
                                        intent.putExtra("numero", adapter.getItemCount() + 1);
                                        intent.putExtra("curso", idCurso);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(context, "YA SE COMPLETO EL MAXIMO DE SEMANAS: 14", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

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
