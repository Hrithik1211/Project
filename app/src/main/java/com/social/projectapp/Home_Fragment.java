package com.social.projectapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
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
import java.util.HashMap;
import java.util.Map;


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
    String username;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String, Object> user = new HashMap<>();
    int count,count1;
    Home_Fragment(String title[],String url[],String description[],String uname,int t ,int q){
        mDescription=description;
        mtitle=title;
        imageurl=url;
        username = uname;
        count=t;
        count1=q;
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
    private static final String TAG = "MainActivity";
    AppCompatActivity appCompatActivity ;

private AdView mAdView;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        appCompatActivity= (AppCompatActivity) view.getContext();
        mAdView = view.findViewById(R.id.adView);
        final AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        super.onViewCreated(view, savedInstanceState);
        final TextView textView = (TextView) view.findViewById(R.id.textdescription);
        textView.setVisibility(View.INVISIBLE);
        final ListView listView = (ListView) view.findViewById(R.id.listView);
        final ScrollView scrollView = (ScrollView) view.findViewById(R.id.homescrollview);
        scrollView.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.VISIBLE);
        final VideoView videoView = (VideoView) view.findViewById(R.id.descriptionVideoView);
        videoView.setVisibility(View.INVISIBLE);
        //  final YouTubePlayerView youTubePlayerView =
        //        (YouTubePlayerView) view.findViewById(R.id.descriptionVideoView);
final ArrayAdapter<String> arrayAdapter ;
        //youTubePlayerView.setVisibility(View.INVISIBLE);


       // arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,mtitle);
        //listView.setAdapter(arrayAdapter);
            adapter = new Adapter(getActivity(),mtitle,imageurl);
        listView.setAdapter(adapter);
        listView.setClickable(true);

InterstitialAd interstitialAd;

        interstitialAd = new InterstitialAd(getActivity());
        interstitialAd.setAdUnitId("ca-app-pub-6029736109689866/6780116568");
        interstitialAd.loadAd(new AdRequest.Builder().build());

       {
            interstitialAd.show();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                user.put("hello",position);


                db.collection(username).document((++count)+"").set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void documentReference) {

                    }
                });
                Map<String, Object> user1 = new HashMap<>();
                user1.put("hello",count);
                db.collection(username).document((0)+"").set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void documentReference) {

                    }
                });
                textView.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.VISIBLE);
                textView.setText(mDescription[position]);
                videoView.setVisibility(View.VISIBLE);

            listView.setVisibility(View.INVISIBLE);


            }
        });

    //    appCompatActivity.onBackPressed();

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
