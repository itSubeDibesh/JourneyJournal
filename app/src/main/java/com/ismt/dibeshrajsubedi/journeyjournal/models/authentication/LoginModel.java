package com.ismt.dibeshrajsubedi.journeyjournal.models.authentication;

import com.google.firebase.auth.FirebaseUser;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.models.authentication.register was
 * Created by Dibesh Raj Subedi on 3/20/2022.
 */
public class LoginModel {
    private final boolean isSuccess;
    private final String message;
    private FirebaseUser firebaseUser;

    public LoginModel(boolean isSuccess) {
        this.isSuccess = isSuccess;
        this.message = null;
    }

    public LoginModel(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public void setFirebaseUser(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
    }
}
