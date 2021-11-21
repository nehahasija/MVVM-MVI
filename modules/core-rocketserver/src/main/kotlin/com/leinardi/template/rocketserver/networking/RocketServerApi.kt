package com.leinardi.template.rocketserver.networking

import android.content.Context
import android.os.Looper
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
import okhttp3.OkHttpClient

class RocketServerApi {

    lateinit var cacheKeyResolver: CacheKeyResolver
    lateinit var memoryFirstThenSqlCacheFactory: NormalizedCacheFactory<LruNormalizedCache>


    fun getCacheKeyResolver(context: Context) {
        val sqlCacheFactory = SqlNormalizedCacheFactory(context, "template_cache")
        memoryFirstThenSqlCacheFactory = LruNormalizedCacheFactory(
            EvictionPolicy.builder().maxSizeBytes((10 * 1024 * 1024).toLong()).build()
        ).chain(sqlCacheFactory)
        cacheKeyResolver = object : CacheKeyResolver() {
            override fun fromFieldRecordSet(field: ResponseField, recordSet: Map<String, Any>): CacheKey {
                val id = recordSet["id"] as? String
                if (id != null)
                    return CacheKey.from(id)
                return CacheKey.NO_KEY
            }

            override fun fromFieldArguments(field: ResponseField, variables: Operation.Variables): CacheKey {
                val id = field.resolveArgument("id", variables) as? String
                if (id != null) return CacheKey.from(id)
                return CacheKey.NO_KEY
            }
        }
    }


    fun getApolloClient(): ApolloClient {

        check(Looper.myLooper() == Looper.getMainLooper()) {
            "Only the main thread can get the apolloClient instance"
        }
        val okHttpClient = OkHttpClient.Builder().build()

        if (this::cacheKeyResolver.isInitialized) {

            return ApolloClient.builder()
                .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com")
                .okHttpClient(okHttpClient)
                .normalizedCache(memoryFirstThenSqlCacheFactory, cacheKeyResolver)
                .defaultResponseFetcher(ApolloResponseFetchers.CACHE_FIRST)
                .build()
        } else {
            return ApolloClient.builder()
                .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com")
                .okHttpClient(okHttpClient)
                .build()
        }


    }
}