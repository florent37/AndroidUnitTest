package com.github.florent37.testsample;

import com.github.florent37.androidunittest.AndroidUnitTest;
import com.github.florent37.androidunittest.annotations.RActivity;
import com.github.florent37.androidunittest.states.ActivityState;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.common.truth.Truth.assertThat;

@RunWith(CustomTestRunner.class)
public class StateActivityTest {
    @Rule public AndroidUnitTest androidUnitTest = AndroidUnitTest.rule();

    @RActivity(state = ActivityState.STARTED) MainActivity activity;

    @Test
    public void testAnnotations() throws Exception {
        assertThat(activity.created).isTrue();
        assertThat(activity.started).isTrue();
        assertThat(activity.resumed).isFalse();
    }

}
