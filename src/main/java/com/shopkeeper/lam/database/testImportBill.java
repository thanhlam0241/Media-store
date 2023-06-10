package com.shopkeeper.lam.database;


import com.shopkeeper.linh.seeders.DataSeeder;
import com.shopkeeper.mediaone.database.DatabaseAdapter;
import com.shopkeeper.minh.models.ImportBill;

import java.time.LocalDate;

public class testImportBill {
    public void insert() throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        ImportBill[] bookBills = new ImportBill[20];
        ImportBill[] filmBills = new ImportBill[20];
        ImportBill[] musicBills = new ImportBill[20];

        //music
        musicBills[0] = new ImportBill("Đĩa nhạc Lạc Trôi", 100000, LocalDate.of(2021,12,4), true, "3 đĩa", "Công Ty Cổ Phần Văn Hóa Tổng Hợp Bến Thành");
        musicBills[1] = new ImportBill("Đĩa nhạc Hãy trao cho anh", 100000, LocalDate.of(2021,11,3), true, "3 đĩa", "Tổng Công Ty Văn Hóa Sài Gòn");
        musicBills[2] = new ImportBill("Đĩa nhạc Nơi này có anh", 100000, LocalDate.of(2021,6,5), true, "3 đĩa", "Công Ty Maxell Asia");
        musicBills[3] = new ImportBill("Đĩa nhạc Chạy ngay đi ", 100000, LocalDate.of(2022,2,1), true, "3 đĩa", "Tổng Công Ty Văn Hóa Sài Gòn");
        musicBills[4] = new ImportBill("Đĩa nhạc Chúng ta không thuộc về nhau", 100000, LocalDate.of(2021,12,5), true, "3 đĩa", "Công Ty Cổ Phần Văn Hóa Tổng Hợp Bến Thành");
        musicBills[5] = new ImportBill("Đĩa nhạc Có chắc yêu là đây", 100000, LocalDate.of(2021,12,6), true, "3 đĩa", "Công Ty Maxell Asia");
        musicBills[6] = new ImportBill("Đĩa nhạc Âm thầm bên em", 100000, LocalDate.of(2021,11,7), true, "3 đĩa", "Công Ty Maxell Asia");
        musicBills[7] = new ImportBill("Đĩa nhạc Lâu Đài Tình Ái", 100000, LocalDate.of(2021,10,3), true, "3 đĩa", "Công Ty Cổ Phần Văn Hóa Tổng Hợp Bến Thành");
        musicBills[8] = new ImportBill("Đĩa nhạc Blank Space", 100000, LocalDate.of(2021,11,9), true, "3 đĩa", "Tổng Công Ty Văn Hóa Sài Gòn");
        musicBills[9] = new ImportBill("Đĩa nhạc I Knew You Were Trouble", 100000, LocalDate.of(2021,5,4), true, "3 đĩa", "Công Ty Maxell Asia");
        musicBills[10] = new ImportBill("Đĩa nhạc Love Story", 100000, LocalDate.of(2021,8,4), true, "3 đĩa", "Tổng Công Ty Văn Hóa Sài Gòn");
        musicBills[11] = new ImportBill("Đĩa nhạc Lover", 100000, LocalDate.of(2022,5,4), true, "3 đĩa", "Tổng Công Ty Văn Hóa Sài Gòn");
        musicBills[12] = new ImportBill("Đĩa nhạc You Belong with Me", 100000, LocalDate.of(6,12,4), true, "3 đĩa", "Tổng Công Ty Văn Hóa Sài Gòn");
        musicBills[13] = new ImportBill("Đĩa nhạc Shake It Off", 100000, LocalDate.of(2022,3,4), true, "3 đĩa", "Công Ty Maxell Asia");
        musicBills[14] = new ImportBill("Đĩa nhạc Bad Blood", 100000, LocalDate.of(2022,2,4), true, "3 đĩa", "Công Ty Cổ Phần Văn Hóa Tổng Hợp Bến Thành");
        musicBills[15] = new ImportBill("Đĩa nhạc Sakura Sakura", 100000, LocalDate.of(2022,5,4), true, "3 đĩa", "Công Ty Maxell Asia");
        musicBills[16] = new ImportBill("Đĩa nhạc Faded", 100000, LocalDate.of(2022,12,5), true, "3 đĩa", "Công Ty Cổ Phần Văn Hóa Tổng Hợp Bến Thành");
        musicBills[17] = new ImportBill("Đĩa nhạc On my way", 100000, LocalDate.of(2022,4,4), true, "3 đĩa", "Tổng Công Ty Văn Hóa Sài Gòn");
        musicBills[18] = new ImportBill("Đĩa nhạc Sing Me to Sleep", 100000, LocalDate.of(2022,6,4), true, "3 đĩa", "Công Ty Maxell Asia");
        musicBills[19] = new ImportBill("Đĩa nhạc The Spectre", 100000, LocalDate.of(2022,7,1), true, "3 đĩa", "Công Ty Cổ Phần Văn Hóa Tổng Hợp Bến Thành");

