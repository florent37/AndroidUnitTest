package com.github.florent37.androidunittest.managers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.github.florent37.androidunittest.AndroidUnitTest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by kevinleperf on 18/05/16.
 */
public abstract class AbstractAnnotationManager {
    private AndroidUnitTest mAndroidUnitTest;

    private AbstractAnnotationManager() {

    }

    public AbstractAnnotationManager(AndroidUnitTest parent) {
        this();

        mAndroidUnitTest = parent;
    }

    public final boolean canManage(@NonNull Field field) {
        return field.isAnnotationPresent(canManagerInternal());
    }

    @NonNull
    protected abstract Class<? extends Annotation> canManagerInternal();

    /**
     * Method called by the AndroidUnitTestAnnotations class
     * during the scan() method
     *
     * @param field the scanned field to manage internally
     */
    public abstract void scanned(@NonNull Field field);

    /**
     * Method called by the AndroidUnitTestAnnotations class
     * during the execute() method
     *
     * @param object  the object to be applied
     * @param context the current app context
     */
    public abstract void execute(@NonNull Object object,
                                 @NonNull Context context);

    @NonNull
    protected AndroidUnitTest getAndroidUnitTest() {
        return mAndroidUnitTest;
    }

}
