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

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewPautaAdapter extends RecyclerView.Adapter<RecyclerViewPautaAdapter.ViewHolder> {

    private List<Pauta> pautaList = new ArrayList<>();

    public void setPautaList(List<Pauta> pautaList) {
        this.pautaList = pautaList;
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
    }

    public void deleteAll() {
        pautaList.clear();
        notifyDataSetChanged();
    }

    public void deletarPauta (Pauta pauta) {
        pautaList.remove(pauta);
        notifyDataSetChanged();
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
        }

        public void bind(final Pauta pauta) {

            titulo.setText(pauta.getTitulo());
            descricao.setText(pauta.getDescricao());
            detalhe.setText(pauta.getDetalhe());
            autor.setText(pauta.getAutor());


        }
    }
}
