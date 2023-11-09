package hieunnph32561.fpoly.du_an_1_hieu.model;

    public class KhachHang {
        private int maKh;
        private String hoTen;
        private String dienThoai;
        private String diachi;


        public KhachHang() {
        }

        public KhachHang(int maKh, String hoTen, String dienThoai, String diachi) {
            this.maKh = maKh;
            this.hoTen = hoTen;
            this.dienThoai = dienThoai;
            this.diachi = diachi;
        }

        public String getDiachi() {
            return diachi;
        }

        public void setDiachi(String diachi) {
            this.diachi = diachi;
        }

        public void setMaKh(int maKh) {
            this.maKh = maKh;
        }

        public void setHoTen(String hoTen) {
            this.hoTen = hoTen;
        }

        public void setDienThoai(String dienThoai) {
            this.dienThoai = dienThoai;
        }


        public int getMaKh() {
            return maKh;
        }

        public String getHoTen() {
            return hoTen;
        }

        public String getDienThoai() {
            return dienThoai;
        }

    }

