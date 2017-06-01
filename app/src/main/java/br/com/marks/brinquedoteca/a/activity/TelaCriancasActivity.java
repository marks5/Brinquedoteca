package br.com.marks.brinquedoteca.a.activity;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.com.marks.brinquedoteca.R;
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

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mChildrenRef;

    private FirebaseRecyclerAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_criancas);

        mChildrenRef = database.getReference("criancas");

        handleInstanceState(savedInstanceState);
        findViewById(R.id.edt_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insereItem();
            }
        });

        rv_children = (RecyclerView) findViewById(R.id.rv_children);
        fab_add = (FloatingActionButton) findViewById(R.id.fab_add);

        setupFirebaseAdapter();
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
                    Toast.makeText(TelaCriancasActivity.this, "Inserido OBJETO", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupFirebaseAdapter(){
        rv_children.setHasFixedSize(true);

        glm = new GridLayoutManager(TelaCriancasActivity.this,5);
        glm.setOrientation(GridLayoutManager.VERTICAL);
        rv_children.setLayoutManager(glm);

        Query lastFifty = mChildrenRef.limitToLast(50);

        mRecyclerAdapter = new FirebaseRecyclerAdapter<Crianca,TelaCriancasActivity.ViewHolder>(Crianca.class,R.layout.item_crianca,TelaCriancasActivity.ViewHolder.class,lastFifty){
            @Override
            protected void populateViewHolder(TelaCriancasActivity.ViewHolder viewHolder, Crianca model, int position) {
                viewHolder.tv_idade_crianca.setText(String.format("%s",model.idade));
                viewHolder.tv_nome_crianca.setText(model.nome);

                //Picasso.with(getContext()).load(model.urlImagem).into(viewHolder.iv_foto);
                //viewHolder.tv_nome_responsavel.setText(model.nomeResponsavel);
            }
        };

        rv_children.setAdapter(mRecyclerAdapter);

        mRecyclerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                glm.smoothScrollToPosition(rv_children,null,mRecyclerAdapter.getItemCount());
            }
        });

        TelaCriancasFragment.ViewHolder.setBtnClickListener(new TelaCriancasFragment.ViewHolder.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Toast.makeText(context, "Position: "+position, Toast.LENGTH_SHORT).show();
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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        static TelaCriancasFragment.ViewHolder.MyClickListener btnClickListener;
        ImageView iv_foto;
        CardView cv_item;
        TextView tv_nome_crianca;
        TextView tv_idade_crianca;
        TextView tv_nome_responsavel;

        public ViewHolder(View view) {
            super(view);
            iv_foto = (ImageView) view.findViewById(R.id.iv_foto);
            tv_nome_crianca = (TextView) view.findViewById(R.id.tv_nome_crianca);
            tv_idade_crianca = (TextView) view.findViewById(R.id.tv_idade_crianca);
            tv_nome_responsavel = (TextView) view.findViewById(R.id.tv_nome_responsavel);
            cv_item =(CardView) view.findViewById(R.id.cv_item);

            cv_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnClickListener.onItemClick(getAdapterPosition(),v);
                }
            });
        }

        public static void setBtnClickListener(TelaCriancasFragment.ViewHolder.MyClickListener btnClickListener) {
            TelaCriancasActivity.ViewHolder.btnClickListener = btnClickListener;
        }

        public interface MyClickListener {
            void onItemClick(int position, View v);
        }

    }
}