        //film
        filmBills[0]=new ImportBill("Đĩa phim Thánh bài", 100000, LocalDate.of(2021,5,13), true, "3 đĩa", "Công Ty Cổ Phần Truyền Thông Trí Việt");
        filmBills[1]=new ImportBill("Đĩa phim Titanic", 100000, LocalDate.of(2021,3,23), true, "3 đĩa", "Công Ty TNHH Webbing Green Vina");
        filmBills[2]=new ImportBill("Đĩa phim Tuyệt đỉnh Kung-fu", 50000, LocalDate.of(2021,8,3), true, "3 đĩa", "Công Ty Cổ Phần Truyền Thông Trí Việt");
        filmBills[3]=new ImportBill("Đĩa phim Ký sinh trùng", 100000, LocalDate.of(2021,3,5), true, "3 đĩa", "Hồ Gươm Audio - Công Ty Cổ Phần Mỹ Thuật Và Vật Phẩm Văn Hóa");
        filmBills[4]=new ImportBill("Đĩa phim Người Nhện 2", 100000, LocalDate.of(2021,8,6), true, "3 đĩa", "Công Ty TNHH Webbing Green Vina");
        filmBills[5]=new ImportBill("Đĩa phim Đội bóng thiếu lâm", 80000, LocalDate.of(2021,1,1), true, "3 đĩa", "Hồ Gươm Audio - Công Ty Cổ Phần Mỹ Thuật Và Vật Phẩm Văn Hóa");
        filmBills[6]=new ImportBill("Đĩa phim Người Nhện 3", 100000, LocalDate.of(2021,2,12), true, "3 đĩa", "Công Ty TNHH Webbing Green Vina");
        filmBills[7]=new ImportBill("Đĩa phim Mỹ nhân ngư", 100000, LocalDate.of(2021,9,4), true, "3 đĩa", "Công Ty TNHH Webbing Green Vina");
        filmBills[8]=new ImportBill("Đĩa phim Người Sắt", 100000, LocalDate.of(2021,6,16), true, "3 đĩa", "Công Ty Cổ Phần Truyền Thông Trí Việt");
        filmBills[9]=new ImportBill("Đĩa phim Người sắt 2", 250000, LocalDate.of(2021,11,3), true, "3 đĩa", "Công Ty TNHH Webbing Green Vina");
        filmBills[10]=new ImportBill("Đĩa phim Spider-Man", 205000, LocalDate.of(2021,4,3), true, "3 đĩa", "Hồ Gươm Audio - Công Ty Cổ Phần Mỹ Thuật Và Vật Phẩm Văn Hóa");
        filmBills[11]=new ImportBill("Đĩa phim Avengers: Infinity War", 120000, LocalDate.of(2021,2,23), true, "3 đĩa", "Công Ty Cổ Phần Truyền Thông Trí Việt");
        filmBills[12]=new ImportBill("Đĩa phim Rô-bốt đại chiến", 120000, LocalDate.of(2022,5,22), true, "3 đĩa", "Công Ty TNHH Webbing Green Vina");
        filmBills[13]=new ImportBill("Đĩa phim Transformers: Revenge of the Fallen", 200000, LocalDate.of(2022,1,21), true, "3 đĩa", "Hồ Gươm Audio - Công Ty Cổ Phần Mỹ Thuật Và Vật Phẩm Văn Hóa");
        filmBills[14]=new ImportBill("Đĩa phim Transformers: Dark of the Moon", 200000, LocalDate.of(2021,6,23), true, "3 đĩa", "Công Ty TNHH Webbing Green Vina");
        filmBills[15]=new ImportBill("Đĩa phim Transformers: Age of Extinction", 200000, LocalDate.of(2022,2,12), true, "3 đĩa", "Công Ty Cổ Phần Truyền Thông Trí Việt");
        filmBills[16]=new ImportBill("Đĩa phim Transformers: The Last Knight", 200000, LocalDate.of(2022,5,17), true, "3 đĩa", "Công Ty TNHH Webbing Green Vina");
        filmBills[17]=new ImportBill("Đĩa phim Biệt đội siêu anh hùng", 200000, LocalDate.of(2022,3,3), true, "3 đĩa", "Hồ Gươm Audio - Công Ty Cổ Phần Mỹ Thuật Và Vật Phẩm Văn Hóa");
        filmBills[18]=new ImportBill("Đĩa phim Avengers: Đế chế Ultron", 200000, LocalDate.of(2022,6,19), true, "3 đĩa", "Công Ty Cổ Phần Truyền Thông Trí Việt");
        filmBills[19]=new ImportBill("Đĩa phim Avengers: Endgame", 200000, LocalDate.of(2022,5,3), true, "3 đĩa", "Hồ Gươm Audio - Công Ty Cổ Phần Mỹ Thuật Và Vật Phẩm Văn Hóa");

