package auntschool.think.com.mynettest.net

import com.top.kjb.net.ApiService
import com.top.kjb.net.GsonDConverterFactory
import retrofit2.Retrofit

/**
 * Created by MaiBenBen on 2018/12/24.
 */
object RetrofitManager {

    var service = getRetrofit().create(ApiService::class.java)

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(UrlConstant.BASE_URL) // 设置 网络请求 Url
                .addConverterFactory(GsonDConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build()
    }

}