package org.example.utils;

import javax.swing.*;
import java.awt.*;

import static org.example.utils.Constant.IMAGE_PATH;

public class TimerManager extends JFrame {
    public static JLabel timerLabel;// 计时器标签作为类成员
    public Timer timer;

    public TimerManager(GameManager game) {
        this.setTimerLabel();
        this.setTimer(game);
    }

    // 初始化计时器标签
    public void setTimerLabel() {
        timerLabel = new JLabel("Time: 0.0 S");
        timerLabel.setBounds(300, 20, 150, 20);  // 设置位置
        timerLabel.setForeground(Color.red);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 15));
        this.add(timerLabel);
    }

    // 初始化计时器
    public void setTimer(GameManager game) {
        timer = new Timer(100, e -> {
            game.timeElapsed += 0.1;
            timerLabel.setText(String.format("Time: %.1f s", game.timeElapsed));
        });
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void start() {
        timer.start();
    }
}
