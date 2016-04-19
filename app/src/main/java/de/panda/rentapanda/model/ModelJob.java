package de.panda.rentapanda.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import de.panda.rentapanda.database.TableJob;

/**
 * @author Marcin
 */
public class ModelJob implements Parcelable {

    @SerializedName("customer_name")
    private String customerName;
    private String distance;
    @SerializedName("job_date")
    private Date jobDate;
    private String extras;
    @SerializedName("order_duration")
    private int orderDuration;
    @SerializedName("order_id")
    private String id;
    @SerializedName("order_time")
    private String orderTime;
    @SerializedName("payment_method")
    private String paymentMethod;
    private String price;
    private int recurrency;
    @SerializedName("job_city")
    private String city;
    @SerializedName("job_latitude")
    private String lat;
    @SerializedName("job_longitude")
    private String lon;
    @SerializedName("job_postalcode")
    private int postalCode;
    @SerializedName("job_street")
    private String street;
    private String status;

    ModelJob(String id, String customerName, Date jobDate, String orderTime, int orderDuration, String paymentMethod,
                    String price, int recurrency, String city, String street, int postalCode, String status) {
        this.id = id;
        this.customerName = customerName;
        this.jobDate = jobDate;
        this.orderTime = orderTime;
        this.paymentMethod = paymentMethod;
        this.price = price;
        this.recurrency = recurrency;
        this.city = city;
        this.street = street;
        this.status = status;
        this.postalCode = postalCode;
        this.orderDuration = orderDuration;
    }

    protected ModelJob(Parcel in) {
        customerName = in.readString();
        distance = in.readString();
        long tmpJobDate = in.readLong();
        jobDate = tmpJobDate != -1 ? new Date(tmpJobDate) : null;
        extras = in.readString();
        orderDuration = in.readInt();
        id = in.readString();
        orderTime = in.readString();
        paymentMethod = in.readString();
        price = in.readString();
        recurrency = in.readInt();
        city = in.readString();
        lat = in.readString();
        lon = in.readString();
        postalCode = in.readInt();
        street = in.readString();
        status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(customerName);
        dest.writeString(distance);
        dest.writeLong(jobDate != null ? jobDate.getTime() : -1L);
        dest.writeString(extras);
        dest.writeInt(orderDuration);
        dest.writeString(id);
        dest.writeString(orderTime);
        dest.writeString(paymentMethod);
        dest.writeString(price);
        dest.writeInt(recurrency);
        dest.writeString(city);
        dest.writeString(lat);
        dest.writeString(lon);
        dest.writeInt(postalCode);
        dest.writeString(street);
        dest.writeString(status);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ModelJob> CREATOR = new Parcelable.Creator<ModelJob>() {
        @Override
        public ModelJob createFromParcel(Parcel in) {
            return new ModelJob(in);
        }

        @Override
        public ModelJob[] newArray(int size) {
            return new ModelJob[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getDistance() {
        return distance;
    }

    public Date getJobDate() {
        return jobDate;
    }

    public String[] getExtras() {
        return TextUtils.isEmpty(extras) ? new String[0] : extras.split(";");
    }

    public int getOrderDuration() {
        return orderDuration;
    }

    public String getId() {
        return id;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getPrice() {
        return price;
    }

    public int getRecurrency() {
        return recurrency;
    }

    public String getCity() {
        return city;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public String getStreet() {
        return street;
    }

    public String getStatus() {
        return status;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues(16);
        values.put(TableJob.COLUMN_CITY, city);
        values.put(TableJob.COLUMN_CODE, postalCode);
        values.put(TableJob.COLUMN_CUSTOMER_NAME, customerName);
        values.put(TableJob.COLUMN_DATE, jobDate.getTime());
        values.put(TableJob.COLUMN_DISTANCE, distance);
        values.put(TableJob.COLUMN_DURATION, orderDuration);
        values.put(TableJob.COLUMN_EXTRAS, extras);
        values.put(TableJob.COLUMN_ID, id);
        values.put(TableJob.COLUMN_LAT, lat);
        values.put(TableJob.COLUMN_LON, lon);
        values.put(TableJob.COLUMN_ORDER_TIME, orderTime);
        values.put(TableJob.COLUMN_PAYMENT, paymentMethod);
        values.put(TableJob.COLUMN_PRICE, price);
        values.put(TableJob.COLUMN_RECURRENCY, recurrency);
        values.put(TableJob.COLUMN_STATUS, status);
        values.put(TableJob.COLUMN_STREET, street);
        return values;
    }

    public static class ModelJobBuilder {
        private String customerName;
        private String distance;
        private Date jobDate;
        private String extras;
        private int orderDuration;
        private String id;
        private String orderTime;
        private String paymentMethod;
        private String price;
        private int recurrency;
        private String city;
        private String lat;
        private String lon;
        private int postalCode;
        private String street;
        private String status;

        public ModelJobBuilder(String id, String customerName, Date jobDate, String orderTime, int orderDuration,
                               String paymentMethod, String price, int recurrency, String city, String street, int postalCode,
                               String status) {
            this.id = id;
            this.customerName = customerName;
            this.jobDate = jobDate;
            this.orderTime = orderTime;
            this.paymentMethod = paymentMethod;
            this.price = price;
            this.recurrency = recurrency;
            this.city = city;
            this.street = street;
            this.status = status;
            this.postalCode = postalCode;
            this.orderDuration = orderDuration;
        }

        public ModelJobBuilder lat(String lat) {
            this.lat = lat;
            return this;
        }

        public ModelJobBuilder lon(String lon) {
            this.lon = lon;
            return this;
        }

        public ModelJobBuilder distance(String distance) {
            this.distance = distance;
            return this;
        }

        public ModelJobBuilder extras(String extras) {
            this.extras = extras;
            return this;
        }

        public ModelJob build() {
            ModelJob job = new ModelJob(id, customerName, jobDate, orderTime, orderDuration, paymentMethod, price, recurrency,
                    city, street, postalCode, status);
            job.lon = lon == null ? "" : lon;
            job.lat = lat == null ? "" : lat;
            job.distance = distance == null ? "" : distance;
            job.extras = extras == null ? "" : extras;
            return job;
        }
    }
}
