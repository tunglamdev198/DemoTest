package com.example.demotest;

import java.util.Comparator;

public class ThiSinh {
    private String soBaoDanh;
    private String hoTen;
    private double diemToan;
    private double diemLi;
    private double diemHoa;

    public ThiSinh(String soBaoDanh, String hoTen, double diemToan, double diemLi, double diemHoa) {
        this.soBaoDanh = soBaoDanh;
        this.hoTen = hoTen;
        this.diemToan = diemToan;
        this.diemLi = diemLi;
        this.diemHoa = diemHoa;
    }

    public String getSoBaoDanh() {
        return soBaoDanh;
    }

    public void setSoBaoDanh(String soBaoDanh) {
        this.soBaoDanh = soBaoDanh;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public double getDiemToan() {
        return diemToan;
    }

    public void setDiemToan(double diemToan) {
        this.diemToan = diemToan;
    }

    public double getDiemLi() {
        return diemLi;
    }

    public void setDiemLi(double diemLi) {
        this.diemLi = diemLi;
    }

    public double getDiemHoa() {
        return diemHoa;
    }

    public void setDiemHoa(double diemHoa) {
        this.diemHoa = diemHoa;
    }

    public double tongDiem() {
        return diemToan + diemLi + diemHoa;
    }

    public static Comparator<ThiSinh> comparator = new Comparator<ThiSinh>() {
        @Override
        public int compare(ThiSinh o1, ThiSinh o2) {
            String ten1 = o1.hoTen.substring(o1.hoTen.lastIndexOf(" "));
            String ten2 = o2.hoTen.substring(o2.hoTen.lastIndexOf(" "));
            if(ten1.compareTo(ten2)==0){
                return o1.hoTen.compareTo(o2.hoTen);
            }
            else {
                return ten1.compareTo(ten2);
            }
        }
    };
}
