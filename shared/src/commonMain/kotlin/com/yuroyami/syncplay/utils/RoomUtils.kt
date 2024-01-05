package com.yuroyami.syncplay.utils

import com.yuroyami.syncplay.models.Message
import com.yuroyami.syncplay.protocol.JsonSender
import com.yuroyami.syncplay.settings.DataStoreKeys.PREF_FILE_MISMATCH_WARNING
import com.yuroyami.syncplay.settings.valueBlockingly
import com.yuroyami.syncplay.watchroom.isSoloMode
import com.yuroyami.syncplay.watchroom.lyricist
import com.yuroyami.syncplay.watchroom.viewmodel
import kotlinx.coroutines.launch

/** Methods exclusive to Room functionality (messages, sending data to server, etc) */
object RoomUtils {

    /** Sends a play/pause playback to the server **/
    fun sendPlayback(play: Boolean) {
        if (isSoloMode) return

        viewmodel?.p?.sendPacket(
            JsonSender.sendState(
                servertime = null, clienttime = generateTimestampMillis() / 1000.0,
                doSeek = null, seekPosition = 0, iChangeState = 1, play = play
            )
        )
    }

    fun sendSeek(newpos: Long) {
        if (isSoloMode) return

        viewmodel?.player?.playerScopeMain?.launch {
            viewmodel?.p?.sendPacket(
                JsonSender.sendState(
                    null, (generateTimestampMillis() / 1000.0), true,
                    newpos, 1,
                    play = viewmodel?.player?.isPlaying() == true,
                )
            )
        }
    }

    /** Sends a chat message to the server **/
    fun sendMessage(message: String) {
        if (isSoloMode) return

        viewmodel?.p?.sendPacket(JsonSender.sendChat(message))
    }

    /** This broadcasts a message to show it in the chat section **/
    fun broadcastMessage(message: String, isChat: Boolean, chatter: String = "", isError: Boolean = false) {
        if (isSoloMode) return

        /** Messages are just a wrapper class for everything we need about a message
        So first, we initialize it, customize it, then add it to our long list of messages */
        val msg = Message(
            sender = if (isChat) chatter else null,
            isMainUser = chatter == viewmodel?.p?.session?.currentUsername,
            content = message,
            isError = isError
        )

        /** Adding the message instance to our message sequence **/
        viewmodel?.p?.session?.messageSequence?.add(msg)
    }

    /** Mismatches are: Name, Size, Duration. If 3 mismatches are detected, no error is thrown
     * since that would mean that the two files are completely and obviously different.*/
    fun checkFileMismatches() {
        if (isSoloMode) return

        /** First, we check if user wanna be notified about file mismatchings */
        val pref = valueBlockingly(PREF_FILE_MISMATCH_WARNING, true)

        if (!pref) return

        for (user in viewmodel!!.p.session.userList.value) {
            val theirFile = user.file ?: continue /* If they have no file, iterate unto next */

            val nameMismatch = (viewmodel?.media?.fileName != theirFile.fileName) and (viewmodel?.media?.fileNameHashed != theirFile.fileNameHashed)
            val durationMismatch = viewmodel?.media?.fileDuration != theirFile.fileDuration
            val sizeMismatch = (viewmodel?.media?.fileSize != theirFile.fileSize) and (viewmodel?.media?.fileSizeHashed != theirFile.fileSizeHashed)

            if (nameMismatch && durationMismatch && sizeMismatch) continue /* 2 mismatches or less */

            with(lyricist.strings) {
                var warning = roomFileMismatchWarningCore(user.name)

                if (nameMismatch) warning += roomFileMismatchWarningName
                if (durationMismatch) warning += roomFileMismatchWarningDuration
                if (sizeMismatch) warning += roomFileMismatchWarningSize

                broadcastMessage(warning, false, isError = true)
            }
        }
    }

}