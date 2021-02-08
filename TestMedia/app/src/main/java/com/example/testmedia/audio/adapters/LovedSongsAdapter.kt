package com.example.testmedia.audio.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.example.testmedia.R
import com.example.testmedia.audio.GoConstants
import com.example.testmedia.audio.extensions.toFilenameWithoutExtension
import com.example.testmedia.audio.goPreferences
import com.example.testmedia.audio.helpers.DialogHelper
import com.example.testmedia.audio.models.Music
import com.example.testmedia.audio.player.MediaPlayerHolder
import com.example.testmedia.audio.ui.UIControlInterface

class LovedSongsAdapter(
    private val activity: Activity,
    private val lovedSongsDialog: MaterialDialog,
    private val mediaPlayerHolder: MediaPlayerHolder
) :
    RecyclerView.Adapter<LovedSongsAdapter.LoveHolder>() {

    private var mLovedSongs = goPreferences.lovedSongs?.toMutableList()
    private val mUiControlInterface = activity as UIControlInterface

    fun swapSongs(lovedSongs: MutableList<Music>?) {
        mLovedSongs = lovedSongs
        notifyDataSetChanged()
        mUiControlInterface.onLovedSongsUpdate(false)
        if (mLovedSongs?.isEmpty()!!) {
            lovedSongsDialog.dismiss()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoveHolder {
        return LoveHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.music_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mLovedSongs?.size!!
    }

    override fun onBindViewHolder(holder: LoveHolder, position: Int) {
        holder.bindItems(mLovedSongs?.get(holder.adapterPosition))
    }


    inner class LoveHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(lovedSong: Music?) {

            val title = itemView.findViewById<TextView>(R.id.title)
            val duration = itemView.findViewById<TextView>(R.id.duration)
            val subtitle = itemView.findViewById<TextView>(R.id.subtitle)

            val displayedTitle =
                    if (goPreferences.songsVisualization != GoConstants.TITLE) {
                        lovedSong?.displayName?.toFilenameWithoutExtension()
                    } else {
                        lovedSong?.title
                    }
            title.text = displayedTitle
            duration.text =
                    DialogHelper.computeDurationText(activity, lovedSong)


            subtitle.text =
                activity.getString(R.string.artist_and_album, lovedSong?.artist, lovedSong?.album)

            itemView.run {
                setOnClickListener {
                    mediaPlayerHolder.isSongFromLovedSongs =
                        Pair(true, lovedSong?.startFrom!!)
                    mUiControlInterface.onAddAlbumToQueue(
                        mLovedSongs,
                        Pair(false, lovedSong),
                        isLovedSongs = true,
                        isShuffleMode = false,
                        clearShuffleMode = true,
                        mediaPlayerHolder.launchedBy
                    )
                }
                setOnLongClickListener {
                    performLovedSongDeletion(adapterPosition, false)
                    return@setOnLongClickListener true
                }
            }
        }
    }

    fun performLovedSongDeletion(position: Int, isSwipe: Boolean) {
        mLovedSongs?.get(position).let { song ->
            DialogHelper.showDeleteLovedSongDialog(
                activity,
                song,
                this@LovedSongsAdapter,
                Pair(isSwipe, position)
            )
        }
    }
}
