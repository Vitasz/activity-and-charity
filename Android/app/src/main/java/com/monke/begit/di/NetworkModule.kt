package com.monke.begit.di

import android.webkit.CookieManager
import com.google.gson.GsonBuilder
import com.monke.begit.data.remote.API
import com.monke.begit.data.remote.Constants
import dagger.Module
import dagger.Provides
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookiePolicy

class MyCookieJar(): CookieJar {
    private val cookieStore = HashMap<HttpUrl?, List<Cookie>>()
    private val cookieManager = CookieManager.getInstance()


    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        val host = url.host
        cookieStore[host.toHttpUrlOrNull()] = cookies
        cookieManager.removeAllCookie()
        val cookies1 = cookieStore[host.toHttpUrlOrNull()]
        if (cookies1 != null) {
            for (cookie in cookies1) {
                val cookieString =
                    cookie.name + "=" + cookie.value + "; path=" + cookie.path
                cookieManager.setCookie(cookie.domain, cookieString)
            }
        }
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val host = url.host
        val cookies = cookieStore[host.toHttpUrlOrNull()]
        return cookies ?: ArrayList()
    }
}
val COOKIE_JAR = MyCookieJar()
@Module
class NetworkModule {

    @Provides
    @AppScope
    fun provideRetrofit(): Retrofit {
        //CookieManager.getInstance().
        val okHttpBuilder = OkHttpClient.Builder().cookieJar(COOKIE_JAR)

        CookieManager.getInstance().setAcceptCookie(true)

        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        return Retrofit.Builder().baseUrl(Constants.APIUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpBuilder.build())
            .build()
    }

    @Provides
    @AppScope
    fun provideApi(retrofit: Retrofit): API = retrofit.create(API::class.java)


}