package ru.kalinin.gui;

import ru.kalinin.cell.MarkStatus;
import ru.kalinin.cell.MineStatus;
import ru.kalinin.cell.RectangleCell;
import ru.kalinin.coordinate.DecCoord;
import ru.kalinin.logic.GameStatus;
import ru.kalinin.logic.SweeperGame;
import ru.kalinin.rules.LoseIfOpenBomb;
import ru.kalinin.rules.RulesOfGame;
import ru.kalinin.rules.WinIfOpenAllField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WindowsSweeper extends JFrame {
    private SweeperGame game;
    private List<RulesOfGame> rulesWin = new ArrayList<>();
    private List<RulesOfGame> rulesLose = new ArrayList<>();
    private JPanel jPanel;
    private Map<String, Image> imageMap = new HashMap<>(14);
    private int sizeX = 10;
    private int sizeY = 10;
    private int mines = 10;
    private final int IMAGE_SIZE = 50;

    public WindowsSweeper() {
        rulesWin.add(new WinIfOpenAllField());
        rulesLose.add(new LoseIfOpenBomb());
        game = new SweeperGame();
        game.startGame(sizeX, sizeY, mines, rulesWin, rulesLose);
        setImage();
        initGamePanel();
        initFrame();
        pack();
    }

    private void initGamePanel() {
        jPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                for (RectangleCell rectangleCell : game.getField()) {
                    String nameImage;
                    if (rectangleCell.getMarkStatus().equals(MarkStatus.OPENED)) {
                        nameImage = rectangleCell.getMineStatus().toString();
                    } else {
                        nameImage = rectangleCell.getMarkStatus().toString();
                    }
                    g2.drawImage(imageMap.get(nameImage), rectangleCell.getDecCoord().getX() * IMAGE_SIZE,
                            rectangleCell.getDecCoord().getY() * IMAGE_SIZE, this);
                }
            }
        };
        jPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                DecCoord decCoord = new DecCoord(x, y);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    game.openCell(decCoord);
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    game.markedCell(decCoord);
                }
                if (e.getButton() == MouseEvent.BUTTON2)
                    game.startGame(sizeX, sizeY, mines, rulesWin, rulesLose);
                jPanel.repaint();
                repaint();
                showDialog();
            }
        });
        jPanel.setPreferredSize(new Dimension(sizeX * IMAGE_SIZE, sizeY * IMAGE_SIZE));
        add(jPanel, BorderLayout.SOUTH);
    }

    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("WindowsSweeper");
        setResizable(false);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setIconImage(getImage("icon"));
    }

    private void showDialog() {
        if (!game.getGameStatus().equals(GameStatus.PLAYING)) {
            int startAgain;
            startAgain = JOptionPane.showConfirmDialog(jPanel, game.getGameStatus() + "!!!   Play again?", "Game result.", JOptionPane.YES_NO_OPTION);
            if (startAgain == 0) {
                game.startGame(sizeX, sizeY, mines, rulesWin, rulesLose);
                jPanel.repaint();
            }
        }
    }

    private Image getImage(String name) {
        String filename = "/images/" + name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }

    private void setImage() {
        for (MineStatus mineStatus : MineStatus.values()) {
            imageMap.put(mineStatus.toString(), getImage(mineStatus.toString()));
        }
        imageMap.put("CLOSED", getImage("CLOSED"));
        imageMap.put("MARKED", getImage("MARKED"));
    }
}
