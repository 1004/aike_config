package com.aike.lib.config

import android.content.Context
import com.aike.cache.AikeCacheManager
import com.aike.httpserver.AikeHttpService
import com.aike.httpserver.config.service.dependency.DefaultRetrofitConfigDependency
import com.aike.lib.config.core.AikeConfigNotifyCenter
import com.aike.lib.config.core.AikeInnerConfigManager
import com.aike.lib.config.core.IAikeConfigNotifyListener
import com.aike.lib.config.parser.AikeJsonConfigParser
import com.aike.lib.config.parser.IAikeConfigParser

/**
 * 创建时间: 2021/02/25 18:23 <br>
 * 作者: xiekongying <br>
 * 描述: 配置中心
 */
object AIkeConfigCenter {
  @JvmStatic
  val CONFIG_NAME = "aike_config"

  @JvmStatic
  var name_spance: String = ""

  fun init(context: Context, configSpace: String, parser: IAikeConfigParser = AikeJsonConfigParser()) {
    name_spance = configSpace
    AikeCacheManager.getInstance().init(CONFIG_NAME, context)
    AikeHttpService.initService(CONFIG_NAME, object : DefaultRetrofitConfigDependency() {
      override fun baseUrl(): String {
        return "https://aikeconfig/"
      }
    })
    AikeInnerConfigManager.instance.setParser(parser)
  }

  fun checkConfig(data: String) {
    AikeInnerConfigManager.instance.checkConfig(data)
  }

  fun getStringConfig(name: String): String? {
    return AikeInnerConfigManager.instance.getStringConfig(name)
  }

  fun <T> getObjectConfig(name: String, clazz: Class<T>): T? {
    return AikeInnerConfigManager.instance.getObjectConfig(name, clazz)
  }

  fun getVersion(): String? {
    return AikeInnerConfigManager.instance.getVersion()
  }

  fun addConfigChangeListener(listener: IAikeConfigNotifyListener) {
    AikeConfigNotifyCenter.addConfigNotifyListener(listener)
  }

  fun removeConfigChangeListener(listener: IAikeConfigNotifyListener) {
    AikeConfigNotifyCenter.removeConfigNotifyListener(listener)
  }
}