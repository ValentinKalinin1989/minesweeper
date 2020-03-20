package ru.kalinin.gui;

import ru.kalinin.cell.Cell;
import ru.kalinin.cell.MarkStatus;
import ru.kalinin.cell.MineStatus;
import ru.kalinin.cell.RectangleCell;
import ru.kalinin.coordinate.DecCoord;
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
    private final int COLS = 10;
    private final int ROWS = 10;
    private final int IMAGE_SIZE = 50;

    public static void main(String[] args) {
        new WindowsSweeper().setVisible(true);
    }

    private WindowsSweeper() {
        rulesWin.add(new WinIfOpenAllField());
        rulesLose.add(new LoseIfOpenBomb());
        game = new SweeperGame();
        game.startGame(10, 10, 10, rulesWin, rulesLose);
        setImage();
        initPanel();
        initFrame();
    }

    private void initPanel() {
        jPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                for (Cell cell: game.getField()) {
                    RectangleCell rectangleCell = (RectangleCell) cell;
                    String nameImage;
                    if (rectangleCell.getMarkStatus().equals(MarkStatus.OPENED)) {
                        nameImage = rectangleCell.getMineStatus().toString();
                    } else {
                        nameImage = rectangleCell.getMarkStatus().toString();
                    }
                    g2.drawImage(imageMap.get(nameImage), rectangleCell.getDecCoord().getX()*IMAGE_SIZE,
                            rectangleCell.getDecCoord().getY()*IMAGE_SIZE, this);
                }
            }
        };
        jPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX()/IMAGE_SIZE;
                int y = e.getY()/IMAGE_SIZE;
                DecCoord decCoordcoord = new DecCoord(x, y);
                if(e.getButton() == MouseEvent.BUTTON1)
                    game.openCell(decCoordcoord);
                if(e.getButton() == MouseEvent.BUTTON3)
                    game.markedCell(decCoordcoord);
                if(e.getButton() == MouseEvent.BUTTON2)
                    game.startGame(10, 10, 10, rulesWin, rulesLose);
                jPanel.repaint();
            }
        });
        jPanel.setPreferredSize(new Dimension(COLS * IMAGE_SIZE, ROWS * IMAGE_SIZE));
        add(jPanel);
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

    private Image getImage (String name) {
        String filename = "/images/" + name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }

    private void setImage() {
        for (MineStatus mineStatus: MineStatus.values()) {
            imageMap.put(mineStatus.toString(), getImage(mineStatus.toString()));
        }
        imageMap.put("CLOSED", getImage("CLOSED"));
        imageMap.put("MARKED", getImage("MARKED"));
    }
}
