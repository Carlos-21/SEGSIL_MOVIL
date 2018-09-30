package pe.edu.unmsm.sistemas.segsil.activities.silabus;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import pe.edu.unmsm.sistemas.segsil.activities.BienvenidoActivity;
import pe.edu.unmsm.sistemas.segsil.activities.LoginActivity;
import pe.edu.unmsm.sistemas.segsil.pojos.Tema;
import pe.edu.unmsm.sistemas.segsil.util.NumericKeyBoardTransformationMethod;
import pe.edu.unmsm.sistemas.segsil.R;
import pe.edu.unmsm.sistemas.segsil.pojos.Semana;
import pe.edu.unmsm.sistemas.segsil.pojos.Silabus;
import pe.edu.unmsm.sistemas.segsil.pojos.Unidad;

public class UnidadActivity extends AppCompatActivity {

    TextView txtNumero;
    TextInputEditText edtNombre;
    //    TextInputEditText edtSemanas;
    NumberPicker numberPicker;
    Button btnRegistrar;
    String idCurso;
    String action;
    int semanas;
    final String TAG = "FIRESTORE";
    int numero;
    int posicionNueva = 1;
    int nuevoNumero;
    String idSemana;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference unidadesCollection;
    DocumentReference unidadDocument;
    CollectionReference semanasCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unidad);


        idCurso = getIntent().getExtras().getString("curso");
        numero = getIntent().getExtras().getInt("numero");
        action = getIntent().getExtras().getString("action");
        semanas = getIntent().getExtras().getInt("semanas");

        txtNumero = (TextView) findViewById(R.id.registrar_unidad_txtNumero);
        edtNombre = (TextInputEditText) findViewById(R.id.registrar_unidad_edtNombre);
//        edtSemanas = (TextInputEditText) findViewById(R.id.registrar_unidad_edtSemanas);
        btnRegistrar = (Button) findViewById(R.id.registrar_unidad_btnRegistrar);
        numberPicker = (NumberPicker) findViewById(R.id.registrar_unidad_numSemanas);


        unidadesCollection = db.collection("silabus").document(idCurso).collection("unidades");
        unidadDocument = unidadesCollection.document(numero+"");
        semanasCollection = db.collection("silabus").document(idCurso).collection("semanas");

        txtNumero.setText("REGISTRAR UNIDAD N° " + numero);
