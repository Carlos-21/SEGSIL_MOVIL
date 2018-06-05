package pe.edu.unmsm.sistemas.segsil.activities.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import pe.edu.unmsm.sistemas.segsil.R;
import pe.edu.unmsm.sistemas.segsil.activities.LoginActivity;
import pe.edu.unmsm.sistemas.segsil.activities.silabus.MenuSilabusActivity;
import pe.edu.unmsm.sistemas.segsil.pojos.Curso;
import pe.edu.unmsm.sistemas.segsil.pojos.Grupo;
import pe.edu.unmsm.sistemas.segsil.pojos.Perfil;
import pe.edu.unmsm.sistemas.segsil.pojos.Persona;
import pe.edu.unmsm.sistemas.segsil.pojos.Silabus;
import pe.edu.unmsm.sistemas.segsil.pojos.Usuario;

public class AdminActivity extends AppCompatActivity {

    FirebaseFirestore db;
    Button btnCargaData;
    Button btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btnCargaData =  (Button) findViewById(R.id.admin_btnCargaData);
        btnCerrarSesion = (Button) findViewById(R.id.admin_btnCerrarSesion);

        btnCargaData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = FirebaseFirestore.getInstance();
                cargaUsuarios();
                cargaCursos();
                cargarGrupos();
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void cargaUsuarios(){
        db.collection("usuarios").document("jgonzales").set(new Usuario("jgonzales","Juan Alberto","Gonzales Gutierrez", "123456", new Perfil(false,true,false,false,false,false,false)));
        db.collection("personas").document("jgonzales").set(new Persona("jgonzales", "Juan Alberto","Gonzales Gutierrez", "jgonzales@sistemas.edu.pe", "974654321"));
        db.collection("usuarios").document("hvega").set(new Usuario("hvega", "Hugo Alonso","Vega Alfaro", "123456", new Perfil(false,true,false,false,false,false,false)));
        db.collection("personas").document("hvega").set(new Persona("hvega", "Hugo Alonso","Vega Alfaro", "hvega@sistemas.edu.pe", "974653321"));
        db.collection("usuarios").document("acortez").set(new Usuario("acortez", "Alberto Alonso","Cortez Suarez", "123456", new Perfil(false,false,false,true,false,false,false)));
        db.collection("personas").document("acortez").set(new Persona("acortez", "Alberto Alonso","Cortez Suarez", "acortez@sistemas.edu.pe", "978937421"));
        db.collection("usuarios").document("cnavarro").set(new Usuario("cnavarro", "Carlos Benito","Navarro Cabezas", "123456", new Perfil(false,false,false,true,false,false,false)));
        db.collection("personas").document("cnavarro").set(new Persona("cnavarro", "Carlos Benito","Navarro Cabezas", "cnavarro@sistemas.edu.pe", "978937333"));
        db.collection("usuarios").document("lmota").set(new Usuario("lmota", "Luis Angel","Mota Meza", "123456", new Perfil(false,false,false,false,true,false,false)));
        db.collection("personas").document("lmota").set(new Persona("lmota", "Luis Angel","Mota Meza", "lmota@sistemas.edu.pe", "978447333"));
        db.collection("usuarios").document("promero").set(new Usuario("promero", "Pablo Agustin","Romero Beltran", "123456", new Perfil(false,false,false,false,false,true,false)));
        db.collection("personas").document("promero").set(new Persona("promero", "Pablo Agustin","Romero Beltran", "promero@sistemas.edu.pe", "978441234"));
        db.collection("usuarios").document("dmorales").set(new Usuario("dmorales", "Denis Ricardo","Morales Retamozo", "123456", new Perfil(false,false,false,false,false,false,true)));
        db.collection("personas").document("dmorales").set(new Persona("dmorales", "Denis Ricardo","Morales Retamozo", "dmorales@sistemas.edu.pe", "978447662"));

        db.collection("usuarios").document("smurakami").set(new Usuario("smurakami", "Sumiko","Murakami Cruz", "123456", new Perfil(false,false,false,false,true,true,false)));
        db.collection("personas").document("smurakami").set(new Persona("smurakami", "Sumiko","Murakami Cruz", "smurakami@sistemas.edu.pe", "978447662"));
        db.collection("usuarios").document("fescobedo").set(new Usuario("fescobedo", "Frank Edmundo","Escobedo Bailon", "123456", new Perfil(false,false,false,false,true,true,false)));
        db.collection("personas").document("fescobedo").set(new Persona("fescobedo", "Frank Edmundo","Escobedo Bailon", "fescobedo@sistemas.edu.pe", "978447662"));
        db.collection("usuarios").document("wchalco").set(new Usuario("wchalco", "walter","Chalco Arangonita", "123456", new Perfil(false,false,false,false,true,true,false)));
        db.collection("personas").document("wchalco").set(new Persona("wchalco", "walter","Chalco Arangonita","wchalco@sistemas.edu.pe", "978447662"));
        db.collection("usuarios").document("rsolis").set(new Usuario("rsolis", "Rolando","Solis Narro", "123456", new Perfil(false,false,false,false,true,true,false)));
        db.collection("personas").document("rsolis").set(new Persona("rsolis", "Rolando","Solis Narro", "rsolis@sistemas.edu.pe", "978447662"));
        db.collection("usuarios").document("wacuna").set(new Usuario("wacuna", "Walter","Acuña Montañez", "123456", new Perfil(false,false,false,false,true,true,false)));
        db.collection("personas").document("wacuna").set(new Persona("wacuna", "Walter","Acuña Montañez", "wacuna@sistemas.edu.pe", "978447662"));
        db.collection("usuarios").document("lcachi").set(new Usuario("lcachi", "Luis","Cachi Montoya", "123456", new Perfil(false,false,false,false,true,true,false)));
        db.collection("personas").document("lcachi").set(new Persona("lcachi", "Luis","Cachi Montoya",  "lcachi@sistemas.edu.pe", "978447662"));
        db.collection("usuarios").document("cmora").set(new Usuario("cmora", "Carlos Abel","Mora Zavala", "123456", new Perfil(false,false,false,false,true,true,false)));
        db.collection("personas").document("cmora").set(new Persona("cmora", "Carlos Abel","Mora Zavala", "cmora@sistemas.edu.pe", "978447662"));
    }

