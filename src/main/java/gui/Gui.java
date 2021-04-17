package gui;

import crawler.Crawler;
import crawler.DaumBlogCrawler;
import crawler.DaumNewsCrawler;
import crawler.NaverBlogCrawler;
import crawler.NaverNewsCrawler;
import crawler.Website;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.List;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Gui {

    private static final String BLOG_BUTTON_ICON = "src/main/resources/blog.png";
    private static final String NEWS_BUTTON_ICON = "src/main/resources/news.png";
    private static final String SEARCH_BUTTON_ICON = "src/main/resources/search.png";
    private static final String LOGO_IMAGE = "src/main/resources/logo.png";

    private List<JComponent> components;
    private Vector<Website> blogWebsites;
    private Vector<Website> newsWebsites;

    public void run() {
        createComponents();
        JPanel panel = createPanel();
        JFrame frame = createFrame(panel);
        frame.setVisible(true);
    }

    private void createComponents() {
        components = List.of(
            createButton(new ImageIcon(BLOG_BUTTON_ICON), new ConnectListenerBlog(), new Rectangle(225, 715, 90, 57)),
            createButton(new ImageIcon(NEWS_BUTTON_ICON), new ConnectListenerNews(), new Rectangle(815, 715, 80, 57)),
            createButton(new ImageIcon(SEARCH_BUTTON_ICON), new SearchListener(), new Rectangle(480, 30, 82, 58)),
            createSearchBar(new Rectangle(120, 30, 350, 50)),
            createList(new Rectangle(20, 100, 520, 600)),
            createList(new Rectangle(600, 100, 520, 600)),
            createComboBox(new Rectangle(20, 30, 100, 50)),
            createLogo(new Rectangle(600, 1, 520, 139))
        );
    }

    private JButton createButton(ImageIcon icon, ActionListener actionListener, Rectangle bound) {
        JButton button = new JButton(icon);
        button.addActionListener(actionListener);
        button.setBorderPainted(false);
        button.setBounds(bound);
        return button;
    }

    private JTextField createSearchBar(Rectangle bound) {
        JTextField searchBar = new JTextField(50);
        searchBar.addActionListener(new EnterListener());
        searchBar.setBackground(new Color(255, 255, 233));
        searchBar.setBounds(bound);
        return searchBar;
    }

    private JList<Website> createList(Rectangle bound) {
        JList<Website> list = new JList<>();
        list.setBackground(new Color(255, 255, 233));
        list.setBounds(bound);
        return list;
    }

    private JComboBox<String> createComboBox(Rectangle bound) {
        String[] menu = {"Naver", "Daum"};
        JComboBox<String> comboBox = new JComboBox<>(menu);
        comboBox.setBounds(bound);
        return comboBox;
    }

    private JLabel createLogo(Rectangle bound) {
        JLabel label = new JLabel(new ImageIcon(LOGO_IMAGE));
        label.setBounds(bound);
        return label;
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

    class SearchListener implements ActionListener {

        private static final int NAVER = 0;
        private static final int DAUM = 1;

        @Override
        public void actionPerformed(ActionEvent event) {
            JTextField searchBar = (JTextField) components.get(3);
            JComboBox<String> comboBox = (JComboBox<String>) components.get(6);

            String search = searchBar.getText();
            int site = comboBox.getSelectedIndex();

            if (site == NAVER) {
                crawl(NaverBlogCrawler.getInstance(search), NaverNewsCrawler.getInstance(search));
            } else if (site == DAUM) {
                crawl(DaumBlogCrawler.getInstance(search), DaumNewsCrawler.getInstance(search));
            }
        }

        private void crawl(Crawler blogCrawler, Crawler newsCrawler) {
            JList<Website> blogSearchResults = (JList<Website>) components.get(4);
            blogWebsites = blogCrawler.run();
            blogSearchResults.setListData(blogWebsites);

            JList<Website> newsSearchResults = (JList<Website>) components.get(5);
            newsWebsites = newsCrawler.run();
            newsSearchResults.setListData(newsWebsites);
        }
    }

    class ConnectListenerBlog implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            JList<Website> blogSearchResults = (JList<Website>) components.get(4);
            int index = blogSearchResults.getSelectedIndex();
            String uri = blogWebsites.get(index).toString();
            try {
                Desktop.getDesktop().browse(new URI(uri));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class ConnectListenerNews implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            JList<Website> newsSearchResults = (JList<Website>) components.get(5);
            int index = newsSearchResults.getSelectedIndex();
            String uri = newsWebsites.get(index).toString();
            try {
                Desktop.getDesktop().browse(new URI(uri));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class EnterListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            new SearchListener().actionPerformed(event);
        }
    }
}
