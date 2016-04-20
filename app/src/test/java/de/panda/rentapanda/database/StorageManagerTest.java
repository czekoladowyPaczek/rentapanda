package de.panda.rentapanda.database;

import android.content.ContentResolver;
import android.database.sqlite.SQLiteDatabase;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowContentResolver;

import java.util.ArrayList;
import java.util.List;

import de.panda.rentapanda.BuildConfig;
import de.panda.rentapanda.model.ModelJob;
import de.panda.rentapanda.model.ModelJobTest;

/**
 * @author Marcin
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class StorageManagerTest extends TestCase {

    private ContentResolver resolver;
    private SQLiteDatabase db;
    private ModelJob testJobFull;
    private ModelJob testJobEmpty;

    private StorageManager manager;

    @Before
    public void setup() {
        ProviderJob providerJob = new ProviderJob();
        providerJob.onCreate();
        ShadowContentResolver.registerProvider(ProviderJob.AUTHORITY, providerJob);
        resolver = RuntimeEnvironment.application.getContentResolver();
        db = new DatabaseHelper(RuntimeEnvironment.application).getWritableDatabase();
        db.delete(TableJob.TABLE_NAME, null, null);

        testJobFull = ModelJobTest.getFullModelJob();
        testJobEmpty = ModelJobTest.getEmptyModelJob();

        manager = new StorageManager(RuntimeEnvironment.application);
    }

    @After
    public void tearDown() {
        db.delete(TableJob.TABLE_NAME, null, null);
    }

    @Test
    public void deleteJobs() throws Exception {
        resolver.insert(ProviderJob.CONTENT_URI, testJobFull.getContentValues());
        assertEquals(1, db.query(TableJob.TABLE_NAME, ProviderJob.available, null, null, null, null, null).getCount());

        manager.deleteJobs();
        assertEquals(0, db.query(TableJob.TABLE_NAME, ProviderJob.available, null, null, null, null, null).getCount());
    }

    @Test
    public void insertJobs() throws Exception {
        assertEquals(0, db.query(TableJob.TABLE_NAME, ProviderJob.available, null, null, null, null, null).getCount());

        List<ModelJob> jobs = new ArrayList<>(2);
        jobs.add(testJobFull);
        jobs.add(testJobEmpty);
        manager.insertJobs(jobs);
        assertEquals(2, db.query(TableJob.TABLE_NAME, ProviderJob.available, null, null, null, null, null).getCount());
    }
}