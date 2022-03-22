package com.ismt.dibeshrajsubedi.journeyjournal.repository.firebase;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.repository.firebase was
 * Created by Dibesh Raj Subedi on 3/21/2022.
 */
public class FirebaseStorageImpl {
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private final StorageReference reference = storage.getReferenceFromUrl("gs://journeyjournal-app.appspot.com");

    public FirebaseStorage getStorage() {
        return storage;
    }

    public StorageReference getReference() {
        return reference;
    }
}
