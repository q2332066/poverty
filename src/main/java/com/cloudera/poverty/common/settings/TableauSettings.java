package com.cloudera.poverty.common.settings;

import lombok.Data;

/**
 * Tableau 配置文件类
 */
@Data
public class TableauSettings {
    //服务器地址
    public String server;
    //嵌入式用户名
    public String username;
}
