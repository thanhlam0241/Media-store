package com.shopkeeper.linh.seeders;

import com.shopkeeper.linh.models.Customer;
import com.shopkeeper.mediaone.database.DatabaseAdapter;

public class CustomerSeeder {
    public static void SeedData(){
        Customer[] list = {
                new Customer("Lương Thanh Lâm", "Ngõ 97 Đ. Nguyễn Chí Thanh, Láng Hạ, Đống Đa, Hà Nội, Vietnam", "0999971123"),
                new Customer("Trương Phương Long", "113A2 P. Thành Công, Thành Công, Ba Đình, Hà Nội, Vietnam", "0982306219"),
                new Customer("Lý Tuấn Tùng", "102 E2 P. Thành Công, Thành Công, Ba Đình, Hà Nội, Vietnam", "0964757458"),
                new Customer("Mai Ngọc Linh", "25/612/50, Đường La Thành, Phường Giảng Võ, Quận Ba Đình, Giảng Võ, Ba Đình, Hà Nội, Vietnam", "0933008237"),
                new Customer("Phạm Như Lê", "599 Đ. La Thành, Thành Công, Ba Đình, Hà Nội, Vietnam", "0976180396"),
                new Customer("Vũ Thành Dũng", "5 P. Láng Hạ, Thành Công, Ba Đình, Hà Nội, Vietnam", "0924268962"),
                new Customer("Bùi Thị Tâm", "187 P. Giảng Võ, Chợ Dừa, Đống Đa, Hà Nội, Vietnam", "0972748228"),
                new Customer("Đỗ Quỳnh Trang", "135 P. An Trạch, Chợ Dừa, Đống Đa, Hà Nội 100000, Vietnam", "0930120517"),
                new Customer("Bùi Hạ Thuỷ", "84 P. Trần Hữu Tước, Nam Đồng, Đống Đa, Hà Nội, Vietnam", "0932106352"),
                new Customer("Hồ Tiến Vũ", "338 P. Xã Đàn, Phương Liên, Đống Đa, Hà Nội 100000, Vietnam", "0948563527"),
                new Customer("Võ Như Linh", "97C Đại Cồ Việt, Bách Khoa, Hai Bà Trưng, Hà Nội 10000, Vietnam", "0911665546"),
                new Customer("Lê Đức Phong", "29 P. Trần Đại Nghĩa, Bách Khoa, Hai Bà Trưng, Hà Nội, Vietnam", "0942680541"),
                new Customer("Đinh Tuấn Phong", "10 P. Tạ Quang Bửu, Bách Khoa, Hai Bà Trưng, Hà Nội, Vietnam", "0963561215"),
                new Customer("Đinh Đăng Hiếu", "91 P. Trần Đại Nghĩa, Bách Khoa, Hai Bà Trưng, Hà Nội, Vietnam", "0917959769"),
                new Customer("Vũ Đăng Phong", "92 A P. Lê Thanh Nghị, Bách Khoa, Hai Bà Trưng, Hà Nội, Vietnam", "0912150104"),
                new Customer("Bùi Như Lan", "373 Bạch Mai, Hai Bà Trưng, Hà Nội, Vietnam", "0936720714"),
                new Customer("Đinh Viết Nam", "1 P. Bùi Ngọc Dương, Bạch Mai, Hai Bà Trưng, Hà Nội, Vietnam", "0991972513"),
                new Customer("Lý Phương Hùng", "189 P. Chùa Quỳnh, P, Hai Bà Trưng, Hà Nội, Vietnam", "0977473361"),
                new Customer("Trịnh Quỳnh Lan", "288 Ng. Quỳnh, Thanh Nhàn, Hai Bà Trưng, Hà Nội 100000, Vietnam", "0981268120"),
                new Customer("Bùi Ngọc Điệp", "238 P. Minh Khai, Minh Khai, Hai Bà Trưng, Hà Nội, Vietnam", "0985150448"),
                new Customer("Đinh Phương Lương", "25 Trương Định, Hai Bà Trưng, Hà Nội, Vietnam", "0967820794"),
                new Customer("Nguyễn Thành Đức", "164 Trương Định, Hoàng Mai, Hà Nội, Vietnam", "0942431631"),
                new Customer("Đặng Nguyệt Thuý", "4 P. Vọng, Đồng Tâm, Đống Đa, Hà Nội, Vietnam", "0968293768"),
                new Customer("Trần Nguyệt Thuỷ", "36 Ng. 259 P. Vọng, Đồng Tâm, Hai Bà Trưng, Hà Nội, Vietnam", "0935379414"),
                new Customer("Phạm Phúc Phong", "147 Ng. Trại Cá, Trương Định, Hai Bà Trưng, Hà Nội, Vietnam", "0970440811"),
                new Customer("Phạm Anh Thảo", "10 Ng. Trại Cá, Trương Định, Hai Bà Trưng, Hà Nội, Vietnam", "098990169"),
                new Customer("Võ Hạ Đan", "Ngõ 104 Nguyễn An Ninh, Trương Định, Hoàng Mai, Hà Nội, Vietnam", "092654746"),
                new Customer("Trương Như Linh", "437 Bạch Mai, Trương Định, Hai Bà Trưng, Hà Nội, Vietnam", "0922099156"),
                new Customer("Trần Ngọc Lê", "Ngõ 92 P. Bùi Ngọc Dương, Bạch Mai, Hai Bà Trưng, Hà Nội, Vietnam", "0931683223"),
                new Customer("Đinh Hạ Như", "191 Bà Triệu, Lê Đại Hành, Hai Bà Trưng, Hà Nội, Vietnam", "0961107871"),
        };
        var adapter = DatabaseAdapter.getDbAdapter();
        for(var x : list){
            adapter.insertCustomer(x);
        }
    }
}
