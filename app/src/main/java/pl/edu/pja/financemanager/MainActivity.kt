package pl.edu.pja.financemanager

import android.app.Activity
import android.content.Intent
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
    private var reloadNeeded = true
    private val addingRequest = 222


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)
        addEditButton()
        monthlyBalanceButton()
        mainList()
        //addPosition()
    }

    private fun mainList() = with(mainBinding.positionList) {
        layoutManager=LinearLayoutManager(this@MainActivity)
        val items = listOf("a","b","c")
        println(items)
        adapter = PositionAdapter(items)
    }

    override fun onActivityResult(requestCode:Int , resultCode: Int, data:Intent?) {
        super.onActivityResult(requestCode,resultCode,data)
        if (requestCode == addingRequest) {
            if (resultCode == Activity.RESULT_OK) {
                this.reloadNeeded = true;
                }
            }
        }

    override fun onResume() {
        super.onResume();

        thread{
            var today = LocalDate.now()
            var currentMonthSum:Double
            if(this.reloadNeeded) {
                currentMonthSum = db.positions().getSumForChosenMonth(
                    today.year.toString(),
                    DateHelper.getStringNumberMonth(today)
                )
                runOnUiThread {
                    mainBinding.summaryValue.text = String.format("%.2f", currentMonthSum)
                }
                this.reloadNeeded = false
            }
        }
    }

    private fun monthlyBalanceButton() {
        mainBinding.monthButton.setOnClickListener {
            Intent(this, MonthlyBalanceActivity::class.java).let(this::startActivity)
        }
    }

    private fun addEditButton() {
        mainBinding.addButton.setOnClickListener {
                startActivityForResult(Intent(this, AddPositionActivity::class.java),222)
            }
        }

    private fun currentMonthBalance() {
        thread{
            var today = LocalDate.now()
            var currentMonthSum = db.positions().getSumForChosenMonth(today.year.toString(), DateHelper.getStringNumberMonth(today))
            runOnUiThread {
                mainBinding.summaryValue.text = String.format("%.2f", currentMonthSum)
            }
        }
    }

    fun addPosition() {
        val position = Position(
                0,
                "Biedronka",
                "Zakupy",
                LocalDate.of(2021,10,2),
                10.00
        )
        thread {
            val x = db.positions().getAllForChosenMonth("2021","03")
            println(x.joinToString ("|"))
            //val z = db.positions().getSumForChosenMonth("2021", "05")
            //println(z)
            //db.positions().insert(position)
            //db.positions().insert(position)
            //val y = db.positions().getAll()
            //println(y.joinToString ("|"))

            /*db.positions().insert(position)
            db.positions().insert(position)
            db.positions().insert(position)
            val y = db.positions().getAll()
            println(y.joinToString ("|"))
            var a = db.positions().getPosition(2)
            a.place = "Tesco"
            db.positions().update(a)
            a = db.positions().getPosition(2)
            println(a)*/


        }
    }

}