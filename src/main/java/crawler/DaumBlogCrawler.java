package crawler;

import org.jsoup.nodes.Element;

public class DaumBlogCrawler extends Crawler {

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
    int getMaxPage() {
        return PAGES;
    }

    @Override
    int getPageUnit() {
        return 1;
    }

    @Override
    String getUrl(int page) {
        return URL1 + searchKeyword + URL2 + page + URL3;
    }

    @Override
    String getSelector() {
        return SELECTOR;
    }

    @Override
    String getDocumentTitle(Element element) {
        return element.text();
    }

    @Override
    String getDocumentUrl(Element element) {
        return element.attr("href");
    }
}
