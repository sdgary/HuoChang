package byxx.android.exception;

import java.io.File;

/**
 * Created by wgary on 15/11/25.
 */
public interface CrashListener {
    /**
     * 发送日志文件。
     * @param file
     */
    public void sendFile(File file);

    /**
     * 退出APP。
     * @param thread
     * @param ex
     */
    public void closeApp(Thread thread, Throwable ex);
}
