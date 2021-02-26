package com.aike.lib.config.parser

import com.aike.cache.memorydisk.disk.CacheJsonUtil

/**
 * 创建时间: 2021/02/25 18:35 <br>
 * 作者: xiekongying <br>
 * 描述:
 */
class AikeJsonConfigParser :IAikeConfigParser{
  override fun <T> json2Obj(json: String, clazz: Class<T>): T? {
    return CacheJsonUtil.getData(json,clazz)
  }

  override fun obj2Json(obj: Any): String? {
    return CacheJsonUtil.toJsonStr(obj)
  }

}