package pe.edu.unmsm.sistemas.segsil;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class CursoHolder extends RecyclerView.ViewHolder{
    CardView cardView;
    TextView txtEap;
    TextView txtNombre1;
    TextView txtNombre2;


    public CursoHolder(View itemView) {
        super(itemView);
        cardView = (CardView) itemView.findViewById(R.id.item_curso_cv);
        txtEap = (TextView) itemView.findViewById(R.id.item_curso_eap);
        txtNombre1 = (TextView) itemView.findViewById(R.id.item_curso_nombre1);
        txtNombre2 = (TextView) itemView.findViewById(R.id.item_curso_nombre2);

    }
}
