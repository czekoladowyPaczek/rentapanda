package de.panda.rentapanda.helper;

import org.junit.Before;
import org.junit.Test;

import de.panda.rentapanda.R;

import static junit.framework.Assert.assertEquals;

/**
 * @author Marcin
 */
public class TranslateHelperTest {

   private TranslateHelper helper;

    @Before
    public void setUp() throws Exception {
        helper = new TranslateHelper();
    }

    @Test
    public void testGetExtras() throws Exception {
        assertEquals(R.string.detail_extra_fridge, helper.getExtras("fridge"));
        assertEquals(R.string.detail_extra_windows, helper.getExtras("windows"));
        assertEquals(R.string.detail_extra_laundry, helper.getExtras("laundry"));
        assertEquals(R.string.detail_extra_oven, helper.getExtras("oven"));
        assertEquals(R.string.detail_extra_ironing, helper.getExtras("ironing"));
    }

    @Test
    public void testGetPayment() throws Exception {
        assertEquals(R.string.detail_payment_cash, helper.getPayment("Cash"));
        assertEquals(R.string.detail_payment_wire, helper.getPayment("Wire Transfer"));
        assertEquals(R.string.detail_payment_wire, helper.getPayment("wiretransfer"));
    }

    @Test
    public void testGetStatus() throws Exception {
        assertEquals(R.string.detail_status_cancelled, helper.getStatus("CANCELLED CUSTOMER"));
        assertEquals(R.string.detail_status_invoice, helper.getStatus("INVOICED"));
        assertEquals(R.string.detail_status_fulfilled, helper.getStatus("FULFILLED"));
        assertEquals(R.string.detail_status_pending, helper.getStatus("PENDING TO START"));
        assertEquals(R.string.detail_status_error, helper.getStatus("ERROR"));
    }

    @Test
    public void testGetRecurrence() throws Exception {
        assertEquals(R.string.detail_recurrence_once, helper.getRecurrence(0));
        assertEquals(R.string.detail_recurrence_weekly, helper.getRecurrence(7));
        assertEquals(R.string.detail_recurrence_2week, helper.getRecurrence(14));
        assertEquals(R.string.detail_recurrence_monthly, helper.getRecurrence(28));
    }
}