package crawler;

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

    @Override
    public String toString() {
        return title;
    }
}
