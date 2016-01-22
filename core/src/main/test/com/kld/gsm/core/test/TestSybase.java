package com.kld.gsm.core.test;

import java.sql.*;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;


/**
 * Created by miaozy on 15/10/31.
 */
public class TestSybase {
    public static void main(String [] args){
        DBConnectionPool.newConnection();
    }
}




  class DBConnectionPool {

    private static int checkedOut;

    private static Vector freeConnections = new Vector();

    private static int maxConn;

    private static String name; // Connection Pool Name.

    private static String DB_STATUS = "0";

    private static String DB_URL = "jdbc:mysql://10.238.193.223:3306/epsdb";

    private static String DB_USER = "bos";

    private static String DB_PASSWD = "bos";

    private static String DB_ENCODING = "latin1";

    /**
     * constructor
     *
     * @param name
     *            pool name
     * @param maxConn
     *            max connection
     */
    public DBConnectionPool(String name, int maxConn) {

        DBConnectionPool.name = name;
        DBConnectionPool.maxConn = maxConn;
    }

    public static boolean isConnect(Connection con) {

        try {
            Statement st = con.createStatement();
            String strStatus = "1";
            if (strStatus == null) {
                strStatus = DB_STATUS;
            }
            if ("0".equals(strStatus)) {
                st.execute("select 1 from dual");
            }
            st.close();
        } catch (Exception e) {
           // Log.error(e.toString());
            return false;
        }
        return true;
    }

    /**
     * free the connection
     *
     * @param con
     */
    public synchronized void freeConnection(Connection con) {

        freeConnections.addElement(con);
        checkedOut--;
        notifyAll();
    }

    /**
     * get the connection
     */
    public static Connection getConnection() {

        Connection con = null;
        if (freeConnections.size() > 0) {
            con = (Connection) freeConnections.firstElement();
            freeConnections.removeElementAt(0);
            if (!isConnect(con)) {
                con = getConnection();
            }
        } else if (maxConn == 0 || checkedOut < maxConn) {
            con = newConnection();
        }
        if (con != null) {
            checkedOut++;
        }
        return con;
    }

    /**
     * timeout get connection
     *
     * @param timeout
     */
    public synchronized Connection getConnection(long timeout) {

        long startTime = new Date().getTime();
        Connection con;
        while ((con = getConnection()) == null) {
            try {
                wait(timeout);
            } catch (InterruptedException e) {
            }
            if ((new Date().getTime() - startTime) >= timeout) {
                return null;
            }
        }
        return con;
    }

    /**
     *
     */
    public synchronized void release() {

        Enumeration allConnections = freeConnections.elements();
        while (allConnections.hasMoreElements()) {
            Connection con = (Connection) allConnections.nextElement();
            try {
                con.close();
            } catch (SQLException e) {
              //  Log.error(e.toString());
                e.printStackTrace();
            }
        }
        freeConnections.removeAllElements();
    }

    /**
     *
     */
    public static Connection newConnection() {

        Connection con = null;
        try {
            String strStatus = "1";
            if (strStatus == null) {
                strStatus = DB_STATUS;
            }
            String strURL = "jdbc:sybase:Tds:Candy:2638/gkdb;CHARSET=iso_1";
            if (strURL == null)
                strURL = DB_URL;
            String strUser = "dba";
            if (strUser == null)
                strUser = DB_USER;
            String strPwd = "sql";
            if (strPwd == null)
                strPwd = DB_PASSWD;

            if ("0".equals(strStatus)) {
                Class.forName("org.gjt.mm.mysql.Driver").newInstance();
            }
            else if ("1".equals(strStatus)) {
                Class.forName("com.sybase.jdbc4.jdbc.SybDriver").newInstance();
            }
            else {
                Class.forName("org.gjt.mm.mysql.Driver").newInstance();
            }
            String url = strURL + "?user=" + strUser + "&password=" + strPwd;
//			Class.forName("org.gjt.mm.mysql.Driver").newInstance();
//							+ "autoReconnect=true&useUnicode=true&characterEncoding=" + encodeing;
            //autoReconnect=true&useUnicode=true&characterEncoding=GBK
//							+ "&useUnicode=true&characterEncoding=" + encodeing;

            con = DriverManager.getConnection(url);


            Statement  s = con.createStatement();

            ResultSet rs = s.executeQuery("select *  from  menuinfor");

            while (rs.next()){
                 System.out.println(rs.getString(2));
            }

        } catch (Exception e) {
         //   Log.error(e.toString());
            e.printStackTrace();
        }

        return con;
    }
}
