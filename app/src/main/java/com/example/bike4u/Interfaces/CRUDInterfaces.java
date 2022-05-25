package com.example.bike4u.Interfaces;

import com.example.bike4u.Model.Announcement;
import com.example.bike4u.Model.MotorBike;
import com.example.bike4u.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CRUDInterfaces {

    @GET("user/getAll")
    Call<List<User>> getAllUser();

    @GET("user/getUser/{userName}")
    Call<User> getUser(@Path("userName") String userName);

    @POST("user/create")
    Call<User> createUser(@Body User user);

    @POST("announcement/create")
    Call<Announcement> createAnnouncement(@Body Announcement announcement);

    @GET("announcement/getAllAnnouncement")
    Call<List<Announcement>> getAllAnnouncement();

    @GET("announcement/getAnnouncementById/{id}")
    Call<Announcement> getAnnouncement(@Path("id")int id);

    @GET("motorbike/getMotorBikeByBrand/{model}")
    Call<MotorBike> getMotorBikeByBrand(@Path("brand")String brand);

    @GET("motorbike/getMotorBikeByModel/{model}")
    Call<MotorBike> getMotorBikeByModel(@Path("model")String model);

}
