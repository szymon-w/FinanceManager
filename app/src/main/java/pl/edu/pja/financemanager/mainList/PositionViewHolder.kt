package pl.edu.pja.financemanager.mainList

import androidx.recyclerview.widget.RecyclerView
import pl.edu.pja.financemanager.databinding.ItemPositionBinding

class PositionViewHolder(
    private val itemPositionBinding: ItemPositionBinding
): RecyclerView.ViewHolder(itemPositionBinding.root) {

    fun bindItem(text: String) {
        itemPositionBinding.textView6.text=text
    }

}