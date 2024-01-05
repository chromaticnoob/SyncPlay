package com.yuroyami.syncplay.utils

import com.yuroyami.syncplay.models.MediaFile
import com.yuroyami.syncplay.protocol.JsonSender.sendPlaylistChange
import com.yuroyami.syncplay.protocol.JsonSender.sendPlaylistIndex
import com.yuroyami.syncplay.settings.DataStoreKeys
import com.yuroyami.syncplay.settings.valueBlockingly
import com.yuroyami.syncplay.settings.writeValue
import com.yuroyami.syncplay.utils.RoomUtils.broadcastMessage
import com.yuroyami.syncplay.watchroom.dispatchOSD
import com.yuroyami.syncplay.watchroom.lyricist
import com.yuroyami.syncplay.watchroom.viewmodel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.currentCoroutineContext

/** Shared Playlists also have their fair amount of methods related to it which I believe can be
 * wrapped into their own class.**/
object SharedPlaylistUtils {

    /** Adding a file to the playlist: This basically adds one file name to the playlist, then,
     * adds the parent directory to the known media directories, after that, it informs the server
     * about it. The server will send back the new playlist which will invoke playlist updating */
    fun addFilesToPlaylist(uris: List<String>) {
        //io
        for (uri in uris) {
            /** We get the file name */
            val filename = getFileName(uri) ?: return

            /** If the playlist already contains this file name, prevent adding it */
            if (viewmodel!!.p.session.sharedPlaylist.contains(filename)) return

            /** If there is no duplicate, then we proceed, we check if the list is empty */
            if (viewmodel!!.p.session.sharedPlaylist.isEmpty() && viewmodel!!.p.session.sharedPlaylistIndex == -1) {
                /* If it is empty, then we load the media file */
                viewmodel?.media = MediaFile()
                viewmodel?.media!!.uri = uri
                viewmodel?.player?.collectInfoLocal(viewmodel?.media!!)
                //TODO: injectVideo(uri.toString(), true)
                viewmodel?.p?.sendPacket(sendPlaylistIndex(0))
            }
            viewmodel!!.p.session.sharedPlaylist.add(filename)
        }
        viewmodel!!.p.sendPacket(sendPlaylistChange(viewmodel?.p?.session!!.sharedPlaylist))
    }

    suspend fun addFolderToPlaylist(uri: String) {
        /* First, we save it in our media directories as a common directory */
        saveFolderPathAsMediaDirectory(uri)

        /* Now we get children files */
//            val childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(
//                uri,
//                DocumentsContract.getTreeDocumentId(uri)
//            )
//
//            /** Obtaining the children tree from the path **/
//            val tree = DocumentFile.fromTreeUri(this@addFolderToPlaylist, childrenUri) ?: return@launch
//            val files = tree.listFiles() todo

//            /** We iterate through the children file tree and add them to playlist */
//            val newList = mutableListOf<String>()
//            for (file in files) {
//                if (file.isDirectory) continue
//                val filename = file.name!!
//                if (!p.session.sharedPlaylist.contains(filename)) newList.add(filename)
//            }
//            newList.sort()
//            if (p.session.sharedPlaylistIndex == -1) {
//                retrieveFile(newList.first())
//                p.sendPacket(sendPlaylistIndex(0))
//            }
        // p.sendPacket(sendPlaylistChange(p.session.sharedPlaylist + newList))
    }

    private suspend fun saveFolderPathAsMediaDirectory(uri: String) {
        val paths = valueBlockingly(DataStoreKeys.PREF_SP_MEDIA_DIRS, emptySet<String>()).toMutableSet()

        if (!paths.contains(uri)) paths.add(uri)

        writeValue(DataStoreKeys.PREF_SP_MEDIA_DIRS, paths)
    }

    /** This is to send a playlist selection change to the server */
    fun sendPlaylistSelection(index: Int) {
        viewmodel?.p?.sendPacket(sendPlaylistIndex(index))
    }

    /** This is to change playlist selection in response other users' selection */
    suspend fun changePlaylistSelection(index: Int) {
        if (viewmodel!!.p.session.sharedPlaylist.size < (index + 1)) return /* In rare cases when this was called on an empty list */
        if (index != viewmodel!!.p.session.sharedPlaylistIndex) {
            /* If the file on that index isn't playing, play the file */
            retrieveFile(viewmodel?.p!!.session.sharedPlaylist[index])
        }
    }

    /** TODO: This will search all media directories specified by the user in settings to look for a file
     * name and load it into ExoPlayer. This is executed on a separate thread since the IO operation
     * is heavy.
     */
    private suspend fun retrieveFile(fileName: String) {
            /** We have to know whether the file name is an URL or just a file name */
            if (fileName.contains("http://", true) ||
                fileName.contains("https://", true) ||
                fileName.contains("ftp://", true)
            ) {
                viewmodel?.player?.injectVideo(fileName, isUrl = true)
            } else {
                /** We search our media directories which were added by the user in settings */
                val paths = valueBlockingly(DataStoreKeys.PREF_SP_MEDIA_DIRS, emptySet<String>()).toMutableSet()

                if (paths.isEmpty()) {
                    broadcastMessage(lyricist.strings.roomSharedPlaylistNoDirectories, false)
                }

                var fileUri2Play: String? = null
                /** We iterate through the media directory paths spreading their children tree **/
                for (path in paths) {
//                    val uri = Uri.parse(path)
//
//                    /** Will NOT work if Uri hasn't been declared persistent upon retrieving it */
//                    val childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(
//                        uri,
//                        DocumentsContract.getTreeDocumentId(uri)
//                    )
//
//                    /** Obtaining the children tree from the path **/
//                    val tree = DocumentFile.fromTreeUri(this@retrieveFile, childrenUri) ?: return@launch
//                    val files = tree.listFiles()
//
//                    /** We iterate through the children file tree, and we search for the specific file */
//                    for (file in files) {
//                        val filename = file.name!!
//                        if (filename == fileName) {
//                            fileUri2Play = tree.findFile(filename)?.uri ?: return@launch
//                            /** Changing current file variable **/
//                            media = MediaFile()
//                            media?.uri = fileUri2Play
//                            media?.collectInfo(this@retrieveFile)
//                            /** Loading the file into our player **/
//                            player?.injectVideo(this@retrieveFile, fileUri2Play)
//
//                            break
//                        }
//                    }
                }
                if (fileUri2Play == null) {
                    if (viewmodel?.media?.fileName != fileName) {
                        val s = lyricist.strings.roomSharedPlaylistNotFound
                        CoroutineScope(currentCoroutineContext()).dispatchOSD(s)
                        broadcastMessage(s, false)
                    }
                }

        }
    }

