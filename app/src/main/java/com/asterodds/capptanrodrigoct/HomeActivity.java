package com.asterodds.capptanrodrigoct;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.asterodds.capptanrodrigoct.adapter.RecyclerViewPautaAdapter;
import com.asterodds.capptanrodrigoct.model.Pauta;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements CriarPautaFragment.CriarPautaActions {

    private RecyclerViewPautaAdapter adapter;
    private FirebaseAuth mAuth;
    private CriarPautaFragment fragment;
    private RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    private Pauta pauta;


    @Override
    protected void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        mAuth = FirebaseAuth.getInstance();

        myRef = database.getReference("users/" + mAuth.getCurrentUser().getUid());

        setupRecyclerView();
        receberFireBase();


        FloatingActionButton fab = findViewById(R.id.fab_id);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                fragment = new CriarPautaFragment();
                transaction.replace(R.id.container_id, fragment);
                transaction.commit();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar_id);
        toolbar.setTitle("Controle de Pautas");
        setSupportActionBar(toolbar);

    }

    private void receberFireBase() {

        database.getReference("users/"+mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                GenericTypeIndicator<Map<String, Pauta>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Pauta>>() {
                };
                if (dataSnapshot.getValue(genericTypeIndicator) != null) {
                    Collection<Pauta> userCollection = dataSnapshot.getValue(genericTypeIndicator).values();

                    List<Pauta> pautaList = new ArrayList<>(userCollection);

                    pauta = dataSnapshot.getValue(Pauta.class);

                    adapter.setPautaList(pautaList);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void setupRecyclerView() {

        recyclerView = findViewById(R.id.recycler_view_id);
        adapter = new RecyclerViewPautaAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void criarPauta(Pauta pauta) {

        enviarFireBase(pauta);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.remove(fragment);
        adapter.notifyDataSetChanged();

        transaction.commit();
    }

    private void enviarFireBase(Pauta pauta) {

        myRef.push().setValue(pauta);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_trash:
                new MaterialDialog.Builder(this)
                        .title("Atenção")
                        .content("Deseja realmente excluir todas as Pautas?")
                        .positiveText("ok")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                adapter.deleteAll();
                            }
                        })
                        .negativeText("Cancel")
                        .show();
                return true;
            case R.id.action_profile:
                Intent intent = new Intent(this,PerfilActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
