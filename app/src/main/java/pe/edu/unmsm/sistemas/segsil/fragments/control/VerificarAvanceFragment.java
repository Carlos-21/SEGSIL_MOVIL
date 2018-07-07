package pe.edu.unmsm.sistemas.segsil.fragments.control;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import pe.edu.unmsm.sistemas.segsil.R;
import pe.edu.unmsm.sistemas.segsil.activities.control.VerificarAvanceActivity;
import pe.edu.unmsm.sistemas.segsil.pojos.Avance;
import pe.edu.unmsm.sistemas.segsil.pojos.Grupo;
import pe.edu.unmsm.sistemas.segsil.pojos.Observacion;
import pe.edu.unmsm.sistemas.segsil.pojos.Perfiles;
import pe.edu.unmsm.sistemas.segsil.pojos.Respuestas;
import pe.edu.unmsm.sistemas.segsil.pojos.Tema;

/**
 * A simple {@link Fragment} subclass.
 */
public class VerificarAvanceFragment extends Fragment {
    String idGrupo;
    String idCurso;
    int perfil;
    int numeroSemana;
    TextView txtCurso;
    TextView txtGrupoTipo;
    TextView porcentajeProfesor;
    TextView porcentajeDelegado;
    ProgressBar progressBarProfesor;
    ProgressBar progressBarDelegado;
    LinearLayout checklist1;
    LinearLayout checklist2;
    LinearLayout layoutChecklist1;
    LinearLayout layoutChecklist2;
    TextView txtTema1;
    TextView txtTema2;
    TextView txtObservaciones1,txtObservaciones2;
    LinearLayout l1lyt1,l1lyt2,l1lyt3,l1lyt4,l1lyt5,l1lyt6,l1lyt7,l1lyt8,l1lyt9,l1lyt10;
    LinearLayout l2lyt1,l2lyt2,l2lyt3,l2lyt4,l2lyt5,l2lyt6,l2lyt7,l2lyt8,l2lyt9,l2lyt10;
    TextView txt1Actividad1, txt1Actividad2, txt1Actividad3, txt1Actividad4, txt1Actividad5, txt1Actividad6, txt1Actividad7, txt1Actividad8, txt1Actividad9, txt1Actividad10;
    TextView txt2Actividad1, txt2Actividad2, txt2Actividad3, txt2Actividad4, txt2Actividad5, txt2Actividad6, txt2Actividad7, txt2Actividad8, txt2Actividad9, txt2Actividad10;
    CheckBox cl1ckp1, cl1ckp2, cl1ckp3, cl1ckp4, cl1ckp5, cl1ckp6, cl1ckp7, cl1ckp8, cl1ckp9, cl1ckp10;
    CheckBox cl2ckp1, cl2ckp2, cl2ckp3, cl2ckp4, cl2ckp5, cl2ckp6, cl2ckp7, cl2ckp8, cl2ckp9, cl2ckp10;
    CheckBox cl1ckd1, cl1ckd2, cl1ckd3, cl1ckd4, cl1ckd5, cl1ckd6, cl1ckd7, cl1ckd8, cl1ckd9, cl1ckd10;
    CheckBox cl2ckd1, cl2ckd2, cl2ckd3, cl2ckd4, cl2ckd5, cl2ckd6, cl2ckd7, cl2ckd8, cl2ckd9, cl2ckd10;
    Context contexto;


