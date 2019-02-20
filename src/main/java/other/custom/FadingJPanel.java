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

import java.awt.Graphics;

import java.awt.Graphics2D;
import javax.swing.JFrame;

import javax.swing.JPanel;

/**
 *
 *
 *
 * @author Greg Cope
 *
 *
 *
 */
public class FadingJPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private long animationSpeed;

    private float alpha = 0;

    private float increment = 0.01f;

    private SwingAnimator fadeInAnimator = null;

    private SwingAnimator fadeOutAnimator = null;

    /**
     *
     * Sets the alpha value
     *
     * @param a
     *
     */
    public void setAlpha(float a) {

        this.alpha = a;

    }

    /**
     *
     * Fades this JPanel in. *
     */
    public void fadeIn() {

        stop();

        fadeInAnimator = new SwingAnimator(new FadeInCallback());

        fadeInAnimator.addListener(new AnimationStateListener() {

            @Override

            public void animationComplete(SwingAnimator animator) {

                FadingJPanel.this.firePropertyChange("fade complete", 0, 0);

            }

            @Override

            public void animationStarted(SwingAnimator animator) {

                FadingJPanel.this.firePropertyChange("fade started", 0, 1);

            }

        });

        fadeInAnimator.start();

    }

    /**
     *
     * Fades this JPanel out
     *
     */
    public void fadeOut() {

        stop();

        fadeOutAnimator = new SwingAnimator(new FadeOutCallback());

        fadeOutAnimator.addListener(new AnimationStateListener() {

            @Override

            public void animationComplete(SwingAnimator animator) {

                FadingJPanel.this.firePropertyChange("fade complete", 1, 1);

            }

            @Override

            public void animationStarted(SwingAnimator animator) {

                FadingJPanel.this.firePropertyChange("fade started", 1, 0);

            }

        });

        fadeOutAnimator.start();

    }

    /**
     *
     * Stops all animators. *
     */
    private void stop() {

        if (fadeOutAnimator != null && fadeOutAnimator.isRunning()) {

            fadeOutAnimator.stop();

        }

        if (fadeInAnimator != null && fadeInAnimator.isRunning()) {

            fadeInAnimator.stop();

        }

    }

    /**
     *
     * Sets how quickly each frame will change the alpha transparency
     *
     * @param increment
     *
     */
    public void setIncrement(float increment) {

        this.increment = increment;

    }

    /**
     *
     * Sets the animation speed
     *
     * @param ms
     *
     */
    public void setAnimationSpeed(int ms) {

        this.animationSpeed = ms;

    }

    @Override

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        if (alpha >= 0 && alpha <= 1) {

            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);

            g2d.setComposite(ac);

        }

    }

    /**
     *
     * Callback implementation for fading in
     *
     * @author Greg Cope
     *
     *
     *
     */
    private class FadeInCallback implements SwingAnimatorCallback {

        @Override

        public void callback(Object caller) {

            alpha += increment;

            repaint();

        }

        @Override

        public boolean hasTerminated() {

            if (alpha >= 1) {

                alpha = 1;

                return true;

            }

            return false;

        }

    }

    /**
     *
     * Callback implementation to fade out
     *
     * @author Greg Cope
     *
     *
     *
     */
    private class FadeOutCallback implements SwingAnimatorCallback {

        @Override

        public void callback(Object caller) {

            alpha -= increment;

            repaint();

        }

        @Override

        public boolean hasTerminated() {

            if (alpha <= 0) {

                alpha = 0;

                return true;

            }

            return false;

        }

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setContentPane(new FadingJPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
