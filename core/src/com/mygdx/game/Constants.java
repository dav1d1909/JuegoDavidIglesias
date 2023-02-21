package com.mygdx.game;

public class Constants {
    public static float PIXELS_IN_METERS = 45f;
    public static float PLAYER_SPEED = 2.5f;
    public static float ENEMY_SPEED = 1f;
    static void setEnemySpeed(float multi){
        if (ENEMY_SPEED == 1){
            ENEMY_SPEED=multi;
        }else if(ENEMY_SPEED == 1.5f){
            ENEMY_SPEED=multi;
        } else if(ENEMY_SPEED == 2){
            ENEMY_SPEED=multi;
        }else if(ENEMY_SPEED == 2.5){
            ENEMY_SPEED=multi;
        }

    }


}
