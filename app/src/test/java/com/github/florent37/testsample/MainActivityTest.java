package com.github.florent37.testsample;

import android.content.Context;

import com.github.florent37.androidunittest.AndroidUnitTest;
import com.github.florent37.androidunittest.annotations.MActivity;
import com.github.florent37.androidunittest.annotations.MContext;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.spy;

@RunWith(CustomTestRunner.class)
public class MainActivityTest {
    @Rule public AndroidUnitTest androidUnitTest = AndroidUnitTest.rule();

    @MContext Context context;
    @MActivity MainActivity activity;

    @Test
    public void testAnnotations() throws Exception {
        assertThat(context).isNotNull();
        assertThat(activity).isNotNull();
    }

    @Test
    public void testOnResume() throws Exception {
        assertThat(activity.resumed).isFalse();

        androidUnitTest.activity().resume();

        assertThat(activity.resumed).isTrue();
    }

}
