package humazed.github.com.kotlinandroidutils

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


data class Tab(
    val fragment: Fragment,
    @StringRes val titleRes: Int? = null,
    @DrawableRes val iconRes: Int? = null
)

// region the legacy ViewPager
fun AppCompatActivity.setupTabs(tabLayout: TabLayout?, viewPager: ViewPager?, tabs: List<Tab>) {
    viewPager?.adapter = SectionsPagerAdapter(this, supportFragmentManager, tabs)
    tabLayout?.setupWithViewPager(viewPager)

    // setup Tab Icons
    if (tabs[0].iconRes != null) // if the first tab has icon then the rest will have one
        tabs.forEachIndexed { index, tab -> tabLayout?.getTabAt(index)?.setIcon(tab.iconRes!!) }
}

fun Fragment.setupTabs(tabLayout: TabLayout?, viewPager: ViewPager?, tabs: List<Tab>) {
    context?.let { context ->
        viewPager?.adapter = SectionsPagerAdapter(context, childFragmentManager, tabs)
        tabLayout?.setupWithViewPager(viewPager)

        // setup Tab Icons
        if (tabs[0].iconRes != null) // if the first tab has icon then the rest will have one
            tabs.forEachIndexed { index, tab -> tabLayout?.getTabAt(index)?.setIcon(tab.iconRes!!) }
    }
}


class SectionsPagerAdapter(val context: Context, fm: FragmentManager, private val tabs: List<Tab>) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment = tabs[position].fragment
    override fun getPageTitle(position: Int): CharSequence? =
        tabs[position].titleRes?.let { context.getString(it) }

    override fun getCount() = tabs.size
}
// endregion the legacy ViewPager

// region the ViewPager2
fun AppCompatActivity.setupTabs(tabLayout: TabLayout?, viewPager: ViewPager2?, tabs: List<Tab>) {
    viewPager?.adapter = SectionsPager2Adapter(this, tabs)

    if (tabLayout != null && viewPager != null) {
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tabs[position].apply {
                tab.text = titleRes?.let { getString(it) } ?: ""
                iconRes?.let { tab.setIcon(it) }
            }
        }.attach()
    }
}

fun Fragment.setupTabs(tabLayout: TabLayout?, viewPager: ViewPager2?, tabs: List<Tab>) {
    viewPager?.adapter = SectionsPager2Adapter(this, tabs)

    if (tabLayout != null && viewPager != null) {
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tabs[position].apply {
                tab.text = titleRes?.let { getString(it) } ?: ""
                iconRes?.let { tab.setIcon(it) }
            }
        }.attach()
    }
}

private class SectionsPager2Adapter(
    fm: FragmentManager,
    lifecycle: Lifecycle,
    private val tabs: List<Tab>
) : FragmentStateAdapter(fm, lifecycle) {
    constructor(fragment: Fragment, tabs: List<Tab>) : this(
        fragment.childFragmentManager,
        fragment.lifecycle,
        tabs
    )

    constructor(activity: FragmentActivity, tabs: List<Tab>) : this(
        activity.supportFragmentManager,
        activity.lifecycle,
        tabs
    )

    override fun createFragment(position: Int): Fragment = tabs[position].fragment

    override fun getItemCount() = tabs.size
}
// endregion the ViewPager2


