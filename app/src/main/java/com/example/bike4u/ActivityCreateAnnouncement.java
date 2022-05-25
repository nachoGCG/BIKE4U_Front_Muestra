package com.example.bike4u;

import static com.example.bike4u.Utils.Constants.URL_BOS;
import static com.example.bike4u.Utils.Constants.URL_CASA;
import static com.example.bike4u.Utils.Constants.URL_COM;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.bike4u.Interfaces.CRUDInterfaces;
import com.example.bike4u.Model.Announcement;
import com.example.bike4u.Model.MotorBike;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityCreateAnnouncement extends AppCompatActivity {

    Toolbar toolbar;
    Retrofit retrofit;
    CRUDInterfaces crudInterfaces;
    String userName;
    TextView title;
    TextView description;
    TextView infoKM;
    CheckBox infoLIMITED;
    TextView infoPRICE;
    Announcement announcement;
    Spinner spinnerModel;
    ImageButton createAnnouncement;
    MotorBike motorBike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_announcement);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        spinnerModel = (Spinner) findViewById(R.id.spinnerModel);
        title = findViewById(R.id.editTextTitleCreate);
        description = findViewById(R.id.editTextDescriptionCreate);
        infoKM = findViewById(R.id.editTextKmCreate);
        infoPRICE = findViewById(R.id.editTextPrecioCreate);
        infoLIMITED = findViewById(R.id.checkBoxLimited);
        createAnnouncement = findViewById(R.id.createAnnouncement);
        ArrayAdapter<CharSequence> models = ArrayAdapter.createFromResource(this, R.array.spinnerModels, android.R.layout.simple_spinner_item);
        spinnerModel.setAdapter(models);


        retrofit = new Retrofit.Builder()
                .baseUrl(URL_BOS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudInterfaces = retrofit.create(CRUDInterfaces.class);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userName = extras.getString("userName");
        }

        createAnnouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMotorBike(spinnerModel.getSelectedItem().toString());
                announcement = new Announcement(title.getText().toString(), motorBike.getBrand(), motorBike.getModel()
                        , motorBike.getPh(), Integer.parseInt(infoKM.getText().toString()), Boolean.parseBoolean(infoLIMITED.getText().toString())
                        , description.getText().toString(), userName, Integer.parseInt(infoPRICE.getText().toString()));
                createAnnouncement();
            }
        });


    }

    private void getMotorBike(String bikeModel) {
        Call<MotorBike> call = crudInterfaces.getMotorBikeByModel(bikeModel);
        call.enqueue(new Callback<MotorBike>() {
            @Override
            public void onResponse(Call<MotorBike> call, Response<MotorBike> response) {
                if(!response.isSuccessful()){
                    Toast toast = Toast.makeText(getApplicationContext(), "Ha ocurrido algun error", Toast.LENGTH_LONG);
                    toast.show();
                    Log.e("Response err: ",response.message());
                    return;
                }
                motorBike = response.body();
            }

            @Override
            public void onFailure(Call<MotorBike> call, Throwable t) {
                Log.e("Throw err: ",t.getMessage());
                Toast toast = Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG);
            }
        });
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onResponse(Call<MotorBike> call, Response<MotorBike> response) {
//                if(!response.isSuccessful()){
//                    Toast toast = Toast.makeText(getApplicationContext(), "Ha ocurrido algun error", Toast.LENGTH_LONG);
//                    toast.show();
//                    Log.e("Response err: ",response.message());
//                    return;
//                }
//                motorBike = response.body();
//            }
//
//            @Override
//            public void onFailure(Call<MotorBike> call, Throwable t) {
//                Log.e("Throw err: ",t.getMessage());
//                Toast toast = Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG);
//
//            }
//        });


    }

    private void createAnnouncement() {
        Call<Announcement> call = crudInterfaces.createAnnouncement(announcement);
        call.enqueue(new Callback<Announcement>() {
            @Override
            public void onResponse(Call<Announcement> call, Response<Announcement> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Ha ocurrido algún error al insertar el anuncio", Toast.LENGTH_LONG);
                    toast.show();
                    Log.e("Response err: ", response.message());
                    return;
                }
                announcement = response.body();
                callActivityDetail();
            }

            @Override
            public void onFailure(Call<Announcement> call, Throwable t) {

            }
        });
    }

    private void callActivityDetail() {
        Intent intent = new Intent(this, ActivityAnnouncementDetail.class);
        intent.putExtra("id", announcement.getId());
        startActivity(intent);
    }
}
