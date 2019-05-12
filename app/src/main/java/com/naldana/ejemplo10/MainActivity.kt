package com.naldana.ejemplo10

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.BaseAdapter
import com.google.gson.Gson
import com.naldana.ejemplo10.utilities.NetworkUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

import java.io.IOException
import java.net.URL

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var twoPane =  false

    private lateinit var viewAdapter : CoinAdapter
    private lateinit var viewManager : LinearLayoutManager
    private var listaMonedas : ArrayList<Coin> = ArrayList<Coin>()
    //private var coinList = ArrayList<Coin>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_main)
        // TODO (9) Se asigna a la actividad la barra personalizada
        this.setSupportActionBar(this.toolbar)


        // TODO (10) Click Listener para el boton flotante
        this.fab.setOnClickListener { view ->
            Snackbar.make(view, "Banco de los Trabajadores Salvadoreños S.A. de C.V. ©", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }




        // TODO (11) Permite administrar el DrawerLayout y el ActionBar
        // TODO (11.1) Implementa las caracteristicas recomendas
        // TODO (11.2) Un DrawerLayout (drawer_layout)
        // TODO (11.3) Un lugar donde dibujar el indicador de apertura (la toolbar)
        // TODO (11.4) Una String que describe el estado de apertura
        // TODO (11.5) Una String que describe el estado cierre
        val toggle = ActionBarDrawerToggle(
            this, this.drawer_layout, this.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        // TODO (12) Con el Listener Creado se asigna al  DrawerLayout
        this.drawer_layout.addDrawerListener(toggle)


        // TODO(13) Se sincroniza el estado del menu con el LISTENER
        toggle.syncState()

        // TODO (14) Se configura el listener del menu que aparece en la barra lateral
        // TODO (14.1) Es necesario implementar la inteface {{@NavigationView.OnNavigationItemSelectedListener}}
        this.nav_view.setNavigationItemSelectedListener(this)

        // TODO (20) Para saber si estamos en modo dos paneles
        if (this.fragment_content != null ){
            this.twoPane =  true
            println("Le diste vuelta a la pantalla xd")
        }
    }


    fun initRecycler(coins : ArrayList<Coin>) {
        //viewManager = LinearLayoutManager(this)
        if(this.resources.configuration.orientation == 2
            || this.resources.configuration.orientation == 4){
            viewManager = LinearLayoutManager(this)
        } else{
            viewManager = GridLayoutManager(this, 2)
        }
        viewAdapter = CoinAdapter(coins, { item: Coin -> itemClick(item) })

        recyclerview.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

    }

    fun coinEvent(element:Coin){

    }

    private inner class FetchCoins() : AsyncTask<String,Void, String >(){
        override fun doInBackground(vararg params: String?): String {
            val url : URL = NetworkUtils.buildUrl();
            try{
                var response : String = NetworkUtils.getResponseFromHttpUrl(url)
                var gson : Gson = Gson()
                var coins : AllCoins = gson.fromJson(response,AllCoins::class.java)
                for(i in 0 .. (coins.datos.size-1)){
                    var moneda : Coin = Coin(coins.datos.get(i).value,coins.datos.get(i).value_us,coins.datos.get(i).year,
                        coins.datos.get(i).review,coins.datos.get(i).isAvaliable,coins.datos.get(i).img,coins.datos.get(i)._id,
                        coins.datos.get(i).name,coins.datos.get(i).country,coins.datos.get(i).__v,coins.datos.get(i).imgBanderaPais)
                    listaMonedas.add(moneda)
                }
                return response
            }
            catch(e: IOException){
                e.printStackTrace()
                return ""
            }
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            viewManager = LinearLayoutManager(this@MainActivity)
            viewAdapter= CoinAdapter(listaMonedas,{element:Coin -> coinEvent(element)})
            recyclerview.apply { setHasFixedSize(true)
                layoutManager= viewManager
                adapter=viewAdapter
            }
        }


    }


    private fun itemClick(item: Coin) {

    }




    // TODO (16) Para poder tener un comportamiento Predecible
    // TODO (16.1) Cuando se presione el boton back y el menu este abierto cerralo
    // TODO (16.2) De lo contrario hacer la accion predeterminada
    override fun onBackPressed() {
        if (this.drawer_layout.isDrawerOpen(GravityCompat.START)) {
            this.drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    // TODO (17) LLena el menu que esta en la barra. El de tres puntos a la derecha
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menuInflater.inflate(R.menu.main, menu)
        return true
    }

    // TODO (18) Atiende el click del menu de la barra
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    // TODO (14.2) Funcion que recibe el ID del elemento tocado
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            // TODO (14.3) Los Id solo los que estan escritos en el archivo de MENU
            R.id.nav_camera -> {
                println("Camera")
            }
            R.id.nav_gallery -> {
                println("Galería")
            }
            R.id.nav_slideshow -> {
                println("slide")
            }
            R.id.nav_manage -> {
                println("manejar")
            }
            R.id.nav_share -> {
                println("compartir")
            }
            R.id.nav_send -> {
                println("enviar")
            }
        }

        // TODO (15) Cuando se da click a un opcion del menu se cierra de manera automatica
        this.drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
