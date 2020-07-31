package com.cloudera.poverty.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudera.poverty.entity.admin.UserTable;
import com.cloudera.poverty.entity.region.City;
import com.cloudera.poverty.entity.region.DistrictTable;
import com.cloudera.poverty.entity.region.ResettlementPointTable;
import com.cloudera.poverty.entity.region.TownshipTable;
import com.cloudera.poverty.entity.vo.CityAllVo;
import com.cloudera.poverty.entity.vo.CityReVo;
import com.cloudera.poverty.entity.vo.CityVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ct
 * @since 2020-06-04
 */
public interface CityService extends IService<City> {

    List<CityVo> nestList(UserTable userTable);

    List<ResettlementPointTable> selectRes(String towId);

    List<DistrictTable> selectDis(String cityId);

    List<TownshipTable> selectTow(String districtId);

    void update(CityReVo vo);

    List<CityAllVo> selectList();

    void saveList(CityReVo cityReVo);
}
