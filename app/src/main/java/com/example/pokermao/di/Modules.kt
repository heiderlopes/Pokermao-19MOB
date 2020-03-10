package com.example.pokermao.di

import android.content.Context
import com.example.pokermao.api.AuthInterceptor
import com.example.pokermao.api.PokemonService
import com.example.pokermao.repository.PokemonRepository
import com.example.pokermao.repository.PokemonRepositoryImpl
import com.example.pokermao.view.detail.DetailViewModel
import com.example.pokermao.view.form.FormPokemonViewModel
import com.example.pokermao.view.list.ListPokemonsAdapter
import com.example.pokermao.view.list.ListPokemonsViewModel
import com.example.pokermao.view.splash.SplashViewModel
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val viewModule = module {
    factory { ListPokemonsAdapter(get()) }
}

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { ListPokemonsViewModel(get()) }
    viewModel { FormPokemonViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}

val repositoryModule = module {
    single<PokemonRepository> { PokemonRepositoryImpl(get()) }
}

val networkModule = module {
    single { createNetworkClient(get(), get(named("baseUrl"))).create(PokemonService::class.java) }
    single { createOkhttpClientAuth(get()) }
    single<Interceptor> { AuthInterceptor() }
    single(named("baseUrl")) { "https://pokedexdx.herokuapp.com" }
    single { createPicassoAuth(get(), get())}
}


private fun createPicassoAuth(context: Context, client: OkHttpClient): Picasso {
    return Picasso
        .Builder(context)
        .downloader(OkHttp3Downloader(client))
        .build()
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