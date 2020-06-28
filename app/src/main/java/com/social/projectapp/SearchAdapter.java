package com.social.projectapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {



    Context context;
    ArrayList<String> title;
    ArrayList<String > image;
    ArrayList<String > description;
    public SearchAdapter(Context context,ArrayList<String> title, ArrayList<String> image,ArrayList<String > description) {
        this.title = title;
        this.image = image;
        this.context = context;
      this.description = description;
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list,parent,false);
ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
holder.textView.setText(title.get(position));
Glide.with(context).load(image.get(position)).placeholder(R.mipmap.ic_launcher).into(holder.imageView);

holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Log.i("INFO",description.get(position));
        AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();
        Fragment favorites_fragment = new SearchRecipe(description.get(position));
        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,favorites_fragment).commit();

    }
});
    }




    @Override
    public int getItemCount() {
        return title.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.searchimage);
            textView = (TextView) itemView.findViewById(R.id.searchname);
        }
    }
}
