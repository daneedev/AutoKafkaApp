package dev.daneeskripter.cernyrobin

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarItemView

class MainActivity : AppCompatActivity() {

    private lateinit var builder: AlertDialog.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        builder = AlertDialog.Builder(this)

        checkInternetAndHandle(HomeFragment())

        val navigationMenu = findViewById<BottomNavigationView>(R.id.BottomNavigationView)
        navigationMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeBtn -> {
                    checkInternetAndHandle(HomeFragment())
                    true
                }
                R.id.infoBtn -> {
                    checkInternetAndHandle(InfoFragment())
                    true
                }
                else -> false
            }
        }

    }
    fun replaceFragment(fragment: Fragment) {
        val fragmentMgr = supportFragmentManager
        val fragmentTransaction = fragmentMgr.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }

    private fun checkInternetAndHandle(fragment: Fragment) {
        if (InternetChecker().checkForInternet(this)) {
            // Internet is available, replace fragment or perform necessary action
            replaceFragment(fragment)
        } else {
            // Internet is not available, show dialog with "Try Again" button
            builder.setTitle("Internet není k dispozici")
                .setMessage("Připojte se prosím k internetu")
                .setCancelable(false)
                .setPositiveButton("Zkusit znovu") { _: DialogInterface, _: Int ->
                checkInternetAndHandle(fragment)
                }
                .show()
        }
    }
}