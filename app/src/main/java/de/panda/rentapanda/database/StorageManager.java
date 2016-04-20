package de.panda.rentapanda.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import de.panda.rentapanda.model.ModelJob;

/**
 * @author Marcin
 */
public class StorageManager {
    private ContentResolver resolver;

    public StorageManager(Context context) {
        this.resolver = context.getContentResolver();
    }

    public void deleteJobs() {
        resolver.delete(ProviderJob.CONTENT_URI, null, null);
    }

    public void insertJobs(@NonNull List<ModelJob> jobs) {
        ContentValues[] values = new ContentValues[jobs.size()];

        for(int i = 0; i < values.length; i++) {
            values[i] = jobs.get(i).getContentValues();
        }

        resolver.bulkInsert(ProviderJob.CONTENT_URI, values);
    }
}
