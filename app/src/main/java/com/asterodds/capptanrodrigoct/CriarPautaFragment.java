package com.asterodds.capptanrodrigoct;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.asterodds.capptanrodrigoct.model.Pauta;


/**
 * A simple {@link Fragment} subclass.
 */
public class CriarPautaFragment extends Fragment {

    CriarPautaActions listener;

    public CriarPautaFragment() {
        // Required empty public constructor
    }

    public interface CriarPautaActions {
        void criarPauta(Pauta pauta);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CriarPautaActions) {
            listener = (CriarPautaActions) context;
        } else {
            throw new ClassCastException("Exception");
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_criar_pauta, container, false);

        Button buttonCriar = view.findViewById(R.id.button_criar_id);
        final TextInputEditText titulo = view.findViewById(R.id.edit_text_titulo_pauta_id);
        final TextInputEditText descricao = view.findViewById(R.id.edit_text_descricao_pauta_id);
        final TextInputEditText detalhe = view.findViewById(R.id.edit_text_detalhes_pauta_id);

        buttonCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                criar(titulo, descricao, detalhe);
            }
        });


        return view;
    }

    private void criar(TextInputEditText titulo, TextInputEditText descricao, TextInputEditText detalhe) {
        if (!titulo.getText().toString().equals("") && !descricao.getText().toString().equals("") && !detalhe.getText().toString().equals("")) {
            Pauta pauta = new Pauta();
            pauta.setTitulo(titulo.getEditableText().toString());
            pauta.setDescricao(descricao.getEditableText().toString());
            pauta.setDetalhe(detalhe.getEditableText().toString());

            listener.criarPauta(pauta);

        } else {
            Toast.makeText(getContext(), "Favor preencher todos os campos.", Toast.LENGTH_SHORT).show();
        }

    }
}
