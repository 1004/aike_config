package com.example.aike_config

import android.app.Application
import com.aike.httpserver.AikeHttpService
import com.aike.httpserver.config.service.dependency.DefaultRetrofitConfigDependency
import com.aike.lib.config.AIkeConfigCenter
import com.aike.lib.config.interceptor.AikeConfigInterceptor
import okhttp3.Interceptor

/**
 * 创建时间: 2021/02/26 11:17 <br>
 * 作者: xiekongying <br>
 * 描述:
 */
class MyApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    AIkeConfigCenter.init(this, configSpace = "newhouse")
    AikeHttpService.initService("main", object : DefaultRetrofitConfigDependency() {
      override fun interceptors(): MutableList<Interceptor> {
        val list = mutableListOf<Interceptor>()
        list.add(AikeConfigInterceptor())
        return list
      }

      override fun baseUrl(): String {
        return "https://www.test.com"
      }
    })
  }

}