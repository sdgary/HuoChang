package byxx.android.huochang.util;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
/**
 * Created by wgary on 15/11/24.
 */
public class LogWriter {
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS",
            Locale.getDefault());

    /**
     * 将日志写入文件。
     *
     * @param tag
     * @param message
     * @param tr
     */
    public static synchronized void writeLog(File logFile, String tag, String message, Throwable tr) {
        logFile.getParentFile().mkdirs();
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        deleteLog();
        String time = timeFormat.format(Calendar.getInstance().getTime());
        synchronized (logFile) {
            FileWriter fileWriter = null;
            BufferedWriter bufdWriter = null;
            PrintWriter printWriter = null;
            try {
                fileWriter = new FileWriter(logFile, true);
                bufdWriter = new BufferedWriter(fileWriter);
                printWriter = new PrintWriter(fileWriter);
                bufdWriter.append(time).append(" ").append("E").append('/').append(tag).append(" ")
                        .append(message).append('\n');
                bufdWriter.flush();
                tr.printStackTrace(printWriter);
                printWriter.flush();
                fileWriter.flush();
            } catch (IOException e) {
                closeQuietly(fileWriter);
                closeQuietly(bufdWriter);
                closeQuietly(printWriter);
            }
        }
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ioe) {
                // ignore
            }
        }
    }

    public static void deleteLog() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,   -2);
        String old = new SimpleDateFormat( "yyyyMMdd ").format(cal.getTime());
        File file = new File(Environment.getExternalStorageDirectory()
                + "/byxxdownload/" + old+ ".log");
        if (file.exists()) {//删除前两天的日志文件
            file.delete();
        }
    }
}
