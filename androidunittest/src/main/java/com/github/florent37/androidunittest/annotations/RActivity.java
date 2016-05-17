package com.github.florent37.androidunittest.annotations;

import com.github.florent37.androidunittest.states.SActivity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by florentchampigny on 07/05/2016.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RActivity {
    SActivity state() default SActivity.CREATED;
}
