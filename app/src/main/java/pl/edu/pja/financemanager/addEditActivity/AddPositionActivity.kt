package pl.edu.pja.financemanager.addEditActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pl.edu.pja.financemanager.R
import pl.edu.pja.financemanager.databinding.ActivityAddPositionBinding
import pl.edu.pja.financemanager.databinding.ActivityMainBinding
import pl.edu.pja.financemanager.db.Position
import pl.edu.pja.financemanager.db.PositionDb
import java.time.LocalDate
import kotlin.concurrent.thread

class AddPositionActivity : AppCompatActivity() {
    private val addEditBinding by lazy { ActivityAddPositionBinding.inflate(layoutInflater)}
    private val db by lazy { PositionDb.open(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(addEditBinding.root)
        submitButton()
    }

    private fun submitButton() {
        addEditBinding.submitButton.setOnClickListener {
            addPosition()
        }
    }

    private fun addPosition() {
        val position = Position(
            0,
            addEditBinding.editPlace.text.toString(),
            addEditBinding.editCategory.text.toString(),
            LocalDate.parse(addEditBinding.editDate.text),
            addEditBinding.editAmount.text.toString().toDoubleOrNull() ?: 0.0
        )
        thread {
            db.positions().insert(position)
            println("done")
            finish()
        }
    }


}