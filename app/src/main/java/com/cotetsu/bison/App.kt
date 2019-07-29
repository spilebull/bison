package com.cotetsu.bison

import com.cotetsu.bison.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * DaggerApplication クラス 定義
 *
 * @date 2019-07-29
 * @author spilebull
 */
class App : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}