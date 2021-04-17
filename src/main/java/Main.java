import crawler.DaumBlogCrawler;
import crawler.DaumNewsCrawler;
import crawler.NaverBlogCrawler;
import crawler.NaverNewsCrawler;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main {

    JFrame frame;
    JPanel panel;
    JTextField text_box;
    JList searchListNews, searchListBlog;
    JButton searchButton, linkButtonBlog, linkButtonNews;
    JLabel logo;
    JComboBox combo;
    Vector vTitle_blog, vTitle_news;
    Vector vLink_blog, vLink_news;
    String[] menu = {"Naver", "Daum"};

    public void run() {
        frame = new JFrame("project");

        panel = new JPanel();

        linkButtonBlog = new JButton(new ImageIcon("src/main/resources/blog.png"));
        linkButtonBlog.addActionListener(new ConnectListenerBlog());
        linkButtonBlog.setBorderPainted(false);

        linkButtonNews = new JButton(new ImageIcon("src/main/resources/news.png"));
        linkButtonNews.addActionListener(new ConnectListenerNews());
        linkButtonNews.setBorderPainted(false);

        searchButton = new JButton(new ImageIcon("src/main/resources/search.png"));
        searchButton.addActionListener(new SearchListener());
        searchButton.setBorderPainted(false);

        text_box = new JTextField(50);
        text_box.addActionListener(new EnterListener());
        text_box.setBackground(new Color(255, 255, 233));

        searchListBlog = new JList();
        searchListBlog.setBackground(new Color(255, 255, 233));

        searchListNews = new JList();
        searchListNews.setBackground(new Color(255, 255, 233));

        combo = new JComboBox<String>(menu);

        logo = new JLabel(new ImageIcon("src/main/resources/logo.png"));

        panel.setBackground(new Color(30, 52, 74));
        panel.setLayout(null);
        panel.add(linkButtonBlog);
        panel.add(linkButtonNews);
        panel.add(searchButton);
        panel.add(text_box);
        panel.add(searchListBlog);
        panel.add(searchListNews);
        panel.add(combo);
        panel.add(logo);

        text_box.setBounds(120, 30, 350, 50);
        searchButton.setBounds(480, 30, 82, 58);
        linkButtonBlog.setBounds(225, 715, 90, 57);
        linkButtonNews.setBounds(815, 715, 80, 57);
        searchListBlog.setBounds(20, 100, 520, 600);
        searchListNews.setBounds(600, 100, 520, 600);
        combo.setBounds(20, 30, 100, 50);
        logo.setBounds(600, 1, 520, 139);

        frame.setBackground(new Color(30, 52, 74));
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1140, 825);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Main().run();
    }

    class SearchListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            String search = text_box.getText();
            int site = combo.getSelectedIndex();

            vLink_blog = new Vector();
            vTitle_blog = new Vector();
            vLink_news = new Vector();
            vTitle_news = new Vector();

            if (site == 0) {
                NaverBlogCrawler bc = new NaverBlogCrawler(search);
                bc.run(vTitle_blog, vLink_blog);
                searchListBlog.setListData(vTitle_blog);

                NaverNewsCrawler nc = new NaverNewsCrawler(search);
                nc.run(vTitle_news, vLink_news);
                searchListNews.setListData(vTitle_news);
            } else if (site == 1) {
                DaumBlogCrawler bc = new DaumBlogCrawler(search);
                bc.run(vTitle_blog, vLink_blog);
                searchListBlog.setListData(vTitle_blog);

                DaumNewsCrawler nc = new DaumNewsCrawler(search);
                nc.run(vTitle_news, vLink_news);
                searchListNews.setListData(vTitle_news);
            }
        }
    }

    class ConnectListenerBlog implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            int index = searchListBlog.getSelectedIndex();
            String uri = vLink_blog.get(index).toString();
            try {
                Desktop.getDesktop().browse(new URI(uri));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class ConnectListenerNews implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            int index = searchListNews.getSelectedIndex();
            String uri = vLink_news.get(index).toString();
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
