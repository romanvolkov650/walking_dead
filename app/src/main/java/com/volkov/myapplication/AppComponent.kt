package com.volkov.myapplication

import dagger.Component

@Component(
    modules = [
        DataModule::class
    ]
)
interface AppComponent {
    fun inject(app: AppImpl)
}