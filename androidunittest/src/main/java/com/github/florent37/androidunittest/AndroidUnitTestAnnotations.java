package com.github.florent37.androidunittest;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.github.florent37.androidunittest.managers.AbstractAnnotationManager;
import com.github.florent37.androidunittest.managers.AnnotationActivityManager;
import com.github.florent37.androidunittest.managers.AnnotationContextManager;
import com.github.florent37.androidunittest.managers.AnnotationFragmentManager;
import com.github.florent37.androidunittest.managers.AnnotationViewManager;

import org.mockito.Mockito;
import org.mockito.internal.util.reflection.FieldSetter;
import org.robolectric.RuntimeEnvironment;

import java.lang.reflect.Field;

/**
 * Created by florentchampigny on 07/05/2016.
 */
public final class AndroidUnitTestAnnotations {
    AndroidUnitTest androidUnitTest;

    final Context context;
    private Object target;

    private AbstractAnnotationManager annotationManagers[];
    private AnnotationActivityManager activityManager;


    private AndroidUnitTestAnnotations() {
        androidUnitTest = null;

        Context appContext = RuntimeEnvironment.application;
        context = Mockito.spy(appContext);
    }

    public AndroidUnitTestAnnotations(AndroidUnitTest androidUnitTest) {
        this();

        this.androidUnitTest = androidUnitTest;
    }

    public AndroidUnitTestAnnotations init(Object target) {
        //find @Activity
        //find @Fragment
        //find @RContext -> RuntimeEnvironment.application;
        this.target = target;

        //since we are using a new target
        //we must flush the previous instanciation
        instantiate();
        scan();
        execute();

        return this;
    }

    public void updateActivity() {
        new FieldSetter(target, activityManager.getScanned())
                .set(androidUnitTest.getActivityController().get());
    }

    private void instantiate() {
        annotationManagers = new AbstractAnnotationManager[]{
                new AnnotationContextManager(androidUnitTest),
                new AnnotationActivityManager(androidUnitTest),
                new AnnotationViewManager(androidUnitTest),
                new AnnotationFragmentManager(androidUnitTest)
        };

        //to make the update easier, we store a complete reference to it here
        activityManager = null;
        for (AbstractAnnotationManager manager : annotationManagers) {
            if (manager instanceof AnnotationActivityManager)
                activityManager = (AnnotationActivityManager) manager;
        }
    }

    private void execute() {
        for (AbstractAnnotationManager manager : annotationManagers)
            manager.execute(target, context);
    }

    private void scan() {
        for (Field field : target.getClass().getDeclaredFields()) {
            for (AbstractAnnotationManager manager : annotationManagers) {
                if (manager.canManage(field)) {
                    manager.scanned(field);
                }
            }
        }
    }

    private FragmentActivity getActivity() {
        return (FragmentActivity) androidUnitTest.getActivityController().get();
    }
}
