package com.matheus.popcodeentrevista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import com.matheus.popcodeentrevista.adapters.CharacterAdapter;
import com.matheus.popcodeentrevista.models.Page;
import com.matheus.popcodeentrevista.models.Result;
import com.matheus.popcodeentrevista.ws.SetupREST;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Boolean isScrolling = false;
    private int currentItems, totalItems, scrollOutItems;
    private int id = 1 ;
    private  CharacterAdapter characterAdapter;
    private ProgressBar progressBar;
    private static int firstVisibleInListview;
    private List<Result> resultList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);

        RecyclerView recyclerViewCharacters = findViewById(R.id.recyclerView_characters);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerViewCharacters.setLayoutManager(manager);
        recyclerViewCharacters.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        firstVisibleInListview = manager.findFirstVisibleItemPosition();

         characterAdapter = new CharacterAdapter();
            recyclerViewCharacters.setAdapter(characterAdapter);

        try {
            SetupREST.apiREST.pageCharacters(id ).enqueue(new Callback<Page>() {
                @Override
                public void onResponse(Call<Page> call, Response<Page> response) {
                    if(response.isSuccessful()) {
                        List<Result> results = response.body().getResults();
                        characterAdapter.update(results);
                    }

                }

                @Override
                public void onFailure(Call<Page> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        recyclerViewCharacters.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                 currentItems = manager.getChildCount();
                 totalItems = manager.getItemCount();
                 scrollOutItems = manager.findFirstVisibleItemPosition();

                 if(isScrolling && ( currentItems + scrollOutItems == totalItems)) {
                     isScrolling = false;
                     fetchData(id = id +1);
                 }
            }
        });






    }

    private void fetchData(int id) {
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    try {
                        SetupREST.apiREST.pageCharacters(id).enqueue(new Callback<Page>() {
                            @Override
                            public void onResponse(Call<Page> call, Response<Page> response) {
                                if(response.isSuccessful()) {

                                    for (Result result : response.body().getResults()) {
                                        resultList.add(result);
                                    }
                                    characterAdapter.update(resultList);
                                    progressBar.setVisibility(View.GONE);
                                }

                            }

                            @Override
                            public void onFailure(Call<Page> call, Throwable t) {

                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

            }
        }, 4000);
    }
}
