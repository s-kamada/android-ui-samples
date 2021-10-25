package com.example.ui_samples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.ui_samples.databinding.ActivityMainBinding
import com.example.ui_samples.tutorial.TutorialViewClient
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val disposables = CompositeDisposable()

    /** ref: viewmodel生成の簡単化・共通化パターン(Factory) https://suihan74.github.io/posts/2020/09_14_01_provide_view_model/ */
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val adapter = GroupAdapter<GroupieViewHolder>()
        binding.propertiesRecyclerView.adapter = adapter

        listOf("cell0").forEach {
            adapter.add(PropertyItem(viewModel, it))
        }

        // TODO: 試す
        viewModel.tutorialButtonTapped
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                TutorialViewClient.from(this)
                    .setContentView(R.layout.view_tutorial_content)
                    .on(binding.previewTextLabel)
                    .on(binding.dummyText)
                    .addRoundRect(8f)
                    .show()
                    .setOnClickListener(object : TutorialViewClient.Listener {
                        override fun onDismissed() {
                            Timber.d("clicked in listener")
                        }
                    })
            }.addTo(disposables)

//        binding.tutorialButton.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onClick(p0: View?) {
        Timber.d("tapped onclick")
    }
}
