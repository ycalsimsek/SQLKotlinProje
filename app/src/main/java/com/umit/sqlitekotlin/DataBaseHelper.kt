package com.umit.sqlitekotlin

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.util.ArrayList


val database_name = "Veritabanim"
val table_name = "Kullanicilar"
val col_name = "adisoyadi"
val col_age = "yasi"
val col_id = "id"

class DataBaseHelper (var context: Context):SQLiteOpenHelper(context,
database_name, null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        var createTable = " CREATE TABLE "+ table_name+"("+
                col_id +" INTEGER PRİMARY KEY AUTOINCREMENT,"+
                col_name +" VARCHAR(256),"+
                col_age +" INTEGER)"

        db?.execSQL(createTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
    //kaydetmek
    fun insertData(kullanici: kullanici){
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(col_name,kullanici.adsoyad)
        cv.put(col_age,kullanici.yasi)
        var sonuc = db.insert(table_name,null,cv)
        if (sonuc == (-1).toLong()){
            Toast.makeText(context,"Hatalı",Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(context,"Başarılı",Toast.LENGTH_LONG).show()
        }
    }

    //verileri okumak için fonksiyon tanımlama
    @SuppressLint("Range")
    fun readData(): MutableList<kullanici> {
        var liste:MutableList<kullanici> = ArrayList()
        val db = this.readableDatabase
        var sorgu = "Select * from "+ table_name
        var sonuc = db.rawQuery(sorgu,null)
        if (sonuc.moveToFirst()){
            do {
                var kullanici = kullanici()
                kullanici.id = sonuc.getString(sonuc.getColumnIndex(col_id)).toInt()
                kullanici.adsoyad = sonuc.getString(sonuc.getColumnIndex(col_name))
                kullanici.yasi = sonuc.getString(sonuc.getColumnIndex(col_age)).toInt()
                liste.add(kullanici)
            }while (sonuc.moveToNext())
        }
        sonuc.close()
        db.close()
        return liste

    }
    //güncellemek için
    @SuppressLint("Range")
    fun updateData(){
        val db = this.readableDatabase
        var sorgu =  "Select * from $table_name"
        var sonuc = db.rawQuery(sorgu,null)
        if (sonuc.moveToFirst()){
            do {
                var cv = ContentValues()
                cv.put(col_age,(sonuc.getInt(sonuc.getColumnIndex(col_age)))+1)
                cv.put(col_name,(sonuc.getString(sonuc.getColumnIndex(col_name)))+" "+"Güncel")
                db.update(table_name,cv, "$col_id=? AND $col_name=?",
                arrayOf(sonuc.getString(sonuc.getColumnIndex(col_id)),
                sonuc.getString(sonuc.getColumnIndex(col_name))))
            }while (sonuc.moveToNext())
        }
        sonuc.close()
        db.close()
    }
    //verilerimizi silmek için
    fun deleteData(){
        val db = this.writableDatabase
        db.delete(table_name,null,null)
        db.close()
    }
}
