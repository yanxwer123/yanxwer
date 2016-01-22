package com.kld.app.view.main;

import com.kld.app.util.GifComponent;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 *
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2015/12/10 17:55
 * @Description:
 */
public class ZCTXFrame  extends JDialog {
    private static JDialog frame;
    public JLabel ypLabel;
    public void setsInfo(String sInfo){ypLabel.setText(sInfo);}

    public JDialog getFrame() {
        return frame;
    }

    public void setFrame(JDialog frame) {
        this.frame = frame;
    }

    /**
     * Create the application.
     */
    public ZCTXFrame() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = this;
        frame.setResizable(false);
        frame.setTitle("信息提示");
        frame.setSize(400, 200);
        frame.setModal(true);
        frame.getContentPane().setLayout(null);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        JPanel panel = new JPanel();
        panel.setBackground(UIManager.getColor("Button.light"));
        panel.setBounds(0, 0, 400, 200);
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        try {
            String strFile=this.getClass().getResource("/image/loading.gif").getFile();
            System.out.println(strFile);
            File file = new File(strFile);
            GifComponent gif = new GifComponent(file, 5);
            gif.setBounds(20, 68, 32, 32);
            panel.add(gif);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        ypLabel = new JLabel("请稍后...",JLabel.LEFT);
        ypLabel.setVerticalAlignment(JLabel.CENTER);
        ypLabel.setBounds(80, 0, 400, 170);
        ypLabel.setFont(new Font("宋体", Font.BOLD, 16));
        panel.add(ypLabel);
    }
}
