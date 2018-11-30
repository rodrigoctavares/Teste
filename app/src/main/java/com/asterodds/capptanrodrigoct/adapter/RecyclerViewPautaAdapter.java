package com.asterodds.capptanrodrigoct.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.asterodds.capptanrodrigoct.R;
import com.asterodds.capptanrodrigoct.model.Pauta;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewPautaAdapter extends RecyclerView.Adapter<RecyclerViewPautaAdapter.ViewHolder> {

    public List<Pauta> pautaList = new ArrayList<>();
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    public void setPautaList(List<Pauta> pautaList) {
        this.pautaList = pautaList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.content_recycler_view_pauta, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Pauta pauta = pautaList.get(i);
        viewHolder.bind(pauta);

        viewHolder.excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(v.getContext())
                        .title("Atenção")
                        .content("Deseja realmente excluir a Pauta?")
                        .positiveText("ok")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                deletarPauta(pauta);
                            }
                        })
                        .negativeText("Cancel")
                        .show();
            }
            });
        }


    @Override
    public int getItemCount() {
        return pautaList.size();
    }

    public void addPauta(Pauta pauta){
        pautaList.add(pauta);
        notifyItemChanged(getItemCount());
        notifyDataSetChanged();
    }

    public void deleteAll() {
        pautaList.clear();
        notifyDataSetChanged();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users/"+mAuth.getUid());
        myRef.removeValue();
    }

    public void deletarPauta (Pauta pauta) {
        pautaList.remove(pauta);
        notifyDataSetChanged();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users/"+mAuth.getUid());
        myRef.removeValue();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titulo,descricao,detalhe,autor;
        private ImageView excluir;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.text_view_titulo_id);
            descricao = itemView.findViewById(R.id.text_view_descricao_id);
            detalhe = itemView.findViewById(R.id.text_view_detalhes_id);
            autor = itemView.findViewById(R.id.text_view_autor_id);
            excluir = itemView.findViewById(R.id.image_view_delete_id);
            mAuth = FirebaseAuth.getInstance();

        }

        public void bind(final Pauta pauta) {

            titulo.setText(pauta.getTitulo());
            descricao.setText("Descrição: "+"\n"+pauta.getDescricao());
            detalhe.setText("Detalhe: "+"\n"+pauta.getDetalhe());
            FirebaseUser user = mAuth.getCurrentUser();
            autor.setText(user.getDisplayName());


        }
    }
}
