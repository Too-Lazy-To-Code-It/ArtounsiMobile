/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author achref
 */
public class View {
    private AllUsers u;
    private Video v;
    private String dateV;

    public View() {
    }

    public View(AllUsers u, Video v) {
        this.u = u;
        this.v = v;
    }

    public AllUsers getU() {
        return u;
    }

    public void setU(AllUsers u) {
        this.u = u;
    }

    public Video getV() {
        return v;
    }

    public void setV(Video v) {
        this.v = v;
    }

    public String getDateV() {
        return dateV;
    }

    public void setDateV(String dateV) {
        this.dateV = dateV;
    }

    @Override
    public String toString() {
        return "View{" + "u=" + u + ", v=" + v + ", dateV=" + dateV + '}';
    }
    
    
}
