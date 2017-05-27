package com.example.boyinzhang.splendorandroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.boyinzhang.splendorandroid.Game.Game;
import com.example.boyinzhang.splendorandroid.Model.Card;
import com.example.boyinzhang.splendorandroid.Model.Gem;
import com.example.boyinzhang.splendorandroid.Model.utils.GemInfo;

import java.io.Serializable;

import static com.example.boyinzhang.splendorandroid.View.ImageUtil.drawTextToLeftBottom;
import static com.example.boyinzhang.splendorandroid.View.ImageUtil.drawTextToRightTop;
import static com.example.boyinzhang.splendorandroid.View.ImageUtil.drawableToBitamp;
import static com.example.boyinzhang.splendorandroid.View.ImageUtil.scaleWithWH;

/**
 * Created by boyinzhang on 5/27/17.
 */

public class ReserveActivity extends Activity{
    Button buyButton;
    Button backButton;
    Game game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserve_board);
        this.game= (Game) getIntent().getSerializableExtra(GameActivity.KEY);
        this.backButton = (Button) findViewById(R.id.reserve_back);
        backButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(ReserveActivity.this,GameActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable(GameActivity.KEY,game);
                mainIntent.putExtras(mBundle);
                startActivity(mainIntent);
            }
        });
        this.buyButton = (Button) findViewById(R.id.reserve_buy);
        buyButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(ReserveActivity.this,GameActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable(GameActivity.KEY,game);
                mainIntent.putExtras(mBundle);
                startActivity(mainIntent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.game= (Game) getIntent().getSerializableExtra(GameActivity.KEY);
    }

    private Drawable drawCard(Card card){
        Bitmap background = drawableToBitamp(getResources().getDrawable(backImageByGem(card.getTargetGem())));
        background = scaleWithWH(background,100,150);
        Bitmap tempBitmap = background.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(tempBitmap);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 120, 30, 150, paint);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 90, 30, 120, paint);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 60, 30, 90, paint);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 30, 30, 60, paint);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, 30, 30, paint);
        switch (card.getTargetGem()){
            case DIAMOND:
                paint.setColor(Color.WHITE);
                break;
            case EMERALD:
                paint.setColor(Color.GREEN);
                break;
            case ONYX:
                paint.setColor(Color.BLACK);
                break;
            case RUBY:
                paint.setColor(Color.RED);
                break;
            case SAPPHIRE:
                paint.setColor(Color.BLUE);
                break;
        }
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(70, 0, 100, 30, paint);
        tempBitmap = drawTextToLeftBottom(getApplicationContext(),tempBitmap, String.valueOf(card.getDevelopmentCost().sapphire), 10, Color.WHITE,3,3);
        tempBitmap = drawTextToLeftBottom(getApplicationContext(),tempBitmap, String.valueOf(card.getDevelopmentCost().ruby), 10, Color.WHITE,3,14);
        tempBitmap = drawTextToLeftBottom(getApplicationContext(),tempBitmap, String.valueOf(card.getDevelopmentCost().onyx), 10, Color.WHITE,3,25);
        tempBitmap = drawTextToLeftBottom(getApplicationContext(),tempBitmap, String.valueOf(card.getDevelopmentCost().emerald), 10, Color.WHITE,3,36);
        tempBitmap = drawTextToLeftBottom(getApplicationContext(),tempBitmap, String.valueOf(card.getDevelopmentCost().diamond), 10, Color.BLACK,3,47);
        if(card.getTargetGem()!= Gem.DIAMOND){
            tempBitmap = drawTextToRightTop(getApplicationContext(),tempBitmap, String.valueOf(card.getCardScore()), 10, Color.WHITE,3,3);
        } else{
            tempBitmap = drawTextToRightTop(getApplicationContext(),tempBitmap, String.valueOf(card.getCardScore()), 10, Color.BLACK,3,3);
        }

        return new BitmapDrawable(tempBitmap);
    }

    private int backImageByGem(Gem gem){
        switch (gem){
            case DIAMOND:
                return R.drawable.diamond;
            case EMERALD:
                return R.drawable.emerald;
            case ONYX:
                return R.drawable.onyx;
            case RUBY:
                return R.drawable.ruby;
            case SAPPHIRE:
                return R.drawable.sapphire;
            default:
                return -1;
        }
    }
}