    /** This will delete an item from playlist at a given index 'i' */
    fun deleteItemFromPlaylist(i: Int) {
        viewmodel?.p?.session?.sharedPlaylist?.removeAt(i)
        viewmodel?.p?.sendPacket(sendPlaylistChange(viewmodel?.p?.session!!.sharedPlaylist))
        if (viewmodel?.p!!.session.sharedPlaylist.isEmpty()) {
            viewmodel?.p?.session?.sharedPlaylistIndex = -1
        }
    }

    /** Shuffles the playlist
     * @param mode False to shuffle all playlist, True to shuffle only the rest*/
    suspend fun shuffle(mode: Boolean) {
        /** If the shared playlist is empty, do nothing */
        if (viewmodel!!.p.session.sharedPlaylistIndex < 0 || viewmodel!!.p.session.sharedPlaylist.isEmpty()) return


        /** Shuffling as per the mode selected: False = shuffle all, True = Shuffle rest */
        if (mode) {
            /** Shuffling the rest of playlist is a bit trickier, we split the shared playlist into two
             * grp1 is gonna be the group that doesn't change (everything until current index)
             * grp2 is the group to be shuffled since it's the 'remaining group' */

            val grp1 = viewmodel!!.p.session.sharedPlaylist.take(viewmodel!!.p.session.sharedPlaylistIndex + 1).toMutableList()
            val grp2 = viewmodel!!.p.session.sharedPlaylist.takeLast(viewmodel!!.p.session.sharedPlaylist.size - grp1.size).shuffled()
            grp1.addAll(grp2)
            viewmodel!!.p.session.sharedPlaylist.clear()
            viewmodel!!.p.session.sharedPlaylist.addAll(grp1)
        } else {
            /** Shuffling everything is easy as Kotlin gives us the 'shuffle()' method */
            viewmodel!!.p.session.sharedPlaylist.shuffle()

            /** Index won't change, but the file at the given index did change, play it */
            retrieveFile(viewmodel!!.p.session.sharedPlaylist[viewmodel!!.p.session.sharedPlaylistIndex])
        }

        /** Announcing a new updated list to the room members */
        viewmodel!!.p.sendPacket(sendPlaylistChange(viewmodel!!.p.session.sharedPlaylist))
    }

    /** Saves the playlist as a plain text file (.txt) to the designated folder with a timestamp of the current time
     * @param folderUri The uri of the save folder */
    fun saveSHP(folderUri: String) {
            //val folder = DocumentFile.fromTreeUri(this@saveSHP, folderUri)
            //val txt = folder?.createFile("text/plain", "SharedPlaylist_${System.currentTimeMillis()}.txt")

            /** Converting the shared playlist to a text string, line by line */
            var string = ""
            for (f in viewmodel!!.p.session.sharedPlaylist) {
                string += f
                if (viewmodel!!.p.session.sharedPlaylist.indexOf(f) != viewmodel!!.p.session.sharedPlaylist.size - 1) {
                    string += "\n"
                }
            }
            string = string.trim()

            /** Writing to the file */
//            val s = contentResolver.openOutputStream(txt?.uri ?: return@launch)
//            s?.write(string.toByteArray(Charsets.UTF_8))
//            s?.flush()
//            s?.close()
    }

    /** After selecting a txt file, this will attempt to load the contents of the plain text file
     * line by line as separate individual file name entries.
     * @param fileUri Uri of the selected txt file
     * @param shuffle Determines whether the content of the file should be shuffled or not */
    fun loadSHP(fileUri: String, shuffle: Boolean) {
        /** Opening the input stream of the file */
//        val s = contentResolver.openInputStream(fileUri) ?: return
//        val string = s.readBytes().decodeToString()
//        s.close()

        /** Reading content */
/*        val lines = string.split("\n").toMutableList()

        *//** If the user chose the shuffling option along with it, then we shuffle it *//*
        if (shuffle) {
            lines.shuffle()
        }*/

        /** Updating the shared playlist */
        //p.sendPacket(sendPlaylistChange(lines))
    }

    /** Adds URLs from the url adding popup */
    fun addURLs(string: List<String>) {
        val l = mutableListOf<String>()
        l.addAll(viewmodel!!.p.session.sharedPlaylist)
        for (s in string) {
            if (!viewmodel!!.p.session.sharedPlaylist.contains(s)) l.add(s)
        }
        viewmodel!!.p.sendPacket(sendPlaylistChange(l))
    }

    /** Clears the shared playlist */
    fun clearPlaylist() {
        if (viewmodel!!.p.session.sharedPlaylist.isEmpty()) return
        viewmodel!!.p.sendPacket(sendPlaylistChange(emptyList()))
    }

}