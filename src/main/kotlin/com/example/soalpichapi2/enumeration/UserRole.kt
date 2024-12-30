package com.example.soalpichapi2.enumeration

enum class UserRole {
    PLAYER,
    DESIGNER,
    ;

    fun getAuthority(): String {
        return "$AUTHORITY_PREFIX$this"
    }

    companion object {
        const val AUTHORITY_PREFIX = "ROLE_"
        const val PLAYER_VALUE = "PLAYER"
        const val DESIGNER_VALUE = "DESIGNER"
    }
}
