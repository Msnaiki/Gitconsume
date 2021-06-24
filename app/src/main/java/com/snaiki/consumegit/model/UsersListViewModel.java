package com.snaiki.consumegit.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.snaiki.consumegit.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersListViewModel extends ArrayAdapter<UserGit> {
;
    private int res;
    public UsersListViewModel(@NonNull Context context, int resource, List <UserGit> data) {
        super(context, resource,data);

        this.res =resource;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewitem = convertView;
        if(listViewitem==null){
            listViewitem = LayoutInflater.from(getContext()).inflate(res,parent,false);
        }
        CircleImageView imageView = listViewitem.findViewById(R.id.imageViewUser);
        TextView textViewLogin =  listViewitem.findViewById(R.id.textViewLogin);
        TextView textViewScore =  listViewitem.findViewById(R.id.textView2Score);
        textViewLogin.setText(getItem(position).login);
        textViewScore.setText(String.valueOf(getItem(position).score));
        try {
            URL url = new URL(getItem(position).avatarUrl);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listViewitem;
    }
}
