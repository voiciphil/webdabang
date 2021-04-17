package crawler;

import static util.Util.sleep;

import java.io.IOException;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.util.*;

public class NaverBlogCrawler {

    private static final String URL1 = "https://search.naver.com/search.naver?date_from=&date_option=0&date_to=&dup_remove=1&nso=&post_blogurl=&post_blogurl_without=&query=";
    private static final String URL2 = "&sm=tab_pge&srchby=all&st=sim&where=post&start=";
    private static final int PAGES = 3;
    private static final int ELEMENTS_PER_PAGE = 10;

    private final String searchKeyword;

    public NaverBlogCrawler(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public void run(Vector vec1, Vector vec2) {
        try {
            Document doc;
            Elements elem;
            for (int page = 1; page <= PAGES * ELEMENTS_PER_PAGE; page += ELEMENTS_PER_PAGE) {
                sleep(500);
                doc = Jsoup.connect(URL1 + searchKeyword + URL2 + page).get();
                elem = doc.select(".sh_blog_top dl dt a");
                for (Element a : elem) {
                    vec1.addElement(a.text());
                    vec2.addElement(a.attr("href"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
