package com.cloudera.poverty.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author helen
 * @since 2020/4/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtInfo {
    private String uId;
    private String showName;
    private String userName;
    private String regional;
    private String did;
    private String dname;
    private String tid;
    private String tname;
    private String rid;
    private String rname;
    private String level;
    private List<String> roleTables=new ArrayList<>();
    private List<String> authorityTables=new ArrayList<>();
}
