package com.inspur.category.mapper;

import com.inspur.entity.Category;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-09 11:42
 */
public interface CategoryMapper extends Mapper<Category>, SelectByIdListMapper<Category, Long> {

}
