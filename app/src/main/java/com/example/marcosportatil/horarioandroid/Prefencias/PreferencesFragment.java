package com.example.marcosportatil.horarioandroid.Prefencias;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.marcosportatil.horarioandroid.R;

/**
 * Created by MarcosPortatil on 13/01/2017.
 */

public class PreferencesFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}