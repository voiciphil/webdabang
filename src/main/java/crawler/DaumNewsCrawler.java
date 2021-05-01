package crawler;

import org.jsoup.nodes.Element;

public class DaumNewsCrawler extends Crawler {

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
    int getMaxPage() {
        return PAGES;
    }

    @Override
    int getPageUnit() {
        return 1;
    }

    @Override
    String getUrl(int page) {
        return URL1 + searchKeyword + URL2 + page;
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
