package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class MainActivity extends AppCompatActivity {

    // creating variables for edittext,
    // button, textview and progressbar.
    private EditText tempEdt;
    private Button postDataBtn;
    private TextView responseTV;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing our views
        tempEdt = findViewById(R.id.idEdtTemp);
        postDataBtn = findViewById(R.id.idBtnPost);
        responseTV = findViewById(R.id.idTVResponse);
        loadingPB = findViewById(R.id.idLoadingPB);

        // adding on click listener to our button.
        postDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if the text field is empty or not.
                if (tempEdt.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter the value", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to post the data and passing our name
                postData(tempEdt.getText().toString());
            }
        });
    }

    private void postData(String temperature) {

        // display progress bar.
        loadingPB.setVisibility(View.VISIBLE);

        // create retrofit builder and passing base url
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://temperature-converter-retrofit.herokuapp.com/")
                // sending data in json format
                // add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // build retrofit builder.
                .build();
        // create an instance for retrofit api class.
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        // passing data from text fields to modal class.
        DataModel modal = new DataModel(temperature);

        // calling a method to create a post and passing modal class.
        Call<DataModel> call = retrofitAPI.createPost(modal);

        // executing our method.
        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                // this method is called when we get response from the api.

                // hiding progress bar.
                loadingPB.setVisibility(View.GONE);

                // setting empty text to edit text.
                tempEdt.setText("");

                // getting response from body
                // and passing it to the modal class.
                DataModel responseFromAPI = response.body();

                // getting our data from modal class and adding it to string.
                String responseString = modal.getTemperature() + " C in Fahrenheit : " + responseFromAPI.getTemperature() + " F";

                // setting string to text view.
                responseTV.setText(responseString);
            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {
                // setting text to text view when get error response from API.
                responseTV.setText("Error found is : " + t.getMessage());
            }
        });
    }
}