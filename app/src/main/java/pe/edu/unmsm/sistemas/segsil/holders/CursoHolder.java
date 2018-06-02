package pe.edu.unmsm.sistemas.segsil.holders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import pe.edu.unmsm.sistemas.segsil.R;

public class CursoHolder extends RecyclerView.ViewHolder{
    private CardView cardView;
    private TextView txtEap;
    private TextView txtNombre1;
    private TextView txtNombre2;


    public CursoHolder(View itemView) {
        super(itemView);
        cardView = (CardView) itemView.findViewById(R.id.item_curso_cv);
        txtEap = (TextView) itemView.findViewById(R.id.item_curso_eap);
        txtNombre1 = (TextView) itemView.findViewById(R.id.item_curso_nombre1);
        txtNombre2 = (TextView) itemView.findViewById(R.id.item_curso_nombre2);

    }

    public void setHolderTxtNombre1(String s){
        txtNombre1.setText(s);
    }
    public void setHolderTxtNombre2(String s){
        txtNombre2.setText(s);
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
