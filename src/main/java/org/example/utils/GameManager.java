package org.example.utils;

import org.example.MainFrame;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.example.utils.Constant.ARRAY_SIZE;

public class GameManager {
    MainFrame mainFrame;
    public int step;
    public double timeElapsed;
    // 空白色块
    public int row;// 行
    public int col;// 列

    public int[][] imageDate = new int[Constant.ARRAY_SIZE][Constant.ARRAY_SIZE];

    public GameManager(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public boolean isWin() {
        for (int i = 0; i < this.imageDate.length; i++) {
            for (int j = 0; j < this.imageDate[i].length; j++) {
                if (this.imageDate[i][j] != Constant.winData[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // 随机数组
    public void RandomArrayInit() {
        List<Integer> array = new ArrayList<>();
        for (int i = 0; i < ARRAY_SIZE * ARRAY_SIZE; i++) {
            array.add(i);
        }
        // 打乱列表顺序
        Collections.shuffle(array);
        // 将打乱后的列表元素填充到二维数组
        int index = 0;
        for (int i = 0; i < imageDate.length; i++) {
            for (int j = 0; j < imageDate.length; j++) {
                // 填充到二维数组
                imageDate[i][j] = array.get(index++);

                // 定位空白色块
                if (imageDate[i][j] == 0) {
                    row = i;
                    col = j;
                }
            }
        }
    }


    // 移动
    public void up() {
        // cow<3
        if (row < imageDate.length - 1) {
            int temp = imageDate[row][col];
            imageDate[row][col] = imageDate[row + 1][col];
            imageDate[row + 1][col] = temp;
            row++;
            step++;
        }
    }

    public void down() {
        if (row > 0) {
            int temp = imageDate[row][col];
            imageDate[row][col] = imageDate[row - 1][col];
            imageDate[row - 1][col] = temp;
            row--;
            step++;
        }
    }

    public void left() {
        // col<3
        if (col < imageDate[0].length - 1) {
            int temp = imageDate[row][col];
            imageDate[row][col] = imageDate[row][col + 1];
            imageDate[row][col + 1] = temp;
            col++;
            step++;
        }
    }

    public void right() {
        // col>0
        if (col > 0) {
            int temp = imageDate[row][col];
            imageDate[row][col] = imageDate[row][col - 1];
            imageDate[row][col - 1] = temp;
            col--;
            step++;
        }
    }


    // 加载游戏数据
    public void loadGame() {
        File file = new File("save.txt");
        if (!file.exists()) {
            JOptionPane.showMessageDialog(mainFrame, "Save file not found!", "Error", JOptionPane.ERROR_MESSAGE);
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
            timeElapsed = gameData.getInt("Time");

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
            JOptionPane.showMessageDialog(mainFrame, "Progress loaded successfully!");

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame, "Failed to load progress!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // 保持游戏数据
    public void saveGame() {
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
            gameData.put("Time", timeElapsed);

            // 将JSON写入文件
            try (FileWriter writer = new FileWriter("save.txt")) {
                writer.write(gameData.toString(4));  // 格式化输出，4为缩进级别
                JOptionPane.showMessageDialog(mainFrame, "Progress saved Successfully");
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame, "Failed to save progress!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
