package com.dorukaneskiceri.currencyconvertkotlin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_features.*

class FeaturesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_features)

        val myAdapter = MyCustomAdapter(this)
        listView.adapter = myAdapter

    }

    inner class MyCustomAdapter(context: Context): BaseAdapter(){

        private val names = arrayListOf<String>("Google Maps", "Şarj Olma Durumu", "Fotoğraf Çekme ve Yükleme", "Galeriden Fotoğraf Seçme",
            "Hareket Sensörü")
        private val myContext = context

        init {
            myContext
        }

        //her satır için değişecek olan tasarımım
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(myContext)
            val rowFeatures = layoutInflater.inflate(R.layout.row_features,parent,false)

            val nameTextView = rowFeatures.findViewById<TextView>(R.id.textName)
            nameTextView.text = names.get(position)

            listView.setOnItemClickListener { parent, view, position, id ->
                if(position == 0){
                    val intent = Intent(myContext,MapsActivity::class.java)
                    startActivity(intent)
                }
                if(position == 1){
                    val intent = Intent(myContext,BatteryActivity::class.java)
                    startActivity(intent)
                }
                if(position == 2){
                    val intent = Intent(myContext,CameraActivity::class.java)
                    startActivity(intent)
                }
                if(position == 3){
                    val intent = Intent(myContext,GalleryActivity::class.java)
                    startActivity(intent)
                }
                if(position == 4){
                    val intent = Intent(myContext,SensorActivity::class.java)
                    startActivity(intent)
                }
            }

            // İkinci bir text olduğunda yapılabilir
//           val googleMapText = googleText.text
//           googleMapText = "Row Number $position"

            return rowFeatures
        }

        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        // listemdeki eleman sayıma göre değişecek olan tasarımım
        override fun getCount(): Int {
            return names.size
        }
    }
}
