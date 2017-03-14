package com.basementstudios.tag;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.controller.ObjectControler;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.level.Level;
import com.basementstudios.tag.mob.Enemy;
import com.basementstudios.tag.mob.Mob;
import com.basementstudios.tag.mob.Player;
import com.basementstudios.tag.screen.EndScreen;

import java.util.ArrayList;
import java.util.Random;

/**
 * Controls the game flow
 *
 * @author James Bray
 */
public class GameController {

    public static final int STATE_NULL = 0;
    public static final int STATE_PLAYER_ATACK = 1;
    public static final int STATE_PLAYER_ATACKING = 2;
    public static final int STATE_ENEMY_ATACK = 3;
    public static ArrayList<CharacterData> selectedCharacters = new ArrayList<CharacterData>(3);

    private static int charaGap = 100;
    private static int charaIndent = 100;

    private int turn = 0;
    private int maxSpd = 0;
    private Level level = null;
    private ObjectControler<Player> playerController;
    private ObjectControler<Enemy> enemyController;
    private Random rand = new Random();
    private ScreenManager screenManager;
    private int gameState = 0;

    public GameController(Level level) {
        this.level = level;
        playerController = new ObjectControler<Player>(level);
        enemyController = new ObjectControler<Enemy>(level);
    }


    /**
     * Handles input and processing and handles mob attack completion
     *
     * @param input
     */
    public void tick(Input input) {
        switch (gameState) {
            case STATE_PLAYER_ATACKING:
                if (playerController.getSelectedMob().attackState == Mob.NO_ATTACK) {
                    gameState = STATE_NULL;
                    getNext();
                }
                break;

            case STATE_PLAYER_ATACK:
                boolean moveUp = input.up.isClicked() || input.left.isClicked();
                boolean moveDown = input.down.isClicked() || input.right.isClicked();
                boolean clicked = input.enter.isClicked() || input.space.isClicked();

                if (moveUp)
                    enemyController.selectAtack(1);
                if (moveDown)
                    enemyController.selectAtack(-1);

                if (clicked && playerController.getSelectedMob().attackState == Mob.NO_ATTACK) {
                    playerController.getSelectedMob().startAttack(200, enemyController.getAttackMob());
                    gameState = STATE_PLAYER_ATACKING;
                }
                break;
            case STATE_ENEMY_ATACK:
                if (enemyController.getSelectedMob().attackState == Mob.NO_ATTACK) {
                    gameState = STATE_NULL;
                    getNext();
                }
                break;
        }
        playerController.tick();
        enemyController.tick();
    }

    /**
     * Starts the game
     */

    public void render(Bitmap bm) {
        playerController.render(bm);
        enemyController.render(bm);
    }


    public void startGame() {
        maxSpd = playerController.getMaxSpeed();
        if (enemyController.getMaxSpeed() > maxSpd)
            maxSpd = enemyController.getMaxSpeed();
        maxSpd++;
        turn++;

        getNext();
    }

    /**
     * Gets the next action to be performed
     */
    public void getNext() {
        playerController.checkDead();
        enemyController.checkDead();
        if (playerController.getCharaList().size() == 0)
            screenManager.setScreen(new EndScreen());
        else if (enemyController.getCharaList().size() == 0)
            screenManager.setScreen(new EndScreen(playerController.getCharaList()));
        else {
            Player player = playerController.getNext(turn, maxSpd);
            if (player != null) {
                gameState = STATE_PLAYER_ATACK;
                playerController.select(player);
                enemyController.defending();
                playerController.atack();
            } else {
                Enemy enemy = enemyController.getNext(turn, maxSpd);
                if (enemy != null) {
                    gameState = STATE_ENEMY_ATACK;
                    enemyController.select(enemy);
                    enemyController.atack();
                    playerController.defending();
                    playerController.selectAtack(rand.nextInt(3));
                    enemyController.getSelectedMob().startAttack(200, playerController.getAttackMob());
                } else {
                    nextTurn();
                }
            }
        }
    }

    public void nextTurn() {
        turn++;
        resetEntity();
        playerController.turnTick();
        enemyController.turnTick();
        getNext();
    }

    /**
     * Resets an entity at the end of a turn
     */
    public void resetEntity() {
        for (Mob player : playerController.getCharaList()) {
            player.hasGone = false;
        }

        for (Mob enemy : enemyController.getCharaList()) {
            enemy.hasGone = false;
        }
    }


    /**
     * Adds players
     */
    public void addPlayers() {
        int y = 70;

        if (Characters.getAvailable() == null) throw new RuntimeException("Characters not loaded!");
        int unlovedCharas = 3 - selectedCharacters.size();
        for (int i = 0; i < unlovedCharas; i++) {
            selectedCharacters.add(Characters.getAvailable().get(i));
            System.out.println(selectedCharacters.size());
        }

        for (int i = 0; i < 3; i++) {
            int x = 50;
            if (i == 1) {
                x += charaIndent;
            }
            Player player = new Player(x, y + charaGap * i, 1, 1, selectedCharacters.get(i));
            playerController.addMob(player);
        }
    }

    /**
     * Adds enemy
     *
     * @param seed
     */
    public void addEnemys(int seed) {
        int y = 70;
        ArrayList<String> names = new ArrayList<String>();
        names.add("AP and D");
        names.add("That guy who spoiled Logan");
        names.add("C++ GUIs");

        for (int i = 0; i < 3; i++) {
            int x = Game.WIDTH - 50 - ResourceManager.i.knightSpriteSheet.getSpriteWidth();
            int dmg = (2 + rand.nextInt(5)) * seed;
            int def = (2 + rand.nextInt(5)) * seed;
            int spd = (1 + rand.nextInt(10)) * seed;
            int spellDuration = 0;
            int health = 50 * seed;
            int weponType = CharacterData.NO_WEAPON;

            int wT = (0 + rand.nextInt(4)) * seed;

            switch (wT) {
                case 0:
                    weponType = CharacterData.NO_WEAPON;
                    break;
                case 1:
                    weponType = CharacterData.MELEE_WEAPON;
                    break;
                case 2:
                    weponType = CharacterData.RANGED_WEAPON;
                    break;
                case 3:
                    dmg = dmg / 2;
                    spellDuration = (1 + rand.nextInt(3)) * seed;
                    weponType = CharacterData.MAGIC_WEAPON;
            }


            String name = names.get(i);
            int type = rand.nextInt(3);
            CharacterData data = new CharacterData(0, name, type, health, health);
            data.setDmg(dmg);
            data.setSpd(spd);
            data.setDef(def);
            data.setSpellDuration(spellDuration);
            data.setWeaponType(weponType);

            if (i == 1) {
                x -= charaIndent;
            }

            Enemy enemy = new Enemy(x, y + charaGap * i, 1, 1, data);
            enemyController.addMob(enemy);
        }
        enemyController.selectAtack(0);
    }

    public int getTurn() {
        return turn;
    }

    public ObjectControler<Player> getPlayerController() {
        return playerController;
    }

    public ObjectControler<Enemy> getEnemyController() {
        return enemyController;
    }

    public int getGameState() {
        return gameState;
    }

    public void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }
}