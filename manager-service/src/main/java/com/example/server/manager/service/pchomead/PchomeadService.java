package com.example.server.manager.service.pchomead;

import com.example.server.manager.dao.mysql.domain.Pchomead;

import java.util.List;

public interface PchomeadService {

    boolean createPchomead(Pchomead pchomead);

    Pchomead getPchomead(Long pchomeadId);

    /**
     * 分页查询
     *
     * @param page  页码
     * @param pagesize 每页显示数量
     */
    List<Pchomead> getPchomeadList(Integer page, Integer pagesize);

    /**
     * 分页查询 按查询条件查询
     *
     * @param page  页码
     * @param pagesize 每页显示数量
     */
    List<Pchomead> getPchomeadListByCondition(Integer page, Integer pagesize, String filterWord);

    boolean updatePchomead(Pchomead pchomead);

    boolean updatePchomeadStatusById(Long pchomeadId, Integer status, String operator);

    boolean deletePchomeadById(Long pchomeadId, String operator);
}
