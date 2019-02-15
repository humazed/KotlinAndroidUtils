package humazed.github.com.kotlinandroidutils

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner


fun <T> Spinner.onItemSelected(items: List<T>, names: List<String>, onItemSelected: (item: T, position: Int) -> Unit) {
    adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, names)
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            onItemSelected(items[position], position)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
        }
    }
}

fun <T> Spinner.onItemSelected(items: List<T>, names: List<String>, onItemSelected: (item: T) -> Unit) {
    onItemSelected(items, names) { item, _ -> onItemSelected(item) }
}


fun <T> Spinner.onItemSelected(items: List<T>, mapNames: (List<T>) -> List<String>, onItemSelected: (item: T, position: Int) -> Unit) {
    onItemSelected(items, mapNames(items), onItemSelected)
}

fun <T> Spinner.onItemSelected(items: List<T>, mapNames: (List<T>) -> List<String>, onItemSelected: (item: T) -> Unit) {
    onItemSelected(items, mapNames) { item, _ -> onItemSelected(item) }
}
