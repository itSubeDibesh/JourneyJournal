package com.ismt.dibeshrajsubedi.journeyjournal.repository.firebase;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.helper.StatusHelperDAO;

import java.io.ByteArrayOutputStream;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.repository.firebase was
 * Created by Dibesh Raj Subedi on 3/21/2022.
 */
public class FirebaseStorageImpl {
    private final String TAG = "JJ_" + FirebaseStorageImpl.class.getSimpleName();
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private final StorageReference reference = storage.getReference();
    private final MutableLiveData<StatusHelperDAO> isUploadSuccess = new MutableLiveData<>();
    private final MutableLiveData<StatusHelperDAO> isDeleteSuccess = new MutableLiveData<>();

    public MutableLiveData<StatusHelperDAO> getIsUploadSuccess() {
        return isUploadSuccess;
    }

    public MutableLiveData<StatusHelperDAO> getIsDeleteSuccess() {
        return isDeleteSuccess;
    }

    public void UploadBitMap(Bitmap image, String filePath, String fileName, Context context) {
        if (image != null && fileName != null && filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle(R.string.label_uploading);
            progressDialog.setIcon(R.drawable.ic_launcher_foreground);
            progressDialog.show();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            StorageReference ref = reference.child(filePath + fileName);
            ref.putBytes(data)
                    .addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().addOnSuccessListener(uri -> {
                        progressDialog.dismiss();
                        isUploadSuccess.postValue(new StatusHelperDAO(true, uri.toString()));
                    }))
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

    public void UploadImage(Uri image, String filePath, String fileName, Context context) {
        if (image != null && fileName != null && filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle(R.string.label_uploading);
            progressDialog.setIcon(R.drawable.ic_launcher_foreground);
            progressDialog.show();
            StorageReference ref = reference.child(filePath + fileName);
            ref.putFile(image)
                    .addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().addOnSuccessListener(uri -> {
                        progressDialog.dismiss();
                        isUploadSuccess.postValue(new StatusHelperDAO(true, uri.toString()));
                    }))
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

    public void DeleteImage(String url) {
        if (url != null) {
            storage.getReferenceFromUrl(url).delete().addOnSuccessListener(task -> {
                isDeleteSuccess.postValue(new StatusHelperDAO(true, "Delete Successfully"));
            }).addOnFailureListener(task -> {
                isDeleteSuccess.postValue(new StatusHelperDAO(false, task.getMessage()));
            });
        }
    }
}
