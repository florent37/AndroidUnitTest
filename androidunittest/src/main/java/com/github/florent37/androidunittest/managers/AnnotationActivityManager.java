package com.github.florent37.androidunittest.managers;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.github.florent37.androidunittest.AndroidUnitTest;
import com.github.florent37.androidunittest.annotations.RActivity;

import org.mockito.internal.util.reflection.FieldSetter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by kevinleperf on 18/05/16.
 */
public class AnnotationActivityManager extends AbstractAnnotationManager {

    @VisibleForTesting Field activityField;

    public AnnotationActivityManager(AndroidUnitTest parent) {
        super(parent);

        //createAndInitActivity(null, FragmentActivity.class, null);
        //parent.activity().setActivityState(ActivityState.CREATED);
        //useless create() here since in createAndInitActivity
        //parent.getActivityController().create();
    }

    @Override
    public void scanned(@NonNull Field field) {
        activityField = field;
    }

    @Override
    public void execute(@NonNull Object object, @NonNull Context context) {
        if (activityField != null) {
            createAndInitActivity(object,
                    activityField.getType(),
                    activityField.getAnnotation(RActivity.class));
        }//activityField is null, using standard FragmentActivity from constructor
    }

    public Field getScanned() {
        return activityField;
    }

    public void createAndInitActivity(@Nullable Object target, Class activityClass, @Nullable RActivity activityAnnotation) {
        Activity activity = getAndroidUnitTest().activity().createAndInitActivity(activityClass, activityAnnotation);
        if (this.activityField != null && target != null) {
            new FieldSetter(target, this.activityField).set(activity);
        }
    }

    public void updateActivity(Object target) {
        new FieldSetter(target, this.activityField)
                .set(getAndroidUnitTest().getActivityController().get());
    }

    @NonNull
    @Override
    protected Class<? extends Annotation> canManagerInternal() {
        return RActivity.class;
    }
}
