package com.github.florent37.androidunittest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.github.florent37.androidunittest.managers.AbstractAnnotationManager;
import com.github.florent37.androidunittest.managers.AnnotationActivityManager;
import com.github.florent37.androidunittest.managers.AnnotationContextManager;
import com.github.florent37.androidunittest.managers.AnnotationFragmentManager;
import com.github.florent37.androidunittest.managers.AnnotationViewManager;

import org.robolectric.RuntimeEnvironment;

import java.lang.reflect.Field;

/**
 * Created by florentchampigny on 07/05/2016.
 */
public class AndroidUnitTestAnnotations {
    final Context context;

    AndroidUnitTest androidUnitTest;
    Object target;

    AbstractAnnotationManager managers[];
    AnnotationActivityManager activityManager;
    AnnotationFragmentManager fragmentManager;


    private AndroidUnitTestAnnotations() {
        context = RuntimeEnvironment.application;
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

        instantiate();
        scan();
        execute();
        return this;
    }


    public void updateActivity() {
        activityManager.updateActivity(target);
    }

    public void addToActivity(@NonNull Fragment fragment) {
        fragmentManager.addToActivity(target, fragment);
    }

    public void removeFromActivity(@NonNull Fragment fragment) {
        fragmentManager.removeFromActivity(fragment);
    }


    /**
     * Instantiate the list of abstract annotation managers
     */
    private void instantiate() {
        managers = new AbstractAnnotationManager[]{
                new AnnotationContextManager(androidUnitTest),
                new AnnotationActivityManager(androidUnitTest),
                new AnnotationFragmentManager(androidUnitTest),
                new AnnotationViewManager(androidUnitTest)
        };

        activityManager = (AnnotationActivityManager) managers[1];
        fragmentManager = (AnnotationFragmentManager) managers[2];
    }

    /**
     * Scan the target to populate the managers
     */
    private void scan() {
        for (Field field : target.getClass().getDeclaredFields()) {
            for (AbstractAnnotationManager manager : managers)
                if (manager.canManage(field))
                    manager.scanned(field);
        }
    }

    /**
     * Execute the different managers
     *
     * given their positions, it represents their dependencies
     */
    private void execute() {
        for (AbstractAnnotationManager manager : managers)
            manager.execute(target, context);
    }
}