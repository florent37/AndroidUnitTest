package com.github.florent37.androidunittest.activity;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.github.florent37.androidunittest.AndroidUnitTest;

/**
 * Created by florentchampigny on 08/05/2016.
 */
public class ControllerActivity {

    private AndroidUnitTest androidUnitTest;
    public ControllerActivity(AndroidUnitTest androidUnitTest) {
        this.androidUnitTest = androidUnitTest;
    }

    @Nullable
    public FragmentActivity get(){
        if(androidUnitTest.getActivityController() != null) {
            return (FragmentActivity) androidUnitTest.getActivityController().get();
        }
        return null;
    }

    @Nullable
    public ControllerActivity resume(){
        if(androidUnitTest.getActivityController() != null) {
            androidUnitTest.getActivityController().resume();
            androidUnitTest.getAndroidUnitTestAnnotations().updateActivity();
        }
        return this;
    }

    @Nullable
    public ControllerActivity create(){
        if(androidUnitTest.getActivityController() != null) {
            androidUnitTest.getActivityController().create();
            androidUnitTest.getAndroidUnitTestAnnotations().updateActivity();
        }
        return this;
    }

    @Nullable
    public ControllerActivity start(){
        if(androidUnitTest.getActivityController() != null) {
            androidUnitTest.getActivityController().start();
            androidUnitTest.getAndroidUnitTestAnnotations().updateActivity();
        }
        return this;
    }

    @Nullable
    public ControllerActivity pause(){
        if(androidUnitTest.getActivityController() != null) {
            androidUnitTest.getActivityController().pause();
            androidUnitTest.getAndroidUnitTestAnnotations().updateActivity();
        }
        return this;
    }

    @Nullable
    public ControllerActivity stop(){
        if(androidUnitTest.getActivityController() != null) {
            androidUnitTest.getActivityController().stop();
            androidUnitTest.getAndroidUnitTestAnnotations().updateActivity();
        }
        return this;
    }

    @Nullable
    public ControllerActivity destroy(){
        if(androidUnitTest.getActivityController() != null) {
            androidUnitTest.getActivityController().destroy();
            androidUnitTest.getAndroidUnitTestAnnotations().updateActivity();
        }
        return this;
    }
}
