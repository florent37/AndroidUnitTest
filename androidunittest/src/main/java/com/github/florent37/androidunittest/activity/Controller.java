package com.github.florent37.androidunittest.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.github.florent37.androidunittest.AndroidUnitTest;

import org.robolectric.util.ActivityController;

/**
 * Created by florentchampigny on 08/05/2016.
 */
public class Controller {

    private AndroidUnitTest androidUnitTest;

    private Controller() {

    }

    public Controller(@NonNull AndroidUnitTest androidUnitTest) {
        this();

        this.androidUnitTest = androidUnitTest;
    }

    @Nullable
    public FragmentActivity get() {
        return (FragmentActivity) androidUnitTest.getActivityController().get();
    }

    @Nullable
    public Controller resume() {
        getActivityController().resume();
        updateActivity();
        return this;
    }

    @Nullable
    public Controller create() {
        getActivityController().create();
        updateActivity();
        return this;
    }

    @Nullable
    public Controller start() {
        getActivityController().start();
        updateActivity();
        return this;
    }

    @Nullable
    public Controller pause() {
        getActivityController().pause();
        updateActivity();
        return this;
    }

    @Nullable
    public Controller stop() {
        getActivityController().stop();
        updateActivity();
        return this;
    }

    @Nullable
    public Controller destroy() {
        getActivityController().destroy();
        updateActivity();
        return this;
    }

    @NonNull
    private ActivityController getActivityController() {
        return androidUnitTest.getActivityController();
    }

    private void updateActivity() {
        androidUnitTest.getAndroidUnitTestAnnotations()
                .updateActivity();
    }
}
