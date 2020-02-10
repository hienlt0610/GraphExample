package dev.hienlt.graph

import android.view.View
import android.widget.TextView
import de.blox.graphview.ViewHolder

class SimpleViewHolder(itemView: View) : ViewHolder(itemView) {

    var textView: TextView? = null

    init {
        textView = itemView.findViewById(R.id.textView)
    }
}