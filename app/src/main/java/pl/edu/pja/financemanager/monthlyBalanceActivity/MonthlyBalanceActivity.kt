package pl.edu.pja.financemanager.monthlyBalanceActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import pl.edu.pja.financemanager.MainActivity
import pl.edu.pja.financemanager.R
import pl.edu.pja.financemanager.databinding.ActivityAddPositionBinding
import pl.edu.pja.financemanager.databinding.ActivityMonthlyBalanceBinding
import pl.edu.pja.financemanager.db.PositionDb
import pl.edu.pja.financemanager.helper.DateHelper
import java.time.LocalDate
import kotlin.concurrent.thread

class MonthlyBalanceActivity : AppCompatActivity() {
    private val monthlyBalanceBinding by lazy { ActivityMonthlyBalanceBinding.inflate(layoutInflater)}
    private val db by lazy { PositionDb.open(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(monthlyBalanceBinding.root)
        setupSpinner()
        reloadGraph()
    }

    private fun setupSpinner() {
        val map = DateHelper.monthMap
        monthlyBalanceBinding.spinner.adapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, map.keys.toList())
        monthlyBalanceBinding.spinner.setSelection(LocalDate.now().monthValue-1)
        monthlyBalanceBinding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                reloadGraph()
            }
        }
    }

    private fun reloadGraph() {
        thread {
            val map = mutableMapOf<Int,Double>()
            val monthName = monthlyBalanceBinding.spinner.selectedItem.toString()
            val monthNumber = DateHelper.monthMap.getValue(monthName)
            val cursor = db.positions().getSumDayByDay(LocalDate.now().year.toString(), monthNumber)
            cursor.moveToPosition(-1)
            while(cursor.moveToNext()){
                map[LocalDate.parse(cursor.getString(0)).dayOfMonth] = cursor.getDouble(1)
            }
            cursor.close()

            val addingList = mutableListOf<DataPoint>()
            var sum = 0.0
            for(i in 1..31){
                var value = if(map.containsKey(i)) map.getValue(i) else 0.0
                sum+=value
                addingList.add(DataPoint(i,sum.toInt()))
                //addingList.add(DataPoint(i,value.toInt()))
            }

            runOnUiThread{
                monthlyBalanceBinding.graphView.setData(addingList)
            }
        }
    }
}