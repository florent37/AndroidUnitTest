package com.github.florent37.androidunittest.managers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.github.florent37.androidunittest.AndroidUnitTest;
import com.github.florent37.androidunittest.annotations.RContext;

import org.mockito.Mockito;
import org.mockito.internal.util.reflection.FieldSetter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by kevinleperf on 18/05/16.
 */
public class AnnotationContextManager extends AbstractAnnotationManager {

    @VisibleForTesting Field contextField;

    public AnnotationContextManager(AndroidUnitTest parent) {
        super(parent);
    }

    @Override
    public void scanned(@NonNull Field field) {
        contextField = field;
    }

    @Override
    public void execute(@NonNull Object target, @NonNull Context context) {
        if (contextField != null) {
            Context appContext = context;
            appContext = Mockito.spy(appContext);
            new FieldSetter(target, this.contextField).set(appContext);
        }
    }

    @NonNull
    @Override
    protected Class<? extends Annotation> canManagerInternal() {
        return RContext.class;
    }
}
