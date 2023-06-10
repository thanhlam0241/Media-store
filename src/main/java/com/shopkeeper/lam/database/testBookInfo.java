package com.shopkeeper.lam.database;

import com.shopkeeper.lam.models.BookInfo;
import com.shopkeeper.mediaone.database.DatabaseAdapter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class testBookInfo {
    public void insert() throws Exception {

        var adapter = DatabaseAdapter.getDbAdapter();
        var categories = adapter.getAllCategories();
        var people = adapter.getAllPeople();
        var publishers = adapter.getAllPublishers();
        BookInfo[] b = new BookInfo[20];
        b[0] = new BookInfo("Cuộc sống không giới hạn", "CÂU CHUYỆN DIỆU KỲ CỦA CHÀNG TRAI ĐẶC BIỆT NHẤT HÀNH TINH Nick sinh ra mắc hội chứng Tetra-amelia bẩm sinh, một rối loạn gen hiếm gặp gây ra sự thiếu hụt chân, tay. Điều đó đồng nghĩa với việc anh có rất ít hy vọng để sống một cuộc đời bình thường. ... ",
                categories.get(34), LocalDate.of(2007, 8, 31), 60000, publishers.get(14), 5,
                new ArrayList<>(Arrays.asList("Không biết")),
                new ArrayList<>(Arrays.asList(people.get(20))),
                120);
        b[1] = new BookInfo("Đừng Bao Giờ Từ Bỏ Khát Vọng", "“Đừng bao giờ từ bỏ khát vọng” là quyển sách thứ hai của Nick Vujicic. Nguồn cảm hứng để anh viết cuốn sách này đến từ những người thuộc mọi lứa tuổi trên khắp thế giới, những người anh đã cho lời khuyên và hướng dẫn họ đương đầu với những thách thức trong cuộc sống.",
                categories.get(34), LocalDate.of(2012, 10, 2), 50000, publishers.get(14), 5,
                new ArrayList<>(Arrays.asList("Không biết")),
                new ArrayList<>(Arrays.asList(people.get(20))),
                98);
        b[2] = new BookInfo("Sống Cho Điều Ý Nghĩa Hơn", "Sau hai cuốn tự truyện gây sốt cho độc giả: Cuộc Sống Không Giới Hạn và Đừng Bao Giờ Từ Bỏ Khát Vọng, một lần nữa độc giả Việt Nam lại được hội ngộ cùng Nick Vujicic thông qua tác phẩm Limitless – Sống Cho Điều Ý Nghĩa Hơn. Tựa sách được đặt từ ý nghĩa của bài hát nổi tiếng nhất của Nick Something More, do Công ty Văn hóa Sáng tạo Trí Việt – First News ấn hành vào ngày 16/05/2013 nhân sự kiện chàng trai đặc biệt nhất hành tinh Nick Vujicic sang thăm Việt Nam và gặp gỡ, trò chuyện cùng mọi người.",
                categories.get(34), LocalDate.of(2013, 5, 16), 500000, publishers.get(14), 5,
                new ArrayList<>(Arrays.asList("Không biết")),
                new ArrayList<>(Arrays.asList(people.get(20))),
                80);
        b[3] = new BookInfo("Cái Ôm Diệu Kỳ", "Với Cái Ôm Diệu Kỳ, một cuốn sách dành cho trẻ em, lứa tuổi bắt đầu nhận thức và tiếp thu các vấn đề trong cuộc sống, thông qua câu chuyện của Nick, chúng tôi hy vọng đây sẽ là nguồn cảm hứng bất tận giúp các em biết yêu thương những người xung quanh, biết đứng dậy sau những vấp ngã và vượt qua những giới hạn của bản thân để trở thành người có ích và sống cho những điều ý nghĩa hơn, giống như tên của một cuốn sách được xuất bản trước đây của Nick.",
                categories.get(34), LocalDate.of(2013, 5, 7), 390000, publishers.get(14), 5,
                new ArrayList<>(Arrays.asList("Không biết")),
                new ArrayList<>(Arrays.asList(people.get(20))),
                76);
        b[4] = new BookInfo("Đứng Dậy Mạnh Mẽ", "Nếu bạn bị bắt nạt, bạn sẽ thấy nó gây tổn thương cho bạn. Quả là khó chịu đựng khi cảm thấy tình trạng bị bắt nạt dường như như sẽ không bao giờ chịu chấm dứt. Là người thỉnh thoảng phải chịu đựng sự bắt nạt suốt những năm mới lớn cho đến khi trưởng thành, tôi muốn mang đến cho bạn niềm hy vọng và cảm giác bình yên. Bạn có thể vươn lên, vượt qua chuyện đó.Với cuốn sách này, tác giả hy vọng “khi gấp cuốn sách lại bạn đọc có thể cảm thấy tự tin hơn, mạnh mẽ hơn, được chuẩn bị tốt hơn để đương đầu không chỉ với sự bắt nạt mà những thách thức khác trong cuộc sống.",
                categories.get(34), LocalDate.of(2007, 8, 31), 100000, publishers.get(14), 5,
                new ArrayList<>(Arrays.asList("Không biết")),
                new ArrayList<>(Arrays.asList(people.get(20))),
                150);
        b[5] = new BookInfo("Nhật ký trong tù", "Nhật ký trong tù là tập thơ chữ Hán gồm 134 bài theo thể Đường luật do Hồ Chí Minh sáng tác trong thời gian bị chính quyền Tưởng Giới Thạch bắt giam ở Quảng Tây, Trung Quốc, từ ngày 29 tháng 8 năm 1942 đến ngày 10 tháng 9 năm 1943.",
                categories.get(35), LocalDate.of(1943, 10, 19), 100000, publishers.get(14), 5,
                new ArrayList<>(Arrays.asList("Không biết")),
                new ArrayList<>(Arrays.asList(people.get(19))),
                163);
        b[6] = new BookInfo("Bản án chế độ thực dân Pháp", "Bản án chế độ thực dân Pháp (tiếng Pháp: Le Procès de la colonisation française) là một tác phẩm chính luận do Nguyễn Ái Quốc viết bằng tiếng Pháp và được xuất bản lần đầu ở Paris năm 1925 trên một tờ báo của Quốc tế Cộng sản có tên Imprékor, tại Việt Nam được xuất bản lần đầu năm 1946.",
                categories.get(35), LocalDate.of(1946, 10, 19), 100000, publishers.get(14), 5,
                new ArrayList<>(Arrays.asList("Không biết")),
                new ArrayList<>(Arrays.asList(people.get(19))),
                133);
        b[7] = new BookInfo("Con đường phía trước (The Road Ahead)", "Bill Gates - Con Đường Phía Trước là cuốn sách độc đáo và nổi tiếng do chính tay Bill Gates viết. Cuốn sách đã lập kỷ lục được bình chọn là cuốn sách bán chạy nhất nhiều tháng liền khi phát hành tại Mỹ, là cuốn sách không thể thiếu cho bất cứ ai quan tâm đến công nghệ thông tin.",
                categories.get(34), LocalDate.of(1995, 11, 24), 100000, publishers.get(15), 5,
                new ArrayList<>(Arrays.asList("Không biết")),
                new ArrayList<>(Arrays.asList(people.get(9))),
                163);
        b[8] = new BookInfo("Tốc Độ Tư Duy (Business @ the Speed of Thought)", "",
                categories.get(34), LocalDate.of(1999, 10, 19), 100000, publishers.get(17), 5,
                new ArrayList<>(Arrays.asList("Không biết")),
                new ArrayList<>(Arrays.asList(people.get(9))),
                220);
        b[9] = new BookInfo("How to Prevent the Next Pandemic", "How to Prevent the Next Pandemic is a 2022 book by Bill Gates. In it, Gates details the COVID-19 pandemic and how to prevent another pandemic, including proposing a \"Global Epidemic Response and Mobilization\" team with annual funding of $1 billion. ",
                categories.get(34), LocalDate.of(2022, 5, 19), 150000, publishers.get(16), 5,
                new ArrayList<>(Arrays.asList("Không biết")),
                new ArrayList<>(Arrays.asList(people.get(9))),
                173);
        b[10] = new BookInfo("Bill Gates Quotes: Bill Gates, Quotes, Quotations, Famous", "\" The Best Bill Gates Quotation Book ever Published. Special Edition This book of Bill Gates quotes contains only the rarest and most valuable quotations ever recorded about Bill Gates, authored by a team of experienced researchers",
                categories.get(34), LocalDate.of(2016, 6, 4), 120000, publishers.get(14), 5,
                new ArrayList<>(Arrays.asList("Không biết")),
                new ArrayList<>(Arrays.asList(people.get(9))),
                169);
        b[11] = new BookInfo("How to Avoid a Climate Disaster", "How to Avoid a Climate Disaster: The Solutions We Have and the Breakthroughs We Need is a 2021 book by Bill Gates. In it, Gates presents what he learned in over a decade of studying climate change and investing in innovations to address global warming and recommends strategies to tackle it.",
                categories.get(34), LocalDate.of(2021, 2, 16), 130000, publishers.get(11), 5,
                new ArrayList<>(Arrays.asList("Không biết")),
                new ArrayList<>(Arrays.asList(people.get(9))),
                180);
        b[12] = new BookInfo("The Art of Avatar", "The Art of Avatar is a film production art book released on November 30, 2009, by Abrams Books.",
                categories.get(29), LocalDate.of(2009, 11, 30), 200000, publishers.get(8), 5,
                new ArrayList<>(Arrays.asList("Không biết")),
                new ArrayList<>(Arrays.asList(people.get(3))),
                300);
        b[13] = new BookInfo("Battleship Bismarck: A Design and Operational History", "“A complete operational history of the Bismarck . . . with period photos [and] underwater photography of the wreck, allowing a forensic analysis of the damage.”",
                categories.get(27), LocalDate.of(2019, 11, 30), 200000, publishers.get(17), 5,
                new ArrayList<>(Arrays.asList("Không biết")),
                new ArrayList<>(Arrays.asList(people.get(3))),
                300);
        b[14] = new BookInfo("Ghosts of the Titanic", "Using the latest technology to penetrate the \"Titanic's\" watery grave, the author of \"Her Name, Titanic\" recreates those last, horrifying moments on board the doomed ship and uncovers fascinating secrets about ocean life.",
                categories.get(29), LocalDate.of(2000, 1, 1), 50000, publishers.get(11), 5,
                new ArrayList<>(Arrays.asList("Không biết")),
                new ArrayList<>(Arrays.asList(people.get(3))),
                200);
        b[15] = new BookInfo("Dr. Horrible", "Revisit the smash-hit web musical Dr. Horrible's Sing-Along Blog with the stories that started it all by Zack Whedon and a brand-new story by Joss Whedon set after the events of the web series! ",
                categories.get(30), LocalDate.of(2019, 6, 25), 200000, publishers.get(10), 5,
                new ArrayList<>(Arrays.asList("Không biết")),
                new ArrayList<>(Arrays.asList(people.get(10))),
                300);
        b[16] = new BookInfo("Astonishing X-Men - Volume 1: Gifted", "Dream-team creators Joss Whedon and John Cassaday present a brand-new era for the X-Men! Cyclops and Emma Frost re-form the X-Men with the express purpose of \"astonishing\" the world.",
                categories.get(30), LocalDate.of(2004, 1, 1), 50000, publishers.get(12), 5,
                new ArrayList<>(Arrays.asList("Không biết")),
                new ArrayList<>(Arrays.asList(people.get(10))),
                100);
        b[17] = new BookInfo("Astonishing X-Men: Torn. Vol. 3", "Emma Frost's erratic behavior has the X-Men spinning in a non-stop downward spiral. Will an unlikely union be the final straw? After secretly lying in wait for months, the new Hellfire Club makes its move! Plus: The X-Man destined to destroy the Breakworld stands revealed! Who is it, and what will be their fate?",
                categories.get(30), LocalDate.of(2007, 2, 14), 100000, publishers.get(14), 5,
                new ArrayList<>(Arrays.asList("Không biết")),
                new ArrayList<>(Arrays.asList(people.get(10))),
                100);
        b[18] = new BookInfo("Chạm tới giấc mơ", "TTO - Chạm tới giấc mơ là tên tự truyện đầu tiên của ca sĩ Sơn Tùng M-TP, chính thức phát hành vào ngày 30-9. Sơn Tùng cho hay anh đã ấp ủ viết tự truyện này từ rất lâu với mục đích truyền cảm hứng đến giới trẻ, khuyến khích các bạn dám nghĩ, dám làm để theo đuổi ước mơ của mình.",
                categories.get(35), LocalDate.of(2017, 9, 30), 80000, publishers.get(4), 5,
                new ArrayList<>(Arrays.asList("Không biết")),
                new ArrayList<>(Arrays.asList(people.get(4))),
                120);
        b[19] = new BookInfo("Đắc nhân tâm", "Đắc nhân tâm, tên tiếng Anh là How to Win Friends and Influence People là một quyển sách nhằm tự giúp bản thân bán chạy nhất từ trước đến nay. Quyển sách này do Dale Carnegie viết và đã được xuất bản lần đầu vào năm 1936, nó đã được bán 15 triệu bản trên khắp thế giới.",
                categories.get(34), LocalDate.of(1936, 10, 19), 70000, publishers.get(6), 5,
                new ArrayList<>(Arrays.asList("Không biết")),
                new ArrayList<>(Arrays.asList(people.get(21))),
                291);
        for(BookInfo x : b){
            adapter.insertBookInfo(x);
        }

    }
    public void update() throws Exception{
        var adapter = DatabaseAdapter.getDbAdapter();
        var categories = adapter.getAllCategories();
        var people = adapter.getAllPeople();
        var publishers = adapter.getAllPublishers();
        var musics = adapter.getAllBookInfos();
        for(var x : adapter.getAllBookInfos()){
            System.out.println(x);
        }
        var m2 = musics.get(1);
        m2.setTitle("Ve ben anh");
        m2.setDescription("Mang bau ke em");
        m2.setCategory(categories.get(2));
        m2.setReleaseDate(LocalDate.of(2021, 6,1));
        m2.setCurrentSalePrice(2000);
        m2.setPublisher(publishers.get(1));
        m2.setRating(2);
        m2.setAward(new ArrayList<>(Arrays.asList("Mâm xôi gấc", "Hello kitty")));
        m2.setAuthors(new ArrayList<>(Arrays.asList(people.get(4), people.get(1), people.get(2))));
        m2.setNumberOfPage(69);
        adapter.updateBookInfo(m2);
        System.out.println("----------<><><><><>----------");
        for(var x : adapter.getAllBookInfos()){
            System.out.println(x);
        }
    }
    public void delete() throws Exception {
        var adapter = DatabaseAdapter.getDbAdapter();
        for(var x:adapter.getAllBookInfos()){
            //Nếu như các ô đã triển khai override thuộc tính toString() cho
            // của ô rồi thì viết thế này
            System.out.println(x);
            //Còn nếu không các ô phải in từng thuộc tính 1 ra
        }
        var y = adapter.getAllBookInfos().get(1);
        adapter.deleteBookInfo(y);
        System.out.println("----------<><><><><>----------");
        for(var x:adapter.getAllBookInfos()){
            //Nếu như các ô đã triển khai override thuộc tính toString() cho
            // của ô rồi thì viết thế này
            System.out.println(x);
            //Còn nếu không các ô phải in từng thuộc tính 1 ra
        }
    }
}
