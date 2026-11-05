package com.cpo.ut6_08laboratorio_elespejodegaladriel

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

// el espejo de galadriel: un webview nativo que incrusta el streaming del palantir
class MainActivity : AppCompatActivity() {

    // lateinit para poder usar la variable antes de que sea asignada
    private lateinit var wvEspejo: WebView

    // suppresslint: android avisa de que activar javascript puede ser inseguro.
    // aqui lo necesitamos para el reproductor de youtube, asi que quitamos el aviso
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // enlazamos la variable con el webview del xml usando su id
        wvEspejo = findViewById(R.id.wvEspejo)

        // webviewclient: el streaming se abre DENTRO de la app y no en el navegador del movil.
        // webchromeclient: permite reproducir el video (controles, pantalla completa...)
        wvEspejo.webViewClient = WebViewClient()
        wvEspejo.webChromeClient = WebChromeClient()

        // javascript: imprescindible, el reproductor de youtube esta hecho con js
        wvEspejo.settings.javaScriptEnabled = true
        // domstorage: deja al reproductor guardar su configuracion local
        wvEspejo.settings.domStorageEnabled = true
        // responsivo: la pagina se ajusta al ancho de la pantalla del dispositivo
        wvEspejo.settings.useWideViewPort = true
        wvEspejo.settings.loadWithOverviewMode = true

        // cargamos el reproductor del palantir. la direccion /embed/ es la version
        // del video de youtube pensada para incrustarse en otras paginas o apps.
        // el segundo parametro son cabeceras http extra: youtube exige saber desde
        // donde se incrusta su reproductor (cabecera referer). sin ella muestra
        // "error 153 - video player configuration error" y no reproduce nada.
        // como una app no tiene pagina web, nos identificamos con el nombre del
        // paquete (packagename). si ponemos youtube.com da "error 152 - 4"
        wvEspejo.loadUrl(
            "https://www.youtube.com/embed/R4qfx3ZMSB8",
            mapOf("Referer" to "https://$packageName")
        )
    }
}
