package com.codeancy.metroui.ads

interface InterstitialAdController {
    fun preload()
    fun show(onFinish: () -> Unit)
}