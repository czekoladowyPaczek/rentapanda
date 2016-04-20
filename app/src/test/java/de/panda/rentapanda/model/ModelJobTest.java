package de.panda.rentapanda.model;

import android.content.ContentValues;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.Calendar;

import de.panda.rentapanda.BuildConfig;
import de.panda.rentapanda.database.TableJob;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * @author Marcin
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class ModelJobTest {
    public static final String TEST_JOB_FULL = "{" +
            "\"__status\": \"FULFILLED\"," +
            "\"customer_name\": \"testUser\"," +
            "\"distance\": \"1.885938875621\"," +
            "\"job_date\": \"2016-03-04T00:00:00.000Z\"," +
            "\"extras\": \"oven;test\"," +
            "\"order_duration\": 2," +
            "\"order_id\": \"dc4n47ggy\"," +
            "\"order_time\": \"16:00\"," +
            "\"payment_method\": \"Cash\"," +
            "\"price\": \"33.50\"," +
            "\"recurrency\": 7," +
            "\"job_city\": \"Berlin\"," +
            "\"job_latitude\": \"52.5130435\"," +
            "\"job_longitude\": \"13.4180222\"," +
            "\"job_postalcode\": 12047," +
            "\"job_street\": \"bruckenstrasse 5a\"," +
            "\"status\": \"FULFILLED\"" +
            "  }";
    public static final String TEST_JOB_EMPTY = "{" +
            "\"__status\": \"FULFILLED\"," +
            "\"customer_name\": \"testUser\"," +
            "\"distance\": \"\"," +
            "\"job_date\": \"2016-03-04T00:00:00.000Z\"," +
            "\"extras\": \"\"," +
            "\"order_duration\": \"2\"," +
            "\"order_id\": \"dc4n48ggy\"," +
            "\"order_time\": \"16:00\"," +
            "\"payment_method\": \"Cash\"," +
            "\"price\": \"33.50\"," +
            "\"recurrency\": 7," +
            "\"job_city\": \"Berlin\"," +
            "\"job_latitude\": \"\"," +
            "\"job_longitude\": \"\"," +
            "\"job_postalcode\": 12047," +
            "\"job_street\": \"bruckenstrasse 5a\"," +
            "\"status\": \"FULFILLED\"" +
            "  }";

    @Test
    public void testGsonSerializationWhenEveryFieldAvailable() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2016);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.DAY_OF_MONTH, 4);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        ModelJob job = getFullModelJob();
        assertEquals("testUser", job.getCustomerName());
        assertEquals("1.885938875621", job.getDistance());
        assertEquals(2, job.getExtras().length);
        assertEquals("oven", job.getExtras()[0]);
        assertEquals("test", job.getExtras()[1]);
        assertEquals("2", job.getOrderDuration());
        assertEquals("dc4n47ggy", job.getId());
        assertEquals("16:00", job.getOrderTime());
        assertEquals("Cash", job.getPaymentMethod());
        assertEquals("33.50", job.getPrice());
        assertEquals(7, job.getRecurrency());
        assertEquals("Berlin", job.getCity());
        assertEquals("52.5130435", job.getLat());
        assertEquals("13.4180222", job.getLon());
        assertEquals(12047, job.getPostalCode());
        assertEquals("bruckenstrasse 5a", job.getStreet());
        assertEquals("FULFILLED", job.getStatus());
        assertEquals(cal.getTimeInMillis(), job.getJobDate().getTime());
    }

    @Test
    public void getContentValues() {
        ModelJob job = getFullModelJob();
        ContentValues values = job.getContentValues();

        assertTrue(values.containsKey(TableJob.COLUMN_CODE));
        assertTrue(values.containsKey(TableJob.COLUMN_DATE));
        assertTrue(values.containsKey(TableJob.COLUMN_CUSTOMER_NAME));
        assertTrue(values.containsKey(TableJob.COLUMN_CITY));
        assertTrue(values.containsKey(TableJob.COLUMN_DISTANCE));
        assertTrue(values.containsKey(TableJob.COLUMN_EXTRAS));
        assertTrue(values.containsKey(TableJob.COLUMN_DURATION));
        assertTrue(values.containsKey(TableJob.COLUMN_ID));
        assertTrue(values.containsKey(TableJob.COLUMN_LAT));
        assertTrue(values.containsKey(TableJob.COLUMN_LON));
        assertTrue(values.containsKey(TableJob.COLUMN_ORDER_TIME));
        assertTrue(values.containsKey(TableJob.COLUMN_PAYMENT));
        assertTrue(values.containsKey(TableJob.COLUMN_PRICE));
        assertTrue(values.containsKey(TableJob.COLUMN_RECURRENCY));
        assertTrue(values.containsKey(TableJob.COLUMN_STATUS));
        assertTrue(values.containsKey(TableJob.COLUMN_STREET));
    }

    @Test
    public void testGsonSerializationWhenNotEveryFieldAvailable() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2016);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.DAY_OF_MONTH, 4);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        ModelJob job = getEmptyModelJob();
        assertEquals("testUser", job.getCustomerName());
        assertEquals("", job.getDistance());
        assertEquals(0, job.getExtras().length);
        assertEquals("2", job.getOrderDuration());
        assertEquals("dc4n48ggy", job.getId());
        assertEquals("16:00", job.getOrderTime());
        assertEquals("Cash", job.getPaymentMethod());
        assertEquals("33.50", job.getPrice());
        assertEquals(7, job.getRecurrency());
        assertEquals("Berlin", job.getCity());
        assertEquals("", job.getLat());
        assertEquals("", job.getLon());
        assertEquals(12047, job.getPostalCode());
        assertEquals("bruckenstrasse 5a", job.getStreet());
        assertEquals("FULFILLED", job.getStatus());
        assertEquals(cal.getTimeInMillis(), job.getJobDate().getTime());
    }

    public static ModelJob getFullModelJob() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        return gson.fromJson(TEST_JOB_FULL, ModelJob.class);
    }

    public static ModelJob getEmptyModelJob() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        return gson.fromJson(TEST_JOB_EMPTY, ModelJob.class);
    }
}