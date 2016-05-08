package com.github.florent37.testsample;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.github.florent37.testsample.model.User;

/**
 * Created by florentchampigny on 08/05/2016.
 */
public class MainView extends FrameLayout {
    public MainView(Context context) {
        this(context, null);
    }

    public MainView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void displayText(String text){

    }

    public void display(User user){
        displayText(user.getName());
    }
}
