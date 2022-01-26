package com.example.retrofityt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retrofityt.model.User;
import com.example.retrofityt.remote.ApiUtils;
import com.example.retrofityt.remote.UserAdapter;
import com.example.retrofityt.remote.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

Button btnAddUser;
Button btnGetUserList;
ListView listView;
UserService userService;
EditText edtUid;
EditText edtUName;
TextView tUserId;
Button btnBack;
Button btnSave;
Button btnDelete;
List<User> list = new ArrayList<User>();

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    edtUid = (EditText) findViewById(R.id.edtUId);
    edtUName = (EditText) findViewById(R.id.edtUName);
    tUserId = (TextView) findViewById(R.id.tUserId);
    btnAddUser = (Button) findViewById(R.id.btnAddUser);
    btnGetUserList = (Button) findViewById(R.id.btnGetUserList);
    listView = (ListView) findViewById(R.id.listView);
    userService = ApiUtils.getUserService();
    btnBack = (Button) findViewById(R.id.btnBack);
    btnDelete = (Button) findViewById(R.id.btnDelete);
    btnSave = (Button) findViewById(R.id.btnSave);

    btnGetUserList.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getUsersList();

        }
    });

    btnAddUser.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String url="";
            Intent intent = new Intent(MainActivity.this, UserActivity.class);
            intent.putExtra("user_name", url);
            startActivity(intent);
        }
    });
}
    public void getUsersList(){
        Call<User> call = userService.getUsers();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body()!=null){
                    list=response.body().getData();
                }
                listView.setAdapter(new UserAdapter(MainActivity.this, R.layout.list_user,list));
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            Log.e("Error:",t.getMessage());
            }
        });
    }
}
