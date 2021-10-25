package com.example.ui_samples.tutorial

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewTreeObserver
import timber.log.Timber

class TutorialViewClient(
    private val activity: Activity
) {
    private val container: FrameLayout
    private val tutorialView: TutorialView

    init {
        container = FrameLayout(activity)
        tutorialView = TutorialView(activity)

        // add tutorial view to screen in advance
        val content = activity.window.decorView.findViewById<ViewGroup>(android.R.id.content)
        content.addView(container, MATCH_PARENT, MATCH_PARENT)
        container.addView(tutorialView, MATCH_PARENT, MATCH_PARENT)
//        val inflatedLayout = content.getChildAt(0)
//        this.fitsSystemWindows = inflatedLayout != null && inflatedLayout.fitsSystemWindows
        container.visibility = View.GONE
        container.alpha = 0F
    }

    /**
     * Rectを載せる対象のViewを設定
     */
    public fun on(view: View): ViewActions {
        return ViewActions(this, view)
    }

    public fun show(): TutorialViewClient {
        container.visibility = View.VISIBLE
        ViewCompat.animate(container)
            .alpha(1f)
            .setDuration(container.resources.getInteger(android.R.integer.config_longAnimTime).toLong())
            .start()
        // container.setOnClickListener((v) -> { dismiss() })
        return this
    }

    // TODO: チュートリアルの文字とかを載せるviewを載せる
//    public fun setContentView(): TutorialViewClient {
//        return this
//    }



    companion object {
        public fun from(activity: Activity): TutorialViewClient {
            return TutorialViewClient(activity)
        }

        public class ViewActions(
            val client: TutorialViewClient,
            val view: View,
            val settings: ViewActionSettings = ViewActionSettings()
//            val fileSystemWindow: Boolean
        ) {
            var n = 0

            public fun on(view: View): ViewActions {
                return client.on(view)
            }

            public fun show(): TutorialViewClient {
                return client.show()
            }

            /**
             * チュートリアルとしてハイライトする角丸長方形を追加する
             * @param rectCornerRadius 角丸長方形の角丸の半径
             */
            public fun addRoundRect(rectCornerRadius: Int): ViewActionsEditor {
                view.viewTreeObserver.addOnPreDrawListener(object :
                    ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        n += 1
                        Timber.d("addonpredrawlistener: ${n}")

                        addRoundRectOnView(rectCornerRadius)
                        view.viewTreeObserver.removeOnPreDrawListener(this)
                        return false
                    }
                })
                return ViewActionsEditor(this)
            }

            private fun addRoundRectOnView(rectCornerRadius: Int) {
                val rect = Rect()
                view.getGlobalVisibleRect(rect)
                val padding = 40

                val x = rect.left - padding
                val y = rect.top - padding// - getStatusBarOffset()
                val width = rect.width() + 2 * padding
                val height = rect.height() + 2 * padding

                val roundRect = RoundRect(x, y, width, height)
                client.tutorialView.addRoundRect(roundRect)
//                addClickableView(roundRect, settings.onClickListener, additionalRadiusRatio)
                client.tutorialView.postInvalidate()
            }
        }

        open class ViewActionsEditor (
            protected val viewActions: ViewActions
        ) {
            public fun on(view: View): ViewActions {
                return viewActions.on(view)
            }

            public fun show(): TutorialViewClient {
                return viewActions.show()
            }

//            open fun onClick(onClickListener: View.OnClickListener?): ShapeViewActionsEditor? {
//                viewActions.settings.onClickListener = onClickListener
//                return this
//            }
        }

        data class ViewActionSettings(
            private val animated: Boolean = true,
            private val withBorder: Boolean = false,
            private var onClickListener: View.OnClickListener? = null,
            private val delay: Int = 0,
            private val duration: Int = 300
        )
    }
}
