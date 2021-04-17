package crawler;

import static util.Util.sleep;

import java.io.IOException;
import java.util.Vector;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DaumNewsCrawler implements Crawler {

    private static DaumNewsCrawler instance;

    private static final String URL1 = "https://search.daum.net/search?w=news&nil_search=btn&DA=PGD&enc=utf8&cluster=y&cluster_page=1&q=";
    private static final String URL2 = "&p=";
    private static final String SELECTOR = "#clusterResultUL .wrap_tit.mg_tit a";
    private static final int PAGES = 3;

    private String searchKeyword;

    public static DaumNewsCrawler getInstance(String searchKeyword) {
        if (instance == null) {
            instance = new DaumNewsCrawler(searchKeyword);
            return instance;
        }

        instance.changeSearchKeyword(searchKeyword);
        return instance;
    }

    private DaumNewsCrawler(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    private void changeSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    @Override
    public Vector<Website> run() {
        Vector<Website> result = new Vector<>();
        try {
            for (int page = 1; page <= PAGES; page++) {
                sleep(500);
                Document document = Jsoup.connect(URL1 + searchKeyword + URL2 + page).get();
                Elements elements = document.select(SELECTOR);
                for (Element element : elements) {
                    result.add(Website.of(element.text(), element.attr("href")));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
