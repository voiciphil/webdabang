package crawler;

import static util.Util.sleep;

import java.io.IOException;
import java.util.Vector;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NaverNewsCrawler {

    private static final String URL1 = "https://search.naver.com/search.naver?&where=news&query=";
    private static final String URL2 = "&sm=tab_pge&sort=0&photo=0&field=0&reporter_article=&pd=0&ds=&de=&docid=&nso=so:r,p:all,a:all&mynews=0&cluster_rank=24&start=";
    private static final String URL3 = "&refresh_start=0";
    private static final int PAGES = 3;
    private static final int ELEMENTS_PER_PAGE = 10;

    private final String searchKeyword;

    public NaverNewsCrawler(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public void run(Vector vec1, Vector vec2) {
        try {
            Document doc;
            Elements elem;
            for (int page = 1; page <= PAGES * ELEMENTS_PER_PAGE; page += ELEMENTS_PER_PAGE) {
                sleep(500);
                doc = Jsoup.connect(URL1 + searchKeyword + URL2 + page + URL3).get();
                elem = doc.select(".type01 dl dt a");
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
