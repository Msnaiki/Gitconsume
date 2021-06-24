package com.snaiki.consumegit;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.snaiki.consumegit.model.UserGit;
import com.snaiki.consumegit.model.UserGitResponse;
import com.snaiki.consumegit.model.UsersListViewModel;
import com.snaiki.consumegit.service.GitRepServiceAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //strict mode config for images request
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //declaration of layout elements
        EditText editTextQuery = findViewById(R.id.editTextQuery);
        Button buttonSearch = findViewById(R.id.buttonSearch);
        ListView listViewUsers = findViewById(R.id.listViewUsers);
        //temporary list for names/logins
        List<UserGit> data = new ArrayList<>();
        //declaring retrofit
        Retrofit retrofit = new Retrofit.Builder()
                //declaring base url
                .baseUrl("https://api.github.com/")
                //declaring deserialization converter
                .addConverterFactory(GsonConverterFactory.create())
                //building
                .build();
        //declaring an array adapter to be middle man between data and listview and choosing which type of list view
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,data);
        //personalized adapter
        UsersListViewModel listViewModel = new UsersListViewModel(this,R.layout.users_list_view_layout,data);

        //making connection between them
        listViewUsers.setAdapter(listViewModel);
        //on click do
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the keywords
                String query = editTextQuery.getText().toString();
                //create an object of the type service
                GitRepServiceAPI gitRepServiceAPI = retrofit.create(GitRepServiceAPI.class);
                //preparing to call for the search function using the service object and storing result in a list of objects of type response
                Call<UserGitResponse> userGitResponseCall = gitRepServiceAPI.searchUsers(query);
                //asynchronically execute the prevous step
                userGitResponseCall.enqueue(new Callback<UserGitResponse>() {
                    @Override
                    //if we get a response :
                    public void onResponse(Call<UserGitResponse> call, Response<UserGitResponse> response) {

                        //if the response is null show what went wrong
                        if (!response.isSuccessful()) {
                            Log.i("info", String.valueOf(response.code()));
                            return;
                        }
                        //if the response isn't null create a userGitResponse object and store the object in it
                        UserGitResponse userGitResponse = response.body();
                        //populate data list with users
                        for (UserGit user : userGitResponse.users) {

                            data.add(user);

                        }
                        //notify layout thatdata changed
                        listViewModel.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<UserGitResponse> call, Throwable t) {
                        t.printStackTrace();

                    }
                });
            }
        });
    listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String login = data.get(position).login;
            Log.i("login",login);
        }
    });
    }

}