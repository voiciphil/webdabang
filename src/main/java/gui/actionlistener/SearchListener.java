package gui.actionlistener;

import crawler.Crawler;
import crawler.DaumBlogCrawler;
import crawler.DaumNewsCrawler;
import crawler.NaverBlogCrawler;
import crawler.NaverNewsCrawler;
import gui.Gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchListener implements ActionListener {

    private static final int NAVER = 0;
    private static final int DAUM = 1;

    private final Gui gui;

    public SearchListener(Gui gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String search = gui.getSearchKeyword();
        int site = gui.getSelectedComboBoxIndex();

        if (site == NAVER) {
            crawl(NaverBlogCrawler.getInstance(search), NaverNewsCrawler.getInstance(search));
        } else if (site == DAUM) {
            crawl(DaumBlogCrawler.getInstance(search), DaumNewsCrawler.getInstance(search));
        }
    }

    private void crawl(Crawler blogCrawler, Crawler newsCrawler) {
        gui.setBlogData(blogCrawler.run());
        gui.setNewsData(newsCrawler.run());
    }
}
