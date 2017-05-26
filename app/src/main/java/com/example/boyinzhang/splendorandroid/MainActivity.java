package com.example.boyinzhang.splendorandroid;

import android.app.AlertDialog;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boyinzhang.splendorandroid.Game.Game;
import com.example.boyinzhang.splendorandroid.Model.Card;
import com.example.boyinzhang.splendorandroid.Model.utils.GemInfo;

import static com.example.boyinzhang.splendorandroid.View.ImageUtil.*;

public class MainActivity extends AppCompatActivity {
    Game game;
    GemInfo currentGemInfo;
    Card selectedCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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


        this.game = new Game();
        currentGemInfo = new GemInfo(0);
        addGemButtonListeners();
        addButtonListeners();






    }

    private void addButtonListeners() {
        Button resetButton = (Button) findViewById(R.id.reset);
        Button collectButton = (Button) findViewById(R.id.collect);
        Button buyButton = (Button) findViewById(R.id.buy);
        Button reserveButton = (Button) findViewById(R.id.reserve);
        resetButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentGemInfo.reset();
                LinearLayout gemBoard = (LinearLayout) findViewById(R.id.gem_board);
                RelativeLayout gemBoardChild = (RelativeLayout) gemBoard.getChildAt(0);
                for(int i =1; i<5; i++){
                    RelativeLayout tempChild = (RelativeLayout) gemBoardChild.getChildAt(i);
                    TextView tempView = (TextView) tempChild.getChildAt(1);
                    tempView.setText("0");
                }
            }
        });
        collectButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!game.getCurrentPlayer().collectGems(currentGemInfo)){
                    Toast.makeText(MainActivity.this, "You cannot make this collection! Please try again!", Toast.LENGTH_LONG).show();
                }
                else{
                    currentGemInfo.reset();
                    game.turnToNextPlayer();
                }
            }
        });

        buyButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!game.getCurrentPlayer().buyCard(selectedCard, selectedCard.isReserved())){
                    Toast.makeText(MainActivity.this, "You cannot make this purchase! Please try again!", Toast.LENGTH_LONG).show();
                }
                else{
                    Card newCard = game.getGameBoard().getNewCard(selectedCard.getPosition()[0]);
                    game.getGameBoard().setCardOnBoard(newCard, selectedCard.getPosition());
                    selectedCard = null;
                    game.getCurrentPlayer().recruitAvailableNobles();
                    if (game.currentPlayer.hasWon()) {
                        game.turnToNextPlayer();
                        new  AlertDialog.Builder(getApplicationContext())
                                .setTitle("Game End" )
                                .setMessage("You have won! Congrats!" )
                                .setPositiveButton("Yes" ,  null )
                                .show();
                    }
                }
            }
        });

        reserveButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!game.getCurrentPlayer().reserveCard(selectedCard)){
                    Toast.makeText(MainActivity.this, "You cannot reserve this card! Please try again!", Toast.LENGTH_LONG).show();
                }
                else {
                    Card newCard = game.getGameBoard().getNewCard(selectedCard.getPosition()[0]);
                    game.getGameBoard().setCardOnBoard(newCard, selectedCard.getPosition());
                    selectedCard = null;
                    game.turnToNextPlayer();
                }
            }
        });
    }

    private void addGemButtonListeners() {
        ImageButton diamondGemButton = (ImageButton) findViewById(R.id.diamond_gem);
        diamondGemButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView diamondNumView = (TextView) findViewById(R.id.diamond_num);
                currentGemInfo.updateInfo(1,0,0,0,0);
                diamondNumView.setText(Integer.toString(currentGemInfo.diamond));
            }
        });

        ImageButton emeraldGemButton = (ImageButton) findViewById(R.id.emerald_gem);
        emeraldGemButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView emeraldNumView = (TextView) findViewById(R.id.emerald_num);
                currentGemInfo.updateInfo(0,1,0,0,0);
                emeraldNumView.setText(Integer.toString(currentGemInfo.emerald));
            }
        });

        ImageButton onyxGemButton = (ImageButton) findViewById(R.id.onyx_gem);
        onyxGemButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView onyxNumView = (TextView) findViewById(R.id.onyx_num);
                currentGemInfo.updateInfo(0,0,1,0,0);
                onyxNumView.setText(Integer.toString(currentGemInfo.onyx));
            }
        });

        ImageButton rubyGemButton = (ImageButton) findViewById(R.id.ruby_gem);
        rubyGemButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView rubyNumView = (TextView) findViewById(R.id.ruby_num);
                currentGemInfo.updateInfo(0,0,0,1,0);
                rubyNumView.setText(Integer.toString(currentGemInfo.ruby));
            }
        });

        ImageButton sapphireGemButton = (ImageButton) findViewById(R.id.sapphire_gem);
        sapphireGemButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView sapphireNumView = (TextView) findViewById(R.id.sapphire_num);
                currentGemInfo.updateInfo(0,0,0,0,1);
                sapphireNumView.setText(Integer.toString(currentGemInfo.sapphire));
            }
        });
    }
}
