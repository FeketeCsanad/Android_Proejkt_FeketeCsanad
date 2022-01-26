package com.example.retrofityt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retrofityt.model.User;
import com.example.retrofityt.remote.ApiUtils;
import com.example.retrofityt.remote.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    UserService userService;
    Button btnBack;
    Button btnSave;
    Button btnDelete;

    EditText edtUid;
    EditText edtUname;
    TextView tUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        edtUid = (EditText) findViewById(R.id.edtUId);
        edtUname = (EditText) findViewById(R.id.edtUName);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        tUserId = (TextView) findViewById(R.id.txtUserId);
        userService = ApiUtils.getUserService();
        Bundle extras = getIntent().getExtras();

        final String userId = extras.getString("user_id");
        String userName = extras.getString("user_name");
        edtUid.setText(userId);
        edtUname.setText(userName);
        if (userId != null && userId.trim().length() > 0) {
            edtUid.setFocusable(false);
        } else {
            tUserId.setVisibility(View.INVISIBLE);
            edtUid.setVisibility(View.INVISIBLE);
            btnDelete.setVisibility(View.INVISIBLE);
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User u = new User();
                u.setName(edtUname.getText().toString());

                if (userId != null && userId.trim().length() > 0) {

                    updateUser(Integer.parseInt(userId), u);
                } else {
                    addUser(u);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser(Integer.parseInt(userId));
                Intent intent = new Intent(UserActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void addUser(User u) {
        Call<User> call = userService.addUser(u);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UserActivity.this, "User created", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });
    }

    public void updateUser(int id, User u) {
        Call<User> call = userService.updateUser(id, u);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UserActivity.this, "User updated", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });
    }

    public void deleteUser(int id) {
        Call<User> call= userService.deleteUser(id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(UserActivity.this, "User deleted", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                {
                    Log.e("Error: ", t.getMessage());
                }
            }
        });
    }
}