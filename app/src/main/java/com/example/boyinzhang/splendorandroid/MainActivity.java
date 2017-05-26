package com.example.boyinzhang.splendorandroid;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.boyinzhang.splendorandroid.View.ImageUtil.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addGemButtonListeners();

        ImageView noble1 = (ImageView) findViewById(R.id.noble1);
        Bitmap src = drawableToBitamp(getResources().getDrawable(R.drawable.female_noble));
        src = scaleWithWH(src,70,70);
//        Bitmap rect = Bitmap.createBitmap(8,8, Bitmap.Config.ARGB_8888);
//        Canvas canvas=new Canvas(rect);
//        Paint paint=new Paint();
//        paint.setColor(Color.parseColor("blue"));
//        canvas.drawBitmap(rect, 0, 0, paint);
//        rect = drawTextToCenter(getApplicationContext(),rect,"3",7,Color.parseColor("white"));
//        //Bitmap watermark = drawableToBitamp(getResources().getDrawable(R.drawable.gold_gem));
//        Bitmap newImage = createWaterMaskRightBottom(getApplicationContext(),src,rect,0,0);
//        //Bitmap newImage = drawTextToLeftBottom(getApplicationContext(),src, "3", 5, Color.parseColor("red"),0,0);
//        Drawable newDrawable = new BitmapDrawable(newImage);
//        noble1.setImageDrawable(newDrawable);

        Bitmap tempBitmap = src.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(tempBitmap);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);//不填充
        //paint.setStrokeWidth(1);  //线的宽度
        canvas.drawRect(0, 60, 10, 70, paint);
        tempBitmap = drawTextToLeftBottom(getApplicationContext(),tempBitmap, "3", 5, Color.WHITE,0,0);
        Drawable newDrawable = new BitmapDrawable(tempBitmap);
        noble1.setImageDrawable(newDrawable);





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
