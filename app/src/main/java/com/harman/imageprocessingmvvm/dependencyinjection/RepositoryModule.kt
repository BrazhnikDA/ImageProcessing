package com.harman.imageprocessingmvvm.dependencyinjection

import com.harman.imageprocessingmvvm.repositories.EditImageRepository
import com.harman.imageprocessingmvvm.repositories.EditImageRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory<EditImageRepository> { EditImageRepositoryImpl(androidContext()) }
}