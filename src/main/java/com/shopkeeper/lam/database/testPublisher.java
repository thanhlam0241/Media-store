package com.shopkeeper.lam.database;

import com.shopkeeper.lam.models.*;
import com.shopkeeper.mediaone.database.DatabaseAdapter;


//PUBLISHER


public class testPublisher {
    public void insertPublisher() throws Exception {
         Publisher[] p = new Publisher[30];
         p[0] = new Publisher("Kim Dong","Hà Nội","Nhà xuất bản Kim Đồng là nhà xuất bản chuyên về sách văn học thiếu nhi của Việt Nam được thành lập vào ngày 15 tháng 6 năm 1956 tại Hà Nội.");
         p[1] = new Publisher("Hoyoverse","Shanghai, China","miHoYo Co., Ltd. is a Chinese video game developer based in Shanghai, China. Founded in 2012 by three students from Shanghai Jiao Tong University, miHoYo currently employs 4,000 people.");
         p[2] = new Publisher("Garena","Trung Quốc","LOL,FIFA 4");
         p[3] = new Publisher("HolyWood","USA",":)))");
         p[4] = new Publisher("WibuIsYou","Japan","We can make you be wibu.");
         p[5] = new Publisher("Giáo dục và đào tạo","Việt Nam","xuất bản sách giáo dục");
         p[6] = new Publisher("Bách Khoa Hà Nội","Đại học Bách Khoa Hà Nội","xuất bản sách cho sinh viên Bách Khoa hoặc trường khác");
         p[7] = new Publisher("Nhà xuất bản trẻ","161B Lý Chính Thắng-Phường 7-Quận 3-Thành Phố Hồ Chí Minh","xuất bản truyện tranh,sách phục vụ thanh niên,thiếu nhi");
         p[8] = new Publisher("Nhà xuất bản tổng hợp TPHCM","Số 6/86 Duy Tân-Cầu Giấy-Hà Nội","Sách về chính trị,lịch sử,giáo trình,...");
         p[9] = new Publisher("Nhà xuất bản chính trị Quốc gia","Số 6/86 DUy Tân Cầu Giấy Hà Nội","Xuất bản sách chính trị,lý luận và pháp luật");
         p[10] = new Publisher("Công Thương","Tầng 4, Tòa nhà Bộ Công Thương, số 655 Phạm Văn Đồng, quận Bắc Từ Liêm, Hà Nội","Xuất bản sách chính trị,lý luận và pháp luật");
         p[11] = new Publisher("Công an nhân dân", "92 Nguyễn Du, quận Hai Bà Trưng, TP. Hà Nội", "Không");
         p[12] = new Publisher("Nhà xuất bản Dân trí", "Số 9, ngõ 26, phố Hoàng Cầu, quận Đống Đa, thành phố Hà Nội", "");
         p[13] = new Publisher("Nhà xuất bản Giao thông vận tải", "80B Trần Hưng Đạo, Quận Hoàn Kiếm, Thành phố Hà Nội", "");
         p[14] = new Publisher("Nhà xuất bản Giáo dục Việt Nam", "81 Trần Hưng Đạo - Q. Hoàn KIếm - Hà Nội", "");
         p[15] = new Publisher("Nhà xuất bản Hàng hải", "484 Lạch Tray, Ngô Quyền, Hải Phòng", "");
         p[16] = new Publisher("Big Machine Records", "Nashville, Tennessee, Hoa Kỳ", "Big Machine Records là một hãng thu âm độc lập của Mỹ, được phân phối bởi Universal Music Group và chuyên về các nghệ sĩ nhạc đồng quê và nhạc pop. Big Machine có trụ sở đặt tại Nashville, Tinnessee.");
         p[17] = new Publisher("MTP Entertainment", "Thành phố Hồ Chí Minh, Việt Nam", "M-TP Entertainment có tên đầy đủ là Công ty trách nhiệm hữu hạn M-TP Entertainment (M-TP Entertainment Ltd) là một công ty giải trí, sản xuất âm nhạc đồng thời là hãng đĩa thu âm do nam ca sĩ người Việt Nam Sơn Tùng M-TP thành lập vào ngày 8 tháng 11 năm 2016. Tên công ty được đặt theo nghệ danh khác của Sơn Tùng M-TP là M-TP.[6]Trong tự truyện \"Chạm tới giấc mơ\", Sơn Tùng M-TP cho biết việc thành lập M-TP Entertainment là bước đi giúp anh trở thành một nhà sản xuất âm nhạc thay vì ca sĩ đơn thuần. Sơn Tùng M-TP cũng muốn công ty của mình trở thành một lò đào tạo nghệ sĩ mới.[7][6]. Tuy nhiên, Sơn Tùng M-TP xác định chỉ là chủ sở hữu và làm công việc gắn với chuyên môn âm nhạc. Những công việc khác như kinh doanh, marketing, truyền thông sẽ do cộng sự của anh đảm nhận[8]");
         p[18] = new Publisher("Nhà xuất bản Khoa học Tự nhiên và Công nghệ", "Nhà A16 - Số 18 Hoàng Quốc Việt, Cầu Giấy, Hà Nội", "");
         p[19] = new Publisher("CJ Entertainment", "Seoul,Hàn Quốc", "CJ E&M Film Division (CJ E&M Pictures), trước đây là CJ Entertainment (tiếng Hàn: 씨제이엔터테인먼트, CJ엔터테인먼트), là một công ty giải trí của Hàn Quốc tham gia vào các lĩnh vực triển lãm, sản xuất, đầu tư, phân phối phim. Đây là công ty giải trí lớn nhất ở Hàn Quốc và là một công ty con của tập đoàn CJ");
         p[20] = new Publisher("Universal Pictures", "Universal City, California, Hoa Kỳ", "Universal Pictures là một công ty sản xuất và phân phối phim của Mỹ thuộc sở hữu của Comcast thông qua bộ phận NBCUniversal Phim và Giải trí của NBCUniversal. Được thành lập vào năm 1912 bởi Carl Laemmle, Mark Dintenfass, Charles O. Baumann, Adam Kessel, Pat Powers, William Swanson, David Horsley, Robert H. ");
         p[21] = new Publisher("Marvel Entertainment", " Thành phố New York, Tiểu bang New York, Hoa Kỳ", "Marvel Entertainment, LLC là một công ty giải trí của Mỹ được thành lập vào tháng 6 năm 1998 và có trụ sở tại thành phố New York, được thành lập nhờ sự sáp nhập của Marvel Entertainment Group, Inc. và ToyBiz.");
         p[22] = new Publisher("DATVIET MEDIA - CÔNG TY TNHH ĐẤT VIỆT PROLAB", "Số 43, đường Giải Phóng, Phường Đồng Tâm, Quận Hai Bà Trưng, Tp Hà Nội (TPHN)", "DATVIET MEDIA hoạt động trong lĩnh vực quảng cáo truyền thông " );
         p[23] = new Publisher("Sony", "Minato City, Tokyo, Japan", "Sony Group Corporation, commonly known as Sony and stylized as SONY, is a Japanese multinational conglomerate corporation headquartered in Kōnan, Minato, Tokyo, Japan");
         p[24] = new Publisher("CGV ", "", " Tính đến nay, CGV đang có mặt ở 26 tỉnh thành với 75 cụm rạp và khoảng 445 màn hình, đến năm 2020, CGV dự định sẽ đạt được 96 cụm rạp");
         p[25] = new Publisher("Công ty TNHH Thắng Việt", "Số 13, Đường 26, Khu Dân Cư An Lạc, P. Bình Trị Đông B, Q. Bình Tân,Tp. Hồ Chí Minh", "");
         p[26] = new Publisher("Công Ty Cổ Phần Văn Hóa Tổng Hợp Bến Thành", "142 Pasteur, P. Bến Nghé, Q. 1, Tp. Hồ Chí Minh (TPHCM), Việt Nam", "Công ty Cổ Phần Văn Hóa Tổng Hợp Bến Thành là một trong những công ty chuyên phân phối băng đĩa nhạc, in sang băng-đĩa cam kết chất lượng và giá cả phải chăng  ");
         p[27] = new Publisher("Công Ty Cổ Phần Sản Xuất Thương Mại Những Gương Mặt Âm Nhạc", "90 Đề Thám, P. Cầu Ông Lãnh, Q. 1, Tp. Hồ Chí Minh (TPHCM), Việt Nam", "Công ty Cổ Phần Sản Xuất Thương Mại Những Gương Mặt Âm Nhạc chuyên sản xuất băng đĩa nhạc, sản xuất chương trình và nhà cung cấp phòng thu. Công ty luôn đem đến cho thính giả những sản phẩm âm nhạc đặc sắc nhất, ghi lại dấu ấn trong lòng người nghe nhất");
         p[28] = new Publisher("Công Ty Maxell Asia", "Phòng 15, TầngM, Cao ốc Sun Wah, 115 Nguyễn Huệ, P. Bến Nghé, Q. 1, Tp. Hồ Chí Minh (TPHCM), Việt Nam", "Văn phòng Đại Diện – Công ty Maxell Asia cung cấp băng đĩa nhạc, nhận in sang băng đĩa nhiều nhiều khách hàng tin tưởng và cho nhiều phản hồi tốt từ chất lượng sản phẩm đến dịch vụ");
         p[29] = new Publisher("Công Ty TNHH Thương Mại Kim Như", "151/4 Tôn Thất Đạm, P. Bến Nghé, Q. 1, Tp. Hồ Chí Minh (TPHCM), Việt Nam", "Công ty TNHH Thương Mại Kim Thư Nằm trong top những cơ sở bán đĩa phim chất lượng sản phẩm, uy tín ở TP. Hồ Chí Minh, chuyên cung cấp đĩa trắng nhập khẩu từ các hãng đĩa DVD danh tiếng như Maxell, Sony, Verbatim, Kachi, Melody… được nhiều người thích coi phim , phòng game, dân IT sử dụng.");


        var adapter = DatabaseAdapter.getDbAdapter();

        for(Publisher pi : p){
            adapter.insertPublisher(pi);
        }

    }

}
