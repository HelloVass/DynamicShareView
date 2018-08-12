# DynamicShareView
基于 rxjava 实现的动态渲染**分享图**

# Feature
- 工作线程渲染图片
- 将生成的分享图保存到本地，方便之后**第三方分享sdk**使用
- 无回调地狱，只需要实现一个 RenderDelegate 即可

# 开源库
- RxJava&RxAndroid（2.x版本），懒得介绍系列
- Glide（4.x版本），懒得介绍系列
- [FlexboxLayout](https://github.com/google/flexbox-layout), 在 sample 用来实现不规则图片墙的效果

# 使用
```kotlin

// 传入自己的渲染器即可，这里的 FlexRenderDelegate 是基于 FlexboxLayout 实现的渲染器
        ShareViewImpl(this, FlexRenderDelegate(this))
                .getLocalPath()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { localImagePath ->
                    // TODO: 这里可以调用第三方分享SDK将本地生成好的图片文件分享出去
                }
```


# 效果图
> PS: 基于 FlexboxLayout 实现了一个不规则的照片墙，你也可以仿照 [FlexRenderDelegate](https://github.com/HelloVass/DynamicShareView/blob/master/library/src/main/java/info/hellovass/dynamicdrawbitmap/library/FlexRenderDelegate.kt)
> 的示例，根据自己的实际业务需求实现自己的渲染器。



## 动态渲染 4 张图

<p align="center">
<img src="http://7xsq1h.com1.z0.glb.clouddn.com/%E5%8A%A8%E6%80%81%E6%B8%B2%E6%9F%934%E5%BC%A0%E5%9B%BE%E7%89%87%E6%97%B6%E5%80%99%E7%9A%84%E6%A0%B7%E5%AD%90.png" width="33%" height="33%"/>
</p>


## 动态渲染 6 张图

<p align="center">
<img src="http://7xsq1h.com1.z0.glb.clouddn.com/%E5%8A%A8%E6%80%81%E6%B8%B2%E6%9F%934%E5%BC%A0%E5%9B%BE%E7%89%87%E6%97%B6%E5%80%99%E7%9A%84%E6%A0%B7%E5%AD%90.png" width="33%" height="33%"/>
</p>


## 动态渲染 7 张图

<p align="center">
<img src="http://7xsq1h.com1.z0.glb.clouddn.com/%E5%8A%A8%E6%80%81%E6%B8%B2%E6%9F%937%E5%BC%A0%E5%9B%BE%E7%89%87%E6%97%B6%E5%80%99%E7%9A%84%E6%A0%B7%E5%AD%90.gif" width="33%" height="33%"/>
</p>

> PS:因为 7 张图的高度已经超过了屏幕的高度，所以用一个 ScrollView 包裹起来，可以上下滑动，以看到完整的图片


## More

实际上，我实现的 FlexRenderDelegate 可以支持 7 种布局，也就是1~7张图片时会渲染不同的布局，有兴趣的话可以看看实现，这里就不一一截图演示了，毕竟我懒。



### 通过计算属性实现多种布局

```kotlin

internal val FlexRenderDelegate.coverCount: Int
    get() {
        return Math.min(FlexRenderDelegate.MAX_SIZE, repo.covers().size)
    }

internal val FlexRenderDelegate.layout: IntArray
    get() =
        when (coverCount) {
            1 -> {
                intArrayOf(1)
            }
            2 -> {
                intArrayOf(1, 1)
            }
            3 -> {
                intArrayOf(1, 2)
            }
            4 -> {
                intArrayOf(1, 3)
            }
            5 -> {
                intArrayOf(1, 3, 1)
            }
            6 -> {
                intArrayOf(1, 3, 2)
            }
            else -> {
                intArrayOf(1, 3, 2, 1)
            }
        }
```

最多支持 7 种布局，猜也能猜到 MAX_SIZE 的值，然后，根据封面数返回不同的布局（数组完事）。


### 最早的版本
根据之前的写过的文章[动态生成分享图片](https://hellovass.info/2017/12/30/%E5%8A%A8%E6%80%81%E7%94%9F%E6%88%90%E5%88%86%E4%BA%AB%E5%9B%BE%E7%89%87/)重构而来，对比一下，就会发现，居然优化了这么多？！啧啧啧
