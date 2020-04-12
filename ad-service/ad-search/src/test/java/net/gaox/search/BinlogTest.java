package net.gaox.search;


import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2020/4/11 22:38
 */
@SpringBootTest
public class BinlogTest {
    @Test
    public void Tes() {
        BinaryLogClient client = new BinaryLogClient("127.0.0.1", 3306, "root", "root");
        // client.setBinlogFilename();
        // client.setBinlogPosition();
        client.registerEventListener(event -> {
            EventData data = event.getData();
            if (data instanceof UpdateRowsEventData) {
                System.out.println("Update............");
                System.out.println(data.toString());
            } else if (data instanceof DeleteRowsEventData) {
                System.out.println("Delete............");
                System.out.println(data.toString());
            } else if (data instanceof WriteRowsEventData) {
                System.out.println("Write............");
                System.out.println(data.toString());
            }
        });
    }
}
