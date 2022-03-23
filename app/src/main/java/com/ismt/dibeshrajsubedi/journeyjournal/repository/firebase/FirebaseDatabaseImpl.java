package com.ismt.dibeshrajsubedi.journeyjournal.repository.firebase;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.FirebaseDatabase;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.home.JourneyDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.models.home.JourneyModel;

import java.util.UUID;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.repository.firebase was
 * Created by Dibesh Raj Subedi on 3/21/2022.
 */
public class FirebaseDatabaseImpl {
    private final String TAG = FirebaseDatabaseImpl.class.getSimpleName();
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final String Table_Name = "Journey";
    private final MutableLiveData<JourneyModel> fetchJourney = new MutableLiveData<>();
    private final MutableLiveData<JourneyModel> addJourney = new MutableLiveData<>();
    private final MutableLiveData<JourneyModel> updateJourney = new MutableLiveData<>();
    private final MutableLiveData<JourneyModel> deleteJourney = new MutableLiveData<>();

    public FirebaseDatabase getDatabase() {
        return database;
    }

    public MutableLiveData<JourneyModel> getFetchJourney() {
        return fetchJourney;
    }

    public MutableLiveData<JourneyModel> getAddJourney() {
        return addJourney;
    }

    public MutableLiveData<JourneyModel> getUpdateJourney() {
        return updateJourney;
    }

    public MutableLiveData<JourneyModel> getDeleteJourney() {
        return deleteJourney;
    }

    public void addJourney(JourneyDAO journeyDAO) {

    }

    public void fetchJourney() {

    }

    public void updateJourney(JourneyDAO journeyDAO, UUID uuid) {

    }

    public void deleteJourney() {

    }

}
