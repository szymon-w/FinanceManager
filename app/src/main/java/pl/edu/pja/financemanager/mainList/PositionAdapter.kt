package pl.edu.pja.financemanager.mainList

import android.icu.text.Transliterator
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.edu.pja.financemanager.databinding.ItemPositionBinding
import pl.edu.pja.financemanager.db.Position

class PositionAdapter(
    private val items:List<String>
): RecyclerView.Adapter<PositionViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PositionViewHolder {
        return ItemPositionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
            .let(::PositionViewHolder)
    }

    override fun onBindViewHolder(holder: PositionViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount(): Int =items.size


}

