package pe.edu.unmsm.sistemas.segsil.fragments.avance;


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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;

import pe.edu.unmsm.sistemas.segsil.R;
import pe.edu.unmsm.sistemas.segsil.pojos.Avance;
import pe.edu.unmsm.sistemas.segsil.pojos.Grupo;
import pe.edu.unmsm.sistemas.segsil.pojos.Observacion;
import pe.edu.unmsm.sistemas.segsil.pojos.Perfiles;
import pe.edu.unmsm.sistemas.segsil.pojos.Respuestas;
import pe.edu.unmsm.sistemas.segsil.pojos.Tema;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class AvanceSemanaFragment extends Fragment {

    String idGrupo;
    String idCurso;

    int perfil;
    int numeroSemana;
    TextView txtCurso;
    TextView txtGrupoTipo;
    LinearLayout checklist1;
    LinearLayout checklist2;
    LinearLayout layoutChecklist1;
    LinearLayout layoutChecklist2;
    Button btnActualizar;
    CardView btnObservaciones;
    TextView txtTema1;
    TextView txtTema2;
    TextView txtObservaciones;
    CheckBox cl1ck1, cl1ck2, cl1ck3, cl1ck4, cl1ck5, cl1ck6, cl1ck7, cl1ck8, cl1ck9, cl1ck10;
    CheckBox cl2ck1, cl2ck2, cl2ck3, cl2ck4, cl2ck5, cl2ck6, cl2ck7, cl2ck8, cl2ck9, cl2ck10;
    Context contexto;
    ArrayList<CheckBox> checkBoxes1;
    ArrayList<CheckBox> checkBoxes2;

    public AvanceSemanaFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public AvanceSemanaFragment(String idGrupo, String idCurso, int numeroSemana, Context contexto, int perfil) {
        this.idGrupo = idGrupo;
        this.idCurso = idCurso;
        this.numeroSemana = numeroSemana;
        this.contexto = contexto;
        this.perfil = perfil;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_avance_semana, container, false);
        txtCurso = (TextView) rootView.findViewById(R.id.fragment_avance_txtCurso);
        txtGrupoTipo = (TextView) rootView.findViewById(R.id.fragment_avance_txtGrupoTipo);
        layoutChecklist1 = (LinearLayout) rootView.findViewById(R.id.fragment_avance_layout_tema1);
        layoutChecklist2 = (LinearLayout) rootView.findViewById(R.id.fragment_avance_layout_tema2);
        checklist1 = (LinearLayout) rootView.findViewById(R.id.registrar_avance_checklist1);
        checklist2 = (LinearLayout) rootView.findViewById(R.id.registrar_avance_checklist2);
        btnActualizar = (Button) rootView.findViewById(R.id.fragment_avance_btnActualizar);
        btnObservaciones = (CardView) rootView.findViewById(R.id.fragment_avance_btnObservaciones);
        txtObservaciones = (TextView) rootView.findViewById(R.id.fragment_avance_txtxObservaciones);
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
                    checkBoxes1 = new ArrayList<>();
                    for (int i = 0; i < tema.getActividades().size(); i++) {
                        String actividad = tema.getActividades().get(i);
                        checkBoxes[i].setVisibility(View.VISIBLE);
                        checkBoxes[i].setText(actividad);
                        checkBoxes1.add(checkBoxes[i]);
                    }
                    String coleccionRespuestas;
                    String coleccionObservaciones;
                    if(perfil == Perfiles.PROFESOR){
                        coleccionRespuestas = "respuestas_profesores";
                        coleccionObservaciones = "observaciones_profesores";
                    }else{
                        coleccionRespuestas = "respuestas_delegados";
                        coleccionObservaciones = "observaciones_delegados";
                    }
                    String idRespuestas = idGrupo+"_s"+numeroSemana+"_t1";
                    FirebaseFirestore.getInstance().collection(coleccionRespuestas).document(idRespuestas)
                            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Respuestas respuestas = documentSnapshot.toObject(Respuestas.class);
                            if (respuestas != null){
                                for (int i = 0; i < respuestas.getRespuestas().size(); i++) {
                                    boolean respuesta = respuestas.getRespuestas().get(i);
                                    CheckBox[] checkBoxes = {cl1ck1, cl1ck2, cl1ck3, cl1ck4, cl1ck5, cl1ck6, cl1ck7, cl1ck8, cl1ck9, cl1ck10};
                                    if (respuesta) checkBoxes[i].setChecked(true);
                                    else checkBoxes[i].setChecked(false);
                                }
                            }
                        }
                    });
                    FirebaseFirestore.getInstance().collection(coleccionObservaciones).document(idRespuestas)
                            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Observacion observacion = documentSnapshot.toObject(Observacion.class);
                            if (observacion != null){
                                txtObservaciones.setText(observacion.getObservacion());
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
                    CheckBox[] checkBoxes = {cl2ck1, cl2ck2, cl2ck3, cl2ck4, cl2ck5, cl2ck6, cl2ck7, cl2ck8, cl2ck9, cl2ck10};
                    checkBoxes2 = new ArrayList<>();
                    for (int i = 0; i < tema.getActividades().size(); i++) {
                        String actividad = tema.getActividades().get(i);
                        checkBoxes[i].setVisibility(View.VISIBLE);
                        checkBoxes[i].setText(actividad);
                        checkBoxes2.add(checkBoxes[i]);
                    }
                }
            }
        });

        btnObservaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(contexto);
                final View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_observaciones, null);
                LinearLayout lytObservaciones = dialogView.findViewById(R.id.dialog_lytObservaciones);
                final EditText edtObservaciones = dialogView.findViewById(R.id.dialog_edtObservaciones);
                dialog.setView(dialogView);
                dialog.setTitle("Observaciones");
                dialog.setPositiveButton("Aceptar",null);
                dialog.setNegativeButton("Cancelar",null);
                final AlertDialog alertDialog = dialog.create();
                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        edtObservaciones.setText(txtObservaciones.getText().toString());
                        Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // TODO Do something
                                txtObservaciones.setText(edtObservaciones.getText().toString());
                                alertDialog.dismiss();
                            }
                        });
                    }
                });
                alertDialog.show();
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layoutChecklist1.getVisibility() == View.VISIBLE){
                    String coleccionRespuestas;
                    String coleccionObservaciones;
                    String coleccionAvances;
                    ArrayList<Boolean> booleans = new ArrayList<>();
                    int falsos = 0;
                    int verdaderos = 0;
                    for (CheckBox c :checkBoxes1){
                        if(c.isChecked()) {
                            booleans.add(true);
                            verdaderos++;
                        }
                        else {
                            booleans.add(false);
                            falsos++;
                        }
                    }
                    String idRespuestas = idGrupo+"_s"+numeroSemana+"_t1";
                    Respuestas respuestas = new Respuestas(idRespuestas,booleans);
                    Observacion observacion = new Observacion(idRespuestas,txtObservaciones.getText().toString());
                    double p = verdaderos *100.00 / (verdaderos+falsos);
                    if(layoutChecklist2.getVisibility() == View.VISIBLE) p = p/24;
                    else p = p/12;
                    Avance avance = new Avance(idRespuestas,idGrupo,p);
                    if(perfil == Perfiles.PROFESOR){
                        coleccionRespuestas = "respuestas_profesores";
                        coleccionAvances = "avances_profesores";
                        coleccionObservaciones = "observaciones_profesores";
                    }else{
                        coleccionRespuestas = "respuestas_delegados";
                        coleccionAvances = "avances_delegados";
                        coleccionObservaciones = "observaciones_delegados";
                    }
                    FirebaseFirestore.getInstance().collection(coleccionRespuestas)
                            .document(idRespuestas).set(respuestas);

                    FirebaseFirestore.getInstance().collection(coleccionAvances)
                            .document(idRespuestas).set(avance);

                    FirebaseFirestore.getInstance().collection(coleccionObservaciones)
                            .document(idRespuestas).set(observacion)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("FIRESTORE OBSERVACION", "DocumentSnapshot successfully written!");
                                    Toast.makeText(contexto, "DATOS TEMA 1 ACTUALIZADOS", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("FIRESTORE OBSERVACION", "Error writing document", e);
                                }
                            });
                }
                if(layoutChecklist2.getVisibility() == View.VISIBLE){
                    String coleccionRespuestas;
                    String coleccionObservaciones;
                    String coleccionAvances;
                    ArrayList<Boolean> booleans = new ArrayList<>();
                    int falsos = 0;
                    int verdaderos = 0;
                    for (CheckBox c :checkBoxes2){
                        if(c.isChecked()) {
                            booleans.add(true);
                            verdaderos++;
                        }
                        else {
                            booleans.add(false);
                            falsos++;
                        }
                    }
                    String idRespuestas = idGrupo+"_s"+numeroSemana+"_t2";
                    Respuestas respuestas = new Respuestas(idRespuestas,booleans);
                    Observacion observacion = new Observacion(idRespuestas,txtObservaciones.getText().toString());
                    double p = verdaderos *100.00 / (verdaderos+falsos);
                    Avance avance = new Avance(idRespuestas,idGrupo,p/12);
                    if(perfil == Perfiles.PROFESOR){
                        coleccionRespuestas = "respuestas_profesores";
                        coleccionAvances = "avances_profesores";
                        coleccionObservaciones = "observaciones_profesores";
                    }else{
                        coleccionRespuestas = "respuestas_delegados";
                        coleccionAvances = "avances_delegados";
                        coleccionObservaciones = "observaciones_delegados";
                    }
                    FirebaseFirestore.getInstance().collection(coleccionRespuestas)
                            .document(idRespuestas).set(respuestas);
                    FirebaseFirestore.getInstance().collection(coleccionAvances)
                            .document(idRespuestas).set(avance);
                    FirebaseFirestore.getInstance().collection(coleccionObservaciones)
                            .document(idRespuestas).set(observacion)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("FIRESTORE OBSERVACION", "DocumentSnapshot successfully written!");
                                    Toast.makeText(contexto, "DATOS TEMA 2 ACTUALIZADOS", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("FIRESTORE OBSERVACION", "Error writing document", e);
                                }
                            });
                }
            }
        });
    }
}
