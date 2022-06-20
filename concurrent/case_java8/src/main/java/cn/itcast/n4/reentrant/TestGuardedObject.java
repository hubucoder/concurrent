package cn.itcast.n4.reentrant;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Slf4j(topic = "c.TestGuardedObject")
public class TestGuardedObject {
    // 线程1 等待 线程2 的下载结果
    public static void main(String[] args) {
        GuardedObject guardedObject = new GuardedObject();
        new Thread(() -> {
            // 等待结果
            log.debug("t1线程等待结果");
            List<String> list = (List<String>) guardedObject.get(5000);
            log.debug("获得的结果长度 {}", list);
        },"t1").start();

        new Thread(() -> {
            try {

                List<String> list = DownLoad.downLoad();
                guardedObject.complete(list);
                log.debug("t2线程返回结果");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }, "t2").start();
    }
}

class GuardedObject {
    private Object reponse;

    // 获得结构的方法
    /*public Object get() {
        synchronized (this) {
            while (reponse == null) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return reponse;
        }
    }*/

    // 获取结果
    // timeout 表示要等待多久
    public Object get(long timeout) {
        synchronized (this) {
            // 开始时间
            long begin = System.currentTimeMillis();
            // 经历时间
            long passedTime = 0;
            while(reponse == null) {
                if (passedTime >= timeout) {
                    break;
                }

                try {
                    this.wait(timeout - passedTime); // 不能用 timeout, 避免虚假唤醒从而增加 wait 时间
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                passedTime = System.currentTimeMillis() - begin;
            }
            return reponse;
        }

    }

    // 获得结果
    public void complete(Object res) {
        synchronized (this) {
            this.reponse = res;
            this.notifyAll();
        }
    }
}

// 普通的下载类
class DownLoad{
    public static List<String> downLoad () throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL("https://www.baidu.com/").openConnection();

        List<String> list = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))){
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            return list;
        }
    }
}
