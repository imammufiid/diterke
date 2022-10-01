package com.mufiid.locationapi.di

import com.mufiid.locationapi.data.repository.LocationApiRepositoryImpl
import com.mufiid.locationapi.data.source.network.LocationService
import com.mufiid.locationapi.domain.repository.LocationApiRepository
import org.koin.dsl.module

object FeatureLocationApiModule {
    fun modules() = module {
        single { LocationService.build() }
        single<LocationApiRepository> { LocationApiRepositoryImpl(get()) }
    }
}