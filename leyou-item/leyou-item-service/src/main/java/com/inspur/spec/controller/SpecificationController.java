package com.inspur.spec.controller;

import com.inspur.entity.SpecGroup;
import com.inspur.entity.SpecParam;
import com.inspur.spec.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-12 16:27
 */
@Controller
@RequestMapping("/spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;

    @GetMapping("/groups/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupList(@PathVariable("cid") Long cid) {
        List<SpecGroup> specGroupList = specificationService.queryGroup(cid);
        if(CollectionUtils.isEmpty(specGroupList)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(specGroupList);
    }

    @GetMapping("/params")
    public ResponseEntity<List<SpecParam>> queryParamList(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "generic", required = false) Boolean generic,
            @RequestParam(value = "searching", required = false) Boolean searching ) {
        List<SpecParam> specParamList = specificationService.queryParamList(gid, cid, generic, searching);
        if(CollectionUtils.isEmpty(specParamList)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(specParamList);
    }

    @GetMapping("/group/param/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupWithParams(@PathVariable("cid") Long cid) {
        List<SpecGroup> specGroupList = specificationService.queryGroupWithParams(cid);
        if(CollectionUtils.isEmpty(specGroupList)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(specGroupList);
    }
}
