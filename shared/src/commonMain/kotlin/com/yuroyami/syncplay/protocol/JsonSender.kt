package com.yuroyami.syncplay.protocol

import com.yuroyami.syncplay.datastore.DataStoreKeys.DATASTORE_GLOBAL_SETTINGS
import com.yuroyami.syncplay.datastore.DataStoreKeys.PREF_HASH_FILENAME
import com.yuroyami.syncplay.datastore.DataStoreKeys.PREF_HASH_FILESIZE
import com.yuroyami.syncplay.datastore.obtainString
import com.yuroyami.syncplay.models.MediaFile
import com.yuroyami.syncplay.utils.md5
import com.yuroyami.syncplay.utils.toHex
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/** This class does not actually send anything but what it actually does is compose JSON strings which will be sent later */
object JsonSender {

    fun sendHello(username: String, roomname: String, serverPassword: String): String {
        val hello: HashMap<String, Any> = hashMapOf()
        hello["username"] = username
        if (serverPassword != "") {
            /* Syncplay servers accept passwords in MD5-Hexadecimal form. */
            hello["password"] = md5(serverPassword).toHex()
        }
        val room: HashMap<String, String> = hashMapOf()
        room["name"] = roomname
        hello["room"] = room
        hello["version"] = "1.6.9"
        val features: HashMap<String, Boolean> = hashMapOf()
        features["sharedPlaylists"] = false
        features["chat"] = true
        features["featureList"] = true
        features["readiness"] = true
        features["managedRooms"] = true
        hello["features"] = features

        val wrapper: HashMap<String, HashMap<String, Any>> = hashMapOf()
        wrapper["Hello"] = hello

        return Json.encodeToString(wrapper)
    }

    fun sendJoined(roomname: String): String {
        val event: HashMap<String, Any> = hashMapOf()
        event["joined"] = true

        val room: HashMap<String, String> = hashMapOf()
        room["name"] = roomname

        val username: HashMap<String, Any> = hashMapOf()
        username["room"] = room
        username["event"] = event

        val user: HashMap<String, Any> = hashMapOf()
        user[roomname] = username

        val wrapper: HashMap<String, HashMap<String, Any>> = hashMapOf()
        wrapper["Set"] = user
        return Json.encodeToString(wrapper)
    }

    fun sendReadiness(isReady: Boolean, manuallyInitiated: Boolean): String {
        val ready: HashMap<String, Boolean> = hashMapOf()
        ready["isReady"] = isReady
        ready["manuallyInitiated"] = manuallyInitiated

        val setting: HashMap<String, Any> = hashMapOf()
        setting["ready"] = ready

        val wrapper: HashMap<String, Any> = hashMapOf()
        wrapper["Set"] = setting

        return Json.encodeToString(wrapper)
    }

    fun sendFile(media: MediaFile): String {
        /** Checking whether file name or file size have to be hashed **/
        val nameBehavior = runBlocking { DATASTORE_GLOBAL_SETTINGS.obtainString(PREF_HASH_FILENAME, "1") }
        val sizeBehavior = runBlocking { DATASTORE_GLOBAL_SETTINGS.obtainString(PREF_HASH_FILESIZE, "1") }

        val fileproperties: HashMap<String, Any> = hashMapOf()
        fileproperties["duration"] = media.fileDuration
        fileproperties["name"] = when (nameBehavior) {
            "1" -> media.fileName
            "2" -> media.fileNameHashed.take(12)
            else -> ""
        }
        fileproperties["size"] = when (sizeBehavior) {
            "1" -> media.fileSize
            "2" -> media.fileSizeHashed.take(12)
            else -> ""
        }

        val file: HashMap<String, Any> = hashMapOf()
        file["file"] = fileproperties

        val wrapper: HashMap<String, Any> = hashMapOf()
        wrapper["Set"] = file

        return Json.encodeToString(wrapper)
    }

    fun sendEmptyList(): String {
        val emptylist: HashMap<String, Any?> = hashMapOf()
        emptylist["List"] = listOf<String>() //TODO:Check

        return Json.encodeToString(emptylist)
    }

    fun sendChat(message: String): String {
        val wrapper: HashMap<String, Any> = hashMapOf()
        wrapper["Chat"] = message

        return Json.encodeToString(wrapper)
    }

    fun sendState(
        servertime: Double?,
        clienttime: Double,
        doSeek: Boolean?,
        seekPosition: Long = 0,
        iChangeState: Int,
        play: Boolean?,
        protocol: SyncplayProtocol,
    ): String {

        val state: HashMap<String, Any?> = hashMapOf()
        val playstate: HashMap<String, Any?> = hashMapOf()
        if (doSeek == true) {
            playstate["position"] = seekPosition.toDouble() / 1000.0
        } else {
            playstate["position"] = protocol.currentVideoPosition.toFloat()
        }
        playstate["paused"] = protocol.paused
        playstate["doSeek"] = doSeek
        val ping: HashMap<String, Any?> = hashMapOf()
        if (servertime != null) {
            ping["latencyCalculation"] = servertime
        }
        ping["clientLatencyCalculation"] = clienttime
        ping["clientRtt"] = protocol.ping.value

        if (iChangeState == 1) {
            val ignore: HashMap<String, Any?> = hashMapOf()
            ignore["client"] = protocol.clientIgnFly
            state["ignoringOnTheFly"] = ignore
            playstate["paused"] = !play!!

        } else {
            if (protocol.serverIgnFly != 0) {
                val ignore: HashMap<String, Any?> = hashMapOf()
                ignore["server"] = protocol.serverIgnFly
                state["ignoringOnTheFly"] = ignore
                protocol.serverIgnFly = 0
            }
        }

        state["playstate"] = playstate
        state["ping"] = ping

        val statewrapper: HashMap<String, Any?> = hashMapOf()
        statewrapper["State"] = state

        return Json.encodeToString(statewrapper)
    }

    fun sendPlaylistChange(list: List<String>): String {
        val files: HashMap<String, Any?> = hashMapOf()
        files["files"] = list

        val playlistChange: HashMap<String, Any?> = hashMapOf()
        playlistChange["playlistChange"] = files

        val set: HashMap<String, Any?> = hashMapOf()
        set["Set"] = playlistChange

        return Json.encodeToString(set)
    }

    fun sendPlaylistIndex(i: Int): String {
        val index: HashMap<String, Any?> = hashMapOf()
        index["index"] = i

        val playlistIndex: HashMap<String, Any?> = hashMapOf()
        playlistIndex["playlistIndex"] = index

        val set: HashMap<String, Any?> = hashMapOf()
        set["Set"] = playlistIndex

        return Json.encodeToString(set)
    }

    fun sendTLS(): String {
        val tls: HashMap<String, String> = hashMapOf()
        tls["startTLS"] = "send"

        val wrapper: HashMap<String, Any> = hashMapOf()
        wrapper["TLS"] = tls

        return Json.encodeToString(wrapper)
    }


}