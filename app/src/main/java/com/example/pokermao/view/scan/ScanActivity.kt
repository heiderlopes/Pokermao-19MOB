package com.example.pokermao.view.scan

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.pokermao.BaseScanActivity
import com.example.pokermao.R
import com.example.pokermao.view.detail.DetailActivity
import com.google.zxing.Result
import kotlinx.android.synthetic.main.activity_scan.*
import kotlinx.android.synthetic.main.include_permissions.*
import me.dm7.barcodescanner.zxing.ZXingScannerView


class ScanActivity : BaseScanActivity(), ZXingScannerView.ResultHandler {
    override val baseScannerView: ZXingScannerView?
        get() = mScannerView

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        btPermission.setOnClickListener {
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:$packageName")
            )
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            startActivity(intent)
        }

        super.requestPermission()
    }

    public override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            containerPermission.visibility = View.GONE
            mScannerView.setResultHandler(this)
            mScannerView.startCamera()
        } else {
            containerPermission.visibility = View.VISIBLE
        }
    }

    override fun onPermissionDenied() {
        containerPermission.visibility = View.VISIBLE
    }

    override fun onPermissionGranted() {
        containerPermission.visibility = View.GONE
    }

    override fun handleResult(rawResult: Result?) {
        val pokemonNumber = rawResult?.text
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("POKEMON_NUMBER", pokemonNumber)
        startActivity(intent)
        finish()
    }
}

