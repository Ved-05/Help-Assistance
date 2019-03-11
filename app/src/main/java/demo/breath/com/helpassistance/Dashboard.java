package demo.breath.com.helpassistance;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

public class Dashboard extends Fragment {

    //button
    private Button sosbtn, accdnt, medical, theft, terrorism;
    //textView
    private TextView name;
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseFirestore mDatabase;

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
        mDatabase = FirebaseFirestore.getInstance();

        //
        name = (TextView) view.findViewById(R.id.name2);
        //
        //get userId
        final String userId = mAuth.getCurrentUser().getUid();

        DocumentReference docRef = mDatabase.collection("users").document(userId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        final String userName = document.getString("name");
                        name.setText("Greetings "+userName);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
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
