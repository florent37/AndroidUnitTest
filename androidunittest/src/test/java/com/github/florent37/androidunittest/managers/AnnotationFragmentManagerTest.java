package com.github.florent37.androidunittest.managers;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.github.florent37.androidunittest.AndroidUnitTest;
import com.github.florent37.androidunittest.annotations.RFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Field;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by florentchampigny on 19/05/2016.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class AnnotationFragmentManagerTest {

    @Rule public MockitoRule rule = MockitoJUnit.rule();

    AndroidUnitTest androidUnitTest;
    AnnotationFragmentManager manager;

    @Before
    public void setUp() throws Exception {
        androidUnitTest = AndroidUnitTest.rule();
        manager = new AnnotationFragmentManager(androidUnitTest);
    }

    @Test
    public void testCanManagerInternal() throws Exception {
        // Given
        // When
        Object canManage = manager.canManagerInternal();
        // Then
        assertThat(canManage).isNotNull();
        assertThat(canManage).isEqualTo(RFragment.class);
    }

    @Test
    public void testScanned() throws Exception {
        // Given
        TestObject testObject = new TestObject();
        Field field = testObject.getClass().getDeclaredField("fragment1");
        // When
        manager.scanned(field);
        // Then
        assertThat(manager.fragmentFields).contains(field);
    }

    @Test
    public void testExecute() throws Exception {
        // Given
        TestObject testObject = new TestObject();
        Context context = new Application();
        manager.fragmentFields.add(testObject.getClass().getDeclaredField("fragment1"));
        manager.fragmentFields.add(testObject.getClass().getDeclaredField("fragment2"));
        // When
        manager.execute(testObject, context);
        //Then
        assertThat(testObject.fragment1).isNotNull();
        assertThat(testObject.fragment2).isNotNull();
    }

    @Test
    public void testAddToActivity() throws Exception {
        // Given
        TestObject target = new TestObject();
        target.fragment1 = new Fragment();
        // When
        manager.addToActivity(target, target.fragment1);
        // Then
        assertThat(target.fragment1.getActivity()).isNotNull();
    }

    @Test
    public void testRemoveFromActivity() throws Exception {
        // Given
        TestObject target = new TestObject();
        target.fragment1 = new Fragment();
        manager.addToActivity(target, target.fragment1);
        // When
        manager.removeFromActivity(target.fragment1);
        // Then
        assertThat(target.fragment1.getActivity()).isNull();
    }

    @Test
    public void testAddToActivityWithTag() throws Exception {
        // Given
        TestObject target = new TestObject();
        FragmentActivity activity = Robolectric.buildActivity(FragmentActivity.class).create().get();
        target.fragment1 = new Fragment();
        String tag = "TAG";
        // When
        AnnotationFragmentManager.addToActivity(activity, target.fragment1, tag);
        // Then
        assertThat(target.fragment1.getActivity()).isNotNull();
    }

    static class TestObject {
        @RFragment Fragment fragment1;
        @RFragment Fragment fragment2;
    }

}