/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import java.io.File;
import javax.swing.ImageIcon;

/**
 *
 * @author chuna
 */
public class ImageFile {

    private ImageIcon image;
    private String fileName;

    public ImageFile() {
    }

    public ImageFile(String fileName) {
        this.fileName = fileName;
    }

    public ImageIcon getImage() {
        return new ImageIcon(fileName);
    }

    public ImageIcon getImage(String fileName) {
        image = new ImageIcon(getClass().getResource(fileName));
        return image;
    }

    public ImageIcon getIconReverse(ImageIcon icon) {
        try {
            String pathIcon = icon.getDescription();
            if (pathIcon != null || !"".equals(pathIcon)) {
                String[] p = pathIcon.split("px");
                String pathIcon_hover = p[0] + "px_hover" + p[1];
                return new ImageIcon(pathIcon_hover);
            }
        } catch (NullPointerException e) {
        }
        return null;
    }

    public void anh() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
//            ImageIcon icon = new ImageIcon(classLoader.getResource("image/2.png"));
            File resourcesDirectory = new File("src/main/resources/image/2.png");
            System.out.println(resourcesDirectory);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

    }

}
