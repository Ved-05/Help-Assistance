package demo.breath.com.helpassistance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Settings extends Fragment {

    //buttons
    private Button signOutBtn;
    //firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    //construtor
    public Settings() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        //get userId
        final String userId = mAuth.getCurrentUser().getUid();

        signOutBtn = (Button) view.findViewById(R.id.signOut);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child("usersActive").child(userId).setValue(null);
                mAuth.signOut();
                getActivity().finish();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            String user_id = mAuth.getCurrentUser().getUid();
            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("users").child("usersActive").child(user_id);
            db.setValue(true);
        }
    }
}
