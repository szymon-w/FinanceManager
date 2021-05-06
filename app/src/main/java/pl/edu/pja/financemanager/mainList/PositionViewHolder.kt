package pl.edu.pja.financemanager.mainList

import android.content.Intent
import android.graphics.Color
import android.view.View
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.RecyclerView
import pl.edu.pja.financemanager.addEditActivity.AddPositionActivity
import pl.edu.pja.financemanager.databinding.ItemPositionBinding
import pl.edu.pja.financemanager.db.Position

class PositionViewHolder(
    private val itemPositionBinding: ItemPositionBinding
): RecyclerView.ViewHolder(itemPositionBinding.root) {

    fun bindItem(item: Position) {
        val amount = item.amount
        val t = itemPositionBinding.amountTV
        t.text=String.format("%.2f", amount)
        if(amount<0.0) t.setTextColor(Color.RED) else t.setTextColor(Color.BLACK)

        itemPositionBinding.placeTV.text=item.place
        itemPositionBinding.categoryTV.text=item.category
        itemPositionBinding.dateTV.text=item.eventDate.toString()
    }
}