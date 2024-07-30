package com.reven.revenutil.utils;//package com.nobo.noboble.utils;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteDatabase.CursorFactory;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import com.blankj.utilcode.util.LogUtils;
//import com.nobo.noboble.model.DbColorModel;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * SQLite工具类
// * <p>
// * by 于交龙 at 2014-11-17
// */
//public class SQLiteUtils
//{
//    private static SQLiteUtils sSQLiteUtils = null;
//
//    private SQLiteHelper mSQLiteHelper = null;
//
//    private SQLiteDatabase mSQLiteDatabase = null;
//
//    /**
//     * 获取SQLiteUtils类的唯一实例
//     *
//     * @param context          当前应用的Context
//     * @param databaseFileName 数据库的名字
//     * @return SQLiteUtils类的唯一实例
//     */
//    public static SQLiteUtils getInstance(Context context, String databaseFileName)
//    {
//        if (sSQLiteUtils == null)
//        {
//            synchronized (SQLiteUtils.class)
//            {
//                if (sSQLiteUtils == null)
//                {
//                    sSQLiteUtils = new SQLiteUtils(context, databaseFileName);
//                }
//            }
//        }
//
//        return sSQLiteUtils;
//    }
//
//    /**
//     * 隐式构造函数
//     *
//     * @param context          当前应用的Context
//     * @param databaseFileName 数据库文件的名字
//     */
//    private SQLiteUtils(Context context, String databaseFileName)
//    {
//        mSQLiteHelper = new SQLiteHelper(context, databaseFileName);
//
//        mSQLiteDatabase = mSQLiteHelper.getWritableDatabase();
//    }
//
//    /**
//     * 关闭SQLite数据库(不使用数据库或需要创建新的数据库文件时调用)
//     */
//    public void closeSQLiteDatabase()
//    {
//        if (mSQLiteHelper != null)
//        {
//            mSQLiteHelper.close();
//            mSQLiteHelper = null;
//            sSQLiteUtils = null;
//        }
//    }
//
//    /**
//     * 清空上传列表
//     */
//    public void clearDbColorRecord(String type)
//    {
//        mSQLiteDatabase.delete("t_color", "id = ?", new String[]{type});
//    }
//
//    /**
//     * 插入上传记录
//     *
//     * @param model DbColorModel
//     */
//    public void insertDbHost(DbColorModel model)
//    {
//        LogUtils.d("插入数据库：" + model.getId());
//        ContentValues contentValues = new ContentValues();
////        id;//门板1、星空顶2
////        driveModel;//驾驶模式
////        receModel;//迎宾模式
////        leisure;//休闲模式
////        rest;//休息模式
////        play;//娱乐模式
////        skyDrive;//星空顶驾驶模式
////        skyPlay;//星空顶娱乐模式
////        skyMeeting;//星空顶会议
////        skySleep;//星空顶睡眠模式
////        doorFlow;//门板流动模式
//        contentValues.put("id", model.getId());
//        contentValues.put("drive", model.getDrive());
//        contentValues.put("rece", model.getRece());
//        contentValues.put("leisure", model.getLeisure());
//        contentValues.put("rest", model.getRest());
//        contentValues.put("play", model.getPlay());
//        contentValues.put("skyDrive", model.getSkyDrive());
//        contentValues.put("skyPlay", model.getSkyPlay());
//        contentValues.put("skyMeeting", model.getSkyMeeting());
//        contentValues.put("skySleep", model.getSkySleep());
//        contentValues.put("doorFlow", model.getDoorFlow());
//
//        mSQLiteDatabase.insertWithOnConflict("t_color", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
//    }
//
//    public void updateModeColor(String id, String mode, String color)
//    {
//        LogUtils.d("修改数据库：" + mode);
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(mode, color);
//
//        mSQLiteDatabase.updateWithOnConflict("t_color", contentValues, "id = ?", new String[]{
//                                                     id},
//                                             SQLiteDatabase.CONFLICT_REPLACE);
//    }
//
//    public List<DbColorModel> getColor()
//    {
//        LogUtils.d("获取数据库");
//        List<DbColorModel> list = new ArrayList<>();
//        Cursor cursor = mSQLiteDatabase.query("t_color", null, null, null, null, null, null);
//
//        if (cursor.moveToFirst())
//        {
//            int count = cursor.getCount();
//            for (int i = 0; i < count; i++)
//            {
//                //        id;//门板1、星空顶2
//                //        driveModel;//驾驶模式
//                //        receModel;//迎宾模式
//                //        leisure;//休闲模式
//                //        rest;//休息模式
//                //        play;//娱乐模式
//                //        skyDrive;//星空顶驾驶模式
//                //        skyPlay;//星空顶娱乐模式
//                //        skyMeeting;//星空顶会议
//                //        skySleep;//星空顶睡眠模式
//                //        doorFlow;//门板流动模式
//                DbColorModel model = new DbColorModel();
//                cursor.moveToPosition(i);
//                model.setId(cursor.getString(0));
//                model.setDrive(cursor.getString(1));
//                model.setRece(cursor.getString(2));
//                model.setLeisure(cursor.getString(3));
//                model.setRest(cursor.getString(4));
//                model.setPlay(cursor.getString(5));
//                model.setSkyDrive(cursor.getString(6));
//                model.setSkyPlay(cursor.getString(7));
//                model.setSkyMeeting(cursor.getString(8));
//                model.setSkySleep(cursor.getString(9));
//                model.setDoorFlow(cursor.getString(10));
//                list.add(model);
//            }
//        }
//
//        return list;
//    }
//}
//
///**
// * 自定义SQLite管理类
// * <p>
// * by 于交龙 at 2014-11-17
// */
//class SQLiteHelper extends SQLiteOpenHelper
//{
//    /**
//     * SQLite数据库版本
//     */
//    private final static int SQLITE_DATA_BASE_VERSION = 1;
//
//    //        id;//门板1、星空顶2
//    //        drive;//驾驶模式
//    //        rece;//迎宾模式
//    //        leisure;//休闲模式
//    //        rest;//休息模式
//    //        play;//娱乐模式
//    //        skyDrive;//星空顶驾驶模式
//    //        skyPlay;//星空顶娱乐模式
//    //        skyMeeting;//星空顶会议
//    //        skySleep;//星空顶睡眠模式
//    //        doorFlow;//门板流动模式
//    /**
//     * 创建上传表
//     */
//    private final String CREATE_COLOR_TABLE =
//            "create table t_color(id text primary key, "// 0
//            + "drive text,"
//            + "rece text,"
//            + "leisure text,"
//            + "rest text,"
//            + "play text,"
//            + "skyDrive text,"
//            + "skyPlay text,"
//            + "skyMeeting text,"
//            + "skySleep text,"
//            + "doorFlow text)"; // 1
//
//    /**
//     * 构造函数
//     *
//     * @param context          当前应用的Context
//     * @param databaseFileName 数据库文件的名字
//     */
//    public SQLiteHelper(Context context, String databaseFileName)
//    {
//        super(context, databaseFileName, null, SQLITE_DATA_BASE_VERSION);
//    }
//
//    /**
//     * 构造函数
//     */
//    public SQLiteHelper(Context context, String name, CursorFactory factory, int version)
//    {
//        super(context, name, factory, version);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db)
//    {
//        // 创建所需的数据表
//        createDataTables(db);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
//    {
//    }
//
//    /**
//     * 创建数据表
//     *
//     * @param sqliteDatabase SQLiteDatabase对象
//     */
//    public void createDataTables(SQLiteDatabase sqliteDatabase)
//    {
//        // 创建上传表
//        sqliteDatabase.execSQL(CREATE_COLOR_TABLE);
//    }
//}
