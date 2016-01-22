package com.kld.app.view.main;

import com.kld.app.socket.client.TcpClient;
import com.kld.app.socket.ob.ConcreteWatched;
import com.kld.app.socket.ob.Watched;
import com.kld.app.socket.ob.Watcher;
import com.kld.app.util.Common;
import com.kld.app.util.Constant;
import com.kld.app.view.acceptance.CkdcxPage;
import com.kld.app.view.acceptance.JhysPage;
import com.kld.app.view.alarm.OilExcep;
import com.kld.gsm.ATG.domain.CurUser;
import com.kld.gsm.Socket.Constants;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.util.JsonMapper;
import com.kld.gsm.util.SybaseUtils;
import io.netty.channel.Channel;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.junit.Test;
import org.slf4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

  public class Main extends JFrame implements Watcher {
    public static Map USERMAP = new HashMap<String, String>();
    private static JFrame frame;
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(Main.class);


    // 全局的位置变量，用于表示鼠标在窗口上的位置,用于拖动
    static Point origin = new Point();

    //标题栏panel
    private JPanel titlePanel = new JPanel();
    //上部panel
    public static JPanel topPanel = new JPanel();
    //中部panel
    public static JPanel centerPanel = new JPanel();
    //底部panel
    public static JPanel bottomPanel = new JPanel();

    public static JLabel statusLabel = new JLabel();

    public static String statusString = "        ";

    public static JhysPage jhys;

    public static OilExcep oilExcep;

    public static CkdcxPage ckdcxPage;
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
    //public static Channel CC = SocketContext.getInstance();
    public static Channel CC ;
      //油罐 - 实时标准体积库存
    public  static Map oilCanRealTime = new HashMap();


    public static Watched watch = ConcreteWatched.getInstance();
    public static String sign = "";
    public static String IP = Constant.IP;
    public static int Port =  Constant.PORT;
    static StartApp zc = new StartApp();
     //操作员号
     public static String oprno;
     //操作员名
     public static String oprname;
     //操作菜单
     public static List menuList = new ArrayList();
     public static void main(String[] args) {
        Thread thread =  new Thread() {
            @Override
             public void run() {
              zc.setVisible(true);
             }
         };
        thread.start();
        try {
            //设置本属性将改变窗口边框样式定义
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            //TODO exception
        }
        //region 自动生成signid
        // 创建 GUID 对象
        UUID uuid = UUID.randomUUID();
        // 得到对象产生的ID
        sign = uuid.toString();
        System.out.println("IP-----[" + IP + "]");
        System.out.println("PORT:-----[" + Port + "]");
         Main.watch.addWetcher("A", new Main());

         CC = reLink();
        //endregion
         // new Main().frame.setVisible(true);
        AsynHeart.sendIdel();
    }


    /**
     * Create the application.
     */
    public Main() {
      /* //初始化主JFRAME
        initialize();
        //初始化标题栏
        initTitlePanel();
        //初始化上部
        initTopPanel();
        //初始化底部

        //initBottomPanel();

        //初始化中间部位
        initCenterPanel();*/
        //获取权限
//		sendMessage();
    }


    private void initCenterPanel() {
//		centerPanel.setLayout(new BorderLayout(0,0));
//		JScrollPane scrollPane = new JScrollPane();
//		centerPanel.add(scrollPane);
//		JTextArea textArea = new JTextArea(2000,3000); 
//		scrollPane.setViewportView(textArea);
//		scrollPane.setVisible(true);
    }

    private void initTopPanel() {
        topPanel.setLayout(null);
        topPanel.add(new TablePanel(centerPanel).tabbedpane);
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        frame.add(titlePanel);
        titlePanel.setBounds(0, 0, 800, 60);
        titlePanel.setBackground(new Color(Integer.decode(Constant.TITLE_COCLER)));
        frame.add(topPanel);
        topPanel.setBounds(0, 60, 800, 120);
        topPanel.setBackground(new Color(Integer.decode(Constant.TAB_TOP_BG)));
        frame.add(centerPanel);
        centerPanel.setBounds(0, 180, 800, 390);
        centerPanel.setBackground(new Color(Integer.decode(Constant.CENTER_BG_COCLER)));

        statusLabel.setBounds(0, 570, 800, 30);
        frame.add(bottomPanel);
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBounds(0, 570, 800, 30);
        bottomPanel.add(statusLabel, BorderLayout.EAST);

        bottomPanel.setBackground(new Color(Integer.decode(Constant.BOTTOM_COCLER)));

        //实现标题栏拖动整体的JFrame
        titlePanel.addMouseListener(new MouseAdapter() {
            // 按下（mousePressed 不是点击，而是鼠标被按下没有抬起）
            public void mousePressed(MouseEvent e) {
                // 当鼠标按下的时候获得窗口当前的位置
                origin.x = e.getX();
                origin.y = e.getY();
            }
        });
        titlePanel.addMouseMotionListener(new MouseMotionAdapter() {
            // 拖动（mouseDragged 指的不是鼠标在窗口中移动，而是用鼠标拖动）
            public void mouseDragged(MouseEvent e) {
                // 当鼠标拖动时获取窗口当前位置
                Point p = frame.getLocation();
                // 设置窗口的位置
                // 窗口当前的位置 + 鼠标当前在窗口的位置 - 鼠标按下的时候在窗口的位置
                frame.setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
            }
        });
        //设置LOGO
        try {
            frame.setIconImage(Common.createImageIcon(this.getClass(), "icon.png").getImage());
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void initTitlePanel() {
        titlePanel.setLayout(null);
//		titlePanel.setBackground(new Color(Integer.decode(Constant.TITLE_COCLER)));

        //设置logo
        ImageIcon logo = Common.createImageIcon(this.getClass(), "sinopec-logo.png");
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(logo);
        logoLabel.setBounds(15, 0, 50, 60);
        titlePanel.add(logoLabel);

        //title名称
        JLabel title = new JLabel("中国石化");
        title.setFont(Constant.TITLE_FONT);
        title.setBounds(60, 19, 88, 22);
        title.setForeground(new Color(Integer.decode(Constant.TOP_TITLE_COCLER)));
        titlePanel.add(title);


        //title名称后部
        JLabel title_back = new JLabel("-加油站液位仪应用系统");
        title_back.setFont(Constant.TITLE_BACK_FONT);
        title_back.setBounds(148, 21, 300, 18);
        title_back.setForeground(new Color(Integer.decode(Constant.TOP_TITLE_COCLER)));
        titlePanel.add(title_back);

        //图标
        ImageIcon nav_red = Common.createImageIcon(this.getClass(), "nav_test.png");
        JButton Icon;

        //日期
        Date rq = new Date();
        JLabel date = new JLabel(sdf.format(rq) + " " + dateFm.format(rq));
        date.setFont(Constant.TITLE_CONTENT_FONT);
        date.setBounds(450, 24, 300, 12);
        date.setForeground(new Color(Integer.decode(Constant.FORE_COCLER_1)));
        titlePanel.add(date);


        //图标
        Icon = new JButton();
        Icon.setBorderPainted(false);
        Icon.setContentAreaFilled(false);
        Icon.setIcon(nav_red);
        Icon.setBounds(580, 20, 20, 20);
        titlePanel.add(Icon);

        //当前用户

        JLabel user = new JLabel("用户：" + oprname);
         if (USERMAP != null && USERMAP.size() > 0) {
            user.setText(USERMAP.get("oprname").toString());
        }
        user.setFont(Constant.TITLE_CONTENT_FONT);
        user.setBounds(600, 24, 200, 12);
        user.setForeground(new Color(Integer.decode(Constant.FORE_COCLER_1)));
        titlePanel.add(user);

/*
        //图标
        Icon = new JButton();
        Icon.setBorderPainted(false);
        Icon.setContentAreaFilled(false);
        Icon.setIcon(nav_red);
        Icon.setBounds(698, 20, 20, 20);
        titlePanel.add(Icon);*/

        //最小化
        JLabel minLabel = new JLabel("最小化");
        minLabel.setFont(Constant.TITLE_CONTENT_FONT);
        minLabel.setBounds(695, 24, 40, 12);
        minLabel.setForeground(new Color(Integer.decode(Constant.FORE_COCLER_1)));
        titlePanel.add(minLabel);
        minLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.setExtendedState(frame.ICONIFIED);
            }
        });
        //图标
        Icon = new JButton();
        Icon.setBorderPainted(false);
        Icon.setContentAreaFilled(false);
        Icon.setIcon(nav_red);
        Icon.setBounds(730, 20, 20, 20);
        titlePanel.add(Icon);
        //退出z
        JLabel closeLabel = new JLabel("退出");
        closeLabel.setFont(Constant.TITLE_CONTENT_FONT);
        closeLabel.setBounds(750, 24, 40, 12);
        closeLabel.setForeground(new Color(Integer.decode(Constant.FORE_COCLER_1)));
        titlePanel.add(closeLabel);
        closeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void update(GasMsg gasMsg) {
        if(gasMsg.getPid().equals(Constants.PID_Code.A15_10000.toString())){
            System.out.println("return Heart" );
        }

        if (gasMsg.getPid().equals(Constants.PID_Code.A15_10001.toString())) {
            System.out.println("【Main】收到操作员信息" + gasMsg);
            //USERMAP = (Map) data.getData().get(0);
            CurUser curUser = new JsonMapper().fromJson(gasMsg.getMessage(), CurUser.class);
            System.out.println("Ctrl Return" + curUser);
            if ("0".equals(curUser.getResult())) {
                System.out.println("登陆成功,加载操作权限");
                oprno = curUser.getOprno();
                oprname = SybaseUtils.getSybaseCNStr(curUser.getOprname().trim());
                menuList = curUser.getMenuList();
                System.out.println("========================菜单");
                for (int i = 0; i < curUser.getMenuList().size(); i++) {
                    System.out.println(curUser.getMenuList().get(i));
                }
                System.out.println("========================菜单");
                //初始化主JFRAME
                initialize();
                //初始化标题栏
                initTitlePanel();
                //初始化上部
                initTopPanel();
                //初始化底部

                //initBottomPanel();
                //初始化中间部位
                initCenterPanel();
                //endregion
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        zc.setVisible(false);
                    }
                });
                new Main().frame.setVisible(true);

                System.out.println("页面加载完成。。。。");
            } else {
                JOptionPane.showMessageDialog(null, "操作员未登录,请登录后操作", "信息提示", JOptionPane.INFORMATION_MESSAGE);
            }


        }

    }

    public void actionPerformed(ActionEvent e) {// 实现ActionListener接口的actionPerformed接口。
        JFrame frame = new JFrame("新窗口");//构造一个新的JFrame，作为新窗口。
        frame.setBounds(// 让新窗口与Swing7窗口示例错开50像素。
                new Rectangle(
                        (int) this.getBounds().getX() + 50,
                        (int) this.getBounds().getY() + 50,
                        (int) this.getBounds().getWidth(),
                        (int) this.getBounds().getHeight()
                )
        );
        JLabel jl = new JLabel();// 注意类名别写错了。
        frame.getContentPane().add(jl);
        jl.setText("这是新窗口");
        jl.setVerticalAlignment(JLabel.CENTER);
        jl.setHorizontalAlignment(JLabel.CENTER);// 注意方法名别写错了。
        frame.setVisible(true);
        /*SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                zc.setVisible(false);
            }
        });*/
    }


    //region socket----

     public synchronized static Channel reLink() {
        int i = 1;
        boolean flag = true;
        while (flag) {
            try {
                if (CC != null && (CC.isActive() || CC.isOpen())) {
                    System.out.println("这个链路通着");
                    return CC;
                }

                CC = TcpClient.getInstance().getChannel(Main.IP, Main.Port);
                if (CC != null) {
                    System.out.println("Main.reLink ok  ");
                    flag = false;
                    return CC;
                }
                System.out.println("Main.Wait five seconds reLink......");

                Thread.sleep(3000);
                 System.out.println("Main.ReLink[" + i + "]....");
                //如果未成功连接，则20秒左右弹出一次提示
                if (i == 8) {
                    JOptionPane.showMessageDialog(null, "与主调度未能成功建立连接,正在尝试重连...", "错误提示", JOptionPane.ERROR_MESSAGE);
                    //i = 0;
                    // System.exit(0);
                }
                //尝试60秒后，未能成功连接，系统退出
                if (i == 16) {
                    JOptionPane.showMessageDialog(null, "与主调度未能成功建立连接,请尝试重启...", "错误提示", JOptionPane.ERROR_MESSAGE);
                    //i = 0;
                    System.exit(0);
                }
            } catch (InterruptedException e) {
                logger.info("重连出错.......");
                JOptionPane.showMessageDialog(null, "与主调度未能成功建立连接,请尝试重启...", "错误提示", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            i++;

        }
        return null;
    }

    //endregion
    @Test
    public void getP() {
        Properties prop = new Properties();
        InputStream in = Object.class.getResourceAsStream("classpath*:important.properties");
        try {
            prop.load(in);
            String ctrlIp = prop.getProperty("ctrl.ip").trim();
            String ctrlPort = prop.getProperty("ctrl.port").trim();
            //System.out.println("IP:" + ctrlIp);
            //System.out.println("IP:" + ctrlPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setStatus(String name) {
        statusLabel.setText(statusString + name);
        bottomPanel.repaint();
    }


      public static void setCenter(JFrame frame)
      {
          Dimension   screensize   =   Toolkit.getDefaultToolkit().getScreenSize();   //获得当前屏幕的大小
          Dimension   framesize   =   frame.getSize();   //获得当前窗口的大小
          int   x   =   (int)screensize.getWidth()/2   -   (int)framesize.getWidth()/2;   //计算要显示的窗口的左上角的坐标
          int   y   =   (int)screensize.getHeight()/2   -   (int)framesize.getHeight()/2;
          frame.setLocation(x,y);
      }

      public static void setCenter(JDialog frame)
      {
          Dimension   screensize   =   Toolkit.getDefaultToolkit().getScreenSize();   //获得当前屏幕的大小
          Dimension   framesize   =   frame.getSize();   //获得当前窗口的大小
          int   x   =   (int)screensize.getWidth()/2   -   (int)framesize.getWidth()/2;   //计算要显示的窗口的左上角的坐标
          int   y   =   (int)screensize.getHeight()/2   -   (int)framesize.getHeight()/2;
          frame.setLocation(x,y);
      }

    public static void clearStatus() {
        statusLabel.setText("");
        bottomPanel.repaint();
    }

    /**
     * 取得frame当前坐标
     *
     * @return
     */
    public static Point getFrameLocation() {
        return frame.getLocation();
    }
}

