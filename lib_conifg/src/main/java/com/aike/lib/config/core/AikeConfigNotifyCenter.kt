package com.aike.lib.config.core

import com.aike.lib.config.bean.Conifg

/**
 * 创建时间: 2021/02/26 12:14 <br>
 * 作者: xiekongying <br>
 * 描述: 通知中心
 */
object AikeConfigNotifyCenter {
  private val listeners: MutableList<IAikeConfigNotifyListener> = mutableListOf()
  fun addConfigNotifyListener(notifyListener: IAikeConfigNotifyListener) {
    if (notifyListener != null) {
      listeners.add(notifyListener)
    }
  }

  fun removeConfigNotifyListener(notifyListener: IAikeConfigNotifyListener) {
    listeners.remove(notifyListener)
  }

  fun notifyConfigChange(config: Conifg) {
    if (config != null) {
      listeners.forEach {
        it.onConfigChange(config)
      }
    }
  }

}