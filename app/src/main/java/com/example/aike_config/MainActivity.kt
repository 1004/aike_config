package com.example.aike_config

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.aike.httpserver.AikeHttpService
import com.aike.httpserver.adapter.calladapter.ASimpleCallBack
import com.aike.lib.config.AIkeConfigCenter
import com.aike.lib.config.api.CdnApi
import com.aike.lib.config.bean.Conifg
import com.aike.lib.config.cache.AikeConfigCacheManager
import com.aike.lib.config.core.AikeConfigNotifyCenter
import com.aike.lib.config.core.IAikeConfigNotifyListener
import retrofit2.Response

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    findViewById<View>(R.id.test1).setOnClickListener {
      AIkeConfigCenter.checkConfig(getJson())
    }
    findViewById<View>(R.id.test2).setOnClickListener {
      val createService = AikeHttpService.createService("main", TestApi::class.java)
      createService.cdnContent("http://10.36.210.8:8080/as/config/query")?.enqueue(object : ASimpleCallBack<Any>() {
        override fun onResponse(response: Response<Any>?, entity: Any?) {
          if (entity != null) {
            Toast.makeText(this@MainActivity, "success", Toast.LENGTH_SHORT).show()
          }
        }

        override fun onError(e: Throwable?) {
          Toast.makeText(this@MainActivity, "failed", Toast.LENGTH_SHORT).show()
        }
      })
    }
    findViewById<TextView>(R.id.test3).text = AIkeConfigCenter.getStringConfig("a")

    AIkeConfigCenter.addConfigChangeListener(object : IAikeConfigNotifyListener {
      override fun onConfigChange(config: Conifg) {
        val content = config.content
        val get = content.get("a");
        findViewById<TextView>(R.id.test3).text = get
      }
    })
  }

  fun getJson(): String {
    return "{\n" +
        "    \"ext\": {\n" +
        "        \"config\": {\n" +
        "            \"id\": 9,\n" +
        "            \"namespance\": \"newhouse\",\n" +
        "            \"version\": \"20210222152522\",\n" +
        "            \"createTime\": \"2021-02-22\",\n" +
        "            \"originalUrl\": \"http://127.0.0.1:5088/asfile/as/newhouse_20210222152522\",\n" +
        "            \"jsonUrl\": \"http://10.36.210.8:8080/as/file/as/test2_20210225103913.json\"\n" +
        "        }\n" +
        "    }\n" +
        "}"
  }
}