package com.github.florent37.testsample;

import android.content.Context;

import com.github.florent37.androidunittest.AndroidUnitTest;
import com.github.florent37.androidunittest.annotations.RActivity;
import com.github.florent37.androidunittest.annotations.RContext;
import com.github.florent37.androidunittest.annotations.RView;
import com.github.florent37.testsample.model.User;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static com.google.common.truth.Truth.assertThat;

@RunWith(CustomTestRunner.class)
public class BasicActivityTest {
    @Rule public AndroidUnitTest androidUnitTest = AndroidUnitTest.rule();

    @RContext Context context;
    @RActivity MainActivity activity;
    @RView MainView view;

    @Mock User user;

    @Test
    public void testAnnotations() throws Exception {
        assertThat(context).isNotNull();
        assertThat(activity).isNotNull();
        assertThat(view).isNotNull();
        assertThat(user).isNotNull();
    }

}
