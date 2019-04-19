package com.sartainstudios.automaticintelligentsmsreply

import android.text.format.DateUtils.formatDateTime
import jdk.nashorn.internal.runtime.ECMAErrors.getMessage
import android.R
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView


private class ReceivedMessageHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    internal var messageText: TextView
    internal var timeText: TextView
    internal var nameText: TextView
    internal var profileImage: ImageView

    init {
        messageText = itemView.findViewById(R.id.text_message_body)
        timeText = itemView.findViewById(R.id.text_message_time)
        nameText = itemView.findViewById(R.id.text_message_name)
        profileImage = itemView.findViewById(R.id.image_message_profile) as ImageView
    }

    internal fun bind(message: UserMessage) {
        messageText.setText(message.getMessage())

        // Format the stored timestamp into a readable String using method.
        timeText.setText(Utils.formatDateTime(message.getCreatedAt()))
        nameText.setText(message.getSender().getNickname())

        // Insert the profile image from the URL into the ImageView.
        Utils.displayRoundImageFromUrl(mContext, message.getSender().getProfileUrl(), profileImage)
    }
}
