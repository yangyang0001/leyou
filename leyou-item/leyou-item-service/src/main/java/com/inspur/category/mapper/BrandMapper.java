package com.inspur.category.mapper;

import com.inspur.entity.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-09 17:03
 */
public interface BrandMapper extends Mapper<Brand> {

    @Insert("insert into tb_category_brand (category_id, brand_id) values (#{category_id}, #{brand_id})")
    public int insertBrandWithCategories(@Param("category_id") Long category_id, @Param("brand_id") Long brand_id);


    @Select("select b.* from tb_brand b inner join tb_category_brand cb on b.id = cb.brand_id where cb.category_id = #{category_id}")
    public List<Brand> queryBrandListWithCategoryId(@Param("category_id") Long category_id);

}
