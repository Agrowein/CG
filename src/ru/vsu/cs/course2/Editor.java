package ru.vsu.cs.course2;

import ru.vsu.cs.course2.Plane.Point;
import ru.vsu.cs.course2.graphics.CurveDrawer;
import ru.vsu.cs.course2.graphics.GraphicsProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Editor extends JPanel implements MouseListener {
    private static HashMap<RenderingHints.Key, Object> rh;
    private final int dx = 4, dy = 4;
    private Plane plane;
    private Point selected;
    GraphicsProvider graphicsProvider;

    Editor(Plane plane, GraphicsProvider graphicsProvider) {
        this.plane = plane;
        this.graphicsProvider = graphicsProvider;
        rh = new HashMap<>();
        rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        addMouseListener(this);
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    private void redraw() {
        paintImmediately(0, 0, getWidth(), getHeight());
    }

    @Override
    public void paintComponent(Graphics gg) {
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHints(rh);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        graphicsProvider.setBufferedImage(bi);
        for (int i = 0; i < plane.points.size(); i++) {
            drawButton(g, i);
        }
        CurveDrawer cd = graphicsProvider.getCurveDrawer();
        cd.drawCurve(plane.getPoints());

        g.drawImage(bi, 0, 0, null);
    }

    private void drawButton(Graphics2D g, int i) {
        Point point = plane.points.get(i);
        g.setColor(Color.BLACK);
        g.fillOval(getX(point) - dx, getY(point) - dy, 2 * dx, 2 * dy);

    }
    private int getX(Point point) {
        return getScreenPoint(point).x;
    }

    private ScreenPoint getScreenPoint(Point point) {
        return graphicsProvider.getScreenConverter().realToScreen(point.vect);
    }

    private int getY(Point point) {
        return getScreenPoint(point).y;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        selected = plane.getClosest(e.getX(), e.getY(), dx, dy);
        if (selected == null && e.getButton() == MouseEvent.BUTTON1) {
            selected = plane.addNewPoint(e.getX(), e.getY());
        } else if (selected != null && e.getButton() == MouseEvent.BUTTON3) {
            plane.points.remove(selected);
            selected = null;
        }
        redraw();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}