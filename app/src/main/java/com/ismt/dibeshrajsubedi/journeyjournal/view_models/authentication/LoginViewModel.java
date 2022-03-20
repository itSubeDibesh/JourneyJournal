package com.ismt.dibeshrajsubedi.journeyjournal.view_models.authentication;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.authentication.LoginDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.models.authentication.LoginModel;
import com.ismt.dibeshrajsubedi.journeyjournal.view_models.helper.JourneyJournalViewModel;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.view_models was
 * Created by Dibesh Raj Subedi on 3/19/2022.
 */
public class LoginViewModel extends JourneyJournalViewModel {
    private final String TAG = "JJ_LoginViewModel";
    public final MutableLiveData<LoginModel> isLoginSuccess = new MutableLiveData<>();
    public final MutableLiveData<LoginModel> isUserLoggedIn = new MutableLiveData<>();

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
            LoginModel loginModel;
            if (isLoggedIn) {
                loginModel = new LoginModel(true, getString(R.string.message_welcome_back));
                loginModel.setFirebaseUser(firebaseAuthImpl.getAuth().getCurrentUser());
                isUserLoggedIn.postValue(loginModel);
            } else
                isUserLoggedIn.postValue(new LoginModel(false, getString(R.string.message_login)));
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
            // Checking User LoggedIn Success
            firebaseAuthImpl.login(loginDAO);
            // Get firebase Logged in User Details
            firebaseAuthImpl.getUserLoggedMutableLiveData().observe(owner, user -> {
                // Check if firebase User is Not Null
                LoginModel loginModel;
                if (user.isSuccess()) {
                    Log.d(TAG, "loginValidation: Login Success With Email as " + user.getFirebaseUser().getEmail());
                    loginModel = new LoginModel(true, getString(R.string.message_login_success));
                    loginModel.setFirebaseUser(user.getFirebaseUser());
                    isLoginSuccess.postValue(loginModel);
                } else {
                    isLoginSuccess.postValue(new LoginModel(false, user.getMessage()));
                }
            });

        }
    }

}