//        edtSemanas.setTransformationMethod(new NumericKeyBoardTransformationMethod());
//        edtSemanas.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        edtNombre.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30),new InputFilter.AllCaps()});
        numberPicker.setMinValue(1);

        unidadesCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int numeroSemanas = 0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        Unidad unidad = document.toObject(Unidad.class);
                        numeroSemanas = numeroSemanas + unidad.getSemanas();
                    }
                    if ( action != null ) {
                        numberPicker.setMaxValue(14 - numeroSemanas + semanas);
                    } else {
                        numberPicker.setMaxValue(14 - numeroSemanas);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (camposValidos()){

                    if (action != null) {

                        if(numberPicker.getValue() > semanas) {

                            unidadesCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    Unidad unidad = new Unidad();

                                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                        unidad = documentSnapshot.toObject(Unidad.class);

                                        if (numero >= unidad.getNumero()) {
                                            posicionNueva = posicionNueva + unidad.getSemanas();
                                        }
                                    }
                                    if (posicionNueva > 7) posicionNueva++;
                                    unidadDocument.set(new Unidad(numero, edtNombre.getText().toString(), numberPicker.getValue()));

//                                        Toast.makeText(getApplicationContext(), "POSICION NUEVA " + posicionNueva, Toast.LENGTH_SHORT).show();

                                    semanasCollection.orderBy("numero", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            Semana semana = new Semana();
                                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                                semana = documentSnapshot.toObject(Semana.class);
                                                idSemana = documentSnapshot.getId();

                                                if (semana.getNumero() >= posicionNueva) {
                                                    nuevoNumero = semana.getNumero() + numberPicker.getValue() - semanas;
                                                    if (semana.getNumero() <= 7 && nuevoNumero > 7) nuevoNumero++;
//                                                        Toast.makeText(getApplicationContext(), "De Semana: " + semana.getNumero() + " A " + posicionNueva, Toast.LENGTH_SHORT).show();
//                                                        Toast.makeText(getApplicationContext(),"ID DOCUMENTO : "+documentSnapshot.getId(),Toast.LENGTH_SHORT);
//                                                        semanasCollection.document(documentSnapshot.getId()).update("numero", nuevoNumero);
                                                    semanasCollection.document(nuevoNumero+"").set(documentSnapshot.getData());
                                                    semanasCollection.document(nuevoNumero+"").update("numero",nuevoNumero);
//                                                        Toast.makeText(getApplicationContext(),"INICIARA EL COPIADO Y BORRADO XD",Toast.LENGTH_SHORT);
//
                                                    semanasCollection.document(documentSnapshot.getId()).collection("temas").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                            for(QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                                                                Toast.makeText(getApplicationContext(),"PASO POR AQUÍ",Toast.LENGTH_SHORT);

                                                                semanasCollection.document(nuevoNumero+"").collection("temas").add(queryDocumentSnapshot.getData());
//                                                                    Toast.makeText(getApplicationContext(),"Borrar: "+idSemana + " " + queryDocumentSnapshot.getId(),Toast.LENGTH_SHORT);
//
                                                                semanasCollection.document(idSemana).collection("temas").document(queryDocumentSnapshot.getId()).delete();
                                                            }
                                                        }
                                                    });
                                                }
                                            }

                                            for (int i = posicionNueva; i < posicionNueva + numberPicker.getValue() - semanas; i++) {
                                                int id = i;
                                                if(posicionNueva <= 7 && i > 7) id++;
                                                Toast.makeText(getApplicationContext(), "INSERTANDO SEMANA: " + id, Toast.LENGTH_SHORT).show();
                                                semanasCollection.document(id + "").set(new Semana(id, numero, edtNombre.getText().toString(), false));
                                            }
                                        }
                                    });

                                }
                            });
                        } else {
                            unidadDocument.set(new Unidad(numero, edtNombre.getText().toString(), numberPicker.getValue()));
                        }
                        semanasCollection.whereEqualTo("unidad",numero).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                Semana semana = new Semana();
                                for(QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                                    semana = queryDocumentSnapshot.toObject(Semana.class);
                                    semanasCollection.document(queryDocumentSnapshot.getId()).update("nombreUnidad",edtNombre.getText().toString());
                                }
                            }
                        });

                    } else {
                        FirebaseFirestore.getInstance().collection("silabus").document(idCurso).set(new Silabus(idCurso));
                        FirebaseFirestore.getInstance().collection("silabus").document(idCurso).collection("unidades").document(numero + "")
                                .set(new Unidad(numero, edtNombre.getText().toString(), numberPicker.getValue()));
                        FirebaseFirestore.getInstance().collection("silabus").document(idCurso).collection("semanas")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            List<Semana> semanas = new ArrayList<>();
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Log.d(TAG, document.getId() + " => " + document.getData());
                                                semanas.add(document.toObject(Semana.class));
                                            }
                                            int num = numberPicker.getValue();
                                            for (int i = 1; i <= num; i++) {
                                                int n = semanas.size() + i;
                                                if (n > 7) n++;
                                                FirebaseFirestore.getInstance().collection("silabus").document(idCurso)
                                                        .collection("semanas").document(n + "").set(new Semana(n, numero, edtNombre.getText().toString(), false));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                    }
                    finish();
                }

            }
        });

        if ( action != null ){
            edtNombre.setText(getIntent().getExtras().getString("nombre"));
            numberPicker.setMinValue(semanas);
        }

    }


    public boolean camposValidos(){
        boolean valido1 = true, valido2= true;
//        if(edtSemanas.getText().toString().trim().equals("")){
//            valido1 = false;
//        }else{
//            if (edtSemanas.getText().toString().trim().equals("0")){
//                valido1 = false;
//            }
//        }
        if(edtNombre.getText().toString().trim().equals("")){
            valido2 = false;
        }

        boolean camposOk = valido1 && valido2;
        if(!camposOk) mostrarMensaje("DEBE LLENAR LOS CAMPOS CON DATOS CORRECTOS");
        return camposOk;
    }

    public void mostrarMensaje(String s){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(s);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
