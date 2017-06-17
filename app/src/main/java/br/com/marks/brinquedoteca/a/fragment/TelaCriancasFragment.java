package br.com.marks.brinquedoteca.a.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import br.com.marks.brinquedoteca.R;
import br.com.marks.brinquedoteca.a.adapter.CriancasAdapter;
import br.com.marks.brinquedoteca.a.adapter.CriancasAdapterFirebase;
import br.com.marks.brinquedoteca.a.model.Crianca;

/**
 * A simple {@link Fragment} subclass.
 */
public class TelaCriancasFragment extends Fragment {

    private RecyclerView rv_children;
    private FloatingActionButton fab_add;

    public Context context;

    private final static String SAVED_ADAPTER_ITEMS = "SAVED_ADAPTER_ITEMS";
    private final static String SAVED_ADAPTER_KEYS = "SAVED_ADAPTER_KEYS";

    private Query mQuery;
    private CriancasAdapterFirebase mMyAdapter;
    private ArrayList<Crianca> mAdapterItems;
    private ArrayList<String> mAdapterKeys;

    private GridLayoutManager glm;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mChildrenRef;

    private CriancasAdapter mRecyclerAdapter;

    public TelaCriancasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tela_criancas, container, false);
        mChildrenRef = database.getReference("criancas");
        context = getContext();

        handleInstanceState(savedInstanceState);
        v.findViewById(R.id.edt_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insereItem();
            }
        });

        rv_children = (RecyclerView) v.findViewById(R.id.rv_children);
        fab_add = (FloatingActionButton) v.findViewById(R.id.fab_add);

        setupFirebaseAdapter();

        return v;
    }

    private void insereItem(){
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
    }

    private void setupFirebaseAdapter(){
        rv_children.setHasFixedSize(true);

        glm = new GridLayoutManager(getActivity(),5);
        glm.setOrientation(GridLayoutManager.VERTICAL);
        rv_children.setLayoutManager(glm);

        Query lastFifty = mChildrenRef.limitToLast(50);

        mRecyclerAdapter = new CriancasAdapter(lastFifty);

        rv_children.setAdapter(mRecyclerAdapter);

        mRecyclerAdapter.setBtnClickListener(new CriancasAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {

            }
        });

    }

    private void handleInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null &&
                savedInstanceState.containsKey(SAVED_ADAPTER_ITEMS) &&
                savedInstanceState.containsKey(SAVED_ADAPTER_KEYS)) {
            mAdapterItems = Parcels.unwrap(savedInstanceState.getParcelable(SAVED_ADAPTER_ITEMS));
            mAdapterKeys = savedInstanceState.getStringArrayList(SAVED_ADAPTER_KEYS);
        } else {
            mAdapterItems = new ArrayList<Crianca>();
            mAdapterKeys = new ArrayList<String>();
        }
    }



}
