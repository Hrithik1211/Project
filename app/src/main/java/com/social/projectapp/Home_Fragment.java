package com.social.projectapp;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
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

    FragmentActivity listener;
    //ArrayAdapter adapter;
    Adapter adapter;

    VideoView videoView ;
    TextView  textView;
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

ArrayAdapter arrayAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("hello");arrayList.add("facebook");
        arrayList.add("how");
        arrayList.add("are");arrayList.add("you");
        adapter = new Adapter(getActivity(),mtitle,images);
        // arrayAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,mtitle);
        super.onCreate(savedInstanceState);
    }
    String str;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final FirebaseDatabase database;
        final DatabaseReference databaseReference;
        database = FirebaseDatabase.getInstance();
        databaseReference= database.getReference("my-project-1568486854550");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String s1 = dataSnapshot.getValue(String.class);
                mtitle.add(s1);
                Log.i("INFO",s1);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        final VideoView videoView = (VideoView) view.findViewById(R.id.descriptionVideoView);
        videoView.setVisibility(View.INVISIBLE);
        mtitle.add("Facebook");
        mtitle.add("WhatsApp");
        mtitle.add("Twitter");
        mtitle.add("Instagram");
        mtitle.add("Youtube");

        final TextView textView = (TextView) view.findViewById(R.id.textdescription);
        textView.setVisibility(View.INVISIBLE);
        final ListView listView = (ListView) view.findViewById(R.id.listView);
        final ScrollView scrollView = (ScrollView) view.findViewById(R.id.homescrollview);
        scrollView.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.VISIBLE);
        listView.setAdapter(adapter);
        listView.setClickable(true);
       // Toast.makeText(getActivity(),"hello",Toast.LENGTH_LONG).show();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = listView.getItemAtPosition(position);
               // VideoView videoView = (VideoView) view.findViewById(R.id.descriptionVideoView);
                String string = ""+position;
                 str = (String)o;
                 textView.setVisibility(View.VISIBLE);
                 scrollView.setVisibility(View.VISIBLE);
                 textView.setText(str);
                 videoView.setVisibility(View.VISIBLE);
                 listView.setVisibility(View.INVISIBLE);
            }
        });
    }

    class DownloadTask extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... urls) {

            String downloadstr="";
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data!=-1){

                    char current = (char)data;
                    downloadstr += current;
                    data = reader.read();
                }
                return downloadstr;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String downloadstr) {


            super.onPostExecute(downloadstr);
        }
    }
    void getdata(){


    }
    ListView listView ;
   ArrayList<String> mtitle = new ArrayList<>();

    String mDescription[]={"Hello","Hello","Hello","Hello","Hello"};
    int images[] = {R.drawable.facebook,R.drawable.whatsapp,R.drawable.twitter,R.drawable.instagram,R.drawable.youtube};
    ArrayList<String > arrayList =new ArrayList<>();

    class Adapter extends ArrayAdapter<String>{

        Context context;
        ArrayList<String> rTitle ;
        String rDescription[];
        int rImgs [] ;


        Adapter ( Context c,ArrayList<String> title , int imgs []){
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
            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle.get(position));
            return row;
        }
    }


}
