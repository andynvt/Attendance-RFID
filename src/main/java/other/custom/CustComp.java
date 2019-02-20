/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other.custom;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.text.JTextComponent;
import org.jdesktop.swingx.JXDatePicker;

/**
 *
 * @author chuna
 */
public class CustComp {

    public static boolean hasComponent(Container pa, Component child) {
        Component[] components = pa.getComponents();
        for (Component c : components) {
            if (c == child) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<JButton> getButtons(Container pa) {
        Component[] components = pa.getComponents();
        ArrayList<JButton> buttons = new ArrayList<>();
        for (Component com : components) {
            if (com instanceof JButton) {
                buttons.add((JButton) com);
            } else if (com instanceof JPanel) {
                buttons.addAll(getButtons((JPanel) com));
            }
        }
        return buttons;
    }

    public static ArrayList<JTextComponent> getTextComponents(Container pa) {
        Component[] components = pa.getComponents();
        ArrayList<JTextComponent> text = new ArrayList<>();
        for (Component com : components) {
            if (com instanceof JTextComponent) {
                text.add((JTextComponent) com);
            } else if (com instanceof JPanel) {
                text.addAll(getTextComponents((JPanel) com));
            }
        }
        return text;
    }

    public static ArrayList<JSpinner> getSpinners(Container pa) {
        Component[] components = pa.getComponents();
        ArrayList<JSpinner> text = new ArrayList<>();
        for (Component com : components) {
            if (com instanceof JTextComponent) {
                text.add((JSpinner) com);
            } else if (com instanceof JPanel) {
                text.addAll(getSpinners((JPanel) com));
            }
        }
        return text;
    }

    public static ArrayList<JXDatePicker> getDatePickers(Container pa) {
        Component[] components = pa.getComponents();
        ArrayList<JXDatePicker> text = new ArrayList<>();
        for (Component com : components) {
            if (com instanceof JTextComponent) {
                text.add((JXDatePicker) com);
            } else if (com instanceof JPanel) {
                text.addAll(getDatePickers((JPanel) com));
            }
        }
        return text;
    }

    public static MouseAdapter addHoverEffect(JButton btn, Color bg_hover, Color bg, Color font_hover, Color font) {
        return new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                hoverButton(btn, bg_hover, font_hover);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                resetButton(btn, bg, font);
            }
        };
    }

    public static MouseAdapter addHoverEffect(GradientButton btn, Color bg_hover, Color bg, Color font_hover, Color font) {
        return new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                hoverButton(btn, bg_hover, font_hover);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                resetButton(btn, bg, font);
            }
        };
    }

    public static void hoverButton(JButton btn, Color bg_hover, Color font_hover) {
        if (btn.isEnabled()) {
            btn.setBackground(bg_hover);
            btn.setForeground(font_hover);
            btn.setOpaque(true);
        }
    }

    public static void resetButton(JButton btn, Color bg, Color font) {
        btn.setOpaque(true);
        btn.setBackground(bg);
        btn.setForeground(font);
    }

    public static void hoverButton(GradientButton btn, Color bg_hover, Color font_hover) {
        if (btn.isEnabled()) {
            btn.setBackground(bg_hover);
            btn.setForeground(font_hover);
            btn.setOpaque(true);
        }
    }

    public static void resetButton(GradientButton btn, Color bg, Color font) {
        btn.setOpaque(true);
        btn.setBackground(bg);
        btn.setForeground(font);
    }

    public static FocusAdapter setFocusInput(JTextComponent com, String prompText, Color text, Color background) {
        return new FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                com.setForeground(Color.decode("#2e3233"));
                if (com.getText().equals(prompText)) {
                    com.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (com.getText().trim().equals("")) {
                    com.setText(prompText);
                    com.setForeground(text);
                }
            }
        };
    }

    public static FocusAdapter setFocusInput(JTextComponent com, Color text, Color boder, Color boder_focus) {
        return new FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                gained();
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                lost();
            }

            private void gained() {
                com.setForeground(Color.decode("#2e3233"));
                com.setBorder(FormatPattern.getRoundBorder(8, boder_focus));
            }

            private void lost() {
                com.setForeground(text);
                com.setBorder(FormatPattern.getRoundBorder(8, boder));
            }
        };
    }

    public static void setAbsoluteSize(Component pa, int w, int h) {
        pa.setSize(new Dimension(w, h));
        pa.setPreferredSize(new Dimension(w, h));
    }

    public static void setAbsoluteSize(Container pa, int w, int h) {
        pa.setSize(new Dimension(w, h));
        pa.setPreferredSize(new Dimension(w, h));
    }

    public static void setAbsoluteBound(Component pa, int x, int y, int w, int h) {
        pa.setLocation(x, y);
        pa.setSize(new Dimension(w, h));
        pa.setPreferredSize(new Dimension(w, h));
    }

    public static void setSameToolTipText(Container pa) {
        Component[] components = pa.getComponents();
        for (Component com : components) {
            if (com instanceof JButton) {
                ((JButton) com).setToolTipText(((JButton) com).getText());
            }
        }
    }

    public static void setSameToolTipText(Container pa, String text) {
        Component[] components = pa.getComponents();
        for (Component com : components) {
            if (com instanceof JButton) {
                ((JButton) com).setToolTipText(text);
            }
        }
    }
}
