package org.example;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainFrame extends JFrame {
    public static final int ARRAY_SIZE = 4;
    public static final String IMAGE_PATH = "src/main/resources/image/";
    private  int[][] imageDate = new int[ARRAY_SIZE][ARRAY_SIZE];
    // 空白色块
    private int row;// 行
    private int col;// 列
    private int step=0;

    private JLabel timerLabel; // 计时器标签作为类成员
    private int timeElapsed;   // 记录经过的时间（秒）

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
        imageInit();
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

        // 初始化计时器标签
        timerLabel = new JLabel("Time: 0 seconds");
        timerLabel.setBounds(300, 20, 150, 20);  // 设置位置
        timerLabel.setForeground(Color.red);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 15));
        this.add(timerLabel);

        // 初始化计时器
        Timer timer = new Timer(1000, e -> {
            timeElapsed++;
            timerLabel.setText("Time: " + timeElapsed + " seconds");
        });
        timer.start();
    }

    // 在窗口上展示图片
    private void imageInit() {

        // 清空图层
        this.getContentPane().removeAll();

        // 重新添加计时器标签，确保它显示
        this.add(timerLabel);

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
        JMenu system = new JMenu("System");
        JMenu save = new JMenu("Save");
        JMenu load = new JMenu("Load");
        JMenu about = new JMenu("About");

        JMenuItem exit = new JMenuItem("Exit");
        system.add(exit);
        exit.addActionListener(e -> {
            dispose();
        });

        JMenuItem restart = new JMenuItem("Restart");
        system.add(restart);
        restart.addActionListener(e -> {
            RandomArrayInit();
            step=0;
            imageInit();

        });
        menuBar.add(system);

        // 为“Save”菜单添加保存功能
        JMenuItem saveItem = new JMenuItem("Local Save");
        save.add(saveItem);
        saveItem.addActionListener(e -> {
            saveGame();
        });
        menuBar.add(save);

        // 为“Load”菜单添加读取功能
        JMenuItem loadItem = new JMenuItem("Load Progress");
        load.add(loadItem);
        loadItem.addActionListener(e -> {
            loadGame();
            imageInit();
        });
        menuBar.add(load);


        // 为“About”菜单添加鼠标监听器
        about.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                JOptionPane.showMessageDialog(null,
                        "This is Stone-Maze version 1.0.\nDeveloped by aWild-Pointer.\nresource:https://github.com/aWild-Pointer/Stone-Maze",
                        "About",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        menuBar.add(about);

        this.setJMenuBar(menuBar);
    }

    private void loadGame() {
        File file = new File("save.txt");
        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "Save file not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        StringBuilder jsonData = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonData.append(line);
            }

            // 解析JSON数据
            JSONObject gameData = new JSONObject(jsonData.toString());

            // 提取step和二维数组
            step = gameData.getInt("step");

            JSONArray arrayData = gameData.getJSONArray("array");
            imageDate = new int[arrayData.length()][];
            for (int i = 0; i < arrayData.length(); i++) {
                JSONArray jsonRow = arrayData.getJSONArray(i);
                imageDate[i] = new int[jsonRow.length()];
                for (int j = 0; j < jsonRow.length(); j++) {
                    imageDate[i][j] = jsonRow.getInt(j);
                }
            }

            // 显示成功加载的消息
            JOptionPane.showMessageDialog(this, "Progress loaded successfully!");

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load progress!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void saveGame() {
        try {
            // 创建JSON对象
            JSONObject gameData = new JSONObject();

            // 将二维数组保存为JSONArray
            JSONArray arrayData = new JSONArray();
            for (int[] row : imageDate) {
                JSONArray jsonRow = new JSONArray();
                for (int value : row) {
                    jsonRow.put(value);
                }
                arrayData.put(jsonRow);
            }

            // 将step和二维数组添加到JSON对象中
            gameData.put("array", arrayData);
            gameData.put("step", step);

            // 将JSON写入文件
            try (FileWriter writer = new FileWriter("save.txt")) {
                writer.write(gameData.toString(4));  // 格式化输出，4为缩进级别
                JOptionPane.showMessageDialog(this, "Progress saved Successfully");
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save progress!", "Error", JOptionPane.ERROR_MESSAGE);
        }
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
        imageInit();
    }
}
