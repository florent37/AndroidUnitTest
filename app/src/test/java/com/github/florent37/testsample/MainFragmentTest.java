package com.github.florent37.testsample;

import android.content.Context;

import com.github.florent37.androidunittest.AndroidUnitTest;
import com.github.florent37.androidunittest.annotations.MActivity;
import com.github.florent37.androidunittest.annotations.MContext;
import com.github.florent37.androidunittest.annotations.MFragment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.common.truth.Truth.assertThat;

@RunWith(CustomTestRunner.class)
public class MainFragmentTest {
    @Rule public AndroidUnitTest androidUnitTest = AndroidUnitTest.rule();

    @MContext Context context;
    @MFragment MainFragment fragment;

    @Test
    public void testAnnotations() throws Exception {
        assertThat(context).isNotNull();
        assertThat(fragment).isNotNull();
    }

}
