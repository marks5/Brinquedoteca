package br.com.marks.brinquedoteca.a.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.marks.brinquedoteca.R;
import br.com.marks.brinquedoteca.a.model.Crianca;

/**
 * A simple {@link Fragment} subclass.
 */
public class TelaCriancasFragment extends Fragment {


    public TelaCriancasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("criancas")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        Crianca crianca1 = new Crianca("Gabriel",12);

        myRef.setValue(crianca1);

        return inflater.inflate(R.layout.fragment_tela_criancas, container, false);
    }

}
