package pl.edu.pja.financemanager.monthlyBalanceActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pl.edu.pja.financemanager.R
import pl.edu.pja.financemanager.databinding.ActivityAddPositionBinding
import pl.edu.pja.financemanager.databinding.ActivityMonthlyBalanceBinding

class MonthlyBalanceActivity : AppCompatActivity() {
    private val monthlyBalanceBinding by lazy { ActivityMonthlyBalanceBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(monthlyBalanceBinding.root)
    }
}