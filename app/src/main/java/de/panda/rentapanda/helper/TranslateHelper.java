package de.panda.rentapanda.helper;

import android.support.annotation.StringRes;

import javax.inject.Inject;

import de.panda.rentapanda.R;

/**
 * @author Marcin
 */
public class TranslateHelper {

    @Inject
    public TranslateHelper() {}

    public @StringRes int getExtras(String extra) {
        switch (extra) {
            case "ironing":
                return R.string.detail_extra_ironing;
            case "laundry":
                return R.string.detail_extra_laundry;
            case "oven":
                return R.string.detail_extra_oven;
            case "windows":
                return R.string.detail_extra_windows;
            case "fridge":
                return R.string.detail_extra_fridge;
        }
        return -1;
    }

    public @StringRes int getPayment(String payment) {
        switch (payment) {
            case "Cash":
                return R.string.detail_payment_cash;
            case "Wire Transfer":
            case "wiretransfer":
                return R.string.detail_payment_wire;
        }
        return -1;
    }

    public @StringRes int getStatus(String status) {
        switch (status) {
            case "PENDING TO START":
                return R.string.detail_status_pending;
            case "FULFILLED":
                return R.string.detail_status_fulfilled;
            case "INVOICED":
                return R.string.detail_status_invoice;
            case "CANCELLED CUSTOMER":
                return R.string.detail_status_cancelled;
            default:
                return R.string.detail_status_error;
        }
    }

    public @StringRes int getRecurrence(int recurrence) {
        switch (recurrence) {
            case 0:
                return R.string.detail_recurrence_once;
            case 7:
                return R.string.detail_recurrence_weekly;
            case 14:
                return R.string.detail_recurrence_2week;
            case 28:
                return R.string.detail_recurrence_monthly;
        }
        return -1;
    }
}
