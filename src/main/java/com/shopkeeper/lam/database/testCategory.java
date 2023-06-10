package com.shopkeeper.lam.database;

import com.shopkeeper.lam.models.Category;
import com.shopkeeper.mediaone.database.DatabaseAdapter;

//CATEGORY

public class testCategory {
    public void insertCategory() throws Exception {
        //music
        Category x1 = new Category("EDM","Nhạc nhảy điện tử, còn được biết đến với thuật ngữ: EDM, được mô tả như một thể loại nhạc có tiết tấu mạnh kế thừa từ nhạc disco của những năm 1970 và ở một vài khía cạnh nào đó, nó cũng là những thể nghiệm của nhạc Pop, hay còn gọi là Nhạc POP thể nghiệm của các nhạc sĩ/ca sĩ tiền phong");//music
        Category x2 = new Category("Tình ca ","các ca khúc viết về tình yêu");
        Category x3 = new Category("Nhạc Pop","Nhạc Pop thường được phân biệt với các thể loại khác nhờ một số đặc điểm về phong cách nghệ thuật như: nhịp nhảy hay nhịp phách, những giai điệu đơn giản dễ nghe, cùng với một số đoạn trong bài hát được lặp đi lặp lại.");
        Category x4 = new Category("Nhạc đồng quê","Chịu nhiều ảnh hưởng từ những hệ thống nhạc khác như Blues, Jazz. Loại nhạc này thường mang giai điệu trầm buồn. Nguồn gốc của nhạc country xuất phát từ những người dân Anh nhập cư đến nước Mỹ. Họ mang theo các ca khúc Ballad Celtic với phần lời theo lối kể chuyện mộc mạc, bình dân.");
        Category x5 = new Category("Nhạc Rock","Dựa trên tiết tấu của ca ba loại nhạc(Blues, Jazz, Country) nhưng Rock lại có tiết tấu mạnh và nhanh hơn, thường được sử dụng cho các loại nhạc cụ điện tử, phổ biến là Guitar điện phối hợp cùng với đó là guitar bass và trống");
        Category x6 = new Category("Nhạc Dance","Nhạc Dance, hay gọi dân dã là nhạc vũ trường, là một loạt các thể loại nhạc có âm mạnh thường được dùng trong các tụ điểm vũ trường, hộp đêm, phát triển từ thể loại nhạc disco thập niên 1970");
        Category x7 = new Category("Nhạc Blues","Những ca khúc nhạc Blues thường mang đến sự buồn bã, ảm đạm, sâu lắng cho người nghe. Blues là dòng nhạc xuất hiện gần như sớm nhất trong cộng đồng người da đen sống tại Mỹ");
        Category x8 = new Category("Nhạc Jazz","Jazz là một nét văn hoá bản xứ ban đầu chỉ của riêng người Mỹ và đã được tạo ra bởi người Mỹ. Âm nhạc phương Tây và châu Phi là nơi đã gieo hạt nên Jazz, nhưng chính văn hoá Mỹ mới là nơi Jazz nảy mầm và phát triển");
        Category x9 = new Category("Nhạc R&B","R&B là viết tắt của Rhythm and Blues. Thể loại nhạc này có nguồn gốc từ cộng đồng người Phi da đen này đã phát sinh từ đầu thế kỉ 20 và trở thành một loại nhạc phổ biến trên nhiều quốc gia vào khoảng thập niên 40");
        Category x10 = new Category("Nhạc không lời","Nhạc không có lời hát nhưng rất hay,êm tai,thích thợp để thư giãn đầu óc");
        Category x11 = new Category("Nhạc Piano","Loại nhạc đánh đàn piano");
        Category x12 = new Category("Nhạc trữ tình","Nhạc trữ tình là một khái niệm vay mượn từ Trung Quốc. Chữ \"trữ tình\", có nghĩa là mô tả tình cảm.");

        //film
        Category x13 = new Category("Kinh di","phim rat dang so,khong danh cho tre em duoi 18 tuoi");//film
        Category x14 = new Category("Comedy","Thể loại gây hài hước cho người đọc hoặc xem");//film
        Category x15 = new Category("Lãng mạn","Thể loại về tình yêu");//Music,book,film
        Category x16 = new Category("Kịch","là dòng phim thường mang tính tự sự về cuộc đời của một hay nhiều nhân vật. Phim là sự kết hợp của các yếu tố tâm lý xã hội, bi – hài tạo nên một tổng thể mang lại nhiều cảm xúc đan xen lẫn nhau cho người xem.");//Music,book,film
        Category x17 = new Category("Dảk","Thể loại mà nhiều người chết hoặc NTR");//film,book
        Category x18 = new Category("Hành động","có những cuộc chiến nảy lửa,tình tiết kịch tính");//film,book
        Category x19 = new Category("Khoa học viễn tưởng","là thể loại phim với chủ đề tưởng tượng, không có thật như phép thuật, các hiện tượng thần kỳ, siêu nhiên");//film,book
        Category x20 = new Category("Lịch sử","phim có bối cảnh trong lịch sử");
        Category x21 = new Category("Phim tài liệu","là phim ghi lại các hình ảnh thực tế, không có yếu tố hư cấu và thường được sử dụng làm tài liệu giảng dạy hay lưu giữ các sự kiện mang tính chất lịch sử.");
        Category x22 = new Category("Phim ca nhạc","Trong các thể loại phim thì Musical là dạng phim mà nhân vật có ít hoặc không có lời thoại. Thay vào đó là phần âm nhạc được lồng ghép vào bên trong");
        Category x23 = new Category("Phim gia đình","là các phim có nội dung hướng đến đối tượng là mọi thế hệ trong gia đình. Kịch bản phim thường nhẹ nhàng, giải trí có có kết cục có hậu");
        Category x24 = new Category("Phim cổ trang","đây cũng là một thể loại phim đặc trưng của châu Á. Phim cổ trang cơ bản được chia làm hai nhánh nhỏ. Một là, phim tái hiện lại chính xác một sự kiện có thật trong lịch sử. Hai là, phim chỉ dựa trên bối cảnh hay nhân vật lịch sử kết hợp với các yếu tố hư cấu khác");

        //book
        Category x25 = new Category("Light novel","là một dòng tiểu thuyết có nguồn gốc từ Nhật Bản. \"Light\" trong \"light novel\" nghĩa là ngắn, nhẹ về số lượng từ ngữ. Light novel thường được gọi tắt là ranobe hay rainobe.");//book
        Category x26 = new Category("Trinh thám","truyen/phim co noi dung giai do, toi pham, pha an");//book
        Category x27 = new Category("18+","dành cho lứa tuổi 18 trở lên ");
        Category x28 = new Category("12+","Dành cho lứa tuổi trên 12");//Music,book,film
        Category x29 = new Category("Bi kịch","nội dung có những tình tiết bi kịch");//film.book
        Category x30 = new Category("Phiêu lưu"," kể về các chuyến du hành mạo hiểm bao gồm những cuộc tìm kiếm các vùng đất mới, truy tìm kho báu");//book,film
        Category x31 = new Category("Bí ẩn","thường kể về quá trình điều tra một bí ẩn chưa được khám phá");//film
        Category x32 = new Category("Tiểu thuyết lãng mạn","là một trong số các thể loại sách phổ biến nhất khi so về doanh số bán sách. Tiểu thuyết lãng mạn sở hữu một quy mô kinh doanh đa dạng nhất trong thị trường sách, các tác phẩm này được trưng bày khắp nơi từ các quầy thanh toán ở cửa hàng tạp hóa, đến các ấn phẩm định kỳ của nhà xuất bản trên nền tảng trực tuyến, cũng như thông qua các dịch vụ tự xuất bản.");
        Category x33 = new Category("Giả tưởng và khoa học viễn tưởng","Sách giả tưởng thường diễn ra trong một khoảng thời gian khác với thời gian hiện tại của chúng ta. Chúng thường có các sinh vật huyền bí, từ pháp sư / phù thuỷ cho đến những thây ma không có thật");
        Category x34 = new Category("Kinh dị, giật gân","Các thể loại sách này bao gồm các ấn phẩm thường có mối liên hệ mật thiết đến những thể loại sách Mystery và đôi khi là giả tưởng – Fantasy, yếu tố ly kỳ và kinh dị tạo nên sự hồi hộp và điểm nổi bật của thể loại sách phổ biến này. ");
        Category x35 = new Category("Sách truyền cảm hứng","Nhiều cuốn sách về self-help liên quan đến các bài học thành công trong kinh doanh hoặc bí quyết kinh doanh hiện đang đứng dầu trong các danh sách Best Sellers của thị trường này.");
        Category x36 = new Category("Tiểu sử, tự truyện và hồi ký","Đây là các thể loại sách phi hư cấu dùng để kể những câu chuyện về cuộc đời của một người.");

        Category x37 = new Category("Unknown","Bằng cách nào đó,chúng tôi không biết được thể loại là gì");



        var adapter = DatabaseAdapter.getDbAdapter();
        adapter.insertCategory(x1);
        adapter.insertCategory(x2);
        adapter.insertCategory(x3);
        adapter.insertCategory(x4);
        adapter.insertCategory(x5);
        adapter.insertCategory(x6);
        adapter.insertCategory(x7);
        adapter.insertCategory(x8);
        adapter.insertCategory(x9);
        adapter.insertCategory(x10);
        adapter.insertCategory(x11);
        adapter.insertCategory(x12);
        adapter.insertCategory(x13);
        adapter.insertCategory(x14);
        adapter.insertCategory(x15);
        adapter.insertCategory(x16);
        adapter.insertCategory(x17);
        adapter.insertCategory(x18);
        adapter.insertCategory(x19);
        adapter.insertCategory(x20);
        adapter.insertCategory(x21);
        adapter.insertCategory(x22);
        adapter.insertCategory(x23);
        adapter.insertCategory(x24);
        adapter.insertCategory(x25);
        adapter.insertCategory(x26);
        adapter.insertCategory(x27);
        adapter.insertCategory(x28);
        adapter.insertCategory(x29);
        adapter.insertCategory(x30);
        adapter.insertCategory(x31);
        adapter.insertCategory(x32);
        adapter.insertCategory(x33);
        adapter.insertCategory(x34);
        adapter.insertCategory(x35);
        adapter.insertCategory(x36);
        adapter.insertCategory(x37);



    }


}
