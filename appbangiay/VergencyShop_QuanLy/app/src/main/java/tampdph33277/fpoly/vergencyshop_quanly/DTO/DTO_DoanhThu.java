package tampdph33277.fpoly.vergencyshop_quanly.DTO;

public class DTO_DoanhThu {
    public   String tuNgay;
    public   String denNgay;

    public  int doanhThu;

    public DTO_DoanhThu(String tuNgay, String denNgay, int doanhThu) {
        this.tuNgay = tuNgay;
        this.denNgay = denNgay;
        this.doanhThu = doanhThu;
    }
    public String getTuNgay() {
        return tuNgay;
    }

    public void setTuNgay(String tuNgay) {
        this.tuNgay = tuNgay;
    }

    public String getDenNgay() {
        return denNgay;
    }

    public void setDenNgay(String denNgay) {
        this.denNgay = denNgay;
    }

    public int getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(int doanhThu) {
        this.doanhThu = doanhThu;
    }
}
