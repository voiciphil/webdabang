package crawler;

import static util.Util.sleep;

import java.io.IOException;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.util.*;

public class DaumBlogCrawler {

    private static final String URL1 = "https://search.daum.net/search?w=blog&nil_search=btn&DA=PGD&enc=utf8&q=";
    private static final String URL2 = "&page=";
    private static final String URL3 = "&m=board";
    private static final String SELECTOR = "#blogColl .wrap_tit.mg_tit a";
    private static final int PAGES = 3;

    private final String searchKeyword;

    public DaumBlogCrawler(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public void run(Vector vec1, Vector vec2) {
        try {
            Document doc;
            Elements elem;
            for (int page = 1; page <= PAGES; page++) {
                sleep(500);
                doc = Jsoup.connect(URL1 + searchKeyword + URL2 + page + URL3).get();
                elem = doc.select(SELECTOR);
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
