package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.PillRegister;
import model.ReminderRegister;

public class Database extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "PillsDB";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_PILL = "pill";
    public static final String TABLE_REMINDER = "reminder";
    public static final String TABLE_PILL_REMINDER = "pill_reminder";

    // PILL TABLE //
    public static final String pill_id = "id";
    public static final String pill_name = "Name";
    public static final String pill_photoId = "PhotoId";
    public static final String pill_start = "Start";
    public static final String pill_duration = "Duration";
    public static final String pill_frequency = "Frequency";
    public static final String pill_status = "Status";

    // REMINDER TABLE //
    public static final String reminder_id = "id";
    public static final String reminder_hour = "Hour";
    public static final String reminder_minutes = "Minutes";
    public static final String reminder_quantity = "Quantity";
    public static final String reminder_pill_id = "Pill_id";

    // PILL REMINDER TABLE //
    public static final String pill_reminder_id = "id";
    public static final String pill_reminder_pillid = "pill_id";
    public static final String pill_reminder_reminderid = "reminder_id";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_PILL_TABLE = "CREATE TABLE "+TABLE_PILL+" ("+pill_id+" INTEGER PRIMARY KEY AUTOINCREMENT,"+pill_name+" nVarchar(100) NOT NULL UNIQUE,"+pill_photoId+" INTEGER NOT NULL,"+pill_start+
                " DATETIME NOT NULL,"+pill_duration+" nVarchar(255) NOT NULL,"+pill_frequency+" nVarchar(255) NOT NULL,"+pill_status+" TEXT NOT NULL)";
        db.execSQL(CREATE_PILL_TABLE);

        final String CREATE_REMINDER_TABLE = "CREATE TABLE "+TABLE_REMINDER+" ("+reminder_id+" INTEGER  PRIMARY KEY AUTOINCREMENT,"+reminder_hour+" INTEGER NOT NULL, "+reminder_minutes+" INTEGER NOT NULL,"+
                reminder_quantity+" nVarchar(100) NOT NULL,"+reminder_pill_id+" INTEGER, FOREIGN KEY ("+reminder_pill_id+") REFERENCES "+TABLE_PILL+"("+pill_id+") ON DELETE CASCADE)";
        db.execSQL(CREATE_REMINDER_TABLE);

        final String CREATE_PILL_REMINDER = "CREATE TABLE "+TABLE_PILL_REMINDER+" ("+pill_reminder_id+" INTEGER PRIMARY KEY AUTOINCREMENT,"+pill_reminder_pillid+" INTEGER NOT NULL,"+
                pill_reminder_reminderid+" INTEGER NOT NULL, FOREIGN KEY ("+pill_reminder_pillid+") REFERENCES "+TABLE_PILL+"("+pill_id+") ON DELETE CASCADE," +
                "FOREIGN KEY ("+pill_reminder_reminderid+") REFERENCES "+TABLE_REMINDER+"("+reminder_id+") ON DELETE CASCADE);";
        db.execSQL(CREATE_PILL_REMINDER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public ArrayList<String> getReminders() {
        ArrayList<String> reminders = new ArrayList<>();

        String selectQuery = "SELECT * FROM "+TABLE_PILL;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                reminders.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return reminders;
    }

    public void addReminder(ReminderRegister reminderRegister, String pillName) {

        SQLiteDatabase db = this.getWritableDatabase();


        db.execSQL("INSERT INTO "+TABLE_REMINDER+
                        "("+reminder_hour+","+
                        reminder_minutes+","+reminder_quantity+","+reminder_pill_id+")"+
                        "VALUES("+reminderRegister.getHour()+","+reminderRegister.getMinutes()+","+reminderRegister.getQuantity()+",(SELECT "+pill_id+
                        " FROM "+TABLE_PILL+
                        " WHERE "+pill_name+"=?))",
                new Object[]{ pillName });
        db.close();
    }

    public ArrayList<String> getSongsByName() {
        ArrayList<String> songs = new ArrayList<>();

        String selectQuery = "SELECT * FROM "+TABLE_PILL;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                songs.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return songs;
    }

    public void addPill(PillRegister pillRegister) {

        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        ContentValues values = new ContentValues();

        values.put(pill_name, pillRegister.getPillName());
        values.put(pill_photoId, pillRegister.getPhotoId());
        values.put(pill_start, dateFormat.format(pillRegister.getStart()));
        values.put(pill_duration, pillRegister.getDuration());
        values.put(pill_frequency, pillRegister.getFrequency());
        values.put(pill_status, pillRegister.getStatus());

        db.insert(TABLE_PILL, null, values);
        db.close();
    }

    public ArrayList<String> getPills() {
        ArrayList<String> pills = new ArrayList<>();

        String selectQuery = "SELECT * FROM "+TABLE_PILL;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                pills.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return pills;
    }

    public void addReminderToPill(Integer reminderId, String pillName) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("INSERT INTO "+TABLE_PILL_REMINDER+
                        "("+pill_reminder_pillid+","+
                        pill_reminder_reminderid+")"+
                        "VALUES((SELECT "+pill_id+
                        " FROM "+TABLE_PILL+
                        " WHERE "+pill_name+"=?),?)",
                new Object[]{ pillName, reminderId });
        db.close();
    }
}
