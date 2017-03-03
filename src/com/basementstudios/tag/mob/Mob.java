package com.basementstudios.tag.mob;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.Entity;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.graphics.SpriteSheet;
import com.basementstudios.tag.particle.TextParticle;
import com.basementstudios.tag.phys.AxisAlignedBB;

/**
 * A moving and dynamic character within the game.
 *
 * @author Jaspreet Dhanjan
 * @since Version 1.0 combat removed.
 */

public class Mob extends Entity {
    public boolean hasGone = false;
    protected int dmg, def, spd, spellDuration, wepponType, health, maxHealth;
    protected int debuffDamage, debuffDuration;
    protected String name;
    public boolean isAttacking = false;
    public boolean isRetracting = false;
    public int maxAttackFrame;

    public double xStart, yStart;

    protected AxisAlignedBB bb = new AxisAlignedBB();
    protected int lastWalkDist, walkDist;
    protected Mob target = null;

    public double xa, ya;

    public Mob(double x, double y, double xSize, double ySize) {
        bb.set(x, y, xSize, ySize);
        xStart = x;
        yStart = y;
    }

    public void attemptMove() {
        double xxa = bb.xPos + xa;
        double yya = bb.yPos + ya;

        AxisAlignedBB newBB = new AxisAlignedBB(xxa, yya, bb.xSize, bb.ySize);

        move(newBB);
    }

    private boolean move(AxisAlignedBB newBB) {
        if (isRemoved())
            return false;

        // TODO: AABB.contains() is broken – check y clipping.
        // if (!level.getBB().contains(newBB)) {
        // collide(null, newBB);
        // return false;
        // }

        lastWalkDist = walkDist;
        walkDist++;
        bb.set(newBB);
        return true;
    }

    protected void collide(Entity cause, AxisAlignedBB newBB) {
        xa = 0;
        ya = 0;
    }

    public boolean isMoving() {
        return walkDist != lastWalkDist;
    }

    public void render(Bitmap bm) {
        int xp = (int) bb.xPos;
        int yp = (int) bb.yPos;
        bm.render(getBitmap(), xp, yp, colour);
        bm.drawString(health + "/" + maxHealth, xp, yp + 128, 0xff0000);
        //bm.drawString(name, xp, yp, 0x000000e);
    }

    public AxisAlignedBB getBB() {
        return bb;
    }

    public void startAttack(int maxAttackFrame, Mob enemy) {
        isAttacking = true;
        isRetracting = false;
        this.maxAttackFrame = maxAttackFrame;
        target = enemy;
    }

    public void spellCast(int spellDamage, int speelDamageDuration) {
        this.debuffDamage = spellDamage;
        this.debuffDuration = speelDamageDuration;
    }

    public void turnTick() {
        spellHurt();
    }

    public void spellHurt() {
        System.out.println("Spell Hurt");
        int colour = 0x0f5b00;

        if (debuffDuration > 0) {
            health -= debuffDamage;
            level.add(new TextParticle("-" + debuffDamage, bb.xPos, bb.yPos, 2, colour));
            debuffDuration--;
        }
    }

    public void hurt(int dmg) {
        int colour = 0xff0000;

        System.out.println(health);
        level.add(new TextParticle("-" + dmg, bb.xPos, bb.yPos, 2, colour));
    }

    public void hit(int dmg) {
        int damage = dmg - def;

        if (damage < 0) {
            damage = 0;
        }
        health -= damage;

        hurt(damage);
    }

    public void movePlayer() {

    }

    public void onDied() {
        remove();
    }

    public int getDmg() {
        return dmg;
    }

    public int getDef() {
        return def;
    }

    public int getSpd() {
        return spd;
    }

    public int getSpellDuration() {
        return spellDuration;
    }

    public int getWepponType() {
        return wepponType;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public Mob getTarge() {
        return target;
    }

    public int getDebuffDamage() {
        return debuffDamage;
    }

    public int getDebuffDuration() {
        return debuffDuration;
    }

    public String getName() {
        return name;
    }

}