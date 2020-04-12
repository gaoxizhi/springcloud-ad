package net.gaox.search.runner;

import lombok.extern.slf4j.Slf4j;
import net.gaox.search.mysql.BinlogClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * <p> binlog监听任务 </p>
 *
 * @author gaox·Eric
 * @date 2020/4/12 23:04
 */
@Slf4j
@Component
public class BinlogRunner implements CommandLineRunner {

    private final BinlogClient client;

    public BinlogRunner(BinlogClient client) {
        this.client = client;
    }

    @Override
    public void run(String... strings) {
        log.info("Coming in BinlogRunner...");
        client.connect();
    }
}