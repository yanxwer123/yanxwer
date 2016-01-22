package com.kld.gsm.center.common.upload;

import org.apache.commons.fileupload.ProgressListener;
/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2016/1/5 16:09
 * @Description:
 */

/**
 * 监听器
 */
public class UploadListener implements ProgressListener {
    private UploadStatus status;
    //构造方法
    public UploadListener(UploadStatus status) {
        super();
        this.status = status;
    }
    //重写方法
    @Override
    public void update(long bytesRead, long contentLength, int items) {
        status.setBytesRead(bytesRead);
        status.setContentLength(contentLength);
        status.setItems(items);
    }
}