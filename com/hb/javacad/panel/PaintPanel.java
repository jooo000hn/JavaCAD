package com.hb.javacad.panel;

import com.hb.javacad.cmdinterface.Command;
import com.hb.javacad.file.Myfilter;
import com.hb.javacad.file.OpenFile;
import com.hb.javacad.file.SaveFile;
import com.hb.javacad.main.MainFrame;
import com.hb.javacad.shape.*;
import com.hb.javacad.shape.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class PaintPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

    /**
     *
     */
    private static final long serialVersionUID = -2175955416572421359L;

    //����press��release��Point
    private Point startPoint;
    private Point endPoint;

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    //undo��redo�Ĵ�ȡ��
    public ArrayList<ShapeSet> current = new ArrayList<>();
    public ArrayList<ShapeSet> groupShape = new ArrayList<>();
    public ArrayList<ArrayList<ShapeSet>> history = new ArrayList<>();

    //�������й��ߵĽӿ�
    private int command = Command.SELECT;

    //���ʵ���ɫ
    private Color color = Color.black;

    //ͼ���ܼ�¼���±�
    public int totalIndex = -1;

    //��ѡͼ���±�
    private int index = -1;

    //�ı��С��ͼ�ε��±�
    private int changeIndex = -1;

    //��������һ����ʾ����ͼ�ε�hotPoints
    private boolean change = false;

    //�Ƿ�ʼ���µ�ͼ��
    private boolean isPressed = false;

    //ѡ���ͼ��
    private ShapeSet selectedShape;

    //�Ƿ�ͼ����������ͼ�η�Χ֮��
    private boolean isInArea = false;

    public PaintPanel() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
    }

    /**
     * undo���˵�ǰһ��
     */
    public void undo() {
        if (totalIndex == 0) {
            totalIndex = -1;
            current.clear();
        }
        if (totalIndex > 0) {
            totalIndex--;
            current.clear();
            for (int i = 0; i < history.get(totalIndex).size(); i++) {
                current.add(history.get(totalIndex).get(i));
            }
        }
        changeIndex = -1;
        repaint();
    }

    /**
     * redoǰ������һ��
     */
    public void redo() {
        if (totalIndex < history.size() - 1) {
            totalIndex++;
            current.clear();
            for (int i = 0; i < history.get(totalIndex).size(); i++) {
                current.add((ShapeSet) history.get(totalIndex).get(i));
            }
        }
        changeIndex = -1;
        repaint();
    }

    /**
     * ˢ�����
     */
    public void paint(Graphics g) {
        Dimension size = getSize();
        int width = size.width;
        int height = size.height;
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);

        //����ѡ������ʱ,��������ͼ�θ�ΪUNSELECTED״̬
        if (this.command != Command.SELECT) {
            for (int i = 0; i < current.size(); i++) {
                current.get(i).setState(ShapeSet.UNSELECTED);
            }
        }
        //������ǰ��ͼ��
        ShapeSet htrShape = null;
        for (int i = 0; i < current.size(); i++) {
            htrShape = current.get(i);
            htrShape.draw(g, true);
        }

        if (isPressed) {
            switch (this.command) {
                case Command.LINE:
                    new Line(this.startPoint, this.endPoint, this.color, MainFrame.groupId).draw(g, true);
                    break;
                case Command.BROKENLINE:
                    //TODO ������Ҫ�����ߡ� �� ��
                    new Line(this.startPoint, this.endPoint, this.color, MainFrame.groupId).draw(g, true);
                    break;
                case Command.RECTANGLE:
                    new Rectangle(this.startPoint, this.endPoint, this.color, MainFrame.groupId).draw(g, true);
                    break;
                case Command.ELLIPSE:
                    new Ellipse(this.startPoint, this.endPoint, this.color).draw(g, true);
                    break;
                case Command.CIRCLE:
                    new Circle(this.startPoint, this.endPoint, this.color).draw(g, true);
                    break;
                case Command.TRIANGLE:
                    new Triangle(this.startPoint, this.endPoint, this.color).draw(g, true);
                    break;
                case Command.PENTAGON:
                    new Pentagon(this.startPoint, this.endPoint, this.color).draw(g, true);
                    break;
                case Command.FIVEPOINTS:
                    new FivePointsStar(this.startPoint, this.endPoint, this.color).draw(g, true);
                    break;
                case Command.SELECT:

                    ShapeSet changeShape = this.getChangSizeShape();

                    if (changeShape != null) {
                        changeShape.draw(g, true);
                        current.get(changeIndex).setState(ShapeSet.SELECTED);
                        current.get(changeIndex).draw(g, true);
                        //ԭʼͼ����ɫ���
                        ShapeSet old = current.get(index).catchShape();
                        old.setState(ShapeSet.SELECTED);
                        old.setColor(Color.gray);
                        old.draw(g, false);
                    } else {
                        selectedShape = whenSelect();
                        if (selectedShape != null) {

                            //TODO ����ͼ��֮�������
                            //ѡ�е�ͼ�ε�X,Y����
                            int selectedStartX = selectedShape.getStartPoint().x;
                            int selectedStartY = selectedShape.getStartPoint().y;

                            int selectedEndX = selectedShape.getEndPoint().x;
                            int selectedEndY = selectedShape.getEndPoint().y;

                            for (ShapeSet shapeSet : current) {
                                int shapeStartX = shapeSet.getStartPoint().x;
                                int shapeStartY = shapeSet.getStartPoint().y;

                                int shapeEndX = shapeSet.getEndPoint().x;
                                int shapeEndY = shapeSet.getEndPoint().y;

                                if (selectedStartX >= shapeStartX && selectedStartX <= shapeEndX && selectedStartY >= shapeStartY && selectedStartY <= shapeEndY) {
                                    //����ѡ��ͼ����Ҫ������ͼ�η�Χ֮��
                                    this.isInArea = true;
                                    Point newStartPoint = new Point();
                                    newStartPoint.setLocation(shapeEndX, shapeEndY);
                                    selectedShape.setStartPoint(newStartPoint);
                                }
                            }

                            selectedShape.draw(g, true);
                            current.get(index).setState(ShapeSet.SELECTED);
                            current.get(index).draw(g, true);
                            //ԭʼͼ����ɫ���
                            ShapeSet old = current.get(index).catchShape();
                            old.setState(ShapeSet.SELECTED);
                            old.setColor(Color.gray);
                            old.draw(g, false);
                        }
                    }
                    break;
            }
            //������������������ѡ��
            for (int i = 0; i < current.size(); i++) {
                current.get(i).setState(ShapeSet.UNSELECTED);
            }
        }
    }


    public void mouseClicked(MouseEvent e) {
        if (this.command == Command.SELECT) {
            this.showHotZoom(e.getPoint());
        }
    }

    public void mouseEntered(MouseEvent e) {
        this.requestFocus();
        if (this.command != Command.SELECT) {
            setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        } else {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    public void mouseExited(MouseEvent e) {
    }

    /**
     * �õ����pressʱ��startPoint
     * ���
     */
    public void mousePressed(MouseEvent e) {
        //����
        if (!(this.command == Command.BROKENLINE)) {
            this.startPoint = e.getPoint();
            this.isPressed = true;
        }

    }

    /**
     * �õ����releaseʱ��endPoint
     */
    public void mouseReleased(MouseEvent e) {
        this.endPoint = e.getPoint();
        this.isPressed = false;
        switch (this.command) {
            case Command.LINE:
                current.add(new Line(this.startPoint, this.endPoint, this.color, MainFrame.groupId));
                removeTail();
                history.add(copyCurrent());
                break;
            case Command.BROKENLINE:
                //���ﻭ���ߡ� �� ��
                if(current.size() > 0){
                    current.add(new Line(this.startPoint, this.endPoint, this.color, MainFrame.groupId));
                    this.startPoint = e.getPoint();
                }
                removeTail();
                history.add(copyCurrent());
                break;
            case Command.RECTANGLE:
                current.add(new Rectangle(this.startPoint, this.endPoint, this.color, MainFrame.groupId));
                removeTail();
                history.add(copyCurrent());
                break;
            case Command.ELLIPSE:
                current.add(new Ellipse(this.startPoint, this.endPoint, this.color));
                removeTail();
                history.add(copyCurrent());
                break;
            case Command.CIRCLE:
                current.add(new Circle(this.startPoint, this.endPoint, this.color));
                removeTail();
                history.add(copyCurrent());
                break;
            case Command.TRIANGLE:
                current.add(new Triangle(this.startPoint, this.endPoint, this.color));
                removeTail();
                history.add(copyCurrent());
                break;
            case Command.PENTAGON:
                current.add(new Pentagon(this.startPoint, this.endPoint, this.color));
                removeTail();
                history.add(copyCurrent());
                break;
            case Command.FIVEPOINTS:
                current.add(new FivePointsStar(this.startPoint, this.endPoint, this.color));
                removeTail();
                history.add(copyCurrent());
                break;
            case Command.SELECT:
                ShapeSet changeShape = this.getChangSizeShape();
                if (changeShape != null) {
                    changeShape.setColor(Color.BLUE);
                    current.add(changeShape);
                    current.remove(changeIndex);
                    removeTail();
                    history.add(copyCurrent());
                    changeIndex = current.size() - 1;
                } else {
                    ShapeSet shape = whenSelect();
                    if (shape != null) {
                        current.add(shape);
                        current.remove(index);
                        removeTail();
                        history.add(copyCurrent());
                        changeIndex = current.size() - 1;
                    }
                }

                // todo �ر� �����߳�ʵ��ѡ��ͼ����˸
                flickerThreadStart();
                break;
        }
        totalIndex = history.size() - 1;
        this.repaint();
    }

    /**
     * �õ�����϶�ʱ��endPoint
     */
    public void mouseDragged(MouseEvent e) {
        this.endPoint = e.getPoint();
        this.repaint();
    }

    /**
     * ��ʾͼ��HotPoints
     */
    public void mouseMoved(MouseEvent e) {
        if (this.command == Command.SELECT) {
            if (this.changeSize(e.getPoint()) <= -1) {
                this.showHotPoints(e.getPoint());
            }
        }
    }


    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public int getCommand() {
        return command;
    }

    public ArrayList<ShapeSet> getCurrent() {
        return current;
    }

    public void setCurrent(ArrayList<ShapeSet> current) {
        this.current = current;
    }

    public void setHistory(ArrayList<ArrayList<ShapeSet>> history) {
        this.history = history;
    }

    /**
     * @return true �ڼ�¼current��history�ж�Ϊ��!
     * ��֮��false
     */
    public boolean isRecordNull() {
        boolean bl = false;
        if (this.current.size() == 0) {
            this.clearRecord();
            bl = true;
        }
        return bl;
    }

    /**
     * ��ռ�¼current��history
     */
    public void clearRecord() {
        this.changeIndex = -1;
        this.current.clear();
        this.history.clear();
    }

    /**
     * �½�����
     */
    public void newPaintPanel() {
        if (!this.isRecordNull()) {
            int replay = JOptionPane.showConfirmDialog(this, "�Ƿ�Ҫ���������ͼƬ���޸�?");
            switch (replay) {
                case JOptionPane.YES_OPTION:
                    this.savePaint();
                case JOptionPane.NO_OPTION:
                    this.clearRecord();
                    this.repaint();
                    break;
                case JOptionPane.CANCEL_OPTION:
                    break;
            }
        }
    }

    /**
     * ����ͼƬ
     */
    public void savePaint() {

        SaveFile save = new SaveFile(MainFrame.mainFrame, this);
        save.addChoosableFileFilter(new Myfilter());
        save.save();
        repaint();

    }

    /**
     * �˳���ͼϵͳ
     */
    public void exitPaint() {
        if (!this.isRecordNull()) {
            int replay = JOptionPane.showConfirmDialog(this, "�Ƿ�Ҫ���������ͼƬ���޸�?");
            switch (replay) {
                case JOptionPane.YES_OPTION:
                    this.savePaint();
                case JOptionPane.NO_OPTION:
                    System.exit(0);
                    break;
                case JOptionPane.CANCEL_OPTION:
                    break;
            }
        } else {
            System.exit(0);
        }
    }

    /**
     * ��ͼƬ�ļ�
     */
    public void openImg() {
        if (!this.isRecordNull()) {
            int replay = JOptionPane.showConfirmDialog(this, "�Ƿ�Ҫ���������ͼƬ���޸�?");
            switch (replay) {
                case JOptionPane.YES_OPTION:
                    this.savePaint();
                case JOptionPane.NO_OPTION:
                    this.clearRecord();
                    OpenFile open = new OpenFile(MainFrame.mainFrame, this);
                    open.addChoosableFileFilter(new Myfilter());
                    try {
                        open.open();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                    break;
                case JOptionPane.CANCEL_OPTION:
                    break;
            }
        } else {
            OpenFile open = new OpenFile(MainFrame.mainFrame, this);
            open.addChoosableFileFilter(new Myfilter());
            try {
                open.open();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }

    /**
     * ��ʾͼ��HotPoints�ķ���
     *
     * @param mousePoint
     */
    private int showHotPoints(Point mousePoint) {
        int crtIndex = getIndex(mousePoint);
        if (crtIndex != -1 && !change) {
            current.get(crtIndex).drawHotPoints(this.getGraphics());
        } else {
            repaint();
        }
        return crtIndex;
    }

    /**
     * @param mousePoint
     * @return ѡ��ͼ����current�е��±�, ����ѡ��ͼ����return -1
     */
    private int getIndex(Point mousePoint) {
        for (int i = 0; i < current.size(); i++) {    //ͨ��ѭ������Ҫ��ʾHotPoints�ĵ���ͼ��
            if (current.get(i).isHotPoint(mousePoint)) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                if (index != i) {
                    index = i;
                    change = true;
                } else {
                    change = false;
                }
                return i;
            }
        }
        setCursor(Cursor.getDefaultCursor());
        return -1;
    }

    /**
     * ��ʾͼ��HotZoom�ķ���
     *
     * @param mousePoint
     */
    private void showHotZoom(Point mousePoint) {
        for (int i = 0; i < current.size(); i++) {
            current.get(i).setState(ShapeSet.UNSELECTED);
        }
        int index = getIndex(mousePoint);
        if (index != -1) {
            current.get(index).setState(ShapeSet.SELECTED);
        }
        changeIndex = index;
        repaint();
    }

    /**
     * @return ����ѡ��ͼ�εĸ���
     */
    private ShapeSet whenSelect() {
        index = getIndex(startPoint);
        ShapeSet self = null;
        if (index != -1) {
            ShapeSet currentShape = current.get(index);
            //������������
            for (int i = 0; i < current.size(); i++) {
                if (current.get(i).getGroup() == currentShape.getGroup()) {
                    //��ͬһ�����µ�ͼ�η���һ������
                    groupShape.add(current.get(i));
                }
            }
            if (groupShape.size() > 0) {
                for (ShapeSet shapeSet : groupShape) {
                    if (currentShape.getColor() == Color.BLUE) {
                        shapeSet.setColor(Color.BLACK);
                    } else {
                        shapeSet.setColor(Color.BLUE);
                    }
                }
            }
            self = currentShape.catchShape();
            int addX = this.endPoint.x - this.startPoint.x;
            int addY = this.endPoint.y - this.startPoint.y;
            //�����Ŀ�ʼ�ڵ�������Ҫѡ�е�ͼ����
            Point newStart;
            if (selectedShape != null && this.isInArea) {
                newStart = new Point(selectedShape.getStartPoint().x, selectedShape.getStartPoint().y);
                this.isInArea = false;
            } else {
                newStart = new Point(currentShape.getStartPoint().x + addX, currentShape.getStartPoint().y + addY);
            }
            Point newEnd = new Point(currentShape.getEndPoint().x + addX, currentShape.getEndPoint().y + addY);
            self.setStartPoint(newStart);
            self.setEndPoint(newEnd);
            if (self.equals(currentShape) || endPoint.equals(startPoint)) {
                return null;
            }
            self.setState(ShapeSet.SELECTED);
            //self.setRealShape();//���¶���RealShape,������ԭʼʵ����������!
        }
        return self;
    }

    /**
     * ȥ��history�ķϾɼ�¼
     */
    private void removeTail() {
        if (totalIndex < history.size()) {
            for (int i = totalIndex + 1; i < history.size(); ) {
                history.remove(i);
            }
        }
    }

    /**
     * @return current�ĸ���
     */
    public ArrayList<ShapeSet> copyCurrent() {
        ArrayList<ShapeSet> al = new ArrayList<>();
        for (int i = 0; i < current.size(); i++) {
            current.get(i);
            al.add(current.get(i).catchShape());
            al.get(i).setState(ShapeSet.UNSELECTED);
        }
        return al;
    }

    /**
     * @return Ҫ�ı��С��ͼ��
     */
    private ShapeSet getChangSizeShape() {
        ShapeSet shape = null;
        int zoomIndex = -1;
        if (changeIndex != -1 && (zoomIndex = this.changeSize(startPoint)) > -1) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            shape = current.get(changeIndex).catchShape();
            int addX = this.endPoint.x - this.startPoint.x;
            int addY = this.endPoint.y - this.startPoint.y;
            Point start = shape.getStartPoint();
            Point end = shape.getEndPoint();
            if (shape instanceof Triangle || shape instanceof Circle || shape instanceof FivePointsStar || shape instanceof Pentagon) {
                end = this.endPoint;
            } else if (shape instanceof Line) {
                switch (zoomIndex) {
                    case 0:
                        start = this.endPoint;
                        break;
                    case 1:
                        end = this.endPoint;
                        break;
                }
            } else {
                switch (zoomIndex) {
                    case 0:
                        start = new Point(shape.getStartPoint().x + addX, shape.getStartPoint().y + addY);
                        break;
                    case 1:
                        start = new Point(shape.getStartPoint().x, shape.getStartPoint().y + addY);
                        break;
                    case 2:
                        start = new Point(shape.getStartPoint().x, shape.getStartPoint().y + addY);
                        end = new Point(shape.getEndPoint().x + addX, shape.getEndPoint().y);
                        break;
                    case 3:
                        end = new Point(shape.getEndPoint().x + addX, shape.getEndPoint().y);
                        break;
                    case 4:
                        end = new Point(shape.getEndPoint().x + addX, shape.getEndPoint().y + addY);
                        break;
                    case 5:
                        end = new Point(shape.getEndPoint().x, shape.getEndPoint().y + addY);
                        break;
                    case 6:
                        start = new Point(shape.getStartPoint().x + addX, shape.getStartPoint().y);
                        end = new Point(shape.getEndPoint().x, shape.getEndPoint().y + addY);
                        break;
                    case 7:
                        start = new Point(shape.getStartPoint().x + addX, shape.getStartPoint().y);
                        break;
                }
            }

            shape.setStartPoint(start);
            shape.setEndPoint(end);
            if (endPoint.equals(startPoint)) {
                return null;
            }
            shape.setState(ShapeSet.SELECTED);
            //shape.setRealShape();//���¶���RealShape,������ԭʼʵ����������!
        } else {
            this.setCursor(Cursor.getDefaultCursor());
        }
        return shape;
    }

    /**
     * �ı���ѡ��ͼ�ε���ɫ
     */
    public void changeColor() {
        ShapeSet shape = null;
        if (changeIndex != -1) {
            shape = current.get(changeIndex).catchShape();
            shape.setColor(getColor());
            current.remove(changeIndex);
            current.add(shape);
            removeTail();
            history.add(copyCurrent());
            totalIndex = history.size() - 1;
            changeIndex = current.size() - 1;
            current.get(changeIndex).setState(ShapeSet.SELECTED);
            repaint();
        }
    }

    /**
     * @param mousePoint
     * @return Ҫ�ı��ͼ����current�е��±�
     */
    private int changeSize(Point mousePoint) {
        int i;
        if (changeIndex > -1 && (i = current.get(changeIndex).inHotZoom(mousePoint)) > -1) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            return i;
        }

        setCursor(Cursor.getDefaultCursor());
        return -1;
    }

    /**
     * ɾ��ͼ�η���
     */
    public void deleteShape() {
        if (changeIndex > -1) {
            current.remove(changeIndex);
            removeTail();
            history.add(copyCurrent());
            changeIndex = -1;
            totalIndex = history.size() - 1;
            repaint();
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DELETE && this.command == Command.SELECT) {
            this.deleteShape();
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void flickerThreadStart() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    //synchronized ���������߳�Ӱ���̵߳�sleepʱ��
                    synchronized (PaintPanel.class) {
                        while (true) {
                            //ѡ������ͼ��
                            whenSelect();
                            //ˢ�»�����ʵ��ͼ����˸��Ч��
                            repaint();
                            //�߳���Ϣʱ��
                            Thread.sleep(1000);
                        }
                    }
                } catch (Exception ex) {
                }
            }
        });
        //���߳̿���֮ǰ�����һ�������µ���ͼ��
        groupShape.clear();
        thread.start();
    }

}
