package pe.edu.unmsm.sistemas.segsil.holders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import pe.edu.unmsm.sistemas.segsil.R;

public class TemaHolder extends RecyclerView.ViewHolder{
    CardView cv;
    TextView txtTema;
    TextView txtActividades;

    public TemaHolder(View itemView) {
        super(itemView);
        cv =  (CardView) itemView.findViewById(R.id.item_tema_cv);
        txtTema =  (TextView) itemView.findViewById(R.id.item_tema_nombre);
        txtActividades =  (TextView) itemView.findViewById(R.id.item_tema_actividades);
    }

    public CardView getCv() {
        return cv;
    }

    public void setCv(CardView cv) {
        this.cv = cv;
    }

    public void setTxtTema(String s) {
        txtTema.setText(s);
    }

    public void setTxtActividades(String s) {
        txtActividades.setText(s);
    }
}
