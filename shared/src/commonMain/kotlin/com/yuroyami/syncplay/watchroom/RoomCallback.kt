package com.yuroyami.syncplay.watchroom

interface RoomCallback {

    fun onLeave()

    fun onPlayback(paused: Boolean)

}