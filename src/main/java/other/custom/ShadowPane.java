/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other.custom;

/**
 *
 * @author chuna
 */
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

public class ShadowPane extends JPopupMenu {

    public ShadowPane() {
        setLayout(new BorderLayout());
        setOpaque(false);
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(0, 0, 5, 5));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 100);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.SrcOver.derive(0.5f));
        g2d.fillRect(5, 5, getWidth(), getHeight());
        g2d.dispose();
    }

}
