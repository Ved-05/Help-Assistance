package demo.breath.com.helpassistance;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Assistance extends AppCompatActivity {

    //Image
    private ImageView imgContainer;
    private Button cancel, outOfEmg, yesButton, noButton;
    private TextView statusBar, response, userAddContainer;
    private FirebaseFirestore mDb;
    private UserLocation mUserLocation;
    private String userAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistance);

        final int id = getIntent().getIntExtra("choosed", 0);

        mDb = FirebaseFirestore.getInstance();

        getActiveUserDetails(id);

        imgContainer = (ImageView) findViewById(R.id.img_container);
        outOfEmg = (Button) findViewById(R.id.outOfEmergency);
        switch(id){
            case 0:
                imgContainer.setImageResource(getResources().getIdentifier("img_sos", "drawable",  getPackageName()));
                outOfEmg.setTextColor(getResources().getColor(R.color.fontColorPrimary));
                outOfEmg.setBackgroundResource(getResources().getIdentifier("btn_asst_sos", "drawable",  getPackageName()));
                break;
            case 1: imgContainer.setImageResource(getResources().getIdentifier("img_accdnt", "drawable",  getPackageName()));
                outOfEmg.setBackgroundResource(getResources().getIdentifier("btn_asst_accdnt", "drawable",  getPackageName()));
                break;
            case 2: imgContainer.setImageResource(getResources().getIdentifier("img_theft", "drawable",  getPackageName()));
                outOfEmg.setBackgroundResource(getResources().getIdentifier("btn_asst_theft", "drawable",  getPackageName()));
                break;
            case 3: imgContainer.setImageResource(getResources().getIdentifier("img_medical", "drawable",  getPackageName()));
                outOfEmg.setBackgroundResource(getResources().getIdentifier("btn_asst_medical", "drawable",  getPackageName()));
                break;
            case 4: imgContainer.setImageResource(getResources().getIdentifier("img_terrorism", "drawable",  getPackageName()));
                outOfEmg.setBackgroundResource(getResources().getIdentifier("btn_asst_terrorism", "drawable",  getPackageName()));
                break;
        }

        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDb.collection(getString(R.string.collection_assistance))
                        .document(FirebaseAuth.getInstance().getUid())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Failed deleting node.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        statusBar = (TextView) findViewById(R.id.status);
        response = (TextView) findViewById(R.id.response);

        outOfEmg = (Button) findViewById(R.id.outOfEmergency);
        yesButton = (Button) findViewById(R.id.yesBtn);
        noButton = (Button) findViewById(R.id.noBtn);

        outOfEmg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusBar.setCompoundDrawables(null,null,null,null);
                statusBar.setText("If you exit, below details won't be shared.");
                cancel.setVisibility(View.GONE);
                outOfEmg.setVisibility(View.GONE);
                response.setVisibility(View.VISIBLE);
                yesButton.setVisibility(View.VISIBLE);
                noButton.setVisibility(View.VISIBLE);
            }
        });

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDb.collection(getString(R.string.collection_assistance))
                        .document(FirebaseAuth.getInstance().getUid())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Failed deleting node.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusBar.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_if_9_3898370,0);
                statusBar.setText("Hold tight, dispatch is on the way.");
                cancel.setVisibility(View.VISIBLE);
                outOfEmg.setVisibility(View.VISIBLE);
                response.setVisibility(View.GONE);
                yesButton.setVisibility(View.GONE);
                noButton.setVisibility(View.GONE);
            }
        });

        userAddContainer = (TextView) findViewById(R.id.useradd);
    }

    private String getAddress(double latitude, double longitude) throws IOException {
        Geocoder geocoder = new Geocoder(Assistance.this, Locale.getDefault());
        List<Address> addresses= geocoder.getFromLocation(latitude, longitude, 1);
        String userAddress = addresses.get(0).getAddressLine(0);
        return userAddress;

    }

    private void getActiveUserDetails(final int id) {
        if(mUserLocation == null){
            mUserLocation = new UserLocation();
            final DocumentReference activeUserRef = mDb.collection(getString(R.string.collection_user_locations))
                    .document(FirebaseAuth.getInstance().getUid());

            final DocumentReference assistanceRef = mDb.collection(getString(R.string.collection_assistance))
                    .document(FirebaseAuth.getInstance().getUid());

            activeUserRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        final UserLocation user = task.getResult().toObject(UserLocation.class);

                        switch (id) {
                            case 0:
                                user.setAssistanceRequired(true);
                                break;
                            case 1:
                                user.setNeed_Accident_Assistance(true);
                                break;
                            case 2:
                                user.setNeed_Harassment_Assistance(true);
                                break;
                            case 3:
                                user.setNeed_Medical_Assistance(true);
                                break;
                            case 4:
                                user.setNeed_Terrorism_Assistance(true);
                                break;
                        }

                        Log.d("Assistance Activity", "Fetched and Update: successfully set the user client." + user);
                        mUserLocation = user;

                        try {
                            userAddress = getAddress(user.getGeo_point().getLatitude(),user.getGeo_point().getLongitude());
                            userAddContainer.setText(userAddress);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        assistanceRef.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("Assistance Activity", "Fetched and Update: User." + user + "\nmUserLocation."+ mUserLocation);
                            }
                        });
                    }
                }
            });
        }
    }


}
