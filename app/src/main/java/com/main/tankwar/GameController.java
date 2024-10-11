package com.main.tankwar;

import com.main.tankwar.Animations.AnimationManager;
import com.main.tankwar.enums.MapElementType;
import com.main.tankwar.gameobj.MapElement;
import com.main.tankwar.gameobj.EnemyTank;
import com.main.tankwar.gameobj.PlayerTank;
import com.main.tankwar.levels.Level;
import com.main.tankwar.levels.Level1;
import com.main.tankwar.levels.Level2;
import com.main.tankwar.levels.Level3;

import java.util.ArrayList;


public class GameController {
    // 游戏配置和参数
    static public int playerSpeed = 9;
    static public int bulletSpeed = 14;

    static public int enemy1Speed=9,enemy1RemainLives=0;// 移动快的敌人
    static public double enemy1P1=0.4,enemy1P2=0.6,enemy1P3=0.8;
    static public int enemy2Speed=6,enemy2RemainLives=1;// 普通敌人
    static public double enemy2P1=0.6,enemy2P2=0.4,enemy2P3=0.9;
    static public int enemy3Speed=3,enemy3RemainLives=2;// 移动慢的敌人
    static public double enemy3P1=0.5,enemy3P2=0.2,enemy3P3=1;

    // 地图元素
    public ArrayList<MapElement> map;
    public ArrayList<EnemyTank> enemies;
    public PlayerTank playerTank;
    public MapElement home;

    // 敌人生成相关
    public int leftEnemyCount;
    public int currentEnemyCount;
    private int maxEnemyCount;
    private int timer;
    private boolean isGenerating;
    private EnemyTank nextBornTank;

    public GameController(int level) {
        loadGame(level);
        onInit();
    }

    public void upDate() {
        // 玩家操作直接外部调用player类方法更新

        // 其余更新
        for (EnemyTank e : enemies) {
            e.timerAttack++;//敌人子弹计时器
            e.timerAI++;//敌人AI计时器
            e.AI();//敌人移动
            e.bulletMove();//敌人子弹移动;
        }
        playerTank.bulletMove();//我方子弹移动
        playerTank.timer++;//玩家计时器，控制子弹发射频率

        // 敌人生成
        generateEnemy();
    }

    private void onInit() {
        // 坦克初始化
        playerTank.setGC(this);
        for (EnemyTank e : enemies) {
            e.setGC(this);
        }
        // 设置家
        home = new MapElement(MapElementType.HOME, 13, 25);
        home.x += 5;
        home.y += 8;
        home.size = (int) (GameView.cellSize * 1.7);
        // 敌人生成相关配置 允许的最大敌人数量由loadGame()根据关卡决定
        leftEnemyCount = enemies.size();
        currentEnemyCount = 0;
        timer = 0;
        isGenerating = false;

        GameSoundManager.playGameStart();
    }

    private void loadGame(int level) {
        map = new ArrayList<>();
        enemies = new ArrayList<>();
        playerTank = new PlayerTank();
        // 根据level参数选择对应的关卡类、工厂模式
        Level levelToLoad;
        switch (level) {
            case 1:
                levelToLoad = new Level1();
                maxEnemyCount = 3;
                break;
            case 2:
                levelToLoad = new Level2();
                maxEnemyCount = 4;
                break;
            case 3:
                levelToLoad = new Level3();
                maxEnemyCount = 5;
                break;
            default:
                throw new IllegalArgumentException("Unsupported level: " + level);
        }
        // 加载关卡
        levelToLoad.loadLevel(map, enemies);
    }

    private void generateEnemy() {
        //如果可以生成敌人
        if (currentEnemyCount < maxEnemyCount) {
            //计时开始
            timer++;
            //播放一次性动画，锁定下一个要生成的敌人
            if (!isGenerating && timer>=0) {
                for (EnemyTank e : enemies) {
                    if (!e.visible && e.remainingLives >= 0) {
                        nextBornTank = e;//下一个敌人
                        AnimationManager.addEnemyBorn(nextBornTank.x, nextBornTank.y);//动画
                        isGenerating = true;
                        break;
                    }
                }
            }
            // 动画到敌人生成间隔间隔
            if (nextBornTank!=null && timer > 60) {
                nextBornTank.visible = true;
                nextBornTank = null;

                //两次敌人生成间隔
                if(currentEnemyCount==0){
                    timer = 0;
                }
                else{
                    timer = -120;
                }

                isGenerating = false;
                currentEnemyCount++;
            }
        }
    }
}
