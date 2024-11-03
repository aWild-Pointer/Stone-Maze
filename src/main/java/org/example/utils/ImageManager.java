package org.example.utils;

import org.example.MainFrame;

import javax.swing.*;
import java.awt.*;

import static org.example.utils.Constant.IMAGE_PATH;

public class ImageManager {
    public MainFrame mainFrame;
    public GameManager game;

    public ImageManager(MainFrame mainFrame, GameManager game) {
        this.mainFrame = mainFrame;
        this.game = game;
    }

    public void init() {
        System.out.println("Image init");

        // 清空图层
        mainFrame.getContentPane().removeAll();

        // 重新添加计时器标签，确保它显示
        mainFrame.add(TimerManager.timerLabel);

        // 重置暂停状态
        mainFrame.pauseLabel = null;

        // 显示步数 给窗口添加展示文字的组件
        JLabel stepShow = new JLabel("当前移动" + game.step + "步");
        stepShow.setBounds(40, 20, 100, 20);
        // red
        stepShow.setForeground(Color.red);
        // 加粗
        stepShow.setFont(new Font("楷体", Font.BOLD, 15));
        mainFrame.add(stepShow);

        // 判断
        if (game.isWin()) {
            JLabel jLabel = new JLabel(new ImageIcon(Constant.IMAGE_PATH + "win.png"));
            jLabel.setBounds(100, 200, 260, 80);
            mainFrame.add(jLabel);
        }

        for (int i = 0; i < game.imageDate.length; i++) {
            for (int j = 0; j < game.imageDate[i].length; j++) {
                // 获取图片名称
                String imageName = game.imageDate[i][j] + ".png";
                // 创建一个JLabel对象，设置图片给他展示
                JLabel label = new JLabel();
                // 设置图片到JLabel对象中去
                label.setIcon(new ImageIcon(Constant.IMAGE_PATH + imageName));
                // 设置图片的位置
                label.setBounds(30 + j * 100, 60 + i * 100, 100, 100);
                // 把图片展示到窗口
                mainFrame.add(label);
            }
        }

        // 设置窗口的背景图片
        JLabel backgroundLabel = new JLabel(new ImageIcon(Constant.IMAGE_PATH + "background.png"));
        backgroundLabel.setBounds(0, 0, 450, 484);
        mainFrame.add(backgroundLabel);

        Container parentContainer = backgroundLabel.getParent();
        System.out.println("Label is in container: " + parentContainer.getClass().getName());

        // 刷新图层
        mainFrame.repaint();
    }

    public JLabel setImage(String path) {
        System.out.println("image: " + path);
        JLabel imageLabel = new JLabel(new ImageIcon(path));
        imageLabel.setBounds(0, -25, mainFrame.getWidth(), mainFrame.getHeight());
        mainFrame.add(imageLabel, 0);
        // 刷新图层
        mainFrame.repaint();
//        Container parentContainer = imageLabel.getParent();
//        System.out.println("Label is in container: " + parentContainer.getClass().getName());
        return imageLabel;
    }

    public void clearImage(JLabel imageLabel) {

        // 从 LayeredPane 中移除图片标签
        mainFrame.remove(imageLabel);
        // 刷新界面，移除图片
        mainFrame.repaint();
        System.out.println("Image cleared");
    }


}
