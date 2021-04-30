package gui;

import crawler.Website;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ComponentFactory {
    
    public static JButton createButton(ImageIcon icon, ActionListener actionListener, Rectangle bound) {
        JButton button = new JButton(icon);
        button.addActionListener(actionListener);
        button.setBorderPainted(false);
        button.setBounds(bound);
        return button;
    }

    public static JTextField createSearchBar(ActionListener actionListener, Rectangle bound) {
        JTextField searchBar = new JTextField(50);
        searchBar.addActionListener(actionListener);
        searchBar.setBackground(Color.IVORY);
        searchBar.setBounds(bound);
        return searchBar;
    }

    public static JList<Website> createList(Rectangle bound) {
        JList<Website> list = new JList<>();
        list.setBackground(Color.IVORY);
        list.setBounds(bound);
        return list;
    }

    public static JComboBox<String> createComboBox(Rectangle bound) {
        String[] menu = {"Naver", "Daum"};
        JComboBox<String> comboBox = new JComboBox<>(menu);
        comboBox.setBounds(bound);
        return comboBox;
    }

    public static JLabel createLogo(ImageIcon icon, Rectangle bound) {
        JLabel label = new JLabel(icon);
        label.setBounds(bound);
        return label;
    }

    public static JPanel createPanel(List<JComponent> components) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.INDIGO);
        panel.setLayout(null);
        components.forEach(panel::add);
        return panel;
    }

    public static JFrame createFrame(JPanel panel) {
        JFrame frame = new JFrame();
        frame.setBackground(Color.INDIGO);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1140, 825);
        frame.setResizable(false);
        return frame;
    }

}
