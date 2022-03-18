package com.ismt.dibeshrajsubedi.journeyjournal.frameworks.journeyjournal.registration;

import android.util.Log;

import com.ismt.dibeshrajsubedi.journeyjournal.data.sources.registration.RegistrationRemoteSource;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.classes.Auth;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.classes.Register;
import com.ismt.dibeshrajsubedi.journeyjournal.domain.models.RegistrationModel;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.frameworks.journeyjournal.registration was
 * Created by Dibesh Raj Subedi on 3/15/2022.
 */
public class RegisterRemoteImpl implements RegistrationRemoteSource {
    private final String TAG = "JJ_RSIRegister";
    private final boolean internetConnected;

    public RegisterRemoteImpl(boolean internetConnected) {
        this.internetConnected = internetConnected;
        Log.d(TAG, "RegisterRemoteImpl: Constructor Called with internetConnected as " + internetConnected);
    }

    @Override
    public boolean isInternetConnected() {
        Log.d(TAG, "isInternetConnected: Returning internetConnected as " + internetConnected);
        return internetConnected;
    }

    @Override
    public RegistrationModel registerUserRemote(Register register, Auth auth) {
        Log.d(TAG, "registerUserRemote: Method called with Register with Email as " + register.getEmail());
        // TODO: Validate and Return actual Dataset
        return new RegistrationModel(true, "Success");
    }
}
