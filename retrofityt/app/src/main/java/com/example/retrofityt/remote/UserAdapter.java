package com.example.retrofityt.remote;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.retrofityt.R;
import com.example.retrofityt.UserActivity;
import com.example.retrofityt.model.User;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
  private Context context;
  private List<User> users;

    public UserAdapter(@NonNull Context context,int resource, @NonNull List<User> objects){
        super(context,resource,objects);
        this.users=objects;
        this.context=context;
    }
    @Override
    public View getView(final int pos, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_user,parent,false);
        TextView txtUserId = (TextView)rowView.findViewById(R.id.txtUserId);
        TextView txtUsername = (TextView)rowView.findViewById(R.id.txtUsername);
        txtUserId.setText(String.format("user_id: %d",users.get(pos).getId()));
        txtUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserActivity.class);
                intent.putExtra("user_id",String.valueOf(users.get(pos).getId()));
                intent.putExtra("user_name",users.get(pos).getName());
                context.startActivity(intent);
            }
        });
        return rowView;
    }
}
