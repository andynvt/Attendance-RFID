/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other.custom;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.AbstractBorder;

/**
 *
 * @author chuna
 */
public class RoundedBorder extends AbstractBorder {

    public RoundedBorder() {
        color = Color.decode("#e9e9e9");
        gap = 8;
    }

    public RoundedBorder(Color c) {
        color = c;
        gap = 8;
    }

    public RoundedBorder(Color c, int g) {
        color = c;
        gap = g;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        super.paintBorder(c, g, x, y, width, height);
        Graphics2D g2d;
        if (g instanceof Graphics2D) {
            g2d = (Graphics2D) g;
            g2d.setColor(color);
            System.out.println(x + y);
            g2d.draw(new Line2D.Double((double) x, (double) y + 10, (double) x + 3, (double) y + 3));
            g2d.draw(new Line2D.Double((double) x + 3, (double) y + 3, (double) x + 10, (double) y));
            g2d.draw(new Line2D.Double((double) x + 10, (double) y, (double) x + 30, (double) y));
            g2d.draw(new Line2D.Double((double) x + 30, (double) y, (double) x + 33, (double) y + 2));
            g2d.draw(new Line2D.Double((double) x + 33, (double) y + 2, (double) x + 36, (double) y + 8));
            g2d.draw(new Line2D.Double((double) x + 36, (double) y + 8, (double) x + 36, (double) y + 28));
            g2d.draw(new Line2D.Double((double) x + 36, (double) y + 28, (double) x + 34, (double) y + 31));
            g2d.draw(new Line2D.Double((double) x + 34, (double) y + 31, (double) x + 32, (double) y + 33));
            g2d.draw(new Line2D.Double((double) x + 32, (double) y + 33, (double) x + 6, (double) y + 33));
            g2d.draw(new Line2D.Double((double) x + 6, (double) y + 33, (double) x + 3, (double) y + 31));
            g2d.draw(new Line2D.Double((double) x + 3, (double) y + 31, (double) x, (double) y + 27));
            g2d.draw(new Line2D.Double((double) x, (double) y + 27, (double) x, (double) y + 10));
        }
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return (getBorderInsets(c, new Insets(gap, gap, gap, gap)));
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.top = insets.right = insets.bottom = gap;
        return insets;
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }

// Variable declarations
    private final Color color;
    private final int gap;

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://1point1c.org/gif/thum/plnttm.gif");

        Image image = Toolkit.getDefaultToolkit().createImage(url);
        ImageIcon spinIcon = new ImageIcon(image);
        JOptionPane.showMessageDialog(null, new JLabel(spinIcon));

        // create a static version of this icon
        BufferedImage bi = new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        ImageIcon staticIcon = new ImageIcon(bi);

        JButton button = new JButton(staticIcon);
        button.setRolloverIcon(spinIcon);
        JOptionPane.showMessageDialog(null, button);
    }
}
