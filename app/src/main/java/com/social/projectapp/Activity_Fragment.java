package com.social.projectapp;

import android.content.Context;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Activity_Fragment extends Fragment {











    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String username;
    String mtitle[] = new String[170];
    String mdescription[] = new String[170];
    String imageurl [] = new String[170];
    ArrayList<Integer> list = new ArrayList<>();
    int count=0;
    Activity_Fragment(){

    }
    Activity_Fragment(String uname,String title[],String description[],String image[])
    {
        username= uname;
        mtitle= title;
        mdescription= description;
        imageurl= image;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        final AdView[] mAdView = new AdView[1];

        super.onViewCreated(view, savedInstanceState);
        final TextView textView = (TextView) view.findViewById(R.id.textdescription);
        textView.setVisibility(View.INVISIBLE);
        final ListView listView = (ListView) view.findViewById(R.id.listView);
        final ScrollView scrollView = (ScrollView) view.findViewById(R.id.homescrollview);
        scrollView.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.VISIBLE);
        final VideoView videoView = (VideoView) view.findViewById(R.id.descriptionVideoView);
        videoView.setVisibility(View.INVISIBLE);

        db.collection(username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                int len = document.getData().toString().length();
                                list.add(Integer.parseInt(document.getData().toString().substring(7,document.getData().toString().indexOf('}'))));
                                count = count+1;
                            }
                        } else {
                            Log.w("INFO", "Error getting documents.", task.getException());
                        }


                    }
                });
        mAdView[0] = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView[0].loadAd(adRequest);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

               view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
               if(count>0){

                   String title []= new String[count-1];
                   final String description[] = new String[count-1];
                   String  image[] = new String[count-1];

                   for(int j=1;j<count;j++){
                       title[count-1-j]=mtitle[list.get(j)];
                       description[count-1-j] = mdescription[list.get(j)];
                       image[count-1-j] = imageurl[list.get(j)];
                   }
                   Adapter adapter = new Adapter(getActivity(),title,image);listView.setAdapter(adapter);
                   listView.setClickable(true);

                   listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                       @Override
                       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                           Object o = listView.getItemAtPosition(position);
                           textView.setVisibility(View.VISIBLE);
                           scrollView.setVisibility(View.VISIBLE);
                           textView.setText(description[position]);
                           videoView.setVisibility(View.VISIBLE);
                           listView.setVisibility(View.INVISIBLE);

                       }
                   });

               }

            }
        },2500);





    }


    class Adapter extends ArrayAdapter<String> {

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
