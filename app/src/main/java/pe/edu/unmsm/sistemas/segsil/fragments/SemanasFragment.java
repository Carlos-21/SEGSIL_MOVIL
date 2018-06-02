package pe.edu.unmsm.sistemas.segsil.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import pe.edu.unmsm.sistemas.segsil.R;
import pe.edu.unmsm.sistemas.segsil.activities.silabus.UnidadActivity;
import pe.edu.unmsm.sistemas.segsil.holders.SemanaHolder;
import pe.edu.unmsm.sistemas.segsil.holders.UnidadHolder;
import pe.edu.unmsm.sistemas.segsil.pojos.Semana;
import pe.edu.unmsm.sistemas.segsil.pojos.Unidad;


/**
 * A simple {@link Fragment} subclass.
 */
public class SemanasFragment extends Fragment {

    String idCurso;
    Context context;
    RecyclerView recyclerView;
    FirestoreRecyclerAdapter adapter;

    public SemanasFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public SemanasFragment(String idCurso, Context context) {
        this.idCurso = idCurso;
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_semanas, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.semanas_fragment_recycler);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Query query = FirebaseFirestore.getInstance().collection("silabus").document(idCurso).collection("semanas");

        FirestoreRecyclerOptions<Semana> options = new FirestoreRecyclerOptions.Builder<Semana>()
                .setQuery(query, Semana.class).build();

        adapter = new FirestoreRecyclerAdapter<Semana, SemanaHolder>(options) {
            @Override
            public void onBindViewHolder(SemanaHolder holder, int position, Semana model) {
                holder.setTxtNumero("Semana " + model.getNumero());
                holder.setTxtUnidad("Unidad" + model.getUnidad());
            }

            @Override
            public SemanaHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext()).inflate(R.layout.item_semana, group, false);
                return new SemanaHolder(view);
            }
        };

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


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
