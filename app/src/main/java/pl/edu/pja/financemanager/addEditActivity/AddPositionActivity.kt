package pl.edu.pja.financemanager.addEditActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pl.edu.pja.financemanager.R
import pl.edu.pja.financemanager.databinding.ActivityAddPositionBinding
import pl.edu.pja.financemanager.databinding.ActivityMainBinding

class AddPositionActivity : AppCompatActivity() {
    private val addEditBinding by lazy { ActivityAddPositionBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(addEditBinding.root)
    }
}