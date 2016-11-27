/**
* File Name : CommonCodeService.java	
*
* Functions:
*  TODO
*
* History:
*  Date          Modified_By     Reason    Description	
*  2013-9-3       Administrator          TODO      <一句话功能简述>
*
*  Copyright (c) 2010-2015 lutongnet Corporation. All rights reserved.
*
*/

package com.ocean.lmh.system.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ocean.lmh.base.dao.BaseDaoInterface;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author     [作者]（必须）
 * @see         [相关类/方法]（可选）
 * @since      [产品/模块版本] （必须）
 */

@Transactional
@Service("commonCodeHelper")
public class CommonCodeService
{
    private static Logger log   = LoggerFactory.getLogger(CommonCodeService.class);
    
    @Resource(name = "mybatisBaseDao")
    private BaseDaoInterface baseDao;
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String getCode(String codeName)
    {
        log.info("");
        return null;
    }
}
