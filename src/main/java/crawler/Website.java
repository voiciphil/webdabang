package crawler;

import java.awt.Desktop;
import java.net.URI;

public class Website {

    private final String title;
    private final String link;

    public static Website of(String title, String link) {
        return new Website(title, link);
    }

    private Website(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public void open() {
        try {
            Desktop.getDesktop().browse(new URI(link));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return title;
    }
}
