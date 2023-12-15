package pl.training.bestweather.commons.components

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri

class UsersProvider : ContentProvider() {

    private var db: SQLiteDatabase? = null

    override fun onCreate(): Boolean {
        val dbHelper = DatabaseHelper(requireContext())
        db = dbHelper.writableDatabase
        return db == null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri {
        db?.insert(TABLE_NAME, "", values)?.let { id ->
            val newUri = ContentUris.withAppendedId(CONTENT_URI, id)
            requireContext().contentResolver.notifyChange(uri, null)
            return newUri
        }
        throw SQLException("User insert failed ($uri)")
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor {
        val queryBuilder = SQLiteQueryBuilder()
        queryBuilder.tables = TABLE_NAME
        when(uriMatcher.match(uri)) {
            USERS_CODE -> queryBuilder.projectionMap = emptyMap<String, String>()
            USER_CODE -> queryBuilder.appendWhere("$ID_COLUMN=${getId(uri)}")
        }
        val cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, NAME_COLUMN)
        cursor.setNotificationUri(requireContext().contentResolver, uri)
        return cursor
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val count = when(uriMatcher.match(uri)) {
            USERS_CODE -> db?.delete(TABLE_NAME, selection, selectionArgs) ?: 0
            USER_CODE -> db?.delete(TABLE_NAME, "$ID_COLUMN=?", arrayOf(getId(uri))) ?: 0
            else -> throw IllegalAccessException("Unsupported uri $uri")
        }
        requireContext().contentResolver.notifyChange(uri, null)
        return count
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        val count = when(uriMatcher.match(uri)) {
            USERS_CODE -> db?.update(TABLE_NAME, values, selection, selectionArgs) ?: 0
            USER_CODE -> db?.update(TABLE_NAME, values, "$ID_COLUMN=?", arrayOf(getId(uri))) ?: 0
            else -> throw IllegalAccessException("Unsupported uri $uri")
        }
        requireContext().contentResolver.notifyChange(uri, null)
        return count
    }

    private fun getId(uri: Uri) = uri.pathSegments[1]

    override fun getType(uri: Uri): String = when(uriMatcher.match(uri)) {
        USERS_CODE -> "vnd.android.cursor.dir/vnd.pl.training.bestweather.users"
        USER_CODE -> "vnd.android.cursor.dir/vnd.pl.training.bestweather.user"
        else -> throw IllegalAccessException("Unsupported uri $uri")
    }

    companion object {

        const val ID_COLUMN = "_id"
        const val NAME_COLUMN = "name"
        const val PROVIDER_NAME = "pl.training.bestweather.UsersProvider"
        private const val URL = "content://$PROVIDER_NAME/users"
        val CONTENT_URI = Uri.parse(URL)

        private const val DATABASE_NAME = "users"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "users"
        private const val CREATE_TABLE = "create table $TABLE_NAME ($ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT, $NAME_COLUMN TEXT NOT NULL);"
        private const val USERS_CODE = 1
        private const val USER_CODE = 2
        private var uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(PROVIDER_NAME, "users", USERS_CODE)
            addURI(PROVIDER_NAME, "users/#", USER_CODE)
        }

        class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

            override fun onCreate(db: SQLiteDatabase) {
                db.execSQL(CREATE_TABLE)
            }

            override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
                db.execSQL("drop table if exists $TABLE_NAME")
                db.execSQL(CREATE_TABLE)
            }

        }

    }

}