    public void cargaCursos(){
        db.collection("cursos").document("sscompinf").set(new Curso("sscompinf","SS",1,"INTRODUCCIÓN A LA  COMPUTACION","(2014)INTRODUCCIÓN A LA  COMPUTACION","(2009)COMPUTACION E INFORMATICA","smurakami","MURAKAMI CRUZ SUMIKO",false));
        db.collection("cursos").document("ssteosis").set(new Curso("ssteosis","SS",1,"TEORIA DE SISTEMAS","(2014)TEORIA DE SISTEMAS","(2009)TEORÍA GENERAL DE SISTEMAS","fescobedo","ESCOBEDO BAILON FRANK EDMUNDO",false));
        db.collection("cursos").document("sscomdin").set(new Curso("sscomdin","SS",1,"COMUNICACIÓN Y DINÁMICA DE GRUPO","(2014)COMUNICACIÓN Y DINÁMICA DE GRUPO","(2009)TALLER DE TÉCNICAS DE ESTUDIO","wchalco","CHALCO ARANGONITA WALTER",false));
        db.collection("cursos").document("ssestapinv").set(new Curso("ssestapinv","SS",1,"ESTRATÉGIAS DE APRENDIZAJE E INVESTIGACIÓN","(2014) ESTRATÉGIAS DE APRENDIZAJE E INVESTIGACIÓN","","rsolis","SOLIS NARRO ROLANDO",false));
        db.collection("cursos").document("sscalc1").set(new Curso("sscalc1","SS",1,"CÁLCULO I","(2014)CÁLCULO I","(2009)CÁLCULO I","wacuna","ACUÑA MONTAÑEZ, WALTER",false));
        db.collection("cursos").document("ssmatbas1").set(new Curso("ssmatbas1","SS",1,"MATEMÁTICA BÁSICA I","(2014)MATEMÁTICA BÁSICA I","(2009)MATEMÁTICA BÁSICA I" ,"lcachi","CACHI MONTOYA LUIS",false));
        db.collection("cursos").document("sseticpro").set(new Curso("sseticpro","SS",1,"ÉTICA DE LA PROFESIÓN","(2014) ÉTICA DE LA PROFESIÓN","","cmora","CARLOS ABEL MORA ZAVALA",false));
    }


