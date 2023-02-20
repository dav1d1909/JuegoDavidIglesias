package com.mygdx.game;

public class Constants {
    public static float PIXELS_IN_METERS = 45f;
    public static float PLAYER_SPEED = 2.5f;
    public static float ENEMY_SPEED = 1f;
    static void setEnemySpeed(float multi){
        ENEMY_SPEED*=multi;
    }


}
