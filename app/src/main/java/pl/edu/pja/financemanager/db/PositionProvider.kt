package pl.edu.pja.financemanager.db

import android.content.*
import android.database.Cursor
import android.database.SQLException
import android.net.Uri
import pl.edu.pja.financemanager.MainActivity
import java.time.LocalDate


const val PROVIDER_AUTHORITY = "pl.edu.pja.financemanager"

class PositionProvider: ContentProvider() {
    private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(PROVIDER_AUTHORITY, "positions", 1)
        addURI(PROVIDER_AUTHORITY, "positions/#", 2)
    }

    override fun getType(uri: Uri): String? = when (sUriMatcher.match(uri)) {
        1 -> "vnd.android.cursor.dir/vnd.${PROVIDER_AUTHORITY}.positions"
        2 -> "vnd.android.cursor.item/vnd.${PROVIDER_AUTHORITY}.positions"
        else -> throw IllegalArgumentException()
    }

    override fun query(
            uri: Uri, projection: Array<String>?, selection: String?,
            selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor {
        val db = (context?.applicationContext as? MainActivity)?.db
                ?: throw RuntimeException("Cannot get application context")

        return when (sUriMatcher.match(uri)) {
            1 -> db.positions().getAllCursor()
            2 -> {
                val id = ContentUris.parseId(uri)
                db.positions().getPositionByIdCursor(id)
            }
            else -> throw IllegalArgumentException()
        }
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = (context?.applicationContext as? MainActivity)?.db
                ?: throw RuntimeException("Cannot get application context")

        val place=values?.getAsString("place")?:null
        val category=values?.getAsString("category")?:null
        val eventDate= LocalDate.parse(values?.getAsString("eventDate"))?:null
        val amount=values?.getAsDouble("place")?:null

        val pos:Position
        if(place!=null && category!=null && eventDate!=null &&amount!=null) {
            pos = Position(0,
                    place = place,
                    category = category,
                    eventDate = eventDate,
                    amount = amount
            )

            val id: Long = db.positions().insert(pos);
            context?.contentResolver?.notifyChange(uri, null);
            return ContentUris.withAppendedId(uri, id);
        }else{
            throw SQLException("Failed to add a record into $uri")
        }
    }

    override fun update(
            uri: Uri,
            values: ContentValues?,
            selection: String?,
            selectionArgs: Array<out String>?
    ): Int {
        return when (sUriMatcher.match(uri)) {
            1 -> throw java.lang.IllegalArgumentException("Invalid URI, cannot update without ID$uri")
            2 -> {
                val context = context ?: return 0
                val db = (context.applicationContext as MainActivity).db

                val id = values?.getAsLong("place") ?: null
                if (id != null) {
                    var pos: Position = db.positions().getPosition(id.toInt())
                    if (pos != null) {

                        val place = values?.getAsString("place") ?: null
                        val category = values?.getAsString("category") ?: null
                        val eventDate = LocalDate.parse(values?.getAsString("eventDate")) ?: null
                        val amount = values?.getAsDouble("place") ?: null

                        if (place != null && category != null && eventDate != null && amount != null) {
                            pos.place = place
                            pos.category = category
                            pos.eventDate = eventDate
                            pos.amount = amount

                            val id = db.positions().update(pos)
                            context.contentResolver.notifyChange(uri, null);
                            return id
                        } else throw java.lang.IllegalArgumentException("Bad request")
                    } else throw java.lang.IllegalArgumentException("Bad request")
                } else throw java.lang.IllegalArgumentException("Bad request")
            }
            else -> throw java.lang.IllegalArgumentException("Unknown URI: $uri")
        }
    }



    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return when (sUriMatcher.match(uri)) {
            1 -> throw java.lang.IllegalArgumentException("Invalid URI, cannot update without ID$uri")
            2 -> {
                val context = context ?: return 0
                val db = (context.applicationContext as MainActivity).db

                val count: Int = db.positions().deleteById(ContentUris.parseId(uri).toInt())
                context.contentResolver.notifyChange(uri, null)
                return count
            }
            else -> throw java.lang.IllegalArgumentException("Unknown URI: $uri")
        }
    }

}