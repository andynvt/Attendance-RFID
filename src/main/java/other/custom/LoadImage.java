/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other.custom;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author chuna
 */
public class LoadImage {

    private static ImageIcon imageIcon;

    public LoadImage() {
    }

    private static boolean checkPath(String path) {
        return path != null && !"".equals(path);
    }

    private static boolean isWithin(String path) {
        return (checkPath(path) && (path.substring(0, 1).equals("/") || path.substring(0, 1).equals("\\")));
    }

    public static ImageIcon getIconWithin(String path) {
        return new ImageIcon(JFrame.getFrames().getClass().getResource(path));

    }

    public static ImageIcon getIconExternal(String path) {
        return new ImageIcon(path);
    }

    public ImageIcon getIcon(String path) {
        imageIcon = new ImageIcon(getClass().getResource(path));
        return imageIcon;
    }

    public static ImageIcon getImageIcon(String path) {
        return imageIcon;
    }
//    public static ImageIcon getIconReverse(ImageIcon icon) {
//        try {
//            String pathIcon = icon.getDescription();
//            if (pathIcon != null || !"".equals(pathIcon)) {
//                String[] p = pathIcon.split("px");
//                String pathIcon_hover = p[0] + "px_hover" + p[1];
//                return LoadImage.getIcon(pathIcon_hover);
//            }
//        } catch (NullPointerException e) {
//        }
//        return null;
//    }
//
//    public static Image getImageWithin(String path) {
//        return getIconWithin(path).getImage();
//    }
//
//    public static Image getImageExternal(String path) {
//        return getIconExternal(path).getImage();
//    }
//
//    public static Image getImage(String path) {
//        return getIcon(path).getImage();
//    }
}
