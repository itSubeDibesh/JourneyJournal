package com.ismt.dibeshrajsubedi.journeyjournal.repository.firebase;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.helper.StatusHelperDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.home.JourneyDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.home.JourneyRetrieverDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.models.home.JourneyModel;

import java.util.ArrayList;
import java.util.Date;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.repository.firebase was
 * Created by Dibesh Raj Subedi on 3/21/2022.
 */
public class FirebaseDatabaseImpl {
    private final String TAG = FirebaseDatabaseImpl.class.getSimpleName();
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final FirebaseStorageImpl storage = new FirebaseStorageImpl();
    private final String Table_Name = "Journey";
    private final MutableLiveData<JourneyModel> addJourney = new MutableLiveData<>();
    private final MutableLiveData<JourneyModel> updateJourney = new MutableLiveData<>();
    private final MutableLiveData<StatusHelperDAO> deleteJourney = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<JourneyRetrieverDAO>> fetchJourney = new MutableLiveData<>();

    /**
     * Random Numeric Id
     *
     * @return
     */
    public long randomId() {
        return new Date().getTime() / randomNumber(2, 10) * randomNumber(2, 10);
    }

    /**
     * Returns Random Number between Range
     *
     * @param minRange int
     * @param maxRange int
     * @return int
     */
    private int randomNumber(int minRange, int maxRange) {
        return minRange + (int) (Math.random() * maxRange);
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }

    public MutableLiveData<JourneyModel> getAddJourney() {
        return addJourney;
    }

    public MutableLiveData<JourneyModel> getUpdateJourney() {
        return updateJourney;
    }

    public MutableLiveData<StatusHelperDAO> getDeleteJourney() {
        return deleteJourney;
    }

    public MutableLiveData<ArrayList<JourneyRetrieverDAO>> getFetchJourney() {
        return fetchJourney;
    }

    public void addJourney(JourneyDAO journeyDAO, Uri image, Context context, LifecycleOwner owner, boolean isCamera, Bitmap bitmap) {
        long random = randomId();
        String RandomID = "JJ_" + random;
        // Step 1: Upload Image if Exists
        if (image != null || bitmap != null) {
            String imageId = "" + RandomID, ImagePath = "images/journey/" + journeyDAO.getJourneyAuthor() + "/";
            if (isCamera) {
                storage.UploadBitMap(bitmap, ImagePath, imageId, context);
            } else {
                storage.UploadImage(image, ImagePath, imageId, context);
            }
            // Step 2: Check getIsUploadSuccess
            storage.getIsUploadSuccess().observe(owner, statusHelperDAO -> {
                if (statusHelperDAO.getStatus()) {
                    // Update journey DAO Uri
                    if (statusHelperDAO.getStatus()) {
                        journeyDAO.setImageUri(statusHelperDAO.getMessage());
                    }
                    database
                            .getReference(Table_Name)
                            .child(journeyDAO.getJourneyAuthor())
                            .child(RandomID)
                            .setValue(journeyDAO)
                            .addOnCompleteListener(createJourney -> {
                                JourneyModel journey;
                                if (createJourney.isSuccessful()) {
                                    journey = new JourneyModel(true, journeyDAO.getJourneyTitle() + ", Added Successfully.");
                                } else {
                                    journey = new JourneyModel(true, createJourney.getException().getMessage());
                                }
                                journey.setJourneyDAO(journeyDAO);
                                addJourney.postValue(journey);
                            });
                } else {
                    addJourney.postValue(new JourneyModel(false, statusHelperDAO.getMessage()));
                }
            });
        } else {
            // Only Perform Db Related Task
            database
                    .getReference(Table_Name)
                    .child(journeyDAO.getJourneyAuthor())
                    .child(RandomID)
                    .setValue(journeyDAO)
                    .addOnCompleteListener(createJourney -> {
                        JourneyModel journey;
                        if (createJourney.isSuccessful()) {
                            journey = new JourneyModel(true, journeyDAO.getJourneyTitle() + ", Added Successfully.");
                        } else {
                            journey = new JourneyModel(true, createJourney.getException().getMessage());
                        }
                        journey.setJourneyDAO(journeyDAO);
                        addJourney.postValue(journey);
                    });
        }
    }

