package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainFrame extends JFrame {
    public static final int ARRAY_SIZE = 4;
    public static final String IMAGE_PATH = "G:/java/OBJ/Stone-Maze/src/main/java/org/example/image/";
    private  int[][] imageDate = new int[ARRAY_SIZE][ARRAY_SIZE];
    // 空白色块
    private int row;// 行
    private int col;// 列
    private int step=0;
    // 成功
    private int[][] winData = new int[][]{
            {1,2,3,4},
            {5,6,7,8},
            {9,10,11,12},
            {13,14,15,0}
    };

    public MainFrame(){

        // 1、初始化窗口大小等信息
        FrameInit();
        // 4、打乱图片顺序
        RandomArrayInit();
        // 2、初始化界面信息，展示图片
        ImageInit();
        // 3、初始化系统菜单
        MenuInit();
        // 5、绑定方向按键
        KeyPressInit();
        // 设置界面可见
        this.setVisible(true);
    }

    private void FrameInit(){
        // 设置窗口的标题
        this.setTitle("Stone Maze V1.0");
        // 设置窗口的大小
        this.setSize(465,575);
        // 设置窗口的位置
        this.setLocationRelativeTo(null);
        // 设置窗口的关闭方式
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置布局方式为绝对位 置定位
        this.setLayout(null);
    }

    // 在窗口上展示图片
    private void ImageInit(){
        // 清空图层
        this.getContentPane().removeAll();

        // 显示步数 给窗口添加展示文字的组件
        JLabel stepShow = new JLabel("当前移动"+step+"步");
        stepShow.setBounds(40,20,100,20);
        // red
        stepShow.setForeground(Color.red);
        // 加粗
        stepShow.setFont(new Font("楷体",Font.BOLD,15));
        this.add(stepShow);


        // 判断
        if (isWin()){
            JLabel jLabel = new JLabel(new ImageIcon(IMAGE_PATH + "win.png"));
            jLabel.setBounds(100,200,260,80);
            this.add(jLabel);
        }
        
        for(int i = 0 ; i < imageDate.length ; i++){
            for(int j = 0 ; j < imageDate[i].length ; j++){
                // 获取图片名称
                String imageName = imageDate[i][j] + ".png";
                // 创建一个JLabel对象，设置图片给他展示
                JLabel label = new JLabel();
                // 设置图片到JLabel对象中去
                label.setIcon(new ImageIcon(IMAGE_PATH + imageName));
                // 设置图片的位置
                label.setBounds(30+j*100,60+i*100,100,100);
                // 把图片展示到窗口
                this.add(label);
            }
        }

        // 设置窗口的背景图片
        JLabel backgroundLabel = new JLabel(new ImageIcon(IMAGE_PATH + "background.png"));
        backgroundLabel.setBounds(0,0,450,484);
        this.add(backgroundLabel);

        // 刷新图层
        this.repaint();
    }

    private boolean isWin() {
        for (int i = 0; i < imageDate.length; i++) {
            for (int j = 0; j < imageDate[i].length; j++) {
                if (imageDate[i][j] != winData[i][j] ){
                    return false;
                }
            }
        }
        return true;
    }

    private void MenuInit(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("System");
        JMenuItem exit = new JMenuItem("Exit");
        menu.add(exit);
        exit.addActionListener(e -> {
            dispose();
        });

        JMenuItem restart = new JMenuItem("Restart");
        menu.add(restart);
        restart.addActionListener(e -> {
            RandomArrayInit();
            step=0;
            ImageInit();

        });
        menuBar.add(menu);
        this.setJMenuBar(menuBar);
    }

    // 随机数组
    private void RandomArrayInit(){
        List<Integer> array = new ArrayList<>();
        for (int i = 0; i < ARRAY_SIZE*ARRAY_SIZE; i++) {
            array.add(i);
        }
        // 打乱列表顺序
        Collections.shuffle(array);
        // 将打乱后的列表元素填充到二维数组
        int index = 0;
        for (int i = 0; i < imageDate.length; i++) {
            for (int j = 0; j < imageDate.length; j++) {
                // 填充到二维数组
                imageDate[i][j] =array.get(index++);

                // 定位空白色块
                if(imageDate[i][j] == 0 ){
                    row = i;
                    col = j;
                }
            }
        }
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
                        switchAndMove(Direction.DOWN);
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
//                System.out.println("UP");
                // cow<3
                if (row < imageDate.length - 1) {
                    int temp = imageDate[row][col];
                    imageDate[row][col] = imageDate[row + 1][col];
                    imageDate[row + 1][col] = temp;
                    row++;
                    step++;
                }
                break;
            case DOWN:
                // row>0
//                System.out.println("DOWN");
                if (row > 0) {
                    int temp = imageDate[row][col];
                    imageDate[row][col] = imageDate[row - 1][col];
                    imageDate[row - 1][col] = temp;
                    row--;
                    step++;
                }
                break;
            case LEFT:
                // col<3
//                System.out.println("LEFT");
                if (col < imageDate[0].length - 1) {
                    int temp = imageDate[row][col];
                    imageDate[row][col] = imageDate[row][col + 1];
                    imageDate[row][col + 1] = temp;
                    col++;
                    step++;
                }
                break;
            case RIGHT:
                // col>0
//                Syst em.out.println("RIGHT");
                if (col > 0) {
                    int temp = imageDate[row][col];
                    imageDate[row][col] = imageDate[row][col - 1];
                    imageDate[row][col - 1] = temp;
                    col--;
                    step++;

                }
                break;
        }

        // 刷新
        ImageInit();
    }
}
