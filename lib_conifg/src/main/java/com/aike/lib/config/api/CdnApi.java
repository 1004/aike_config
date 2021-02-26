package com.aike.lib.config.api;

import com.aike.httpserver.adapter.calladapter.IAikeHttpCall;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * 创建时间: 2021/02/26 10:19 <br>
 * 作者: xiekongying <br>
 * 描述:
 */
public interface CdnApi {
  @GET
  IAikeHttpCall<Object> cdnContent(@Url String url);
}
