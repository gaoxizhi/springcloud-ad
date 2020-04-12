package net.gaox.search;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;

import java.io.IOException;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2020/4/11 23:11
 */
public class BinLogTest {
    public static void main(String[] args) {
        BinaryLogClient client = new BinaryLogClient("127.0.0.1", 3306, "root", "root");
        // client.setBinlogFilename();
        // client.setBinlogPosition();
//        client.registerEventListener(event -> {
//            EventData data = event.getData();
//            if (data instanceof UpdateRowsEventData) {
//                System.out.println("Update............");
//                System.out.println(data.toString());
//            } else if (data instanceof DeleteRowsEventData) {
//                System.out.println("Delete............");
//                System.out.println(data.toString());
//            } else if (data instanceof WriteRowsEventData) {
//                System.out.println("Write............");
//                System.out.println(data.toString());
//            }
//        });

        EventDeserializer eventDeserializer = new EventDeserializer();
        eventDeserializer.setCompatibilityMode(
                EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG
                // EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY
        );
        client.setEventDeserializer(eventDeserializer);
        client.registerEventListener(new BinaryLogClient.EventListener() {

            @Override
            public void onEvent(Event event) {
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
            }
        });
        try {
            client.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
