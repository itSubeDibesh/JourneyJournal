package com.ismt.dibeshrajsubedi.journeyjournal.view_models.authentication;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.authentication.LoginDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.authentication.register.RegisterDetailsDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.models.helper.LoginProfileHelperModel;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.helper.JJ_AuthViewModel;

import java.util.Objects;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.view_models was
 * Created by Dibesh Raj Subedi on 3/19/2022.
 */
public class LoginViewModel extends JJ_AuthViewModel {
    public final MutableLiveData<LoginProfileHelperModel> isLoginSuccess = new MutableLiveData<>();
    public final MutableLiveData<LoginProfileHelperModel> isUserLoggedIn = new MutableLiveData<>();
    private final String TAG = "JJ_LoginViewModel";

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Checks if user is already LoggedIn and observe isUserLoggedIn MutableLiveData
     *
     * @param owner LifecycleOwner
     */
    public void isUseAlreadyLoggedIn(LifecycleOwner owner) {
        firebaseAuthImpl.getUserLoggedIn().observe(owner, isLoggedIn -> {
            Log.d(TAG, "isUseAlreadyLoggedIn: Received isLoggedIn as " + isLoggedIn);
            if (isLoggedIn) {
                firebaseAuthImpl.getDatabase().getReference("User").child(Objects.requireNonNull(firebaseAuthImpl.getAuth().getCurrentUser()).getUid()).get().addOnCompleteListener(dataset -> {
                    LoginProfileHelperModel loginModel = new LoginProfileHelperModel(true, getString(R.string.message_welcome_back));
                    loginModel.setFirebaseUser(firebaseAuthImpl.getAuth().getCurrentUser());
                    loginModel.setRegisterDetailsDAO(dataset.getResult().getValue(RegisterDetailsDAO.class));
                    isUserLoggedIn.postValue(loginModel);
                });
            } else
                isUserLoggedIn.postValue(new LoginProfileHelperModel(false, getString(R.string.message_login)));
        });

    }

    /**
     * Validates Login and Sets isLoginSuccess and isUserLoggedIn MutableLiveData
     *
     * @param loginDAO          LoginDAO
     * @param internetConnected boolean
     * @param owner             LifecycleOwner
     */
    public void loginValidation(LoginDAO loginDAO, boolean internetConnected, LifecycleOwner owner) {
        Log.d(TAG, "loginValidation: Triggered loginValidation with internetConnected as " + internetConnected);

        // Email Validation
        String Email = loginDAO.getEmail();
        EmailInValid(Email);

        // Password Validation
        String Password = loginDAO.getPassword();
        PasswordInValid(Password);

        // ToDo: Implement UseCase and Other elements of clean architecture
        if (internetConnected) {
            if (!loginDAO.isNullOrEmpty(Email) && !loginDAO.isNullOrEmpty(Password)) {
                // Checking User LoggedIn Success
                firebaseAuthImpl.login(loginDAO);
                // Get firebase Logged in User Details
                firebaseAuthImpl.getUserLoggedMutableLiveData().observe(owner, user -> {
                    // Check if firebase User is Not Null
                    LoginProfileHelperModel loginModel;
                    if (user.getStatus()) {
                        Log.d(TAG, "loginValidation: Login Success With Email as " + user.getFirebaseUser().getEmail());
                        Log.d(TAG, "loginValidation: Login Success With Name as " + user.getRegisterDetailsDAO().getDisplayName());
                        loginModel = new LoginProfileHelperModel(true, getString(R.string.message_login_success));
                        loginModel.setFirebaseUser(user.getFirebaseUser());
                        loginModel.setRegisterDetailsDAO(user.getRegisterDetailsDAO());
                        isLoginSuccess.postValue(loginModel);
                    } else {
                        isLoginSuccess.postValue(new LoginProfileHelperModel(false, user.getMessage()));
                    }
                });
            }
        }
    }

}
