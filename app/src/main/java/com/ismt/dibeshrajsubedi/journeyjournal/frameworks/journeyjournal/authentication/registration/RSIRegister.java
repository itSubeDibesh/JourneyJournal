package com.ismt.dibeshrajsubedi.journeyjournal.frameworks.journeyjournal.authentication.registration;

import android.util.Log;

import com.ismt.dibeshrajsubedi.journeyjournal.data.sources.authentication.registration.RSRegistration;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.classes.authentication.DMCRegistration;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.models.authentication.DMRegistration;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.frameworks.journeyjournal.authentication.registration was
 * Created by Dibesh Raj Subedi on 3/15/2022.
 */
public class RSIRegister implements RSRegistration {
    private final String TAG = "JJ_RSIRegister";

    private final boolean internetConnected;

    public RSIRegister(boolean internetConnected) {
        this.internetConnected = internetConnected;
        Log.d(TAG, "RSIRegister: Constructor Called with internetConnected as " + internetConnected);
    }

    @Override
    public boolean isInternetConnected() {
        Log.d(TAG, "isInternetConnected: Returning internetConnected as " + internetConnected);
        return internetConnected;
    }

    @Override
    public DMRegistration registerUserRemote(DMCRegistration DMCRegistration) {
        Log.d(TAG, "registerUserRemote: Method called with DMCRegistration with Email as " + DMCRegistration.getEmail());
        // Create API Call To Create Account With Data
        // TODO: Firebase Call And create User
        // Fetch Data
        // Return Accordingly
        DMRegistration registration = new DMRegistration(true, "User Registered Successfully");
        registration.setRegistration(DMCRegistration);
        Log.d(TAG, "registerUserRemote: Returning DMRegistration with isRegistrationSuccess as " + true);
        return registration;
    }
}
