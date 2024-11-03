package org.example;

import org.example.utils.Direction;
import org.example.utils.GameManager;
import org.example.utils.ImageManager;
import org.example.utils.TimerManager;


import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


import static org.example.utils.Constant.IMAGE_PATH;
import static org.example.utils.Direction.DOWN;

public class MainFrame extends JFrame {

    GameManager gameManager = new GameManager(this);
    ImageManager imageManager = new ImageManager(this, gameManager);
    TimerManager timerManager = new TimerManager(gameManager);

    public JLabel pauseLabel;

    public MainFrame(){

        // 1、初始化窗口大小等信息
        FrameInit();
        // 4、打乱图片顺序
        gameManager.RandomArrayInit();
        // 2、初始化界面信息，展示图片
        imageManager.init();
        // 3、初始化系统菜单
        MenuInit();
        // 5、绑定方向按键
        KeyPressInit();
        // 设置界面可见
        this.setVisible(true);
    }

    private void FrameInit(){
        // 设置窗口的标题
        this.setTitle("Stone Maze V2.0");
        // 设置窗口的大小
        this.setSize(465, 560);
        // 设置窗口的位置
        this.setLocationRelativeTo(null);
        // 设置窗口的关闭方式
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置布局方式为绝对位 置定位
        this.setLayout(null);
    }


    private void MenuInit(){
        JMenuBar menuBar = new JMenuBar();
        JMenu system = new JMenu("System");
        JMenu save = new JMenu("Save");
        JMenu load = new JMenu("Load");
        JMenu game = new JMenu("Game");
        JMenu about = new JMenu("About");

        JMenuItem exit = new JMenuItem("Exit");
        system.add(exit);
        exit.addActionListener(e -> {
            dispose();
        });

        JMenuItem restart = new JMenuItem("Restart");
        system.add(restart);
        restart.addActionListener(e -> {
            gameManager.RandomArrayInit();
            gameManager.step = 0;
            gameManager.timeElapsed = 0;
            imageManager.init();

        });
        menuBar.add(system);

        // 为“Save”菜单添加保存功能
        JMenuItem saveItem = new JMenuItem("Local Save");
        save.add(saveItem);
        saveItem.addActionListener(e -> {
            timerManager.stop();
            pauseLabel = imageManager.setImage(IMAGE_PATH + "pause.png");
            gameManager.saveGame();
        });
        menuBar.add(save);

        // 为“Load”菜单添加读取功能
        JMenuItem loadItem = new JMenuItem("Load Progress");
        load.add(loadItem);
        loadItem.addActionListener(e -> {
            gameManager.loadGame();
            imageManager.init();
            timerManager.stop();
            pauseLabel = imageManager.setImage(IMAGE_PATH + "pause.png");
        });
        menuBar.add(load);

        // 游戏开始、暂停
        JMenuItem start = new JMenuItem("start");
        game.add(start);
        start.addActionListener(e -> {
            System.out.println("start");
            if (pauseLabel != null) {
                timerManager.start();
                imageManager.clearImage(pauseLabel);
                pauseLabel = null;
            }

        });

        JMenuItem pause = new JMenuItem("pause");
        game.add(pause);
        pause.addActionListener(e -> {
            System.out.println("pause");
            if (pauseLabel == null) {
                timerManager.stop();
                pauseLabel = imageManager.setImage(IMAGE_PATH + "pause.png");
            }
        });

        menuBar.add(game);



        // 为“About”菜单添加鼠标监听器
        about.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                JOptionPane.showMessageDialog(null,
                        "This is Stone-Maze version 2.0.\nDeveloped by aWild-Pointer.\nresource:https://github.com/aWild-Pointer/Stone-Maze",
                        "About",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        menuBar.add(about);

        this.setJMenuBar(menuBar);
    }



    private void KeyPressInit(){
        // 绑定按键事件
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // 获取按钮编号
                int keyCode = e.getKeyCode();
                // 判断方向
                switch (keyCode) {
                    case KeyEvent.VK_UP:
                        switchAndMove(Direction.UP);
                        break;
                    case KeyEvent.VK_DOWN:
                        switchAndMove(DOWN);
                        break;
                    case KeyEvent.VK_LEFT:
                        switchAndMove(Direction.LEFT);
                        break;
                    case KeyEvent.VK_RIGHT:
                        switchAndMove(Direction.RIGHT);
                        break;
                }
            }
        });
    }

    // 监听并移动
    private void switchAndMove(Direction r) {
        // 控制图片移动
        switch (r) {
            case UP:
                System.out.println("UP");
                // cow<3
                gameManager.up();
                break;
            case DOWN:
                // row>0
                System.out.println("DOWN");
                gameManager.down();
                break;
            case LEFT:
                // col<3
                System.out.println("LEFT");
                gameManager.left();
                break;
            case RIGHT:
                // col>0
                System.out.println("RIGHT");
                gameManager.right();
                break;
        }

        // 刷新
        imageManager.init();
    }
}
