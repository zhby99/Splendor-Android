package com.example.boyinzhang.splendorandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addGemButtonListeners();





    }

    private void addGemButtonListeners() {
        ImageButton diamondGemButton = (ImageButton) findViewById(R.id.diamond_gem);
        diamondGemButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView diamondNumView = (TextView) findViewById(R.id.diamond_num);
                int currentNum = Integer.parseInt((String) diamondNumView.getText());
                currentNum++;
                diamondNumView.setText(Integer.toString(currentNum));
            }
        });

        ImageButton emeraldGemButton = (ImageButton) findViewById(R.id.emerald_gem);
        emeraldGemButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView emeraldNumView = (TextView) findViewById(R.id.emerald_num);
                int currentNum = Integer.parseInt((String) emeraldNumView.getText());
                currentNum++;
                emeraldNumView.setText(Integer.toString(currentNum));
            }
        });

        ImageButton onyxGemButton = (ImageButton) findViewById(R.id.onyx_gem);
        onyxGemButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView onyxNumView = (TextView) findViewById(R.id.onyx_num);
                int currentNum = Integer.parseInt((String) onyxNumView.getText());
                currentNum++;
                onyxNumView.setText(Integer.toString(currentNum));
            }
        });

        ImageButton rubyGemButton = (ImageButton) findViewById(R.id.ruby_gem);
        rubyGemButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView rubyNumView = (TextView) findViewById(R.id.ruby_num);
                int currentNum = Integer.parseInt((String) rubyNumView.getText());
                currentNum++;
                rubyNumView.setText(Integer.toString(currentNum));
            }
        });

        ImageButton sapphireGemButton = (ImageButton) findViewById(R.id.sapphire_gem);
        sapphireGemButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView sapphireNumView = (TextView) findViewById(R.id.sapphire_num);
                int currentNum = Integer.parseInt((String) sapphireNumView.getText());
                currentNum++;
                sapphireNumView.setText(Integer.toString(currentNum));
            }
        });
    }
}
