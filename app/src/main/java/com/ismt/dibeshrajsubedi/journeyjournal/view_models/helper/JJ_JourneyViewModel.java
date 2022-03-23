package com.ismt.dibeshrajsubedi.journeyjournal.view_models.helper;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.helper.StatusHelperDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.helper.InputValidationHelper;
import com.ismt.dibeshrajsubedi.journeyjournal.repository.firebase.FirebaseDatabaseImpl;
import com.ismt.dibeshrajsubedi.journeyjournal.repository.firebase.FirebaseStorageImpl;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.view_models.helper was
 * Created by Dibesh Raj Subedi on 3/23/2022.
 */
public class JJ_JourneyViewModel extends AndroidViewModel {
    protected final FirebaseDatabaseImpl database = new FirebaseDatabaseImpl();
    protected final FirebaseStorageImpl storage = new FirebaseStorageImpl();
    private final InputValidationHelper validationHelper = new InputValidationHelper();
    private final MutableLiveData<StatusHelperDAO> isTitleInvalid = new MutableLiveData<>();
    private final MutableLiveData<StatusHelperDAO> isDescriptionInvalid = new MutableLiveData<>();

    public JJ_JourneyViewModel(@NonNull Application application) {
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

    public MutableLiveData<StatusHelperDAO> getIsTitleInvalid() {
        return isTitleInvalid;
    }

    public MutableLiveData<StatusHelperDAO> getIsDescriptionInvalid() {
        return isDescriptionInvalid;
    }

    /**
     * Validates Input and sets isTitleInvalid MutableLiveData
     *
     * @param Title String
     */
    protected void TitleInvalid(String Title) {
        // Email Validation Checks - [Empty Check]
        if (validationHelper.isNullOrEmpty(Title)) {
            isTitleInvalid.setValue(new StatusHelperDAO(true, getString(R.string.error_empty_title)));
            return;
        } else isTitleInvalid.setValue(new StatusHelperDAO(false));
    }

    /**
     * Validates Input and sets isDescriptionInvalid MutableLiveData
     *
     * @param Description String
     */
    protected void DescriptionInvalid(String Description) {
        // Email Validation Checks - [Empty Check]
        if (validationHelper.isNullOrEmpty(Description)) {
            isDescriptionInvalid.setValue(new StatusHelperDAO(true, getString(R.string.error_empty_description)));
            return;
        } else isDescriptionInvalid.setValue(new StatusHelperDAO(false));
    }
}
