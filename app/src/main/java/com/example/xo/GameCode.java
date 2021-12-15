package com.example.xo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameCode extends AppCompatActivity implements View.OnClickListener {

    private TextView playerOneScore, playerTwoScore, playerStatus;
    private Button resetGame;

    private Button[][] buttons = new Button[3][3];

    private int scoreOne = 0;
    private int scoreTwo = 0;
    private int countButtons = 0;
    boolean followingPlayer = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_code);

        playerOneScore = findViewById(R.id.playerOneScore);
        playerTwoScore = findViewById(R.id.playerTwoScore);
        playerStatus = findViewById(R.id.playerStatus);

        resetGame = findViewById(R.id.resetGame);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "btn" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.resetGame);
        buttonReset.setOnClickListener(v -> {
            scoreOne = 0;
            scoreTwo = 0;
            playerStatus.setText("");
            resetBoard();
            updatePlayerScore();
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (followingPlayer) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        countButtons++;

        if (checkForWin()) {
            if(followingPlayer){
                scoreOne++;
                updatePlayerScore();
                Toast.makeText(this, "Player One won!", Toast.LENGTH_LONG).show();;
                resetBoard();
            }else{
                scoreTwo++;
                updatePlayerScore();
                Toast.makeText(this, "Player Two won!", Toast.LENGTH_LONG).show();;
                resetBoard();
            }
        } else if (countButtons == 9) {
            Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
            resetBoard();
        } else {
            followingPlayer = !followingPlayer;
        }

        if (scoreOne > scoreTwo){
            playerStatus.setText("Player One is leading!");
        }else if(scoreOne < scoreTwo){
            playerStatus.setText("Player Two is leading!");
        }else if(scoreOne == 0 && scoreTwo == 0) {
            playerStatus.setText("");
        }else{
            playerStatus.setText("It is a Draw!");
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void updatePlayerScore() {
        playerOneScore.setText(Integer.toString(scoreOne));
        playerTwoScore.setText(Integer.toString(scoreTwo));
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        countButtons = 0;
        followingPlayer = true;
    }

}