    public void fetchJourneys(String UUid) {
        database.getReference(Table_Name)
                .child(UUid)
                .get()
                .addOnSuccessListener(dataSnapshot -> {
                    if (dataSnapshot != null) {
                        ArrayList<JourneyRetrieverDAO> journey = new ArrayList<>();
                        for (DataSnapshot dataset : dataSnapshot.getChildren()) {
                            JourneyDAO journeyDAO = dataset.getValue(JourneyDAO.class);
                            journey.add(new JourneyRetrieverDAO(dataset.getKey(), journeyDAO));
                        }
                        fetchJourney.postValue(journey);
                    } else {
                        fetchJourney.postValue(new ArrayList<>());
                    }
                });
    }

    public void updateJourney(String JourneyId, JourneyDAO journeyDAO, Uri image, Context context, LifecycleOwner owner, boolean isCamera, Bitmap bitmap) {
        // Step 1: Upload Image if Exists
        if (image != null || bitmap != null) {
            String imageId = "" + JourneyId, ImagePath = "images/journey/" + journeyDAO.getJourneyAuthor() + "/";
            if (isCamera) {
                storage.UploadBitMap(bitmap, ImagePath, imageId, context);
            } else {
                storage.UploadImage(image, ImagePath, imageId, context);
            }
            // Step 2: Check getIsUploadSuccess
            storage.getIsUploadSuccess().observe(owner, statusHelperDAO -> {
                if (statusHelperDAO.getStatus()) {
                    // Update journey DAO Uri
                    if (statusHelperDAO.getStatus()) {
                        journeyDAO.setImageUri(statusHelperDAO.getMessage());
                    }
                    database
                            .getReference(Table_Name)
                            .child(journeyDAO.getJourneyAuthor())
                            .child(JourneyId)
                            .setValue(journeyDAO)
                            .addOnCompleteListener(update -> {
                                JourneyModel journey;
                                if (update.isSuccessful()) {
                                    journey = new JourneyModel(true, journeyDAO.getJourneyTitle() + ", Updated Successfully.");
                                } else {
                                    journey = new JourneyModel(true, update.getException().getMessage());
                                }
                                journey.setJourneyDAO(journeyDAO);
                                updateJourney.postValue(journey);
                            });
                } else {
                    updateJourney.postValue(new JourneyModel(false, statusHelperDAO.getMessage()));
                }
            });
        } else {
            // Only Perform Db Related Task
            database
                    .getReference(Table_Name)
                    .child(journeyDAO.getJourneyAuthor())
                    .child(JourneyId)
                    .setValue(journeyDAO)
                    .addOnCompleteListener(update -> {
                        JourneyModel journey;
                        if (update.isSuccessful()) {
                            journey = new JourneyModel(true, journeyDAO.getJourneyTitle() + ", Updated Successfully.");
                        } else {
                            journey = new JourneyModel(true, update.getException().getMessage());
                        }
                        journey.setJourneyDAO(journeyDAO);
                        updateJourney.postValue(journey);
                    });
        }
    }

    public void deleteJourney(String JourneyId, JourneyDAO journeyDAO, LifecycleOwner owner) {
        // Step 1 : Check if Image is Empty or Not
        if (journeyDAO.getImageUri() != null) {
            // Step 2: Delete Image If NO Empty First
            storage.DeleteImage(journeyDAO.getImageUri());
            storage.getIsDeleteSuccess().observe(owner, statusHelperDAO -> {
                if (statusHelperDAO.getStatus()) {
                    database
                            .getReference(Table_Name)
                            .child(journeyDAO.getJourneyAuthor())
                            .child(JourneyId)
                            .removeValue().addOnSuccessListener(status -> {
                        deleteJourney.postValue(new StatusHelperDAO(true, "Journey Deleted Successfully"));
                    }).addOnFailureListener(status -> {
                        deleteJourney.postValue(new StatusHelperDAO(false, status.getMessage()));
                    });
                }
            });
        } else {
            // Step 3: Delete Data from Database and return Status Accordingly
            database
                    .getReference(Table_Name)
                    .child(journeyDAO.getJourneyAuthor())
                    .child(JourneyId)
                    .removeValue().addOnSuccessListener(status -> {
                deleteJourney.postValue(new StatusHelperDAO(true, "Journey Deleted Successfully"));
            }).addOnFailureListener(status -> {
                deleteJourney.postValue(new StatusHelperDAO(false, status.getMessage()));
            });
        }
    }

}
