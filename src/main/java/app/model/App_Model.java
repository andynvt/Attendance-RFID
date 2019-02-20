/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.model;

/**
 *
 * @author chuna
 */
public class App_Model {

    private int id;
    private String name;
    private int leftMenu;
    private int width, height;

    public App_Model(int id, String name, int leftMenu, int width, int height) {
        this.id = id;
        this.name = name;
        this.leftMenu = leftMenu;
        this.width = width;
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLeftMenu() {
        return leftMenu;
    }

    public void setLeftMenu(int leftMenu) {
        this.leftMenu = leftMenu;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