    public void cargarGrupos(){
        db.collection("grupos").document("sscompinf1").
                set(new Grupo("sscompinf1","SS",1,"T","sscompinf",1,"(2014)INTRODUCCIÓN A LA  COMPUTACION","(2009)COMPUTACION E INFORMATICA","smurakami","MURAKAMI CRUZ SUMIKO","smurakami","MURAKAMI CRUZ SUMIKO","dmorales","DENIS MORALES RETAMOZO"));
        db.collection("grupos").document("sscompinf1").
                set(new Grupo("sscompinf1","SS",1,"L","sscompinf",1,"(2014)INTRODUCCIÓN A LA  COMPUTACION","(2009)COMPUTACION E INFORMATICA","smurakami","MURAKAMI CRUZ SUMIKO","smurakami","MURAKAMI CRUZ SUMIKO","dmorales","DENIS MORALES RETAMOZO"));

        db.collection("grupos").document("ssteosis1").
                set(new Grupo("ssteosis1","SS",1,"T","ssteosis",1,"(2014)TEORIA DE SISTEMAS","(2009)TEORÍA GENERAL DE SISTEMAS","fescobedo","ESCOBEDO BAILON FRANK EDMUNDO","fescobedo","ESCOBEDO BAILON FRANK EDMUNDO","dmorales","DENIS MORALES RETAMOZO"));

        db.collection("grupos").document("sscomdin1").
                set(new Grupo("sscomdin1","SS",1,"T","sscomdin",1,"(2014)COMUNICACIÓN Y DINÁMICA DE GRUPO","(2009)TALLER DE TÉCNICAS DE ESTUDIO","wchalco","CHALCO ARANGONITA WALTER","wchalco","CHALCO ARANGONITA WALTER","dmorales","DENIS MORALES RETAMOZO"));
        db.collection("grupos").document("sscomdin1").
                set(new Grupo("sscomdin1","SS",1,"P","sscomdin",1,"(2014)COMUNICACIÓN Y DINÁMICA DE GRUPO","(2009)TALLER DE TÉCNICAS DE ESTUDIO","wchalco","CHALCO ARANGONITA WALTER","wchalco","CHALCO ARANGONITA WALTER","dmorales","DENIS MORALES RETAMOZO"));

        db.collection("grupos").document("ssestapinv1").
                set(new Grupo("ssestapinv1","SS",1,"T","ssestapinv",1,"(2014) ESTRATÉGIAS DE APRENDIZAJE E INVESTIGACIÓN","","rsolis","SOLIS NARRO ROLANDO","rsolis","SOLIS NARRO ROLANDO","dmorales","DENIS MORALES RETAMOZO"));
        db.collection("grupos").document("ssestapinv1").
                set(new Grupo("ssestapinv1","SS",1,"P","ssestapinv",1,"(2014) ESTRATÉGIAS DE APRENDIZAJE E INVESTIGACIÓN","","rsolis","SOLIS NARRO ROLANDO","rsolis","SOLIS NARRO ROLANDO","dmorales","DENIS MORALES RETAMOZO"));

        db.collection("grupos").document("sscalc1").
                set(new Grupo("sscalc1","SS",1,"T","sscalc",1,"CÁLCULO I","(2014)CÁLCULO I","wacuna","ACUÑA MONTAÑEZ, WALTER","wacuna","ACUÑA MONTAÑEZ, WALTER","dmorales","DENIS MORALES RETAMOZO"));
        db.collection("grupos").document("sscalc1").
                set(new Grupo("sscalc1","SS",1,"P","sscalc",1,"CÁLCULO I","(2014)CÁLCULO I","wacuna","ACUÑA MONTAÑEZ, WALTER","wacuna","ACUÑA MONTAÑEZ, WALTER","dmorales","DENIS MORALES RETAMOZO"));

        db.collection("grupos").document("ssmatbas1").
                set(new Grupo("ssmatbas1","SS",1,"T","ssmatbas",1,"(2014)MATEMÁTICA BÁSICA I","(2009)MATEMÁTICA BÁSICA I" ,"lcachi","CACHI MONTOYA LUIS","lcachi","CACHI MONTOYA LUIS","dmorales","DENIS MORALES RETAMOZO"));
        db.collection("grupos").document("ssmatbas1").
                set(new Grupo("ssmatbas1","SS",1,"P","ssmatbas",1,"(2014)MATEMÁTICA BÁSICA I","(2009)MATEMÁTICA BÁSICA I" ,"lcachi","CACHI MONTOYA LUIS","lcachi","CACHI MONTOYA LUIS","dmorales","DENIS MORALES RETAMOZO"));

        db.collection("grupos").document("sseticpro1").
                set(new Grupo("sseticpro1","SS",1,"T","sseticpro",1,"(2014) ÉTICA DE LA PROFESIÓN","" ,"cmora","CARLOS ABEL MORA ZAVALA","cmora","CARLOS ABEL MORA ZAVALA","dmorales","DENIS MORALES RETAMOZO"));
        db.collection("grupos").document("sseticpro1").
                set(new Grupo("sseticpro1","SS",1,"P","sseticpro",1,"(2014) ÉTICA DE LA PROFESIÓN","" ,"cmora","CARLOS ABEL MORA ZAVALA","cmora","CARLOS ABEL MORA ZAVALA","dmorales","DENIS MORALES RETAMOZO"));
    }



}
