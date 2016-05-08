package com.github.florent37.testsample;

import com.github.florent37.androidunittest.AndroidUnitTestRunner;

import org.junit.runners.model.InitializationError;

/**
 * Created by florentchampigny on 07/05/2016.
 */
public class CustomTestRunner extends AndroidUnitTestRunner {
    public CustomTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass, BuildConfig.FLAVOR, BuildConfig.BUILD_TYPE, BuildConfig.APPLICATION_ID, TestMyApplication.class);
    }
}
