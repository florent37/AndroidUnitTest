package com.github.florent37.testsample;

import android.content.Context;

import com.github.florent37.androidunittest.AndroidUnitTest;
import com.github.florent37.androidunittest.annotations.RContext;
import com.github.florent37.androidunittest.annotations.RFragment;
import com.github.florent37.androidunittest.annotations.RView;
import com.github.florent37.testsample.model.User;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(CustomTestRunner.class)
public class MainViewTest {
    @Rule public AndroidUnitTest androidUnitTest = AndroidUnitTest.rule();

    @RView MainView mainView;
    @Mock User user;

    @Test
    public void testDisplayUser() throws Exception {
        // Given
        given(user.getName()).willReturn("florent");
        // When
        mainView.display(user);
        // Then
        verify(mainView).displayText("florent");
    }


}
