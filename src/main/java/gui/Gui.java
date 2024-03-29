package gui;

import static gui.ComponentFactory.createButton;
import static gui.ComponentFactory.createComboBox;
import static gui.ComponentFactory.createFrame;
import static gui.ComponentFactory.createList;
import static gui.ComponentFactory.createLogo;
import static gui.ComponentFactory.createPanel;
import static gui.ComponentFactory.createSearchBar;

import crawler.Website;
import gui.actionlistener.BlogConnectListener;
import gui.actionlistener.EnterListener;
import gui.actionlistener.NewsConnectListener;
import gui.actionlistener.SearchListener;
import java.awt.Rectangle;
import java.util.List;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Gui {

    private static final int SEARCH_BAR_INDEX = 3;
    private static final int BLOG_LIST_INDEX = 4;
    private static final int NEWS_LIST_INDEX = 5;
    private static final int COMBO_BOX_INDEX = 6;

    private List<JComponent> components;
    private Vector<Website> blogWebsites;
    private Vector<Website> newsWebsites;

    public void run() {
        components = createComponents();
        JPanel panel = createPanel(components);
        JFrame frame = createFrame(panel);
        frame.setVisible(true);
    }

    private List<JComponent> createComponents() {
        return List.of(
            createButton(Icon.BLOG_BUTTON, new BlogConnectListener(this), new Rectangle(225, 715, 90, 57)),
            createButton(Icon.NEWS_BUTTON, new NewsConnectListener(this), new Rectangle(815, 715, 80, 57)),
            createButton(Icon.SEARCH_BUTTON, new SearchListener(this), new Rectangle(480, 30, 82, 58)),
            createSearchBar(new EnterListener(this), new Rectangle(120, 30, 350, 50)),
            createList(new Rectangle(20, 100, 520, 600)),
            createList(new Rectangle(600, 100, 520, 600)),
            createComboBox(new Rectangle(20, 30, 100, 50)),
            createLogo(Icon.LOGO, new Rectangle(600, 1, 520, 139))
        );
    }

    public String getSearchKeyword() {
        JTextField searchBar = (JTextField) components.get(SEARCH_BAR_INDEX);
        return searchBar.getText();
    }

    public int getSelectedComboBoxIndex() {
        JComboBox<String> comboBox = (JComboBox<String>) components.get(COMBO_BOX_INDEX);
        return comboBox.getSelectedIndex();
    }

    public void setBlogData(Vector<Website> data) {
        JList<Website> blogSearchResults = (JList<Website>) components.get(BLOG_LIST_INDEX);
        blogSearchResults.setListData(data);
        blogWebsites = data;
    }

    public void setNewsData(Vector<Website> data) {
        JList<Website> newsSearchResults = (JList<Website>) components.get(NEWS_LIST_INDEX);
        newsSearchResults.setListData(data);
        newsWebsites = data;
    }

    public void openBlog() {
        JList<Website> blogSearchResults = (JList<Website>) components.get(BLOG_LIST_INDEX);
        int index = blogSearchResults.getSelectedIndex();
        blogWebsites.get(index).open();
    }

    public void openNews() {
        JList<Website> blogSearchResults = (JList<Website>) components.get(NEWS_LIST_INDEX);
        int index = blogSearchResults.getSelectedIndex();
        newsWebsites.get(index).open();
    }
}
