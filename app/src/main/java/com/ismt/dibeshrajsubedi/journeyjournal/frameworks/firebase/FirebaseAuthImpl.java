package com.ismt.dibeshrajsubedi.journeyjournal.frameworks.firebase;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.classes.Auth;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.classes.Register;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.models.RegistrationModel;

import java.util.Objects;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.frameworks.firebase was
 * Created by Dibesh Raj Subedi on 3/18/2022.
 */
public class FirebaseAuthImpl {
    private final String TAG = "JJ_RIFirebaseAuth";
    private final MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private final MutableLiveData<RegistrationModel> dmRegistrationMutableLiveData;
    private final MutableLiveData<Boolean> userLoggedMutableLiveData;
    private final FirebaseAuth auth;
    private final FirebaseDatabase database;

    public MutableLiveData<RegistrationModel> getDmRegistrationMutableLiveData() {
        return dmRegistrationMutableLiveData;
    }

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public MutableLiveData<Boolean> getUserLoggedMutableLiveData() {
        return userLoggedMutableLiveData;
    }


    public FirebaseAuthImpl() {
        firebaseUserMutableLiveData = new MutableLiveData<>();
        userLoggedMutableLiveData = new MutableLiveData<>();
        dmRegistrationMutableLiveData = new MutableLiveData<>();
        // Extracting Firebase instance
        auth = FirebaseAuth.getInstance();
        // Extracting Database instance
        database = FirebaseDatabase.getInstance();
        // Retrieves User if Null
        if (auth.getCurrentUser() != null)
            firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
    }

    public void register(Register Register, Auth Auth) {
        // Step 1: Create User With Email Password Call
        Log.d(TAG, "register: Received Auth and Register");
        auth.createUserWithEmailAndPassword(
                Auth.getEmail(),
                Auth.getPassword()
        ).addOnCompleteListener(create_auth -> {
            RegistrationModel registration;
            Log.d(TAG, "register: Inside addOnCompleteListener of createUserWithEmailAndPassword ");
            //Step 2: Success Condition Check
            if (create_auth.isSuccessful()) {
                registration = new RegistrationModel(true, "User Registered Successfully");
                // Step 3: Create New user
                Register user = new Register(Register.getEmail(), Register.getDisplayName());
                // Step 4: Create User in Database as well
                database.getReference("User")
                        .child(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                        .setValue(user)
                        .addOnCompleteListener(create_database -> {
                            // Step 5: Success Condition Check
                            if (create_database.isSuccessful()) {
                                firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
                            } else {
                                RegistrationModel registration_db;
                                registration_db = new RegistrationModel(false, create_auth.getException().getMessage());
                                registration_db.setRegistration(Register);
                            }
                        });
            } else {
                registration = new RegistrationModel(false, create_auth.getException().getMessage());
            }
            registration.setRegistration(Register);
            Log.d(TAG, "register: At registration.setRegistration received isRegistrationSuccess as " + registration.isRegistrationSuccess());
            dmRegistrationMutableLiveData.postValue(registration);
        });
    }

    public void logOut() {
        Log.d(TAG, "logOut: At Logout, Triggered by " + auth.getCurrentUser().getEmail());
        auth.signOut();
        userLoggedMutableLiveData.postValue(true);
    }
}
