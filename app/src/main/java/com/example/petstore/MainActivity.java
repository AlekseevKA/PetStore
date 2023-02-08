package com.example.petstore;

import static com.example.petstore.PetstoreAPI.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView petNameTextView;
    private TextView petStatusTextView, petCategoryTextView;
    private ImageView petImageView;
    private EditText petIdEditText;
    private Button findPetButton, buttonAdd;

    private PetstoreAPI petstoreAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        petNameTextView = findViewById(R.id.pet_name_text_view);
        petStatusTextView = findViewById(R.id.pet_status_text_view);
        petImageView = findViewById(R.id.pet_image_view);
        petIdEditText = findViewById(R.id.pet_id_edit_text);
        findPetButton = findViewById(R.id.find_pet_button);
        petCategoryTextView = findViewById(R.id.pet_category_text_view);
        buttonAdd = findViewById(R.id.buttonAdd);

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://petstore.swagger.io/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        petstoreAPI = retrofit.create(PetstoreAPI.class);



        findPetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String petId = petIdEditText.getText().toString();
                findPetById(petId);
            }
        });
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NewPet.class));
            }
        });

    }

    private void findPetById(String petId) {
        petstoreAPI.getPetById(Long.parseLong(petId)).enqueue(new Callback<Pet>() {
            @Override
            public void onResponse(Call<Pet> call, Response<Pet> response) {
                if (response.isSuccessful()) {
                    Pet pet = response.body();
                    if (pet != null) {
                        String name = pet.getName();
                        String status = pet.getStatus();
                      //  JSONObject category = pet.getCategory();
  //                      Pet.Category category = pet.getCategory();
                        List<String> photo = pet.getPhotoUrls();

                        petNameTextView.setText(name);
                        petStatusTextView.setText(status);
//                        petCategoryTextView.setText((CharSequence) category);



                        String photoUrl = pet.getPhotoUrls().get(0);
                        Picasso.get().load(photoUrl).into(petImageView);

                    } else {
                        // Handle error: pet not found
                        Toast.makeText(MainActivity.this, "error: pet not found", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    // Handle error: response not successful
                    Toast.makeText(MainActivity.this, "error response ", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Pet> call, Throwable t) {
                // Handle error: network error
                Toast.makeText(MainActivity.this, "error network " + t, Toast.LENGTH_SHORT).show();

            }
        });
    }

}
