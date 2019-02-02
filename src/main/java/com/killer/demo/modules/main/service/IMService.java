package com.killer.demo.modules.main.service;/**
 * @author killer
 * @date 2019/1/24 -  15:34
 **/

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *@description 用来调用远程服务
 *@author killer
 *@date 2019/01/24 - 15:34
 */
@FeignClient(name = "IM-SYSTEM")
public interface IMService {
    @GetMapping("/im/test")
    String feignTest();
}
