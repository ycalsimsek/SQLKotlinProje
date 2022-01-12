package com.umit.sqlitekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.umit.sqlitekotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
   lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val context = this
        var db = DataBaseHelper(context)

        binding.btnkaydet.setOnClickListener {
            var etadsoyad = binding.etadsoyad.text.toString()
            var etyas = binding.etyas.text.toString()

            if (etadsoyad.isNotEmpty() && etyas.isNotEmpty()){
                var kullanici = kullanici(etadsoyad,etyas.toInt())
                db.insertData(kullanici)

            }else{
                Toast.makeText(applicationContext,"Lütfen boş alanları dolduralım.",Toast.LENGTH_LONG).show()
            }
        }
    //verileri okumak için
        binding.btnoku.setOnClickListener {
            var data = db.readData()
            binding.tvsonuc.text = ""
            for(i in 0 until data.size) {
                binding.tvsonuc.append(data.get(i).id.toString() + " "
                            +data.get(i).adsoyad + " " + data.get(i).yasi + "\n")
            }
        }
        //verileri güncelleme
        binding.btnguncelle.setOnClickListener {
            db.updateData()
            binding.btnguncelle.performClick()
        }
        //verileri silmek için
        binding.btnsil.setOnClickListener {
            db.deleteData()
            binding.btnoku.performClick()
        }
    }
}