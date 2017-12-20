package com.hb.javacad.main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;

import javax.swing.*;

import com.hb.javacad.cmdinterface.Command;
import com.hb.javacad.panel.ColorPanel;
import com.hb.javacad.panel.PaintPanel;
import com.hb.javacad.panel.ShapeButton;


/**
 * 长春易加科技有限公司
 * 为线束行业提供方便高效的接线方案
 */
public class MainFrame extends javax.swing.JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JMenuBar MenuBar;
    private JMenuItem newMenuItem;
    private ButtonGroup toolbuttonGroup;
    private JMenuItem backwardMenuItem;
    private JMenuItem forwardMenuItem;
    private JMenuItem dropMenuItem;
    private JCheckBoxMenuItem toolBoxCheckBoxMenuItem;
    private JCheckBoxMenuItem toolCheckBoxMenuItem;
    private ColorPanel colorPanel;
    private PaintPanel printPanel;
    private ShapeButton starButton;
    private ShapeButton fiveButton;
    private ShapeButton threeButton;
    private ShapeButton circleButton;
    private ShapeButton ellipseButton;
    private ShapeButton rectButton;
    private ShapeButton lineButton;
    private ShapeButton brokenLineButton;
    private ShapeButton selectButton;
    private JToolBar drawToolBar;
    private JButton newButton;
    private JButton saveButton;
    private JButton forwardButton;
    private JButton backButton;
    private JToolBar usefulToolBar;
    private JMenu helpMenu;
    private JMenu windowMenu;
    private JMenu editMenu;
    private JMenuItem exitMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem openMenuItem;
    private JMenu fileMenu;
    public static MainFrame mainFrame;
    public static int groupId = 1;

    /**
     * Auto-generated main method to display this JFrame
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                mainFrame = new MainFrame();
                mainFrame.setVisible(true);
            }
        });
    }

    public MainFrame() {
        super();
        this.setTitle("长春易加科技有限公司");
        initGUI();
        this.setLocationRelativeTo(null);
    }


    private void initGUI() {
        try {
            setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            this.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent evt) {
                    rootWindowClosing(evt);
                }
            });
            {
                {
                    toolbuttonGroup = new ButtonGroup();
                }
                usefulToolBar = new JToolBar();
                getContentPane().add(usefulToolBar, BorderLayout.NORTH);
                usefulToolBar.setPreferredSize(new java.awt.Dimension(392, 27));
                {
                    backButton = new JButton();
                    usefulToolBar.add(backButton);
                    backButton.setPreferredSize(new java.awt.Dimension(49, 23));
                    backButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("后退.jpg")));
                    backButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            backButtonActionPerformed(evt);
                        }
                    });
                }
                {
                    forwardButton = new JButton();
                    usefulToolBar.add(forwardButton);
                    forwardButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("前进.jpg")));
                    forwardButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            forwardButtonActionPerformed(evt);
                        }
                    });
                }
                {
                    saveButton = new JButton();
                    usefulToolBar.add(saveButton);
                    saveButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("保存.jpg")));
                    saveButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            saveButtonActionPerformed(evt);
                        }
                    });
                }
                {
                    newButton = new JButton();
                    usefulToolBar.add(newButton);
                    newButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("新建.jpg")));
                    newButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            newButtonActionPerformed(evt);
                        }
                    });
                }
                {
                    newButton = new JButton();
                    usefulToolBar.add(newButton);
                    newButton.setText("完成组");
                    newButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            group(evt);
                        }
                    });
                }
            }
            {
                drawToolBar = new JToolBar();
                getContentPane().add(drawToolBar, BorderLayout.WEST);
                drawToolBar.setPreferredSize(new java.awt.Dimension(62, 579));
                drawToolBar.setOrientation(SwingConstants.VERTICAL);
                {
                    selectButton = new ShapeButton();
                    drawToolBar.add(selectButton);
                    //选定
                    selectButton.setText("\u9009\u5b9a");
                    toolbuttonGroup.add(selectButton);
                    selectButton.setSelected(true);
                    selectButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            selectButtonActionPerformed(evt);
                        }
                    });
                }
                {
                    lineButton = new ShapeButton();
                    drawToolBar.add(lineButton);
                    //直线
                    lineButton.setText("\u76f4\u7ebf");
                    toolbuttonGroup.add(lineButton);
                    lineButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            lineButtonActionPerformed(evt);
                        }
                    });
                }
                {
                    brokenLineButton = new ShapeButton();
                    drawToolBar.add(brokenLineButton);
                    //折线
                    brokenLineButton.setText("折线");
                    toolbuttonGroup.add(brokenLineButton);
                    brokenLineButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            brokenLineButtonActionPerformed(evt);
                        }
                    });
                }
                {
                    rectButton = new ShapeButton();
                    drawToolBar.add(rectButton);
                    //矩形
                    rectButton.setText("\u77e9\u5f62");
                    toolbuttonGroup.add(rectButton);
                    rectButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            rectButtonActionPerformed(evt);
                        }
                    });
                }
                {
                    ellipseButton = new ShapeButton();
                    drawToolBar.add(ellipseButton);
                    ellipseButton.setText("\u692d\u5706");
                    toolbuttonGroup.add(ellipseButton);
                    ellipseButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            ellipseButtonActionPerformed(evt);
                        }
                    });
                }
                {
                    circleButton = new ShapeButton();
                    drawToolBar.add(circleButton);
                    circleButton.setText("\u6b63\u5706");
                    circleButton.setPreferredSize(new java.awt.Dimension(42, 26));
                    toolbuttonGroup.add(circleButton);
                    circleButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            circleButtonActionPerformed(evt);
                        }
                    });
                }
                {
                    threeButton = new ShapeButton();
                    drawToolBar.add(threeButton);
                    threeButton.setText("\u4e09\u89d2\u5f62");
                    toolbuttonGroup.add(threeButton);
                    threeButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            threeButtonActionPerformed(evt);
                        }
                    });
                }
                {
                    fiveButton = new ShapeButton();
                    drawToolBar.add(fiveButton);
                    fiveButton.setText("\u4e94\u8fb9\u5f62");
                    toolbuttonGroup.add(fiveButton);
                    fiveButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            fiveButtonActionPerformed(evt);
                        }
                    });
                }
                {
                    starButton = new ShapeButton();
                    drawToolBar.add(starButton);
                    starButton.setText("\u4e94\u89d2\u661f");
                    toolbuttonGroup.add(starButton);
                    starButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            starButtonActionPerformed(evt);
                        }
                    });
                }
                {
                    colorPanel = new ColorPanel();
                    drawToolBar.add(colorPanel);
                    colorPanel.setPreferredSize(new java.awt.Dimension(58, 337));
                    colorPanel
                            .addPropertyChangeListener(new PropertyChangeListener() {
                                public void propertyChange(PropertyChangeEvent evt) {
                                    colorPanelPropertyChange(evt);
                                }
                            });
                }
            }
            {
                printPanel = new PaintPanel();
                getContentPane().add(printPanel, BorderLayout.CENTER);
                printPanel.setPreferredSize(new java.awt.Dimension(441, 385));
            }
            {
                MenuBar = new JMenuBar();
                setJMenuBar(MenuBar);
                MenuBar.setPreferredSize(new java.awt.Dimension(392, 26));
                {
                    fileMenu = new JMenu();
                    MenuBar.add(fileMenu);
                    fileMenu.setText("\u6587\u4ef6");
                    fileMenu.setPreferredSize(new java.awt.Dimension(56, 21));
                    {
                        newMenuItem = new JMenuItem();
                        fileMenu.add(newMenuItem);
                        newMenuItem.setText("\u65b0\u5efa");
                        newMenuItem.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent evt) {
                                newMenuItemActionPerformed(evt);
                            }
                        });
                    }
                    {
                        openMenuItem = new JMenuItem();
                        fileMenu.add(openMenuItem);
                        openMenuItem.setText("\u6253\u5f00");
                        openMenuItem.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent evt) {
                                openMenuItemActionPerformed(evt);
                            }
                        });
                    }
                    {
                        saveMenuItem = new JMenuItem();
                        fileMenu.add(saveMenuItem);
                        saveMenuItem.setText("\u4fdd\u5b58");
                        saveMenuItem.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent evt) {
                                saveMenuItemActionPerformed(evt);
                            }
                        });
                    }
                    {
                        exitMenuItem = new JMenuItem();
                        fileMenu.add(exitMenuItem);
                        exitMenuItem.setText("\u9000\u51fa");
                        exitMenuItem.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent evt) {
                                exitMenuItemActionPerformed(evt);
                            }
                        });
                    }
                }
                {
                    editMenu = new JMenu();
                    MenuBar.add(editMenu);
                    editMenu.setText("\u7f16\u8f91");
                    editMenu.setPreferredSize(new java.awt.Dimension(56, 21));
                    {
                        dropMenuItem = new JMenuItem();
                        editMenu.add(dropMenuItem);
                        dropMenuItem.setText("\u5220\u9664");
                        dropMenuItem.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent evt) {
                                dropMenuItemActionPerformed(evt);
                            }
                        });
                    }
                    {
                        forwardMenuItem = new JMenuItem();
                        editMenu.add(forwardMenuItem);
                        forwardMenuItem.setText("\u524d\u8fdb");
                        forwardMenuItem.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent evt) {
                                forwardMenuItemActionPerformed(evt);
                            }
                        });
                    }
                    {
                        backwardMenuItem = new JMenuItem();
                        editMenu.add(backwardMenuItem);
                        backwardMenuItem.setText("\u540e\u9000");
                        backwardMenuItem
                                .addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent evt) {
                                        backwardMenuItemActionPerformed(evt);
                                    }
                                });
                    }
                }
                {
                    windowMenu = new JMenu();
                    MenuBar.add(windowMenu);
                    windowMenu.setText("\u7a97\u53e3");
                    windowMenu.setPreferredSize(new java.awt.Dimension(56, 21));
                    {
                        toolCheckBoxMenuItem = new JCheckBoxMenuItem();
                        windowMenu.add(toolCheckBoxMenuItem);
                        toolCheckBoxMenuItem
                                .setText("\u5e38\u7528\u5de5\u5177\u680f");
                        toolCheckBoxMenuItem.setSelected(true);
                        toolCheckBoxMenuItem
                                .addItemListener(new ItemListener() {
                                    public void itemStateChanged(ItemEvent evt) {
                                        toolCheckBoxMenuItemItemStateChanged(evt);
                                    }
                                });
                    }
                    {
                        toolBoxCheckBoxMenuItem = new JCheckBoxMenuItem();
                        windowMenu.add(toolBoxCheckBoxMenuItem);
                        toolBoxCheckBoxMenuItem.setText("\u5de5\u5177\u7bb1");
                        toolBoxCheckBoxMenuItem.setSelected(true);
                        toolBoxCheckBoxMenuItem
                                .addItemListener(new ItemListener() {
                                    public void itemStateChanged(ItemEvent evt) {
                                        toolBoxCheckBoxMenuItemItemStateChanged(evt);
                                    }
                                });
                    }
                }
                {
                    helpMenu = new JMenu();
                    MenuBar.add(helpMenu);
                    helpMenu.setText("\u5e2e\u52a9");
                    helpMenu.setPreferredSize(new java.awt.Dimension(56, 21));
                }
            }
            pack();
            this.setSize(611, 548);
            this.setBackground(Color.CYAN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 常用工具栏的显示与否
     *
     * @param evt 常用工具栏菜单的选择状态变化
     */
    private void toolCheckBoxMenuItemItemStateChanged(ItemEvent evt) {
        if (this.toolCheckBoxMenuItem.isSelected()) {
            this.usefulToolBar.setVisible(true);
        } else {
            this.usefulToolBar.setVisible(false);
        }
    }

    /**
     * 工具箱的显示与否
     *
     * @param evt 工具箱的菜单的选择状态变化
     */
    private void toolBoxCheckBoxMenuItemItemStateChanged(ItemEvent evt) {
        if (this.toolBoxCheckBoxMenuItem.isSelected()) {
            this.drawToolBar.setVisible(true);
        } else {
            this.drawToolBar.setVisible(false);
        }
    }


    /**
     * 获得画笔颜色
     *
     * @param evt
     */
    private void colorPanelPropertyChange(PropertyChangeEvent evt) {
        this.printPanel.setColor(this.colorPanel.getForwardColor());
        if (this.printPanel.getCommand() == Command.SELECT) {
            this.printPanel.changeColor();
        }
    }

    private void backButtonActionPerformed(ActionEvent evt) {
        this.printPanel.undo();
    }

    private void forwardButtonActionPerformed(ActionEvent evt) {
        this.printPanel.redo();
    }

    private void forwardMenuItemActionPerformed(ActionEvent evt) {
        this.printPanel.undo();
    }

    private void backwardMenuItemActionPerformed(ActionEvent evt) {
        this.printPanel.redo();
    }

    private void lineButtonActionPerformed(ActionEvent evt) {
        this.printPanel.setCommand(Command.LINE);
        this.printPanel.repaint();
    }

    private void brokenLineButtonActionPerformed(ActionEvent evt) {
        this.printPanel.setCommand(Command.BROKENLINE);
        this.printPanel.repaint();
    }

    private void selectButtonActionPerformed(ActionEvent evt) {
        this.printPanel.setCommand(Command.SELECT);
        this.printPanel.repaint();
    }

    private void rectButtonActionPerformed(ActionEvent evt) {
        this.printPanel.setCommand(Command.RECTANGLE);
        this.printPanel.repaint();
    }

    private void ellipseButtonActionPerformed(ActionEvent evt) {
        this.printPanel.setCommand(Command.ELLIPSE);
        this.printPanel.repaint();
    }

    private void circleButtonActionPerformed(ActionEvent evt) {
        this.printPanel.setCommand(Command.CIRCLE);
        this.printPanel.repaint();
    }

    private void threeButtonActionPerformed(ActionEvent evt) {
        this.printPanel.setCommand(Command.TRIANGLE);
        this.printPanel.repaint();
    }

    private void fiveButtonActionPerformed(ActionEvent evt) {
        this.printPanel.setCommand(Command.PENTAGON);
        this.printPanel.repaint();
    }

    private void starButtonActionPerformed(ActionEvent evt) {
        this.printPanel.setCommand(Command.FIVEPOINTS);
        this.printPanel.repaint();
    }

    //新建
    private void newButtonActionPerformed(ActionEvent evt) {
        this.printPanel.newPaintPanel();
    }

    //组号
    private void group(ActionEvent evt) {
        //this.printPanel.newPaintPanel();
        groupId++;
        System.out.println("点击组号了" + groupId);
    }

    private void newMenuItemActionPerformed(ActionEvent evt) {
        this.printPanel.newPaintPanel();
    }

    private void openMenuItemActionPerformed(ActionEvent evt) {
        this.printPanel.openImg();
    }

    private void saveMenuItemActionPerformed(ActionEvent evt) {
        this.printPanel.savePaint();
    }

    private void exitMenuItemActionPerformed(ActionEvent evt) {
        this.printPanel.exitPaint();
    }

    private void rootWindowClosing(WindowEvent evt) {
        this.printPanel.exitPaint();
    }

    private void dropMenuItemActionPerformed(ActionEvent evt) {
        this.printPanel.deleteShape();
    }

    private void saveButtonActionPerformed(ActionEvent evt) {
        this.printPanel.savePaint();
    }
}