    public VerificarAvanceFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public VerificarAvanceFragment(String idGrupo, String idCurso, int numeroSemana, Context contexto, int perfil) {
        this.idGrupo = idGrupo;
        this.idCurso = idCurso;
        this.numeroSemana = numeroSemana;
        this.contexto = contexto;
        this.perfil = perfil;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_verificar_avance, container, false);
        txtCurso = (TextView) rootView.findViewById(R.id.fragment_verificar_avance_txtCurso);
        txtGrupoTipo = (TextView) rootView.findViewById(R.id.fragment_verificar_avance_txtGrupoTipo);
        layoutChecklist1 = (LinearLayout) rootView.findViewById(R.id.fragment_verificar_avance_layout_tema1);
        layoutChecklist2 = (LinearLayout) rootView.findViewById(R.id.fragment_verificar_avance_layout_tema2);
        checklist1 = (LinearLayout) rootView.findViewById(R.id.verificar_avance_checklist1);
        checklist2 = (LinearLayout) rootView.findViewById(R.id.verificar_avance_checklist2);
        txtObservaciones1 = (TextView) rootView.findViewById(R.id.fragment_verificar_avance_txtxObservaciones1);
        txtObservaciones2 = (TextView) rootView.findViewById(R.id.fragment_verificar_avance_txtxObservaciones2);
        txtTema1 = (TextView) checklist1.findViewById(R.id.list_txtTema);
        txtTema2 = (TextView) checklist2.findViewById(R.id.list_txtTema);
        porcentajeProfesor = (TextView) rootView.findViewById(R.id.porcentaje_profesor);
        porcentajeDelegado = (TextView) rootView.findViewById(R.id.porcentaje_delegado);
        progressBarProfesor = (ProgressBar) rootView.findViewById(R.id.progressBar_profesor);
        progressBarDelegado = (ProgressBar) rootView.findViewById(R.id.progressBar_delegado);
        l1lyt1 = (LinearLayout)checklist1.findViewById(R.id.verificar1);
        l1lyt2 = (LinearLayout)checklist1.findViewById(R.id.verificar2);
        l1lyt3 = (LinearLayout)checklist1.findViewById(R.id.verificar3);
        l1lyt4 = (LinearLayout)checklist1.findViewById(R.id.verificar4);
        l1lyt5 = (LinearLayout)checklist1.findViewById(R.id.verificar5);
        l1lyt6 = (LinearLayout)checklist1.findViewById(R.id.verificar6);
        l1lyt7 = (LinearLayout)checklist1.findViewById(R.id.verificar7);
        l1lyt8 = (LinearLayout)checklist1.findViewById(R.id.verificar8);
        l1lyt9 = (LinearLayout)checklist1.findViewById(R.id.verificar9);
        l1lyt10 = (LinearLayout)checklist1.findViewById(R.id.verificar10);

        l2lyt1 = (LinearLayout)checklist2.findViewById(R.id.verificar1);
        l2lyt2 = (LinearLayout)checklist2.findViewById(R.id.verificar2);
        l2lyt3 = (LinearLayout)checklist2.findViewById(R.id.verificar3);
        l2lyt4 = (LinearLayout)checklist2.findViewById(R.id.verificar4);
        l2lyt5 = (LinearLayout)checklist2.findViewById(R.id.verificar5);
        l2lyt6 = (LinearLayout)checklist2.findViewById(R.id.verificar6);
        l2lyt7 = (LinearLayout)checklist2.findViewById(R.id.verificar7);
        l2lyt8 = (LinearLayout)checklist2.findViewById(R.id.verificar8);
        l2lyt9 = (LinearLayout)checklist2.findViewById(R.id.verificar9);
        l2lyt10 = (LinearLayout)checklist2.findViewById(R.id.verificar10);

        cl1ckp1 = (CheckBox) l1lyt1.findViewById(R.id.verificar_ckProfesor);
        cl1ckp2 = (CheckBox) l1lyt2.findViewById(R.id.verificar_ckProfesor);
        cl1ckp3 = (CheckBox) l1lyt3.findViewById(R.id.verificar_ckProfesor);
        cl1ckp4 = (CheckBox) l1lyt4.findViewById(R.id.verificar_ckProfesor);
        cl1ckp5 = (CheckBox) l1lyt5.findViewById(R.id.verificar_ckProfesor);
        cl1ckp6 = (CheckBox) l1lyt6.findViewById(R.id.verificar_ckProfesor);
        cl1ckp7 = (CheckBox) l1lyt7.findViewById(R.id.verificar_ckProfesor);
        cl1ckp8 = (CheckBox) l1lyt8.findViewById(R.id.verificar_ckProfesor);
        cl1ckp9 = (CheckBox) l1lyt9.findViewById(R.id.verificar_ckProfesor);
        cl1ckp10 = (CheckBox) l1lyt10.findViewById(R.id.verificar_ckProfesor);

