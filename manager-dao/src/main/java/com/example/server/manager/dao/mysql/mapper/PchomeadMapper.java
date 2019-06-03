package com.example.server.manager.dao.mysql.mapper;

import com.example.server.manager.dao.mysql.domain.Pchomead;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PchomeadMapper {

    int insert(Pchomead pchomead);

    Pchomead selectById(Long id);

    List<Pchomead> selectAll();

    List<Pchomead> selectAllByCondition(@Param("filterWord") String filterWord);

    int updateById(Pchomead pchomead);

    int updateStatusById(@Param("id") Long id, @Param("status") Integer status, @Param("operator") String operator);

    int deleteById(@Param("id") Long id, @Param("operator") String operator);
}
