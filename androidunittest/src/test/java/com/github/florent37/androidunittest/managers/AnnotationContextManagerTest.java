package com.github.florent37.androidunittest.managers;

import android.app.Application;
import android.content.Context;

import com.github.florent37.androidunittest.AndroidUnitTest;
import com.github.florent37.androidunittest.annotations.RContext;

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
public class AnnotationContextManagerTest {

    @Mock AndroidUnitTest androidUnitTest;
    @InjectMocks AnnotationContextManager manager;

    @Test
    public void testCanManagerInternal() throws Exception {
        // Given
        // When
        Object canManage = manager.canManagerInternal();
        // Then
        assertThat(canManage).isNotNull();
        assertThat(canManage).isEqualTo(RContext.class);
    }

    @Test
    public void testScanned() throws Exception {
        // Given
        TestObject testObject = new TestObject();
        Field field = testObject.getClass().getDeclaredField("context");
        // When
        manager.scanned(field);
        // Then
        assertThat(manager.contextField).isEqualTo(field);
    }

    @Test
    public void testExecute() throws Exception {
        // Given
        TestObject testObject = new TestObject();
        Context context = new Application();
        manager.contextField = testObject.getClass().getDeclaredField("context");
        // When
        manager.execute(testObject, context);
        //Then
        assertThat(testObject.context).isNotNull();
    }

    static class TestObject {
        @RContext Context context;
    }
}