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
        setupData()
    }

    private fun setupData(){
        thread {
            val b = intent.extras
            if(b!=null){
                val positionId = b.getInt("positionId")
                val position = db.positions().getPosition(positionId)
                runOnUiThread {
                    addEditBinding.editPlace.setText(position.place)
                    addEditBinding.editCategory.setText(position.category)
                    addEditBinding.editDate.setText(position.eventDate.toString())
                    addEditBinding.editAmount.setText(position.amount.toString())
                }
            }
        }
    }

    private fun submitButton() {
        addEditBinding.submitButton.setOnClickListener {
            addEditPosition()
        }
    }

    private fun addEditPosition() {
        val place = addEditBinding.editPlace.text.toString()
        val category = addEditBinding.editCategory.text.toString()
        val date =  try{LocalDate.parse(addEditBinding.editDate.text)}catch (e:Exception){null}
        val amount = addEditBinding.editAmount.text.toString().toDoubleOrNull() ?: 0.0



        if(place!="" && category !="" && date!= null && amount != 0.0)
        {
            thread {
                val b = intent.extras
                if(b!=null) {
                    val positionId = b.getInt("positionId")
                    val position = db.positions().getPosition(positionId)
                    position.place=place
                    position.category=category
                    position.eventDate=date
                    position.amount=amount
                    db.positions().update(position)
                }else {
                    val position = Position(
                            0,
                            place,
                            category,
                            date,
                            amount
                    )
                    db.positions().insert(position)
                }
                println("done")
                val returnIntent = Intent()
                setResult(RESULT_OK, returnIntent)
                finish()
            }
        }
    }


}