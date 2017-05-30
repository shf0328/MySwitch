package huafeng.me.myswitch

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import android.graphics.drawable.Drawable
import android.util.Base64
import java.io.ByteArrayInputStream
import android.graphics.Bitmap
import java.io.ByteArrayOutputStream


class SettingActivity : AppCompatActivity(), View.OnClickListener {

    var currentField: String = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        findViewById(R.id.button_logic).setOnClickListener(this);
        findViewById(R.id.button_security).setOnClickListener(this);
        findViewById(R.id.button_teamwork).setOnClickListener(this);
        findViewById(R.id.button_bachelor).setOnClickListener(this);
        findViewById(R.id.button_aggressive).setOnClickListener(this);
        findViewById(R.id.button_parent).setOnClickListener(this);
        findViewById(R.id.button_spouse).setOnClickListener(this);
        findViewById(R.id.button_avoider).setOnClickListener(this);
        findViewById(R.id.button_change).setOnClickListener(this);
    }

    override fun onClick(v: View?) {
        if (v == null){
            return
        }

        when (v.id){
            R.id.button_logic ->{
                showSavedImage(getString(R.string.str_logic))
            }
            R.id.button_security ->{
                showSavedImage(getString(R.string.str_security))
            }
            R.id.button_teamwork ->{
                showSavedImage(getString(R.string.str_teamwork))
            }
            R.id.button_bachelor ->{
                showSavedImage(getString(R.string.str_bachelor))
            }
            R.id.button_aggressive ->{
                showSavedImage(getString(R.string.str_aggressive))
            }
            R.id.button_parent ->{
                showSavedImage(getString(R.string.str_parent))
            }
            R.id.button_spouse ->{
                showSavedImage(getString(R.string.str_spouse))
            }
            R.id.button_avoider ->{
                showSavedImage(getString(R.string.str_avoider))
            }
            R.id.button_change ->{
                if (currentField != ""){
                    val intent = Intent()
                    intent.type = "image/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 22)
                }else {
                    Toast.makeText(applicationContext, "Select one first", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    fun showSavedImage(f:String) {
        currentField = f

        val tv = findViewById(R.id.textViewChange) as TextView
        tv.text = currentField

        val sharedPreferences = getSharedPreferences(getString(R.string.sharedpreference), MODE_PRIVATE)
        val data:String =  sharedPreferences.getString(currentField, "")
        val imageView = findViewById(R.id.image_setting) as ImageView

        if (data != ""){
            try {
                val bys = ByteArrayInputStream(Base64.decode(data, Base64.DEFAULT))
                imageView.setImageDrawable(Drawable.createFromStream(bys, currentField))
            } catch (e:Exception) {
                Log.e("My Switch", e.message)
            }
        }else{
            imageView.setImageResource(R.drawable.pic_question)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 22 && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                Toast.makeText(applicationContext, "You should select a picture", Toast.LENGTH_SHORT).show();
                return
            }
            val inputStream = applicationContext.getContentResolver().openInputStream(data.data)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val imageView = findViewById(R.id.image_setting) as ImageView
            imageView.setImageBitmap(bitmap)


            val sharedPreferences = getSharedPreferences(getString(R.string.sharedpreference), MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            editor.putString(currentField, Base64.encodeToString(byteArray, Base64.DEFAULT))
            editor.apply()
        }
    }
}
