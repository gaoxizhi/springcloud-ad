package net.gaox.search.mysql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * bonlog日志监听配置信息类
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/20 16:07
 */
@Component
@ConfigurationProperties(prefix = "adconf.mysql")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinlogConfig {

    private String host;
    private Integer port;
    private String username;
    private String password;
    /**
     * 指定binlog监听
     */
    private String binlogName;
    /**
     * 从指定位置监听
     */
    private Long position;
}