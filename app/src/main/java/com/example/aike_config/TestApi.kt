package com.example.aike_config

import com.aike.httpserver.adapter.calladapter.IAikeHttpCall
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * 创建时间: 2021/02/26 14:24 <br>
 * 作者: xiekongying <br>
 * 描述:
 */
interface TestApi {
  @GET
  fun cdnContent(@Url url: String?): IAikeHttpCall<Any?>?
}