package com.epam.nyekilajos.roompagingpoc.model.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2: Migration = object : Migration(1, 2) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.run {
            execSQL("ALTER TABLE beers ADD COLUMN `malt` TEXT NOT NULL DEFAULT \"[]\"")
            execSQL("ALTER TABLE beers ADD COLUMN `hops` TEXT NOT NULL DEFAULT \"[]\"")
            execSQL("ALTER TABLE beers ADD COLUMN `yeast` TEXT NOT NULL DEFAULT \"\"")
            execSQL("CREATE  INDEX `index_beers_name` ON beers (`name`)")
        }
    }
}
