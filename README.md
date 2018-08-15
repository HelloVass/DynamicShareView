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
- ZXing（3.x版本）, 懒得介绍系列，将生成二维码的部分封装成了 QRCodeEncoder（放心，边距问题不存在的）
- [transformations](https://github.com/wasabeef/glide-transformations),懒得介绍系列，用来实现各种变换

# Thanks
[超级无敌可爱你妈棒棒糖ok的雾聚dalao](https://fogdong.github.io/)的微博相册日常作为超强有力的数据来源支撑！

> PS:雾聚 dalao 还在找实习中（node方向），有兴趣的童鞋可以py她


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

<p align="center">
<img src="http://7xsq1h.com1.z0.glb.clouddn.com/dynamic_share_image1913513551.jpg" width="33%" height="33%"/>
</p>

## 介绍

这个栗子是基于 FlexboxLayout 实现的不规则图片墙，你也可以仿照 [DefaultRender](https://github.com/HelloVass/DynamicShareView/blob/master/library/src/main/java/info/hellovass/dynamicdrawbitmap/library/core/DefaultIRender.kt) 根据自己的实际业务需求实现自己的渲染器。

## 技术点

- FlexboxLayout 的动态添加子View 技巧
- rx2.X版本的基操
- iconfont 的基操
- 如何正确地手动 measure、layout View
- 自定义 Glide 的变换，通过多重变换实现高斯模糊、水印
- ...


> PS:图片有时候看起来会很奇怪，那是因为动态生成的图片尺寸往往都会很大，所以 `ImageView` 使用矩阵进行了缩放，导致图片看起来被**压缩**了。别慌，之后我写一个 `BigImageView` 就可以正常显示超大图了。


### 最早的版本

根据之前的写过的文章[动态生成分享图片](https://hellovass.info/2017/12/30/%E5%8A%A8%E6%80%81%E7%94%9F%E6%88%90%E5%88%86%E4%BA%AB%E5%9B%BE%E7%89%87/)重构而来，对比一下，就会发现，居然优化了这么多？！啧啧啧
