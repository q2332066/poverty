package com.cloudera.poverty.entity.vo;

import lombok.Data;

@Data
public class ResettlementQueryVo {

    private String rId;
    private String resettlementPoint;
    private String townshipId;
    private String schoolName;
    private String hospitalName;
}
