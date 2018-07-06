package pe.edu.unmsm.sistemas.segsil.activities.control;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import pe.edu.unmsm.sistemas.segsil.R;
import pe.edu.unmsm.sistemas.segsil.activities.LoginActivity;
import pe.edu.unmsm.sistemas.segsil.activities.avance.RegistrarAvanceActivity;
import pe.edu.unmsm.sistemas.segsil.fragments.avance.AvanceSemanaFragment;
import pe.edu.unmsm.sistemas.segsil.fragments.control.VerificarAvanceFragment;

public class VerificarAvanceActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtCurso;
    TextView txtProfesor;
    FirebaseAuth firebaseAuth;
    String idGrupo;
    String idCurso;


    String nombreCurso;
    String nombreProfesor;
    int perfil;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificar_avance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        firebaseAuth = FirebaseAuth.getInstance();
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        idGrupo = bundle.getString("idGrupo");
        idCurso = bundle.getString("curso");
        perfil = bundle.getInt("perfil");
        nombreCurso = bundle.getString("nombre");
        nombreProfesor = bundle.getString("profesor");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        txtCurso = (TextView) headerView.findViewById(R.id.registrar_avance_nav_txtCurso);
        txtProfesor = (TextView) headerView.findViewById(R.id.registrar_avance_nav_txtProfesor);
        txtCurso.setText(nombreCurso);
        txtProfesor.setText(nombreProfesor);
        navigationView.setNavigationItemSelectedListener(this);

        VerificarAvanceFragment verificarAvanceFragment = new VerificarAvanceFragment(idGrupo,idCurso,1,VerificarAvanceActivity.this,perfil);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.registrar_avance_fragment_layout,verificarAvanceFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_avance, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_volver) {
            onBackPressed();
            return true;
        }else if(id == R.id.action_sesion){
            cerrarSesion();
            return true;
        }else if(id == R.id.action_descargar){
            descargar();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void descargar(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        storageRef.child("silabus/" + idCurso + ".pdf").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent);
                Toast.makeText(VerificarAvanceActivity.this, "Descargando...", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(VerificarAvanceActivity.this, "Error al descargar...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        VerificarAvanceFragment verificarAvanceFragment = new VerificarAvanceFragment();
        switch (id){
            case R.id.nav_semana1: verificarAvanceFragment = new VerificarAvanceFragment(idGrupo, idCurso,1,VerificarAvanceActivity.this,perfil);break;
            case R.id.nav_semana2: verificarAvanceFragment = new VerificarAvanceFragment(idGrupo, idCurso,2,VerificarAvanceActivity.this,perfil);break;
            case R.id.nav_semana3: verificarAvanceFragment = new VerificarAvanceFragment(idGrupo, idCurso,3,VerificarAvanceActivity.this,perfil);break;
            case R.id.nav_semana4: verificarAvanceFragment = new VerificarAvanceFragment(idGrupo, idCurso,4,VerificarAvanceActivity.this,perfil);break;
            case R.id.nav_semana5: verificarAvanceFragment = new VerificarAvanceFragment(idGrupo, idCurso,5,VerificarAvanceActivity.this,perfil);break;
            case R.id.nav_semana6: verificarAvanceFragment = new VerificarAvanceFragment(idGrupo, idCurso,6,VerificarAvanceActivity.this,perfil);break;
            case R.id.nav_semana7: verificarAvanceFragment = new VerificarAvanceFragment(idGrupo, idCurso,7,VerificarAvanceActivity.this,perfil);break;
            case R.id.nav_semana9: verificarAvanceFragment = new VerificarAvanceFragment(idGrupo, idCurso,9,VerificarAvanceActivity.this,perfil);break;
            case R.id.nav_semana10: verificarAvanceFragment = new VerificarAvanceFragment(idGrupo, idCurso,10,VerificarAvanceActivity.this,perfil);break;
            case R.id.nav_semana11: verificarAvanceFragment = new VerificarAvanceFragment(idGrupo, idCurso,11,VerificarAvanceActivity.this,perfil);break;
            case R.id.nav_semana12: verificarAvanceFragment = new VerificarAvanceFragment(idGrupo, idCurso,12,VerificarAvanceActivity.this,perfil);break;
            case R.id.nav_semana13: verificarAvanceFragment = new VerificarAvanceFragment(idGrupo, idCurso,13,VerificarAvanceActivity.this,perfil);break;
            case R.id.nav_semana14: verificarAvanceFragment = new VerificarAvanceFragment(idGrupo, idCurso,14,VerificarAvanceActivity.this,perfil);break;
            case R.id.nav_semana15: verificarAvanceFragment = new VerificarAvanceFragment(idGrupo, idCurso,15,VerificarAvanceActivity.this,perfil);break;
        }
        fragmentTransaction.replace(R.id.registrar_avance_fragment_layout,verificarAvanceFragment);
        fragmentTransaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void cerrarSesion(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿DESEA CERRAR SESIÓN Y SALIR DE LA APP?");
        builder.setNegativeButton("CANCELAR", null);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @SuppressLint("NewApi")
            public void onClick(DialogInterface dialog, int id) {
                firebaseAuth.getInstance().signOut();
                finishAffinity();
                dialog.dismiss();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
