package com.github.florent37.androidunittest.managers;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.github.florent37.androidunittest.AndroidUnitTest;
import com.github.florent37.androidunittest.annotations.RActivity;
import com.github.florent37.androidunittest.controllers.ControllerActivity;
import com.github.florent37.androidunittest.states.ActivityState;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import java.lang.reflect.Field;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by florentchampigny on 19/05/2016.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class AnnotationActivityManagerTest {

    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock AndroidUnitTest androidUnitTest;
    ControllerActivity controllerActivity;
    @Mock Activity activity;
    @InjectMocks AnnotationActivityManager manager;

    @Before
    public void setUp() throws Exception {
        controllerActivity = new ControllerActivity(androidUnitTest);
        given(androidUnitTest.activity()).willReturn(controllerActivity);
    }

    @Test
    public void testCanManagerInternal() throws Exception {
        // Given
        // When
        Object canManage = manager.canManagerInternal();
        // Then
        assertThat(canManage).isNotNull();
        assertThat(canManage).isEqualTo(RActivity.class);
    }

    @Test
    public void testScanned() throws Exception {
        // Given
        TestObject testObject = new TestObject();
        Field field = testObject.getClass().getDeclaredField("activity");
        // When
        manager.scanned(field);
        // Then
        assertThat(manager.activityField).isEqualTo(field);
    }

    @Test
    public void testExecute() throws Exception {
        // Given
        TestObject testObject = new TestObject();
        Context context = new Application();
        manager.activityField = testObject.getClass().getDeclaredField("activity");
        // When
        manager.execute(testObject, context);
        //Then
        assertThat(testObject.activity).isNotNull();
        assertThat(testObject.activity.created).isTrue();
        verify(androidUnitTest).setActivityController(any(ActivityController.class));
    }

    @Test
    public void testExecuteState() throws Exception {
        // Given
        TestObjectState testObject = new TestObjectState();
        Context context = new Application();
        manager.activityField = testObject.getClass().getDeclaredField("activity");
        // When
        manager.execute(testObject, context);
        //Then
        assertThat(testObject.activity).isNotNull();
        assertThat(testObject.activity.created).isTrue();
        assertThat(testObject.activity.started).isTrue();
        assertThat(testObject.activity.resumed).isTrue();
        verify(androidUnitTest).setActivityController(any(ActivityController.class));
    }

    static class TestObject {
        @RActivity TestActivity activity;
    }

    static class TestObjectState {
        @RActivity(state = ActivityState.RESUMED) TestActivity activity;
    }

    static class TestActivity extends Activity {
        boolean created = false;
        boolean resumed = false;
        boolean started = false;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            created = true;
        }

        @Override
        protected void onStart() {
            super.onStart();
            started = true;
        }

        @Override
        protected void onResume() {
            super.onResume();
            resumed = true;
        }
    }
}