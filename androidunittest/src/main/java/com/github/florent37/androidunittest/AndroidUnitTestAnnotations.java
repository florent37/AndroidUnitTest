package com.github.florent37.androidunittest;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.github.florent37.androidunittest.annotations.RActivity;
import com.github.florent37.androidunittest.annotations.RContext;
import com.github.florent37.androidunittest.annotations.RFragment;
import com.github.florent37.androidunittest.annotations.RView;

import org.mockito.Mockito;
import org.mockito.internal.util.reflection.FieldReader;
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
    Set<Field> fragmentsField;
    Field contextField;
    Set<Field> viewsField;

    Context context = RuntimeEnvironment.application;

    public AndroidUnitTestAnnotations(AndroidUnitTest androidUnitTest) {
        this.androidUnitTest = androidUnitTest;

        this.fragmentsField = new HashSet<>();
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
                addToActivity(getActivity(), fragment, tag);
            }
        }
    }

    private FragmentActivity getActivity() {
        return (FragmentActivity) androidUnitTest.getActivityController().get();
    }

    private void execute() {
        initContext();
        initActivity();
        for (Field fragment : fragmentsField) {
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
                fragmentsField.add(field);
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
            if (activityAnnotation.created()) {
                activityController.create();
            }
            if (activityAnnotation.started()) {
                activityController.start();
            }
            if (activityAnnotation.resumed()) {
                activityController.resume();
            }
            if (activityAnnotation.paused()) {
                activityController.pause();
            }
            if (activityAnnotation.stoped()) {
                activityController.stop();
            }
            if (activityAnnotation.destroyed()) {
                activityController.destroy();
            }
        }
        androidUnitTest.setActivityController(activityController);
        FragmentActivity fragmentActivity = (FragmentActivity) activityController.get();
        fragmentActivity = Mockito.spy(fragmentActivity);
        if (this.activityField != null) {
            new FieldSetter(target, this.activityField).set(fragmentActivity);
        }
    }

    public void addToActivity(@NonNull Fragment fragment){
        String tag = null;
        for(Field fragmentField : fragmentsField){
            Fragment fragmentOfField = (Fragment) new FieldReader(target, fragmentField).read();
            if(fragment == fragmentOfField){
                RFragment fragmentAnnotation = fragmentField.getAnnotation(RFragment.class);
                tag = fragmentAnnotation.tag();
            }
        }
        if (tag == null || tag.isEmpty()) {
            tag = fragment.getClass().toString();
        }
        addToActivity(getActivity(), fragment, tag);
    }

    public void removeFromActivity(Fragment fragment){
        removeFromActivity(getActivity(), fragment);
    }

    public static void addToActivity(FragmentActivity activity, Fragment fragment, String tag){
        activity.getSupportFragmentManager().beginTransaction()
            .add(fragment, tag)
            .commit();
    }

    public static void removeFromActivity(FragmentActivity activity, Fragment fragment){
        activity.getSupportFragmentManager().beginTransaction()
            .remove(fragment)
            .commit();
    }
}
