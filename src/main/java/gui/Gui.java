package gui;

import static gui.ComponentFactory.createButton;
import static gui.ComponentFactory.createComboBox;
import static gui.ComponentFactory.createList;
import static gui.ComponentFactory.createLogo;
import static gui.ComponentFactory.createSearchBar;

import crawler.Crawler;
import crawler.DaumBlogCrawler;
import crawler.DaumNewsCrawler;
import crawler.NaverBlogCrawler;
import crawler.NaverNewsCrawler;
import crawler.Website;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Gui {

    private static final String BLOG_BUTTON_ICON = "src/main/resources/blog.png";
    private static final String NEWS_BUTTON_ICON = "src/main/resources/news.png";
    private static final String SEARCH_BUTTON_ICON = "src/main/resources/search.png";
    private static final String LOGO_IMAGE = "src/main/resources/logo.png";

    private static final int SEARCH_BAR_INDEX = 3;
    private static final int BLOG_LIST_INDEX = 4;
    private static final int NEWS_LIST_INDEX = 5;
    private static final int COMBO_BOX_INDEX = 6;

    private List<JComponent> components;
    private Vector<Website> blogWebsites;
    private Vector<Website> newsWebsites;

    public void run() {
        components = createComponents();
        JPanel panel = createPanel();
        JFrame frame = createFrame(panel);
        frame.setVisible(true);
    }

    private List<JComponent> createComponents() {
        return List.of(
            createButton(new ImageIcon(BLOG_BUTTON_ICON), new ConnectListenerBlog(), new Rectangle(225, 715, 90, 57)),
            createButton(new ImageIcon(NEWS_BUTTON_ICON), new ConnectListenerNews(), new Rectangle(815, 715, 80, 57)),
            createButton(new ImageIcon(SEARCH_BUTTON_ICON), new SearchListener(), new Rectangle(480, 30, 82, 58)),
            createSearchBar(new EnterListener(), new Rectangle(120, 30, 350, 50)),
            createList(new Rectangle(20, 100, 520, 600)),
            createList(new Rectangle(600, 100, 520, 600)),
            createComboBox(new Rectangle(20, 30, 100, 50)),
            createLogo(new ImageIcon(LOGO_IMAGE), new Rectangle(600, 1, 520, 139))
        );
    }

    private JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 52, 74));
        panel.setLayout(null);
        components.forEach(panel::add);
        return panel;
    }

    private JFrame createFrame(JPanel panel) {
        JFrame frame = new JFrame();
        frame.setBackground(new Color(30, 52, 74));
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1140, 825);
        frame.setResizable(false);
        return frame;
    }

    private String getSearchKeyword() {
        JTextField searchBar = (JTextField) components.get(SEARCH_BAR_INDEX);
        return searchBar.getText();
    }

    private int getSelectedComboBoxIndex() {
        JComboBox<String> comboBox = (JComboBox<String>) components.get(COMBO_BOX_INDEX);
        return comboBox.getSelectedIndex();
    }

    private void setBlogData(Vector<Website> data) {
        JList<Website> blogSearchResults = (JList<Website>) components.get(BLOG_LIST_INDEX);
        blogSearchResults.setListData(data);
        blogWebsites = data;
    }

    private void setNewsData(Vector<Website> data) {
        JList<Website> newsSearchResults = (JList<Website>) components.get(NEWS_LIST_INDEX);
        newsSearchResults.setListData(data);
        newsWebsites = data;
    }

    private void openBlog() {
        JList<Website> blogSearchResults = (JList<Website>) components.get(BLOG_LIST_INDEX);
        int index = blogSearchResults.getSelectedIndex();
        blogWebsites.get(index).open();
    }

    private void openNews() {
        JList<Website> blogSearchResults = (JList<Website>) components.get(NEWS_LIST_INDEX);
        int index = blogSearchResults.getSelectedIndex();
        newsWebsites.get(index).open();
    }

    class SearchListener implements ActionListener {

        private static final int NAVER = 0;
        private static final int DAUM = 1;

        @Override
        public void actionPerformed(ActionEvent event) {
            String search = getSearchKeyword();
            int site = getSelectedComboBoxIndex();

            if (site == NAVER) {
                crawl(NaverBlogCrawler.getInstance(search), NaverNewsCrawler.getInstance(search));
            } else if (site == DAUM) {
                crawl(DaumBlogCrawler.getInstance(search), DaumNewsCrawler.getInstance(search));
            }
        }

        private void crawl(Crawler blogCrawler, Crawler newsCrawler) {
            setBlogData(blogCrawler.run());
            setNewsData(newsCrawler.run());
        }
    }

    class ConnectListenerBlog implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            openBlog();
        }
    }

    class ConnectListenerNews implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            openNews();
        }
    }

    class EnterListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            new SearchListener().actionPerformed(event);
        }
    }
}
