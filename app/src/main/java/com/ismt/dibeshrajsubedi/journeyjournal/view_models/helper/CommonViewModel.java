package com.ismt.dibeshrajsubedi.journeyjournal.view_models.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.material.textfield.TextInputLayout;
import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.helper.ConnectivityHelperDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.dao.helper.StatusHelperDAO;
import com.ismt.dibeshrajsubedi.journeyjournal.repository.firebase.FirebaseAuthImpl;
import com.ismt.dibeshrajsubedi.journeyjournal.repository.firebase.FirebaseDatabaseImpl;
import com.ismt.dibeshrajsubedi.journeyjournal.repository.firebase.FirebaseStorageImpl;
import com.ismt.dibeshrajsubedi.journeyjournal.views.activities.AuthenticationActivity;
import com.ismt.dibeshrajsubedi.journeyjournal.views.activities.HomeActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.view_models.helper was
 * Created by Dibesh Raj Subedi on 3/7/2022.
 */
public class CommonViewModel extends AndroidViewModel {
    private final String TAG = "JJ_CommonViewModel";
    private final FirebaseAuthImpl firebaseAuth = new FirebaseAuthImpl();
    private final FirebaseStorageImpl storage = new FirebaseStorageImpl();
    private final FirebaseDatabaseImpl database = new FirebaseDatabaseImpl();

    public CommonViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Pops out Confirmation using Alert Dialogue
     *
     * @param activity     Activity
     * @param title        int
     * @param message      int
     * @param confirmation Confirmation
     * @param owner        LifecycleOwner
     */
    private void confirmation(Activity activity, int title, int message, Confirmation confirmation, LifecycleOwner owner) {
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
                        .setPositiveButton(R.string.option_yes, (dialog, id) -> logout(owner, activity))
                        .setNegativeButton(R.string.option_no, (dialog, id) -> dialog.cancel());
            case GOOGLE:
                builder.setMessage(message).setCancelable(true)
                        .setNegativeButton(R.string.option_ok, (dialog, id) -> dialog.dismiss());
            case TWITTER:
                builder.setMessage(message).setCancelable(true)
                        .setNegativeButton(R.string.option_ok, (dialog, id) -> dialog.dismiss());
                break;
        }
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Logs User Out
     *
     * @param owner    LifecycleOwner
     * @param activity Activity
     */
    public void logout(LifecycleOwner owner, Activity activity) {
        firebaseAuth.logOut();
        this.observeLogout(owner, activity);
    }

    /**
     * Application Exit Confirmation
     * Alert Dialogue Builder Implementation
     *
     * @param activity Activity
     * @param owner    LifecycleOwner
     */
    public void exitConfirmation(Activity activity, LifecycleOwner owner) {
        this.confirmation(activity, R.string.confirmation_exit, R.string.consent_exit, Confirmation.EXIT, owner);
    }

    /**
     * Dialog Message Working on It Twitter
     * Alert Dialogue Builder Implementation
     *
     * @param activity Activity
     * @param owner    LifecycleOwner
     */
    public void twitterAuth(Activity activity, LifecycleOwner owner) {
        this.confirmation(activity, R.string.confirmation_working_on_it, R.string.message_working_on_twitter, Confirmation.TWITTER, owner);
    }

    /**
     * Dialog Message Working on It Google
     * Alert Dialogue Builder Implementation
     *
     * @param activity Activity
     * @param owner    LifecycleOwner
     */
    public void googleAuth(Activity activity, LifecycleOwner owner) {
        this.confirmation(activity, R.string.confirmation_working_on_it, R.string.message_working_on_google, Confirmation.GOOGLE, owner);
    }


    /**
     * Checks if User Login Details is Cached and Act Accordingly
     *
     * @param activity Activity
     * @param owner    LifecycleOwner
     */
    public void cacheLoggedIn(Activity activity, LifecycleOwner owner) {
        firebaseAuth.getUserLoggedMutableLiveData().observe(owner, loginModel -> {
            Log.d(TAG, "cacheLoggedIn: login cached received email as " + loginModel.getFirebaseUser().getEmail() + " and Image as " + loginModel.getFirebaseUser().getPhotoUrl());
            if (loginModel.getFirebaseUser() != null) {
                Intent intent = new Intent(activity.getApplicationContext(), HomeActivity.class);
                intent.putExtra("USER", loginModel.getFirebaseUser());
                intent.putExtra("PROFILE", loginModel.getRegisterDetailsDAO());
                if (loginModel.getFirebaseUser().getPhotoUrl() != null) {
                    intent.putExtra("ImageURI", loginModel.getFirebaseUser().getPhotoUrl().toString());
                }
                activity.startActivity(intent);
                activity.finish();
            }
        });
    }

    /**
     * Application Logout Confirmation
     * Alert Dialogue Builder Implementation
     *
     * @param activity Activity
     * @param owner    LifecycleOwner
     */
    public void logoutConfirmation(Activity activity, LifecycleOwner owner) {
        this.confirmation(activity, R.string.confirmation_logout, R.string.consent_logout, Confirmation.LOGOUT, owner);
    }

    /**
     * Observes Logout and triggers accordingly
     *
     * @param owner    LifecycleOwner
     * @param activity Activity
     */
    public void observeLogout(LifecycleOwner owner, Activity activity) {
        firebaseAuth.getUserLoggedIn().observe(owner, UserLoggedIn -> {
            if (!UserLoggedIn) {
                activity.startActivity(new Intent(activity.getApplicationContext(), AuthenticationActivity.class));
                activity.finish();
            }
        });
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
        return Objects.requireNonNull(Objects.requireNonNull(input.getEditText()).getText()).toString();
    }

    /**
     * Sets Observable Error
     *
     * @param input  TextInputLayout
     * @param helper StatusHelperDAO
     */
    public void setObserverError(TextInputLayout input, StatusHelperDAO helper) {
        if (helper.getStatus()) {
            input.setError(helper.getMessage());
            input.requestFocus();
        } else input.setError(null);
    }

    /**
     * Checks if Internet is Connected and returns relevant details
     *
     * @param context Context
     * @return ConnectivityHelperDAO
     */
    public ConnectivityHelperDAO checkInternetConnection(Context context) {
        // Step 0: Initialize Basic Data Variables
        boolean status = false;
        String message = null;
        // Step 0: Start Try Catch Block
        try {
            // Step 1: Get Connectivity Manager
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // Step 2: Get network Information
            @SuppressLint("MissingPermission") NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
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
        return new ConnectivityHelperDAO(status, message);
    }

    /**
     * Returns Date in dd/MM/yyyy format
     *
     * @return String
     */
    public String getDateString() {
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }


    /**
     * Enum for Confirmation
     */
    private enum Confirmation {
        EXIT,
        LOGOUT,
        GOOGLE,
        TWITTER
    }
}
