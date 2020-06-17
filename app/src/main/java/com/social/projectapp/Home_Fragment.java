package com.social.projectapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;












public class Home_Fragment extends Fragment {
    int n=170;
    ListView listView ;
    String mtitle[] = new String[n];
    String imageurl [] = new String [n];
    String mDescription[]= new String[n];
    String str;
    ArrayList<String > arrayList =new ArrayList<>();
    FragmentActivity listener;
    Adapter adapter;

    Home_Fragment(String title[],String url[],String description[]){
        mDescription=description;
        mtitle=title;
        imageurl=url;
    }
    Home_Fragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);

    }

    @Override
    public void onAttach(@NonNull Context context) {
       super.onAttach(context);
       if(context instanceof Activity){
           this.listener = (FragmentActivity) context;
       }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextView textView = (TextView) view.findViewById(R.id.textdescription);
        textView.setVisibility(View.INVISIBLE);
        final ListView listView = (ListView) view.findViewById(R.id.listView);
        final ScrollView scrollView = (ScrollView) view.findViewById(R.id.homescrollview);
        scrollView.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.VISIBLE);
        final VideoView videoView = (VideoView) view.findViewById(R.id.descriptionVideoView);
        videoView.setVisibility(View.INVISIBLE);
        adapter = new Adapter(getActivity(),mtitle,imageurl);listView.setAdapter(adapter);
        listView.setClickable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = listView.getItemAtPosition(position);

                String string = ""+position;
                 str = (String)o;
                 textView.setVisibility(View.VISIBLE);
                 scrollView.setVisibility(View.VISIBLE);
                 textView.setText(mDescription[position]);
                 videoView.setVisibility(View.VISIBLE);
                 listView.setVisibility(View.INVISIBLE);
            }
        });
    }




    class Adapter extends ArrayAdapter<String>{

        Context context;
        String rTitle[] ;
        String rDescription[];
        String rImgs [] ;



        Adapter ( Context c,String title[] , String imgs []){
            super(c,R.layout.row,title);
            this.context=c;
            this.rTitle=title;
            this.rImgs = imgs;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row,parent,false);
            ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textview1);

            myTitle.setText(rTitle[position]);
            if(rImgs[position] != null && rImgs[position].length()>0)
            {
                Picasso.get().load(rImgs[position]).placeholder(R.drawable.common_full_open_on_phone).into(images);
            }else
            {
                Picasso.get().load(R.drawable.common_full_open_on_phone).into(images);
            }



            return row;
        }
    }


}
