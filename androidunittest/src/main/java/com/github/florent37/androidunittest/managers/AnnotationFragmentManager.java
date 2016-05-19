package com.github.florent37.androidunittest.managers;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.github.florent37.androidunittest.AndroidUnitTest;
import com.github.florent37.androidunittest.annotations.RFragment;
import com.github.florent37.androidunittest.controllers.ControllerActivity;
import com.github.florent37.androidunittest.states.ActivityState;

import org.mockito.Mockito;
import org.mockito.internal.util.reflection.FieldReader;
import org.mockito.internal.util.reflection.FieldSetter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;

/**
 * Created by kevinleperf on 18/05/16.
 */
public class AnnotationFragmentManager extends AbstractAnnotationManager {
    @VisibleForTesting final HashSet<Field> fragmentFields;

    public AnnotationFragmentManager(AndroidUnitTest parent) {
        super(parent);

        fragmentFields = new HashSet<>();
    }

    public static void addToActivity(Activity activity, Fragment fragment, String tag) {
        if (activity instanceof FragmentActivity) {
            FragmentActivity fragmentActivity = (FragmentActivity) activity;
            fragmentActivity.getSupportFragmentManager().beginTransaction()
                .add(fragment, tag)
                .commit();
        }
    }

    public static void removeFromActivity(Activity activity, Fragment fragment) {
        if (activity instanceof FragmentActivity) {
            FragmentActivity fragmentActivity = (FragmentActivity) activity;
            fragmentActivity.getSupportFragmentManager().beginTransaction()
                .remove(fragment)
                .commit();
        }
    }

    @Override
    public void scanned(@NonNull Field field) {
        fragmentFields.add(field);
    }

    @Override
    public void execute(@NonNull Object object, @NonNull Context context) {
        for (Field fragment : fragmentFields) {
            initFragment(object, fragment);
        }
    }

    public void initFragment(@NonNull Object target, @NonNull Field fragmentField) {
        RFragment fragmentAnnotation = fragmentField.getAnnotation(RFragment.class);

        Class fragmentClass = fragmentField.getType();
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Impossible to instantiate a fragment using the default constructor");
        }

        fragment = Mockito.spy(fragment);
        new FieldSetter(target, fragmentField).set(fragment);

        //if no activity is create, the default activity manager behaviour is to create one
        /*if (this.activityField == null) {
            createActivity(FragmentActivity.class, null);
            androidUnitTest.activity().setActivityState(ActivityState.CREATED);
        }*/

        if (fragmentAnnotation.attached()) {
            String tag = fragmentAnnotation.tag();
            if (tag == null || tag.isEmpty()) {
                tag = fragment.getClass().toString();
            }
            addToActivity(getActivity(), fragment, tag);
        }
    }

    public void addToActivity(@NonNull Object target, Fragment fragment) {
        String tag = null;
        for (Field fragmentField : fragmentFields) {
            Fragment fragmentOfField = (Fragment) new FieldReader(target, fragmentField).read();
            if (fragment == fragmentOfField) {
                RFragment fragmentAnnotation = fragmentField.getAnnotation(RFragment.class);
                tag = fragmentAnnotation.tag();
            }
        }
        if (tag == null || tag.isEmpty()) {
            tag = fragment.getClass().toString();
        }

        ControllerActivity controllerActivity = getAndroidUnitTest().activity();
        //if no activity is create, the default activity manager behaviour is to create one
        if (controllerActivity.get() == null) {
            controllerActivity.createAndInitActivity(FragmentActivity.class, null);
            controllerActivity.setActivityState(ActivityState.CREATED);
        }

        AnnotationFragmentManager.addToActivity(getActivity(), fragment, tag);
    }

    public void removeFromActivity(Fragment fragment) {
        AnnotationFragmentManager.removeFromActivity(getActivity(), fragment);
    }

    @NonNull
    @Override
    protected Class<? extends Annotation> canManagerInternal() {
        return RFragment.class;
    }

    private Activity getActivity() {
        return getAndroidUnitTest().activity().get();
    }
}
