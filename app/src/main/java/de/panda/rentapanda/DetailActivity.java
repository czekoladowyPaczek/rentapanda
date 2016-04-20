package de.panda.rentapanda;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import de.panda.rentapanda.helper.TranslateHelper;
import de.panda.rentapanda.model.ModelJob;

/**
 * @author Marcin
 */
public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String EXTRA_JOB = "de.panda.detail.job";
    private ModelJob job;
    private GoogleMap map;

    private TextView date;
    private TextView duration;
    private TextView customer;
    private TextView street;
    private TextView city;
    private TextView price;
    private TextView payment;
    private TextView extras;
    private View extrasContainer;

    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    @Inject
    TranslateHelper translateHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        job = getIntent().getParcelableExtra(EXTRA_JOB);
        if (job == null) {
            finish();
            return;
        }

        ((PandaApplication) getApplication()).getActivityComponent().inject(this);

        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(job.getStatus());

        date = (TextView) findViewById(R.id.detail_date);
        duration = (TextView) findViewById(R.id.detail_duration);
        customer = (TextView) findViewById(R.id.detail_customer);
        price = (TextView) findViewById(R.id.detail_price);
        payment = (TextView) findViewById(R.id.detail_payment);
        street = (TextView) findViewById(R.id.detail_street);
        city = (TextView) findViewById(R.id.detail_city);
        extras = (TextView) findViewById(R.id.detail_extra);
        extrasContainer = findViewById(R.id.detail_extra_container);

        setInformation(job.getJobDate(), job.getOrderDuration(), job.getRecurrency(), job.getCustomerName());
        setAddress(job.getStreet(), job.getCity(), job.getPostalCode());
        setPayment(job.getPrice(), job.getPaymentMethod());
        setExtras(job.getExtras());

        FragmentManager manager = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) manager.findFragmentById(R.id.map);
        if (job.hasPosition()) {
            mapFragment.getMapAsync(this);
        } else {
            mapFragment.getView().setVisibility(View.GONE);
        }
    }

    private void setInformation(Date date, String hours, int recurrence, String customer) {
        this.customer.setText(customer);
        this.date.setText(format.format(date));

        StringBuilder detailBuilder = new StringBuilder();
        try {
            double duration = Double.valueOf(hours);
            if (duration != 1) { // plurals doesn't support fractions
                duration = 2;
            }
            detailBuilder.append(getResources().getQuantityString(R.plurals.detail_hour, (int) duration, hours));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        detailBuilder.append(" ");
        detailBuilder.append(getString(translateHelper.getRecurrence(recurrence)));
        this.duration.setText(detailBuilder.toString());
    }

    private void setExtras(String[] extras) {
        if (extras.length == 0) {
            extrasContainer.setVisibility(View.GONE);
        } else {
            StringBuilder builder = new StringBuilder();
            for (String s : extras) {
                builder.append(getString(translateHelper.getExtras(s)));
                builder.append(", ");
            }
            builder.replace(builder.length() - 2, builder.length(), ""); // remove last comma
            this.extras.setText(builder.toString());
        }
    }

    private void setAddress(String street, String city, int code) {
        this.street.setText(street);
        this.city.setText(String.format("%s %d", city, code));
    }

    private void setPayment(String price, String payment) {
        this.payment.setText(translateHelper.getPayment(payment));
        this.price.setText(price);
    }

    private void setTitle(String status) {
        setTitle(translateHelper.getStatus(status));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LatLng position = new LatLng(Double.parseDouble(job.getLat()), Double.parseDouble(job.getLon()));
        map.addMarker(new MarkerOptions()
            .position(position));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 12));
    }
}
