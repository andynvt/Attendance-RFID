/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other.custom;

import java.awt.Point;
import java.awt.Rectangle;
import net.java.balloontip.positioners.BalloonTipPositioner;

/**
 *
 * @author chuna
 */
public class SimpleTipPositioner extends BalloonTipPositioner {

    public SimpleTipPositioner() {
        super();
    }

    int x = 0; // Current X-coordinate of the top-left corner of the bounding box around the balloon tip
    int y = 0; // Current Y-coordinate of the top-left corner of the bounding box around the balloon tip

    /* The balloon tip will call this method every time it wants to re-draw itself.
   * The parameter of this function, attached, is the Rectangle that the balloon tip should attach itself to. */
    @Override
    public void determineAndSetLocation(Rectangle attached) {
        /* Calculate the coordinates of the top-left corner of the bounding box around the balloon tip
     * This positioner will place the balloon tip above the attached Rectangle. */
        x = attached.x;
        y = attached.y - balloonTip.getPreferredSize().height;
        // Now move the balloon tip to the position we've just calculated
        balloonTip.setBounds(x, y, balloonTip.getPreferredSize().width, balloonTip.getPreferredSize().height);
        balloonTip.validate();
    }

    /* This method should return the location of the balloon's tip */
    @Override
    public Point getTipLocation() {
        /* You may use the last position calculated in determineAndSetLocation to calculate the tip's location.
     * The fields x and y now contain the position of the top-left corner of the bounding box of the balloon tip. */
        return new Point(x + 20, y + balloonTip.getPreferredSize().height);
    }

    /* Whenever a balloon tip's style is changed (This includes setting it for the first time..), this method is called.
  * Within this method, the positioner should take care of properly setting up this new style. */
    @Override
    protected void onStyleChange() {
        balloonTip.getStyle().setHorizontalOffset(20);
        balloonTip.getStyle().setVerticalOffset(20);
        balloonTip.getStyle().flipX(false);
        balloonTip.getStyle().flipY(false);
    }
}
