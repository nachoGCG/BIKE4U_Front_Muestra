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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.bike4u.Interfaces.CRUDInterfaces;
import com.example.bike4u.Model.User;
import com.example.bike4u.Utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityRegister extends AppCompatActivity {

    private Toolbar toolbar;
    List<User> users;
    CRUDInterfaces crudInterfaces;
    EditText txtUserName;
    EditText txtUserEmail;
    EditText txtUserPassword;
    ImageButton register;
    Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtUserName = findViewById(R.id.editTextTextPersonName);
        txtUserEmail = findViewById(R.id.editTextTextPersonEmailRegister);
        txtUserPassword = findViewById(R.id.editTextTextPersonPasswordRegister);
        register = findViewById(R.id.buttonLogIn);
        retrofit = new Retrofit.Builder()
                .baseUrl(URL_BOS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(txtUserEmail.getText().toString(),txtUserName.getText().toString(),
                        txtUserPassword.getText().toString());
                createUser(user);
            }
        });
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


    public void callMain(User user){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("userName",user.getUserName());
        startActivity(intent);
    }

    public void createUser(User user){
        crudInterfaces = retrofit.create(CRUDInterfaces.class);
        Call<User> call = crudInterfaces.createUser(user);
        call.enqueue(new Callback<User>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    Toast toast = Toast.makeText(getApplicationContext(), "Ya existe un usuario con el nombre '"+user.getUserName()+"'", Toast.LENGTH_LONG);
                    toast.show();
                    Log.e("Response err: ",response.message());
                    return;
                }
                User user = response.body();
                Toast toast = Toast.makeText(getApplicationContext(), "Usuario '"+user.getUserName()+"' creado con éxito", Toast.LENGTH_LONG);
                toast.show();
                callMain(user);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Throw err: ",t.getMessage());
                Toast toast = Toast.makeText(getApplicationContext(), "Ya existe un usuario con el nombre '"+user.getUserName()+"'", Toast.LENGTH_LONG);

            }
        });
    }


}
