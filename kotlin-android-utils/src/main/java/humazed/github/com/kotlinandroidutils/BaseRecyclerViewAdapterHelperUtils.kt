package humazed.github.com.kotlinandroidutils

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.loadmore.BaseLoadMoreView
import com.chad.library.adapter.base.util.getItemView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import kotlinx.android.extensions.LayoutContainer

/**
 * implements [LayoutContainer] so it can cache the viewHolder see [https://github.com/Kotlin/KEEP/blob/master/proposals/android-extensions-entity-caching.md#view-holder-pattern]
 */
class KBaseViewHolder(view: View) : BaseViewHolder(view), LayoutContainer {
    override val containerView: View = view
}

/**
 * convenient class around BaseQuickAdapter
 * use it in case you need to reuse the adapter
 * Note: Need to add `androidExtensions { experimental = true }`
 */
abstract class BaseAdapter<T>(@LayoutRes layoutResId: Int, items: List<T>) :
        BaseQuickAdapter<T, KBaseViewHolder>(
                layoutResId,
                if (items is MutableList<T>) items else items.toMutableList()
        )

/**
 * convenient method to create adapter
 * use it in case you don't need to reuse the adapter
 * Note: Need to add `androidExtensions { experimental = true }`
 */
fun <T> simpleAdapter(@LayoutRes layoutResId: Int,
                      items: List<T>,
                      map: KBaseViewHolder.(item: T) -> Unit,
                      onItemClick: ((adapter: BaseQuickAdapter<*, *>, position: Int, item: T) -> Unit)? = null
): BaseAdapter<T> {
    return object : BaseAdapter<T>(layoutResId, items.toMutableList()) {
        override fun convert(holder: KBaseViewHolder, item: T) {
            holder.map(item)
        }
    }.apply {
        onItemClick { adapter, position, item ->
            onItemClick?.invoke(adapter, position, item)
        }
    }
}


/**
 * convenient method to create adapter
 * use it in case you don't need to reuse the adapter
 * Note: Need to add `androidExtensions { experimental = true }`
 */
fun <T> simpleAdapter(@LayoutRes layoutResId: Int,
                      items: List<T>,
                      map: KBaseViewHolder.(item: T) -> Unit,
                      onItemClick: ((position: Int, item: T) -> Unit)? = null
): BaseAdapter<T> =
        simpleAdapter(layoutResId, items, map,
                { _, position, item -> onItemClick?.invoke(position, item) })

/**
 * convenient method to create adapter
 * use it in case you don't need to reuse the adapter
 * Note: Need to add `androidExtensions { experimental = true }`
 */
fun <T> simpleAdapter(@LayoutRes layoutResId: Int,
                      items: List<T>,
                      map: KBaseViewHolder.(item: T) -> Unit,
                      onItemClick: ((item: T) -> Unit)? = null
): BaseAdapter<T> =
        simpleAdapter(layoutResId, items, map, { _, item -> onItemClick?.invoke(item) })

/**
 * convenient method to set the simple adapter to RecyclerView
 * use it in case you don't need to reuse the adapter
 * Note: Need to add `androidExtensions { experimental = true }`
 */
fun <T> RecyclerView?.setSimpleAdapter(@LayoutRes layoutResId: Int,
                                       items: List<T>,
                                       map: KBaseViewHolder.(item: T) -> Unit,
                                       onItemClick: ((adapter: BaseQuickAdapter<*, *>, position: Int, item: T) -> Unit)? = null
): BaseAdapter<T> =
        simpleAdapter(layoutResId, items, map, onItemClick).also { this?.adapter = it }

/**
 * convenient method to set the simple adapter to RecyclerView
 * use it in case you don't need to reuse the adapter
 * Note: Need to add `androidExtensions { experimental = true }`
 */
fun <T> RecyclerView?.setSimpleAdapter(@LayoutRes layoutResId: Int,
                                       items: List<T>,
                                       map: KBaseViewHolder.(item: T) -> Unit,
                                       onItemClick: ((position: Int, item: T) -> Unit)? = null
): BaseAdapter<T> =
        setSimpleAdapter(layoutResId, items, map,
                { _, position, item -> onItemClick?.invoke(position, item) })

/**
 * convenient method to set the simple adapter to RecyclerView
 * use it in case you don't need to reuse the adapter
 * Note: Need to add `androidExtensions { experimental = true }`
 */
fun <T> RecyclerView?.setSimpleAdapter(@LayoutRes layoutResId: Int,
                                       items: List<T>,
                                       map: KBaseViewHolder.(item: T) -> Unit,
                                       onItemClick: ((item: T) -> Unit)? = null
): BaseAdapter<T> =
        setSimpleAdapter(layoutResId, items, map, { _, item -> onItemClick?.invoke(item) })


/**
 * convenient method to add onItemClick
 */
fun <T, K : BaseViewHolder> BaseQuickAdapter<T, K>.onItemClick(
        onItemClick: (adapter: BaseQuickAdapter<*, *>, position: Int, item: T) -> Unit
): BaseQuickAdapter<T, K> {
    setOnItemClickListener { adapter, _, position ->
        onItemClick(adapter, position, data[position])
    }
    return this
}

/**
 * convenient method to add onItemClick
 */
fun <T, K : BaseViewHolder> BaseQuickAdapter<T, K>.onItemClick(
        onItemClick: (position: Int, item: T) -> Unit
): BaseQuickAdapter<T, K> =
        onItemClick { _, position, item -> onItemClick(position, item) }

/**
 * convenient method to add onItemClick
 */
fun <T, K : BaseViewHolder> BaseQuickAdapter<T, K>.onItemClick(
        onItemClick: (item: T) -> Unit
): BaseQuickAdapter<T, K> =
        onItemClick { _, item -> onItemClick(item) }


class SimpleLoadMoreView : BaseLoadMoreView() {
    override fun getRootView(parent: ViewGroup): View =
            parent.getItemView(R.layout.quick_view_load_more)

    override fun getLoadingView(holder: BaseViewHolder): View =
            holder.getView(R.id.load_more_loading_view)

    override fun getLoadComplete(holder: BaseViewHolder): View =
            holder.getView(R.id.load_more_load_complete_view)

    override fun getLoadEndView(holder: BaseViewHolder): View =
            holder.getView(R.id.load_more_load_end_view)

    override fun getLoadFailView(holder: BaseViewHolder): View =
            holder.getView(R.id.load_more_load_fail_view)
}