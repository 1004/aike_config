package com.aike.lib.config.core

import com.aike.lib.config.parser.AikeJsonConfigParser
import com.aike.lib.config.parser.IAikeConfigParser

/**
 * 创建时间: 2021/02/25 19:37 <br>
 * 作者: xiekongying <br>
 * 描述: 内部配置管理
 */
class AikeInnerConfigManager private constructor() : IAiKeConfig {
  val delegate: AikeConfigDelegate = AikeConfigDelegate()
  private var parser: IAikeConfigParser? = null

  companion object {
    @JvmStatic
    var instance: AikeInnerConfigManager = Holder.instance
      private set
  }

  private object Holder {
    val instance = AikeInnerConfigManager()
  }

  fun setParser(parser: IAikeConfigParser) {
    this.parser = parser
  }

  override fun checkConfig(data: String) {
    delegate.checkConfig(data)
  }

  override fun getStringConfig(name: String): String? {
    return delegate.getStringConfig(name)
  }

  override fun <T> getObjectConfig(name: String, clazz: Class<T>): T? {
    return delegate.getObjectConfig(name, clazz)
  }

  override fun getVersion(): String? {
    return delegate.getVersion()
  }

  fun getConfigParser(): IAikeConfigParser {
    return this.parser ?: AikeJsonConfigParser()
  }

}