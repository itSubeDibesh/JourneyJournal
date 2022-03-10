package com.ismt.dibeshrajsubedi.journeyjournal.views.components.JourneyMockup;

import android.annotation.SuppressLint;

import com.ismt.dibeshrajsubedi.journeyjournal.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Project JourneyJournal with package com.ismt.dibeshrajsubedi.journeyjournal.views.components was
 * Created by Dibesh Raj Subedi on 3/9/2022.
 */
public class JourneyHandler extends JourneyDataset {
    /**
     * Returns Date
     *
     * @return String
     */
    private String getDate() {
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        return formatter.format(date);
    }

    /**
     * Sets Journey For Initial Dataset
     */
    private void setJourney() {
        journey.add(new JourneyModule("Back in Kathmandu", lorem, "Kathmandu", this.getDate(), imageHolder));
        journey.add(new JourneyModule("World is Heaven", lorem, "China", this.getDate(), imageHolder));
        journey.add(new JourneyModule("Something is Good.", lorem, "Maldives", this.getDate(), R.drawable.ic_img_landing));
        journey.add(new JourneyModule("Best of All Times.", lorem, "Alabama", this.getDate(), imageHolder));
        journey.add(new JourneyModule("Thinking About You.", lorem, "Sri-Lanka", this.getDate(), R.drawable.ic_img_city));
        journey.add(new JourneyModule("Back to future.", lorem, "America", this.getDate(), R.drawable.ic_img_register));
    }

    /**
     * Returns Array List of Journey Module
     *
     * @return ArrayList<JourneyModule>
     */
    public ArrayList<JourneyModule> journeyList() {
        return journey;
    }


    /**
     * Adds Journey to Memory
     *
     * @param journeyModule JourneyModule
     * @return boolean
     */
    public boolean addJourney(JourneyModule journeyModule) {
        if (journeyModule == null) return false;
        this.journey.add(journeyModule);
        return true;
    }

    /**
     * Updates Journey
     *
     * @param index         int
     * @param journeyModule JourneyModule
     * @return boolean
     */
    public boolean updateJourney(int index, JourneyModule journeyModule) {
        if (journeyModule == null || index == 0) return false;
        this.journey.set(index, journeyModule);
        return true;
    }

    /**
     * Returns JourneyModule
     *
     * @param index int
     * @return JourneyModule
     */
    public JourneyModule findJourney(int index) {
        return this.journey.get(index);
    }

    /**
     * Removes Journey From List
     *
     * @param index int
     * @return boolean
     */
    public boolean deleteJourney(int index) {
        if (index == 0) return false;
        this.journey.remove(index);
        return true;
    }

    /**
     * Returns Length of Journey List
     *
     * @return int
     */
    public int getLength() {
        return this.journey.size();
    }

    public JourneyHandler() {
        // Initializing Journey DataSet
        this.setJourney();
    }
}
