package com.ismt.dibeshrajsubedi.journeyjournal.models.authentication;

import com.google.firebase.auth.FirebaseUser;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.helper.StatusHelperDAO;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.models.authentication.register was
 * Created by Dibesh Raj Subedi on 3/20/2022.
 */
public class LoginModel extends StatusHelperDAO {
    private FirebaseUser firebaseUser;

    public LoginModel(boolean isSuccess) {
        super(isSuccess);
    }

    public LoginModel(boolean isSuccess, String message) {
        super(isSuccess, message);
    }

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public void setFirebaseUser(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
    }
}
