package com.github.florent37.androidunittest.activity;

import android.support.annotation.Nullable;

import com.github.florent37.androidunittest.AndroidUnitTest;

import org.robolectric.util.ActivityController;

/**
 * Created by florentchampigny on 08/05/2016.
 */
public class Controller {
    private AndroidUnitTest androidUnitTest;
    public Controller(AndroidUnitTest androidUnitTest) {
        this.androidUnitTest = androidUnitTest;
    }

    @Nullable
    public Controller resume(){
        if(androidUnitTest.getActivityController() != null) {
            androidUnitTest.getActivityController().resume();
            androidUnitTest.getRobolectricAnnotations().updateActivity();
        }
        return this;
    }

    @Nullable
    public Controller create(){
        if(androidUnitTest.getActivityController() != null) {
            androidUnitTest.getActivityController().create();
            androidUnitTest.getRobolectricAnnotations().updateActivity();
        }
        return this;
    }

    @Nullable
    public Controller start(){
        if(androidUnitTest.getActivityController() != null) {
            androidUnitTest.getActivityController().start();
            androidUnitTest.getRobolectricAnnotations().updateActivity();
        }
        return this;
    }

    @Nullable
    public Controller pause(){
        if(androidUnitTest.getActivityController() != null) {
            androidUnitTest.getActivityController().pause();
            androidUnitTest.getRobolectricAnnotations().updateActivity();
        }
        return this;
    }

    @Nullable
    public Controller stop(){
        if(androidUnitTest.getActivityController() != null) {
            androidUnitTest.getActivityController().stop();
            androidUnitTest.getRobolectricAnnotations().updateActivity();
        }
        return this;
    }

    @Nullable
    public Controller destroy(){
        if(androidUnitTest.getActivityController() != null) {
            androidUnitTest.getActivityController().destroy();
            androidUnitTest.getRobolectricAnnotations().updateActivity();
        }
        return this;
    }
}
