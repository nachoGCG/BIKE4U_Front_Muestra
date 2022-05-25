package com.example.bike4u;

import static com.example.bike4u.Utils.Constants.URL_BOS;
import static com.example.bike4u.Utils.Constants.URL_CASA;
import static com.example.bike4u.Utils.Constants.URL_COM;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.bike4u.Interfaces.CRUDInterfaces;
import com.example.bike4u.Model.Announcement;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    LinearLayout linearLayout;
    String userName;
    Retrofit retrofit;
    List<Announcement> announcements = new ArrayList<>();
    CRUDInterfaces crudInterfaces;
    List<Button> buttons = new ArrayList<>();
    Button bt;
    ImageButton create;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        linearLayout = findViewById(R.id.main_linearLayout);
        setSupportActionBar(toolbar);
        bt = new Button(this);
        create = findViewById(R.id.imageButtonCreateAnnouncement);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAnnouncement();
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl(URL_BOS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userName = extras.getString("userName");
            //The key argument here must match that used in the other activity
        }

        rellenar();



    }

    private void createAnnouncement(){
        Intent intent = new Intent(this,ActivityCreateAnnouncement.class);
        intent.putExtra("userName",userName);
        startActivity(intent);
    }

    private void mostrar() {

        if (announcements.isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(),"Todavía no exixte ningún anuncio",Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        LinearLayout a = new LinearLayout(this);
        int contId = 0;

        for (Announcement aux: announcements){

            bt = new Button(this);
            bt.setText((aux.getTitle()+"\n"+aux.getBikeBrand()+": "
                    +aux.getBikeModel()+"   "+aux.getId()));
            bt.setId(aux.getId());
            linearLayout.addView(bt);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callDetailAnnouncement(bt.getId());
                }
            });
            buttons.add(bt);
        }
        addOnClickListeners(buttons);
    }

    private void addOnClickListeners(List<Button> buttons) {

        for (Button button: buttons){
            button.setOnClickListener(this);
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int id = bt.getId();
//                    callDetailAnnouncement(bt.getId());
//                }
//            });
        }
    }



    private void callDetailAnnouncement(int id){
        Intent intent = new Intent(this,ActivityAnnouncementDetail.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    private void rellenar() {
        crudInterfaces = retrofit.create(CRUDInterfaces.class);
        Call<List<Announcement>> call = crudInterfaces.getAllAnnouncement();
        call.enqueue(new Callback<List<Announcement>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Announcement>> call, Response<List<Announcement>> response) {
                if(!response.isSuccessful()){
                    Toast toast = Toast.makeText(getApplicationContext(), "Ha ocurrido algun error", Toast.LENGTH_LONG);
                    toast.show();
                    Log.e("Response err: ",response.message());
                    return;
                }
                announcements = response.body();
                mostrar();
            }

            @Override
            public void onFailure(Call<List<Announcement>> call, Throwable t) {

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

    @Override
    public void onClick(View view) {
        callDetailAnnouncement(view.getId());
    }
}