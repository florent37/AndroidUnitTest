package com.github.florent37.androidunittest;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.florent37.androidunittest.activity.Controller;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.util.ActivityController;

/**
 * Created by florentchampigny on 07/05/2016.
 */
public class AndroidUnitTest implements MethodRule {
    private final static String INVALID_CONTROLLER_INITIALISATION = "Invalid initialisation, setActivityController not called";

    public static AndroidUnitTest rule() {
        return new AndroidUnitTest();
    }

    Controller controller;
    @Nullable  ActivityController activityController;
    private AndroidUnitTestAnnotations androidUnitTestAnnotations;

    private AndroidUnitTest() {
        androidUnitTestAnnotations = new AndroidUnitTestAnnotations(this);
        controller = new Controller(this);
    }

    @Override
    public Statement apply(@NonNull final Statement base,
                           @NonNull FrameworkMethod method,
                           @NonNull final Object target) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                MockitoAnnotations.initMocks(target);
                androidUnitTestAnnotations.init(target);
                base.evaluate();
                Mockito.validateMockitoUsage();
            }
        };
    }

    @NonNull
    public ActivityController getActivityController() {
        if(activityController == null)
            throw new IllegalStateException(INVALID_CONTROLLER_INITIALISATION);
        return activityController;
    }

    @NonNull
    public AndroidUnitTestAnnotations getAndroidUnitTestAnnotations() {
        return androidUnitTestAnnotations;
    }

    public void setActivityController(@NonNull ActivityController activityController) {
        this.activityController = activityController;
    }

    @NonNull
    public Controller activity(){
        return controller;
    }
}
