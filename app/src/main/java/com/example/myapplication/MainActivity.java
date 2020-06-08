package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int cnt;
    private int hideEndScreen;
    private String[][] arr;
    private int xScore;
    private int oScore;
    Button btn[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cnt = 1;
        xScore = 0;
        oScore = 0;
        hideEndScreen = 0;
        arr = new String[3][3];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                arr[i][j] = "empty";
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private String getCurrentPlayer() {
        if(cnt % 2 == 0){
            return "O";
        }
        else {
            return "X";
        }
    }
    private String toggle(View v){
        Button b = (Button)(v);

        String strToReturn = "";
        if(cnt % 2 == 0){
            b.setText(getCurrentPlayer());
            strToReturn = getCurrentPlayer();
            TextView res = (TextView) findViewById(R.id.currentPlayer);
            res.setText("X's Turn");
        }
        else{
            b.setText(getCurrentPlayer());
            strToReturn = getCurrentPlayer();
            TextView res = (TextView) findViewById(R.id.currentPlayer);
            res.setText("O's Turn");
        }
        cnt++;
        return strToReturn;
    }

    private boolean isSame(String[] arr){
        String str = "";
        boolean res = true;
        if(arr[0] == "empty" || arr[1] == "empty" || arr[1] == "empty"){
            return false;
        }
        else{
            for(int i = 0; i < arr.length - 1; i++){
                if(!arr[i].equals(arr[i + 1])){
                    return false;
                }
            }
            return true;
        }
    }

    private void toggleButtons() {
        btn = new Button[9];
        btn[0] = (Button) findViewById(R.id.button1);
        btn[1] = (Button) findViewById(R.id.button2);
        btn[2] = (Button) findViewById(R.id.button3);
        btn[3] = (Button) findViewById(R.id.button4);
        btn[4] = (Button) findViewById(R.id.button5);
        btn[5] = (Button) findViewById(R.id.button6);
        btn[6] = (Button) findViewById(R.id.button7);
        btn[7] = (Button) findViewById(R.id.button8);
        btn[8] = (Button) findViewById(R.id.button9);
        for(int i = 1; i <= btn.length; i++){
                btn[i - 1].setEnabled(true);
                btn[i - 1].setText("" + i);
        }
    }

    // Check for solutions

    private boolean checkHorizontal() {
        for(int i = 0; i < arr.length; i++){
            if(isSame(arr[i])){
                return true;
            }
        }
        return false;
    }

    private boolean checkVertical() {
        for(int i = 0; i < arr[0].length; i++){
            String[] temp = {arr[0][i], arr[1][i], arr[2][i]};
            if(isSame(temp)){
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonals() {
        String[] dr = {arr[0][0], arr[1][1], arr[2][2]};
        String[] dl = {arr[0][2], arr[1][1], arr[2][0]};
        if(isSame(dr) || isSame(dl)){
            Log.d("debug", "Diagonal Found");
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isFull() {
        for(int i = 0; i < arr.length; i++){
            for(int j = 0; j < arr[0].length; j++){
                if(arr[i][j] == "empty"){
                    return false;
                }
            }
        }
        return true;
    }

    private String getLastPlayer() {
        if(getCurrentPlayer().equals("X")){
            return "O";
        }
        else{
            return "X";
        }
    }
    private void checkForEnd(){
        boolean isEnd = false;
        TextView o = (TextView) findViewById(R.id.oScore);
        TextView x = (TextView) findViewById(R.id.xScore);

        TextView tv = (TextView) findViewById(R.id.endText);
        if(checkHorizontal()){
            isEnd = true;
            tv.setText(getLastPlayer() + " won with a horizontal sequence");
            if(getCurrentPlayer().equals("X")){
                xScore++;
                x.setText("X: " + xScore);

            }
            else{
                oScore++;
                o.setText("O: " + oScore);
            }

        }
        else if(checkVertical()){
            isEnd = true;
            tv.setText(getLastPlayer() + " won with a vertical sequence");
            if(getCurrentPlayer().equals("X")){
                xScore++;
                x.setText("X: " + xScore);

            }
            else{
                oScore++;
                o.setText("O: " + oScore);
            }

        }
        else if(checkDiagonals()){
            isEnd = true;
            tv.setText(getLastPlayer() + " won with a diagonal sequence");
            if(getCurrentPlayer().equals("X")){
                xScore++;
                x.setText("X: " + xScore);

            }
            else{
                oScore++;
                o.setText("O: " + oScore);
            }
        }
        else if(isFull()){
            isEnd = true;
            TextView _tv = (TextView) findViewById(R.id.endText);
            _tv.setText("No one won");
        }

        if(isEnd){
            ConstraintLayout cl = (ConstraintLayout) findViewById(R.id.endView);
            cl.setVisibility(ConstraintLayout.VISIBLE);
            ConstraintLayout main = (ConstraintLayout) findViewById(R.id.main);
            main.setVisibility(ConstraintLayout.GONE);

            o.setVisibility(ConstraintLayout.INVISIBLE);
            x.setVisibility(ConstraintLayout.INVISIBLE);
        }
    }

    public void selected(View v){
        Button b = (Button) v;
        int val = Integer.valueOf(b.getText().toString());
        String str = toggle(v);
        arr[(val - 1)/ 3][(val - 1) % 3] = str;
        v.setEnabled(false);
        checkForEnd();
    }

    public void reset(View v){
        cnt = 0;
        hideEndScreen = 0;
        arr = new String[3][3];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                arr[i][j] = "empty";
            }
        }
        TextView o = (TextView) findViewById(R.id.oScore);
        TextView x = (TextView) findViewById(R.id.xScore);
        o.setVisibility(ConstraintLayout.VISIBLE);
        x.setVisibility(ConstraintLayout.VISIBLE);
        ConstraintLayout cl = (ConstraintLayout) findViewById(R.id.endView);
        cl.setVisibility(ConstraintLayout.INVISIBLE);
        ConstraintLayout _cl = (ConstraintLayout) findViewById(R.id.main);
        _cl.setVisibility(ConstraintLayout.VISIBLE);
        toggleButtons();
    }




}
