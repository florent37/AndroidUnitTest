package com.github.florent37.testsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.github.florent37.testsample.model.User;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.textView = (TextView) findViewById(R.id.textView);
    }

    public void display(User user) {
        this.textView.setText(user.getName());
    }
}
