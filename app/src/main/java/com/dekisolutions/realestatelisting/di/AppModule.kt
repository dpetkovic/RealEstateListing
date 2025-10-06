package com.dekisolutions.realestatelisting.di

import com.dekisolutions.realestatelisting.data.api.PropertyApiService
import com.dekisolutions.realestatelisting.data.repository.PropertyRepositoryImpl
import com.dekisolutions.realestatelisting.domain.contracts.PropertyRepository
import com.dekisolutions.realestatelisting.domain.usecase.GetAllPropertiesUseCase
import com.dekisolutions.realestatelisting.domain.usecase.GetPropertyByIdUseCase
import com.dekisolutions.realestatelisting.vw.DetailViewModel
import com.dekisolutions.realestatelisting.vw.ListingViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    // Network dependencies
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://gsl-apps-technical-test.dignp.com/")
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    single { get<Retrofit>().create(PropertyApiService::class.java) }

    // Repository
    single<PropertyRepository> { PropertyRepositoryImpl(get()) }

    // Use Cases
    single { GetAllPropertiesUseCase(get()) }
    single { GetPropertyByIdUseCase(get()) }

    // ViewModels
    viewModel { ListingViewModel(get()) }
    viewModel { DetailViewModel(get(), get()) }
}
