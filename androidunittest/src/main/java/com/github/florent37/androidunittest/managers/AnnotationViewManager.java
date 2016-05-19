package com.github.florent37.androidunittest.managers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.view.View;

import com.github.florent37.androidunittest.AndroidUnitTest;
import com.github.florent37.androidunittest.annotations.RView;

import org.mockito.Mockito;
import org.mockito.internal.util.reflection.FieldSetter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kevinleperf on 18/05/16.
 */
public class AnnotationViewManager extends AbstractAnnotationManager {

    @VisibleForTesting Set<Field> fields;

    public AnnotationViewManager(AndroidUnitTest androidUnitTest) {
        super(androidUnitTest);

        fields = new HashSet<>();
    }

    @Override
    public void scanned(@NonNull Field field) {
        fields.add(field);
    }

    @Override
    public void execute(@NonNull Object object, @NonNull Context context) {
        for (Field view : fields) {
            initView(object, context, view);
        }
    }

    @NonNull
    @Override
    protected Class<? extends Annotation> canManagerInternal() {
        return RView.class;
    }

    private void initView(@NonNull Object target, @NonNull Context context, @NonNull Field viewField) {
        Class viewClass = viewField.getType();
        View view = null;
        try {
            view = (View) viewClass.getDeclaredConstructor(Context.class).newInstance(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        view = Mockito.spy(view);
        new FieldSetter(target, viewField).set(view);
    }

}
