package com.ismt.dibeshrajsubedi.journeyjournal.repository.firebase;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.authentication.LoginDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.authentication.register.RegisterDetailsDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.authentication.register.RegisterFormDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.models.authentication.LoginModel;
import com.ismt.dibeshrajsubedi.journeyjournal.models.authentication.RegisterModel;

import java.util.Objects;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.frameworks.firebase was
 * Created by Dibesh Raj Subedi on 3/18/2022.
 */
public class FirebaseAuthImpl {
    private final String TAG = "JJ_RIFirebaseAuth";
    private final MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private final MutableLiveData<RegisterModel> registerModelMutableLiveData;
    private final MutableLiveData<LoginModel> userLoggedMutableLiveData;
    private final MutableLiveData<Boolean> resetSuccessMutableLiveData;
    private final  MutableLiveData<Boolean> userLoggedIn;
    private final FirebaseAuth auth;
    private final FirebaseDatabase database;

    /**
     * Returns Firebase Auth
     *
     * @return FirebaseAuth
     */
    public FirebaseAuth getAuth() {
        return auth;
    }

    /**
     * Returns  MutableLiveData<RegisterFormDAO>
     *
     * @return MutableLiveData<RegisterFormDAO>
     */
    public MutableLiveData<RegisterModel> getRegisterModelMutableLiveData() {
        return registerModelMutableLiveData;
    }

    /**
     * Returns  MutableLiveData<FirebaseUser>
     *
     * @return MutableLiveData<FirebaseUser>
     */
    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    /**
     * Returns  MutableLiveData<Boolean>
     *
     * @return MutableLiveData<Boolean>
     */
    public MutableLiveData<LoginModel> getUserLoggedMutableLiveData() {
        return userLoggedMutableLiveData;
    }

    /**
     * Returns  MutableLiveData<Boolean>
     *
     * @return MutableLiveData<Boolean>
     */
    public MutableLiveData<Boolean> getUserLoggedIn() {
        return userLoggedIn;
    }


    /**
     * Returns  MutableLiveData<Boolean>
     *
     * @return MutableLiveData<Boolean>
     */
    public MutableLiveData<Boolean> getResetSuccessMutableLiveData() {
        return resetSuccessMutableLiveData;
    }

    /**
     * Constructor and Initializer for FirebaseAuthImpl
     */
    public FirebaseAuthImpl() {
        firebaseUserMutableLiveData = new MutableLiveData<>();
        userLoggedMutableLiveData = new MutableLiveData<>();
        userLoggedIn = new MutableLiveData<>();
        registerModelMutableLiveData = new MutableLiveData<>();
        resetSuccessMutableLiveData = new MutableLiveData<>();
        // Extracting Firebase instance
        auth = FirebaseAuth.getInstance();
        // Extracting Database instance
        database = FirebaseDatabase.getInstance();
        // Retrieves User if Null
        if (auth.getCurrentUser() != null)
            firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
    }

    /**
     * Registers new user and adds user details on user table or realtime database
     *
     * @param registerFormDAO RegisterFormDAO
     */
    public void register(RegisterFormDAO registerFormDAO) {
        // Step 1: Create User With Email Password Call
        Log.d(TAG, "register: Received Auth and Register");
        auth.createUserWithEmailAndPassword(
                registerFormDAO.getEmail(),
                registerFormDAO.getPassword()
        ).addOnCompleteListener(create_auth -> {
            RegisterModel registration;
            Log.d(TAG, "register: Inside addOnCompleteListener of createUserWithEmailAndPassword ");
            //Step 2: Success Condition Check
            if (create_auth.isSuccessful()) {
                registration = new RegisterModel(true, "User Registered Successfully");
                // Step 3: Create New user
                RegisterDetailsDAO user = new RegisterDetailsDAO(registerFormDAO.getEmail(), registerFormDAO.getName());
                // Step 4: Create User in Database as well
                database.getReference("User")
                        .child(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                        .setValue(user)
                        .addOnCompleteListener(create_database -> {
                            // Step 5: Success Condition Check
                            if (!create_database.isSuccessful()) {
                                RegisterModel registration_db = new RegisterModel(false, Objects.requireNonNull(create_auth.getException()).getMessage());
                                registration_db.setRegistration(user);
                            }
                        });
            } else {
                registration = new RegisterModel(false, Objects.requireNonNull(create_auth.getException()).getMessage());
            }
            registration.setRegistration(new RegisterDetailsDAO(registerFormDAO.getEmail(), registerFormDAO.getName()));
            Log.d(TAG, "register: At registration.setRegistration received isRegistrationSuccess as " + registration.isRegistrationSuccess());
            registerModelMutableLiveData.postValue(registration);
        });
    }

    /**
     * Login User and Sets userLoggedMutableLiveData and firebaseUserMutableLiveData on success
     *
     * @param loginDAO LoginDAO
     */
    public void login(LoginDAO loginDAO) {
        // Step 1: Call Auth Login Method
        Log.d(TAG, "login: Received LoginDAO");
        auth.signInWithEmailAndPassword(loginDAO.getEmail(), loginDAO.getPassword())
                .addOnCompleteListener(login -> {
                    // Step 2: Set userLoggedMutableLiveData
                    LoginModel loginModel;
                    userLoggedIn.postValue(login.isSuccessful());
                    if (login.isSuccessful()) {
                        loginModel = new LoginModel(true);
                        loginModel.setFirebaseUser(login.getResult().getUser());
                        userLoggedMutableLiveData.postValue(loginModel);
                        Log.d(TAG, "login: User logged in Successfully with UUID as " + Objects.requireNonNull(auth.getCurrentUser()).getUid());
                    } else
                        userLoggedMutableLiveData.postValue(new LoginModel(false, login.getException().getMessage()));
                });
    }

    /**
     * LogOut User From Current System
     */
    public void logOut() {
        Log.d(TAG, "logOut: At Logout, Triggered by " + Objects.requireNonNull(auth.getCurrentUser()).getEmail());
        auth.signOut();
        userLoggedIn.postValue(false);
    }

    // TODO: User Profile Update
}
