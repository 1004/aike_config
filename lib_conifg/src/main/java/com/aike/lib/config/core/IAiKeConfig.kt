package com.aike.lib.config.core

/**
 * 创建时间: 2021/02/25 18:27 <br>
 * 作者: xiekongying <br>
 * 描述:
 */
interface IAiKeConfig {
  /**
   * 检测配置是否要更新
   */
  fun checkConfig(data: String)

  /**
   * 获取发布的配置name的值
   */
  fun getStringConfig(name: String): String?

  /**
   * 获取配置的name的对象
   */
  fun <T> getObjectConfig(name: String, clazz: Class<T>): T?

  /**
   * 获取配置的版本号
   */
  fun getVersion(): String?
}