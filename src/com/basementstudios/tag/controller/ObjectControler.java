package com.basementstudios.tag.controller;

import com.basementstudios.tag.ResourceManager;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.level.Level;
import com.basementstudios.tag.mob.Mob;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author James Bray
 */
public class ObjectControler<T extends Mob> {

    protected Level level;
    protected ArrayList<T> charaList = new ArrayList<T>();

    protected T selectedMob = null;
    protected T attackMob = null;
    private int atackIndex = 0;
    private boolean atacking = false;


    public ObjectControler(Level level) {
        this.level = level;
    }

    public void select(T selectedMob) {
        this.selectedMob = selectedMob;
    }

    /**
     * Sets the controller up for attack mode
     */
    public void atack() {
        atacking = true;
        atackIndex = 0;
        attackMob = charaList.get(0);
    }

    /**
     * Sets the controller to be attacked
     */
    public void defending() {
        atacking = false;
        atackIndex = 0;
        attackMob = charaList.get(0);
    }

    /**
     * Happens at the end of ever turn, updates all attached mobs
     */
    public void turnTick() {
        for (T mob : charaList) {
            mob.turnTick();
        }
    }

    public Mob getActiveMob() {
        if (atacking) return selectedMob;
        else return attackMob;
    }

    public T getNext(int turn, int maxSpd) {
        int i = 0;
        for (T player : charaList) {
            if (turn % (maxSpd - player.getCharacterData().getSpd()) == 0 && !player.hasGone) {
                player.hasGone = true;
                return player;
            }
            i++;
        }
        return null;
    }

    public int getMaxSpeed() {
        int maxSpd = 0;
        for (Mob player : charaList) {
            if (player.getCharacterData().getSpd() > maxSpd)
                maxSpd = player.getCharacterData().getSpd();
        }
        return maxSpd;
    }

    /**
     * Renders the arrows above mobs heads, colour depended on the mode the
     * Controller is in
     *
     * @param bm
     */
    public void render(Bitmap bm) {
        if (selectedMob != null && atacking) {
            int yOffs = (int) (Math.sin(System.currentTimeMillis() % 250.0 / 100.0) * 5.0);
            int xp = (int) (selectedMob.getX() + (128 - ResourceManager.i.entitiesSpriteSheet.getSpriteWidth()) / 2);
            int yp = (int) (selectedMob.getY() - 30) + yOffs;
            bm.render(ResourceManager.i.entitiesSpriteSheet.getSprites()[0][0], xp, yp, 0xffffff);
        }

        if (attackMob != null && !atacking) {
            int yOffs = (int) (Math.sin(System.currentTimeMillis() % 250.0 / 100.0) * 5.0);
            int xp = (int) (attackMob.getX() + (128 - ResourceManager.i.entitiesSpriteSheet.getSpriteWidth()) / 2);
            int yp = (int) (attackMob.getY() - 30) + yOffs;
            bm.render(ResourceManager.i.entitiesSpriteSheet.getSprites()[1][0], xp, yp, 0xffffff);
        }
    }

    /**
     * Returns the mob to do the attacking
     *
     * @return
     */
    public Mob getSelectedMob() {
        return selectedMob;
    }

    /**
     * Sets the Mob to be attacked
     *
     * @param idDelta
     */
    public void selectAtack(int idDelta) {
        atackIndex += idDelta;
        System.out.println(atackIndex);
        attackMob = charaList.get(Math.abs(atackIndex % charaList.size()));
    }

    /**
     * Returns the Mob to be attacked
     *
     * @return
     */
    public Mob getAttackMob() {
        return attackMob;
    }

    public ArrayList<T> getCharaList() {
        return charaList;
    }

    public void tick() {
        for (T player : charaList) {
            player.movePlayer();
        }
    }

    public void checkDead() {
        for (Iterator<T> iterator = charaList.iterator(); iterator.hasNext(); ) {
            T mob = iterator.next();
            if (mob.getCharacterData().getCurrentHealth() <= 0) {
                mob.onDied();
                iterator.remove();
            }
        }
    }

    public void addMob(T mob) {
        level.add(mob);
        charaList.add(mob);
    }
}