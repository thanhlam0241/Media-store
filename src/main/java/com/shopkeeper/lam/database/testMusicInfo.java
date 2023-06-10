package com.shopkeeper.lam.database;

import com.shopkeeper.lam.funtions.ProductInfoManager;
import com.shopkeeper.lam.models.Category;
import com.shopkeeper.lam.models.MusicInfo;
import com.shopkeeper.lam.models.Person;
import com.shopkeeper.lam.models.Publisher;
import com.shopkeeper.mediaone.database.DatabaseAdapter;

import java.sql.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class testMusicInfo {
    public void insert() throws Exception {

        var adapter = DatabaseAdapter.getDbAdapter();
        var categories = adapter.getAllCategories();
        var people = adapter.getAllPeople();
        var publishers = adapter.getAllPublishers();
        MusicInfo[] m = new MusicInfo[20];
        m[0] = new MusicInfo("Lạc Trôi", "Lạc trôi là một bài hát của nam ca sĩ kiêm sáng tác nhạc Sơn Tùng M-TP được trích từ album phòng thu đầu tiên của anh, m-tp M-TP (2017). Nó được phát hành với vai trò là đĩa đơn mở đường trong album. Bài hát được sáng tác và thể hiện bởi chính Sơn Tùng với sự đồng hỗ trợ của nhà sản xuất Triple D. Ca khúc được phát hành lần đầu tiên trên hệ thống YouTube vào lúc ngày 1 tháng 1 năm 2017 giờ Việt Nam[1] và được phát hành trên hệ thống cửa hàng iTunes bởi công ty M-TP Entertainment và bởi Nhac.vn. Đây là ca khúc đầu tiên của Sơn Tùng M-TP trong năm 2017 cũng như ca khúc đầu tiên của Sơn Tùng sau khi rời khỏi WePro Entertainment và người quản lý cũ Nguyễn Quang Huy.",
                categories.get(0),
                LocalDate.of(2017, 1, 1), 500000, publishers.get(17), 5,
                new ArrayList<>(Arrays.asList("Top thịnh hành Youtube")),
                new ArrayList<>(Arrays.asList(people.get(4))),
                LocalTime.of(0, 4, 15));
        m[1] = new MusicInfo("Hãy trao cho anh", ":)))) nghe phèn vl",
                categories.get(2),
                LocalDate.of(2019, 7, 1), 100000, publishers.get(17), 5,
                new ArrayList<>(Arrays.asList("Top thịnh hành trên YouTube")),
                new ArrayList<>(Arrays.asList(people.get(4))),
                LocalTime.of(0, 4, 30));
        m[2] = new MusicInfo("Nơi này có anh", "\"Nơi này có anh\" là một bài hát của nam ca sĩ kiêm sáng tác nhạc Sơn Tùng M-TP nằm trong album phòng thu đầu tiên của anh, m-tp M-TP (2017). Nó được phát hành dưới dạng đĩa đơn thứ 2 được trích ra từ album. Ca khúc cùng với MV chính thức được Sơn Tùng cho ra mắt vào lúc 0:00 (GMT+7) ngày 14 tháng 2 năm 2017 nhân dịp ngày Valentine nhằm tri ân những người hâm mộ luôn sát cánh bên anh trong suốt thời gian qua.[1] Bản MP3 của ca khúc được phát hành trên hệ thống cửa hàng iTunes bởi công ty M-TP Entertainment và bởi Nhac.vn trong cùng ngày. Đây là ca khúc thứ hai của Sơn Tùng M-TP trong năm 2017 cũng như ca khúc thứ hai của Sơn Tùng sau khi rời khỏi WePro Entertainment và người quản lỹ cũ Nguyễn Quang Huy.",
                categories.get(8),
                LocalDate.of(2017, 2, 14), 100000, publishers.get(17), 5,
                new ArrayList<>(Arrays.asList("Top thịnh hành trên YouTube")),
                new ArrayList<>(Arrays.asList(people.get(4))),
                LocalTime.of(0, 4, 30));
        m[3] = new MusicInfo("Chạy ngay đi", "\"Chạy ngay đi\" (tiếng Anh: \"Run Now\", tiếng Thái: เรียกใช้เดี๋ยวนี้[2]) là một bài hát của ca sĩ kiêm sáng tác nhạc Sơn Tùng M-TP nằm trong album phòng thu thứ hai của anh, Chúng ta (2020), được phát hành song song cùng video âm nhạc phát hành trên YouTube vào lúc 0:00 (GMT+7) ngày 12 tháng 5 năm 2018 bởi M-TP Entertainment – công ty do chính Sơn Tùng điều hành.[3] Đây là ca khúc đầu tiên của Sơn Tùng được phát hành trong năm 2018 cũng như ca khúc đầu tiên trong lần quay trở lại sau hơn một năm \"vắng bóng\".[3] Bài hát được phát hành với vai trò là ca khúc đầu tiên từ album phòng thu đầu tiên của anh, Chúng ta (2020).",
                categories.get(8),
                LocalDate.of(2018, 5, 12), 100000, publishers.get(17), 5,
                new ArrayList<>(Arrays.asList("Top thịnh hành trên YouTube")),
                new ArrayList<>(Arrays.asList(people.get(4))),
                LocalTime.of(0, 4, 30));
        m[4] = new MusicInfo("Chúng ta không thuộc về nhau", "\"Chúng ta không thuộc về nhau\" là bài hát được thu âm bởi nam ca sĩ kiêm sáng tác nhạc Sơn Tùng M-TP. Bài hát được sáng tác bởi chính Sơn Tùng với sự đồng hỗ trợ của nhà sản xuất Triple D. Bài hát đã được phát hành trực tuyến vào ngày 2 tháng 8 năm 2016. Đoạn video quảng bá cho clip nhạc chính thức được đưa lên YouTube vào ngày 26 tháng 7 năm 2016. Bài hát được ra mắt chính thức trong một buổi biểu diễn ca nhạc vào ngày 2 tháng 8 năm 2016. Video âm nhạc chính thức của ca khúc được đăng tải lên YouTube vào lúc 0h ngày 3 tháng 8 năm 2016 và bản audio chính thức cũng được phát hành trên hệ thống Nhaccuatui. Đây là ca khúc thứ 2 và cuối cùng được Sơn Tùng M-TP cho ra mắt trong năm 2016 và là ca khúc cuối cùng trước khi anh rời khỏi ông \"bầu\" Nguyễn Quang Huy.",
                categories.get(2),
                LocalDate.of(2016, 2, 8), 100000, publishers.get(17), 5,
                new ArrayList<>(Arrays.asList("Top thịnh hành trên YouTube")),
                new ArrayList<>(Arrays.asList(people.get(4))),
                LocalTime.of(0, 4, 30));
        m[5] = new MusicInfo("Có chắc yêu là đây", "Có chắc yêu là đây là một bài hát của nam ca sĩ kiêm sáng tác nhạc người Việt Nam Sơn Tùng M-TP. Bài hát được phát hành bởi M-TP Entertainment vào ngày 5 tháng 7 năm 2020 cùng với video âm nhạc trên YouTube với vai trò là \"món quà thứ hai của năm 2020 sau Sky Tour Movie\" nhằm dành tặng đến người hâm mộ của Sơn Tùng đồng thời dọn đường cho album phòng thu sắp tới của Tùng, Chúng ta (2020) trước khi phát hành. Bài hát được sáng tác bởi Sơn Tùng M-TP và sản xuất bởi Onionn.",
                categories.get(8),
                LocalDate.of(2020, 5, 7), 100000, publishers.get(17), 5,
                new ArrayList<>(Arrays.asList("Top thịnh hành trên YouTube")),
                new ArrayList<>(Arrays.asList(people.get(4))),
                LocalTime.of(0, 4, 30));
        m[6] = new MusicInfo("Âm thầm bên em", "Âm thầm bên em là một bài hát được thu âm bởi ca sĩ kiêm sáng tác nhạc người Việt Nam Sơn Tùng M-TP. Bài hát được phát hành vào ngày 22 tháng 8 năm 2015 trên Zing MP3 cùng với một video âm nhạc trên YouTube, nhưng vì những tranh chấp tác quyền với Zing MP3, bài hát đã bị gỡ khỏi nền tảng này. Được sản xuất bởi Long Halo và hoà âm bởi SlimV,[1] sau khi phát hành, mặc dù \"Âm thầm bên em\" có những thành công về mặt thương mại khá khiêm tốn so với các bài hát trước kia của anh, nhưng bài hát lại được xem là một trong những tác phẩm nổi bật nhất sự nghiệp của Sơn Tùng M-TP. Bài hát đã đoạt một giải Làn Sóng Xanh cho hạng mục Bài hát của năm.[2][3] Bài hát đã được thêm vào album tuyển tập đầu tay của Tùng, m-tp M-TP (2017) với vai trò là bài hát thứ 11.",
                categories.get(2),
                LocalDate.of(2015, 8, 22), 100000, publishers.get(17), 5,
                new ArrayList<>(Arrays.asList("Top thịnh hành trên YouTube")),
                new ArrayList<>(Arrays.asList(people.get(4))),
                LocalTime.of(0, 4, 30));
        m[7] = new MusicInfo("Lâu Đài Tình Ái", "Lâu Đài Tình Ái là một ca khúc nhạc trữ tình, được sáng tác bởi nhạc sĩ Trần Thiện Thanh năm 1966, và được thi sĩ Mai Trung Tĩnh đặt lời.",
                categories.get(11),
                LocalDate.of(2015, 1, 19), 100000, publishers.get(17), 5,
                new ArrayList<>(Arrays.asList("Không")),
                new ArrayList<>(Arrays.asList(people.get(22))),
                LocalTime.of(0, 4, 30));
        m[8] = new MusicInfo("Blank Space", "\"Blank Space\" là ca khúc do ca sĩ-nhạc sĩ người Mỹ Taylor Swift thu âm cho album phòng thu thứ năm của cô, 1989 (2014). Ca khúc do Swift, Max Martin và Shellback sáng tác. Ca khúc được phát hành trên đài phát thanh hit hiện đại bởi hãng đĩa Big Machine Records vào ngày 10 tháng 11 năm 2014 dưới dạng đĩa đơn thứ hai từ album này, sau \"Shake It Off\". Đây là ca khúc thứ hai trong album, sau \"Welcome to New York\" và là một trong số ba ca khúc có bản thu nháp (voice memo) được phát hành trong phiên bản Deluxe của album. \"Blank Space\" từng giữ vị trí thứ nhất trên bảng xếp hạng Billboard Hot 100 trong 7 tuần liên tiếp.",
                categories.get(2),
                LocalDate.of(2014, 10, 11), 100000, publishers.get(15), 5,
                new ArrayList<>(Arrays.asList("Ca khúc đứng đầu bảng xếp hạng Hot Digital Songs")),
                new ArrayList<>(Arrays.asList(people.get(29))),
                LocalTime.of(0, 4, 30));
        m[9] = new MusicInfo("I Knew You Were Trouble", "\"I Knew You Were Trouble\" là một ca khúc của nữ ca sĩ-nhạc sĩ người Mỹ Taylor Swift trích từ album phòng thu thứ tư của cô, Red (2012). Ca khúc được phát hành vào ngày 9 tháng 10, 2012 bởi Big Machine Records và là đĩa đơn quảng bá thứ ba của album. Sau đó, \"I Knew You Were Trouble\" được chọn làm đĩa đơn chính thức thứ ba từ Red, phát hành vào ngày 27 tháng 11, 2012 tại Mỹ. Ở Anh, đây là đĩa đơn thứ hai và được phát hành vào ngày 10 tháng 12, 2012. Ca khúc này được sáng tác bởi Taylor Swift, cùng với Max Martin và Shellback.",
                categories.get(2),
                LocalDate.of(2012, 10, 12), 100000, publishers.get(15), 5,
                new ArrayList<>(Arrays.asList("Unknown")),
                new ArrayList<>(Arrays.asList(people.get(29))),
                LocalTime.of(0, 4, 30));
        m[10] = new MusicInfo("Love Story", "\"Love Story\" là một bài hát của nghệ sĩ thu âm người Mỹ Taylor Swift nằm trong album phòng thu thứ hai của cô, Fearless (2008). Nó được phát hành vào ngày 12 tháng 9 năm 2008 như là đĩa đơn đầu tiên trích từ album bởi Big Machine Records.",
                categories.get(2),
                LocalDate.of(2008, 9, 12), 100000, publishers.get(15), 5,
                new ArrayList<>(Arrays.asList("Unknown")),
                new ArrayList<>(Arrays.asList(people.get(29))),
                LocalTime.of(0, 4, 30));
        m[11] = new MusicInfo("Lover", "\"Lover\" là một bài hát được sáng tác và thu âm bởi nữ ca sĩ kiêm nhạc sĩ người Mỹ Taylor Swift trong album thứ bảy của cô là Lover (album) (2019). Bái hát được sản xuất bởi Jack Antonoff và Swift, \"Lover\" được phát hành dưới dạng đĩa đơn thứ ba của album vào ngày 16 tháng 8 năm 2019 bới hãng đĩa Republic Records.",
                categories.get(3),
                LocalDate.of(2019, 8, 16), 100000, publishers.get(15), 5,
                new ArrayList<>(Arrays.asList("Unknown")),
                new ArrayList<>(Arrays.asList(people.get(29))),
                LocalTime.of(0, 3, 41));
        m[12] = new MusicInfo("You Belong with Me", "\"You Belong with Me\" là một bài hát của nghệ sĩ thu âm người Mỹ Taylor Swift nằm trong album phòng thu thứ hai của cô, Fearless (2008). Nó được phát hành như là đĩa đơn thứ ba trích từ album vào ngày 18 tháng 4 năm 2009 bởi Big Machine Records và Universal Republic Records",
                categories.get(2),
                LocalDate.of(2009, 4, 18), 100000, publishers.get(15), 5,
                new ArrayList<>(Arrays.asList("Unknown")),
                new ArrayList<>(Arrays.asList(people.get(29))),
                LocalTime.of(0, 4, 30));
        m[13] = new MusicInfo("Shake It Off", "\"Shake It Off\" (Lắc nó đi) là một bài hát của nữ ca sĩ kiêm sáng tác nhạc người Mỹ Taylor Swift, nằm trong album phòng thu thứ năm của cô, 1989. Được sáng tác bởi chính Swift cùng Max Martin và Shellback, bài hát đánh dấu bước đột phá lớn trong âm nhạc của cô, từ thể loại Country pop quen thuộc trước đây sang các yếu tố pop mang tính đại chúng hơn kết hợp cùng tiếng kèn saxophone. Bài hát được công bố lần đầu thông qua buổi truyền tiếp trên Yahoo! vào ngày 18 tháng 8 năm 2014, và được phát hành trên hệ thống cửa hàng iTunes bởi hãng đĩa Big Machine Records như là đĩa đơn đầu tiên trích từ 1989.",
                categories.get(2),
                LocalDate.of(2014, 8, 18), 100000, publishers.get(15), 5,
                new ArrayList<>(Arrays.asList("Unknown")),
                new ArrayList<>(Arrays.asList(people.get(29))),
                LocalTime.of(0, 4, 30));
        m[14] = new MusicInfo("Bad Blood", "\"Bad Blood\" là một bài hát của ca sĩ người Mỹ Taylor Swift nằm trong album phòng thu thứ năm của cô, 1989 (2014). Phiên bản remix của bài hát, với sự tham gia góp giọng của rapper người Mỹ Kendrick Lamar, được phát hành vào ngày 17 tháng 5 năm 2015, bởi Republic Records như là đĩa đơn thứ 4 trích từ album. Phiên bản gốc của nó do Swift, Max Martin, và Shellback đồng sáng tác, trong khi Lamar viết phần rap của mình cho bản remix. Nội dung của \"Bad Blood\" đề cập đến sự phản bội của một người bạn thân, mà theo suy đoán của giới phê bình thì ca sĩ Katy Perry chính là nhân vật được đề cập đến.",
                categories.get(2),
                LocalDate.of(2015, 5, 17), 100000, publishers.get(15), 5,
                new ArrayList<>(Arrays.asList("Unknown")),
                new ArrayList<>(Arrays.asList(people.get(29))),
                LocalTime.of(0, 4, 30));
        m[15] = new MusicInfo("Sakura Sakura", "\"Sakura Sakura\" (さくら さくら Hoa anh đào, hoa anh đào?), cũng có tên khác là \"Sakura\", là một bài hát dân ca của Nhật Bản miêu tả mùa xuân, mùa hoa anh đào nở. Nguồn gốc điệu nhạc này không hẳn là một giai điệu truyền thống lâu đời mà chỉ xuất hiện vào cuối thời kỳ Edo sang đầu triều Minh Trị. Giai điệu này được phổ biến khi được đưa vào một ấn phẩm dành cho học viên mới bắt đầu học đàn koto của Học viện âm nhạc Tokyo, do Bộ Giáo dục Nhật phát hành năm 1888",
                categories.get(2),
                LocalDate.of(2014, 10, 11), 100000, publishers.get(15), 5,
                new ArrayList<>(Arrays.asList("Unknown")),
                new ArrayList<>(Arrays.asList(people.get(17))),
                LocalTime.of(0, 4, 30));
        m[16] = new MusicInfo("Faded", "\"Faded\" là một bài hát ballad điện tử năm 2015 bởi nhà sản xuất nhạc người Anh-Na Uy Alan Walker, phát hành vào ngày 25 tháng 11 năm 2015. Bài hát trở nên hết sức thành công, khi nằm trong top 10 ở hầu hết các bảng xếp hạng âm nhạc ở nhiều nước, và vươn tới hạng nhất ở trên 10 bảng xếp hạng.",
                categories.get(0),
                LocalDate.of(2017, 3, 2), 100000, publishers.get(15), 5,
                new ArrayList<>(Arrays.asList("Unknown")),
                new ArrayList<>(Arrays.asList(people.get(8))),
                LocalTime.of(0, 4, 30));
        m[17] = new MusicInfo("On my way", "\"On My Way\" là một bài hát của DJ người Na Uy Alan Walker sáng tác cùng ca sĩ người Mỹ Sabrina Carpenter và ca sĩ người Puerto Rico Farruko, được phát hành dưới dạng đĩa đơn vào 21 tháng 3 năm 2019 qua hai hãng đĩa MER và Sony Music. Farruko đã đóng góp một đoạn tiếng Tây Ban Nha cho bài hát.",
                categories.get(0),
                LocalDate.of(2019, 3, 21), 100000, publishers.get(15), 5,
                new ArrayList<>(Arrays.asList("Unknown")),
                new ArrayList<>(Arrays.asList(people.get(8))),
                LocalTime.of(0, 4, 30));
        m[18] = new MusicInfo("Sing Me to Sleep", "\"Sing Me to Sleep\" là một bài hát của nhà sản xuất nhạc người Anh-Na Uy Alan Walker. Bài hát có sự góp mặt của ca sĩ thu âm Iselin Solheim, và được phát hành vào ngày 3 tháng 6 năm 2016.[1] Một video ca nhạc cho đĩa đơn đã được quay tại Hồng Kông.",
                categories.get(0),
                LocalDate.of(2016, 3, 6), 100000, publishers.get(15), 5,
                new ArrayList<>(Arrays.asList("Unknown")),
                new ArrayList<>(Arrays.asList(people.get(8))),
                LocalTime.of(0, 4, 30));
        m[19] = new MusicInfo("The Spectre", "\"The Spectre\" là bài hát EDM được sáng tác bởi DJ và nghệ sĩ người Na Uy Alan Walker, kết hợp với các ca sĩ không được công nhận được nhạc sĩ kiêm đạo diễn người Na Uy Jesper Borgen giới thiệu. Nó được sáng tác bởi Jesper Borgen, Walker, Marcus Arnbekk, Mood Melodies và Lars Kristian Rosness, với the latter four xử lí sản xuất, và lời bài hát được viết bởi tất cả nhà soạn nhạc như Tommy La Verdi và Gunnar Greve. Bài hát được phát hành thông qua Mer Musikk vào 15 tháng 9 năm 2017.",
                categories.get(0),
                LocalDate.of(2017, 9, 15), 100000, publishers.get(15), 5,
                new ArrayList<>(Arrays.asList("Unknown")),
                new ArrayList<>(Arrays.asList(people.get(8))),
                LocalTime.of(0, 4, 30));
        for(MusicInfo s : m){
            adapter.insertMusicInfo(s);
        }



    }

}
