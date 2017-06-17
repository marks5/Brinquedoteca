package br.com.marks.brinquedoteca.a.activity;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import br.com.marks.brinquedoteca.R;
import br.com.marks.brinquedoteca.a.adapter.CriancasAdapter;
import br.com.marks.brinquedoteca.a.adapter.CriancasAdapterFirebase;
import br.com.marks.brinquedoteca.a.fragment.TelaCriancasFragment;
import br.com.marks.brinquedoteca.a.model.Crianca;

public class TelaCriancasActivity extends AppCompatActivity {

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
    private Timer timer;

    private EditText edt_search;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mChildrenRef;

    private CriancasAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_criancas);

        mChildrenRef = database.getReference("criancas");

        handleInstanceState(savedInstanceState);
        edt_search =(EditText) findViewById(R.id.edt_search);
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(edt_search.getText().length()>0){
                                        String busca = edt_search.getText().toString();
                                        Log.i("Produto_buscado",busca);
                                        performarBusca(busca);
                                    }else{
                                        setupFirebaseAdapter();
                                    }
                                }
                            });
                    }
                },700);


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        rv_children = (RecyclerView) findViewById(R.id.rv_children);
        fab_add = (FloatingActionButton) findViewById(R.id.fab_add);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insereItem();
            }
        });

        setupFirebaseAdapter();
    }

    private void performarBusca(final String queryText){
        Log.i("busca_search",queryText);
        rv_children.setHasFixedSize(true);

        glm = new GridLayoutManager(TelaCriancasActivity.this,2);
        glm.setOrientation(GridLayoutManager.VERTICAL);
        rv_children.setLayoutManager(glm);

        Query query = mChildrenRef.orderByChild("nome").startAt(queryText)
                .endAt(queryText+"\uf8ff");

        mRecyclerAdapter = new CriancasAdapter(query);

        rv_children.setAdapter(mRecyclerAdapter);
    }

    private void insereItem(){
        String key = database.getReference()
                .child("criancas").push().getKey();;

        Crianca crianca1 = new Crianca();
        crianca1.nome = "Marques";
        crianca1.idade = 13;
        crianca1.identidade = "14917951757";

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key,crianca1);
        database.getReference("criancas").updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError == null){
                    Toast.makeText(TelaCriancasActivity.this, "Inserido OBJETO", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupFirebaseAdapter(){
        rv_children.setHasFixedSize(true);

        glm = new GridLayoutManager(TelaCriancasActivity.this,2);
        glm.setOrientation(GridLayoutManager.VERTICAL);
        rv_children.setLayoutManager(glm);

        Query lastFifty = mChildrenRef.limitToLast(50);

        mRecyclerAdapter = new CriancasAdapter(lastFifty);

        rv_children.setAdapter(mRecyclerAdapter);

        mRecyclerAdapter.setBtnClickListener(new CriancasAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Toast.makeText(TelaCriancasActivity.this, "Teste"+position, Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
