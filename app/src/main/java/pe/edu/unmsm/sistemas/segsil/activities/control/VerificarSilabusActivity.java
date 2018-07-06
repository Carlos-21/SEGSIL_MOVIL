package pe.edu.unmsm.sistemas.segsil.activities.control;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import pe.edu.unmsm.sistemas.segsil.R;
import pe.edu.unmsm.sistemas.segsil.pojos.Curso;
import pe.edu.unmsm.sistemas.segsil.pojos.Semana;
import pe.edu.unmsm.sistemas.segsil.pojos.Tema;

public class VerificarSilabusActivity extends AppCompatActivity {
    String idCurso;
    LinearLayout semana1, semana2, semana3, semana4, semana5, semana6, semana7,
            semana9, semana10, semana11, semana12, semana13, semana14, semana15;
    TextView txtSemana1,txtSemana2, txtSemana3, txtSemana4, txtSemana5, txtSemana6, txtSemana7,
            txtSemana9, txtSemana10, txtSemana11, txtSemana12, txtSemana13, txtSemana14, txtSemana15;
    TextView txtTemasSemana1, txtTemasSemana2, txtTemasSemana3, txtTemasSemana4, txtTemasSemana5, txtTemasSemana6, txtTemasSemana7,
            txtTemasSemana9, txtTemasSemana10, txtTemasSemana11, txtTemasSemana12, txtTemasSemana13, txtTemasSemana14, txtTemasSemana15;
    String TAG = "FIRESTORE";

    Button btnFinalizar;

    TextView txtNombreCurso, txtCoordinador;
    Toolbar myToolbar;
    TextView[] textViewSemanas;
    TextView[] textViewTemas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificar_silabus);

        idCurso = getIntent().getExtras().getString("idCurso");
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("VERIFICAR SILABUS");
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txtNombreCurso = findViewById(R.id.silabus_registrado_txtCurso);
        txtCoordinador = findViewById(R.id.silabus_registrado_txtCoordinador);
        semana1 = findViewById(R.id.item_resumen_semana1);
        semana2 = findViewById(R.id.item_resumen_semana2);
        semana3 = findViewById(R.id.item_resumen_semana3);
        semana4 = findViewById(R.id.item_resumen_semana4);
        semana5 = findViewById(R.id.item_resumen_semana5);
        semana6 = findViewById(R.id.item_resumen_semana6);
        semana7 = findViewById(R.id.item_resumen_semana7);
        semana9 = findViewById(R.id.item_resumen_semana9);
        semana10 = findViewById(R.id.item_resumen_semana10);
        semana11 = findViewById(R.id.item_resumen_semana11);
        semana12 = findViewById(R.id.item_resumen_semana12);
        semana13 = findViewById(R.id.item_resumen_semana13);
        semana14 = findViewById(R.id.item_resumen_semana14);
        semana15 = findViewById(R.id.item_resumen_semana15);
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
        txtTemasSemana2 = semana2.findViewById(R.id.item_resumen_txtTemas);
        txtTemasSemana3 = semana3.findViewById(R.id.item_resumen_txtTemas);
        txtTemasSemana4 = semana4.findViewById(R.id.item_resumen_txtTemas);
        txtTemasSemana5 = semana5.findViewById(R.id.item_resumen_txtTemas);
        txtTemasSemana6 = semana6.findViewById(R.id.item_resumen_txtTemas);
        txtTemasSemana7 = semana7.findViewById(R.id.item_resumen_txtTemas);
        txtTemasSemana9 = semana9.findViewById(R.id.item_resumen_txtTemas);
        txtTemasSemana10 = semana10.findViewById(R.id.item_resumen_txtTemas);
        txtTemasSemana11 = semana11.findViewById(R.id.item_resumen_txtTemas);
        txtTemasSemana12 = semana12.findViewById(R.id.item_resumen_txtTemas);
        txtTemasSemana13 = semana13.findViewById(R.id.item_resumen_txtTemas);
        txtTemasSemana14 = semana14.findViewById(R.id.item_resumen_txtTemas);
        txtTemasSemana15 = semana15.findViewById(R.id.item_resumen_txtTemas);
        textViewTemas =  new TextView[]{txtTemasSemana1, txtTemasSemana2, txtTemasSemana3, txtTemasSemana4, txtTemasSemana5, txtTemasSemana6, txtTemasSemana7, txtTemasSemana9, txtTemasSemana10, txtTemasSemana11, txtTemasSemana12, txtTemasSemana13, txtTemasSemana14, txtTemasSemana15};
        btnFinalizar = (Button) findViewById(R.id.resumen_fragment_btnFinalizar);

        FirebaseFirestore.getInstance().collection("cursos").document(idCurso)
        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Curso curso = documentSnapshot.toObject(Curso.class);
                txtNombreCurso.setText(curso.getNombreCurso());
                txtCoordinador.setText("Coordinador: " + curso.getNombreCoordinador());
            }
        });

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
                descargar();
            }
        });
    }

    public void descargar(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        storageRef.child("silabus/" + idCurso + ".pdf").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent);
                Toast.makeText(VerificarSilabusActivity.this, "Descargando...", Toast.LENGTH_SHORT).show();
                DownloadManager.Request r = new DownloadManager.Request(uri);
                r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "fileName");
                r.allowScanningByMediaScanner();
                r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(r);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Toast.makeText(VerificarSilabusActivity.this, "Error al descargar...", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
