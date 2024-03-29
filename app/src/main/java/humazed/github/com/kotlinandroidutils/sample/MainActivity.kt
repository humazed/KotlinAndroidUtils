package humazed.github.com.kotlinandroidutils.sample

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import humazed.github.com.kotlinandroidutils.*
import humazed.github.com.kotlinandroidutils.sample.databinding.ActivityMainBinding
import humazed.github.com.kotlinandroidutils.sample.databinding.RowSimpleTextBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.imagePickerBt.setOnClickListener { start<ImagePickerTestActivity> { } }

        d { "Locale.getDefault().language = ${Locale.getDefault().language}" }
        d { "getLanguage() = ${getLanguage()}" }

        codifiedTest()

        logWithTimber()

        testCodified()

        setupRecycler()

        binding.logBt.setOnClickListener {
            d { "MainActivity.onCreate.setOnClickListener" }
            try {
                throw NullPointerException("12345")
            } catch (e: Exception) {
                er(e) { "MainActivity.onCreate.setOnClickListener" }
            }
        }

        binding.testImageView.load("https://picsum.photos/200/300") {
            default(R.drawable.ic_launcher_background)
                centerCrop()
        }
    }

    private fun testCodified() {
        d { "Language::class.tryDecode(\" ar \") = ${Language::class.tryDecode("ar")}" }
        d { "Language::class.tryDecode(\" lol \") = ${Language::class.tryDecode("lol")}" }
    }

    private fun logWithTimber() {
        var i = 0

        d { "log ${i++}" }
        i { "log ${i++}" }
        w { "log ${i++}" }
        e { "log ${i++}" }
        wtf { "log ${i++}" }


        //        val exception = NullPointerException("12345")
        //        er { exception }
        //        er(exception) { "exception" }
        //        er(null)

        "INNER".apply {
            5?.let {
                d { "this = ${this}" }

                //                val exception = NullPointerException("12345")
                //                er { exception }
                //                er({ exception }) { "dsds" }
                //                er(exception) { "exception" }
                //                er(null)
            }
        }

        logFromFile()

        LogTest.logFromJava()
    }


    private fun codifiedTest() {
        val decode = Language::class.decode("ar")
        val decode1 = Language::class.decode("en")
        val decode3: Language = try {
            Language::class.decode("aa")
        } catch (e: Exception) {
            Language::class.decode("ar")
        }

        d { "decode = $decode" }
        d { "decode1 = $decode1" }
        d { "decode3 = $decode3" }
    }


    private fun setupRecycler() {
        val items = listOf(Item("1"), Item("2"), Item("3"), Item("4"))

/*
        recycler.adapter = simpleAdapter(R.layout.row_simple_text, items, {
            it.apply {
                textView.text = text
            }
        }) { position, item ->
            d { "item = $item" }
        }
*/

        binding.recycler.setSimpleAdapter(R.layout.row_simple_text, items, {
            val rowSimpleTextBinding = RowSimpleTextBinding.bind(itemView)
            rowSimpleTextBinding.apply {
                it.apply {
                    textView.text = text
                }
            }
        }) { position, item ->
            d { "item = $item" }
        }


//        recycler.adapter = BaseQuickStanderAdapter(items)
//        recycler.adapter = SimpleTextAdapter(items.toMutableList())
//                .onItemClick { item ->
//                    d { "item = $item" }
//                }

    }


    override fun onResume() {
        super.onResume()
        onResumeLocaleDelegate()
    }

    override fun attachBaseContext(newBase: Context?) = super.attachBaseContext(wrap(newBase!!))
}

data class Item(val text: String)

class SimpleTextAdapter(items: List<Item>) : BaseAdapter<Item>(R.layout.row_simple_text, items) {
    override fun convert(holder: BaseViewHolder, item: Item) {
        val binding = RowSimpleTextBinding.bind(holder.itemView)

        binding.apply {
            item.apply {
                textView.text = text

                // using onItemClick for some reason is not working in the fist item so stick to using this
                holder.itemView.setOnClickListener {
                    d { "itemView.setOnClickListener = ${item}" }
                }
            }
        }
    }
}

class BaseQuickStanderAdapter(items: MutableList<Item>) :
    BaseQuickAdapter<Item, BaseViewHolder>(R.layout.row_simple_text, items) {
    override fun convert(holder: BaseViewHolder, item: Item) {
        holder.apply {
            item.apply {
                setText(R.id.textView, text)

//
//                onItemClick { item ->
//                    d { "onItemClick::item = ${item}" }
//                }
            }
        }
    }
}

private fun logFromFile() {
    var i = 0

    d { "logFromFile ${i++}" }
    i { "logFromFile ${i++}" }
    w { "logFromFile ${i++}" }
    e { "logFromFile ${i++}" }
    wtf { "logFromFile ${i++}" }
}
