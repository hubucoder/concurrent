package cn.itcast.n2;

import cn.itcast.Constants;
import cn.itcast.n2.util.FileReader;
import lombok.extern.slf4j.Slf4j;

/**
 * 同步等待
 */
@Slf4j(topic = "c.Sync")
public class Sync {

    public static void main(String[] args) {
        // 一个本地的视频文件
        FileReader.read(Constants.MP4_FULL_PATH);   // 同步
        log.debug("do other things ..."); // 只有上一步结果返回了，才调用本行代码
    }

}
