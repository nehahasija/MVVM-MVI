package com.leinardi.template.rocketserver.networking

import android.app.AppComponentFactory
import android.content.Context
import android.os.Looper
import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.ResponseField
import com.apollographql.apollo.cache.normalized.CacheKey
import com.apollographql.apollo.cache.normalized.CacheKeyResolver
import com.apollographql.apollo.cache.normalized.NormalizedCacheFactory
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCache
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.modules.ApplicationContextModule
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import javax.inject.Inject

class RocketServerApi @Inject constructor(context: Context) {
    var mContext: Context = context
     var cacheKeyResolver: CacheKeyResolver
    lateinit var apolloClient: ApolloClient

    var memoryFirstThenSqlCacheFactory: NormalizedCacheFactory<LruNormalizedCache>


    init {
        val sqlCacheFactory = SqlNormalizedCacheFactory(mContext, "template_cache")
        memoryFirstThenSqlCacheFactory = LruNormalizedCacheFactory(
            EvictionPolicy.builder().maxSizeBytes((10 * 1024 * 1024).toLong()).build()
        ).chain(sqlCacheFactory)
        cacheKeyResolver = object : CacheKeyResolver() {
            override fun fromFieldRecordSet(
                field: ResponseField,
                recordSet: Map<String, Any>
            ): CacheKey {
                val id = recordSet["id"] as? String
                if (id != null)
                    return CacheKey.from(id)
                return CacheKey.NO_KEY
            }

            override fun fromFieldArguments(
                field: ResponseField,
                variables: Operation.Variables
            ): CacheKey {
                val id = field.resolveArgument("id", variables) as? String
                if (id != null) return CacheKey.from(id)
                return CacheKey.NO_KEY
            }
        }
    }


    fun getApolloclient(): ApolloClient {
        check(Looper.myLooper() == Looper.getMainLooper()) {
            "Only the main thread can get the apolloClient instance"
        }
        val okHttpClient = OkHttpClient.Builder().build()

        if (this::apolloClient.isInitialized) {
            return apolloClient
        } else {
            apolloClient = ApolloClient.builder()
                .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com")
                .okHttpClient(okHttpClient)
                .normalizedCache(memoryFirstThenSqlCacheFactory, cacheKeyResolver)
                .defaultResponseFetcher(ApolloResponseFetchers.CACHE_FIRST)
                .build()

            return apolloClient
        }


    }
}