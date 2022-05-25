package com.example.bike4u;

import static com.example.bike4u.Utils.Constants.URL_BOS;
import static com.example.bike4u.Utils.Constants.URL_CASA;
import static com.example.bike4u.Utils.Constants.URL_COM;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.bike4u.Interfaces.CRUDInterfaces;
import com.example.bike4u.Model.Announcement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityAnnouncementDetail extends AppCompatActivity {

    Toolbar toolbar;
    Retrofit retrofit;
    CRUDInterfaces crudInterfaces;
    int id;
    TextView title;
    ImageView photo;
    TextView description;
    TextView infoPH;
    TextView infoKM;
    TextView infoLIMITED;
    TextView infoPRICE;
    Announcement announcement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        title = findViewById(R.id.textViewAnnouncementTitle);
        photo = findViewById(R.id.imageView2);
        description = findViewById(R.id.textViewDescription);
        infoKM = findViewById(R.id.infoKM);
        infoPH = findViewById(R.id.infoPH);
        infoPRICE = findViewById(R.id.infoPRICE);
        infoLIMITED = findViewById(R.id.infoLIMITED);

        retrofit = new Retrofit.Builder()
                .baseUrl(URL_BOS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("id");
        }
        rellenarAnnouncement(id);

    }

    private void rellenarAnnouncement(int id) {
        crudInterfaces = retrofit.create(CRUDInterfaces.class);
        Call<Announcement> call = crudInterfaces.getAnnouncement(id);
        call.enqueue(new Callback<Announcement>() {
            @Override
            public void onResponse(Call<Announcement> call, Response<Announcement> response) {
                if(!response.isSuccessful()){
                    Toast toast = Toast.makeText(getApplicationContext(), "Ha ocurrido algun error", Toast.LENGTH_LONG);
                    toast.show();
                    Log.e("Response err: ",response.message());
                    return;
                }
                announcement = response.body();
                announcementData();
            }

            @Override
            public void onFailure(Call<Announcement> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), "Ha ocurrido algun error", Toast.LENGTH_LONG);
                toast.show();
                Log.e("Throw err: ",t.getMessage());
            }
        });
    }

    private void announcementData(){
        if (announcement.getLimited()){
            infoLIMITED.setText("SI");
        }else{
            infoLIMITED.setText("NO");
        }
        title.setText(announcement.getTitle());
        description.setText(announcement.getDescription());
        infoPH.setText(announcement.getBikePh()+"");
        infoKM.setText(announcement.getKm()+"");
        infoPRICE.setText(announcement.getPrice()+"€");

        switch (announcement.getBikeModel()){
            case "R1":photo.setImageResource(R.drawable.r1);
                break;
            case "CBR650R":photo.setImageResource(R.drawable.cbr650r);
                break;
            case "RSV4":photo.setImageResource(R.drawable.rsv4);
                break;
            case "R7":photo.setImageResource(R.drawable.r7);
                break;
            case "RS660":photo.setImageResource(R.drawable.rs660);
                break;
            case "CBR1000R":photo.setImageResource(R.drawable.cbr1000r);
                break;
            case "PANIGALE":photo.setImageResource(R.drawable.panigale);
                break;
            case "MONSTER":photo.setImageResource(R.drawable.monster);
                break;
            case "CBR500R":photo.setImageResource(R.drawable.cbr500r);
                break;
            case "MT07":photo.setImageResource(R.drawable.mt07);
                break;
            case "MT09":photo.setImageResource(R.drawable.mt09);
                break;

        }
    }
}
