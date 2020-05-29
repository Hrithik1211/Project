package com.social.projectapp;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.ListFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputBinding;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {









    ListView listView ;
    String mtitle[] = {"Facebook","WhatsApp","Twitter","Instagram","Youtube"};
    String mDescription[]={"Hello","Hello","Hello","Hello","Hello"};
    int images[] = {R.drawable.facebook,R.drawable.whatsapp,R.drawable.twitter,R.drawable.instagram,R.drawable.youtube};
    ArrayList<String > arrayList =new ArrayList<>();

    class Adapter extends ArrayAdapter<String>{

        Context context;
        String rTitle [];
        String rDescription[];
        int rImgs [] ;


        Adapter ( Context c,String  title [] , int imgs []){
            super(c,R.layout.row,title);
            this.context=c;
            this.rTitle=title;
            this.rImgs = imgs;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row,parent,false);
            ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textview1);
            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);
            return row;
        }
    }
    Adapter adapter;


    private static int SPLASH_TIME_OUT = 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    long backpressedtime;
    Toast toast;
    @Override
    public void onBackPressed() {
       // if(backpressedtime + 2000>System.currentTimeMillis()){
       //     toast.cancel();
            super.onBackPressed();
        //    return;
      /*  }
        else
        {
            toast = Toast.makeText(this, "Press Again to exit the app", Toast.LENGTH_LONG);
            toast.show();
        }
        backpressedtime = System.currentTimeMillis();
*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Fragment selectedFragment = null;

        BottomNavigationView hello = (BottomNavigationView) findViewById(R.id.bottomnavigationmenu);

        switch (item.getItemId()) {
            case R.id.history:
                selectedFragment = new History_Fragment();
                hello.setVisibility(View.INVISIBLE);

                break;
            case R.id.about:
                selectedFragment = new Activity_Fragment();
                hello.setVisibility(View.INVISIBLE);
                break;
            case R.id.mainscreen:
                selectedFragment = new Home_Fragment();
                hello.setVisibility(View.INVISIBLE);
                break;
           /* case R.id.logout:
                selectedFragment = new LogOut();
                break;
*/
        }
        if (item.getItemId() == R.id.history || item.getItemId() == R.id.about) {
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, selectedFragment).addToBackStack(null).commit();
        } else if (R.id.mainscreen == item.getItemId()) {

            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, selectedFragment).addToBackStack(null).commit();
            hello.setVisibility(View.VISIBLE);

        } else{
         //   getSupportFragmentManager().beginTransaction().replace(R.id.framelayoutmenu,HomeActivity).commit();
        }


        return super.onOptionsItemSelected(item);
    }
    BottomNavigationView menu;



    ArrayAdapter arrayAdapter;
    public void login(View v){




        BottomNavigationView menu = (BottomNavigationView) findViewById(R.id.bottomnavigationmenu);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        Button login = (Button) findViewById(R.id.logIn);

        Button signin = (Button) findViewById(R.id.signUp);

        imageView.setVisibility(View.INVISIBLE);

        login.setVisibility(View.INVISIBLE);

        signin.setVisibility(View.INVISIBLE);

        menu.setVisibility(View.VISIBLE);

        menu.setOnNavigationItemSelectedListener(navlistener);

        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new Home_Fragment()).addToBackStack(null).commit();


    }

    TextView textView ;
    private BottomNavigationView.OnNavigationItemSelectedListener navlistener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            BottomNavigationView hello = (BottomNavigationView) findViewById(R.id.bottomnavigationmenu);
            hello.setVisibility(View.VISIBLE);
            switch (item.getItemId()){
                case R.id.favorite :
                    selectedFragment = new Favorites_Fragment();
                    break;
                case R.id.home :
                    selectedFragment = new Home_Fragment();

                    break;
                case R.id.activity :
                    selectedFragment = new Activity_Fragment();
                    break;
            }
             getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,selectedFragment).addToBackStack(null).commit();

            return true;
        }
    };


}
