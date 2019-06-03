package com.example.server.manager.service.pchomead.impl;

import com.example.server.manager.dao.mysql.domain.Pchomead;
import com.example.server.manager.dao.mysql.mapper.PchomeadMapper;
import com.example.server.manager.service.base.BaseService;
import com.example.server.manager.service.pchomead.PchomeadService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class PchomeadServiceImpl extends BaseService implements PchomeadService {

    @Autowired
    private PchomeadMapper pchomeadMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean createPchomead(Pchomead pchomead) {
        if (pchomead == null) {
            return false;
        }
        return pchomeadMapper.insert(pchomead) == 1;
    }

    @Override
    public Pchomead getPchomead(Long pchomeadId) {
        if (pchomeadId == null) {
            return null;
        }
        return pchomeadMapper.selectById(pchomeadId);
    }

    @Override
    public List<Pchomead> getPchomeadList(Integer page, Integer pagesize) {
        // 物理分页
        PageHelper.startPage(page, pagesize);
        PageHelper.orderBy("id desc");
        return pchomeadMapper.selectAll();
    }

    @Override
    public List<Pchomead> getPchomeadListByCondition(Integer page, Integer pagesize, String filterWord) {
        // 物理分页
        PageHelper.startPage(page, pagesize);
        PageHelper.orderBy("id desc");
        return pchomeadMapper.selectAllByCondition(filterWord);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updatePchomead(Pchomead pchomead) {
        if (pchomead == null || pchomead.getId() == null) {
            return false;
        }
        return pchomeadMapper.updateById(pchomead) == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updatePchomeadStatusById(Long pchomeadId, Integer status, String operator) {
        if (pchomeadId == null || status == null) {
            return false;
        }
        return pchomeadMapper.updateStatusById(pchomeadId, status, operator) == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deletePchomeadById(Long pchomeadId, String operator) {
        if (pchomeadId == null) {
            return false;
        }
        return pchomeadMapper.deleteById(pchomeadId, operator) == 1;
    }
}