        //book
        bookBills[0] = new ImportBill("Sách Cuộc sống không giới hạn", 50000, LocalDate.of(2021,9,1), true, "3 sách", "Nhà xuất bản Giáo dục Việt Nam");
        bookBills[1] = new ImportBill("Sách Đừng Bao Giờ Từ Bỏ Khát Vọng", 50000, LocalDate.of(2021,12,1), true, "3 sách", "Nhà xuất bản Giáo dục Việt Nam");
        bookBills[2] = new ImportBill("Sách Sống Cho Điều Ý Nghĩa Hơn", 50000, LocalDate.of(2021,12,1), true, "3 sách", "Nhà xuất bản Giáo dục Việt Nam");
        bookBills[3] = new ImportBill("Sách Cái Ôm Diệu Kỳ", 50000, LocalDate.of(2021,2,5), true, "3 sách", "Nhà xuất bản Giáo dục Việt Nam");
        bookBills[4] = new ImportBill("Sách Đứng Dậy Mạnh Mẽ", 50000, LocalDate.of(2021,11,11), true, "3 sách", "Nhà xuất bản Giáo dục Việt Nam");
        bookBills[5] = new ImportBill("Sách Nhật ký trong tù", 50000, LocalDate.of(2021,6,16), true, "3 sách", "Nhà xuất bản Giáo dục Việt Nam");
        bookBills[6] = new ImportBill("Sách Bản án chế độ thực dân Pháp", 50000, LocalDate.of(2021,1,11), true, "3 sách", "Nhà xuất bản Giáo dục Việt Nam");
        bookBills[7] = new ImportBill("Sách Con đường phía trước (The Road Ahead)", 50000, LocalDate.of(2021,10,1), true, "3 sách", "Nhà xuất bản Dân trí");
        bookBills[8] = new ImportBill("Sách Tốc Độ Tư Duy", 50000, LocalDate.of(2021,1,11), true, "3 sách", "Nhà xuất bản Dân trí");
        bookBills[9] = new ImportBill("Sách How to Prevent the Next Pandemic", 50000, LocalDate.of(2021,11,1), true, "3 sách", "Nhà xuất bản tổng hợp TPHCM");
        bookBills[10] = new ImportBill("Sách Bill Gates Quotes: Bill Gates, Quotes, Quotations, Famous", 50000, LocalDate.of(2022,1,1), true, "3 sách", "Nhà xuất bản tổng hợp TPHCM");
        bookBills[11] = new ImportBill("Sách How to Avoid a Climate Disaster", 50000, LocalDate.of(2021,6,16), true, "3 sách", "Nhà xuất bản Dân trí");
        bookBills[12] = new ImportBill("Sách The Art of Avatar", 50000, LocalDate.of(2021,1,19), true, "3 sách", "Nhà xuất bản tổng hợp TPHCM");
        bookBills[13] = new ImportBill("Sách Battleship Bismarck: A Design and Operational History", 50000, LocalDate.of(2022,7,1), true, "3 sách", "Nhà xuất bản Dân trí");
        bookBills[14] = new ImportBill("Sách Ghosts of the Titanic", 50000, LocalDate.of(2021,12,10), true, "3 sách", "Nhà xuất bản Dân trí");
        bookBills[15] = new ImportBill("Sách Dr. Horrible", 50000, LocalDate.of(2021,12,12), true, "3 sách", "Nhà xuất bản Dân trí");
        bookBills[16] = new ImportBill("Sách Astonishing X-Men - Volume 1: Gifted", 50000, LocalDate.of(2022,1,19), true, "3 sách", "Nhà xuất bản Dân trí");
        bookBills[17] = new ImportBill("Sách Astonishing X-Men: Torn. Vol. 3", 50000, LocalDate.of(2021,10,13), true, "3 sách", "Nhà xuất bản tổng hợp TPHCM");
        bookBills[18] = new ImportBill("Sách Chạm tới giấc mơ", 50000, LocalDate.of(2021,9,16), true, "3 sách", "Nhà xuất bản tổng hợp TPHCM");
        bookBills[19] = new ImportBill("Sách Đắc nhân tâm", 50000, LocalDate.of(2021,6,21), true, "3 sách", "Nhà xuất bản tổng hợp TPHCM");

        for(var x : bookBills){
            adapter.insertImportBill(x);
        }
        for(var x : filmBills){
            adapter.insertImportBill(x);
        }
        for(var x : musicBills){
            adapter.insertImportBill(x);
        }



    }
}