        cl1ckd1 = (CheckBox) l1lyt1.findViewById(R.id.verificar_ckDelegado);
        cl1ckd2 = (CheckBox) l1lyt2.findViewById(R.id.verificar_ckDelegado);
        cl1ckd3 = (CheckBox) l1lyt3.findViewById(R.id.verificar_ckDelegado);
        cl1ckd4 = (CheckBox) l1lyt4.findViewById(R.id.verificar_ckDelegado);
        cl1ckd5 = (CheckBox) l1lyt5.findViewById(R.id.verificar_ckDelegado);
        cl1ckd6 = (CheckBox) l1lyt6.findViewById(R.id.verificar_ckDelegado);
        cl1ckd7 = (CheckBox) l1lyt7.findViewById(R.id.verificar_ckDelegado);
        cl1ckd8 = (CheckBox) l1lyt8.findViewById(R.id.verificar_ckDelegado);
        cl1ckd9 = (CheckBox) l1lyt9.findViewById(R.id.verificar_ckDelegado);
        cl1ckd10 = (CheckBox) l1lyt10.findViewById(R.id.verificar_ckDelegado);

        txt1Actividad1 = (TextView) l1lyt1.findViewById(R.id.verificar_txtActividad);
        txt1Actividad2 = (TextView) l1lyt2.findViewById(R.id.verificar_txtActividad);
        txt1Actividad3 = (TextView) l1lyt3.findViewById(R.id.verificar_txtActividad);
        txt1Actividad4 = (TextView) l1lyt4.findViewById(R.id.verificar_txtActividad);
        txt1Actividad5 = (TextView) l1lyt5.findViewById(R.id.verificar_txtActividad);
        txt1Actividad6 = (TextView) l1lyt6.findViewById(R.id.verificar_txtActividad);
        txt1Actividad7 = (TextView) l1lyt7.findViewById(R.id.verificar_txtActividad);
        txt1Actividad8 = (TextView) l1lyt8.findViewById(R.id.verificar_txtActividad);
        txt1Actividad9 = (TextView) l1lyt9.findViewById(R.id.verificar_txtActividad);
        txt1Actividad10 = (TextView) l1lyt10.findViewById(R.id.verificar_txtActividad);

        cl2ckp1 = (CheckBox) l2lyt1.findViewById(R.id.verificar_ckProfesor);
        cl2ckp2 = (CheckBox) l2lyt2.findViewById(R.id.verificar_ckProfesor);
        cl2ckp3 = (CheckBox) l2lyt3.findViewById(R.id.verificar_ckProfesor);
        cl2ckp4 = (CheckBox) l2lyt4.findViewById(R.id.verificar_ckProfesor);
        cl2ckp5 = (CheckBox) l2lyt5.findViewById(R.id.verificar_ckProfesor);
        cl2ckp6 = (CheckBox) l2lyt6.findViewById(R.id.verificar_ckProfesor);
        cl2ckp7 = (CheckBox) l2lyt7.findViewById(R.id.verificar_ckProfesor);
        cl2ckp8 = (CheckBox) l2lyt8.findViewById(R.id.verificar_ckProfesor);
        cl2ckp9 = (CheckBox) l2lyt9.findViewById(R.id.verificar_ckProfesor);
        cl2ckp10 = (CheckBox) l2lyt10.findViewById(R.id.verificar_ckProfesor);

        cl2ckd1 = (CheckBox) l2lyt1.findViewById(R.id.verificar_ckDelegado);
        cl2ckd2 = (CheckBox) l2lyt2.findViewById(R.id.verificar_ckDelegado);
        cl2ckd3 = (CheckBox) l2lyt3.findViewById(R.id.verificar_ckDelegado);
        cl2ckd4 = (CheckBox) l2lyt4.findViewById(R.id.verificar_ckDelegado);
        cl2ckd5 = (CheckBox) l2lyt5.findViewById(R.id.verificar_ckDelegado);
        cl2ckd6 = (CheckBox) l2lyt6.findViewById(R.id.verificar_ckDelegado);
        cl2ckd7 = (CheckBox) l2lyt7.findViewById(R.id.verificar_ckDelegado);
        cl2ckd8 = (CheckBox) l2lyt8.findViewById(R.id.verificar_ckDelegado);
        cl2ckd9 = (CheckBox) l2lyt9.findViewById(R.id.verificar_ckDelegado);
        cl2ckd10 = (CheckBox) l2lyt10.findViewById(R.id.verificar_ckDelegado);

