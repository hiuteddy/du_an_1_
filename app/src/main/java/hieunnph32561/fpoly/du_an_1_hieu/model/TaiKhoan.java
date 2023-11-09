package hieunnph32561.fpoly.du_an_1_hieu.model;

    public class TaiKhoan {
        private int maTk;
        private String tenDN;
        private String matKhau;

        public TaiKhoan(int maTk, String tenDN, String matKhau) {
            this.maTk = maTk;
            this.tenDN = tenDN;
            this.matKhau = matKhau;
        }

        public int getMaTk() {
            return maTk;
        }

        public String getTenDN() {
            return tenDN;
        }

        public String getMatKhau() {
            return matKhau;
        }
    }

