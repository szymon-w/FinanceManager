package pl.edu.pja.financemanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pl.edu.pja.financemanager.addEditActivity.AddPositionActivity
import pl.edu.pja.financemanager.databinding.ActivityMainBinding
import pl.edu.pja.financemanager.db.Position
import pl.edu.pja.financemanager.db.PositionDb
import pl.edu.pja.financemanager.helper.DateHelper
import pl.edu.pja.financemanager.monthlyBalanceActivity.MonthlyBalanceActivity
import java.time.LocalDate
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private val mainBinding by lazy {ActivityMainBinding.inflate(layoutInflater)}
    private val db by lazy { PositionDb.open(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)
        currentMonthBalance()
        addEditButton()
        monthlyBalanceButton()
        //addPosition()
    }

    private fun monthlyBalanceButton() {
        mainBinding.monthButton.setOnClickListener {
            Intent(this, MonthlyBalanceActivity::class.java).let(this::startActivity)
        }
    }

    private fun addEditButton() {
        mainBinding.addButton.setOnClickListener {
                Intent(this, AddPositionActivity::class.java).let(this::startActivity)
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
            db.positions().insert(position)
            db.positions().insert(position)
            db.positions().insert(position)
            val y = db.positions().getAll()
            println(y.joinToString ("|"))
            val x = db.positions().getAllForChosenMonth("2021","04")
            println(x.joinToString ("|"))
            val z = db.positions().getSumForChosenMonth("2021", "05")
            println(z)
            var a = db.positions().getPosition(2)
            a.place = "Tesco"
            db.positions().update(a)
            a = db.positions().getPosition(2)
            println(a)


        }
    }

}