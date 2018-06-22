package pe.edu.unmsm.sistemas.segsil.fragments.avance;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import pe.edu.unmsm.sistemas.segsil.R;
import pe.edu.unmsm.sistemas.segsil.pojos.Grupo;
import pe.edu.unmsm.sistemas.segsil.pojos.Tema;

/**
 * A simple {@link Fragment} subclass.
 */
public class AvanceSemanaFragment extends Fragment {

    String idGrupo;
    String idCurso;

    int numeroSemana;
    TextView txtCurso;
    TextView txtGrupoTipo;
    LinearLayout checklist1;
    LinearLayout checklist2;
    LinearLayout layoutChecklist1;
    LinearLayout layoutChecklist2;
    Button btnActualizar;
    TextView txtTema1;
    TextView txtTema2;
    CheckBox cl1ck1, cl1ck2, cl1ck3, cl1ck4, cl1ck5, cl1ck6, cl1ck7, cl1ck8, cl1ck9, cl1ck10;
    CheckBox cl2ck1, cl2ck2, cl2ck3, cl2ck4, cl2ck5, cl2ck6, cl2ck7, cl2ck8, cl2ck9, cl2ck10;


    public AvanceSemanaFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public AvanceSemanaFragment(String idGrupo,String idCurso, int numeroSemana) {
        this.idGrupo = idGrupo;
        this.idCurso = idCurso;
        this.numeroSemana = numeroSemana;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_avance_semana, container, false);
        txtCurso = (TextView) rootView.findViewById(R.id.fragment_avance_txtCurso);
        txtGrupoTipo = (TextView) rootView.findViewById(R.id.fragment_avance_txtGrupoTipo);
        layoutChecklist1 = (LinearLayout) rootView.findViewById(R.id.registrar_avance_layout_tema1);
        layoutChecklist2 = (LinearLayout) rootView.findViewById(R.id.registrar_avance_layout_tema2);
        checklist1 = (LinearLayout) rootView.findViewById(R.id.registrar_avance_checklist1);
        checklist2 = (LinearLayout) rootView.findViewById(R.id.registrar_avance_checklist2);
        btnActualizar = (Button) rootView.findViewById(R.id.registrar_avance_btnActualizar);
        txtTema1 = (TextView) checklist1.findViewById(R.id.checklist_txtTema);
        txtTema2 = (TextView) checklist2.findViewById(R.id.checklist_txtTema);
        cl1ck1 = (CheckBox) checklist1.findViewById(R.id.checklist_ck1);
        cl1ck2 = (CheckBox) checklist1.findViewById(R.id.checklist_ck2);
        cl1ck3 = (CheckBox) checklist1.findViewById(R.id.checklist_ck3);
        cl1ck4 = (CheckBox) checklist1.findViewById(R.id.checklist_ck4);
        cl1ck5 = (CheckBox) checklist1.findViewById(R.id.checklist_ck5);
        cl1ck6 = (CheckBox) checklist1.findViewById(R.id.checklist_ck6);
        cl1ck7 = (CheckBox) checklist1.findViewById(R.id.checklist_ck7);
        cl1ck8 = (CheckBox) checklist1.findViewById(R.id.checklist_ck8);
        cl1ck9 = (CheckBox) checklist1.findViewById(R.id.checklist_ck9);
        cl1ck10 = (CheckBox) checklist1.findViewById(R.id.checklist_ck10);
        cl2ck1 = (CheckBox) checklist2.findViewById(R.id.checklist_ck1);
        cl2ck2 = (CheckBox) checklist2.findViewById(R.id.checklist_ck2);
        cl2ck3 = (CheckBox) checklist2.findViewById(R.id.checklist_ck3);
        cl2ck4 = (CheckBox) checklist2.findViewById(R.id.checklist_ck4);
        cl2ck5 = (CheckBox) checklist2.findViewById(R.id.checklist_ck5);
        cl2ck6 = (CheckBox) checklist2.findViewById(R.id.checklist_ck6);
        cl2ck7 = (CheckBox) checklist2.findViewById(R.id.checklist_ck7);
        cl2ck8 = (CheckBox) checklist2.findViewById(R.id.checklist_ck8);
        cl2ck9 = (CheckBox) checklist2.findViewById(R.id.checklist_ck9);
        cl2ck10 = (CheckBox) checklist2.findViewById(R.id.checklist_ck10);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("grupos").document(idGrupo);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Grupo grupo = documentSnapshot.toObject(Grupo.class);
                txtCurso.setText(grupo.getNombrePlan1());
                txtGrupoTipo.setText("Grupo: " + grupo.getNumero()+ " Tipo: "+grupo.getTipo());
            }
        });

        FirebaseFirestore.getInstance().collection("grupos").document(idGrupo)
        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Grupo grupo = documentSnapshot.toObject(Grupo.class);
                txtCurso.setText(grupo.getNombrePlan1());
                txtGrupoTipo.setText("Grupo: " + grupo.getNumero()+ " Tipo: "+grupo.getTipo());
            }
        });

        FirebaseFirestore.getInstance().collection("silabus").document(idCurso)
                .collection("semanas").document(numeroSemana+"")
                .collection("temas").document("1")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Tema tema = documentSnapshot.toObject(Tema.class);
                if (tema != null){
                    layoutChecklist1.setVisibility(View.VISIBLE);
                    txtTema1.setText("TEMA 1: " + tema.getNombre());
                    CheckBox[] checkBoxes = {cl1ck1, cl1ck2, cl1ck3, cl1ck4, cl1ck5, cl1ck6, cl1ck7, cl1ck8, cl1ck9, cl1ck10};
                    for (int i = 0; i < tema.getActividades().size(); i++) {
                        String actividad = tema.getActividades().get(i);
                        checkBoxes[i].setVisibility(View.VISIBLE);
                        checkBoxes[i].setText(actividad);
                    }
                }
            }
        });

        FirebaseFirestore.getInstance().collection("silabus").document(idCurso)
                .collection("semanas").document(numeroSemana+"")
                .collection("temas").document("2")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Tema tema = documentSnapshot.toObject(Tema.class);
                if (tema != null){
                    layoutChecklist2.setVisibility(View.VISIBLE);
                    txtTema2.setText("TEMA 2: " + tema.getNombre());
                    CheckBox[] checkBoxes = {cl2ck1, cl2ck2, cl2ck3, cl2ck4, cl2ck5, cl2ck6, cl2ck7, cl2ck8, cl2ck9, cl2ck10};
                    for (int i = 0; i < tema.getActividades().size(); i++) {
                        String actividad = tema.getActividades().get(i);
                        checkBoxes[i].setVisibility(View.VISIBLE);
                        checkBoxes[i].setText(actividad);
                    }
                }
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
