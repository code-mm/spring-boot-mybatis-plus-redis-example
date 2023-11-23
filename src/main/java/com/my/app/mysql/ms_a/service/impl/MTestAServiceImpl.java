package com.my.app.mysql.ms_a.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.my.app.mysql.ms_a.entity.MTestA;
import com.my.app.mysql.ms_a.mapper.MTestAMapper;
import com.my.app.mysql.ms_a.service.IMTestAService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ms
 * @since 2023-11-23
 */
@Service
@DS("ms_a")
public class MTestAServiceImpl extends ServiceImpl<MTestAMapper, MTestA> implements IMTestAService {
    @Override
    public MTestA getMTestA(Integer id) {
        return baseMapper.selectById(id);
    }
}
