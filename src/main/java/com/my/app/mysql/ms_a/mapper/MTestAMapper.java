package com.my.app.mysql.ms_a.mapper;

import com.my.app.cache.MybatisPlusRedisCache;
import com.my.app.mysql.ms_a.entity.MTestA;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Property;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ms
 * @since 2023-11-23
 */
@CacheNamespace(implementation = MybatisPlusRedisCache.class, eviction = MybatisPlusRedisCache.class, properties = {@Property(name = "flushInterval", value = "60000")})
public interface MTestAMapper extends BaseMapper<MTestA> {

}
