package com.inspur.spec.service.impl;


import com.inspur.entity.SpecGroup;
import com.inspur.entity.SpecParam;
import com.inspur.spec.mapper.SpecGroupMapper;
import com.inspur.spec.mapper.SpecParamMapper;
import com.inspur.spec.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-12 16:26
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private SpecParamMapper specParamMapper;

    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Override
    public List<SpecGroup> queryGroup(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        return specGroupMapper.select(specGroup);
    }

    /**
     * 根据条件 查询各种规格 参数
     * @param gid
     * @param cid
     * @param generic
     * @param searching
     * @return
     */
    @Override
    public List<SpecParam> queryParamList(Long gid, Long cid, Boolean generic, Boolean searching) {
        SpecParam specParam = new SpecParam();
        if(gid != null) {
            specParam.setGroupId(gid);
        }
        if(cid != null) {
            specParam.setCid(cid);
        }
        if(generic != null) {
            specParam.setGeneric(generic);
        }
        if(searching != null) {
            specParam.setSearching(searching);
        }
        return specParamMapper.select(specParam);
    }

    @Override
    public List<SpecGroup> queryGroupWithParams(Long cid) {
        List<SpecGroup> specGroups = this.queryGroup(cid);
        specGroups.forEach(group -> {
            List<SpecParam> specParams = this.queryParamList(group.getId(), null, null, null);
            group.setParams(specParams);
        });
        return specGroups;
    }

}
