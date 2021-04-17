package crawler;

import static util.Util.sleep;

import java.io.IOException;
import java.util.Vector;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NaverBlogCrawler implements Crawler {

    private static NaverBlogCrawler instance;

    private static final String URL1 = "https://search.naver.com/search.naver?date_from=&date_option=0&date_to=&dup_remove=1&nso=&post_blogurl=&post_blogurl_without=&query=";
    private static final String URL2 = "&sm=tab_pge&srchby=all&st=sim&where=post&start=";
    private static final String SELECTOR = ".sh_blog_top dl dt a";
    private static final int PAGES = 3;
    private static final int ELEMENTS_PER_PAGE = 10;

    private String searchKeyword;

    public static NaverBlogCrawler getInstance(String searchKeyword) {
        if (instance == null) {
            instance = new NaverBlogCrawler(searchKeyword);
            return instance;
        }

        instance.changeSearchKeyword(searchKeyword);
        return instance;
    }

    private NaverBlogCrawler(String searchKeyword) {
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
                    result.add(Website.of(element.text(), element.attr("href")));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
