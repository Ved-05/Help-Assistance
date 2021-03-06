package demo.breath.com.helpassistance;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.PrivateKey;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import io.opencensus.tags.Tag;

public class UserDetails extends AppCompatActivity {
    private TextView welcomeUser, DOB;
    private EditText name, mobNo;
    private RadioButton male, female, transgender;
    private Button complete;
    private FirebaseFirestore database;
    final Calendar myCalendar = Calendar.getInstance();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        //firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Access a Cloud Firestore instance from your Activity
        database = FirebaseFirestore.getInstance();

        welcomeUser = (TextView) findViewById(R.id.welcomeUser);
        //entries
        name = (EditText) findViewById(R.id.get_userName);
        DOB = (TextView) findViewById(R.id.dob);
        mobNo = (EditText) findViewById(R.id.mbno);
        //radio Buttons
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        transgender = (RadioButton) findViewById(R.id.transgender);
        //button
        complete = (Button) findViewById(R.id.complete);

        //greet user
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                welcomeUser.setText("Hello, "+name.getText().toString().trim());
            }
        });

        //date picker
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                DOB.setText(dayOfMonth+"-"+monthOfYear+"-"+year);
            }

        };

        DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(UserDetails.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //complete ?
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String gender;
                if (male.isChecked()) {
                    gender = "Male";
                } else if (female.isChecked()) {
                    gender = "Female";
                } else {
                    gender = "Transgender";
                }
                final String userName = name.getText().toString().trim();
                final String date = DOB.getText().toString().trim();
                final String mobile = mobNo.getText().toString().trim();

                //check constraints
                if(userName.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please enter your name",Toast.LENGTH_SHORT).show();
                }else if(gender.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please select gender",Toast.LENGTH_SHORT).show();
                }else if(date.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please select dob",Toast.LENGTH_SHORT).show();
                }else if(mobile.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Check Mobile Number",Toast.LENGTH_SHORT).show();
                }else{
                    addDetails(userName,gender,date,mobile);

                    //launch next
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

   private void addDetails(String userName, String gender, String date, String mobile) {
       String userId = mAuth.getCurrentUser().getUid();
       Map<String, Object> node = new HashMap<>();
       node.put("name", userName);
       node.put("gender", gender);
       node.put("dateOfBirth", date);
       node.put("mobileNumber", mobile);
       node.put("ID", userId);

       database.collection("users")
               .document(userId).set(node)
               .addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void aVoid) {
                       Toast.makeText(getApplicationContext(), "User Details Captured", Toast.LENGTH_SHORT).show();
                   }
               })
       ;
   }
}
