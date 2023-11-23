package com.my.app.mysql.ms_a.service;

import com.my.app.mysql.ms_a.entity.MTestA;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ms
 * @since 2023-11-23
 */
public interface IMTestAService extends IService<MTestA> {
    MTestA getMTestA(Integer id);
}
