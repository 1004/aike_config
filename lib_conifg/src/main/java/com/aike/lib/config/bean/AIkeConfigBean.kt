package com.aike.lib.config.bean

import androidx.annotation.Keep

/**
 * 创建时间: 2021/02/26 09:34 <br>
 * 作者: xiekongying <br>
 * 描述:
 */

@Keep
data class AIkeConfigBean(val ext: Extra) {

}

@Keep
data class Extra(
  val config: Conifg
)

@Keep
data class Conifg(
  val id: String,
  val namespance: String,
  val version: String,
  val createTime: String,
  val originalUrl: String,
  val jsonUrl: String,
  var content:Map<String,String?>
)