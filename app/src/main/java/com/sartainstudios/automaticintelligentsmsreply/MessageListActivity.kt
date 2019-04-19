package com.sartainstudios.automaticintelligentsmsreply

import android.support.v7.widget.LinearLayoutManager
import android.R
import android.support.v7.widget.RecyclerView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View


class MessageListActivity : AppCompatActivity() {
    private var mMessageRecycler: RecyclerView? = null
    private var mMessageAdapter: MessageListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_list)

        mMessageRecycler = findViewById<View>(R.id.reyclerview_message_list)
        mMessageAdapter = MessageListAdapter(this, messageList)
        mMessageRecycler!!.layoutManager = LinearLayoutManager(this)
    }
}
