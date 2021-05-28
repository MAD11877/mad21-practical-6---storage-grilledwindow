package sg.edu.np.mad.madpractical;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "userDB.db";
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FOLLOWED = "followed";

    public DatabaseHandler(Context context,
                           SQLiteDatabase.CursorFactory factory)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "" +
                "CREATE TABLE " + TABLE_USERS +
                "(" +
                    COLUMN_ID           + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME         + " TEXT," +
                    COLUMN_DESCRIPTION  + " TEXT," +
                    COLUMN_FOLLOWED     + " INTEGER" +
                ")";
        db.execSQL(CREATE_USERS_TABLE);

        for (int i = 0; i < 20; ++i) {
            Log.v("Create", "");
            ContentValues values = new ContentValues();

            values.put(COLUMN_NAME, "Name" + rng());
            values.put(COLUMN_DESCRIPTION, "Description" + rng());
            values.put(COLUMN_FOLLOWED, rngFollow());

            Log.v("User ", String.valueOf(values));
            db.insert(TABLE_USERS, null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> userList = new ArrayList<User>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                boolean followed = cursor.getInt(3) == 1;

                userList.add(new User(name, description, id, followed));
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }

        cursor.close();

        return userList;
    }

    private boolean rngFollow() {
        return new Random().nextInt(2) == 1;
    }

    private int rng() {
        return new Random().nextInt();
    }

    public void updateUser(User user) {
        Log.v("Update", String.valueOf(user));
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL(
            "UPDATE " + TABLE_USERS +
            " SET " + COLUMN_FOLLOWED + " = " + (user.followed ? 1 : 0) +
            " WHERE " + COLUMN_ID + " = " + user.id
        );
    }

}
