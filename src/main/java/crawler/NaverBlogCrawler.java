package crawler;

import org.jsoup.nodes.Element;

public class NaverBlogCrawler extends Crawler {

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
        return element.text();
    }

    @Override
    String getDocumentUrl(Element element) {
        return element.attr("href");
    }
}
