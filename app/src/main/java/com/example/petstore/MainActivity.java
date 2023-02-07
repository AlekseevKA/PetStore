package com.example.petstore;

import static com.example.petstore.PetstoreAPI.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    Button button;
    EditText editText;
    TextView textView, textView3;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String petId = editText.getText().toString();

                PetstoreAPI petstoreAPI = retrofit.create(PetstoreAPI.class);
                Call<Pet> petByIdCall = petstoreAPI.getPetById(Long.parseLong(petId));
                petByIdCall.enqueue(new Callback<Pet>() {
                    @Override
                    public void onResponse(Call<Pet> call, Response<Pet> response) {
                        if(response.isSuccessful()) {
                            Pet pet = response.body();
                            if (pet != null) {

                                String name = pet.getName();
                                String photoUrls = pet.getPhotoUrls();
                                String status = pet.getStatus();


                                textView.setText(name);
                                textView3.setText(status);
                                Glide.with(getApplicationContext()).load(photoUrls).into(imageView);
                            }
                            else{
                                Toast.makeText(MainActivity.this, "error: pet not found ", Toast.LENGTH_SHORT).show();

                            }
                        }



                    }

                    @Override
                    public void onFailure(Call<Pet> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "error " + t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });




    }





}
/*
    public class MainActivity extends AppCompatActivity {
    private TextView petNameTextView;
    private TextView petStatusTextView;
    private ImageView petImageView;
    private EditText petIdEditText;
    private Button findPetButton;

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
    }

    private void findPetById(String petId) {
        petstoreAPI.getPetById(petId).enqueue(new Callback<Pet>() {
            @Override
            public void onResponse(Call<Pet> call, Response<Pet> response) {
                if (response.isSuccessful()) {
                    Pet pet = response.body();
                    if (pet != null) {
                        String name = pet.getName();
                        String status = pet.getStatus();
                        String photo = pet.getPhotoUrls().get(0);

                        petNameTextView.setText(name);
                        petStatusTextView.setText(status);

                        // Load the image from the URL
                        Picasso.get().load(photo).into(petImageView);
                    } else {
                        // Handle error: pet not found
                    }
                } else {
                    // Handle error: response not successful
                }
            }

            @Override
            public void onFailure(Call<Pet> call, Throwable t) {
                // Handle error: network error
            }
        });
    }
}
 */