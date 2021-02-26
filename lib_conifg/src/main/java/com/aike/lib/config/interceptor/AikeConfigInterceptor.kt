package com.aike.lib.config.interceptor

import com.aike.lib.config.AIkeConfigCenter
import com.aike.lib.config.core.AikeInnerConfigManager
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import okhttp3.internal.http.HttpHeaders
import okio.Buffer
import java.io.EOFException
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException

/**
 * 创建时间: 2021/02/26 11:49 <br>
 * 作者: xiekongying <br>
 * 描述:
 */
class AikeConfigInterceptor : Interceptor {
  val MAX_BODY_SIZE = 1024 * 1024.toLong()

  override fun intercept(chain: Chain): Response {
    val request = chain.request()
    val newBuilder = request.newBuilder()
    val params = mapOf(
      "version" to (AIkeConfigCenter.getVersion()),
      "name_space" to AIkeConfigCenter.name_spance
    )
    newBuilder.addHeader("config", AikeInnerConfigManager.instance.getConfigParser().obj2Json(params))
    val response = chain.proceed(newBuilder.build())
    val handleResponseBody = handleResponseBody(response)
    handleResponseBody?.let {
      AikeInnerConfigManager.instance.checkConfig(it)
    }
    return response
  }

  fun handleResponseBody(response: Response?): String? {
    if (response == null || !HttpHeaders.hasBody(response)
      || !response.isSuccessful
    ) {
      return null
    }
    val utf8Value = Charset.forName("UTF-8")
    var respCharset: Charset =utf8Value
    val buffer: Buffer
    val responseBody = response.body()
    if (responseBody != null) {
      val contentType = responseBody.contentType()
      if (contentType != null) {
        try {
          respCharset = contentType.charset(utf8Value)!!
        } catch (e: UnsupportedCharsetException) {
          return null
        }
      }
      val responseSource = responseBody.source()
      try {
        responseSource.request(Long.MAX_VALUE) // Buffer the entire body.
      } catch (e: Throwable) {
      }
      try {
        buffer = responseSource.buffer()
        if (!isPlaintext(buffer)) {
          return null
        }
        val respBody = buffer.clone().readString(respCharset)
        // body超过MAX_BODY_SIZE的就不采集body
        if (buffer.size() <= MAX_BODY_SIZE) {
          //LJTSLog.i("Interceptor >> Response END save respBody (" + buffer.size() + "-byte body)");
          return respBody
        }
      } catch (e: Throwable) {
      }
    }
    return null
  }

  @Throws(EOFException::class) fun isPlaintext(buffer: Buffer): Boolean {
    return try {
      val prefix = Buffer()
      val byteCount = if (buffer.size() < 64) buffer.size() else 64
      buffer.copyTo(prefix, 0, byteCount)
      for (i in 0..15) {
        if (prefix.exhausted()) {
          break
        }
        if (Character.isISOControl(prefix.readUtf8CodePoint())) {
          return false
        }
      }
      true
    } catch (e: EOFException) {
      false // Truncated UTF-8 sequence.
    }
  }
}