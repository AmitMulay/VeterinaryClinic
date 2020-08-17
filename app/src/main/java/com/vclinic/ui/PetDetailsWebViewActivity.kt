package com.vclinic.ui

import android.annotation.SuppressLint
import android.net.http.SslError
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.vclinic.R
import com.vclinic.ui.constant.ApplicationConstant
import kotlinx.android.synthetic.main.activity_pet_details_web_view.*

class PetDetailsWebViewActivity : AppCompatActivity() {

    companion object {
        const val MAX_PROGRESS = 100
    }
    private lateinit var pageUrl: String
    private lateinit var pageTitle: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_details_web_view)
        pageUrl = intent.getStringExtra(ApplicationConstant.ARGUMENT_CONTENT_URL)
            ?: throw IllegalStateException("field ${ApplicationConstant.ARGUMENT_CONTENT_URL} missing in Intent")

        pageTitle = intent.getStringExtra(ApplicationConstant.ARGUMENT_TITLE)
            ?: throw IllegalStateException("field ${ApplicationConstant.ARGUMENT_TITLE} missing in Intent")
        initWebView()
        setWebClient()
        loadUrl(pageUrl)

    }

    /**
     * Method to initialise web view
     */
    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        webView.settings.javaScriptEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override
            fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                handler?.proceed()
            }
        }

    }

    /**
     * Method to set web client to web view
     */
    private fun setWebClient() {
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(
                view: WebView,
                newProgress: Int
            ) {
                super.onProgressChanged(view, newProgress)
                progressBar.progress = newProgress
                if (newProgress < MAX_PROGRESS && progressBar.visibility == ProgressBar.GONE) {
                    progressBar.visibility = ProgressBar.VISIBLE
                }
                if (newProgress == MAX_PROGRESS) {
                    progressBar.visibility = ProgressBar.GONE
                }
            }
        }
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        // If it wasn't the Back key or there's no web page history, exit the activity)
        return super.onKeyDown(keyCode, event)
    }

    /**
     * Method to load url in web view
     */
    private fun loadUrl(pageUrl: String) {
        webView.loadUrl(pageUrl)
    }
}