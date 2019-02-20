/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diemdanh.model;

import canbo.model.CanBo_Model;
import sinhvien.model.SinhVien_Model;

/**
 *
 * @author chuna
 */
public class DiemDanh_Model {

    private String masukien;
    private CanBo_Model cadre_Model;
    private SinhVien_Model student_Model;

    public DiemDanh_Model(String masukien, CanBo_Model cadre_Model) {
        this.masukien = masukien;
        this.cadre_Model = cadre_Model;
    }

    public DiemDanh_Model(String masukien, SinhVien_Model student_Model) {
        this.masukien = masukien;
        this.student_Model = student_Model;
    }

    public String getMasukien() {
        return masukien;
    }

    public void setMasukien(String masukien) {
        this.masukien = masukien;
    }

    public CanBo_Model getCadre_Model() {
        return cadre_Model;
    }

    public void setCadre_Model(CanBo_Model cadre_Model) {
        this.cadre_Model = cadre_Model;
    }

    public SinhVien_Model getStudent_Model() {
        return student_Model;
    }

    public void setStudent_Model(SinhVien_Model student_Model) {
        this.student_Model = student_Model;
    }

}
