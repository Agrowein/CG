package ru.vsu.cs.course2;

import ru.vsu.cs.course2.graphics.*;

import javax.swing.*;
import java.awt.*;

public class CurveDrawerForm {
    public static final int FPS = 60;
    private JPanel drawPanel;
    private JPanel rootPanel;
    private JRadioButton curve1RadioButton;
    private JRadioButton curve2RadioButton;
    private JButton playButton;
    private JSlider slider;
    private JLabel sliderValue;
    private Editor editor;
    private final GraphicsProvider graphicsProvider;
    private final ButtonGroup buttonGroup;
    private final Plane plane1;
    private final Plane plane2;
    private CurvePlayer player;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        JFrame frame = new JFrame("CG Task 3");
        frame.setContentPane(new CurveDrawerForm().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setVisible(true);
    }

    private CurveDrawerForm() {
        graphicsProvider = new GraphicsProvider();
        graphicsProvider.setColor(Color.red);
        graphicsProvider.setPixelDrawer(new BufferedImagePixelDrawer(graphicsProvider));
        graphicsProvider.setLineDrawer(new BresenhamLineDrawer(graphicsProvider));
        graphicsProvider.setRealLineDrawer(new RealLineDrawer(graphicsProvider));
        graphicsProvider.setPolyLineDrawer(new PolyLineDrawer(graphicsProvider));
        graphicsProvider.setCurveDrawer(new CurveDrawer(graphicsProvider));

        buttonGroup = new ButtonGroup();
        buttonGroup.add(curve1RadioButton);
        buttonGroup.add(curve2RadioButton);
        curve1RadioButton.addActionListener(e -> curve1Selected());
        curve2RadioButton.addActionListener(e -> curve2Selected());
        slider.addChangeListener(e -> sliderValue.setText(String.valueOf(slider.getValue())));

        plane1 = new Plane(graphicsProvider);
        plane2 = new Plane(graphicsProvider);

        editor = new Editor(plane1, graphicsProvider);

        graphicsProvider.setScreenConverter(new ScreenConverter(editor));
        drawPanel.setLayout(new GridLayout());
        drawPanel.add(editor);
        curve1Selected();

        sliderValue.setText(String.valueOf(slider.getValue()));
        playButton.addActionListener(e -> setPlayer());
    }

    private void curve1Selected() {
        buttonGroup.clearSelection();
        curve1RadioButton.setSelected(true);
        editor.setPlane(plane1);
        updateEditor();
    }

    private void curve2Selected() {
        buttonGroup.clearSelection();
        curve2RadioButton.setSelected(true);
        editor.setPlane(plane2);
        updateEditor();
    }

    private void updateEditor() {
        if (player != null) {
            player.stop();
        }
        drawPanel.removeAll();
        drawPanel.add(editor);
        drawPanel.revalidate();
        editor.paintImmediately(0, 0, editor.getWidth(), editor.getHeight());
    }

    private void setPlayer() {
        CurveProcessor cp = new CurveProcessor(plane1, plane2);
        int duration = (int) ((double) slider.getModel().getValue() * FPS);
//        int duration = (int) ((double) slider1.getModel().getValue() * FPS);
        player = new CurvePlayer(graphicsProvider, cp, duration);
        drawPanel.removeAll();
        drawPanel.add(player);
        drawPanel.revalidate();
    }

}
