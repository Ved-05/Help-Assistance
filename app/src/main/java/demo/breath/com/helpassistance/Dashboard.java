package demo.breath.com.helpassistance;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends Fragment {

    //button
    private Button sosbtn, accdnt, medical, theft, terrorism;
    //textView
    private TextView name;
    //firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public Dashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        //
        name = (TextView) view.findViewById(R.id.name2);
        //
        //get userId
        final String userId = mAuth.getCurrentUser().getUid();

        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("users");                           ;

        db.child("allUsers").child(userId).child("username").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String username = dataSnapshot.getValue(String.class);
                //do what you want with the email
                name.setText("Greetings "+username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity().getApplicationContext(), "Navigation failed.", Toast.LENGTH_SHORT).show();
            }
        });

        //buttons
        accdnt = (Button) view.findViewById(R.id.btn_accdnt);
        accdnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), Assistance.class);
                i.putExtra("choosed", 1);
                startActivity(i);
            }
        });
        medical = (Button) view.findViewById(R.id.btn_medical);
        medical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), Assistance.class);
                i.putExtra("choosed", 3);
                startActivity(i);
            }
        });
        theft = (Button) view.findViewById(R.id.btn_theft);
        theft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), Assistance.class);
                i.putExtra("choosed", 2);
                startActivity(i);
            }
        });
        terrorism = (Button) view.findViewById(R.id.btn_terrorism);
        terrorism.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), Assistance.class);
                i.putExtra("choosed", 4);
                startActivity(i);
            }
        });
        sosbtn = (Button) view.findViewById(R.id.sosButton);
        sosbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), Assistance.class);
                i.putExtra("choosed", 0);
                startActivity(i);
            }
        });
    }

}
