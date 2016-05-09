package com.github.florent37.testsample;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.github.florent37.androidunittest.AndroidUnitTest;
import com.github.florent37.androidunittest.annotations.RActivity;
import com.github.florent37.androidunittest.annotations.RContext;
import com.github.florent37.androidunittest.annotations.RFragment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.spy;

@RunWith(CustomTestRunner.class)
public class MainFragmentTest {
    @Rule public AndroidUnitTest androidUnitTest = AndroidUnitTest.rule();

    @RContext Context context;
    @RActivity FragmentActivity fragmentActivity;
    @RFragment(attached = false) MainFragment fragment;

    @Test
    public void testAnnotations() throws Exception {
        assertThat(context).isNotNull();
        assertThat(fragment).isNotNull();
    }

    @Test
    public void testAttach() throws Exception {
        // Given
        assertThat(fragment.getActivity()).isNull();

        // When
        androidUnitTest.fragment().attachToActivity(fragment);

        // Then
        assertThat(fragment.getActivity()).isNotNull();
    }

}
