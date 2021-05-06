package pl.edu.pja.financemanager.addEditActivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.edu.pja.financemanager.MainActivity
import pl.edu.pja.financemanager.databinding.ActivityAddPositionBinding
import pl.edu.pja.financemanager.db.Position
import pl.edu.pja.financemanager.db.PositionDb
import java.lang.Exception
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
        val place = addEditBinding.editPlace.text.toString()
        val category = addEditBinding.editCategory.text.toString()
        val date =  try{LocalDate.parse(addEditBinding.editDate.text)}catch (e:Exception){null}
        val amount = addEditBinding.editAmount.text.toString().toDoubleOrNull() ?: 0.0

        if(place!="" && category !="" && date!= null && amount != 0.0)
        {
            val position = Position(
                0,
                place,
                category,
                 date,
                amount
            )
            thread {
                db.positions().insert(position)
                println("done")
                val returnIntent = Intent()
                setResult(RESULT_OK, returnIntent)
                finish()
            }
        }
    }


}