package `in`.test.pro.beta.app.extensions

import `in`.test.pro.beta.app.MyApplication
import `in`.test.pro.beta.app.base.BaseActivity

fun BaseActivity.viewComponent() =
    (application as MyApplication).dataComponent!!.getViewComponentBuilder()
        .context(this)
        .build()
