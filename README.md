DynamicShareView

基于 rxjava 实现的动态渲染分享图

Feature

- 工作线程渲染图片
- 将生成的分享图保存到本地，方便之后第三方分享sdk使用
- 无回调地狱，只需要实现一个 RenderDelegate 即可

开源库

- RxJava&RxAndroid（2.x版本），懒得介绍系列
- Glide（4.x版本），懒得介绍系列
- FlexboxLayout, 在 sample 用来实现不规则图片墙的效果

效果图

PS: 基于 FlexboxLayout 实现了一个不规则的照片墙，你也可以仿照 FlexRenderDelegate

的示例，根据自己的实际业务需求实现自己的渲染器。



动态渲染 4 张图





动态渲染 6 张图





动态渲染 7 张图



More

实际上，我实现的 FlexRenderDelegate 可以支持7种布局，也就是1~7张图片时会渲染不同的布局，有兴趣的话可以看看实现，这里就不一一截图演示了，毕竟我懒。



通过计算属性实现

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

最多支持 7 种布局，猜也能猜到 MAX_SIZE 的值，然后，根据封面数返回不同的布局（数组完事）。
