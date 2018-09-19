package com.epam.nyekilajos.roompagingpoc.model.database

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.google.gson.GsonBuilder
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.isEmptyString
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MigrationsTest {

    @Rule
    @JvmField
    val helper: MigrationTestHelper = MigrationTestHelper(
            InstrumentationRegistry.getInstrumentation(),
            BeersDatabase::class.java.canonicalName,
            FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    fun testMigration1_2() {
        val oldVersion = helper.createDatabase(TEST_DB_NAME, 1)
        oldVersion.run {
            execSQL("INSERT INTO beers (id, name) VALUES ($TEST_ID, \"$TEST_NAME\")")
            close()
        }

        val newVersion = helper.runMigrationsAndValidate(TEST_DB_NAME, 2, true, MIGRATION_1_2)
        newVersion.query("SELECT * FROM beers", null).run {
            moveToFirst()
            assertThat(getInt(0), equalTo(TEST_ID))
            assertThat(getString(1), equalTo(TEST_NAME))
            assertThat(getString(2), equalTo(EMPTY_JSON_LIST))
            assertThat(getString(3), equalTo(EMPTY_JSON_LIST))
            assertThat(getString(4), isEmptyString())
        }
    }
}

private const val TEST_DB_NAME = "test"
private const val TEST_ID = 101
private const val TEST_NAME = "IPA"

private val EMPTY_JSON_LIST = GsonBuilder().create().toJson(mutableListOf<String>())
