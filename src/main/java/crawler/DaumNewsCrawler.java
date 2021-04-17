package crawler;

import static util.Util.sleep;

import java.io.IOException;
import java.util.Vector;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DaumNewsCrawler {

    private static final String URL1 = "https://search.daum.net/search?w=news&nil_search=btn&DA=PGD&enc=utf8&cluster=y&cluster_page=1&q=";
    private static final String URL2 = "&p=";
    private static final String SELECTOR = "#clusterResultUL .wrap_tit.mg_tit a";
    private static final int PAGES = 3;

    private final String searchKeyword;

    public DaumNewsCrawler(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public void run(Vector vec1, Vector vec2) {
        try {
            Document doc;
            Elements elem;
            for (int page = 1; page <= PAGES; page++) {
                sleep(500);
                doc = Jsoup.connect(URL1 + searchKeyword + URL2 + page).get();
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
