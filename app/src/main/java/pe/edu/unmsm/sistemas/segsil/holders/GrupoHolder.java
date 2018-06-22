package pe.edu.unmsm.sistemas.segsil.holders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import pe.edu.unmsm.sistemas.segsil.R;

public class GrupoHolder extends RecyclerView.ViewHolder{
    private CardView cardView;
    private TextView txtEap;
    private TextView txtNombre;
    private TextView txtNumero;
    private TextView txtTipo;



    public GrupoHolder(View itemView) {
        super(itemView);
        cardView = (CardView) itemView.findViewById(R.id.item_grupo_cv);
        txtEap = (TextView) itemView.findViewById(R.id.item_grupo_eap);
        txtNombre = (TextView) itemView.findViewById(R.id.item_grupo_nombre);
        txtNumero = (TextView) itemView.findViewById(R.id.item_grupo_numero);
        txtTipo = (TextView) itemView.findViewById(R.id.item_grupo_tipo);
    }

    public void setHolderTxtNombre(String s){
        txtNombre.setText(s);
    }
    public void setHolderTxtNumero(String s){
        txtNumero.setText(s);
    }
    public void setHolderTxtTipo(String s){
        txtTipo.setText(s);
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
