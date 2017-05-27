package com.example.boyinzhang.splendorandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by boyinzhang on 5/27/17.
 */

public class MainActivity extends Activity {
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        this.startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(MainActivity.this,GameActivity.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
        });
    }

}
