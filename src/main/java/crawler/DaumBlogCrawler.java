package crawler;

import static util.Util.sleep;

import java.io.IOException;
import java.util.Vector;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DaumBlogCrawler implements Crawler {

    private static DaumBlogCrawler instance;

    private static final String URL1 = "https://search.daum.net/search?w=blog&nil_search=btn&DA=PGD&enc=utf8&q=";
    private static final String URL2 = "&page=";
    private static final String URL3 = "&m=board";
    private static final String SELECTOR = "#blogColl .wrap_tit.mg_tit a";
    private static final int PAGES = 3;

    private String searchKeyword;

    static public DaumBlogCrawler getInstance(String searchKeyword) {
        if (instance == null) {
            instance = new DaumBlogCrawler(searchKeyword);
            return instance;
        }

        instance.changeSearchKeyword(searchKeyword);
        return instance;
    }

    private DaumBlogCrawler(String searchKeyword) {
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
                Document document = Jsoup.connect(URL1 + searchKeyword + URL2 + page + URL3).get();
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
