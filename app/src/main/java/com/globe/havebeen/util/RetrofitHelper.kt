package com.globe.havebeen.util

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * Created by baeminsu on 26/08/2018.
 */
class RetrofitHelper {

    companion object {

        const val KAKAO_REST_API = "50b31d2c92f31dffde365def18c0d969"
        const val KAKAO_BASE_URL = "https://dapi.kakao.com"
        const val FIREBASE_KAKAO_FUNCTION_BASE_URL = "https://us-central1-havebeen-f77da.cloudfunctions.net"

        private var retrofit: Retrofit? = null

        fun getKakaoLocalClient(): Retrofit {
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain?): Response {

                    val original = chain?.request()
                    val request = original!!.newBuilder()
                            .header("Authorization", "KakaoAK $KAKAO_REST_API")
                            .header("Accept", "application/json")
                            .build()
                    return chain.proceed(request)
                }
            })
            val client = httpClient.build()
            retrofit = Retrofit.Builder()
                    .baseUrl(KAKAO_BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            return retrofit!!
        }

        fun getKakaoLoginClient(): Retrofit {
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor { chain ->
                val original = chain.request()
                val request = original!!.newBuilder()
                        .header("Content-Type", "application/json")
                        .build()
                chain.proceed(request)
            }
            val client = httpClient.build()
            retrofit = Retrofit.Builder()
                    .baseUrl(FIREBASE_KAKAO_FUNCTION_BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            return retrofit!!


        }
    }
}