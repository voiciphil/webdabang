package crawler;

import static util.Util.sleep;

import java.io.IOException;
import java.util.Vector;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NaverNewsCrawler implements Crawler {

    private static NaverNewsCrawler instance;

    private static final String URL1 = "https://search.naver.com/search.naver?where=news&sm=tab_pge&query=";
    private static final String URL2 = "&sort=0&photo=0&field=0&pd=0&ds=&de=&cluster_rank=24&mynews=0&office_type=0&office_section_code=0&news_office_checked=&nso=so:r,p:all,a:all&start=";
    private static final String SELECTOR = ".news_tit";
    private static final int PAGES = 3;
    private static final int ELEMENTS_PER_PAGE = 10;

    private String searchKeyword;

    public static NaverNewsCrawler getInstance(String searchKeyword) {
        if (instance == null) {
            instance = new NaverNewsCrawler(searchKeyword);
            return instance;
        }

        instance.changeSearchKeyword(searchKeyword);
        return instance;
    }

    private NaverNewsCrawler(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    private void changeSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    @Override
    public Vector<Website> run() {
        Vector<Website> result = new Vector<>();
        try {
            for (int page = 1; page <= PAGES * ELEMENTS_PER_PAGE; page += ELEMENTS_PER_PAGE) {
                sleep(500);
                Document document = Jsoup.connect(URL1 + searchKeyword + URL2 + page).get();
                Elements elements = document.select(SELECTOR);
                for (Element element : elements) {
                    result.add(Website.of(element.attr("title"), element.attr("href")));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
