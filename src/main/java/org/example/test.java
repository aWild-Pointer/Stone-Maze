package org.example;

import org.example.utils.Constant;
import org.example.utils.GameManager;
import org.example.utils.TimerManager;

import javax.swing.*;
import java.awt.*;

import static org.example.utils.Constant.IMAGE_PATH;

public class test extends JFrame {
    public int[][] imageDate = new int[Constant.ARRAY_SIZE][Constant.ARRAY_SIZE];

    public void init() {
        System.out.println("Image init");

        // 清空图层
        this.getContentPane().removeAll();


//        mainFrame.pauseLabel = null;

        for (int i = 0; i < imageDate.length; i++) {
            for (int j = 0; j < imageDate[i].length; j++) {
                // 获取图片名称
                String imageName = imageDate[i][j] + ".png";
                // 创建一个JLabel对象，设置图片给他展示
                JLabel label = new JLabel();
                // 设置图片到JLabel对象中去
                label.setIcon(new ImageIcon(Constant.IMAGE_PATH + imageName));
                // 设置图片的位置
                label.setBounds(30 + j * 100, 60 + i * 100, 100, 100);
                // 把图片展示到窗口
                this.add(label);
            }
        }

//         设置窗口的背景图片
        JLabel backgroundLabel = new JLabel(new ImageIcon(Constant.IMAGE_PATH + "background.png"));
        backgroundLabel.setBounds(0, 0, 450, 484);
        this.add(backgroundLabel);

        Container parentContainer = backgroundLabel.getParent();
        System.out.println("Label is in container: " + parentContainer.getClass().getName());

        // 刷新图层
        this.revalidate();
        this.repaint();
    }

    public JLabel SetImage(String path, test testing) {
        System.out.println("image: " + path);
        JLabel imageLabel = new JLabel(new ImageIcon(path));
        imageLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
//        mainFrame.getLayeredPane().add(imageLabel);
        testing.getContentPane().add(imageLabel);
//        this.setComponentZOrder(imageLabel,0);
        // 刷新图层
        this.revalidate();
        this.repaint();
        Container parentContainer = imageLabel.getParent();
        System.out.println("Label is in container: " + parentContainer.getClass().getName());
        return imageLabel;
    }

    public static void main(String[] args) {
        test test1 = new test();
        test1.setTitle("Stone Maze V2.0");
        // 设置窗口的大小
        test1.setSize(465, 565);
        // 设置窗口的位置
        test1.setLocationRelativeTo(null);
        // 设置窗口的关闭方式
        test1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置布局方式为绝对位 置定位
        test1.setLayout(null);

        test1.setVisible(true);

        test1.init();
        test1.SetImage(IMAGE_PATH + "pause.png", test1);
//        test1.init();
//        test1.getContentPane().removeAll();


    }
}
