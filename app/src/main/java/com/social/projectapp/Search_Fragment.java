package com.social.projectapp;

import android.content.Context;
import android.media.Image;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Search_Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       return inflater.inflate(R.layout.search,container,false);
    }

    EditText editText ;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    ArrayList<String> title;
    ArrayList<String> image;
SearchAdapter searchAdapter;
SearchView searchView;
ArrayList<String > description;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
     //   editText = (EditText) view.findViewById(R.id.search);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("selection1");
      //  firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView.setHasFixedSize(true);
        title = new ArrayList<String>();
        image = new ArrayList<String>();
        description = new ArrayList<String>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        searchView = (SearchView) view.findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(!s.toString().isEmpty()){
                    setAdapter(s.toString());
                }
                else
                {
                    title.clear();
                    image.clear();
                    recyclerView.removeAllViews();
                }
                return false;
            }
        });
        /*
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().isEmpty()){
                    setAdapter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty()){
                    setAdapter(s.toString());
                }
                else
                {
                    title.clear();
                    image.clear();
                    recyclerView.removeAllViews();
                }
            }
        });
*/

    } private void setAdapter(final String string){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int count=0;
               title.clear();
               image.clear();
               description.clear();
               recyclerView.removeAllViews();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    //String uid = snapshot.getKey();
                    String imagedata = snapshot.child("image").getValue(String.class);
                    String titlename = snapshot.child("name").getValue(String.class);
                   String str = "description";
                    str = "INGREDIENTS:\n\n";
                    str += snapshot.child("ingredients").getValue(String.class);
                    str += "\n\nPROCEDURE:\n\n";
                    str += snapshot.child("instructions").getValue(String.class);

                   if(titlename.toLowerCase().contains(string.toLowerCase())){
                       count++;
                       title.add(titlename);
                       image.add(imagedata);
                       description.add(str);

                   }
                }

                if(count>0){
                    searchAdapter = new SearchAdapter(getActivity(), title,image,description);
                    recyclerView.setAdapter(searchAdapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}