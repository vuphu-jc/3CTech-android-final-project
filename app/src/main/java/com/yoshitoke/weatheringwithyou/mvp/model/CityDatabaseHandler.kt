package com.yoshitoke.weatheringwithyou.mvp.model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import com.yoshitoke.weatheringwithyou.mvp.model.DataClass.City

class CityDatabaseHandler(context: Context) : SQLiteOpenHelper(context, DBName, null, DBVersion) {


    companion object {

        val Tag = "CityDatabaseHandler"
        val DBName = "CityDB"
        val DBVersion = 1

        val tableName = "cityTable"
        val cityID = "id"
        val cityName = "name"
        val cityLat = "latitude"
        val cityLon = "longitude"
    }

    var context: Context? = context
    var sqlObj: SQLiteDatabase

    init {
        sqlObj = this.writableDatabase
    }

    override fun onCreate(sqlite: SQLiteDatabase?) {

        //SQL for creating table
        val sql1: String = "CREATE TABLE IF NOT EXISTS " + tableName + " " +
                "(" + cityID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                cityName + " TEXT, " + cityLat + " TEXT, " + cityLon +
                " TEXT );"

        sqlite?.execSQL(sql1);
    }

    override fun onUpgrade(sqlite: SQLiteDatabase?, p1: Int, p2: Int) {

        sqlite?.execSQL("Drop table IF EXISTS " + tableName)
        onCreate(sqlite)

    }

    fun AddCity(values: ContentValues): String {

        var Msg: String = "error";
        val ID = sqlObj.insert(tableName, "", values)

        if (ID > 0) {
            Msg = "ok"
        }
        return Msg
    }

    fun FetchCities(keyword: String): List<City> {

        val arraylist = ArrayList<City>()

        val sqb = SQLiteQueryBuilder()
        sqb.tables = tableName
        val cols = arrayOf("id", "name", "latitude", "longitude")
        val rowSelArg = arrayOf(keyword)

        val cur = sqb.query(sqlObj, cols, "name like ?", rowSelArg, null, null, "name")

        if (cur.moveToFirst()) {

            do {
                //val id = cur.getInt(cur.getColumnIndex("id"))
                val cityName = cur.getString(cur.getColumnIndex("name"))
                val cityLat = cur.getString(cur.getColumnIndex("latitude"))
                val cityLon = cur.getString(cur.getColumnIndex("longitude"))

                //add id
                arraylist.add(City(cityName, cityLon.toDouble(), cityLat.toDouble()))

            } while (cur.moveToNext())
        }

        return arraylist.reversed()
    }

    fun UpdateCity(values: ContentValues, id: Int): String {

        val selectionArs = arrayOf(id.toString())

        val i = sqlObj.update(tableName, values, "id=?", selectionArs)
        if (i > 0) {
            return "ok";
        } else {

            return "error";
        }
    }

    fun RemoveCity(id: Int): String {

        val selectionArs = arrayOf(id.toString())

        val i = sqlObj.delete(tableName, "id=?", selectionArs)
        if (i > 0) {
            return "ok";
        } else {

            return "error";
        }
    }
}
