package com.github.florent37.androidunittest.controllers;

import android.support.v4.app.Fragment;

import com.github.florent37.androidunittest.AndroidUnitTest;

/**
 * Created by florentchampigny on 08/05/2016.
 */
public class ControllerFragment {
    private AndroidUnitTest androidUnitTest;
    public ControllerFragment(AndroidUnitTest androidUnitTest) {
        this.androidUnitTest = androidUnitTest;
    }

    public ControllerFragment attachToActivity(Fragment fragment){
        if (androidUnitTest.activity().get() == null) {

        }
        androidUnitTest.getAndroidUnitTestAnnotations().addToActivity(fragment);
        return this;
    }

    public ControllerFragment removeFromActivity(Fragment fragment){
        androidUnitTest.getAndroidUnitTestAnnotations().removeFromActivity(fragment);
        return this;
    }
}
