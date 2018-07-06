package pe.edu.unmsm.sistemas.segsil.holders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import pe.edu.unmsm.sistemas.segsil.R;

public class CursoSilabusHolder extends RecyclerView.ViewHolder{
    private CardView cardView;
    private TextView txtEap;
    private TextView txtNombreCurso;
    private TextView txtNombreCoordinador;


    public CursoSilabusHolder(View itemView) {
        super(itemView);
        cardView = (CardView) itemView.findViewById(R.id.item_curso_silabus_cv);
        txtEap = (TextView) itemView.findViewById(R.id.item_curso_silabus_txtEap);
        txtNombreCurso = (TextView) itemView.findViewById(R.id.item_curso_silabus_txtCurso);
        txtNombreCoordinador = (TextView) itemView.findViewById(R.id.item_curso_silabus_txtCoordinador);
    }

    public void setHolderTxtNombreCurso(String s){
        txtNombreCurso.setText(s);
    }
    public void setHolderTxtNombreCoordinador(String s){
        txtNombreCoordinador.setText(s);
    }
    public void setHolderTxtEap(String s){
        txtEap.setText(s);
    }

    public CardView getCardView() {
        return cardView;
    }

    public void setCardView(CardView cardView) {
        this.cardView = cardView;
    }
}
