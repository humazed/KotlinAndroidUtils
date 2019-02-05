package humazed.github.com.kotlinandroidutils

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout


fun AppCompatActivity.setupTabs(tabLayout: TabLayout, viewPager: ViewPager, tabs: List<Tab>) {
    viewPager.adapter = SectionsPagerAdapter(this, supportFragmentManager, tabs)
    tabLayout.setupWithViewPager(viewPager)
    // setup Tab Icons
    tabs.forEachIndexed { index, tab -> tabLayout.getTabAt(index)?.setIcon(tab.iconRes) }
}

fun Fragment.setupTabs(tabLayout: TabLayout, viewPager: ViewPager, tabs: List<Tab>) {
    (requireActivity() as AppCompatActivity).setupTabs(tabLayout, viewPager, tabs)
}

data class Tab(@StringRes val nameRes: Int, @DrawableRes val iconRes: Int, val fragment: Fragment)

class SectionsPagerAdapter(val context: Context, fm: FragmentManager, val tabs: List<Tab>) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment? = tabs[position].fragment
    override fun getPageTitle(position: Int): CharSequence = context.getString(tabs[position].nameRes)
    override fun getCount() = tabs.size
}
