package com.ismt.dibeshrajsubedi.journeyjournal.repository.firebase;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.authentication.LoginDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.authentication.register.RegisterDetailsDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.authentication.register.RegisterFormDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.models.helper.LoginProfileHelperModel;

import java.util.Objects;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.frameworks.firebase was
 * Created by Dibesh Raj Subedi on 3/18/2022.
 */
public class FirebaseAuthImpl {
    private final String TAG = "JJ_RIFirebaseAuth";
    private final MutableLiveData<FirebaseUser> firebaseUserMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<LoginProfileHelperModel> registerModelMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<LoginProfileHelperModel> userLoggedMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> resetSuccessMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> userLoggedIn = new MutableLiveData<>();
    private final FirebaseAuth auth;
    private final FirebaseDatabase database;

    /**
     * Constructor and Initializer for FirebaseAuthImpl
     */
    public FirebaseAuthImpl() {
        // Extracting Firebase instance
        auth = FirebaseAuth.getInstance();
        // Extracting Database instance
        database = FirebaseDatabase.getInstance();
        // Retrieves User if Null
        if (auth.getCurrentUser() != null) {
            database.getReference("User").child(auth.getCurrentUser().getUid()).get().addOnCompleteListener(data -> {
                LoginProfileHelperModel model = new LoginProfileHelperModel(true, "Welcome Back, " + auth.getCurrentUser().getEmail());
                model.setFirebaseUser(auth.getCurrentUser());
                model.setRegisterDetailsDAO(data.getResult().getValue(RegisterDetailsDAO.class));
                userLoggedMutableLiveData.postValue(model);
                Log.d(TAG, "FirebaseAuthImpl: Received Current user Email as " + Objects.requireNonNull(auth.getCurrentUser()).getEmail());
            });
        } else Log.d(TAG, "FirebaseAuthImpl: Null Current User, Needs Login or Register");
    }

    /**
     * Returns Firebase Auth
     *
     * @return FirebaseAuth
     */
    public FirebaseAuth getAuth() {
        return auth;
    }

    /**
     * Returns  MutableLiveData<LoginProfileHelperModel>
     *
     * @return MutableLiveData<LoginProfileHelperModel>
     */
    public MutableLiveData<LoginProfileHelperModel> getRegisterModelMutableLiveData() {
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
     * Returns  MutableLiveData<LoginProfileHelperModel>
     *
     * @return MutableLiveData<LoginProfileHelperModel>
     */
    public MutableLiveData<LoginProfileHelperModel> getUserLoggedMutableLiveData() {
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
            Log.d(TAG, "register: Inside addOnCompleteListener of createUserWithEmailAndPassword ");
            //Step 2: Success Condition Check
            if (create_auth.isSuccessful()) {
                // Step 3: Create New user
                RegisterDetailsDAO user = new RegisterDetailsDAO(registerFormDAO.getEmail(), registerFormDAO.getName());
                // Step 4: Create User in Database as well
                database.getReference("User")
                        .child(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                        .setValue(user)
                        .addOnCompleteListener(create_database -> {
                            // Step 5: Success Condition Check
                            LoginProfileHelperModel login_db;
                            if (create_database.isSuccessful()) {
                                login_db = new LoginProfileHelperModel(true, "User Registered Successfully");
                                login_db.setRegisterDetailsDAO(user);
                                login_db.setFirebaseUser(create_auth.getResult().getUser());
                            } else {
                                login_db = new LoginProfileHelperModel(false, Objects.requireNonNull(create_auth.getException()).getMessage());
                            }
                            registerModelMutableLiveData.postValue(login_db);
                            Log.d(TAG, "register: At registration.setRegistration received isRegistrationSuccess as " + login_db.isSuccess());
                        });
            } else
                registerModelMutableLiveData.postValue(new LoginProfileHelperModel(false, Objects.requireNonNull(create_auth.getException()).getMessage()));
        });
    }

    /**
     * Login User and Sets userLoggedMutableLiveData and firebaseUserMutableLiveData on success
     *
     * @param loginDAO LoginDAO
     */
    public void login(LoginDAO loginDAO) {
        // Step 1: Call Auth Login Method
        Log.d(TAG, "login: Received LoginDAO and email as " + loginDAO.getEmail());
        auth.signInWithEmailAndPassword(loginDAO.getEmail(), loginDAO.getPassword())
                .addOnCompleteListener(login -> {
                    // Step 2: Set userLoggedMutableLiveData
                    LoginProfileHelperModel loginModel;
                    userLoggedIn.postValue(login.isSuccessful());
                    if (login.isSuccessful()) {
                        database.getReference("User").child(Objects.requireNonNull(login.getResult().getUser()).getUid()).get().addOnCompleteListener(dataset -> {
                            LoginProfileHelperModel login_dataset_Model;
                            if (dataset.isSuccessful()) {
                                login_dataset_Model = new LoginProfileHelperModel(true);
                                login_dataset_Model.setFirebaseUser(login.getResult().getUser());
                                login_dataset_Model.setRegisterDetailsDAO(dataset.getResult().getValue(RegisterDetailsDAO.class));
                                userLoggedMutableLiveData.postValue(login_dataset_Model);
                                Log.d(TAG, "login: User logged in Successfully with UUID as " + Objects.requireNonNull(auth.getCurrentUser()).getUid());
                            } else {
                                userLoggedMutableLiveData.postValue(new LoginProfileHelperModel(false, Objects.requireNonNull(login.getException()).getMessage()));
                            }
                        });
                    } else
                        userLoggedMutableLiveData.postValue(new LoginProfileHelperModel(false, Objects.requireNonNull(login.getException()).getMessage()));
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
