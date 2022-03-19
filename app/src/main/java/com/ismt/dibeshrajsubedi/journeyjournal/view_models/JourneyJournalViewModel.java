package com.ismt.dibeshrajsubedi.journeyjournal.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ismt.dibeshrajsubedi.journeyjournal.frameworks.firebase.FirebaseAuthImpl;
import com.ismt.dibeshrajsubedi.journeyjournal.models.helper.StatusHelperModel;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.view_models was
 * Created by Dibesh Raj Subedi on 3/19/2022.
 */
public class JourneyJournalViewModel extends AndroidViewModel {
    /**
     * Instantiating isEmailInvalid MutableLiveDat as common live data on most of case
     */
    public final MutableLiveData<StatusHelperModel> isEmailInValid = new MutableLiveData<>();

    public JourneyJournalViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Returns String Value from resource dataset
     *
     * @param res int
     * @return String
     */
    protected String getString(int res) {
        return getApplication().getString(res);
    }

    /**
     * Instantiating firebase Auth Implementation
     */
    protected final FirebaseAuthImpl firebaseAuthImpl = new FirebaseAuthImpl();

}
