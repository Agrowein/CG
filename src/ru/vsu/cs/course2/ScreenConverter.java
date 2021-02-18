package ru.vsu.cs.course2;

public class ScreenConverter {
    private Editor editor;
    private double x0, y0;
    private double scale = 1;

    public ScreenConverter(Editor editor) {
        this.editor = editor;
    }

    public ScreenPoint realToScreen(Vector2 point) {
        double x = (point.getX() - x0) * scale + getScreenCenterX();
        double y = (y0 - point.getY()) * scale + getScreenCenterY();
        return new ScreenPoint((int) x, (int) y);
    }

    public Vector2 screenToReal(ScreenPoint p) {
        double x = x0 + (p.getX() - getScreenCenterX()) / scale;
        double y = y0 - (p.getY() - getScreenCenterY()) / scale;
        return new Vector2(x, y);
    }

    public int getScreenCenterX() {
        return editor.getWidth() / 2;
    }

    public int getScreenCenterY() {
        return editor.getHeight() / 2;
    }
}
