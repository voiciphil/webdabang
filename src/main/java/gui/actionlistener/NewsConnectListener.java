package gui.actionlistener;

import gui.Gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewsConnectListener implements ActionListener {

    private final Gui gui;

    public NewsConnectListener(Gui gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        gui.openNews();
    }
}
