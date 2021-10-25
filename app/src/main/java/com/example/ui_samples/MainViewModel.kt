package com.example.ui_samples

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.ui_samples.tutorial.TutorialViewClient
import com.example.ui_samples.utils.Signal
import io.reactivex.rxjava3.subjects.PublishSubject
import timber.log.Timber

class MainViewModel: ViewModel() {

    val tutorialButtonTapped = PublishSubject.create<Signal>()

    fun onTextChanged(text: CharSequence) {
        Timber.d("text changed: $text")
        Log.d("hoge", "text changed: $text")
        tutorialButtonTapped.onNext(Signal)
    }

    /**
     * チュートリアルを表示する
     * - テキストを表示するためのレイアウト受付
     * - ハイライトするrectを受け付ける
     */
    // Activityを回す、もしくはfragmentにこの処理を回す
    fun showTutorial(v: View) {
//        TutorialViewClient.from()
//        tutorialButtonTapped.onNext(Signal)
        Timber.d("tapped viewmodel")

    }
}
