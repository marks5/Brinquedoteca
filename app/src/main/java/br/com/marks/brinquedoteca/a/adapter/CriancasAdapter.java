package br.com.marks.brinquedoteca.a.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.annotations.Expose;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.marks.brinquedoteca.R;
import br.com.marks.brinquedoteca.a.model.Crianca;

public class CriancasAdapter extends RecyclerView.Adapter<CriancasAdapter.ViewHolder> {

    List<Crianca> listCriancas = new ArrayList<>();
    private Context context;

    private MyClickListener myClickListener;

    public CriancasAdapter(DatabaseReference firebaseDatabase) {
        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listCriancas.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Crianca crianca = postSnapshot.getValue(Crianca.class);
                    listCriancas.add(crianca);
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("error",databaseError.getDetails());
            }
        });
    }

    public CriancasAdapter(Query firebaseDatabase) {
        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    listCriancas.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Crianca crianca = postSnapshot.getValue(Crianca.class);
                    listCriancas.add(crianca);
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("error",databaseError.getDetails());
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_crianca, parent, false);
        context = parent.getContext();
        return new CriancasAdapter.ViewHolder(itemView);
    }

    private Crianca getObject(int pos){
        return listCriancas.get(pos);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Crianca crianca = getObject(position);
        Log.e("crianca",crianca.toString());
        try {
        holder.tv_idade_crianca.setText(String.format("%s",crianca.getIdade()));
        holder.tv_nome_crianca.setText(String.format("%s",crianca.getNome()));
        holder.tv_nome_responsavel.setText(String.format("%s",crianca.getNomeResponsavel()));
        Picasso.with(context).load(crianca.urlImagem).into(holder.iv_foto);
        }catch (Exception e){
            Log.i("exception",e.getMessage()+" "+e.getCause());
        }
    }

    public void setBtnClickListener(MyClickListener btnClickListener) {
        ViewHolder.btnClickListener = btnClickListener;
    }

    @Override
    public int getItemCount() {
        return listCriancas != null ? listCriancas.size() : 0 ;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        static MyClickListener btnClickListener;
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
    }

    public interface MyClickListener {
        void onItemClick(int position, View v);
    }
}
