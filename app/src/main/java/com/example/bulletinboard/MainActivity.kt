package com.example.bulletinboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.example.bulletinboard.accounthelper.GOOGLE_SIGN_IN_REQUEST_CODE
import com.example.bulletinboard.databinding.ActivityMainBinding
import com.example.bulletinboard.dialoghelper.DialogHelper
import com.example.bulletinboard.dialoghelper.SIGN_IN_STATE_ALERT_DIALOG
import com.example.bulletinboard.dialoghelper.SIGN_UP_STATE_ALERT_DIALOG
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var accountEmail: TextView
    private lateinit var binding: ActivityMainBinding
    private val dialogHelper = DialogHelper(this)

    // Когда запускается активити мы берем  authentication
    val authentication = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        init()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.id_new_ads) {
            val intent = Intent(this, EditAdsAct::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)

    }

    // Для добавления в Toolbar кнопки new в правом верхнем углу
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN_REQUEST_CODE) {

        }
    }

    private fun init() {

        // для того, чтобы работала кнопка new в Toolbar
        setSupportActionBar(binding.mainContent.toolbar)

        val toggle =
            ActionBarDrawerToggle(
                this,
                binding.drawerLayout,
                binding.mainContent.toolbar,
                R.string.open,
                R.string.close
            )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navigationView.setNavigationItemSelectedListener(this)
        accountEmail = binding.navigationView.getHeaderView(0).findViewById(R.id.accountEmail)


    }

    override fun onStart() {
        super.onStart()
        uiUpdate(authentication.currentUser)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.id_my_ads -> {
                Toast.makeText(this, "ads", Toast.LENGTH_SHORT).show()
            }
            R.id.id_cars -> {
                Toast.makeText(this, "cars", Toast.LENGTH_SHORT).show()
            }
            R.id.id_pc -> {
                Toast.makeText(this, "pc", Toast.LENGTH_SHORT).show()
            }
            R.id.id_smartphone -> {
                Toast.makeText(this, "smartphone", Toast.LENGTH_SHORT).show()
            }
            R.id.id_appliances -> {
                Toast.makeText(this, "appliances", Toast.LENGTH_SHORT).show()
            }
            R.id.id_signe_up -> {
                dialogHelper.signDialog(SIGN_UP_STATE_ALERT_DIALOG)
            }

            R.id.id_signe_in -> {
                dialogHelper.signDialog(SIGN_IN_STATE_ALERT_DIALOG)
            }

            R.id.id_signe_out -> {
                uiUpdate(null)
                authentication.signOut()

                Toast.makeText(this, "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show()
            }
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun uiUpdate(user: FirebaseUser?) {
        accountEmail.text = if (user == null) {
            resources.getString(R.string.not_reg)
        } else {
            user.email
        }

    }
}


