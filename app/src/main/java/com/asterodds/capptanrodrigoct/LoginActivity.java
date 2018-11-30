package com.asterodds.capptanrodrigoct;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Login";
    private Button btLogin;
    private TextView cadastro;
    private FirebaseAuth mAuth;
    private TextInputEditText email,senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.edit_text_email_id);
        senha = findViewById(R.id.edit_text_senha_id);
        email.setText("");
        senha.setText("");

        mAuth = FirebaseAuth.getInstance();

        btLogin = findViewById(R.id.button_login_id);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginClicado();
            }
        });

        cadastro = findViewById(R.id.text_view_cadastro_id);
        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCadastro();
            }
        });

    }


    public void loginClicado (){

        email = findViewById(R.id.edit_text_email_id);
        senha = findViewById(R.id.edit_text_senha_id);

        if (!email.getText().toString().equals("") && !senha.getText().toString().equals("")){
        mAuth.signInWithEmailAndPassword(email.getText().toString(), senha.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "Login:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            goToHome();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "Login:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    } else {
        Toast.makeText(LoginActivity.this, "Favor preencher os campos para realizar o login", Toast.LENGTH_LONG).show();
    }

    }

    private void goToHome() {
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);

    }

    private void goToCadastro() {
        Intent intent = new Intent(this,CadastroActivity.class);
        startActivity(intent);

    }
}
