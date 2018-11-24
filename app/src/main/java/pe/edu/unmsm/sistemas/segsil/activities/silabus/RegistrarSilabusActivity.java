package pe.edu.unmsm.sistemas.segsil.activities.silabus;

import android.content.DialogInterface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import pe.edu.unmsm.sistemas.segsil.R;
import pe.edu.unmsm.sistemas.segsil.activities.avance.RegistrarAvanceActivity;
import pe.edu.unmsm.sistemas.segsil.fragments.silabus.ResumenFragment;
import pe.edu.unmsm.sistemas.segsil.fragments.silabus.SemanasFragment;
import pe.edu.unmsm.sistemas.segsil.fragments.silabus.UnidadesFragment;
import pe.edu.unmsm.sistemas.segsil.util.FileChooser;

public class RegistrarSilabusActivity extends AppCompatActivity {
    LinearLayout btnAnterior;
    LinearLayout btnSiguiente;
    TextView txtSiguiente;
    TextView txtTituloFragment;
    Toolbar toolbar;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    String idCurso;
    String nombreCurso;
    int vista_fragment;
    int fragmentActual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_silabus);

        btnAnterior = (LinearLayout) findViewById(R.id.registrar_silabus_layout_btnAnterior);
        btnSiguiente = (LinearLayout) findViewById(R.id.registrar_silabus_layout_btnSiguiente);
        txtSiguiente = (TextView) findViewById(R.id.registrar_silabus_btnSiguiente);
        toolbar = (Toolbar) findViewById(R.id.registrar_silabus_toolbar);
        txtTituloFragment = (TextView) findViewById(R.id.registrar_silabus_txtTituloFragment);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        idCurso = bundle.getString("id_curso");
        nombreCurso = bundle.getString("nombre_curso");
        vista_fragment = bundle.getInt("fragment");
        fragmentActual = vista_fragment;

        Log.v("Fragment", "El valor del fragment es : "+fragmentActual);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.action_bar_title_layout);
        ((TextView) findViewById(R.id.action_bar_titulo)).setText(nombreCurso);

        getSupportActionBar().setTitle("");
        String subtitulo = "";
        if(bundle.getString("eap_curso").equals("SS")) subtitulo = subtitulo + "Sistemas - ";
        else subtitulo = subtitulo + "Software - ";
        subtitulo = subtitulo + "Ciclo " + bundle.getInt("ciclo_curso");
        ((TextView) findViewById(R.id.action_bar_subtitulo)).setText(subtitulo);
//        getSupportActionBar().setSubtitle(subtitulo);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*final AlertDialog.Builder builder = new AlertDialog.Builder(RegistrarSilabusActivity.this);
                builder.setMessage("¿DESEA SALIR DEL PROCESO DE REGISTRO DE SILABUS?");
                builder.setNegativeButton("NO", null);
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        dialog.dismiss();
                    }
                });
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();*/
                onBackPressed();
            }
        });


        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentActual +1 <= 3){
                    fragmentActual++;
                    setFragment(fragmentActual,1);
                }
            }
        });
        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentActual -1 >= 1){
                    fragmentActual--;
                    setFragment(fragmentActual,0);
                }
            }
        });

        setFragment(fragmentActual,1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_silabus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_silabus_subir:
                FileChooser fileChooser = new FileChooser(RegistrarSilabusActivity.this);
                fileChooser.setFileListener(new FileChooser.FileSelectedListener() {
                    @Override
                    public void fileSelected(File file) {
                        String filename = file.getAbsolutePath();
                        Log.d("File", filename);
                        Toast.makeText(RegistrarSilabusActivity.this, "Subiendo...", Toast.LENGTH_SHORT).show();
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReference();
                        Uri uri = Uri.fromFile(file);
                        StorageReference riversRef = storageRef.child("silabus/"+idCurso+".pdf");
                        UploadTask uploadTask = riversRef.putFile(uri);

                        // Register observers to listen for when the download is done or if it fails
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                Toast.makeText(RegistrarSilabusActivity.this, "Error al intentar subir...", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                                // ...
                                Toast.makeText(RegistrarSilabusActivity.this, "Subida Completa...", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                fileChooser.showDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setFragment(int numeroFragment, int direccion){

        FragmentManager fragmentManager =  getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
        if(direccion > 0){
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        }else{
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
        }
        switch (numeroFragment){
            case 1:
                txtTituloFragment.setText("UNIDADES");
                btnAnterior.setVisibility(View.GONE);
                btnSiguiente.setVisibility(View.VISIBLE);
                UnidadesFragment unidadesFragment =  new UnidadesFragment(idCurso,RegistrarSilabusActivity.this);
                fragmentTransaction.replace(R.id.registrar_silabus_fragment,unidadesFragment);
                break;
            case 2:
                txtTituloFragment.setText("SEMANAS");
                btnAnterior.setVisibility(View.VISIBLE);
                btnSiguiente.setVisibility(View.VISIBLE);
                SemanasFragment semanasFragment =  new SemanasFragment(idCurso, nombreCurso,RegistrarSilabusActivity.this);
                fragmentTransaction.replace(R.id.registrar_silabus_fragment, semanasFragment);
                break;
            case 3:
                txtTituloFragment.setText("RESUMEN");
                btnAnterior.setVisibility(View.VISIBLE);
                btnSiguiente.setVisibility(View.GONE);
                ResumenFragment resumenFragment =  new ResumenFragment(idCurso,RegistrarSilabusActivity.this);
                fragmentTransaction.replace(R.id.registrar_silabus_fragment,resumenFragment);
                break;
        }
        fragmentTransaction.commit();
    }
}
