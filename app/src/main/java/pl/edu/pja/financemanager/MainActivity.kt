package pl.edu.pja.financemanager

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import pl.edu.pja.financemanager.addEditActivity.AddPositionActivity
import pl.edu.pja.financemanager.databinding.ActivityMainBinding
import pl.edu.pja.financemanager.db.Position
import pl.edu.pja.financemanager.db.PositionDb
import pl.edu.pja.financemanager.helper.DateHelper
import pl.edu.pja.financemanager.mainList.PositionAdapter
import pl.edu.pja.financemanager.monthlyBalanceActivity.MonthlyBalanceActivity
import java.time.LocalDate
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private val mainBinding by lazy {ActivityMainBinding.inflate(layoutInflater)}
    val db by lazy { PositionDb.open(this) }
    private val positionAdapter by lazy {PositionAdapter(db,this)}
    private var reloadNeeded = true
    private val addingRequest = 222
    val editRequest = 333


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)
        setupAddEditButton()
        setupMonthlyBalanceButton()
        setupMainList()
    }

    override fun onResume() {
        super.onResume();
        thread{
            if(this.reloadNeeded) {
                reloadList()
                reloadBalance()
                this.reloadNeeded = false
            }
        }
    }

    override fun onActivityResult(requestCode:Int , resultCode: Int, data:Intent?) {
        super.onActivityResult(requestCode,resultCode,data)
        if (requestCode == addingRequest || requestCode==editRequest) {
            if (resultCode == Activity.RESULT_OK) {
                this.reloadNeeded = true;
            }
        }
    }

    private fun setupMainList(){ with(mainBinding.positionList) {
        layoutManager = LinearLayoutManager(this@MainActivity)
        adapter = positionAdapter
        }
    }

    private fun setupMonthlyBalanceButton() {
        mainBinding.monthButton.setOnClickListener {
            Intent(this, MonthlyBalanceActivity::class.java).let(this::startActivity)
        }
    }

    private fun setupAddEditButton() {
        mainBinding.addButton.setOnClickListener {
                startActivityForResult(Intent(this, AddPositionActivity::class.java),addingRequest)
            }
        }

    fun reloadList(){
        positionAdapter.getDataToList()
        runOnUiThread {
            positionAdapter.notifyDataSetChanged()
        }
    }

    fun reloadBalance(){
        val today = LocalDate.now()
        val currentMonthSum:Double
        currentMonthSum = db.positions().getSumForChosenMonth(
                today.year.toString(),
                DateHelper.getStringNumberMonth(today)
        )
        runOnUiThread {
            val t = mainBinding.summaryValue
            t.text = String.format("%.2f", currentMonthSum)
            if(currentMonthSum<0.0) t.setTextColor(Color.RED) else t.setTextColor(Color.BLACK)
        }
    }
}