package com.hb.javacad.shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Shape;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class ShapeSet implements Serializable {

    protected Point startPoint;
    protected Point endPoint;
    protected Color color;
    protected int group;

    public static final int UNSELECTED = 0;
    public static final int SELECTED = 1;
    protected int state;

    public ShapeSet() {
        this.setState(UNSELECTED);
    }

    /**
     * ����ʵ�ʵ�ͼ��
     */
    public abstract ArrayList<Shape> getRealShape();

    /**
     * ������Ӧ��ͼ��
     *
     * @param g
     */
    public abstract void drawShape(Graphics g) throws InterruptedException, Exception;

    /**
     * @param mousePoint
     * @return true������HotPoint�ڣ�����false
     */
    public abstract boolean isHotPoint(Point mousePoint);

    /**
     * @return ����HotPoints��ֵ
     */
    public abstract Point[] getHotPoints();

    /**
     * @return ����ͼ������
     */
    public abstract ShapeSet catchShape();

    /**
     * @return ��Χ�ȵ�ĵ㼯��
     */
    public Point[] getHotZoom() {
        Point[] hotZoom = {new Point(), new Point(), new Point(), new Point(), new Point(), new Point(), new Point(), new Point()};
        hotZoom[0].setLocation(this.startPoint.getX(), this.startPoint.getY());
        hotZoom[1].setLocation((this.endPoint.getX() + this.startPoint.getX()) / 2, this.startPoint.getY());
        hotZoom[2].setLocation(this.endPoint.getX(), this.startPoint.getY());
        hotZoom[3].setLocation(this.endPoint.getX(), (this.endPoint.getY() + this.startPoint.getY()) / 2);
        hotZoom[4].setLocation(this.endPoint.getX(), this.endPoint.getY());
        hotZoom[5].setLocation((this.endPoint.getX() + this.startPoint.getX()) / 2, this.endPoint.getY());
        hotZoom[6].setLocation(this.startPoint.getX(), this.endPoint.getY());
        hotZoom[7].setLocation(this.startPoint.getX(), (this.endPoint.getY() + this.startPoint.getY()) / 2);

        return hotZoom;
    }

    /**
     * ����ͼ�κ��ȵ�
     *
     * @param g
     */
    public void draw(Graphics g, boolean bl) {
        try {
            drawShape(g);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (getState() == ShapeSet.SELECTED) {
            this.drawHotZoom(g, bl);
        }
    }

    /**
     * ��С����ʽ����ͼ���ϵ��ȵ�
     *
     * @param g
     */
    public void drawHotPoints(Graphics g) {
        g.setColor(Color.black);
        Point[] hotPoints = getHotPoints();
        //TODO �����ȵ���δ�С
        for (int i = 0; i < hotPoints.length; i++) {
            g.drawRect(hotPoints[i].x - 2, hotPoints[i].y - 2, 4, 4);
        }
    }

    /**
     * ��С����ʽ������Χ�ȵ�
     *
     * @param g
     */
    public void drawHotZoom(Graphics g, boolean bl) {
        if (bl) {
            g.setColor(Color.black);
        } else {
            g.setColor(Color.gray);
        }
        Point[] hotZoom = getHotZoom();
        for (int i = 0; i < hotZoom.length; i++) {
            g.drawRect(hotZoom[i].x - 3, hotZoom[i].y - 3, 7, 7);
        }
    }

    /**
     * @param mousePoint
     * @return HotZoom�ķ��񼯺ϵ��±�
     */
    public int inHotZoom(Point mousePoint) {
        int index = -1;
        for (int i = 0; i < getHotZoom().length; i++) {
            if (new java.awt.Rectangle(getHotZoom()[i].x - 2, getHotZoom()[i].y - 2, 4, 4).contains(mousePoint)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }
}
