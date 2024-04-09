package CrawlWeb;

import database.JDBCUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class crawl1 {
    public void crawlStoriesByCategory(String categoryUrl) {
        try {
            // Kết nối đến trang web thể loại truyện
            Document doc = Jsoup.connect(categoryUrl).get();

            // Lấy tất cả các truyện trong thể loại
            Elements link = doc.select("div[class=\"main-content\"] [class=\"news-item\"] ");
            Elements linkk = doc.select("div[class=\"title\"]");
            String danhMuc = linkk.select("a").text();
            System.out.println("Danh Mục: " + danhMuc);
            for(Element link1 : link) {

                String tenTruyen = link1.select("a").attr("title");
                String dateView = link1.select("p[class=\"info-post\"]").text();
                String content = link1.select("p[class=\"sapo\"]").text();

                Elements link2 = link1.select("div[class=\"img\"]");
                for(Element link3 : link2){
                    String url = link3.select("a").attr("href");
                    String imgUrl = link3.select("img").attr("src");

                    //           System.out.println("Danh Mục: " + danhMuc);
                    System.out.println("Image: " + imgUrl);
                    System.out.println("Tên Truyện: " + tenTruyen);
                    System.out.println("URL: " + url);
                    System.out.println(dateView);
                    System.out.println("Mô tả:  " + content );

                    System.out.println("--------------------------------------------------------");
                }
            }
        }catch (Exception e){
            e.getMessage();
        }
    }
    private void insertDataIntoMySQL(String tenTruyen, String dateView, String content, String url, String imgUrl) {
        String sql = "INSERT INTO stories (tenTruyen, dateView, content, url, imgUrl) VALUES (?, ?, ?, ?, ?)";

        String jdbcURL = "jdbc:mysql://localhost:3306/stories";
        String username = "root";
        String password = "123456";

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, tenTruyen);
            preparedStatement.setString(2, dateView);
            preparedStatement.setString(3, content);
            preparedStatement.setString(4, url);
            preparedStatement.setString(5, imgUrl);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {

        crawl1 crawler = new crawl1();
        String category1Url = "https://cotich.net/co-tich-viet-nam.html";
        crawler.crawlStoriesByCategory(category1Url);

        String category2Url = "https://cotich.net/co-tich-ve-loai-vat.html";
        crawler.crawlStoriesByCategory(category2Url);

    }
}
