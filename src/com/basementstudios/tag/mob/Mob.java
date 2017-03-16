package com.basementstudios.tag.mob;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.Entity;
import com.basementstudios.tag.GameController;
import com.basementstudios.tag.ResourceManager;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.particle.TextParticle;
import com.basementstudios.tag.resource.SpriteSheet;

/**
 * A moving and dynamic character within the game.
 *
 * @author Jaspreet Dhanjan
 */

public class Mob extends Entity {
    public static final int NO_ATTACK = 0;
    public static final int FORWARD_ATTACK = 1;
    public static final int ANIMATION_ATTACK = 2;
    public static final int RETRACT_ATTACK = 3;
    protected final CharacterData characterData;
    public boolean hasGone = false;

    public int attackState = 0;
    protected double x, y;
    protected double xa, ya;
    protected int lastWalkDist, walkDist;
    protected double xSize, ySize;
    protected int maxAttackFrame, animationFrame;

    protected int debuffDamage, debuffDuration;
    protected Mob target = null;

    protected double newX, newY;

    protected double xStart, yStart;

    protected float healthScale, turnScale;


    public Mob(double x, double y, double xSize, double ySize, CharacterData characterData) {
        this.x = x;
        this.y = y;
        this.xSize = xSize;
        this.ySize = ySize;
        this.characterData = characterData;
        newX = x;
        newY = y;

        xStart = x;
        yStart = y;
        healthScale = (float) 50 / (float) characterData.getCurrentHealth();
    }

    public static int getOffset(int weaponType, int type) {
        if (type == CharacterData.NECRO_TYPE)
            return 2;
        else {
            switch (weaponType) {
                case CharacterData.NO_WEAPON:
                    return 4;
                case CharacterData.MAGIC_WEAPON:
                    return 8;
                case CharacterData.RANGED_WEAPON:
                    return 6;
                case CharacterData.MELEE_WEAPON:
                    return 2;
            }
        }
        return 0;
    }

    public static SpriteSheet getSpriteSheet(int type) {
        switch (type) {
            case CharacterData.KNIGHT_TYPE:
                return ResourceManager.i.knightSpriteSheet;
            case CharacterData.MAGE_TYPE:
                return ResourceManager.i.mageSpriteSheet;
            case CharacterData.NECRO_TYPE:
                return ResourceManager.i.necromancerSpriteSheet;
            case CharacterData.ROGUE_TYPE:
                return ResourceManager.i.rougueSpriteSheet;
        }
        return null;
    }

    public void attemptMove() {
        double xxa = x + xa;
        double yya = y + ya;

        move(xxa, yya);
    }

    private boolean move(double xxa, double yya) {
        if (isRemoved()) return false;

        lastWalkDist = walkDist;
        walkDist++;
        x = xxa;
        y = yya;
        return true;
    }

    public boolean isMoving() {
        return walkDist != lastWalkDist;
    }

    public void render(Bitmap bm) {
        int xp = (int) x;
        int yp = (int) y;
        if (attackState == ANIMATION_ATTACK) {
            bm.render(getSpriteSheet().getSprites()[animationFrame][getOffset() + ySpriteIndex], xp, yp, colour);
        } else {
            bm.render(getBitmap(), xp, yp, colour);
        }
        yp = (int) y + ResourceManager.i.knightSpriteSheet.getSpriteHeight() + 16;
        // Draw the health bar
        bm.drawStringShadowed(characterData.getName(), xp + (getSpriteSheet().getSpriteWidth() - bm.getCharWidth(characterData.getName())) / 2, yp - 22, 0xffffff);
        bm.fill(xp + 40, yp - 3, xp + 32 + (50), yp, 0xff0000);
        bm.fill(xp + 40, yp - 3, Math.round(xp + 32 + (characterData.getCurrentHealth() * healthScale)), yp, 0xff00);

        int diffrence = GameController.maxSpd - characterData.getSpd();
        turnScale = (float) 50 / (float) diffrence;

        //draw speed bar
        bm.fill(xp + 40, yp - 6, xp + 32 + (50), yp - 3, 0xe9ff00);
        if (GameController.turn % diffrence != 0) {
            bm.fill(xp + 40, yp - 6, Math.round(xp + 32 +
                    ((diffrence - (GameController.turn % diffrence)) * turnScale)), yp - 3, 0x0002af);
        } else {
            bm.fill(xp + 40, yp - 6, xp + 32 + 50, yp - 3, 0xe9ff00);
        }

    }

    @Override
    public SpriteSheet getSpriteSheet() {
        return getSpriteSheet(characterData.getType());
    }

    public void startAttack(int maxAttackFrame, Mob enemy) {
        attackState = FORWARD_ATTACK;
        this.maxAttackFrame = maxAttackFrame;
        animationFrame = 0;
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
        int colour = 0x0f5b00;

        if (debuffDuration > 0) {
            int xp = (int) x;
            int yp = (int) y;
            characterData.setCurrentHealth(characterData.getCurrentHealth() - debuffDamage);
            level.add(new TextParticle("-" + debuffDamage, xp, yp, 2, colour));
            debuffDuration--;
        }
    }

    public void hurt(int dmg) {
        int colour = 0xff0000;
        int xp = (int) x;
        int yp = (int) y;
        level.add(new TextParticle("-" + dmg, xp, yp, 2, colour));
    }

    public void resetHealth() {
        characterData.setCurrentHealth(characterData.getInitialHealth());
    }

    public void hit(int dmg) {
        int damage = dmg - characterData.getDef();

        if (damage < 0) {
            damage = 0;
        }
        characterData.setCurrentHealth(characterData.getCurrentHealth() - damage);

        hurt(damage);
    }

    public void movePlayer() {

    }

    public int getOffset() {
        return getOffset(characterData.getWeaponType(), characterData.getType());
    }

    public void onDied() {
        remove();
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

    public CharacterData getCharacterData() {
        return characterData;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}