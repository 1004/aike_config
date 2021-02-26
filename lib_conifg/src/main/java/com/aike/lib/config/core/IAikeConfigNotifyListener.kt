package com.aike.lib.config.core

import com.aike.lib.config.bean.Conifg

/**
 * 创建时间: 2021/02/26 14:11 <br>
 * 作者: xiekongying <br>
 * 描述:
 */
interface IAikeConfigNotifyListener {
  fun onConfigChange(config: Conifg)
}