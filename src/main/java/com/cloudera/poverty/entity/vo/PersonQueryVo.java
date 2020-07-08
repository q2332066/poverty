package com.cloudera.poverty.entity.vo;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

@Data
public class PersonQueryVo {
    private String perId;
    private String name;
    private String host;
    private String resettlementPointId;
    private String arrangement;
    private String idCard;
    private String hostId;
    private String relationship;
    private String cityId;
    private String disId;
    private String towId;
    private String resId;
    private Long page;
    private Long limit;
}
