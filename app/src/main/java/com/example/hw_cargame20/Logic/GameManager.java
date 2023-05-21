package com.example.hw_cargame20.Logic;

public class GameManager {

    private int life;
    private int hitPoint;



    public GameManager(int life) {
        this.life = life;
        this.hitPoint = 0;

    }

    public void getHit() {

        hitPoint++;

    }

    public int getHitPoint() {
        return hitPoint;
    }

    public int getLife() {
        return life;
    }

    public int getCurrentLife() {
        return life - hitPoint;
    }

    public boolean isGameEnded() {
        if (getCurrentLife() == 0) {
            return true;
        } else
            return false;

    }


}
