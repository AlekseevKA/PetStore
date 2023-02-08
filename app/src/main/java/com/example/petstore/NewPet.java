package com.example.petstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewPet extends AppCompatActivity {

    private EditText petNameEditText, petStatusEditText, petIdEditText;
    Button addPetButton, buttonFind;
    private PetstoreAPI petstoreAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pet);

        petNameEditText = findViewById(R.id.petNameEditText);
        petStatusEditText = findViewById(R.id.petStatusEditText);
        addPetButton = findViewById(R.id.addPetButton);
        petIdEditText = findViewById(R.id.petIdEditText);
        buttonFind = findViewById(R.id.buttonFind);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://petstore.swagger.io/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        petstoreAPI = retrofit.create(PetstoreAPI.class);


        addPetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String petName = petNameEditText.getText().toString();
                String petStatus = petStatusEditText.getText().toString();
                String petId = petIdEditText.getText().toString();
                addPet(petName, petStatus, petId);

            }
        });
        buttonFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewPet.this, MainActivity.class));
            }
        });
    }
    private void addPet(String petName, String petStatus, String petId) {
        Pet pet = new Pet();
        pet.setName(petName);
        pet.setStatus(petStatus);
        pet.setId(Long.parseLong(petId));

        petstoreAPI.addPet(pet).enqueue(new Callback<Pet>() {
            @Override
            public void onResponse(Call<Pet> call, Response<Pet> response) {
                if (response.isSuccessful()) {
                    Pet pet = response.body();
                    if (pet != null) {
                        String petId = String.valueOf(pet.getId());
                        Toast.makeText(NewPet.this, "New pet added! ", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(NewPet.this, "Error: pet not added( ", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(NewPet.this, "Response not successful ", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Pet> call, Throwable t) {
                Toast.makeText(NewPet.this, "Network error ", Toast.LENGTH_SHORT).show();

            }
        });
    }
}


