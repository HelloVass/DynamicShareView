package info.hellovass.dynamicdrawbitmapsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import info.hellovass.dynamicdrawbitmap.library.core.DefaultIRender
import info.hellovass.dynamicdrawbitmap.library.core.ShareViewImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.acitivty_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivty_main)
        initData()
    }

    private fun initData() {

        ShareViewImpl(this, DefaultIRender(this))
                .getLocalPath()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { localImagePath ->

                    Glide.with(this)
                            .load(File(localImagePath))
                            .into(ivTest)
                }
    }
}