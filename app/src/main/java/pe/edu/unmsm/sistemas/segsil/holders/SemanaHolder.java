package pe.edu.unmsm.sistemas.segsil.holders;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import pe.edu.unmsm.sistemas.segsil.R;

public class SemanaHolder extends RecyclerView.ViewHolder{
    TextView txtNumero;
    TextView txtUnidad;
    ImageView imgLleno;
    CardView cv;

    public SemanaHolder(View itemView) {
        super(itemView);
        txtNumero = (TextView) itemView.findViewById(R.id.item_semana_numero);
        txtUnidad = (TextView) itemView.findViewById(R.id.item_semana_unidad);
        imgLleno = (ImageView) itemView.findViewById(R.id.item_semana_lleno);
        cv = (CardView) itemView.findViewById(R.id.item_semana_cv);
    }

    public void setTxtNumero(String s) {
        txtNumero.setText(s);
    }

    public void setTxtUnidad(String s) {
        txtUnidad.setText(s);
    }

    public void setImgLleno(boolean b) {
        if(b) imgLleno.setColorFilter(Color.argb(255, 1, 1, 255));
    }

    public CardView getCv() {
        return cv;
    }
}
