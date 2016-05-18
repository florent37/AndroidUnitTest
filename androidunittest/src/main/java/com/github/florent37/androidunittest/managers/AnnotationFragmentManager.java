package com.github.florent37.androidunittest.managers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.github.florent37.androidunittest.AndroidUnitTest;
import com.github.florent37.androidunittest.annotations.RFragment;

import org.mockito.Mockito;
import org.mockito.internal.util.reflection.FieldSetter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;

/**
 * Created by kevinleperf on 18/05/16.
 */
public class AnnotationFragmentManager extends AbstractAnnotationManager {
    private final HashSet<Field> fragmentField;

    public AnnotationFragmentManager(AndroidUnitTest parent) {
        super(parent);

        fragmentField = new HashSet();
    }

    @NonNull
    @Override
    protected Class<? extends Annotation> canManagerInternal() {
        return RFragment.class;
    }

    @Override
    public void scanned(@NonNull Field field) {
        fragmentField.add(field);
    }

    @Override
    public void execute(@NonNull Object object, @NonNull Context context) {
        for (Field fragment : fragmentField) {
            initFragment(object, fragment);
        }
    }


    public void initFragment(@NonNull Object object, @NonNull Field fragmentField) {
        RFragment fragmentAnnotation = fragmentField.getAnnotation(RFragment.class);

        Class fragmentClass = fragmentField.getType();
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        fragment = Mockito.spy(fragment);
        new FieldSetter(object, fragmentField).set(fragment);

        //activity is never null since defined in ActivityManager controller

        if (fragmentAnnotation.attached()) {
            String tag = fragmentAnnotation.tag();
            if (tag == null || tag.isEmpty()) {
                tag = fragment.getClass().toString();
            }
            FragmentActivity activity = getAndroidUnitTest().activity().get();

            if (activity != null) {
                activity.getSupportFragmentManager().beginTransaction()
                        .add(fragment, tag)
                        .commit();
            }

        }
    }
}
