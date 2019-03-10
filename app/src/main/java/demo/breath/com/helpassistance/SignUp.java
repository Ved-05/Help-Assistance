package demo.breath.com.helpassistance;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    private EditText getEmail, getPassword;
    private TextView textAlready;
    private Button signUser, changeScreen;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //firebase
        mAuth = FirebaseAuth.getInstance();

        //email and password
        getEmail = (EditText) findViewById(R.id.getMail);
        getPassword = (EditText) findViewById(R.id.getPassword);

        //buttons
        signUser = (Button) findViewById(R.id.signUpUser);
        changeScreen = (Button) findViewById(R.id.changeScreen);

        //TextView
        textAlready = (TextView) findViewById(R.id.textAlready);

        //sign User pressed
        signUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String signUserText = signUser.getText().toString().toLowerCase();
                final String m = getEmail.getText().toString();
                final String p = getPassword.getText().toString();
                if(m.isEmpty() || p.isEmpty()){
                    Toast.makeText(getApplicationContext(),
                            "Both fields are compulsory.",
                            Toast.LENGTH_SHORT).show();
                }else if(p.length()<7){
                    Toast.makeText(getApplicationContext(),"Password is short",Toast.LENGTH_SHORT).show();
                }else {
                    if(signUserText.equals("sign up")){
                        userSignUp(m,p);
                    }else{
                        userSignIn(m,p);
                    }
                }
            }
        });

        //Change screens pressed
        changeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String signUserText = changeScreen.getText().toString().toLowerCase();
                if(!signUserText.equals("sign up")){
                    textAlready.setText("Not Registered");
                    signUser.setText("Sign In");
                    changeScreen.setText("Sign Up");
                }else{
                    textAlready.setText("Already Registered");
                    signUser.setText("Sign Up");
                    changeScreen.setText("Sign In");
                }
            }
        });
    }

    private void userSignIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information\
                            String user_id = mAuth.getCurrentUser().getUid();
                            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("users").child("usersActive").child(user_id);
                            db.setValue(true);

                            Intent intent = new Intent(SignUp.this, Home.class);

                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUp.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void userSignUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information\
                    String user_id = mAuth.getCurrentUser().getUid();
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("users").child("usersActive").child(user_id);
                    db.setValue(true);

                    Intent intent = new Intent(SignUp.this, UserDetails.class);
                    intent.putExtra("userId", user_id);

                    startActivity(intent);
                    finish();
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(SignUp.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                }
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

            Intent intent = new Intent(SignUp.this, Home.class);
            intent.putExtra("userId", user_id);
            startActivity(intent);
            finish();
        }
    }
}
