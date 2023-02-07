package com.example.petstore;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PetstoreAPI {
    @GET("pet/{petId}")
    Call<Pet> getPetById(@Path("petId") long petId);

     Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://petstore.swagger.io/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


     PetstoreAPI petstoreAPI = retrofit.create(PetstoreAPI.class);

}
