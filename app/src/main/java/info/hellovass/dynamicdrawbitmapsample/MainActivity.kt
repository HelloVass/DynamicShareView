package info.hellovass.dynamicdrawbitmapsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import info.hellovass.dynamicdrawbitmap.library.FlexRenderDelegate
import info.hellovass.dynamicdrawbitmap.library.ShareViewImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.acitivty_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivty_main)
        initData()
    }

    private fun initData() {

        ShareViewImpl(this, FlexRenderDelegate(this))
                .getLocalPath()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { localImagePath ->
                    bivTest.loadImage(localImagePath)
                }
    }
}