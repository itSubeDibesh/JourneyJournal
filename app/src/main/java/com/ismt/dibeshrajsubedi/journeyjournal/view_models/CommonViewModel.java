package com.ismt.dibeshrajsubedi.journeyjournal.view_models;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.AndroidViewModel;

import com.google.android.material.textfield.TextInputLayout;
import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.models.helper.MConnectivityHelper;
import com.ismt.dibeshrajsubedi.journeyjournal.models.helper.StatusHelperModel;
import com.ismt.dibeshrajsubedi.journeyjournal.views.activities.authentication.AuthenticationActivity;

import java.util.Objects;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.view_models.helper was
 * Created by Dibesh Raj Subedi on 3/7/2022.
 */
public class CommonViewModel extends AndroidViewModel {

    /**
     * Enum for Confirmation
     */
    private enum Confirmation {
        EXIT,
        LOGOUT,
        DELETE
    }

    /**
     * Pops out Confirmation using Alert Dialogue
     *
     * @param activity     Activity
     * @param title        int
     * @param message      int
     * @param confirmation Confirmation
     */
    private void confirmation(Activity activity, int title, int message, Confirmation confirmation) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setIcon(R.drawable.ic_launcher_foreground);
        switch (confirmation) {
            case EXIT:
                builder
                        .setMessage(message).setCancelable(true)
                        .setPositiveButton(R.string.option_yes, (dialog, id) -> activity.finish())
                        .setNegativeButton(R.string.option_no, (dialog, id) -> dialog.cancel());
                break;
            case LOGOUT:
                builder
                        .setMessage(message).setCancelable(true)
                        .setPositiveButton(R.string.option_yes, (dialog, id) -> {
                            activity.startActivity(new Intent(activity.getApplicationContext(), AuthenticationActivity.class));
                            activity.finish();
                        })
                        .setNegativeButton(R.string.option_no, (dialog, id) -> dialog.cancel());
                break;
            case DELETE:
                builder
                        .setMessage(message).setCancelable(true)
                        .setPositiveButton(R.string.option_yes, (dialog, id) -> dialog.dismiss())
                        .setNegativeButton(R.string.option_no, (dialog, id) -> dialog.cancel());
                break;
        }
        AlertDialog alert = builder.create();
        alert.show();
    }

    public CommonViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Application Journey Delete Confirmation
     * Alert Dialogue Builder Implementation
     *
     * @param activity Activity
     */
    public void deleteConfirmation(Activity activity) {
        // TODO -> Take Delete Details and trigger Deleter From Here Itself
        this.confirmation(activity, R.string.confirmation_delete, R.string.consent_delete_journey, Confirmation.DELETE);
    }

    /**
     * Application Exit Confirmation
     * Alert Dialogue Builder Implementation
     *
     * @param activity Activity
     */
    public void exitConfirmation(Activity activity) {
        this.confirmation(activity, R.string.confirmation_exit, R.string.consent_exit, Confirmation.EXIT);
    }

    /**
     * Application Logout Confirmation
     * Alert Dialogue Builder Implementation
     *
     * @param activity Activity
     */
    public void logoutConfirmation(Activity activity) {
        this.confirmation(activity, R.string.confirmation_logout, R.string.consent_logout, Confirmation.LOGOUT);
    }

    /**
     * Shows or Hides Component based on condition passed
     *
     * @param component View
     * @param condition boolean
     */
    public void setVisibility(View component, boolean condition) {
        // If Condition is true Show it Else Hide it
        component.setVisibility(condition ? View.VISIBLE : View.GONE);

    }

    /**
     * Show recyclerView on false Condition and Show Empty Card View on True Condition
     *
     * @param view            View
     * @param truthyCondition boolean
     */
    public void recyclerViewVisibility(View view, boolean truthyCondition) {
        // Hide Card on True Condition
        this.setVisibility(view.findViewById(R.id.cv_emptyCard), truthyCondition);
        // Show Refresh Layout on False Condition
        this.setVisibility(view.findViewById(R.id.srl_refresh_list), !truthyCondition);
    }

    /**
     * Extract String From TextInputLayout
     *
     * @param input TextInputLayout
     * @return String
     */
    public String til(TextInputLayout input) {
        return Objects.requireNonNull(input.getEditText().getText()).toString();
    }

    /**
     * Sets Observable Error
     *
     * @param input  TextInputLayout
     * @param helper StatusHelperModel
     */
    public void setObserverError(TextInputLayout input, StatusHelperModel helper) {
        if (helper.getStatus()) {
            input.setError(helper.getMessage());
            input.requestFocus();
        } else input.setError(null);
    }

    /**
     * Checks if Internet is Connected and returns relevant details
     *
     * @param context Context
     * @return MConnectivityHelper
     */
    public MConnectivityHelper checkInternetConnection(Context context) {
        // Step 0: Initialize Basic Data Variables
        boolean status = false;
        String message = null;
        // Step 0: Start Try Catch Block
        try {
            // Step 1: Get Connectivity Manager
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // Step 2: Get network Information
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            // Step 3: Conditional Checks regarding Network Information
            if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    status = true;
                    message = context.getString(R.string.connection_wifi);
                } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    status = true;
                    message = context.getString(R.string.connection_data);
                }
            } else {
                message = context.getString(R.string.connection_offline);
            }
        } catch (Exception e) {
            status = false;
            message = e.getMessage();
        }
        // Step 4: Implement Helper to Pass Data and return
        return new MConnectivityHelper(status, message);
    }
}
