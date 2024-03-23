package project_java_group_5;

public class HuanLuyenVien extends NhanVien{
	// constructors:
	public HuanLuyenVien(){}
	public HuanLuyenVien(String ten, String quocTich, String ngaySinh, Integer thamNien,
			String vaitro){
		super(ten, quocTich, ngaySinh, thamNien, vaitro);
		
        }	
	//methods:
        //tinh he so vai tro
        public Integer hesovaitro(){
            if(super.getVaitro() == "HLV trưởng"){
                return 6;
            }
            if(super.getVaitro() == "trợ lý HLV"){
                return 5;
            }
            if(super.getVaitro() == "HLV cho thủ môn"){
                return 3;
            }
            if(super.getVaitro() == "HLV chuyên về thể lực"){
                return 3;
            }
            if(super.getVaitro() == "giám đốc kỹ thuật"){
                return 5;
            }
            if(super.getVaitro() == "bộ phận y tế"){
                return 5;
            }
            return 1;
        }
        
	// tinh phu cap tham nien:
	public Integer tinhLuong(){
		return 1500 * hesovaitro() * super.hesoLuong(super.getThamNien());
	}
        public Integer tinhThuong(){
            return 200 * hesovaitro();
        }
}
