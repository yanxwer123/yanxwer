package com.kld.app.util;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;

public class Common {
    public static ImageIcon createImageIcon(Class cls, String filename) {
//    	  String path = "../../../../resources/image/" + filename;
        try {
            String path = "/image/" + filename;
            return new ImageIcon(cls.getResource(path));
        } catch (Exception e) {

            try {
                URL purl = cls.getClass().getResource("/");
                String p = new File(purl.getFile(), "../conf").getCanonicalPath();
                p = p + "/image/" + filename;
                return new ImageIcon(p);
            } catch (IOException ioe) {

            }
        }
        return null;
    }

    public static ImageIcon createImageIcon(Class cls, String filename, String path) {
        return new ImageIcon(cls.getResource(path + filename));
    }
}
