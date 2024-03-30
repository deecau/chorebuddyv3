
package com.example.chorebuddy

import android.content.Context
import android.content.SharedPreferences

object DataPersistence {
    private const val PREFS_NAME = "ChoreBuddyPrefs"

    fun saveMemberList(context: Context, memberList: List<String>) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putStringSet("members", memberList.toSet())
        editor.apply()
    }

    fun retrieveMemberList(context: Context): List<String> {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val memberSet = sharedPreferences.getStringSet("members", setOf())
        return memberSet?.toList() ?: emptyList()
    }
}

