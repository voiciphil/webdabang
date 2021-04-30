package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnterListener implements ActionListener {

    private final Gui gui;

    EnterListener(Gui gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        new SearchListener(gui).actionPerformed(event);
    }
}
