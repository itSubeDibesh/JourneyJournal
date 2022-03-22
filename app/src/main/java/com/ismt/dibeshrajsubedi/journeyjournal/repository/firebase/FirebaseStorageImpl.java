package com.ismt.dibeshrajsubedi.journeyjournal.repository.firebase;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.helper.StatusHelperDAO;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.repository.firebase was
 * Created by Dibesh Raj Subedi on 3/21/2022.
 */
public class FirebaseStorageImpl {
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private final StorageReference reference = storage.getReference();
    private final MutableLiveData<StatusHelperDAO> isUploadSuccess = new MutableLiveData<>();

    public MutableLiveData<StatusHelperDAO> getIsUploadSuccess() {
        return isUploadSuccess;
    }

    public FirebaseStorage getStorage() {
        return storage;
    }

    public StorageReference getReference() {
        return reference;
    }

    public void UploadImage(Uri image, String filePath, String fileName, Context context) {
        if (image != null && fileName != null && filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle(R.string.label_uploading);
            progressDialog.setIcon(R.drawable.ic_launcher_foreground);
            progressDialog.show();
            StorageReference ref = reference.child(filePath + fileName);
            ref.putFile(image)
                    .addOnSuccessListener(taskSnapshot -> {
                        progressDialog.dismiss();
                        isUploadSuccess.postValue(new StatusHelperDAO(true));
                    })
                    .addOnFailureListener(failure -> {
                        progressDialog.dismiss();
                        isUploadSuccess.postValue(new StatusHelperDAO(false, failure.getMessage()));
                    })
                    .addOnProgressListener(task -> {
                        double progress = (100.0 * task.getBytesTransferred() / task.getTotalByteCount());
                        progressDialog.setMessage("Uploaded " + (int) progress + "%");
                    });

        }
    }
}
