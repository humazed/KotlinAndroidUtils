package humazed.github.com.kotlinandroidutils

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder


fun <T, K : BaseViewHolder> BaseQuickAdapter<T, K>.onItemClick(listener: (position: Int, item: T) -> Unit) {
    setOnItemClickListener { _, _, position ->
        listener(position, data[position])
    }
}
