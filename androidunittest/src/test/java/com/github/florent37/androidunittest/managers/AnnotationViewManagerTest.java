package com.github.florent37.androidunittest.managers;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.github.florent37.androidunittest.AndroidUnitTest;
import com.github.florent37.androidunittest.annotations.RView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by florentchampigny on 19/05/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class AnnotationViewManagerTest {

    @Mock AndroidUnitTest androidUnitTest;
    @InjectMocks AnnotationViewManager manager;

    @Test
    public void testCanManagerInternal() throws Exception {
        // Given
        // When
        Object canManage = manager.canManagerInternal();
        // Then
        assertThat(canManage).isNotNull();
        assertThat(canManage).isEqualTo(RView.class);
    }

    @Test
    public void testScanned() throws Exception {
        // Given
        TestObject testObject = new TestObject();
        Field field = testObject.getClass().getDeclaredField("view1");
        // When
        manager.scanned(field);
        // Then
        assertThat(manager.fields).contains(field);
    }

    @Test
    public void testExecute() throws Exception {
        // Given
        TestObject testObject = new TestObject();
        Context context = new Application();
        manager.fields.add(testObject.getClass().getDeclaredField("view1"));
        manager.fields.add(testObject.getClass().getDeclaredField("view2"));
        // When
        manager.execute(testObject, context);
        //Then
        assertThat(testObject.view1).isNotNull();
        assertThat(testObject.view2).isNotNull();
    }

    static class TestObject {
        @RView View view1;
        @RView TextView view2;
    }
}