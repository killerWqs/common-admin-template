package com.killer.demo.unit;/**
 * @author killer
 * @date 2018/11/17 -  20:11
 **/

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *@description
 *@author killer
 *@date 2018/11/17 - 20:11
 */
public class MyStringTest {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Test
    public void test() {
        String string = "Hello World";
//      @param      beginIndex   the beginning index, inclusive.
//      @param      endIndex     the ending index, exclusive. 最后一个位置不包括
        logger.info(string.substring(0, string.length()));
    }
}
