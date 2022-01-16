package com.harman.imageprocessingmvvm.dependencyinjection

import com.harman.imageprocessingmvvm.viewmodels.EditImageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { EditImageViewModel(editImageRepository = get()) }
}