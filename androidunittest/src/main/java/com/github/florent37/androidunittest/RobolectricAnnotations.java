package com.github.florent37.androidunittest;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.github.florent37.androidunittest.annotations.MActivity;
import com.github.florent37.androidunittest.annotations.MContext;
import com.github.florent37.androidunittest.annotations.MFragment;

import org.mockito.Mockito;
import org.mockito.internal.util.reflection.FieldReader;
import org.mockito.internal.util.reflection.FieldSetter;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.util.ActivityController;

import java.lang.reflect.Field;

/**
 * Created by florentchampigny on 07/05/2016.
 */
public class RobolectricAnnotations {
    final AndroidUnitTest androidUnitTest;
    Object target;
    Field activityField;
    Field fragmentField;
    Field contextField;
    public RobolectricAnnotations(AndroidUnitTest androidUnitTest) {
        this.androidUnitTest = androidUnitTest;
    }

    public RobolectricAnnotations init(Object target) {
        //find @Activity
        //find @Fragment
        //find @MContext -> RuntimeEnvironment.application;
        this.target = target;
        scan();
        execute();
        return this;
    }

    public void initContext() {
        if (this.contextField != null) {
            Context appContext = RuntimeEnvironment.application;
            appContext = Mockito.spy(appContext);
            new FieldSetter(target, this.contextField).set(appContext);
        }
    }

    public void initActivity() {
        if (this.activityField != null) {
            Class activityClass = this.activityField.getType();
            MActivity activityAnnotation = this.activityField.getAnnotation(MActivity.class);
            createActivity(activityClass, activityAnnotation.create());
        }
    }

    public void updateActivity() {
        new FieldSetter(target, this.activityField).set(androidUnitTest.getActivityController().get());
    }

    public void initFragment() {
        if (this.fragmentField != null) {
            Class fragmentClass = this.fragmentField.getType();
            Fragment fragment = null;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            fragment = Mockito.spy(fragment);

            new FieldSetter(target, this.activityField).set(fragment);
            if (this.activityField == null) {
                createActivity(FragmentActivity.class, true);
            }

            FragmentActivity fragmentActivity = (FragmentActivity) new FieldReader(target, this.activityField).read();
            fragmentActivity.getSupportFragmentManager().beginTransaction()
                .add(fragment, fragment.getClass().toString())
                .commit();
        }
    }

    private void execute() {
        initContext();
        initActivity();
        initFragment();
    }

    private void scan() {
        for (Field field : target.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(MActivity.class)) {
                activityField = field;
            }
            if (field.isAnnotationPresent(MFragment.class)) {
                fragmentField = field;
            }
            if (field.isAnnotationPresent(MContext.class)) {
                contextField = field;
            }
        }
    }

    private void createActivity(Class activityClass, boolean create) {
        ActivityController activityController = ActivityController.of(Robolectric.getShadowsAdapter(), activityClass);
        if(create)
            activityController.create();
        androidUnitTest.setActivityController(activityController);
        FragmentActivity activity = (FragmentActivity) activityController.get();
        activity = Mockito.spy(activity);
        new FieldSetter(target, this.activityField).set(activity);
    }
}
