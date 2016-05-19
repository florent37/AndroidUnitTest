package com.github.florent37.androidunittest;

import android.support.annotation.Nullable;

import com.github.florent37.androidunittest.controllers.ControllerActivity;
import com.github.florent37.androidunittest.controllers.ControllerFragment;

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

    ControllerActivity controllerActivity;
    ControllerFragment controllerFragment;
    @Nullable ActivityController activityController;
    private AndroidUnitTestAnnotations androidUnitTestAnnotations;

    protected AndroidUnitTest() {
        androidUnitTestAnnotations = new AndroidUnitTestAnnotations(AndroidUnitTest.this);
        controllerActivity = new ControllerActivity(this);
        controllerFragment = new ControllerFragment(this);
    }

    public static AndroidUnitTest rule() {
        return new AndroidUnitTest();
    }

    @Override
    public Statement apply(final Statement base, FrameworkMethod method, final Object target) {
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

    @Nullable
    public ActivityController getActivityController() {
        return activityController;
    }

    public void setActivityController(@Nullable ActivityController activityController) {
        this.activityController = activityController;
    }

    public AndroidUnitTestAnnotations getAndroidUnitTestAnnotations() {
        return androidUnitTestAnnotations;
    }

    public ControllerActivity activity() {
        return controllerActivity;
    }

    public ControllerFragment fragment() {
        return controllerFragment;
    }

}
