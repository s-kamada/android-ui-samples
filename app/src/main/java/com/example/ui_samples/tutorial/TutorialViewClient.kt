package com.example.ui_samples.tutorial

import android.app.Activity
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewTreeObserver
import androidx.annotation.LayoutRes
import androidx.core.view.ViewPropertyAnimatorListenerAdapter

class TutorialViewClient(
    activity: Activity
) {

    interface Listener {

        fun onDismissed()
    }

    private val container: FrameLayout = FrameLayout(activity)
    private val tutorialView: TutorialView = TutorialView(activity)
    private lateinit var listener: Listener

    init {
        // add tutorial view to screen in advance
        val content = activity.window.decorView.findViewById<ViewGroup>(android.R.id.content)
        content.addView(container, MATCH_PARENT, MATCH_PARENT)
        container.addView(tutorialView, MATCH_PARENT, MATCH_PARENT)
        container.visibility = View.GONE
        container.alpha = 0F
    }

    /**
     * Rectを載せる対象のViewを設定
     * TODO: ViewActions対象のviewをrecyclerViewにも複数もてるようにする、UQ appで試してみる(このアプリだとキーボードが上がった都合なのかわからない
     */
    fun on(view: View): ViewActions {
        return ViewActions(this, view)
    }

    fun show(): TutorialViewClient {
        container.visibility = View.VISIBLE
        ViewCompat.animate(container)
            .alpha(1f)
            .setDuration(container.resources.getInteger(android.R.integer.config_longAnimTime).toLong())
            .start()

        container.setOnClickListener {
            dismiss()
        }

        return this
    }

    private fun dismiss() {
        val duration = container.resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()

        ViewCompat.animate(container)
            .alpha(0f)
            .setDuration(duration)
            .setListener(object : ViewPropertyAnimatorListenerAdapter() {
                override fun onAnimationEnd(view: View) {
                    super.onAnimationEnd(view)
                    val parent = view.parent
                    if (parent is ViewGroup) {
                        parent.removeView(view)
                    }
                    listener.onDismissed()
                }
            }).start()
    }

    fun setOnClickListener(listener: Listener): TutorialViewClient {
        this.listener = listener
        return this
    }

    fun setContentView(@LayoutRes content: Int): TutorialViewClient {
        val child: View =
            LayoutInflater.from(tutorialView.context).inflate(content, container, false)
        container.addView(child, MATCH_PARENT, MATCH_PARENT)
        return this
    }

    companion object {

        fun from(activity: Activity): TutorialViewClient {
            return TutorialViewClient(activity)
        }

        class ViewActions(
            private val client: TutorialViewClient,
            val view: View
        ) {

            fun on(view: View): ViewActions {
                return client.on(view)
            }

            fun show(): TutorialViewClient {
                return client.show()
            }

            /**
             * チュートリアルとしてハイライトする角丸長方形を追加する
             * @param rectCornerRadius 角丸長方形の角丸の半径
             */
            fun addRoundRect(rectCornerRadius: Float): ViewActionsEditor {
                view.viewTreeObserver.addOnPreDrawListener(object :
                    ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        addRoundRectOnView(rectCornerRadius)
                        view.viewTreeObserver.removeOnPreDrawListener(this)
                        return false
                    }
                })
                return ViewActionsEditor(this)
            }

            private fun addRoundRectOnView(rectCornerRadius: Float) {
                val rect = Rect()
                view.getGlobalVisibleRect(rect)
                val padding = 40

                val x = rect.left - padding
                val y = rect.top - padding
                val width = rect.width() + 2 * padding
                val height = rect.height() + 2 * padding

                val roundRect = RoundRect(x, y, width, height, rectCornerRadius)
                client.tutorialView.addRoundRect(roundRect)
                client.tutorialView.postInvalidate()
            }
        }

        open class ViewActionsEditor(
            private val viewActions: ViewActions
        ) {
            fun show(): TutorialViewClient {
                return viewActions.show()
            }
        }
    }
}
