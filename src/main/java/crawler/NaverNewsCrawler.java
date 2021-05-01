package crawler;

import org.jsoup.nodes.Element;

public class NaverNewsCrawler extends Crawler {

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
    int getMaxPage() {
        return PAGES * ELEMENTS_PER_PAGE;
    }

    @Override
    int getPageUnit() {
        return ELEMENTS_PER_PAGE;
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
        return element.attr("title");
    }

    @Override
    String getDocumentUrl(Element element) {
        return element.attr("href");
    }
}
