package humazed.github.com.kotlinandroidutils

import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView


fun <T> AutoCompleteTextView.onItemSelected(
    items: List<T>,
    names: List<String>,
    onItemSelected: (item: T, position: Int) -> Unit
) {
    setAdapter(ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, names))
    onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
        onItemSelected(items[position], position)
    }
}

fun <T> AutoCompleteTextView.onItemSelected(
    items: List<T>,
    names: List<String>,
    onItemSelected: (item: T) -> Unit
) {
    onItemSelected(items, names) { item, _ -> onItemSelected(item) }
}


fun <T> AutoCompleteTextView.onItemSelected(
    items: List<T>,
    mapNames: (T) -> String,
    onItemSelected: (item: T, position: Int) -> Unit
) {
    onItemSelected(items, items.map(mapNames), onItemSelected)
}

fun <T> AutoCompleteTextView.onItemSelected(
    items: List<T>,
    mapNames: (T) -> String,
    onItemSelected: (item: T) -> Unit
) {
    onItemSelected(items, mapNames) { item, _ -> onItemSelected(item) }
}
