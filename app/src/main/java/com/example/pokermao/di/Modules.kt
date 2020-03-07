package com.example.pokermao.di

import com.example.pokermao.api.AuthInterceptor
import com.example.pokermao.api.PokemonService
import com.example.pokermao.repository.PokemonRepository
import com.example.pokermao.repository.PokemonRepositoryImpl
import com.example.pokermao.view.splash.SplashViewModel
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
}
val repositoryModule = module {
    single<PokemonRepository> { PokemonRepositoryImpl(get()) }
}

val networkModule = module {
    single<Interceptor> { AuthInterceptor() }
    single { createNetworkClient(get(), get(named("baseUrl"))).create(PokemonService::class.java) }
    single { createOkhttpClientAuth(get()) }
    single (named("baseUrl")) { "https://pokedexdx.herokuapp.com" }
}


private fun createNetworkClient(okHttpClient: OkHttpClient, baseURL: String): Retrofit {
    return Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
private fun createOkhttpClientAuth(authInterceptor: Interceptor): OkHttpClient {
    val builder = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addNetworkInterceptor(StethoInterceptor())
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
    return builder.build()
}