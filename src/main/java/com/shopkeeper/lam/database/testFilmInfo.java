package com.shopkeeper.lam.database;

import com.shopkeeper.lam.models.FilmInfo;
import com.shopkeeper.mediaone.database.DatabaseAdapter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class testFilmInfo {
    public void insert() throws Exception {

        var adapter = DatabaseAdapter.getDbAdapter();
        var categories = adapter.getAllCategories();
        var people = adapter.getAllPeople();
        var publishers = adapter.getAllPublishers();
        FilmInfo f1 = new FilmInfo("Thánh bài", "Thánh bài nhại theo phim Thần bài (1989), một trong những phim về thể loại cờ bạc nổi tiếng của đạo diễn Vương Tinh. Trong phim điện ảnh Hồng Kông này, Châu Tinh Trì thủ vai chính và vươn lên vị trí ngôi sao hàng đầu của phim hài Hồng Kông cũng như định hình phong cách diễn của mình từ đó về sau.",
                categories.get(13),
                LocalDate.of(1989, 1, 1), 500000, publishers.get(22), 5,
                new ArrayList<>(Arrays.asList("Không biết")),
                people.get(26),
                new ArrayList<>(Arrays.asList(people.get(16), people.get(23))),
                LocalTime.of(2, 3, 50));
        FilmInfo f2 = new FilmInfo("Titanic", "Titanic là một bộ phim điện ảnh lãng mạn thảm họa sử thi của Mỹ phát hành năm 1997, do James Cameron làm đạo diễn, viết kịch bản, đồng sản xuất, đồng biên tập và hỗ trợ tài chính một phần.", categories.get(16),
                LocalDate.of(1911, 11, 1), 100000, publishers.get(23), 5,
                new ArrayList<>(Arrays.asList("Quả cầu vàng", "giải Broadcast Film Critics Association", "Producer Guild of America")),
                people.get(3),
                new ArrayList<>(Arrays.asList(people.get(1), people.get(2))),
                LocalTime.of(2, 4, 30));
        FilmInfo f3 = new FilmInfo("Tuyệt đỉnh Kung-fu","Tuyệt đỉnh Kungfu là một bộ phim hài hước - hành động - võ thuật Hồng Kông, được công chiếu vào năm 2004. Phim được sản xuất kiêm đạo diễn và diễn viên chính bởi Châu Tinh Trì. Phim là một sự nhai lại khôi hài và là một lòng tôn kính tới thể loại tiểu thuyết võ hiệp Kim Dung.",
                categories.get(13),LocalDate.of(1941, 1, 12),
                150000,publishers.get(22),5,
                new ArrayList<>(Arrays.asList("Không biết")),
                people.get(26),
                new ArrayList<>(Arrays.asList(people.get(16))),
                LocalTime.of(2, 4, 30));
        FilmInfo f4 = new FilmInfo("Ký sinh trùng","Ký sinh trùng là một bộ phim điện ảnh hài kịch đen của Hàn Quốc ra mắt năm 2019 do Bong Joon-ho làm đạo diễn, đồng sản xuất kiêm viết kịch bản, với sự tham gia của dàn diễn viên gồm Song Kang-ho, Lee Sun-kyun, Cho Yeo-jeong, Choi Woo-sik, Park So-dam, Lee Jung-eun, Park Myung-hoon và Jang Hye-jin.",
                categories.get(15),LocalDate.of(1911, 11, 1),
                50000,publishers.get(22),5,
                new ArrayList<>(Arrays.asList("Best Film Not In The English Language","Giải Oscar")),
                people.get(0),
                new ArrayList<>(Arrays.asList(people.get(25), people.get(24))),
                LocalTime.of(2, 4, 30));
        FilmInfo f5 = new FilmInfo("Người Nhện 2 (Spider-Man™ 2)","Người Nhện 2 là phim điện ảnh siêu anh hùng của Mỹ năm 2004 do Sam Raimi đạo diễn và Alvin Sargent viết kịch bản từ đầu truyện của Alfred Gough, Miles Millar và Michael Chabon. ",
                categories.get(18),LocalDate.of(2004, 6, 30),
                150000,publishers.get(22),5,
                new ArrayList<>(Arrays.asList("Không biết")),
                people.get(16),
                new ArrayList<>(Arrays.asList(people.get(27))),
                LocalTime.of(2, 4, 30));
        FilmInfo f6 = new FilmInfo("Đội bóng thiếu lâm","Đội bóng Thiếu Lâm là một bộ phim hài - hành động - võ thuật xen lẫn với chính kịch về đề tài thể thao của Hồng Kông ra mắt năm 2001 do Châu Tinh Trì làm đạo diễn kiêm đồng viết kịch bản, Dương Quốc Huy sản xuất với phần chỉ đạo võ thuật của Trình Tiểu Đông.",
                categories.get(13),LocalDate.of(1910, 11, 1),
                50000,publishers.get(22),5,
                new ArrayList<>(Arrays.asList("Không biết")),
                people.get(26),
                new ArrayList<>(Arrays.asList(people.get(16), people.get(23))),
                LocalTime.of(2, 4, 30));
        FilmInfo f7 = new FilmInfo("Người Nhện 3 (Spider-Man™ 3)","Người Nhện 3 là một bộ phim siêu anh hùng của Mỹ năm 2007 dựa trên nhân vật Người Nhện của Marvel Comics. Nó được đạo diễn bởi Sam Raimi từ một kịch bản của Raimi, anh trai của anh ấy là Ivan và Alvin Sargent. Đây là phần cuối cùng trong bộ ba Spider-Man của Raimi, và là phần tiếp theo của Người Nhện và Người Nhện 2.",
                categories.get(18),LocalDate.of(1911, 11, 1),
                150000,publishers.get(22),5,
                new ArrayList<>(Arrays.asList("Không biết")),
                people.get(15),
                new ArrayList<>(Arrays.asList(people.get(27))),
                LocalTime.of(2, 4, 30));
        FilmInfo f8 = new FilmInfo("Mỹ nhân ngư","Mỹ nhân ngư là phim điện ảnh thuộc thể loại hài chính kịch xen lẫn với hình sự - phiêu lưu - kỳ ảo của Hồng Kông - Trung Quốc ra mắt năm 2016 do Châu Tinh Trì làm đạo diễn, viết kịch bản và đồng sản xuất, với sự góp mặt của các diễn viên chính gồm Lâm Doãn, Đặng Siêu, Trương Vũ Kỳ và La Chí Tường.",
                categories.get(13),LocalDate.of(1911, 11, 1),
                50000,publishers.get(22),5,
                new ArrayList<>(Arrays.asList("Không biết")),
                people.get(26),
                new ArrayList<>(Arrays.asList(people.get(16), people.get(23))),
                LocalTime.of(2, 4, 30));
        FilmInfo f9 = new FilmInfo("Người Sắt (Iron Man)","Người Sắt là một bộ phim điện ảnh đề tài siêu anh hùng của Mỹ năm 2008 dựa trên nhân vật truyện tranh cùng tên của Marvel Comics. Phim do hãng Marvel Studios sản xuất và Paramount Pictures chịu trách nhiệm phân phối, đồng thời là bộ phim đầu tiên trong loạt phim điện ảnh thuộc Vũ trụ Điện ảnh",
                categories.get(18),LocalDate.of(2008, 5, 16),
                50000,publishers.get(22),5,
                new ArrayList<>(Arrays.asList("Không biết")),
                people.get(28),
                new ArrayList<>(Arrays.asList(people.get(11))),
                LocalTime.of(2, 4, 30));
        FilmInfo f10 = new FilmInfo("Người sắt 2 (Iron Man 2","Người Sắt 2 là một bộ phim siêu anh hùng do Mỹ sản xuất năm 2010 dựa trên nhân vật Người Sắt trong Marvel Comics. Bộ phim do Marvel Studios sản xuất và được hãng Paramount Pictures phát hành, là phần tiếp theo của bộ phim Người Sắt năm 2008 và là bộ phim thứ ba trong Vũ trụ Điện ảnh Marvel.",
                categories.get(18),LocalDate.of(2010, 5, 7),
                50000,publishers.get(22),5,
                new ArrayList<>(Arrays.asList("Không biết")),
                people.get(28),
                new ArrayList<>(Arrays.asList(people.get(11))),
                LocalTime.of(2, 4, 30));
        FilmInfo f11 = new FilmInfo("Spider-Man","Người Nhện là một bộ phim siêu anh hùng năm 2002 của Mỹ, là phim đầu tiên trong loạt phim Người Nhện dựa trên các nhân vật hư cấu Người Nhện của Marvel Comics. Phim được đạo diễn bởi Sam Raimi và kịch bản viết bởi David Koepp.",
                categories.get(18),LocalDate.of(2002, 3, 5),
                100000,publishers.get(22),5,
                new ArrayList<>(Arrays.asList("Không biết")),
                people.get(15),
                new ArrayList<>(Arrays.asList(people.get(27))),
                LocalTime.of(2, 4, 30));
        FilmInfo f12 = new FilmInfo("Avengers: Infinity War","The Avengers must stop Thanos, an intergalactic warlord, from getting his hands on all the infinity stones. However, Thanos is prepared to go to any lengths to carry out his insane plan",
                categories.get(18),LocalDate.of(2018, 4, 23),
                150000,publishers.get(22),5,
                new ArrayList<>(Arrays.asList("Oscar")),
                people.get(10),
                new ArrayList<>(Arrays.asList(people.get(11), people.get(12),people.get(13),people.get(14))),
                LocalTime.of(2, 4, 30));
        FilmInfo f13 = new FilmInfo("Rô-bốt đại chiến ","Robot đại chiến là phim điện ảnh hành động khoa học viễn tưởng của Mỹ năm 2007 dựa trên dòng đồ chơi cùng tên của Hasbro",
                categories.get(18),LocalDate.of(2007, 6, 24),
                50000,publishers.get(22),5,
                new ArrayList<>(Arrays.asList("Không biết")),
                people.get(5),
                new ArrayList<>(Arrays.asList(people.get(6), people.get(7))),
                LocalTime.of(2, 10, 30));
        FilmInfo f14 = new FilmInfo("Transformers: Revenge of the Fallen","Sam leaves the Autobots to lead a normal life. However, the Decepticons target him and drag him back into the Transformers' war.",categories.get(18),LocalDate.of(1911, 11, 1),
                50000,publishers.get(22),5,
                new ArrayList<>(Arrays.asList("Không biết")),
                people.get(5),
                new ArrayList<>(Arrays.asList(people.get(6), people.get(7))),
                LocalTime.of(2, 4, 30));
        FilmInfo f15 = new FilmInfo("Transformers: Dark of the Moon","Sam Witwicky and the Autobots must unravel the secrets of a Cybertronian spacecraft hidden on the Moon before the Decepticons can use it for their own evil schemes.",
                categories.get(18),LocalDate.of(2011, 6, 29),
                50000,publishers.get(24),5,
                new ArrayList<>(Arrays.asList("Unknown")),
                people.get(5),
                new ArrayList<>(Arrays.asList(people.get(6), people.get(7))),
                LocalTime.of(2, 4, 30));
        FilmInfo f16 = new FilmInfo("Transformers: Age of Extinction","The Autobots, a faction of robots from the planet Cybertron, are hunted down by an elite CIA black ops unit and a ruthless bounty hunter. They turn to a struggling inventor and his daughter for help.",
                categories.get(18),LocalDate.of(2014 , 6, 27),
                50000,publishers.get(22),5,
                new ArrayList<>(Arrays.asList("Unknown")),
                people.get(5),
                new ArrayList<>(Arrays.asList(people.get(6), people.get(7))),
                LocalTime.of(2, 4, 30));
        FilmInfo f17 = new FilmInfo("Transformers: The Last Knight","Quintessa brainwashes Optimus Prime and heads to Earth to search for an ancient staff. Cade, Bumblebee and the Autobots race against time to find it, while also escaping an anti-Transformers force.",
                categories.get(16),LocalDate.of(2017 , 6, 20),
                50000,publishers.get(22),5,
                new ArrayList<>(Arrays.asList("Unknown")),
                people.get(5),
                new ArrayList<>(Arrays.asList(people.get(6), people.get(7))),
                LocalTime.of(2, 4, 30));
        FilmInfo f18 = new FilmInfo("Biệt đội siêu anh hùng (The Avengers)","Biệt đội siêu anh hùng là một phim siêu anh hùng của Hoa Kỳ được xây dựng dựa trên nguyên mẫu các thành viên của biệt đội siêu anh hùng Avengers của Marvel Comics, sản xuất bởi Marvel Studios và phân phối bởi Walt Disney Studios Motion Pictures. Đây là bộ phim thứ 6 trong Vũ trụ Điện ảnh Marvel.",
                categories.get(18),LocalDate.of(2012, 4, 27),
                150000,publishers.get(22),5,
                new ArrayList<>(Arrays.asList("Oscar")),
                people.get(10),
                new ArrayList<>(Arrays.asList(people.get(11), people.get(12),people.get(13),people.get(14))),
                LocalTime.of(2, 4, 30));
        FilmInfo f19 = new FilmInfo("Avengers: Đế chế Ultron","Avengers: Đế chế Ultron là một phim của điện ảnh Hoa Kỳ được xây dựng dựa trên nguyên mẫu các thành viên trong biệt đội siêu anh hùng Avengers của hãng Marvel Comics, sản xuất bởi Marvel Studios và phân phối bởi Walt Disney Studios Motion Pictures.",
                categories.get(18),LocalDate.of(2015, 4, 24),
                150000,publishers.get(22),5,
                new ArrayList<>(Arrays.asList("Oscar")),
                people.get(10),
                new ArrayList<>(Arrays.asList(people.get(11), people.get(12),people.get(13),people.get(14))),
                LocalTime.of(2, 4, 30));
        FilmInfo f20 = new FilmInfo("Avengers: Endgame","After Thanos, an intergalactic warlord, disintegrates half of the universe, the Avengers must reunite and assemble again to reinvigorate their trounced allies and restore balance.",
                categories.get(18),LocalDate.of(2019, 4, 26),
                150000,publishers.get(22),5,
                new ArrayList<>(Arrays.asList("Oscar")),
                people.get(10),
                new ArrayList<>(Arrays.asList(people.get(11), people.get(12),people.get(13),people.get(14))),
                LocalTime.of(2, 4, 30));
        for(var x : adapter.getAllFilmInfos()){
            System.out.println(x);
        }
        adapter.insertFilmInfo(f1);
        adapter.insertFilmInfo(f2);
        adapter.insertFilmInfo(f3);
        adapter.insertFilmInfo(f4);
        adapter.insertFilmInfo(f5);
        adapter.insertFilmInfo(f6);
        adapter.insertFilmInfo(f7);
        adapter.insertFilmInfo(f8);
        adapter.insertFilmInfo(f9);
        adapter.insertFilmInfo(f10);
        adapter.insertFilmInfo(f11);
        adapter.insertFilmInfo(f12);
        adapter.insertFilmInfo(f13);
        adapter.insertFilmInfo(f14);
        adapter.insertFilmInfo(f15);
        adapter.insertFilmInfo(f16);
        adapter.insertFilmInfo(f17);
        adapter.insertFilmInfo(f18);
        adapter.insertFilmInfo(f19);
        adapter.insertFilmInfo(f20);

    }


}
