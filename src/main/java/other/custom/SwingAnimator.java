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
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.util.ArrayList;

import java.util.List;
import javax.swing.JFrame;

import javax.swing.Timer;

/**
 *
 * Class to animate Layouts and Fades for a given component.
 *
 *
 * @author Greg Cope
 *
 *
 *
 */
public class SwingAnimator {

    //callback object
    private final SwingAnimatorCallback callback;

    //Timer to animate on the EDT
    private Timer timer = null;

    //duration in milliseconds betweeen each firing of the Timer
    private int duration = 60;

    //Listeners
    private List<AnimationStateListener> listeners = new ArrayList<>();

    /**
     *
     * Constructs a new SwingAnimator.
     *
     *
     * @param callback The object to callback to
     *
     */
    public SwingAnimator(SwingAnimatorCallback callback) {

        this(callback, false);

    }

    /**
     *
     *
     *
     * @param callback The object to callback to
     *
     * @param start true to automatically start the animation, false otherwise
     *
     */
    public SwingAnimator(SwingAnimatorCallback callback, boolean start) {

        this(callback, 60, start);

    }

    /**
     *
     *
     *
     * @param callback The object to callback to
     *
     * @param frameTiming Timing between each call to callback.
     *
     * @param start true to automatically start the animation, false otherwise
     *
     */
    public SwingAnimator(SwingAnimatorCallback callback, int frameTiming, boolean start) {

        this.callback = callback;

        setAnimationDuration(frameTiming);

        if (start) {

            start();

        }

    }

    /**
     *
     *
     *
     * @param callback The object to callback to
     *
     * @param frameTiming Timing between each call to callback.
     *
     */
    public SwingAnimator(SwingAnimatorCallback callback, int frameTiming) {

        this(callback, frameTiming, false);

    }

    /**
     *
     * Adds a listener to be notified of events
     *
     * @param listener
     *
     */
    public void addListener(AnimationStateListener listener) {

        listeners.add(listener);

    }

    /**
     *
     * Removes the listener.
     *
     * @param listener
     *
     */
    public void removeListener(AnimationStateListener listener) {

        listeners.remove(listener);

    }

    /**
     *
     * Sets the time (in milliseconds) between each call to callback.
     *
     *
     * @param duration
     *
     */
    public void setAnimationDuration(int duration) {

        this.duration = duration;

    }

    /**
     *
     * Checks if this animator is running.
     *
     * @return
     *
     */
    public boolean isRunning() {

        if (timer == null) {

            return false;

        }

        return timer.isRunning();

    }

    /**
     *
     * Stops the timer
     *
     */
    public void stop() {

        if (timer != null) {

            timer.stop();

        }

    }

    /**
     *
     * Starts the timer to fire. If the current timer is non-null and running,
     * this method will first
     *
     * stop the timer before beginning a new one. *
     */
    public void start() {

        if (timer != null && timer.isRunning()) {

            stop();

        }

        timer = new Timer(duration, new CallbackListener());

        timer.start();

        for (AnimationStateListener l : listeners) {

            l.animationStarted(this);

        }

    }

    /**
     *
     * ActionListener implements to be passed to the internal timer instance
     *
     * @author Greg Cope
     *
     *
     *
     */
    private class CallbackListener implements ActionListener {

        @Override

        public void actionPerformed(ActionEvent e) {

            if (callback.hasTerminated()) {

                if (timer == null) {

                    throw new IllegalStateException("Callback listener should not be fired outside of SwingAnimator timer control");

                }

                for (AnimationStateListener l : listeners) {

                    l.animationComplete(SwingAnimator.this);

                }

                timer.stop();

            }

            callback.callback(SwingAnimator.this);

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

/**
 *
 * Callback interface to be notified by a SwingAnimator of a new time frame.
 *
 *
 * @author Greg Cope
 *
 *
 *
 */
