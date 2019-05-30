package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.PillBoxDataResult;
import model.PillDataResult;
import model.PillRegister;
import model.ReminderDataResult;
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
    public static final String pill_reminders = "Reminder";

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

        final String CREATE_PILL_TABLE = "CREATE TABLE " + TABLE_PILL + " (" + pill_id + " INTEGER PRIMARY KEY AUTOINCREMENT," + pill_name + " nVarchar(100) NOT NULL UNIQUE," + pill_photoId + " INTEGER NOT NULL," + pill_start +
                " DATETIME NOT NULL," + pill_duration + " nVarchar(255) NOT NULL," + pill_frequency + " nVarchar(255) NOT NULL," + pill_reminders + " nVarchar(255) NOT NULL," + pill_status + " TEXT NOT NULL)";
        db.execSQL(CREATE_PILL_TABLE);

        final String CREATE_REMINDER_TABLE = "CREATE TABLE " + TABLE_REMINDER + " (" + reminder_id + " INTEGER  PRIMARY KEY AUTOINCREMENT," + reminder_hour + " INTEGER NOT NULL, " + reminder_minutes + " INTEGER NOT NULL," +
                reminder_quantity + " nVarchar(100) NOT NULL," + reminder_pill_id + " INTEGER, FOREIGN KEY (" + reminder_pill_id + ") REFERENCES " + TABLE_PILL + "(" + pill_id + ") ON DELETE CASCADE)";
        db.execSQL(CREATE_REMINDER_TABLE);

        final String CREATE_PILL_REMINDER = "CREATE TABLE " + TABLE_PILL_REMINDER + " (" + pill_reminder_id + " INTEGER PRIMARY KEY AUTOINCREMENT," + pill_reminder_pillid + " INTEGER NOT NULL," +
                pill_reminder_reminderid + " INTEGER NOT NULL, FOREIGN KEY (" + pill_reminder_pillid + ") REFERENCES " + TABLE_PILL + "(" + pill_id + ") ON DELETE CASCADE," +
                "FOREIGN KEY (" + pill_reminder_reminderid + ") REFERENCES " + TABLE_REMINDER + "(" + reminder_id + ") ON DELETE CASCADE);";
        db.execSQL(CREATE_PILL_REMINDER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public ArrayList<ReminderDataResult> getReminders(Integer pillId) {
        ArrayList<ReminderDataResult> reminders = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_REMINDER + " WHERE " + reminder_pill_id + "=" + pillId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ReminderDataResult reminderDataResult = new ReminderDataResult();
                reminderDataResult.setId(Integer.parseInt(cursor.getString(0)));
                reminderDataResult.setHour(Integer.parseInt(cursor.getString(1)));
                reminderDataResult.setMinutes(Integer.parseInt(cursor.getString(2)));
                reminderDataResult.setQuantity(cursor.getString(3));
                reminderDataResult.setPillId(Integer.parseInt(cursor.getString(4)));
                reminders.add(reminderDataResult);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return reminders;
    }

    public void addReminder(ReminderRegister reminderRegister, String pillName) {

        SQLiteDatabase db = this.getWritableDatabase();


        db.execSQL("INSERT INTO " + TABLE_REMINDER +
                        "(" + reminder_hour + "," +
                        reminder_minutes + "," + reminder_quantity + "," + reminder_pill_id + ")" +
                        "VALUES(" + reminderRegister.getHour() + "," + reminderRegister.getMinutes() + "," + reminderRegister.getQuantity() + ",(SELECT " + pill_id +
                        " FROM " + TABLE_PILL +
                        " WHERE " + pill_name + "=?))",
                new Object[]{pillName});
        db.close();
    }

    public void updateReminder(ReminderRegister reminderRegister, Integer pId, Integer id) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(reminder_hour, reminderRegister.getHour());
        values.put(reminder_minutes, reminderRegister.getMinutes());
        values.put(reminder_quantity, reminderRegister.getQuantity());

        db.update(TABLE_REMINDER,  values, "id = ? AND Pill_id = ?", new String[] { Integer.toString(id), Integer.toString(pId)});
        db.close();
    }

    public void deleteReminder(Integer pId) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        db.delete(TABLE_REMINDER,  "Pill_id = ?", new String[] { Integer.toString(pId)});
        db.close();
    }

    public PillDataResult getPillByName(String name) {
        PillDataResult pillDataResult = new PillDataResult();

        String selectQuery = "SELECT * FROM " + TABLE_PILL + " Where " + pill_name + "='" + name + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        if (cursor.moveToFirst()) {
            do {
                try {
                    Date start = dateFormat.parse(cursor.getString(3));

                    pillDataResult.setId(Integer.parseInt(cursor.getString(0)));
                    pillDataResult.setPillName(cursor.getString(1));
                    pillDataResult.setPhotoId(Integer.parseInt(cursor.getString(2)));
                    pillDataResult.setStart(start);
                    pillDataResult.setDuration(cursor.getString(4));
                    pillDataResult.setFrequency(cursor.getString(5));
                    pillDataResult.setReminderTimes(cursor.getString(6));
                } catch (Exception e) {

                }

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return pillDataResult;
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
        values.put(pill_reminders, pillRegister.getReminder());

        db.insert(TABLE_PILL, null, values);
        db.close();
    }

    public void updatePill(PillRegister pillRegister, Integer id) {

        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        ContentValues values = new ContentValues();

        values.put(pill_name, pillRegister.getPillName());
        values.put(pill_photoId, pillRegister.getPhotoId());
        values.put(pill_start, dateFormat.format(pillRegister.getStart()));
        values.put(pill_duration, pillRegister.getDuration());
        values.put(pill_frequency, pillRegister.getFrequency());
        values.put(pill_status, pillRegister.getStatus());
        values.put(pill_reminders, pillRegister.getReminder());

        db.update(TABLE_PILL,  values, "id = ?", new String[] { Integer.toString(id)});
        db.close();
    }

    public ArrayList<PillBoxDataResult> getActivePills() {
        ArrayList<PillBoxDataResult> pills = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_PILL + " WHERE " + pill_status + "='ACTIVE'";


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                PillBoxDataResult pillBoxDataResult = new PillBoxDataResult();
                pillBoxDataResult.setPillName(cursor.getString(1));
                pillBoxDataResult.setPhotoId(Integer.parseInt(cursor.getString(2)));
                pills.add(pillBoxDataResult);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return pills;
    }

    public ArrayList<PillBoxDataResult> getInActivePills() {
        ArrayList<PillBoxDataResult> pills = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_PILL + " WHERE " + pill_status + "='INACTIVE'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                PillBoxDataResult pillBoxDataResult = new PillBoxDataResult();
                pillBoxDataResult.setPillName(cursor.getString(1));
                pillBoxDataResult.setPhotoId(Integer.parseInt(cursor.getString(2)));
                pills.add(pillBoxDataResult);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return pills;
    }


    public void addReminderToPill(Integer reminderId, String pillName) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("INSERT INTO " + TABLE_PILL_REMINDER +
                        "(" + pill_reminder_pillid + "," +
                        pill_reminder_reminderid + ")" +
                        "VALUES((SELECT " + pill_id +
                        " FROM " + TABLE_PILL +
                        " WHERE " + pill_name + "=?),?)",
                new Object[]{pillName, reminderId});
        db.close();
    }

    public void delete(Integer id) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("UPDATE " + TABLE_PILL +
                " SET " + pill_status + "='INACTIVE' WHERE " +
                pill_id + "=" + id);

        db.close();
    }
}
