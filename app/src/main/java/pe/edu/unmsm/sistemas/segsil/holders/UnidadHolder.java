package pe.edu.unmsm.sistemas.segsil.holders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import pe.edu.unmsm.sistemas.segsil.R;

public class UnidadHolder extends RecyclerView.ViewHolder{
    CardView cardView;
    TextView numero;
    TextView nombre;
    TextView semanas;

    public UnidadHolder(View itemView) {
        super(itemView);
        cardView = (CardView) itemView.findViewById(R.id.item_unidad_cv);
        numero = (TextView) itemView.findViewById(R.id.item_unidad_numero);
        nombre = (TextView) itemView.findViewById(R.id.item_unidad_nombre);
        semanas = (TextView) itemView.findViewById(R.id.item_unidad_semanas);
    }

    public void setNumero(String s) {
        numero.setText(s);
    }

    public void setNombre(String s) {
        nombre.setText(s);
    }

    public void setSemanas(String s) {
        semanas.setText(s);
    }
}
