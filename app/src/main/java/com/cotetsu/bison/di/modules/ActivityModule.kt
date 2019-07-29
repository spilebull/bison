package com.cotetsu.bison.di.modules

import com.cotetsu.bison.view.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Activity クラス 注入定義
 *
 * @date 2019-07-29
 * @author spilebull
 */
@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}