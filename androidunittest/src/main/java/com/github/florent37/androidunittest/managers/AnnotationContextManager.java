package com.github.florent37.androidunittest.managers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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

    private Field contextField;

    public AnnotationContextManager(AndroidUnitTest parent) {
        super(parent);
    }

    @NonNull
    @Override
    protected Class<? extends Annotation> canManagerInternal() {
        return RContext.class;
    }

    @Override
    public void scanned(@NonNull Field field) {
        contextField = field;
    }

    @Override
    public void execute(@NonNull Object object, @NonNull Context context) {
        if (contextField != null) {
            new FieldSetter(object, contextField).set(context);
        }
    }
}
