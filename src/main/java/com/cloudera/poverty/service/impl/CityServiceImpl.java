package com.cloudera.poverty.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudera.poverty.entity.admin.UserTable;
import com.cloudera.poverty.entity.region.City;
import com.cloudera.poverty.entity.region.DistrictTable;
import com.cloudera.poverty.entity.region.ResettlementPointTable;
import com.cloudera.poverty.entity.region.TownshipTable;
import com.cloudera.poverty.entity.vo.*;
import com.cloudera.poverty.mapper.CityMapper;
import com.cloudera.poverty.mapper.DistrictTableMapper;
import com.cloudera.poverty.mapper.ResettlementPointTableMapper;
import com.cloudera.poverty.mapper.TownshipTableMapper;
import com.cloudera.poverty.service.CityService;
import com.cloudera.poverty.service.ResettlementPointTableService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ct
 * @since 2020-06-04
 */
@Service
public class CityServiceImpl extends ServiceImpl<CityMapper, City> implements CityService {

    @Autowired
    private DistrictTableMapper districtTableMapper;
    @Autowired
    private TownshipTableMapper townshipTableMapper;
    @Autowired
    private ResettlementPointTableMapper resettlementPointTableMapper;
    @Override
    public List<CityVo> nestList(UserTable userTable) {
        //当前用户等级判断
        Integer level = Integer.valueOf(userTable.getLevel());

        CityVo cityVo=new CityVo();
         //市
        City city = baseMapper.selectById("1");
        BeanUtils.copyProperties(city,cityVo);
        String cityId=city.getCiId();
        //拿到县districtTable集合
        List<DistrictTable> districtTables = this.selectDis(cityId);
        List<DistrictVo> districtVoList=new ArrayList<>();
        for (int i = 0; i < districtTables.size(); i++) {
            //遍历区县
            DistrictTable districtTable = districtTables.get(i);
            //创建区县Vo对象
            DistrictVo districtVo=new DistrictVo();
            BeanUtils.copyProperties(districtTable,districtVo);
            String towId = districtTable.getDId();
                //乡镇
            List<TownshipTable> townshipTables = this.selectTow(towId);
                //当前区县所属乡镇Vo
                List<TownshipVo> townshipVoList=new ArrayList<>();
                //遍历乡镇id,查询得到所有安置点,组合乡镇Vo类
                for (int j = 0; j < townshipTables.size(); j++) {
                    //遍历乡镇
                    TownshipTable townshipTable = townshipTables.get(j);
                    //创建乡镇Vo类,放入数据
                    TownshipVo townshipVo = new TownshipVo();
                    BeanUtils.copyProperties(townshipTable,townshipVo);
                    //乡镇ID
                    String tableId = townshipTable.getTId();
                    List<ResettlementPointTable> resettlementPointTables = this.selectRes(tableId);
                    //安置点进入当前乡镇
                    townshipVo.setChildren(resettlementPointTables);
                    //将乡镇放入区县对象乡镇集合
                    townshipVoList.add(townshipVo);
                }
                //设置区县的乡镇对象
            districtVo.setChildren(townshipVoList);

                //将区县对象加入区县集合
            districtVoList.add(districtVo);
            }
        cityVo.setChildren(districtVoList);

        List<CityVo> list=new ArrayList<>();
        list.add(cityVo);
        return list;
        }

    //查询市所有区县
    @Override
    public List<DistrictTable> selectDis(String cityId){
        QueryWrapper<DistrictTable> districtTableQueryWrapper=new QueryWrapper<>();
        districtTableQueryWrapper.eq("city_id",cityId);
        List<DistrictTable> districtTables = districtTableMapper.selectList(districtTableQueryWrapper);
        return districtTables;
    }
    //查询所有乡镇
    @Override
    public List<TownshipTable> selectTow(String districtId){
        QueryWrapper<TownshipTable> townshipTableQueryWrapper=new QueryWrapper<>();
        QueryWrapper<TownshipTable> district_id = townshipTableQueryWrapper.eq("district_id", districtId);
        List<TownshipTable> townshipTableList = townshipTableMapper.selectList(townshipTableQueryWrapper);
        return townshipTableList;
    }

