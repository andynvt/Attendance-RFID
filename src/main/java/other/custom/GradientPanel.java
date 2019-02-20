/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other.custom;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

/**
 *
 * @author chuna
 */
public class GradientPanel extends JPanel implements MouseListener {

    private Color color1;

    private Color color2;
    /**
     * determine the alignment of the gradient colors.
     */
    private int align;

    private Color oldForeground;

    private Color borderColor;

    private int radius;

    public Color getColor1() {
        return color1;
    }

    public void setColor1(Color color1) {
        this.color1 = color1;
    }

    public Color getColor2() {
        return color2;
    }

    public void setColor2(Color color2) {
        this.color2 = color2;
    }

    public int getAlign() {
        return align;
    }

    public void setAlign(int align) {
        this.align = align;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    private static final Color DEFAULT_COLOR_1 = new Color(250, 180, 250);

    private static final Color DEFAULT_COLOR_2 = new Color(180, 250, 180);

    public static final int TOP_TO_BOTTOM = 0;

    public static final int LEFT_TO_RIGHT = 1;

    public static final int TOP_LEFT_TO_BOTTOM_RIGHT = 2;

    public static final int BOTTOM_LEFT_TO_TOP_RIGHT = 3;

    private static final int DEFAULT_ALIGN = TOP_TO_BOTTOM;

    private static final int RADIUS = 8;

    private int mouse_action;

    private Graphics2D g2 = null;

    public GradientPanel() {
        super();
        this.color1 = DEFAULT_COLOR_1;
        this.color2 = DEFAULT_COLOR_2;
        this.align = DEFAULT_ALIGN;
        this.borderColor = Color.BLACK;
        this.radius = RADIUS;
        addMouseListener(this);
        setDefault();
    }

    public GradientPanel(Color color1, Color color2, int align) {
        super();
        this.color1 = color1;
        this.color2 = color2;
        this.align = align;
        this.radius = RADIUS;
        this.borderColor = Color.BLACK;
        addMouseListener(this);
        setDefault();

    }

    private void setDefault() {
        setOpaque(true);
    }

    public void setGradient(Color color1, Color color2) {
        this.color1 = color1;
        this.color2 = color2;
    }

    public void setGradient(Color color1, Color color2, int align) {
        this.color1 = color1;
        this.color2 = color2;
        this.align = align;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.oldForeground = this.getForeground();
        if (this.oldForeground == Color.BLUE) {
            this.setForeground(Color.BLACK);
        } else {
            this.setForeground(Color.BLUE);
        }
        this.borderColor = Color.RED;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.setForeground(this.oldForeground);
        this.borderColor = Color.GREEN;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouse_action = 1;
        this.borderColor = Color.GREEN;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouse_action = 0;
        this.borderColor = Color.BLACK;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (ui != null) {
            Graphics scratchGraphics = (g == null) ? null : g.create();
            g2 = (Graphics2D) scratchGraphics;

            if (this.align == TOP_TO_BOTTOM) {
                if (mouse_action == 0) {
                    g2.setPaint(new GradientPaint(new Point(0, 0), getColor1(), new Point(0, getHeight()), getColor2()));
                }
                if (mouse_action == 1) {
                    g2.setPaint(new GradientPaint(new Point(0, 0), getColor2(), new Point(0, getHeight()), getColor1()));
                }
            }
            if (this.align == LEFT_TO_RIGHT) {
                if (mouse_action == 0) {
                    g2.setPaint(new GradientPaint(new Point(0, 0), getColor1(), new Point(getWidth(), 0), getColor2()));
                }
                if (mouse_action == 1) {
                    g2.setPaint(new GradientPaint(new Point(0, 0), getColor2(), new Point(getWidth(), 0), getColor1()));
                }
            }
            if (this.align == TOP_LEFT_TO_BOTTOM_RIGHT) {
                if (mouse_action == 0) {
                    g2.setPaint(new GradientPaint(new Point(0, 0), getColor1(), new Point(getWidth(), getHeight()), getColor2()));
                }
                if (mouse_action == 1) {
                    g2.setPaint(new GradientPaint(new Point(0, 0), getColor2(), new Point(getWidth(), getHeight()), getColor1()));
                }
            }
            if (this.align == BOTTOM_LEFT_TO_TOP_RIGHT) {
                if (mouse_action == 0) {
                    g2.setPaint(new GradientPaint(new Point(0, getHeight()), getColor1(), new Point(getWidth(), 0), getColor2()));
                }
                if (mouse_action == 1) {
                    g2.setPaint(new GradientPaint(new Point(0, getHeight()), getColor2(), new Point(getWidth(), 0), getColor1()));
                }
            }

            g2.fillRoundRect(0, 0, getWidth(), getHeight(), getRadius(), getRadius());
            g2.setPaint(getColor2());
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getRadius(), getRadius());
            try {
                ui.update(g2, this);
            } finally {
                scratchGraphics.dispose();
                g2.dispose();
            }
        }
    }

}
