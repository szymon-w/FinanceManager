package pl.edu.pja.financemanager.monthlyBalanceActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import pl.edu.pja.financemanager.R
import pl.edu.pja.financemanager.databinding.ActivityAddPositionBinding
import pl.edu.pja.financemanager.databinding.ActivityMonthlyBalanceBinding
import pl.edu.pja.financemanager.helper.DateHelper

class MonthlyBalanceActivity : AppCompatActivity() {
    private val monthlyBalanceBinding by lazy { ActivityMonthlyBalanceBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(monthlyBalanceBinding.root)
        setupSpinner()
        setupGraph()


    }

    private fun setupSpinner() {
        val map = DateHelper.monthMap
        monthlyBalanceBinding.spinner.adapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, map.keys.toList())
    }

    private fun setupGraph() {
        val list: List<DataPoint> = listOf(
                DataPoint(0,0),
                DataPoint(1,3),
                DataPoint(2,2),
                DataPoint(3,3),
                DataPoint(4,5),
                DataPoint(5,2),
                DataPoint(6,-2),
                DataPoint(7,1),
                DataPoint(8,8)
        )
        monthlyBalanceBinding.graphView.setData(list)
    }
}