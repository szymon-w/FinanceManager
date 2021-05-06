package pl.edu.pja.financemanager.mainList

import android.app.AlertDialog
import android.content.DialogInterface
import android.icu.text.Transliterator
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.edu.pja.financemanager.MainActivity
import pl.edu.pja.financemanager.databinding.ItemPositionBinding
import pl.edu.pja.financemanager.db.Position
import pl.edu.pja.financemanager.db.PositionDb
import pl.edu.pja.financemanager.helper.DateHelper
import java.time.LocalDate
import kotlin.concurrent.thread

class PositionAdapter(
    private val db: PositionDb,
    private val parentContext: MainActivity
): RecyclerView.Adapter<PositionViewHolder>(){
    private var items = emptyList<Position>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PositionViewHolder {
        return ItemPositionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
            .let(::PositionViewHolder)
    }

    override fun onBindViewHolder(holder: PositionViewHolder, position: Int) {
        holder.bindItem(items[position])

        holder.itemView.setOnLongClickListener(){
            AlertDialog.Builder(parentContext)
                    .setTitle("Alert")
                    .setMessage("Do you really want to remove this item?")
                    .setNegativeButton("No",null)
                    .setPositiveButton("Yes",DialogInterface.OnClickListener{
                        dialog,which -> delete(position)
                    })
                    .show()
            true
        }
    }

    override fun getItemCount(): Int =items.size

    fun getDataToList(){
        var today = LocalDate.now()
        items = db.positions().getAllForChosenMonth(
                today.year.toString(),
                DateHelper.getStringNumberMonth(today)
        )
    }

    private fun delete(position: Int)=thread{
        val item = items[position]
        db.positions().deleteById(item.id)
        parentContext.reloadList()
        parentContext.reloadBalance()
    }

}

