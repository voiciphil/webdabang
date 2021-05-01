package crawler;

import static util.Util.sleep;

import java.io.IOException;
import java.util.Vector;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public abstract class Crawler {

    public Vector<Website> run() {
        Vector<Website> result = new Vector<>();
        try {
            for (int page = 1; page <= getMaxPage(); page += getPageUnit()) {
                sleep(500);
                Document document = Jsoup.connect(getUrl(page)).get();
                Elements elements = document.select(getSelector());
                elements.forEach(element -> result
                    .add(Website.of(getDocumentTitle(element), getDocumentUrl(element))));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    abstract int getMaxPage();

    abstract int getPageUnit();

    abstract String getUrl(int page);

    abstract String getSelector();

    abstract String getDocumentTitle(Element element);

    abstract String getDocumentUrl(Element element);
}
