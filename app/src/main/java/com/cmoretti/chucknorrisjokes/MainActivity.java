package com.cmoretti.chucknorrisjokes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.jokeTextView) TextView jokeTextView;
    @BindView(R.id.newJokeButton) Button newJokeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getNewJoke();

        newJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNewJoke();
            }
        });
    }

    public void getNewJoke() {
        String chuckNorrisUrl = "https://api.chucknorris.io/jokes/random";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(chuckNorrisUrl)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String jsonData = response.body().string();
                    if (response.isSuccessful()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                jokeTextView.setText(jsonData);
                            }
                        });
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Exception caught: ", e);
                }
            }
        });
    }

}
