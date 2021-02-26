package com.aike.lib.config.parser

/**
 * 创建时间: 2021/02/25 18:34 <br>
 * 作者: xiekongying <br>
 * 描述:
 */
interface IAikeConfigParser {
  fun <T> json2Obj(json: String, clazz: Class<T>): T?
  fun obj2Json(obj: Any): String?
}