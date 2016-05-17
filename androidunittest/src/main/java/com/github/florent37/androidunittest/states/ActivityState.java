package com.github.florent37.androidunittest.states;

/**
 * Created by kevinleperf on 14/05/16.
 *
 * A ActivityState describe the initial state of an activity at binding
 */
public enum ActivityState {
    CREATED(ActivityStateIndex.INDEX_CREATED),
    STARTED(ActivityStateIndex.INDEX_STARTED),
    RESUMED(ActivityStateIndex.INDEX_RESUMED),
    PAUSED(ActivityStateIndex.INDEX_PAUSED),
    STOPPED(ActivityStateIndex.INDEX_STOPPED),
    DESTROYED(ActivityStateIndex.INDEX_DESTROYED);

    int activityLifecycleIndex = -1;

    ActivityState(int activityLifecycleIndex) {
        this.activityLifecycleIndex = activityLifecycleIndex;
    }

    public int getActivityLifecycleIndex() {
        return activityLifecycleIndex;
    }
}
