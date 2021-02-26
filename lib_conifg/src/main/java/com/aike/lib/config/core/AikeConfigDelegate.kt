package com.aike.lib.config.core

import com.aike.httpserver.AikeHttpService
import com.aike.httpserver.adapter.calladapter.ASimpleCallBack
import com.aike.lib.config.AIkeConfigCenter
import com.aike.lib.config.api.CdnApi
import com.aike.lib.config.bean.AIkeConfigBean
import com.aike.lib.config.bean.Conifg
import com.aike.lib.config.cache.AikeConfigCacheManager
import retrofit2.Response

/**
 * 创建时间: 2021/02/25 18:32 <br>
 * 作者: xiekongying <br>
 * 描述:
 */
class AikeConfigDelegate : IAiKeConfig {
  private var localConfig:Conifg ?= null
  private var isLoadCdn:Boolean=false

  init {
    localConfig = AikeConfigCacheManager.readConfig()
  }

  override fun checkConfig(data: String) {
    if (data == null || data.isEmpty()){
      return
    }
    val configParser = AikeInnerConfigManager.instance.getConfigParser()
    val remoteConfigBean = configParser.json2Obj(data, AIkeConfigBean::class.java)
    remoteConfigBean?.ext?.apply {
      updataConfig(config)
    }
  }

  private fun updataConfig(config: Conifg) {
    if (config == null || config.jsonUrl == null){
      return
    }
    if (compareVersion(config,localConfig) || (!isLoadCdn && localConfig?.content == null)){
      this.localConfig = config
      sync()
    }
  }

  //拉去cdn资源
  private fun sync() {
    isLoadCdn = true
    val createService = AikeHttpService.createService(AIkeConfigCenter.CONFIG_NAME, CdnApi::class.java)
    createService.cdnContent(this.localConfig?.jsonUrl).enqueue(object : ASimpleCallBack<Any>() {
      override fun onResponse(response: Response<Any>?, entity: Any?) {
        isLoadCdn = false
        if (entity != null && entity is Map<*, *>){
          localConfig?.content = entity as Map<String, String?>
          AikeConfigCacheManager.saveConfig(localConfig)
          AikeConfigNotifyCenter.notifyConfigChange(localConfig!!)
        }
      }

      override fun onError(e: Throwable?) {
        isLoadCdn = false
      }
    })
  }

  /**
   * v1: 服务器
   * v2: 本地
   */
  private fun compareVersion(v1:Conifg,v2:Conifg?):Boolean{
    if (v2 == null) {
      return true
    }
    return v1.version>v2.version
  }

  override fun getStringConfig(name: String): String? {
    return localConfig?.content?.get(name) ?: null
  }

  override fun <T> getObjectConfig(name: String, clazz: Class<T>): T? {
    val value = localConfig?.content?.get(name) ?: return null
    return AikeInnerConfigManager.instance.getConfigParser().json2Obj(value,clazz)
  }

  override fun getVersion(): String? {
    return localConfig?.version
  }
}