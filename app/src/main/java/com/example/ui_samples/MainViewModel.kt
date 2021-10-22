package com.example.ui_samples

import android.util.Log
import androidx.lifecycle.ViewModel
import timber.log.Timber

class MainViewModel: ViewModel() {

    fun onTextChanged(text: CharSequence) {
        Timber.d("text changed: $text")
        Log.d("hoge", "text changed: $text")
    }

    /**
     * チュートリアルを表示する
     * - テキストを表示するためのレイアウト受付
     * - ハイライトするrectを受け付ける
     */
    fun showTutorial() {

    }
}
