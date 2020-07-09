package com.cloudera.poverty.entity.admin;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author fengtoos
 * @since 2020-04-08
 */
@Data
@TableName("user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String roleId;


}
