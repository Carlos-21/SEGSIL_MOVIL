package pe.edu.unmsm.sistemas.segsil.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pe.edu.unmsm.sistemas.segsil.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class UnidadesFragment extends Fragment {

    String idCurso;
    Context context;

    public UnidadesFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public UnidadesFragment(String idCurso, Context context) {
        this.idCurso = idCurso;
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_unidades, container, false);
        return rootView;
    }

}
