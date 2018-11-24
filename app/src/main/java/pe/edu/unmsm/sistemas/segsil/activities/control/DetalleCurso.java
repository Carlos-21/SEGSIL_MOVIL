package pe.edu.unmsm.sistemas.segsil.activities.control;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import pe.edu.unmsm.sistemas.segsil.R;
import pe.edu.unmsm.sistemas.segsil.pojos.Curso;

public class DetalleCurso extends AppCompatActivity {
    private Toolbar myToolbar;
    private TextView det_curso,det_coordinador, det_silabus, det_escuela;
    private Button btn_descargar, btn_avance;
    private static final String TAG = "DETALLE_CURSO";
    private File pdfFile;
    FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    String idCurso, idCoordinador, Mensaje;
    TextView MensajePDF ;
    Curso nuevo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_curso);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            idCurso = bundle.getString("idCurso");
            idCoordinador = bundle.getString("idCoordinador");
            Log.v(TAG,idCurso + " - " + idCoordinador);
        }

        det_curso = (TextView) findViewById(R.id.txt_detalle_Curso);
        det_coordinador = (TextView)findViewById(R.id.txt_detalle_Coordinador);
        det_silabus = (TextView)findViewById(R.id.txt_detalle_silabus);
        det_escuela = (TextView)findViewById(R.id.txt_detalle_escuela);
        btn_descargar = (Button)findViewById(R.id.btn_detalle_descargar);
        btn_avance = (Button)findViewById(R.id.btn_detalle_avance);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("CURSO - COORDINADOR");
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        DocumentReference docRef = db.collection("cursos").document(idCurso);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                nuevo = documentSnapshot.toObject(Curso.class);
                if(nuevo.getEap().equals("SS"))
                    det_escuela.setText("EAP: INGENIERIA DE SISTEMAS");

                if(nuevo.getEap().equals("SW"))
                    det_escuela.setText("EAP: INGENIERIA DE SOFTWARE");

                det_curso.setText("Curso: " + nuevo.getNombreCurso() +
                        '\n' + "Plan 1: " + '\t'  + nuevo.getNombrePlan1() +
                        '\n' + "Plan 2: " + '\t'  + nuevo.getNombrePlan2() );
                det_coordinador.setText("Coordinador: " + '\t' + nuevo.getNombreCoordinador());

                if(nuevo.isSilabus()){
                    det_silabus.setText("Silabus :  OK" +
                            '\n' + "Porcentaje de Avance de Silabus " +
                            '\n' + "Avanze Coordinador  --> " + " X%" +
                            '\n' + "Avanze Delegado  --> " + " Y%" +
                            '\n' + "Avanze Alumnado  --> " + " Z%");
                    btn_descargar.setEnabled(true);
                    btn_descargar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            descargar();
                        }
                    });

                    btn_avance.setText("GENERAR REPORTE");
                    btn_avance.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                createPdf();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (DocumentException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }else{
                    det_silabus.setText("Silabus :  No Registra Silabus"  +
                            '\n' + '\n' + '\n' + "Porcentaje de Avance de Silabus" +
                            '\n' + "Avanze Coordinador  --> " + " 0%" +
                            '\n' + "Avanze Delegado  --> " + " 0%" +
                            '\n' + "Avanze Alumnado  --> " + " 0%");
                    btn_descargar.setEnabled(false);
                    btn_descargar.setText("NO REGISTRA SILABUS");
                    btn_avance.setEnabled(false);
                    btn_avance.setText("SIN AVANCE");
                }
            }
        });


    }

    public void descargar(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        storageRef.child("silabus/" + idCurso + ".pdf").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
               Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                Toast.makeText(DetalleCurso.this, "Descargando...", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(DetalleCurso.this, "Error al descargar...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createPdf() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }

        pdfFile = new File(docsFolder.getAbsolutePath(),"Reporte"+nuevo.getId()+".pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        document.open();
        document.add(new Paragraph(det_escuela.getText().toString()));
        document.add(new Paragraph(det_curso.getText().toString()));
        document.add(new Paragraph(det_coordinador.getText().toString()));
        document.add(new Paragraph(det_silabus.getText().toString()));
        document.close();
        previewPdf("Reporte"+nuevo.getId()+".pdf", this);
    }

    private void previewPdf(String archivo, Context context) {
        File file = new File(archivo);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Download a PDF Viewer to see the generated PDF", Toast.LENGTH_SHORT).show();
        }
    }
}
