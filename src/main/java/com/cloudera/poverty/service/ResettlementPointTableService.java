package com.cloudera.poverty.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudera.poverty.entity.region.ResettlementPointTable;
import com.cloudera.poverty.entity.vo.ResettlementQueryVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ct
 * @since 2020-06-04
 */
public interface ResettlementPointTableService extends IService<ResettlementPointTable> {

    IPage<ResettlementPointTable> selectPage(Page<ResettlementPointTable> pageParam, ResettlementQueryVo resettlementQueryVo);

}
