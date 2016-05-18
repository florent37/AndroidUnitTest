package com.github.florent37.androidunittest.managers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.github.florent37.androidunittest.AndroidUnitTest;
import com.github.florent37.androidunittest.annotations.RActivity;
import com.github.florent37.androidunittest.states.SActivity;

import org.mockito.Mockito;
import org.mockito.internal.util.reflection.FieldSetter;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by kevinleperf on 18/05/16.
 */
public class AnnotationActivityManager extends AbstractAnnotationManager {

    private Field activityField;

    public AnnotationActivityManager(AndroidUnitTest parent) {
        super(parent);

        createAndInitActivity(null, FragmentActivity.class, null);
        //useless create() here since in createAndInitActivity
        //parent.getActivityController().create();
    }

    @NonNull
    @Override
    protected Class<? extends Annotation> canManagerInternal() {
        return RActivity.class;
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


    private void createAndInitActivity(Object target, Class activityClass, @Nullable RActivity activityAnnotation) {
        ActivityController activityController = ActivityController.of(Robolectric.getShadowsAdapter(), activityClass);
        SActivity type = SActivity.CREATED;

        if (activityAnnotation != null) {
            type = activityAnnotation.type();
        }

        switch (type) {
            case STARTED:
                activityController.start();
                break;
            case RESUMED:
                activityController.restart();
                break;
            case PAUSED:
                activityController.pause();
                break;
            case STOPPED:
                activityController.stop();
                break;
            case DESTROYED:
                activityController.destroy();
                break;
            default:
            case CREATED:
                activityController.create();
                break;
        }
        
        getAndroidUnitTest().setActivityController(activityController);
        FragmentActivity fragmentActivity = (FragmentActivity) activityController.get();
        fragmentActivity = Mockito.spy(fragmentActivity);
        if (this.activityField != null && target != null) {
            new FieldSetter(target, this.activityField).set(fragmentActivity);
        }
    }
}
