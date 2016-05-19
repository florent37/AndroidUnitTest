package com.github.florent37.androidunittest.controllers;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.github.florent37.androidunittest.AndroidUnitTest;
import com.github.florent37.androidunittest.states.ActivityState;

import org.robolectric.util.ActivityController;

/**
 * Created by florentchampigny on 08/05/2016.
 */
public class ControllerActivity {

    private AndroidUnitTest androidUnitTest;

    public ControllerActivity(AndroidUnitTest androidUnitTest) {
        this.androidUnitTest = androidUnitTest;
    }

    @Nullable
    public FragmentActivity get() {
        if (androidUnitTest.getActivityController() != null) {
            return (FragmentActivity) androidUnitTest.getActivityController().get();
        }
        return null;
    }

    @Nullable
    public ActivityController getActivityController() {
        if (androidUnitTest.getActivityController() != null) {
            return androidUnitTest.getActivityController();
        }
        return null;
    }

    public ControllerActivity resume() {
        if (androidUnitTest.getActivityController() != null) {
            androidUnitTest.getActivityController().resume();
            androidUnitTest.getAndroidUnitTestAnnotations().updateActivity();
        }
        return this;
    }

    public ControllerActivity create() {
        if (androidUnitTest.getActivityController() != null) {
            androidUnitTest.getActivityController().create();
            androidUnitTest.getAndroidUnitTestAnnotations().updateActivity();
        }
        return this;
    }

    public ControllerActivity start() {
        if (androidUnitTest.getActivityController() != null) {
            androidUnitTest.getActivityController().start();
            androidUnitTest.getAndroidUnitTestAnnotations().updateActivity();
        }
        return this;
    }

    public ControllerActivity pause() {
        if (androidUnitTest.getActivityController() != null) {
            androidUnitTest.getActivityController().pause();
            androidUnitTest.getAndroidUnitTestAnnotations().updateActivity();
        }
        return this;
    }

    public ControllerActivity stop() {
        if (androidUnitTest.getActivityController() != null) {
            androidUnitTest.getActivityController().stop();
            androidUnitTest.getAndroidUnitTestAnnotations().updateActivity();
        }
        return this;
    }

    public ControllerActivity destroy() {
        if (androidUnitTest.getActivityController() != null) {
            androidUnitTest.getActivityController().destroy();
            androidUnitTest.getAndroidUnitTestAnnotations().updateActivity();
        }
        return this;
    }

    public ControllerActivity setActivityState(ActivityState activityState) {
        if (androidUnitTest.getActivityController() != null) {
            setActivityState(getActivityController(), activityState);
        }
        return this;
    }

    public ControllerActivity setActivityState(ActivityController activityController, ActivityState activityState) {
        ActivityState init = ActivityState.CREATED;

        while (init != null && init.isLowerOrEquals(activityState)) {
            System.out.println(init);
            applyState(init, activityController);
            init = init.next();
        }

        return this;
    }

    private void applyState(ActivityState state,
                            ActivityController controller) {
        switch (state) {
            case STARTED:
                controller.start();
                break;
            case RESUMED:
                controller.resume();
                break;
            case PAUSED:
                controller.pause();
                break;
            case STOPPED:
                controller.stop();
                break;
            case DESTROYED:
                controller.destroy();
                break;
            case CREATED:
            default:
                controller.create();
                break;
        }
    }

}
