package com.main.tankwar;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.main.tankwar.Animations.AnimationManager;
import com.main.tankwar.enums.Direction;
import com.main.tankwar.enums.MapElementType;
import com.main.tankwar.gameobj.EnemyTank;
import com.main.tankwar.gameobj.GameObject;
import com.main.tankwar.gameobj.MapElement;
import com.main.tankwar.utlis.GameUtils;

import java.util.Objects;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder mSurfaceHolder;
    //绘图的Canvas
    private Canvas mCanvas;
    //子线程标志位
    private boolean mIsDrawing;

    // 图片资源
    private Bitmap elementGrass, elementSteel, elementWall, elementWater;
    private Bitmap playerTankU, playerTankD, playerTankL, playerTankR, playerBullet;
    private Bitmap enemy1TankU, enemy1TankD, enemy1TankL, enemy1TankR,enemyBullet;
    private Bitmap enemy2TankU, enemy2TankD, enemy2TankL, enemy2TankR;
    private Bitmap enemy3TankU, enemy3TankD, enemy3TankL, enemy3TankR;
    private Bitmap home;

    public static int cellSize,playerLiveCount;
    private GameController gc;
    private final GameActivity activity;
    private int currentLevel,maxLevel;
    private volatile boolean isRunning;//游戏线程是否运行

    // 显示信息
    private TextView tvEnemyCount, tvPlayerLive, currentLevelShow;
    private CustomImageButton vkUp, vkDown, vkLeft, vkRight;
    private Handler handler;

    // 默认构造传递到第三个
    public GameView(Context context) {
        this(context, null);
    }
    public GameView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        activity = (GameActivity) context;
        mIsDrawing = false;
        isRunning = true;
        initView();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        mIsDrawing = true;
        //开启子线程
        new Thread(this).start();
    }
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        mIsDrawing = false;
    }
    @Override
    public void run() {
        long lastDrawTime = System.nanoTime(); // 使用纳秒级时间
        final double nsPerUpdate = 1_000_000_000.0 / 60.0; // 每帧大约16.67毫秒
        while (mIsDrawing) {
            // 记录当前时间
            long now = System.nanoTime();

            // 更新游戏逻辑
            if (gc != null) {
                upDateGame();
            }
            // 计算从上次绘制到现在的时间差
            long timeThisFrame = now - lastDrawTime;
            // 如果这一帧花费的时间少于目标时间，则睡眠剩余的时间
            if (timeThisFrame < nsPerUpdate) {
                try {
                    Thread.sleep((long) ((nsPerUpdate - timeThisFrame) / 1_000_000.0));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // 更新 lastDrawTime 为这次绘制的时间
            lastDrawTime = System.nanoTime();
        }
    }

    //绘图逻辑，游戏更新
    private void upDateGame() {
        try {
            mCanvas = mSurfaceHolder.lockCanvas();//画布

            // 更新游戏
            if (vkUp != null && vkUp.isPressed()) gc.playerTank.move(Direction.UP);
            else if (vkDown != null && vkDown.isPressed()) gc.playerTank.move(Direction.DOWN);
            else if (vkLeft != null && vkLeft.isPressed()) gc.playerTank.move(Direction.LEFT);
            else if (vkRight != null && vkRight.isPressed()) gc.playerTank.move(Direction.RIGHT);
            //攻击按钮事件监听在flushText里
            gc.upDate();

            //绘图
            mCanvas.drawColor(Color.BLACK);
            drawMap(mCanvas);
            drawEnemies(mCanvas);
            drawPlayer(mCanvas);
            drawGrass(mCanvas);
            drawHome(mCanvas);
            // 绘制动画
            AnimationManager.drawAnimations(mCanvas);

            //判断是否游戏结束
            if (gc.playerTank.remainingLives <= -1) {
                gameOver();
                GameSoundManager.playGameOver();
            }
            if (gc.leftEnemyCount <= 0) gameSuccessful();

        } catch (Exception ignored) {

        } finally {
            if (mCanvas != null) {
                //释放canvas对象并提交画布
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    // 初始化View、开始游戏
    private void initView() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        setFocusable(true);
        setKeepScreenOn(true);
        setFocusableInTouchMode(true);

        cellSize = 38;
        // 加载资源
        loadBitmaps();
        loadAnimations();
        // 开始游戏
        newGame(1);
    }
    private void newGame(int level) {
        // 重置游戏控制器和其他必要的变量
        if(level==1) playerLiveCount = 5;
        gc = new GameController(level);//游戏控制中心
        currentLevel = level;//当前关卡
        maxLevel = 3;//最大关卡
        AnimationManager.reStart();//动画刷新

        // 设置绘制标志，开始绘制
        mIsDrawing = true;
        if(!isRunning) new Thread(this).start();
    }

    public void flushText() {
        // 初始化控件
        tvEnemyCount = activity.getTvEnemyCount();
        tvPlayerLive = activity.getTvPlayerLive();
        currentLevelShow = activity.getCurrentLevel();
        vkUp = activity.getVkUp();
        vkDown = activity.getVkDown();
        vkLeft = activity.getVkLeft();
        vkRight = activity.getVkRight();
        ImageButton vkAttack = activity.getVkAttack();
        vkAttack.setOnClickListener(view -> gc.playerTank.attack());
        handler = new Handler(Looper.getMainLooper());
        // 更新 TextView 内容
        // 再次调度这个 Runnable
        // 调用间隔s
        Runnable updateTextRunnable = new Runnable() {
            @Override
            public void run() {
                // 更新 TextView 内容
                tvPlayerLive.setText(String.valueOf(gc.playerTank.remainingLives));
                tvEnemyCount.setText(String.valueOf(gc.leftEnemyCount));
                currentLevelShow.setText(String.valueOf(currentLevel));
                // 再次调度这个 Runnable
                handler.postDelayed(this, 500);  // 调用间隔
            }
        };
        // 启动更新
        handler.post(updateTextRunnable);
    }
    private void loadBitmaps() {
        // 地图
        elementGrass = BitmapFactory.decodeResource(getResources(), R.drawable.element_grass);
        elementSteel = BitmapFactory.decodeResource(getResources(), R.drawable.element_steel);
        elementWall = BitmapFactory.decodeResource(getResources(), R.drawable.element_wall);
        elementWater = BitmapFactory.decodeResource(getResources(), R.drawable.element_water);
        // 玩家坦克
        playerTankU = BitmapFactory.decodeResource(getResources(), R.drawable.player_tank_up);
        playerTankD = BitmapFactory.decodeResource(getResources(), R.drawable.player_tank_down);
        playerTankL = BitmapFactory.decodeResource(getResources(), R.drawable.player_tank_left);
        playerTankR = BitmapFactory.decodeResource(getResources(), R.drawable.player_tank_right);
        // 子弹
        playerBullet = BitmapFactory.decodeResource(getResources(), R.drawable.player_bullet);
        enemyBullet = BitmapFactory.decodeResource(getResources(), R.drawable.enemy_bullet);
        // 敌人坦克
        enemy1TankU = BitmapFactory.decodeResource(getResources(), R.drawable.enemy_tank1_up);
        enemy1TankD = BitmapFactory.decodeResource(getResources(), R.drawable.enemy_tank1_down);
        enemy1TankL = BitmapFactory.decodeResource(getResources(), R.drawable.enemy_tank1_left);
        enemy1TankR = BitmapFactory.decodeResource(getResources(), R.drawable.enemy_tank1_right);
        enemy2TankU = BitmapFactory.decodeResource(getResources(), R.drawable.enemy_tank2_up);
        enemy2TankD = BitmapFactory.decodeResource(getResources(), R.drawable.enemy_tank2_down);
        enemy2TankL = BitmapFactory.decodeResource(getResources(), R.drawable.enemy_tank2_left);
        enemy2TankR = BitmapFactory.decodeResource(getResources(), R.drawable.enemy_tank2_right);
        enemy3TankU = BitmapFactory.decodeResource(getResources(), R.drawable.enemy_tank3_up);
        enemy3TankD = BitmapFactory.decodeResource(getResources(), R.drawable.enemy_tank3_down);
        enemy3TankL = BitmapFactory.decodeResource(getResources(), R.drawable.enemy_tank3_left);
        enemy3TankR = BitmapFactory.decodeResource(getResources(), R.drawable.enemy_tank3_right);
        // 家
        home = BitmapFactory.decodeResource(getResources(), R.drawable.home);
    }
    private void loadAnimations() {
        // 敌人生成
        AnimationManager.enemyBorn.clear();
        AnimationManager.enemyBorn.add(BitmapFactory.decodeResource(getResources(), R.drawable.enemy_born1));
        AnimationManager.enemyBorn.add(BitmapFactory.decodeResource(getResources(), R.drawable.enemy_born2));
        AnimationManager.enemyBorn.add(BitmapFactory.decodeResource(getResources(), R.drawable.enemy_born3));
        AnimationManager.enemyBorn.add(BitmapFactory.decodeResource(getResources(), R.drawable.enemy_born4));
        AnimationManager.enemyBorn.add(BitmapFactory.decodeResource(getResources(), R.drawable.enemy_born1));
        AnimationManager.enemyBorn.add(BitmapFactory.decodeResource(getResources(), R.drawable.enemy_born2));
        AnimationManager.enemyBorn.add(BitmapFactory.decodeResource(getResources(), R.drawable.enemy_born3));
        AnimationManager.enemyBorn.add(BitmapFactory.decodeResource(getResources(), R.drawable.enemy_born4));
        // 敌人被摧毁
        AnimationManager.enemyDestroy.clear();
        AnimationManager.enemyDestroy.add(BitmapFactory.decodeResource(getResources(), R.drawable.enemy_destory1));
        AnimationManager.enemyDestroy.add(BitmapFactory.decodeResource(getResources(), R.drawable.enemy_destory2));
        AnimationManager.enemyDestroy.add(BitmapFactory.decodeResource(getResources(), R.drawable.enemy_destory3));
        AnimationManager.enemyDestroy.add(BitmapFactory.decodeResource(getResources(), R.drawable.enemy_destory4));
        AnimationManager.enemyDestroy.add(BitmapFactory.decodeResource(getResources(), R.drawable.enemy_destory5));
        AnimationManager.enemyDestroy.add(BitmapFactory.decodeResource(getResources(), R.drawable.enemy_destory6));
        AnimationManager.enemyDestroy.add(BitmapFactory.decodeResource(getResources(), R.drawable.enemy_destory7));
        AnimationManager.enemyDestroy.add(BitmapFactory.decodeResource(getResources(), R.drawable.enemy_destory8));
        // 子弹摧毁
        AnimationManager.bulletDestroy.clear();
        AnimationManager.bulletDestroy.add(BitmapFactory.decodeResource(getResources(), R.drawable.bullet_destory1));
        AnimationManager.bulletDestroy.add(BitmapFactory.decodeResource(getResources(), R.drawable.bullet_destory2));
        AnimationManager.bulletDestroy.add(BitmapFactory.decodeResource(getResources(), R.drawable.bullet_destory3));
    }

    private void drawPlayer(Canvas c) {
        if (gc.playerTank.visible) {
            // 绘制玩家坦克
            Direction dir = gc.playerTank.dir;
            switch (dir) {
                case UP:
                    drawGameObject(c, playerTankU, gc.playerTank);
                    break;
                case DOWN:
                    drawGameObject(c, playerTankD, gc.playerTank);
                    break;
                case LEFT:
                    drawGameObject(c, playerTankL, gc.playerTank);
                    break;
                case RIGHT:
                    drawGameObject(c, playerTankR, gc.playerTank);
                    break;
            }
        }
        // 绘制玩家子弹s
        if (gc.playerTank.bullet.visible) {
            drawGameObject(c, playerBullet, gc.playerTank.bullet);
        }
    }
    private void drawEnemies(Canvas c) {
        // 绘制敌人坦克
        for (EnemyTank e : gc.enemies) {
            if (e.visible) {
                Direction dir = e.dir;
                switch (dir) {
                    case UP:
                        switch (e.type){
                            case ENEMY1:
                                drawGameObject(c, enemy1TankU, e);
                                break;
                            case ENEMY2:
                                drawGameObject(c, enemy2TankU, e);
                                break;
                            case ENEMY3:
                                drawGameObject(c, enemy3TankU, e);
                                break;
                        }
                        break;
                    case DOWN:
                        switch (e.type){
                            case ENEMY1:
                                drawGameObject(c, enemy1TankD, e);
                                break;
                            case ENEMY2:
                                drawGameObject(c, enemy2TankD, e);
                                break;
                            case ENEMY3:
                                drawGameObject(c, enemy3TankD, e);
                                break;
                        }
                        break;
                    case LEFT:
                        switch (e.type){
                            case ENEMY1:
                                drawGameObject(c, enemy1TankL, e);
                                break;
                            case ENEMY2:
                                drawGameObject(c, enemy2TankL, e);
                                break;
                            case ENEMY3:
                                drawGameObject(c, enemy3TankL, e);
                                break;
                        }
                        break;
                    case RIGHT:
                        switch (e.type){
                            case ENEMY1:
                                drawGameObject(c, enemy1TankR, e);
                                break;
                            case ENEMY2:
                                drawGameObject(c, enemy2TankR, e);
                                break;
                            case ENEMY3:
                                drawGameObject(c, enemy3TankR, e);
                                break;
                        }
                        break;
                }
            }
            // 敌人子弹
            if (e.bullet.visible) {
                drawGameObject(c, enemyBullet, e.bullet);
            }
        }
    }
    private void drawGrass(Canvas c) {
        for (MapElement e : gc.map) {
            if (Objects.requireNonNull(e.type) == MapElementType.GRASS) {
                drawGameObject(c, elementGrass, e);
            }
        }
    }
    private void drawMap(Canvas c) {
        for (MapElement e : gc.map) {
            switch (e.type) {
                case STEEL:
                    drawGameObject(c, elementSteel, e);
                    break;
                case WALL:
                    drawGameObject(c, elementWall, e);
                    break;
                case WATER:
                    drawGameObject(c, elementWater, e);
                    break;
            }
        }
    }
    private void drawHome(Canvas c) {
        c.drawBitmap(home, null, new Rect(gc.home.x, gc.home.y, gc.home.x + gc.home.size,
                gc.home.y + gc.home.size), null);
    }
    private void drawGameObject(Canvas c, Bitmap bitmap, GameObject g) {
        c.drawBitmap(bitmap, null, new Rect(g.x, g.y, g.x + g.size, g.y + g.size), null);
    }



    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private void gameOver() {
        mIsDrawing = false;
        isRunning = false;
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setTitle("Game Over")
                        .setMessage("游戏失败，是否重新进行游戏?")
                        .setPositiveButton("Yes", (dialog, which) -> newGame(1))
                        .setNegativeButton("No", (dialog, which) -> activity.finish())
                        .setCancelable(false)  // 禁止通过返回键取消
                        .setOnCancelListener(null)  // 设置空的取消监听器以防止意外关闭
                        .create();
                alertDialog.setCanceledOnTouchOutside(false);  // 禁止通过点击外部区域取消
                alertDialog.show();  // 显示对话框
            }
        });
    }
    private void gameSuccessful() {
        mIsDrawing = false;
        isRunning = false;
        if(currentLevel != maxLevel){
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle("Congratulations!")
                            .setMessage("恭喜通过本关,是否进入下一关?")
                            .setPositiveButton("Yes", (dialog, which) -> newGame(currentLevel + 1)) //下一关
                            .setNegativeButton("No", (dialog, which) -> activity.finish())
                            .setCancelable(false)  // 禁止通过返回键取消
                            .setOnCancelListener(null)  // 设置空的取消监听器以防止意外关闭
                            .create();
                    alertDialog.setCanceledOnTouchOutside(false);  // 禁止通过点击外部区域取消
                    alertDialog.show();  // 显示对话框
                }
            });
        }
        else{
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle("Congratulations!")
                            .setMessage("恭喜通关,您已完成所有关卡,是否从第一关重新开始?")
                            .setPositiveButton("Yes", (dialog, which) -> newGame(1)) //第一关
                            .setNegativeButton("No", (dialog, which) -> activity.finish())
                            .setCancelable(false)  // 禁止通过返回键取消
                            .setOnCancelListener(null)  // 设置空的取消监听器以防止意外关闭
                            .create();
                    alertDialog.setCanceledOnTouchOutside(false);  // 禁止通过点击外部区域取消
                    alertDialog.show();  // 显示对话框
                }
            });
        }
    }
}