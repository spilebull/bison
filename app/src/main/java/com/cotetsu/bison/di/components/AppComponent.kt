package com.cotetsu.bison.di.components

import com.cotetsu.bison.App
import com.cotetsu.bison.di.modules.ActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * AndroidInjector で inject(T) 定義（`App`で使用）
 *
 * @date 2019-07-29
 * @author spilebull
 */
@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityModule::class
])
interface AppComponent : AndroidInjector<App> {
    override fun inject(application: App)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }
}