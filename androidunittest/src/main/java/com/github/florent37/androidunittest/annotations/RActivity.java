package com.github.florent37.androidunittest.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by florentchampigny on 07/05/2016.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RActivity {
    boolean created() default true;
    boolean started() default false;
    boolean resumed() default false;
    boolean paused() default false;
    boolean stoped() default false;
    boolean destroyed() default false;
}