        txt2Actividad1 = (TextView) l2lyt1.findViewById(R.id.verificar_txtActividad);
        txt2Actividad2 = (TextView) l2lyt2.findViewById(R.id.verificar_txtActividad);
        txt2Actividad3 = (TextView) l2lyt3.findViewById(R.id.verificar_txtActividad);
        txt2Actividad4 = (TextView) l2lyt4.findViewById(R.id.verificar_txtActividad);
        txt2Actividad5 = (TextView) l2lyt5.findViewById(R.id.verificar_txtActividad);
        txt2Actividad6 = (TextView) l2lyt6.findViewById(R.id.verificar_txtActividad);
        txt2Actividad7 = (TextView) l2lyt7.findViewById(R.id.verificar_txtActividad);
        txt2Actividad8 = (TextView) l2lyt8.findViewById(R.id.verificar_txtActividad);
        txt2Actividad9 = (TextView) l2lyt9.findViewById(R.id.verificar_txtActividad);
        txt2Actividad10 = (TextView) l2lyt10.findViewById(R.id.verificar_txtActividad);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseFirestore.getInstance().collection("avances_profesores").whereEqualTo("idGrupo",idGrupo)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int cantidad = 0;
                    double total = 0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("FIRESTORE PORCENTAJE", document.getId() + " => " + document.getData());
                        Avance avance = document.toObject(Avance.class);
                        total = total + avance.getAvance();
                        cantidad++;
                    }
                    int progreso = (int)Math.floor(total/cantidad);
                    progressBarProfesor.setProgress(progreso);
                    porcentajeProfesor.setText(progreso+"%");
                } else {
                    Log.d("FIRESTORE PORCENTAJE", "Error getting documents: ", task.getException());
                }
            }
        });

        FirebaseFirestore.getInstance().collection("avances_delegados").whereEqualTo("idGrupo",idGrupo)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int cantidad = 0;
                    double total = 0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("FIRESTORE PORCENTAJE", document.getId() + " => " + document.getData());
                        Avance avance = document.toObject(Avance.class);
                        total = total + avance.getAvance();
                        cantidad++;
                    }
                    int progreso = (int)Math.floor(total/cantidad);
                    progressBarDelegado.setProgress(progreso);
                    porcentajeDelegado.setText(progreso+"%");
                } else {
                    Log.d("FIRESTORE PORCENTAJE", "Error getting documents: ", task.getException());
                }
            }
        });

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
                            LinearLayout[] linearLayouts1 = {l1lyt1,l1lyt2,l1lyt3,l1lyt4,l1lyt5,l1lyt6,l1lyt7,l1lyt8,l1lyt9,l1lyt10};
                            TextView[] textViews = {txt1Actividad1,txt1Actividad2,txt1Actividad3,txt1Actividad4,txt1Actividad5,txt1Actividad6,
                                    txt1Actividad7,txt1Actividad8,txt1Actividad9,txt1Actividad10};
                            for (int i = 0; i < tema.getActividades().size(); i++) {
                                String actividad = tema.getActividades().get(i);
                                linearLayouts1[i].setVisibility(View.VISIBLE);
                                textViews[i].setText(actividad);
                            }
                            String idRespuestas = idGrupo+"_s"+numeroSemana+"_t1";
                            FirebaseFirestore.getInstance().collection("respuestas_profesores").document(idRespuestas)
                                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Respuestas respuestas = documentSnapshot.toObject(Respuestas.class);
                                    if (respuestas != null){
                                        for (int i = 0; i < respuestas.getRespuestas().size(); i++) {
                                            boolean respuesta = respuestas.getRespuestas().get(i);
                                            CheckBox[] checkBoxesProfesor = {cl1ckp1, cl1ckp2, cl1ckp3, cl1ckp4, cl1ckp5, cl1ckp6, cl1ckp7, cl1ckp8, cl1ckp9, cl1ckp10};
                                            if (respuesta) checkBoxesProfesor[i].setChecked(true);
                                            else checkBoxesProfesor[i].setChecked(false);
                                        }
                                    }
                                }
                            });

                            FirebaseFirestore.getInstance().collection("respuestas_delegados").document(idRespuestas)
                                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Respuestas respuestas = documentSnapshot.toObject(Respuestas.class);
                                    if (respuestas != null){
                                        for (int i = 0; i < respuestas.getRespuestas().size(); i++) {
                                            boolean respuesta = respuestas.getRespuestas().get(i);
                                            CheckBox[] checkBoxesDelegado = {cl1ckd1, cl1ckd2, cl1ckd3, cl1ckd4, cl1ckd5, cl1ckd6, cl1ckd7, cl1ckd8, cl1ckd9, cl1ckd10};
                                            if (respuesta) checkBoxesDelegado[i].setChecked(true);
                                            else checkBoxesDelegado[i].setChecked(false);
                                        }
                                    }
                                }
                            });
                            FirebaseFirestore.getInstance().collection("observaciones_profesores").document(idRespuestas)
                                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Observacion observacion = documentSnapshot.toObject(Observacion.class);
                                    if (observacion != null){
                                        txtObservaciones1.setText("Profesor:\n" + observacion.getObservacion());
                                    }
                                }
                            });
                            FirebaseFirestore.getInstance().collection("observaciones_delegados").document(idRespuestas)
                                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Observacion observacion = documentSnapshot.toObject(Observacion.class);
                                    if (observacion != null){
                                        txtObservaciones2.setText("Delegado:\n" + observacion.getObservacion());
                                    }
                                }
                            });
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
                    LinearLayout[] linearLayouts1 = {l2lyt1,l2lyt2,l2lyt3,l2lyt4,l2lyt5,l2lyt6,l2lyt7,l2lyt8,l2lyt9,l2lyt10};
                    TextView[] textViews = {txt2Actividad1,txt2Actividad2,txt2Actividad3,txt2Actividad4,txt2Actividad5,txt2Actividad6,
                            txt2Actividad7,txt2Actividad8,txt2Actividad9,txt2Actividad10};
                    for (int i = 0; i < tema.getActividades().size(); i++) {
                        String actividad = tema.getActividades().get(i);
                        linearLayouts1[i].setVisibility(View.VISIBLE);
                        textViews[i].setText(actividad);
                    }
                    String idRespuestas = idGrupo+"_s"+numeroSemana+"_t2";
                    FirebaseFirestore.getInstance().collection("respuestas_profesores").document(idRespuestas)
                            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Respuestas respuestas = documentSnapshot.toObject(Respuestas.class);
                            if (respuestas != null){
                                for (int i = 0; i < respuestas.getRespuestas().size(); i++) {
                                    boolean respuesta = respuestas.getRespuestas().get(i);
                                    CheckBox[] checkBoxesProfesor = {cl2ckp1, cl2ckp2, cl2ckp3, cl2ckp4, cl2ckp5, cl2ckp6, cl2ckp7,
                                            cl2ckp8, cl2ckp9, cl2ckp10};
                                    if (respuesta) checkBoxesProfesor[i].setChecked(true);
                                    else checkBoxesProfesor[i].setChecked(false);
                                }
                            }
                        }
                    });

                    FirebaseFirestore.getInstance().collection("respuestas_delegados").document(idRespuestas)
                            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Respuestas respuestas = documentSnapshot.toObject(Respuestas.class);
                            if (respuestas != null){
                                for (int i = 0; i < respuestas.getRespuestas().size(); i++) {
                                    boolean respuesta = respuestas.getRespuestas().get(i);
                                    CheckBox[] checkBoxesDelegado = {cl2ckd1, cl2ckd2, cl2ckd3, cl2ckd4, cl2ckd5, cl2ckd6, cl2ckd7,
                                            cl2ckd8, cl2ckd9, cl2ckd10};
                                    if (respuesta) checkBoxesDelegado[i].setChecked(true);
                                    else checkBoxesDelegado[i].setChecked(false);
                                }
                            }
                        }
                    });

                }
            }
        });
    }
}
