package com.example.boyinzhang.splendorandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import com.example.boyinzhang.splendorandroid.Model.Gem;
import com.example.boyinzhang.splendorandroid.Model.utils.GemInfo;

import java.io.Serializable;

import static com.example.boyinzhang.splendorandroid.View.ImageUtil.*;

public class GameActivity extends Activity{
    Game game;
    GemInfo currentGemInfo;
    Card selectedCard;
    LinearLayout[] CardsLayout;
    TextView[][] PlayerGemsInfo;
    TextView[][] PlayerCollectInfo;
    TextView[] PlayerTag;

    public final static String KEY = "com.boyin.game";

    public Game getGame(){
        return this.game;
    }

    public GemInfo getCurrentGemInfo(){
        return this.currentGemInfo;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linkMembers();
        this.game = new Game();
        currentGemInfo = new GemInfo(0);
        initialCards();
        addGemButtonListeners();
        addButtonListeners();
        updateScoreBoard();


    }

    private void updateScoreBoard() {
        int currentPlayerIndex = game.getCurrentPlayer().getPlayerId()-1;

        for(int i = 0; i<4;i++){
            PlayerTag[i].setBackgroundColor(Color.RED);
            PlayerGemsInfo[i][0].setText(String.valueOf(game.getPlayers()[i].getGolds()));
            PlayerGemsInfo[i][1].setText(String.valueOf(game.getPlayers()[i].getGems().diamond));
            PlayerGemsInfo[i][2].setText(String.valueOf(game.getPlayers()[i].getGems().emerald));
            PlayerGemsInfo[i][3].setText(String.valueOf(game.getPlayers()[i].getGems().onyx));
            PlayerGemsInfo[i][4].setText(String.valueOf(game.getPlayers()[i].getGems().ruby));
            PlayerGemsInfo[i][5].setText(String.valueOf(game.getPlayers()[i].getGems().sapphire));

            PlayerCollectInfo[i][0].setText(String.valueOf(game.getPlayers()[i].getCards().diamond));
            PlayerCollectInfo[i][1].setText(String.valueOf(game.getPlayers()[i].getCards().emerald));
            PlayerCollectInfo[i][2].setText(String.valueOf(game.getPlayers()[i].getCards().onyx));
            PlayerCollectInfo[i][3].setText(String.valueOf(game.getPlayers()[i].getCards().ruby));
            PlayerCollectInfo[i][4].setText(String.valueOf(game.getPlayers()[i].getCards().sapphire));
        }
        PlayerTag[currentPlayerIndex].setBackgroundColor(Color.GREEN);
    }

    private void linkMembers() {
        new Thread(new Runnable(){
            @Override
            public void run() {
                LinearLayout cardBoard = (LinearLayout) findViewById(R.id.card_board);
                CardsLayout = new LinearLayout[3];
                CardsLayout[0] = (LinearLayout) cardBoard.getChildAt(0);
                CardsLayout[1] = (LinearLayout) cardBoard.getChildAt(1);
                CardsLayout[2] = (LinearLayout) cardBoard.getChildAt(2);
                PlayerGemsInfo = new TextView[4][6];
                PlayerCollectInfo = new TextView[4][5];
                PlayerTag = new TextView[4];
                LinearLayout scoreBoard = (LinearLayout) findViewById(R.id.score_board);
                for (int i=0;i<4;i++){
                    LinearLayout tmpScoreBoard = (LinearLayout) scoreBoard.getChildAt(i);
                    LinearLayout tmpGemsBoard = (LinearLayout) tmpScoreBoard.getChildAt(1);
                    for(int j=0;j<6;j++){
                        PlayerGemsInfo[i][j] = (TextView) tmpGemsBoard.getChildAt(j+1);
                    }
                    LinearLayout tmpCollectBoard = (LinearLayout) tmpScoreBoard.getChildAt(2);
                    for (int m=0;m<5;m++){
                        PlayerCollectInfo[i][m]=(TextView) tmpCollectBoard.getChildAt(m+2);
                    }
                    LinearLayout tmpLayout = (LinearLayout) tmpScoreBoard.getChildAt(0);
                    PlayerTag[i] = (TextView) tmpLayout.getChildAt(0);


                }
            }

        }).start();

    }

    private void initialCards() {
        new Thread() {
            public void run() {
                //这儿是耗时操作，完成之后更新UI；
                runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                        //更新UI
                        for(int i =0; i<3;i++){
                            for (int j=0;j<4;j++){
                                LinearLayout tmpCardBoard = (LinearLayout) CardsLayout[i].getChildAt(j+1);
                                ImageView cardView = (ImageView) tmpCardBoard.getChildAt(0);

                                cardView.setImageDrawable(drawCard(game.getGameBoard().getCards()[i][j]));
                                cardView.setScaleType(ImageView.ScaleType.FIT_XY);
                                final int finalI = i;
                                final int finalJ = j;
                                cardView.setOnClickListener(new Button.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        selectedCard = game.getGameBoard().getCards()[finalI][finalJ];
                                    }
                                });
                            }
                        }
                    }

                });
            }
        }.start();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.game= (Game) getIntent().getSerializableExtra(GameActivity.KEY);
    }

    private void addButtonListeners() {
        Button resetButton = (Button) findViewById(R.id.reset);
        Button collectButton = (Button) findViewById(R.id.collect);
        Button buyButton = (Button) findViewById(R.id.buy);
        Button reserveButton = (Button) findViewById(R.id.reserve);
        Button checkButton = (Button) findViewById(R.id.check);
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
                    Toast.makeText(GameActivity.this, "You cannot make this collection! Please try again!", Toast.LENGTH_LONG).show();
                }
                else{
                    currentGemInfo.reset();
                    game.turnToNextPlayer();
                    updateScoreBoard();
                }
            }
        });

        buyButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!game.getCurrentPlayer().buyCard(selectedCard, selectedCard.isReserved())){
                    Toast.makeText(GameActivity.this, "You cannot make this purchase! Please try again!", Toast.LENGTH_LONG).show();
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
                    else {
                        game.turnToNextPlayer();
                        updateScoreBoard();
                    }
                }
            }
        });

        reserveButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!game.getCurrentPlayer().reserveCard(selectedCard)){
                    Toast.makeText(GameActivity.this, "You cannot reserve this card! Please try again!", Toast.LENGTH_LONG).show();
                }
                else {
                    Card newCard = game.getGameBoard().getNewCard(selectedCard.getPosition()[0]);
                    game.getGameBoard().setCardOnBoard(newCard, selectedCard.getPosition());
                    selectedCard = null;
                    game.turnToNextPlayer();
                    updateScoreBoard();
                }


            }
        });

        checkButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(GameActivity.this,ReserveActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable(KEY,game);
                mainIntent.putExtras(mBundle);
                startActivity(mainIntent);
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
        if(card.getTargetGem()!=Gem.DIAMOND){
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
