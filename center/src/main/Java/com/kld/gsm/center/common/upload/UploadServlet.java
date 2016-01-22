package com.kld.gsm.center.common.upload;

import com.mangofactory.swagger.annotations.ApiIgnore;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2016/1/5 16:10
 * @Description:
 */
@Controller
@ApiIgnore
@RequestMapping("/web/upload")
public class UploadServlet extends HttpServlet {
    //定义临时文件盒上传文件的存储路径
    private File uploadTemp = null;
    private File uploadPath = null;

    @RequestMapping("/doGetupload")
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //禁用缓存,index.jsp后台会使用XmlHttpRequest调用本Servlet的doGet方法，从session中获取最新的上传数据情况
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragrma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("text/html;charset=utf-8");
        UploadStatus status = (UploadStatus) request.getSession(true)
                .getAttribute("uploadStatus");

        if (status == null) {
            response.getWriter().println("没有上传信息");
            return;
        }
        long startTime = status.getStartTime();
        long currentTime = System.currentTimeMillis();

        // 已传输的时间 单位：s
        long time = (currentTime - startTime) / 1000 + 1;

        // 传输速度 单位：byte/s
        double velocity = ((double) status.getBytesRead()) / (double) time;

        // 估计总时间 单位：s
        double totalTime = status.getContentLength() / velocity;

        // 估计剩余时间 单位：s
        double timeLeft = totalTime - time;

        // 已完成的百分比
        int percent = (int) (100 * (double) status.getBytesRead() / (double) status
                .getContentLength());

        // 已完成数 单位：M
        double length = ((double) status.getBytesRead()) / 1024 / 1024;

        // 总长度 单位：M
        double totalLength = ((double) status.getContentLength()) / 1024 / 1024;

        // 格式：百分比||已完成数(M)||文件总长度(M)||传输速率(K)||已用时间(s)||估计总时间(s)||估计剩余时间(s)||正在上传第几个文件
        String value = percent + "||" + length + "||" + totalLength + "||"
                + velocity + "||" + time + "||" + totalTime + "||" + timeLeft
                + "||" + status.getItems();

        response.getWriter().println(value);
    }
//    //region 多文件上传带进度条
//    @RequestMapping("/doPostupload")
//    public void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        init();
//        File file = null;
//        String description = null;
//
//        //设置响应格式（不设置请求格式，因为文件是二进制的，不能使用UTF-8格式化请求数据）
//        response.setContentType("text/html;charset=utf-8");
//
//        PrintWriter out = response.getWriter();
//        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
//        out.println("<HTML>");
//        out.println("<HEAD><TITLE>文件上传</TITLE></HEAD>");
//        out.println("<BODY style=‘margin:50px‘>");
//        out.println("上传日志：<BR/>");
//
//        UploadStatus status = new UploadStatus();
//        UploadListener listener = new UploadListener(status);
//        /*
//         * 把 UploadStatus 放到 session 里,引用
//		 * 返回与此请求关联的当前HttpSession，如果没有当前会话和创造是真实的，返回一个新的会话。
//		 * 如果创建是假的，并要求有没有有效的HttpSession，这个方法返回null。
//         */
//        request.getSession(true).setAttribute("uploadStatus", status);
//
//        //创建基于磁盘的工厂，针对大文件，临时文件将存储在磁盘
//        DiskFileItemFactory factory = new DiskFileItemFactory();
//        //设置缓冲区大小,超出该文件直接写入到磁盘的大小设置门槛。
//        factory.setSizeThreshold(10240);  //这里默认10KB
//        //设置用于大于配置的大小阈值设置的临时存储文件目录。
//        factory.setRepository(uploadTemp);
//        //创建一个文件上传的句柄
//        ServletFileUpload upload = new ServletFileUpload(factory);
////        DiskFileUpload upload = new DiskFileUpload ();
//        //设置最大文件尺寸 ，这里是40MB
//        upload.setSizeMax(41943040);
//        upload.setHeaderEncoding("utf-8");
//
//        // 设置 listener
//        upload.setProgressListener(listener);
//        try {
//            //将解析结果放在LIST中
//            List<FileItem> list = upload.parseRequest(request);
//            out.println("遍历所有的 FileItem ... <br/>");
//            // 遍历 list 中所有的 FileItem
//            for (FileItem item : list) {
//                // 如果是 文本域
//                if (item.isFormField()) {
//                    if (item.getFieldName().equals("description1") || item.getFieldName().equals("description2")) {
//
//                        description = item.getString("UTF-8");
//                        System.out.println("文件名： " + item.getFieldName() + " ----描述-------" + description);
//                    }
//                } else {
//                    //否则为文件域,当getName为Null说明没有选则文件
//                    if ((item.getFieldName().equals("file1") || item.getFieldName().equals("file2"))
//                            && item.getName() != null && !item.getName().equals("")) {
//                        try {
//                            // 统一 Linux 与 windows 的路径分隔符
//                            String fileName = item.getName();
//                            //fileName = fileName.substring(fileName.lastIndexOf("\\"));
//
//                            // 服务器端文件，放在 upload 文件夹下
//                            file = new File(uploadPath, fileName);
//                            if (!file.getParentFile().exists())
//                                file.getParentFile().mkdirs();
//                            if (!file.exists())
//                                file.createNewFile();
//
//                            item.write(file);
//
//                            System.out.println("遍历到 " + fileName + " ... <br/>" + description + "<BR/>");
//                        } catch (Exception e) {
//                            System.out.println("Request 上传失败！" + e.getMessage());
//                        } finally //总是立即删除保存表单字段内容的临时文件
//                        {
//                            item.delete();
//                        }
//                    }
//                }
//            }
//            System.out.println("Request 解析完毕,文件上传完毕！");
//        } catch (Exception e) {
//            System.out.println("Request 解析异常！" + e.getMessage());
//        }
//        out.flush();
//        out.close();
//    }
//    //endregion

    /**
     * Initialization of the servlet. <br>
     *
     * @throws ServletException if an error occurs
     */
    public void init() throws ServletException {
        // Put your code here
//        uploadPath = new File(this.getServletContext().getRealPath("upload"));
        uploadPath = new File("D:/upload");
        if (!uploadPath.exists())
            uploadPath.mkdirs();
//        uploadTemp = new File(this.getServletContext().getRealPath("upload/temp"));
        uploadTemp = new File("D:/upload/temp");
        if (!uploadTemp.exists())
            uploadTemp.mkdirs();
    }
    @RequestMapping("/doPostupload")
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile mFile = multipartRequest.getFile("file1");
        // 得到上传服务器的路径
            String path = request.getSession().getServletContext()
                    .getRealPath("/WEB-INF/upload/");
        // 得到上传的文件的文件名
        String filename = mFile.getOriginalFilename();
        byte[] b = mFile.getBytes();
        path += "\\" + filename;
        // 文件流写到服务器端
        FileOutputStream outputStream = new FileOutputStream(path);
        outputStream.write(b);
        outputStream.close();
    }
}
