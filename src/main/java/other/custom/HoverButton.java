/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diemdanh.other.custom;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author chuna
 */
public class HoverButton extends JButton {

    private String title;
    private Dimension dimension;
    private Insets insets;
    private Color fg_color1;
    private Color fg_color2;
    private Color bg_color1;
    private Color bg_color2;
    private Icon icon1;
    private Icon icon2;
    MouseAdapter mouseEvent;
    FocusAdapter focusEvent;
    private boolean isActive;

    public HoverButton(Icon icon1, Icon icon2) {
        super(icon1);
        this.icon1 = icon1;
        this.icon2 = icon2;
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setHorizontalAlignment(SwingConstants.LEFT);
        mouseAdapter();
        focusAdapter();
        addMouseAdapter();
        addFocusAdapter();
    }

    public HoverButton(String title, Dimension dimension, Insets insets, Color fg_color1, Color fg_color2, Color bg_color1, Color bg_color2, Icon icon1, Icon icon2) {
        super(title, icon1);
        this.title = title;
        this.dimension = dimension;
        this.insets = insets;
        this.fg_color1 = fg_color1;
        this.fg_color2 = fg_color2;
        this.bg_color1 = bg_color1;
        this.bg_color2 = bg_color2;
        this.icon1 = icon1;
        this.icon2 = icon2;
        initButton();
        mouseAdapter();
        focusAdapter();
        addMouseAdapter();
        addFocusAdapter();
    }

    private void initButton() {
        setFont(new Font("Arial", 0, 16));
        setForeground(fg_color1);
        setContentAreaFilled(false);
        if (bg_color1 == null) {
            setOpaque(false);
        } else {
            setBackground(bg_color1);
            setOpaque(true);
        }
        setSize(dimension);
        setPreferredSize(dimension);
        setBorder(new EmptyBorder(insets));
        setBorderPainted(false);
        setFocusPainted(false);
        setHorizontalAlignment(SwingConstants.LEFT);
    }

    public void addMouseAdapter() {
        addMouseListener(mouseEvent);
    }

    public void addFocusAdapter() {
        addFocusListener(focusEvent);
    }

    public void removeMouseApdater() {
        removeMouseListener(mouseEvent);
    }

    public void removeFocusApdater() {
        removeFocusListener(focusEvent);
    }

    private void mouseAdapter() {
        reset();
        mouseEvent = new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                hover();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!isActive) {
                    reset();
                }
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (isActive) {
                    active();
                }
            }
        };
    }

    private void focusAdapter() {
        reset();
        focusEvent = new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                active();
            }

            @Override
            public void focusLost(FocusEvent evt) {
                if (!isActive) {
                    reset();
                }
            }
        };
    }

    private void hover() {
        if (isEnabled()) {
            setForeground(fg_color2);
            setBackground(bg_color2);
            setOpaque(true);
            setIcon(icon2);
        }
    }

    public void reset() {
        if (bg_color1 != null) {
            setBackground(bg_color1);
            setOpaque(true);
        } else {
            setOpaque(false);
        }
        setForeground(fg_color1);
        setIcon(icon1);
    }

    private void active() {
        hover();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        g.setColor(Color.WHITE);
//        g.fillRoundRect(100, 100, 500, 500, 30, 30); //i dont know which size you want
//    }
    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

}
