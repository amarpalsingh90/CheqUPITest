package com.dev.chequpitest.data.local.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.dev.chequpitest.constant.StringConstants
import com.dev.chequpitest.data.local.dao.CartDao
import com.dev.chequpitest.data.local.dao.UserDao
import com.dev.chequpitest.data.local.entity.CartItemEntity
import com.dev.chequpitest.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, CartItemEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    StringConstants.DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
