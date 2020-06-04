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
import android.util.Log;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {








    ArrayAdapter arrayAdapter;
    String uname,pword;

    FirebaseDatabase database ;
    DatabaseReference refuname, refpword,ref;

    Users users;
    TextView textView ;
    ListView listView ;
    EditText username , password;
    Button Register; long backpressedtime;
    Toast toast;

    int SPLASH_TIME_OUT = 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);


        username = (EditText) findViewById(R.id.username);
        password =(EditText) findViewById(R.id.password);
        Register = (Button) findViewById(R.id.Register);
        username.setVisibility(View.INVISIBLE);
        password.setVisibility(View.INVISIBLE);
        Register.setVisibility(View.INVISIBLE);
        database = FirebaseDatabase.getInstance();
        //ref = FirebaseDatabase.getInstance().getReference("users");
        ref = database.getReference().child("users");
        ref.setValue("hello world");
        users = new Users();
    }


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




    public void register(View v){


        uname = username.getText().toString();
        pword = password.getText().toString();




        users.username = uname;
        users.password = pword;

        Log.i("USERNAME",users.username);
        Log.i("PASSWORD",users.password);

        ref.setValue(users);

        //ref.push().setValue(users);



    }



    public void signup(View v){

        BottomNavigationView menu = (BottomNavigationView) findViewById(R.id.bottomnavigationmenu);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        Button login = (Button) findViewById(R.id.logIn);

        Button signin = (Button) findViewById(R.id.signUp);

        login.setVisibility(View.INVISIBLE);

        signin.setVisibility(View.INVISIBLE);

        username.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        Register.setVisibility(View.VISIBLE);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);


    }



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
