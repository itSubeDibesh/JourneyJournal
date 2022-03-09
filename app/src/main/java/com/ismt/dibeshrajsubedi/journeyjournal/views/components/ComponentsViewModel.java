package com.ismt.dibeshrajsubedi.journeyjournal.views.components;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.AndroidViewModel;

import com.ismt.dibeshrajsubedi.journeyjournal.R;
import com.ismt.dibeshrajsubedi.journeyjournal.views.activities.authentication.AuthenticationActivity;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.views.components was
 * Created by Dibesh Raj Subedi on 3/7/2022.
 */
public class ComponentsViewModel extends AndroidViewModel {

    /**
     * Enum for Confirmation
     */
    private enum Confirmation {
        EXIT,
        LOGOUT,
        DELETE
    }

    /**
     * Enum for Container Fragment
     */
    private enum FragmentContainer {
        HOME,
        ADD_EDIT
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
                        .setPositiveButton(R.string.option_yes, (dialog, id) -> activity.startActivity(new Intent(activity.getApplicationContext(), AuthenticationActivity.class)))
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

    public ComponentsViewModel(@NonNull Application application) {
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
     * Loads Up The Fragment on Frame Layout
     *
     * @param supportFragmentManager FragmentManager
     * @param fragment               Fragment
     * @param fragmentContainer      FragmentContainer
     * @return boolean
     */
    private boolean loadFragment(FragmentManager supportFragmentManager, Fragment fragment, FragmentContainer fragmentContainer) {
        if (fragment != null) {
            switch (fragmentContainer) {
                case HOME:
                    supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.home_frame_layout_container, fragment)
                            .commit();
                    break;
                case ADD_EDIT:
                    supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.add_edit_journey_frame_layout_container, fragment)
                            .commit();
                    break;
            }
            return true;
        }
        return false;
    }

    /**
     * Loads Home Fragment Container
     *
     * @param supportFragmentManager FragmentManager
     * @param fragment               Fragment
     * @return boolean
     */
    public boolean loadHomeContainerFragment(FragmentManager supportFragmentManager, Fragment fragment) {
        return this.loadFragment(supportFragmentManager, fragment, FragmentContainer.HOME);
    }


    /**
     * Loads AddEdit Fragment Container
     *
     * @param supportFragmentManager FragmentManager
     * @param fragment               Fragment
     * @return boolean
     */
    public boolean loadJourneyAddEditContainerFragment(FragmentManager supportFragmentManager, Fragment fragment) {
        return this.loadFragment(supportFragmentManager, fragment, FragmentContainer.ADD_EDIT);
    }


}
