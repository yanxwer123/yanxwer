package com.kld.app.view.main;

import com.kld.app.util.Common;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2015/12/10 17:55
 * @Description:
 */
public class StartApp  extends JDialog {
    private static JDialog frame;

    public JDialog getFrame() {
        return frame;
    }

    public void setFrame(JDialog frame) {
        this.frame = frame;
    }

    /**
     * Create the application.
     */
    public StartApp() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = this;
        frame.setResizable(false);
        frame.setBounds(480, 300, 700, 360);
        frame.setModal(true);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);

        ImageIcon logo = Common.createImageIcon(this.getClass(), "start.png");
        JLabel label = new JLabel(logo);
        frame.add(label);
    }
}
