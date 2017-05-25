package br.com.marks.brinquedoteca.a.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

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
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String key = database.getReference()
                .child("criancas").push().getKey();;

        Crianca crianca1 = new Crianca();
        crianca1.nome = "Gabriel";
        crianca1.idade = 12;
        crianca1.identidade = "14917951747";

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key,crianca1);
        database.getReference("criancas").updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError == null){
                    Toast.makeText(getActivity().getBaseContext(), "Inserido OBJETO", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return inflater.inflate(R.layout.fragment_tela_criancas, container, false);
    }

}
