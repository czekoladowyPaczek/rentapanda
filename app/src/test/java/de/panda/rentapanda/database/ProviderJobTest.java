package de.panda.rentapanda.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import de.panda.rentapanda.BuildConfig;
import de.panda.rentapanda.model.ModelJob;
import de.panda.rentapanda.model.ModelJobTest;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author Marcin
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class ProviderJobTest {
    private SQLiteDatabase db;
    private ModelJob testJobFull;
    private ModelJob testJobEmpty;

    private ProviderJob providerJob;

    @Before
    public void setup() {
        providerJob = new ProviderJob();
        providerJob.onCreate();

        db = new DatabaseHelper(RuntimeEnvironment.application).getWritableDatabase();
        db.delete(TableJob.TABLE_NAME, null, null);

        testJobEmpty = ModelJobTest.getEmptyModelJob();
        testJobFull = ModelJobTest.getFullModelJob();

        db.insert(TableJob.TABLE_NAME, null, testJobEmpty.getContentValues());
    }

    @After
    public void tearDown() {
        db.delete(TableJob.TABLE_NAME, null, null);
    }

    @Test
    public void testQuery() throws Exception {
        Cursor cursor = providerJob.query(ProviderJob.CONTENT_URI, new String[]{TableJob.COLUMN_ID}, null, null, null);
        assertNotNull(cursor);
        assertEquals(1, cursor.getCount());
        cursor.moveToFirst();
        assertEquals(testJobEmpty.getId(), cursor.getString(0));
        cursor.close();
    }

    @Test
    public void testInsert() throws Exception {
        providerJob.insert(ProviderJob.CONTENT_URI, testJobFull.getContentValues());
        Cursor c = db.query(TableJob.TABLE_NAME, new String[]{TableJob.COLUMN_ID}, null, null, null, null, null);
        assertEquals(2, c.getCount());
        c.close();
    }

    @Test
    public void testDelete() throws Exception {
        providerJob.delete(ProviderJob.CONTENT_URI, null, null);

        Cursor c = db.query(TableJob.TABLE_NAME, new String[]{TableJob.COLUMN_ID}, null, null, null, null, null);
        assertEquals(0, c.getCount());
        c.close();
    }

    @Test
    public void testUpdate() throws Exception {
        ContentValues v = new ContentValues(1);
        v.put(TableJob.COLUMN_CITY, "Tokyo");
        providerJob.update(ProviderJob.CONTENT_URI, v, null, null);
        Cursor c = db.query(TableJob.TABLE_NAME, new String[]{TableJob.COLUMN_CITY}, null, null, null, null, null);
        assertTrue(c.moveToFirst());
        assertEquals("Tokyo", c.getString(0));
    }

    @Test
    public void testBulkInsert() throws Exception {
        providerJob.bulkInsert(ProviderJob.CONTENT_URI, new ContentValues[]{testJobFull.getContentValues()});
        Cursor c = db.query(TableJob.TABLE_NAME, new String[]{TableJob.COLUMN_ID}, null, null, null, null, null);
        assertEquals(2, c.getCount());
        c.close();
    }
}