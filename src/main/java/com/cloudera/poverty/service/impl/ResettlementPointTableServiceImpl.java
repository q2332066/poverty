package com.cloudera.poverty.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudera.poverty.entity.region.ResettlementPointTable;
import com.cloudera.poverty.entity.vo.ResettlementQueryVo;
import com.cloudera.poverty.mapper.ResettlementPointTableMapper;
import com.cloudera.poverty.service.ResettlementPointTableService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ct
 * @since 2020-06-04
 */
@Service
public class ResettlementPointTableServiceImpl extends ServiceImpl<ResettlementPointTableMapper, ResettlementPointTable> implements ResettlementPointTableService {
    /**
     * 安置点分页条件查询
     * @param pageParam
     * @param resettlementQueryVo
     * @return
     */
    @Override
    public IPage<ResettlementPointTable> selectPage(Page<ResettlementPointTable> pageParam, ResettlementQueryVo resettlementQueryVo) {

        if (resettlementQueryVo==null){
         return baseMapper.selectPage(pageParam, null);
        }
        QueryWrapper<ResettlementPointTable> wrapper=new QueryWrapper<>();
        if (!StringUtils.isEmpty(resettlementQueryVo.getRId())){
            wrapper.eq("r_id",resettlementQueryVo.getRId());
        }
        if (!StringUtils.isEmpty(resettlementQueryVo.getResettlementPoint())){
            wrapper.like("resettlement_point",resettlementQueryVo.getResettlementPoint());
        }
        if (!StringUtils.isEmpty(resettlementQueryVo.getSchoolName())){
            wrapper.eq("school_name",resettlementQueryVo.getSchoolName()).or()
            .like("kindergarten_name",resettlementQueryVo.getSchoolName());
        }
        if (!StringUtils.isEmpty(resettlementQueryVo.getHospitalName())){
            wrapper.eq("hospital_name",resettlementQueryVo.getHospitalName()).or()
            .like("library_name",resettlementQueryVo.getHospitalName());
        }
        if (!StringUtils.isEmpty(resettlementQueryVo.getTownshipId())){
            wrapper.eq("township_id",resettlementQueryVo.getTownshipId());
        }
        return baseMapper.selectPage(pageParam, wrapper);
    }

}
