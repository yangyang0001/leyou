package com.inspur.spec.service;

import com.inspur.entity.SpecGroup;
import com.inspur.entity.SpecParam;

import java.util.List;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-12 16:26
 */
public interface SpecificationService {

    public List<SpecGroup> queryGroup(Long cid);

    public List<SpecParam> queryParamList(Long gid, Long cid, Boolean generic, Boolean searching);

    public List<SpecGroup> queryGroupWithParams(Long cid);
}
