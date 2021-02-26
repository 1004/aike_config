package com.aike.lib.config.cache

import com.aike.cache.AikeCacheManager
import com.aike.lib.config.AIkeConfigCenter
import com.aike.lib.config.bean.Conifg
import com.aike.lib.config.core.AikeInnerConfigManager

/**
 * 创建时间: 2021/02/25 19:39 <br>
 * 作者: xiekongying <br>
 * 描述: 配置本地缓存
 */
object AikeConfigCacheManager {
  val KEY = "config_cache"
  fun saveConfig(config: Conifg?) {
    if (config == null){
      return
    }
    AikeCacheManager.getInstance().getComDiskMemoryProvider(AIkeConfigCenter.CONFIG_NAME).put(KEY, config)
  }

  fun readConfig(): Conifg? {
    return AikeCacheManager.getInstance().getComDiskMemoryProvider(AIkeConfigCenter.CONFIG_NAME).getByDisk(KEY, Conifg::class.java)
  }
}