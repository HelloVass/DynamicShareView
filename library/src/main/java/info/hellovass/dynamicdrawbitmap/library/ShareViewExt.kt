package info.hellovass.dynamicdrawbitmap.library

val ShareView.imageCount: Int
    get() {
        return Math.min(getAdapter().getCount(), ShareView.MAX_COUNT)
    }

val ShareView.rowCount: Int
    get() {
        if (imageCount >= 1) {
            return 2
        }
        return 1
    }

val ShareView.columnCount: Int
    get() {
        if (imageCount >= 1) {

        }
    }