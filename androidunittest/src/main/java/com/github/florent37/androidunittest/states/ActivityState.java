package com.github.florent37.androidunittest.states;

import android.support.annotation.NonNull;

/**
 * Created by kevinleperf on 14/05/16.
 * <p/>
 * A ActivityState describe the initial state of an activity at binding
 */
public enum ActivityState {
    //reverse ordered to prevent illegal forward reference
    DESTROYED(null),
    STOPPED(ActivityState.DESTROYED),
    PAUSED(ActivityState.STOPPED),
    RESUMED(ActivityState.PAUSED),
    STARTED(ActivityState.RESUMED),
    CREATED(ActivityState.STARTED);


    private ActivityState mNext;

    ActivityState(ActivityState next) {
        mNext = next;
    }

    public ActivityState next() {
        return mNext;
    }

    /**
     * Check wether the given ActivityState is before or at least the same element as this instance
     *
     * commonly used to check if the previous state can be call
     * for instance, checking if Resume can be used before Paused, it will returns true
     * whereas, testing destroy against resume will return false
     * @param state
     * @return
     */
    public boolean isLowerOrEquals(@NonNull ActivityState state) {
        return ordinal() >= state.ordinal(); //since we are in the reverse order
        //it is not <= but =>
    }
}
