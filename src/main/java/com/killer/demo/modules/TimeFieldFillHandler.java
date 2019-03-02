package com.killer.demo.modules;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 *@description mybatis plus用来给一些通用字段设置值，比如intime，updatetime
 *@author killer
 *@date 2019/03/02 - 12:19
 */
@Component
public class TimeFieldFillHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        // metaObject 就是一个类的元数据
        this.setFieldValByName("intime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updatetime", LocalDateTime.now(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updatetime", LocalDateTime.now(), metaObject);
    }
}
