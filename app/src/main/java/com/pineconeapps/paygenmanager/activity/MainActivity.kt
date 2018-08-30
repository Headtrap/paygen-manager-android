package com.pineconeapps.paygenmanager.activity

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.widget.TextView
import com.pineconeapps.paygenmanager.R
import com.pineconeapps.paygenmanager.fragment.CustomerListFragment
import com.pineconeapps.paygenmanager.prefs
import com.pineconeapps.paygenmanager.util.ImageUtil.load
import com.pineconeapps.paygenmanager.util.UserInfo
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var tvName: TextView
    lateinit var logoImage: CircleImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        val header = nav_view.getHeaderView(0)
        logoImage = header.findViewById(R.id.headerImage)
        tvName = header.findViewById(R.id.tvName)
        fragmentManager.inTransaction { add(R.id.container, CustomerListFragment()) }

    }

    override fun onStart() {
        super.onStart()
        tvName.text = prefs.userName
        logoImage.load(prefs.picture) { request -> request.fit() }

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_employees -> {
                startActivity<EmployeesActivity>()
            }
            R.id.nav_products -> {
                startActivity<ProductsActivity>()
            }
            R.id.nav_images -> {
                startActivity<ImageActivity>()
            }
            R.id.nav_reports -> {
                startActivity<SalesActivity>()
            }
            R.id.nav_send -> {
                UserInfo.clearData()
                startActivity<SplashActivity>()
                finish()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return false
    }
}
