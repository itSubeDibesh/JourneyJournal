package com.ismt.dibeshrajsubedi.journeyjournal.views.components;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.AndroidViewModel;

import com.ismt.dibeshrajsubedi.journeyjournal.R;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.views.components was
 * Created by Dibesh Raj Subedi on 3/7/2022.
 */
public class ComponentsViewModel extends AndroidViewModel {

    public ComponentsViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Application Exit Confirmation
     * Alert Dialogue Builder Implementation
     *
     * @param activity Activity
     */
    public void exitConfirmation(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.confirmation_exit);
        builder.setIcon(R.drawable.ic_launcher_foreground);
        builder
                .setMessage(R.string.consent_exit).setCancelable(false)
                .setPositiveButton(R.string.option_yes, (dialog, id) -> activity.finish())
                .setNegativeButton(R.string.option_no, (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }
}
