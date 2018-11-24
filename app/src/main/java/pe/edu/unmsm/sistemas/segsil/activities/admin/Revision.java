package pe.edu.unmsm.sistemas.segsil.activities.admin;

import android.app.DownloadManager;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


import pe.edu.unmsm.sistemas.segsil.R;
import pe.edu.unmsm.sistemas.segsil.activities.control.DetalleCurso;
import pe.edu.unmsm.sistemas.segsil.pojos.Curso;
import pe.edu.unmsm.sistemas.segsil.pojos.Perfil;
import pe.edu.unmsm.sistemas.segsil.pojos.Usuario;

public class Revision extends AppCompatActivity implements View.OnClickListener{

    private Toolbar myToolbar;
    FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    Button btn_verificar, btn_cargar;
    TextView txt_tabla;
    String tabla;
    private static final String TAG = "SEGUIMIENTO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revision);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btn_verificar = (Button)findViewById(R.id.btn_verificarData);
        btn_cargar = (Button)findViewById(R.id.btn_cargaData);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tabla = bundle.getString("nombreTabla");
            Log.v(TAG,"Nombre de la Tabla - " + tabla);
        }

        txt_tabla = (TextView)findViewById(R.id.txt_tabla);
        txt_tabla.setText(tabla.toUpperCase());

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("ADMINISTRACION DATA");
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_cargar.setOnClickListener(this);
        btn_verificar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_verificarData:
                //Codigo para descargar el archivo a verificar del Firebase Storage
                descargar();
                break;

            case R.id.btn_cargaData:
                //Codigo para Leer el archivo descargado y subir la informacion al firebase database
                Toast.makeText(Revision.this, "Subiendo Data: " + tabla, Toast.LENGTH_SHORT).show();
                if(tabla.equals("cursos")){
                    cargaCursos();
                }

                if(tabla.equals("usuarios")){
                    cargaUsuario();

                }

                if(tabla.equals("grupos")){
                    //readExcelData(tabla+".xlsx");
                    //cargarGrupos();
                }

                break;

            default:
                break;
        }
    }

    public void descargar(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        storageRef.child("carga_data/" + tabla + ".xlsx").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                Toast.makeText(Revision.this, "Descargando...", Toast.LENGTH_SHORT).show();
                //DownloadManager.Request r = new DownloadManager.Request(uri);
                //r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Filename");
                //r.allowScanningByMediaScanner();
                //r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                //DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                //dm.enqueue(r);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Toast.makeText(Revision.this, "Error al descargar...", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //Carga - Opcion 2
    public void cargaUsuario(){
        db.collection("usuarios").document("acortez").	set(new Usuario("acortez","Augusto","Cortez Vasquez", "123456",
                new Perfil(true,true,false,false,true,false,false)));

        db.collection("usuarios").document("admin").	    set(new Usuario("admin","Daniela Veronica","Lovera Miranda", "123456", 		new Perfil(true,false,false,false,false,false,false)));
        db.collection("usuarios").document("cnavarro").	set(new Usuario("cnavarro","Carlos Edmundo","Navarro De Paz", "123456",	 	    new Perfil(false,false,false,false,false,false,false)));
        db.collection("usuarios").document("dlovera").	set(new Usuario("dlovera","Daniela Veronica","Lovera Miranda", "123456",	    new Perfil(false,false,false,true,false,false,false)));
        db.collection("usuarios").document("dmorales").	set(new Usuario("dmorales","Denis Ricardo","Morales Retamozo", "123456",	    new Perfil(false,false,false,true,false,false,false)));
        db.collection("usuarios").document("dquinto").	set(new Usuario("dquinto","Daniel","Quinto Pacze", "123456", 			        new Perfil(false,false,false,false,false,false,true)));
        db.collection("usuarios").document("fescobedo").  set(new Usuario("fescobedo","Frank Edmundo","Escobedo Bailon", "123456",	    new Perfil(false,true,false,false,false,false,true)));
        db.collection("usuarios").document("hvega").	    set(new Usuario("hvega","Hugo Froilan","Vega Huerta", "123456", 		    new Perfil(false,false,true,false,false,false,false)));
        db.collection("usuarios").document("javendano").  set(new Usuario("javendano","Johnny Robert","Avendano Quiroz", "123456",	    new Perfil(false,true,false,false,false,false,true)));
        db.collection("usuarios").document("jgonzales").  set(new Usuario("jgonzales","Juan Carlos","Gonzales Suarez", "123456",  	    new Perfil(false,false,true,false,false,false,false)));
        db.collection("usuarios").document("jprueba").	set(new Usuario("jprueba","Alumno","Prueba", "123456", 				            new Perfil(false,false,false,true,false,false,false)));
        db.collection("usuarios").document("jtrujillo").  set(new Usuario("jtrujillo","John Ledgard","Trujillo Trejo", "123456",  	    new Perfil(false,false,true,false,false,false,true)));
        db.collection("usuarios").document("lmota").	    set(new Usuario("lmota","Lazaro Florian","Mota Alva", "123456", 		    new Perfil(false,true,false,false,false,false,true)));
        db.collection("usuarios").document("ngonzales").  set(new Usuario("ngonzales","Nelson ","Gonzales Andia", "123456", 		    new Perfil(false,false,false,true,false,false,false)));
        db.collection("usuarios").document("pprueba").	set(new Usuario("pprueba","Prueba","Prueba Curso", "123456", 			        new Perfil(false,true,false,false,false,false,true)));
        db.collection("usuarios").document("smoquillaza").set(new Usuario("smoquillaza","Santiago","Moquillaza Henriquez", "123456", 	new Perfil(false,true,false,false,false,false,true)));
        db.collection("usuarios").document("vvera").	    set(new Usuario("vvera","Virginia","Vera Pomalaza", "123456", 			    new Perfil(false,true,false,false,false,false,true)));
        db.collection("usuarios").document("zlam").	    set(new Usuario("zlam","Zhing Fong","Lam", "123456", 				            new Perfil(false,false,false,false,false,false,true)));
        db.collection("usuarios").document("ldelpino").	set(new Usuario("ldelpino","Luz Corina","Del Pino Rodriguez", "123456", 	    new Perfil(false,true,false,false,false,false,true)));
        db.collection("usuarios").document("nmunoz").	    set(new Usuario("nmunoz","Nehil Indalicio","Muñoz Casildo", "123456", 		new Perfil(false,true,false,false,false,false,true)));
        db.collection("usuarios").document("calcantara"). set(new Usuario("calcantara","Cesar Augusto","Alcantara Loayza", "123456", 	new Perfil(false,true,false,false,false,false,true)));
        db.collection("usuarios").document("gvalverde").  set(new Usuario("gvalverde","Giovana Melva","Valverde Ayala", "123456",   	new Perfil(false,true,false,false,false,false,true)));
        db.collection("usuarios").document("msotelo").	set(new Usuario("msotelo","Marcos Adolfo","Sotelo Bedon", "123456", 		    new Perfil(false,true,false,false,false,false,true)));
        db.collection("usuarios").document("rarmas").	    set(new Usuario("rarmas","Raul Marcelo","Armas Calderon", "123456", 		new Perfil(false,true,false,false,false,false,true)));
        db.collection("usuarios").document("ncarrasco").  set(new Usuario("ncarrasco","Nilo Eloy","Carrasco Ore", "123456", 		    new Perfil(false,true,false,false,false,false,true)));
        db.collection("usuarios").document("respinoza").  set(new Usuario("respinoza","Robert","Espinoza Dominguez", "123456", 	        new Perfil(false,false,false,false,false,false,true)));
        db.collection("usuarios").document("jpariona").	set(new Usuario("jpariona","Jaime","Pariona Quispe", "123456", 			        new Perfil(false,false,false,false,false,false,true)));
        db.collection("usuarios").document("hpaucar").	set(new Usuario("hpaucar","Herminio","Paucar", "123456", 			            new Perfil(false,false,false,false,false,false,true)));
        db.collection("usuarios").document("despinoza").  set(new Usuario("despinoza","David","Espinoza Robles", "123456", 		        new Perfil(false,false,false,false,false,false,true)));
        db.collection("usuarios").document("lsoto").	    set(new Usuario("lsoto","Luis","Soto Soto", "123456", 				        new Perfil(false,false,false,false,false,false,true)));
        db.collection("usuarios").document("emacdowal").  set(new Usuario("emacdowal","Edwin","MacDowal Reynoso", "123456", 		    new Perfil(false,false,false,false,false,false,true)));
        db.collection("usuarios").document("rmaguina").	set(new Usuario("rmaguina","Rolando","Maguiña Perez", "123456", 		        new Perfil(false,false,false,false,false,false,true)));
        db.collection("usuarios").document("ahuayna").	set(new Usuario("ahuayna","Ana Maria","Huayna Dueñas", "123456", 		        new Perfil(false,false,false,false,false,false,true)));
        db.collection("usuarios").document("lmalasquez"). set(new Usuario("lmalasquez","Lucio","Malasquez Ruiz", "123456", 		        new Perfil(false,false,false,false,false,false,true)));
        db.collection("usuarios").document("svalcarcel"). set(new Usuario("svalcarcel","Sergio","Valcarcel Ascencios", "123456", 	    new Perfil(false,false,false,false,false,false,true)));


    }

    public void cargaCursos(){
        //Ciclo 1
        db.collection("cursos").document("sscompinf"). set(new Curso(  "sscompinf","SS", 1,
                        "INTRODUCCION A LA  COMPUTACION","(2014)INTRODUCCION A LA  COMPUTACION", " ",
                        "smurakami","MURAKAMI CRUZ SUMIKO",false));

        db.collection("cursos").document("ssteosis").set(new Curso("ssteosis","SS",1,
                        "TEORIA DE SISTEMAS","(2014)TEORIA DE SISTEMAS","(2009)TEORIA GENERAL DE SISTEMAS",
                        "fescobedo","ESCOBEDO BAILON FRANK EDMUNDO",false));

        db.collection("cursos").document("swcomdin").set(new Curso("swcomdin","SW",1,
                        "COMUNICACION Y DINAMICA DE GRUPO","(2014)COMUNICACION Y DINAMICA DE GRUPO","(2009)TALLER DE TECNICAS DE ESTUDIO",
                        "wchalco","CHALCO ARANGONITA WALTER",false));

        db.collection("cursos").document("swestapinv").set(new Curso("swestapinv","SW",1,
                        "ESTRATEGIAS DE APRENDIZAJE E INVESTIGACION","(2014) ESTRATEGIAS DE APRENDIZAJE E INVESTIGACION"," ",
                        "rsolis","SOLIS NARRO ROLANDO",false));

        db.collection("cursos").document("sscalc1").set(new Curso("sscalc1","SS",1,
                        "CALCULO I","(2014)CALCULO I","(2009)CALCULO I",
                        "wacuna","ACUÑA MONTAÑEZ, WALTER",false));

        db.collection("cursos").document("ssmatbas1").set(new Curso("ssmatbas1","SS",1,
                        "MATEMATICA BASICA I","(2014)MATEMATICA BASICA I","(2009)MATEMATICA BASICA I" ,
                        "lcachi","CACHI MONTOYA LUIS",false));

        db.collection("cursos").document("sweticpro").set(new Curso("sweticpro","SW",1,
                        "ETICA DE LA PROFESION","(2014) ETICA DE LA PROFESION","",
                        "cmora","CARLOS ABEL MORA ZAVALA",false));


        //Ciclo 6
        db.collection("cursos").document("sscompinf").set(new Curso(  	"ssbd2","SS", 6,
                "BASE DE DATOS II","(2014) BASE DE DATOS II"," ",
                "ldelpino","DEL PINO RODRIGUEZ, LUZ CORINA",false));

        db.collection("cursos").document("sscompinf").set(new Curso(  	"sscvisual","SS", 6,
                "COMPUTACION VISUAL","(2014) COMPUTACION VISUAL","(2009) COMPUTACION GRAFICA - CICLO IV",
                "javendano","AVENDAÑO QUIROZ, JOHNNY ROBERTO",false));

        db.collection("cursos").document("sscompinf").set(new Curso(  	"ssdsistemas","SS", 6,
                "DISEÑO DE SISTEMAS DE INFORMACION","(2014) DISEÑO DE SISTEMAS DE INFORMACION","(2009) DISEÑO DE SISTEMAS - CICLO VII",
                "nmunoz","MUÑOZ CASILDO, NEHIL INDALICIO",false));

        db.collection("cursos").document("sscompinf").set(new Curso(  	"ssgpn","SS", 6,
                "GESTION DE LOS PROCESOS DE NEGOCIOS","(2014) GESTION DE LOS PROCESOS DE NEGOCIOS"," ",
                "calcantara","ALCÁNTARA LOAYZA, CÉSAR  AUGUSTO",false));

        db.collection("cursos").document("sscompinf").set(new Curso(  	"ssia","SS", 6,
                "INTELIGENCIA ARTIFICIAL","(2014) INTELIGENCIA ARTIFICIAL","(2009) INTELIGENCIA ARTIFICIAL - Ciclo VII",
                "hvega","VEGA HUERTA, HUGO FROILAN",false));

        db.collection("cursos").document("sscompinf").set(new Curso(  	"ssio1","SS", 6,
                "INVESTIGACION OPERATIVA I","(2014) INVESTIGACION OPERATIVA I","(2009) INVESTIGACION OPERATIVA I - Ciclo V",
                "gvalverde","VALVERDE AYALA, GIOVANA MELVA",false));

        db.collection("cursos").document("sscompinf").set(new Curso(  	"sstp1","SS", 6,
                "TALLER DE PROYECTOS I","(2009) TALLER DE PROYECTOS I"," ",
                "msotelo","SOTELO BEDON, MARCOS ADOLFO",false));

        db.collection("cursos").document("sscompinf").set(new Curso(  	"sstrasmi","SS", 6,
                "INTRODUCCION A LA  COMPUTACION","(2009) TRANSMISION DE DATOS"," ",
                "rarmas","ARMAS CALDERON, RAUL MARCELO",false));

        db.collection("cursos").document("sscompinf").set(new Curso(  	"ssredtrasmi","SS", 6,
                "REDES Y TRANSMISION DE DATOS","(2014) REDES Y TRANSMISION DE DATOS"," ",
                "ncarrasco","CARRASCO ORE, NILO ELOY",false));

    }
}
