package com.studybuddy.pc.brainmate;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class RegistrationModeView extends ViewModel {
    // Create a LiveData with a boolean
    private MutableLiveData<String> currentName;

    public MutableLiveData<String> getCurrentName() {
        if (currentName == null) {
            currentName = new MutableLiveData<String>();
        }
        return currentName;
    }
}