    //Layui返回
    @Override
    public List<CityAllVo> selectList() {
        List<CityAllVo> list=new ArrayList<>();
        List<City> cities = baseMapper.selectList(null);
        List list1=new ArrayList();
        for (int i = 0; i < cities.size(); i++) {
            City city = cities.get(i);
            CityAllVo cityAllVo = new CityAllVo();
            cityAllVo.setCiId(city.getCiId());
            cityAllVo.setCityName(city.getCityName());
            cityAllVo.setPatentId("0");
            list1.add(cityAllVo);
        }
        List<DistrictTable> districtTables = districtTableMapper.selectList(null);
        for (int i = 0; i < districtTables.size(); i++) {
            DistrictTable districtTable = districtTables.get(i);
            CityAllVo cityAllVo = new CityAllVo();
            cityAllVo.setCiId(districtTable.getDId());
            cityAllVo.setCityName(districtTable.getDistrict());
            cityAllVo.setPatentId(districtTable.getCityId());
            list1.add(cityAllVo);
        }
        List<TownshipTable> townshipTables = townshipTableMapper.selectList(null);
        for (int i = 0; i < townshipTables.size(); i++) {
            TownshipTable townshipTable = townshipTables.get(i);
            CityAllVo cityAllVo = new CityAllVo();
            cityAllVo.setCiId(townshipTable.getTId());
            cityAllVo.setCityName(townshipTable.getTownship());
            cityAllVo.setPatentId(townshipTable.getDistrictId());
            list1.add(cityAllVo);
        }
        List<ResettlementPointTable> resettlementPointTables = resettlementPointTableMapper.selectList(null);
        for (int i = 0; i < resettlementPointTables.size(); i++) {
            ResettlementPointTable resettlementPointTable = resettlementPointTables.get(i);
            CityAllVo cityAllVo = new CityAllVo();
            cityAllVo.setCiId(resettlementPointTable.getRId());
            cityAllVo.setCityName(resettlementPointTable.getResettlementPoint());
            cityAllVo.setPatentId(resettlementPointTable.getTownshipId());
            list1.add(cityAllVo);
        }
        return list1;
    }

    @Override
    public void saveList(CityReVo cityReVo) {
        String rId = cityReVo.getRId();
        String tId = cityReVo.getTId();
        String dId = cityReVo.getDId();
        String ciId = cityReVo.getCiId();
        String name = cityReVo.getName();
        if (StringUtils.isEmpty(rId)){
            ResettlementPointTable resettlementPointTable = new ResettlementPointTable();
            resettlementPointTable.setTownshipId(tId);
            resettlementPointTable.setResettlementPoint(name);
            resettlementPointTableMapper.insert(resettlementPointTable);
            return;
        }
        if (StringUtils.isEmpty(tId)){
            TownshipTable townshipTable = new TownshipTable();
            townshipTable.setDistrictId(tId);
            townshipTable.setTownship(name);
            townshipTableMapper.insert(townshipTable);
            return;
        }
        if (StringUtils.isEmpty(dId)){
            DistrictTable districtTable = new DistrictTable();
            districtTable.setCityId(ciId);
            districtTable.setDistrict(name);
            districtTableMapper.insert(districtTable);
            return;
        }
    }

    //查询乡镇所属安置点

    public List<ResettlementPointTableVo> selectRese(String towId){

        QueryWrapper<ResettlementPointTable> resettlementPointTableQueryWrapper=new QueryWrapper<>();
        resettlementPointTableQueryWrapper.eq("township_id",towId);
        //拿到安置点集合
        List<ResettlementPointTableVo> list=new ArrayList<>();
        List<ResettlementPointTable> resettlementPointTables = resettlementPointTableMapper.selectList(resettlementPointTableQueryWrapper);
        for (int i = 0; i < resettlementPointTables.size(); i++) {
            ResettlementPointTable resettlementPointTable = resettlementPointTables.get(i);
            ResettlementPointTableVo resettlementPointTableVo = new ResettlementPointTableVo();
            BeanUtils.copyProperties(resettlementPointTable,resettlementPointTableVo);
            list.add(resettlementPointTableVo);
        }
        return list;
    }

    //查询乡镇所属安置点
    @Override
    public List<ResettlementPointTable> selectRes(String towId){

        QueryWrapper<ResettlementPointTable> resettlementPointTableQueryWrapper=new QueryWrapper<>();
        resettlementPointTableQueryWrapper.eq("township_id",towId);
        //拿到安置点集合
        List<ResettlementPointTable> resettlementPointTables = resettlementPointTableMapper.selectList(resettlementPointTableQueryWrapper);
        return resettlementPointTables;
    }

}
