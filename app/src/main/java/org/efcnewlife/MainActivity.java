package org.efcnewlife;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.efcnewlife.model.YoutubePlayList;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;

    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = getResources().getString(R.string.email);
        password = getResources().getString(R.string.password);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

//        if (mFirebaseUser == null) {
//            // Not logged in, launch with preset username/password
//            mFirebaseAuth.createUserWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                Log.d(TAG, "create username/password succeed");
//                            } else {
//                                Log.d(TAG, "create username/password failed");
//                            }
//                        }
//                    });
//        }

        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mUserId = mFirebaseUser.getUid();

                            mDatabase.child("Videos")
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                //Getting the data from snapshot
                                                YoutubePlayList youtubePlayList = new YoutubePlayList(Integer.parseInt(postSnapshot.getKey()), postSnapshot.getValue().toString());
                                                Log.d(TAG, youtubePlayList.toString());
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                        } else {

                        }
                    }
                });
    }
}
