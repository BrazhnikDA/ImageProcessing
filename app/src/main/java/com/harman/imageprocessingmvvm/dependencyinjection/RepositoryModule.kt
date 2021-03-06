package com.harman.imageprocessingmvvm.dependencyinjection

import com.harman.imageprocessingmvvm.repositories.EditImageRepository
import com.harman.imageprocessingmvvm.repositories.EditImageRepositoryImpl
import com.harman.imageprocessingmvvm.repositories.SavedImagesRepository
import com.harman.imageprocessingmvvm.repositories.SavedImagesRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Object Factory
 */
val repositoryModule = module {
    factory<EditImageRepository> { EditImageRepositoryImpl(androidContext()) }
    factory<SavedImagesRepository> { SavedImagesRepositoryImpl(androidContext()) }
}