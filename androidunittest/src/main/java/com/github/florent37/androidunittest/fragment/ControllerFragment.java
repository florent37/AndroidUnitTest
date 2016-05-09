package com.github.florent37.androidunittest.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

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
        androidUnitTest.getAndroidUnitTestAnnotations().addToActivity(fragment);
        return this;
    }

    public ControllerFragment removeFromActivity(Fragment fragment){
        androidUnitTest.getAndroidUnitTestAnnotations().removeFromActivity(fragment);
        return this;
    }
}
