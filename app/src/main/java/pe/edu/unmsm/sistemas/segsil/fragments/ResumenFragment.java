package pe.edu.unmsm.sistemas.segsil.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pe.edu.unmsm.sistemas.segsil.R;
import pe.edu.unmsm.sistemas.segsil.activities.BienvenidoActivity;
import pe.edu.unmsm.sistemas.segsil.activities.LoginActivity;
import pe.edu.unmsm.sistemas.segsil.pojos.Semana;
import pe.edu.unmsm.sistemas.segsil.pojos.Tema;
import pe.edu.unmsm.sistemas.segsil.pojos.Unidad;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResumenFragment extends Fragment {

    String idCurso;
    Context context;
    LinearLayout semana1, semana2, semana3, semana4, semana5, semana6, semana7,
            semana9, semana10, semana11, semana12, semana13, semana14, semana15;
    TextView txtSemana1,txtSemana2, txtSemana3, txtSemana4, txtSemana5, txtSemana6, txtSemana7,
            txtSemana9, txtSemana10, txtSemana11, txtSemana12, txtSemana13, txtSemana14, txtSemana15;
    TextView txtTemasSemana1, txtTemasSemana2, txtTemasSemana3, txtTemasSemana4, txtTemasSemana5, txtTemasSemana6, txtTemasSemana7,
            txtTemasSemana9, txtTemasSemana10, txtTemasSemana11, txtTemasSemana12, txtTemasSemana13, txtTemasSemana14, txtTemasSemana15;
    String TAG = "FIRESTORE";

    Button btnFinalizar;
//    TextView txtUnidades;

    TextView[] textViewSemanas;
    TextView[] textViewTemas;


    boolean completo = true;


    public ResumenFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public ResumenFragment(String idCurso, Context context) {
        this.idCurso = idCurso;
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_resumen, container, false);
//        txtUnidades = (TextView) rootView.findViewById(R.id.resumen_fragment_txtUnidades);
        semana1 = rootView.findViewById(R.id.item_resumen_semana1);
        semana2 = rootView.findViewById(R.id.item_resumen_semana2);
        semana3 = rootView.findViewById(R.id.item_resumen_semana3);
        semana4 = rootView.findViewById(R.id.item_resumen_semana4);
        semana5 = rootView.findViewById(R.id.item_resumen_semana5);
        semana6 = rootView.findViewById(R.id.item_resumen_semana6);
        semana7 = rootView.findViewById(R.id.item_resumen_semana7);
        semana9 = rootView.findViewById(R.id.item_resumen_semana9);
        semana10 = rootView.findViewById(R.id.item_resumen_semana10);
        semana11 = rootView.findViewById(R.id.item_resumen_semana11);
        semana12 = rootView.findViewById(R.id.item_resumen_semana12);
        semana13 = rootView.findViewById(R.id.item_resumen_semana13);
        semana14 = rootView.findViewById(R.id.item_resumen_semana14);
        semana15 = rootView.findViewById(R.id.item_resumen_semana15);
        txtSemana1 = semana1.findViewById(R.id.item_resumen_txtUnidad);
        txtSemana2 = semana2.findViewById(R.id.item_resumen_txtUnidad);
        txtSemana3 = semana3.findViewById(R.id.item_resumen_txtUnidad);
        txtSemana4 = semana4.findViewById(R.id.item_resumen_txtUnidad);
        txtSemana5 = semana5.findViewById(R.id.item_resumen_txtUnidad);
        txtSemana6 = semana6.findViewById(R.id.item_resumen_txtUnidad);
        txtSemana7 = semana7.findViewById(R.id.item_resumen_txtUnidad);
        txtSemana9 = semana9.findViewById(R.id.item_resumen_txtUnidad);
        txtSemana10 = semana10.findViewById(R.id.item_resumen_txtUnidad);
        txtSemana11 = semana11.findViewById(R.id.item_resumen_txtUnidad);
        txtSemana12 = semana12.findViewById(R.id.item_resumen_txtUnidad);
        txtSemana13 = semana13.findViewById(R.id.item_resumen_txtUnidad);
        txtSemana14 = semana14.findViewById(R.id.item_resumen_txtUnidad);
        txtSemana15 = semana15.findViewById(R.id.item_resumen_txtUnidad);
        textViewSemanas = new TextView[]{txtSemana1,txtSemana2,txtSemana3,txtSemana4,txtSemana5,txtSemana6,txtSemana7,txtSemana9,txtSemana10,txtSemana11,txtSemana12,txtSemana13,txtSemana14,txtSemana15};
        txtTemasSemana1 = semana1.findViewById(R.id.item_resumen_txtTemas);
        txtTemasSemana2 = semana1.findViewById(R.id.item_resumen_txtTemas);
        txtTemasSemana3 = semana1.findViewById(R.id.item_resumen_txtTemas);
        txtTemasSemana4 = semana1.findViewById(R.id.item_resumen_txtTemas);
        txtTemasSemana5 = semana1.findViewById(R.id.item_resumen_txtTemas);
        txtTemasSemana6 = semana1.findViewById(R.id.item_resumen_txtTemas);
        txtTemasSemana7 = semana1.findViewById(R.id.item_resumen_txtTemas);
        txtTemasSemana9 = semana1.findViewById(R.id.item_resumen_txtTemas);
        txtTemasSemana10 = semana1.findViewById(R.id.item_resumen_txtTemas);
        txtTemasSemana11 = semana1.findViewById(R.id.item_resumen_txtTemas);
        txtTemasSemana12 = semana1.findViewById(R.id.item_resumen_txtTemas);
        txtTemasSemana13 = semana1.findViewById(R.id.item_resumen_txtTemas);
        txtTemasSemana14 = semana1.findViewById(R.id.item_resumen_txtTemas);
        txtTemasSemana15 = semana1.findViewById(R.id.item_resumen_txtTemas);
        textViewTemas =  new TextView[]{txtTemasSemana1, txtTemasSemana2, txtTemasSemana3, txtTemasSemana4, txtTemasSemana5, txtTemasSemana6, txtTemasSemana7, txtTemasSemana9, txtTemasSemana10, txtTemasSemana11, txtTemasSemana12, txtTemasSemana13, txtTemasSemana14, txtTemasSemana15};
        btnFinalizar = (Button) rootView.findViewById(R.id.resumen_fragment_btnFinalizar);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        FirebaseFirestore.getInstance().collection("silabus").document(idCurso).collection("semanas").orderBy("numero")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Semana> semanas = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                semanas.add(document.toObject(Semana.class));

                            }
                            for (int i = 0; i < semanas.size(); i++) {
                                textViewSemanas[i].setText("SEMANA " + semanas.get(i).getNumero() + " - " + semanas.get(i).getNombreUnidad());
                                completo = completo && semanas.get(i).isLlenado();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        for (int i = 0; i < textViewTemas.length ; i++) {
            final TextView textView = textViewTemas[i];
            textView.setText("");
            final int textViewTema = i;
            int semana = i + 1;
            if (semana > 7) semana++;
            FirebaseFirestore.getInstance().collection("silabus").document(idCurso).collection("semanas").document(semana+"").collection("temas")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                boolean b = false;
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    Tema tema = document.toObject(Tema.class);
                                    if (!b){
                                        b = true;
                                        textView.append(tema.getNombre());
                                    }else{
                                        textView.append("\n" + tema.getNombre());
                                    }
                                    for (String s : tema.getActividades()){
                                        textView.append("\n-" + s + ".");
                                    }
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }

        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("¿CONFIRMA EL LLENADO COMPLETO DEL SILABUS?");
                builder.setNegativeButton("NO", null);
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int id) {
                        if(completo){
                            getActivity().finish();
                            dialog.dismiss();
                        }else{
                            Toast.makeText(context, "NO SE PUEDE FINALIZAR, SILABUS INCOMPLETO", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
}
