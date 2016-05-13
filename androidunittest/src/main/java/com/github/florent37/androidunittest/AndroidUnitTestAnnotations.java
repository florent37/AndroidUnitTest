package com.github.florent37.androidunittest;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.github.florent37.androidunittest.annotations.RActivity;
import com.github.florent37.androidunittest.annotations.RContext;
import com.github.florent37.androidunittest.annotations.RFragment;
import com.github.florent37.androidunittest.annotations.RView;

import org.mockito.Mockito;
import org.mockito.internal.util.reflection.FieldSetter;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.util.ActivityController;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by florentchampigny on 07/05/2016.
 */
public class AndroidUnitTestAnnotations {
    final AndroidUnitTest androidUnitTest;
    Object target;
    Field activityField;
    Set<Field> fragmentField;
    Field contextField;
    Set<Field> viewsField;

    Context context = RuntimeEnvironment.application;

    public AndroidUnitTestAnnotations(AndroidUnitTest androidUnitTest) {
        this.androidUnitTest = androidUnitTest;

        this.fragmentField = new HashSet<>();
        this.viewsField = new HashSet<>();
    }

    public AndroidUnitTestAnnotations init(Object target) {
        //find @Activity
        //find @Fragment
        //find @RContext -> RuntimeEnvironment.application;
        this.target = target;
        scan();
        execute();
        return this;
    }

    public void initContext() {
        if (this.contextField != null) {
            Context appContext = context;
            appContext = Mockito.spy(appContext);
            new FieldSetter(target, this.contextField).set(appContext);
        }
    }

    public void initActivity() {
        if (this.activityField != null) {
            Class activityClass = this.activityField.getType();
            RActivity activityAnnotation = this.activityField.getAnnotation(RActivity.class);
            createActivity(activityClass, activityAnnotation);
        }
    }

    public void updateActivity() {
        new FieldSetter(target, this.activityField).set(androidUnitTest.getActivityController().get());
    }

    public void initFragment(Field fragmentField) {
        if (fragmentField != null) {
            RFragment fragmentAnnotation = fragmentField.getAnnotation(RFragment.class);

            Class fragmentClass = fragmentField.getType();
            Fragment fragment = null;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            fragment = Mockito.spy(fragment);
            new FieldSetter(target, fragmentField).set(fragment);

            if (this.activityField == null) {
                createActivity(FragmentActivity.class, null);
                androidUnitTest.getActivityController().create();
            }

            if (fragmentAnnotation.attached()) {
                String tag = fragmentAnnotation.tag();
                if (tag == null || tag.isEmpty()) {
                    tag = fragment.getClass().toString();
                }
                getActivity().getSupportFragmentManager().beginTransaction()
                    .add(fragment, tag)
                    .commit();
            }
        }
    }

    private FragmentActivity getActivity() {
        return (FragmentActivity) androidUnitTest.getActivityController().get();
    }

    private void execute() {
        initContext();
        initActivity();
        for (Field fragment : fragmentField) {
            initFragment(fragment);
        }
        for (Field view : viewsField) {
            initView(view);
        }
    }

    private void initView(Field viewField) {
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

    private void scan() {
        for (Field field : target.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(RActivity.class)) {
                activityField = field;
            }
            if (field.isAnnotationPresent(RFragment.class)) {
                fragmentField.add(field);
            }
            if (field.isAnnotationPresent(RContext.class)) {
                contextField = field;
            }
            if (field.isAnnotationPresent(RView.class)) {
                viewsField.add(field);
            }
        }
    }

    private void createActivity(Class activityClass, @Nullable RActivity activityAnnotation) {
        ActivityController activityController = ActivityController.of(Robolectric.getShadowsAdapter(), activityClass);
        if(activityAnnotation != null) {
            switch(activityAnnotation.type()){
                case CREATED:
                    activityController.create();
                    break;
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
            }
        }
        androidUnitTest.setActivityController(activityController);
        FragmentActivity fragmentActivity = (FragmentActivity) activityController.get();
        fragmentActivity = Mockito.spy(fragmentActivity);
        if (this.activityField != null) {
            new FieldSetter(target, this.activityField).set(fragmentActivity);
        }
    }
}
