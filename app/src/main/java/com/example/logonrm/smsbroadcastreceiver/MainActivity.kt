package com.example.logonrm.smsbroadcastreceiver

import android.Manifest

import android.content.BroadcastReceiver

import android.content.Context

import android.content.Intent

import android.content.IntentFilter

import android.content.pm.PackageManager

import android.support.v7.app.AppCompatActivity

import android.os.Bundle

import android.support.v4.app.ActivityCompat

import android.support.v4.content.ContextCompat

import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {



    private var mReceiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)



        if (intent != null) {

            val i = getIntent()

            val remetente = i.getStringExtra("remetente")

            val mensagem = i.getStringExtra("mensagem")

            tvMensagem!!.text = if (remetente == null && mensagem == null) "" else remetente + " : " + mensagem

        }



        requestSmsPermission()

    }



    fun requestSmsPermission() {

        val permission = Manifest.permission.RECEIVE_SMS

        val grant = ContextCompat.checkSelfPermission(this, permission)



        if(grant != PackageManager.PERMISSION_GRANTED) {

            val permission_list = arrayOfNulls<String>(1)

            permission_list[0] = permission

            ActivityCompat.requestPermissions(this, permission_list, 1)

        }

    }



    override fun onResume() {

        super.onResume()



        val intentFilter = IntentFilter("android.intent.action.SMSRECEBIDO")



        mReceiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {

                val remetente = intent.getStringExtra("remetente")

                val mensagem  = intent.getStringExtra("mensagem")

                tvMensagem.text = remetente + " : " + mensagem

            }



        }



        this.registerReceiver(mReceiver, intentFilter)

    }

}