package com.cloudera.poverty.common.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @version V1.0
 * @Package com.cloudera.pa.common.base.result
 * @date 2020/6/20 10:05
 * @Copyright ©
 */
@Data
@ApiModel(value = "全局统一返回结果")
public class Lay {
    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String msg;

    @ApiModelProperty(value = "返回条数")
    protected Long count;

    @ApiModelProperty(value = "返回数据")
    private Object data;


    public Lay(){}

    public static Lay ok(){
        Lay lay=new Lay();
        lay.setCode(0);
        lay.setMsg("成功");
        return lay;
    }

    public static Lay error(){
        Lay lay=new Lay();
        lay.setCode(200);
        lay.setMsg("失败");
        return lay;
    }

    public Lay code(Integer code){
        this.setCode(code);
        return this;
    }
    public Lay msg(String msg){
        this.setMsg(msg);
        return this;
    }
    public Lay count(Long count){
        this.setCount(count);
        return this;
    }
    public Lay data(Object data){
        this.setData(data);
        return this;
    }
}
