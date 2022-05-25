package com.example.bike4u;

import static com.example.bike4u.Utils.Constants.URL_BOS;
import static com.example.bike4u.Utils.Constants.URL_CASA;
import static com.example.bike4u.Utils.Constants.URL_COM;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.bike4u.Interfaces.CRUDInterfaces;
import com.example.bike4u.Model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityLogin extends AppCompatActivity {

    Toolbar toolbar;
    Button btRegister;
    ImageButton btLogin;
    EditText txtNombreUsuario;
    EditText txtPassword;
    Retrofit retrofit;
    CRUDInterfaces crudInterfaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        retrofit = new Retrofit.Builder()
                .baseUrl(URL_BOS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        txtNombreUsuario = findViewById(R.id.editTextTextUserNameLogin);
        txtPassword = findViewById(R.id.editTextTextPassword);


        btRegister = findViewById(R.id.buttonRegister);
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterActivity(view);
            }
        });

        btLogin = (ImageButton)findViewById(R.id.buttonLogIn);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(txtNombreUsuario.getText().toString(), txtPassword.getText().toString());
            }
        });



    }

    public void login(String userName, String password){
        crudInterfaces = retrofit.create(CRUDInterfaces.class);
        Call<User> call = crudInterfaces.getUser(userName);
        call.enqueue(new Callback<User>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    Toast toast = Toast.makeText(getApplicationContext(), "El usuario o la contraseña no son correctos", Toast.LENGTH_LONG);
                    toast.show();
                    txtPassword.setText("");
                    Log.e("Response err: ",response.message());
                    return;
                }
                User user = response.body();
                if (!user.getPassword().equals(password)){
                    Toast toast = Toast.makeText(getApplicationContext(), "El usuario o la contraseña no son correctos", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                Toast toast = Toast.makeText(getApplicationContext(), "Bienvenido "+user.getUserName()+"", Toast.LENGTH_LONG);
                toast.show();
                callPrincipal(user);

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), "El usuario o la contraseña no son correctos", Toast.LENGTH_LONG);
                toast.show();
                Log.e("Throw err: ",t.getMessage());
            }
        });
    }

    public void callPrincipal(User user){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("userName",user.getUserName());
        startActivity(intent);
    }


    private void openRegisterActivity(View view){
        Intent intent = new Intent(this, ActivityRegister.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.logo){
            Toast.makeText(this, "Creado por Nacho", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    public boolean onClickRegister(Menu menu){


        return true;
    }
}
