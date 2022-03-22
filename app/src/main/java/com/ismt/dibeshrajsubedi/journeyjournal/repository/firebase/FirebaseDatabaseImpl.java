package com.ismt.dibeshrajsubedi.journeyjournal.repository.firebase;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.repository.firebase was
 * Created by Dibesh Raj Subedi on 3/21/2022.
 */
public class FirebaseDatabaseImpl {
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();

    public FirebaseDatabase getDatabase() {
        return database;
    }

}
