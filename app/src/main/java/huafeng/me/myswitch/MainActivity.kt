package huafeng.me.myswitch

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.content.Intent
import android.util.Log
import android.graphics.drawable.Drawable
import android.util.Base64
import android.widget.ImageView
import java.io.ByteArrayInputStream


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)


        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_logic -> {
                showSavedImageById(R.string.str_logic)
            }
            R.id.nav_security -> {
                showSavedImageById(R.string.str_security)
            }
            R.id.nav_teamwork -> {
                showSavedImageById(R.string.str_teamwork)
            }
            R.id.nav_aggressive -> {
                showSavedImageById(R.string.str_aggressive)
            }
            R.id.nav_bachelor -> {
                showSavedImageById(R.string.str_bachelor)
            }
            R.id.nav_spouse -> {
                showSavedImageById(R.string.str_spouse)
            }
            R.id.nav_parent -> {
                showSavedImageById(R.string.str_parent)
            }
            R.id.nav_avoider -> {
                showSavedImageById(R.string.str_avoider)
            }
            R.id.nav_setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    fun showSavedImageById(fid:Int) {
        val currentField = getString(fid)
        val sharedPreferences = getSharedPreferences(getString(R.string.sharedpreference), MODE_PRIVATE)
        val data:String =  sharedPreferences.getString(currentField, "")
        val imageView = findViewById(R.id.imageMain) as ImageView
        if (data != ""){
            try {
                val bys = ByteArrayInputStream(Base64.decode(data, Base64.DEFAULT))
                imageView.setImageDrawable(Drawable.createFromStream(bys, getString(fid)))
            } catch (e:Exception) {
                Log.e("My Switch", e.message)
            }
        }else{
            imageView.setImageResource(R.drawable.pic_question)
        }
    }
}
