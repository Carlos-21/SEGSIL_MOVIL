package pe.edu.unmsm.sistemas.segsil.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pe.edu.unmsm.sistemas.segsil.R;

public class ActividadAdapter extends RecyclerView.Adapter<ActividadAdapter.ActividadHolder>{
    Context context;
    ArrayList<String> actividades;

    public ActividadAdapter(Context context, ArrayList<String> actividades) {
        this.context = context;
        this.actividades = actividades;
    }

    @NonNull
    @Override
    public ActividadHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actividad,parent,false);
        ActividadHolder actividadHolder = new ActividadHolder(view);
        return actividadHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ActividadHolder holder, int position) {
        holder.txtActividad.setText(actividades.get(position));
        holder.cv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context, "long", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return actividades.size();
    }

    static class ActividadHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView txtActividad;
        public ActividadHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.item_actividad_cv);
            txtActividad = (TextView) itemView.findViewById(R.id.item_actividad_txtActividad);
        }
    }
}
