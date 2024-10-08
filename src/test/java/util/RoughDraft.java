package util;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.AssertionFailedError;
import org.apache.commons.math3.util.Precision;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static util.Calculations.convertJSValuesToJson;
import static util.Calculations.dateOperations;

public class RoughDraft {

    public static JsonNode Response;
    public static JsonNode FacilityDetails;
    public static JsonNode InstallmentDetails;
    public static JsonNode WalletDetails;
    public static JsonNode SDDetails;

    public static void trial() {
        System.out.println("Java class is called successfully");
    }

    public static void pastDuePayments(String WalletResponse, String FacilityResponse, String InstallmentResponse) throws IOException, ParseException {
        Response = DBValidation.jsonParseDBValues(WalletResponse);
        FacilityDetails = DBValidation.jsonParseDBValues(FacilityResponse);
        InstallmentDetails = DBValidation.jsonParseDBValues(InstallmentResponse);
        double PrincipleAmount;
        double ProfitAmount;
        double PrinciplePaid;
        double ProfitPaid;
        double TotalFeesAmount;
        double TotalFeesPaid;
        double TotalInstallmentBalance;
        double TotalReceivableAmount;
        double TotalRemainingBalance;
        double TotalRepaidAmount;
        String loan_status;

//        MCA related fields
        double due_fees_balance;
        double expected_due_fees_balance;

        double total_due_fees_amount;
        double expected_total_due_fees_amount;

        double total_due_fees_paid;
        double expected_total_due_fees_paid;

        double past_due_installment_payment;
        double expected_past_due_installment_payment;

        double past_due_payment;
        double expected_past_due_payment;

        double months_since_origination;
        double expected_months_since_origination;

        double monthly_mandatory_payment_percentage;
        double expected_monthly_mandatory_payment_percentage;

        double monthly_minimum_charge;
        double expected_monthly_minimum_charge;

        double monthly_mandatory_payment_till_date;
        double expected_monthly_mandatory_payment_till_date;

        double expected_daily_mandatory_payment_till_date;
        double daily_mandatory_payment_till_date;


//        NON MCA related fields
        double due_installment_payment;
        double expected_due_installment_payment;
        double due_principal_payment;
        double expected_due_principal_payment;
        double principal_due_amount;
        double expected_principal_due_amount;
        double principal_due_paid;
        double expected_principal_due_paid;
        double due_profit_payment;
        double expected_due_profit_payment;
        double profit_due_amount;
        double expected_profit_due_amount;
        double profit_due_paid;
        double expected_profit_due_paid;
        double past_due_principal_payment;
        double expected_past_due_principal_payment;
        double principal_past_due_amount;
        double expected_principal_past_due_amount;
        double principal_past_due_paid;
        double expected_principal_past_due_paid;
        double past_due_profit_payment;
        double expected_past_due_profit_payment;
        double profit_past_due_amount;
        double expected_profit_past_due_amount;
        double profit_past_due_paid;
        double expected_profit_past_due_paid;
//        wallet details
        PrincipleAmount = Response.get("principle_amount").asDouble();
        ProfitAmount = Response.get("profit_amount").asDouble();
        PrinciplePaid = Response.get("principle_paid").asDouble();
        ProfitPaid = Response.get("profit_paid").asDouble();
        TotalFeesAmount = Response.get("total_fees_amount").asDouble();
        TotalFeesPaid = Response.get("total_fees_paid").asDouble();
        TotalRemainingBalance = Response.get("total_remaining_balance").asDouble();
        TotalInstallmentBalance = Response.get("total_installments_balance").asDouble();
        TotalRepaidAmount = Response.get("total_repaid_amount").asDouble();
        TotalReceivableAmount = Response.get("total_receivable_amount").asDouble();

//        facility details
        int facility_grace_period_length = FacilityDetails.get("facility_grace_period_length").asInt();
        String productType = FacilityDetails.get("product").get("product_type").asText();
        double productMinimumCharge = FacilityDetails.get("product_minimum_charge").asDouble();
        String disbursement_Date = FacilityDetails.get("disbursed_date").asText();
        long tenorInDays = FacilityDetails.get("tenor").asInt();

//        String closing_date = FacilityDetails.get("closing_date").asText();
        String facility_status = FacilityDetails.get("status").asText();
        System.out.println("tenor in days " + tenorInDays);

//        long tenorInMonths = tenorInDays / 30;  // -----> check if the data type needs to be int or long
        double tenorInMonths = tenorInDays / 30;  // -----> check if the data type needs to be int or long


        System.out.println("tenor in months " + tenorInMonths);
//         installment details
        double Current_principal_due_amount;
        double Current_profit_due_amount;
        double Current_principal_due_paid;
        double Current_profit_due_paid;
        String Current_Installment_Due_Date = InstallmentDetails.get("data").get(0).get("due_date").asText();
        Current_principal_due_amount = InstallmentDetails.get("data").get(0).get("principal_amount").asDouble();
        Current_profit_due_amount = InstallmentDetails.get("data").get(0).get("profit_amount").asDouble();
        Current_principal_due_paid = InstallmentDetails.get("data").get(0).get("principal_paid").asDouble();
        Current_profit_due_paid = InstallmentDetails.get("data").get(0).get("profit_paid").asDouble();

        LocalDate disbursementDate = DateOperations.convertToSpecificDateFormat(disbursement_Date, "yyyy-MM-dd'T'HH:mm:ss");
        String maturityDateVal = disbursementDate.plusDays(tenorInDays).toString();
        LocalDate maturityDate = DateOperations.convertToSpecificDateFormat(maturityDateVal, "yyyy-MM-dd");
        System.out.println("Disbursement date -->" + disbursementDate);
        System.out.println("Maturity date -->" + maturityDate);
        System.out.println("Disbursement date " + disbursementDate + " plus tenor " + tenorInDays + " days is " + maturityDate);


        double total_fees_amount = 0;
        double total_fees_paid = 0;
        double total_fees_balance = 0;

//        calculate the total fee amount = sum of all the fee amount  for past due payment filds
        int feeCount = Response.get("fees").get(0).size();
        System.out.println("No of fees present : " + (feeCount - 1));
        for (int i = 0; i < feeCount - 1; i++) {

            total_fees_amount = total_fees_amount + Response.get("fees").get(i).get("total_fees_amount").asDouble();
            total_fees_paid = total_fees_paid + Response.get("fees").get(i).get("total_fees_paid").asDouble();
            total_fees_balance = total_fees_balance + Response.get("fees").get(i).get("total_fees_balance").asDouble();

        }
        System.out.println("sum of total fees amount " + total_fees_amount);
        System.out.println("sum of total fees paid " + total_fees_paid);
        System.out.println("sum of total fees balance " + total_fees_balance);

        //            Due Fees balance , Total Due Fees Amount , Total Fees Paid
        expected_total_due_fees_amount = total_fees_amount;
        expected_total_due_fees_paid = total_fees_paid;
        expected_due_fees_balance = total_fees_amount - total_fees_paid;
        System.out.println("Expected Total Due Fees Amount fees balance " + expected_due_fees_balance);
        System.out.println("Expected Due fees amount " + expected_total_due_fees_amount);
        System.out.println("Expected Due fees paid " + expected_total_due_fees_paid);
        System.out.println("product type is " + productType);

        if (productType.equalsIgnoreCase("MCA")) {

//       Monthly Mandatory Payment Percentage
            expected_monthly_mandatory_payment_percentage = Precision.round(100 / tenorInMonths,3);
            System.out.println("Expected Monthly Mandatory payment percentage " + expected_monthly_mandatory_payment_percentage);

//             Monthly Minimum charge
            expected_monthly_minimum_charge = TotalReceivableAmount / tenorInMonths;
            System.out.println("Expected Monthly minimum charge " + expected_monthly_minimum_charge);

//             Months since origination
            LocalDate Date = DateOperations.getCurrentDate();
            long diffBwDateAndDisbursementDate = DateOperations.dateDifference(Date, disbursementDate);

            expected_months_since_origination = ((diffBwDateAndDisbursementDate - facility_grace_period_length) / 30) * 1000000;
            System.out.println("Expected months since origination " + expected_months_since_origination);

//        Monthly Mandatory Payment till date -calculation
//        Daily Mandatory Payment till date -calculation
//        Past Due Installment Payment -calculation
            if (Date.compareTo(maturityDate) < 0) {
                //  current date <  maturity date
                expected_monthly_mandatory_payment_till_date = Math.round(expected_months_since_origination) * expected_monthly_minimum_charge;
                System.out.println("Expected Monthly payment till_date " + expected_monthly_mandatory_payment_till_date);

                expected_daily_mandatory_payment_till_date = expected_months_since_origination * expected_monthly_minimum_charge;
                System.out.println("Expected daily payment till_date " + expected_daily_mandatory_payment_till_date);

                expected_past_due_installment_payment = expected_monthly_mandatory_payment_till_date - TotalRepaidAmount;
                System.out.println("Expected Past Due Installment Payment" + expected_past_due_installment_payment);


            } else {
                //  current date >  maturity date

                expected_monthly_mandatory_payment_till_date = TotalReceivableAmount;
                System.out.println("Expected Monthly payment till_date " + expected_monthly_mandatory_payment_till_date);

                expected_daily_mandatory_payment_till_date = TotalReceivableAmount;
                System.out.println("Expected daily payment till_date " + expected_daily_mandatory_payment_till_date);

                expected_past_due_installment_payment = TotalRemainingBalance;
                System.out.println("Expected Past Due Installment Payment" + expected_past_due_installment_payment);


            }

//           Past Due Payment - calculation
            expected_past_due_payment = expected_past_due_installment_payment + expected_due_fees_balance;
            System.out.println("Expected Past Due Payment " + expected_past_due_payment);

            //overAll evaluation for all the past due payment fields

            due_fees_balance = Response.get("metrics").get("due_fees_balance").asDouble();
            total_due_fees_amount = Response.get("metrics").get("total_due_fees_amount").asDouble();
            total_due_fees_paid = Response.get("metrics").get("total_due_fees_paid").asDouble();
            past_due_installment_payment = Response.get("metrics").get("past_due_installment_payment").asDouble();
            past_due_payment = Response.get("metrics").get("past_due_payment").asDouble();
            loan_status = Response.get("metrics").get("loan_status").asText();
            months_since_origination = Response.get("metrics").get("months_since_origination").asDouble();
            monthly_mandatory_payment_percentage = Response.get("metrics").get("monthly_mandatory_payment_percentage").asLong();
            monthly_minimum_charge = Response.get("metrics").get("monthly_minimum_charge").asDouble();
            monthly_mandatory_payment_till_date = Response.get("metrics").get("monthly_mandatory_payment_till_date").asDouble();
            daily_mandatory_payment_till_date = Response.get("metrics").get("daily_mandatory_payment_till_date").asDouble();

            assertEquals(expected_past_due_payment, past_due_payment,"Issue present in past_due_payment");
            assertEquals(expected_past_due_installment_payment, past_due_installment_payment,"Issue present in past_due_installment_payment");
            assertEquals(expected_monthly_mandatory_payment_percentage, monthly_mandatory_payment_percentage,"Issue present in monthly_mandatory_payment_percentage");
//            assertEquals(expected_monthly_minimum_charge, monthly_minimum_charge, "Issue present in monthly_minimum_charge");
//            assertEquals(expected_months_since_origination, months_since_origination,"Issue present in months_since_origination");
//            assertEquals(expected_monthly_mandatory_payment_till_date, monthly_mandatory_payment_till_date,"Issue present in monthly_mandatory_payment_till_date");
//            assertEquals(expected_daily_mandatory_payment_till_date, daily_mandatory_payment_till_date,"Issue present in daily_mandatory_payment_till_date");
            assertEquals(expected_due_fees_balance, due_fees_balance,"Issue present in due_fees_balance");
            assertEquals(expected_total_due_fees_amount, total_due_fees_amount,"Issue present in total_due_fees_amount");
            assertEquals(expected_total_due_fees_paid, total_due_fees_paid,"Issue present in total_due_fees_paid");

            System.out.println("Past Due Payment fields are updated as expected for MCA data");

        } else if (productType.equalsIgnoreCase("NON_MCA")) {

            // Past Due related fields
            expected_principal_past_due_amount = PrincipleAmount;
            expected_principal_past_due_paid = PrinciplePaid;
            expected_past_due_principal_payment = expected_principal_past_due_amount - expected_principal_past_due_paid;
            expected_profit_past_due_amount = ProfitAmount;
            expected_profit_past_due_paid = ProfitPaid;
            expected_past_due_profit_payment = expected_profit_past_due_amount - expected_profit_past_due_paid;
            expected_past_due_installment_payment = expected_past_due_principal_payment + expected_past_due_profit_payment;

            // current Due related fields
            expected_principal_due_amount = Current_principal_due_amount;
            expected_principal_due_paid = Current_principal_due_paid;
            expected_due_principal_payment = expected_principal_due_amount - expected_principal_due_paid;

            expected_profit_due_amount = Current_profit_due_amount;
            expected_profit_due_paid = Current_profit_due_paid;
            expected_due_profit_payment = expected_profit_due_amount - expected_profit_due_paid;

            expected_due_installment_payment = expected_due_principal_payment + expected_due_profit_payment;
            expected_past_due_payment = expected_past_due_installment_payment + expected_due_fees_balance;

            // Fields from wallet response
            past_due_payment = Response.get("metrics").get("past_due_payment").asDouble();
            due_installment_payment = Response.get("metrics").get("due_installment_payment").asDouble();
            due_principal_payment = Response.get("metrics").get("due_principal_payment").asDouble();
            principal_due_amount = Response.get("metrics").get("principal_due_amount").asDouble();
            principal_due_paid = Response.get("metrics").get("principal_due_paid").asDouble();
            due_profit_payment = Response.get("metrics").get("due_profit_payment").asDouble();
            profit_due_amount = Response.get("metrics").get("profit_due_amount").asDouble();
            profit_due_paid = Response.get("metrics").get("profit_due_paid").asDouble();
            past_due_installment_payment = Response.get("metrics").get("past_due_installment_payment").asDouble();
            past_due_principal_payment = Response.get("metrics").get("past_due_principal_payment").asDouble();
            principal_past_due_amount = Response.get("metrics").get("principal_past_due_amount").asDouble();
            principal_past_due_paid = Response.get("metrics").get("principal_past_due_paid").asDouble();
            past_due_profit_payment = Response.get("metrics").get("past_due_profit_payment").asDouble();
            profit_past_due_amount = Response.get("metrics").get("profit_past_due_amount").asDouble();
            profit_past_due_paid = Response.get("metrics").get("profit_past_due_paid").asDouble();
            due_fees_balance = Response.get("metrics").get("due_fees_balance").asDouble();
            total_due_fees_amount = Response.get("metrics").get("total_due_fees_amount").asDouble();
            total_due_fees_paid = Response.get("metrics").get("total_due_fees_paid").asDouble();

            assertEquals(expected_past_due_payment, past_due_payment, "Issue present in past_due_payment ");
            assertEquals(expected_due_installment_payment, due_installment_payment,"Issue present in past_due_payment");
            assertEquals(expected_due_principal_payment, due_principal_payment,"Issue present in due_principal_payment");
            assertEquals(expected_principal_due_amount, principal_due_amount,"Issue present in principal_due_amount");
            assertEquals(expected_principal_due_paid, principal_due_paid,"Issue present in principal_due_paid");
            assertEquals(expected_due_profit_payment, due_profit_payment,"Issue present in due_profit_payment");
            assertEquals(expected_profit_due_amount, profit_due_amount,"Issue present in profit_due_amount");
            assertEquals(expected_profit_due_paid, profit_due_paid,"Issue present in profit_due_paid");
            assertEquals(expected_past_due_installment_payment, past_due_installment_payment,"Issue present in past_due_installment_payment");
            assertEquals(expected_past_due_principal_payment, past_due_principal_payment,"Issue present in past_due_principal_payment");
            assertEquals(expected_principal_past_due_amount, principal_past_due_amount,"Issue present in principal_past_due_amount");
            assertEquals(expected_principal_past_due_paid, principal_past_due_paid,"Issue present in principal_past_due_paid");
            assertEquals(expected_past_due_profit_payment, past_due_profit_payment,"Issue present in past_due_profit_payment");
            assertEquals(expected_profit_past_due_amount, profit_past_due_amount,"Issue present in profit_past_due_amount");
            assertEquals(expected_profit_past_due_paid, profit_past_due_paid,"Issue present in profit_past_due_paid");
            assertEquals(expected_due_fees_balance, due_fees_balance,"Issue present in due_fees_balance");
            assertEquals(expected_total_due_fees_amount, total_due_fees_amount,"Issue present in total_due_fees_amount");
            assertEquals(expected_total_due_fees_paid, total_due_fees_paid,"Issue present in total_due_fees_paid");

            System.out.println("Past Due payment fields are calculated correctly for NON-MCA data");

        } else {
            System.out.println("Check the product type passed to perform loan metrics validation");
        }

    }


    public static void assertionResults() {

        String expectedValue1 = "alpha";
        String actualValue1 = "alpha";
        String expectedValue2 = "beta";
        boolean value = true;

        assertAll("payments API vs DB validation results",
                () -> assertEquals(expectedValue1, actualValue1, "results match"),
                () -> assertEquals(expectedValue2, actualValue1, "results does not match")
        );

        assertAll("Transactions API vs DB validation results",
                () -> assertEquals(expectedValue2, actualValue1, "results does not match")


        );


    }

    public static void DbvalueFetch() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        String DBValues = "[\n" +
                "  {\n" +
                "    \"id\": \"8c82eaf3-2014-4068-a3c2-6840b950c9cf\",\n" +
                "    \"action_source\": null\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"59ba428a-f9aa-4e8e-96a9-e25c16da60e5\",\n" +
                "    \"action_source\": null\n" +
                "  }\n" +
                "]";
        JsonNode dbVal;
        JsonParser parser2 = factory.createParser(DBValues);
        dbVal = mapper.readTree(parser2);
        System.out.println("DB values: " + dbVal);

        int count = dbVal.size();
        String dBTxnCount = dbVal.get(0).get("id").asText();
        String dBTxnCount1 = dbVal.get(1).get("id").asText();
        System.out.println("dBTxnCount" + dBTxnCount);
        System.out.println("dBTxnCount1" + dBTxnCount1);
        System.out.println("count" + count);

    }

    public static void earlySettlementCalculation(String WalletResponse, String FacilityResponse, String InstallmentResponse, String SettlementDate) throws IOException, ParseException {
//        capture the current installment and the due date and profit details before that installment
//        Get the TRB and fee details from wallet.

        WalletDetails = DBValidation.jsonParseDBValues(WalletResponse);
        FacilityDetails = DBValidation.jsonParseDBValues(FacilityResponse);
        InstallmentDetails = DBValidation.jsonParseDBValues(InstallmentResponse);

        String dueDateVal;
        String dueDate;
        String undueDate;
        int count = 0;
        long undueProfitAmount = 0;
        long undueProfitPaid = 0;
        long dueProfitAmount = 0;
        long dueProfitPaid = 0;
        LocalDate undueInstallmentDate = null;
        LocalDate dueInstallmentDate = null;
        LocalDate due_date_val;
        long settlementDueProfitPayment;
        long overallDueProfitAmount = 0;
        long overallDueProfitPaid = 0;
        long ProfitOfXDaysFromSettlementDate;
        long EarlySettlementAmount;
        LocalDate settlementDate = DateOperations.convertToSpecificDateFormat(SettlementDate, "dd-MM-yyyy");

        //wallet details
        long installment_principle_balance = WalletDetails.get("installment_principle_balance").asLong();
        long fees_balance = WalletDetails.get("fees_balance").asLong();

//        LocalDate currentDate = DateOperations.getCurrentDate();
//        System.out.println(" Current Date : " + currentDate);
//        settlementDate = currentDate;

        String productType = FacilityDetails.get("product").get("product_type").asText();
        long number_of_profit_days = FacilityDetails.get("product").get("number_of_profit_days").asLong();
        int noOfInstallments = InstallmentDetails.get("data").size();
        System.out.println(" No of installments : " + noOfInstallments);

        if (productType.equalsIgnoreCase("NON_MCA")) {
            for (int i = 0; i < noOfInstallments; i++) {

                dueDateVal = InstallmentDetails.get("data").get(i).get("due_date").asText();
                due_date_val = DateOperations.convertToSpecificDateFormat(dueDateVal, "yyyy-MM-dd'T'HH:mm:ss");
                System.out.println("Due date converted : " + due_date_val);
                count = count + 1;

                if (due_date_val.compareTo(settlementDate) > 0) {
//                  Due date > current date
                    System.out.println("Due date > current Date");
                    undueProfitAmount = InstallmentDetails.get("data").get(i).get("profit_amount").asLong();
                    dueProfitAmount = InstallmentDetails.get("data").get(i - 1).get("profit_amount").asLong();
                    undueProfitPaid = InstallmentDetails.get("data").get(i).get("profit_paid").asLong();
                    dueProfitPaid = InstallmentDetails.get("data").get(i - 1).get("profit_paid").asLong();
                    undueDate = InstallmentDetails.get("data").get(i).get("due_date").asText();
                    undueInstallmentDate = due_date_val;
                    dueDate = InstallmentDetails.get("data").get(i - 1).get("due_date").asText();
                    dueInstallmentDate = DateOperations.convertToSpecificDateFormat(dueDate, "yyyy-MM-dd'T'HH:mm:ss");
                    break;
                }

            }
            System.out.println("Count : " + count);
            System.out.println("Due profit Amount : " + dueProfitAmount);
            System.out.println("Due profit paid : " + dueProfitPaid);
            System.out.println("Undue profit Amount : " + undueProfitAmount);
            System.out.println("Undue profit paid : " + undueProfitPaid);
            System.out.println("Settlement Due date : " + settlementDate);
            System.out.println("Due date : " + dueInstallmentDate);
            System.out.println("UnDue date : " + undueInstallmentDate);
            for (int i = 0; i < count - 1; i++) {
                overallDueProfitAmount = overallDueProfitAmount + InstallmentDetails.get("data").get(i).get("profit_amount").asLong();
                overallDueProfitPaid = overallDueProfitPaid + InstallmentDetails.get("data").get(i).get("profit_paid").asLong();

            }
            System.out.println("Sum of profit amount till due date : " + overallDueProfitAmount);
            System.out.println("Sum of profit paid till due date : " + overallDueProfitPaid);
            long diffBwUndueDateAndDueDate = DateOperations.dateDifference(undueInstallmentDate, dueInstallmentDate);
            long diffBwSettlementDateAndDueDate = DateOperations.dateDifference(settlementDate, dueInstallmentDate);
            settlementDueProfitPayment = (overallDueProfitAmount - overallDueProfitPaid) + ((undueProfitAmount / diffBwUndueDateAndDueDate) * diffBwSettlementDateAndDueDate);
            System.out.println("SettlementDueProfitPayment : " + settlementDueProfitPayment);
            LocalDate settlementDatePlusNoOfProfitDays = settlementDate.plusDays(number_of_profit_days);
            System.out.println("X (number of days to collect profit from) : " + number_of_profit_days);
            System.out.println("X + Settlement Date : " + settlementDatePlusNoOfProfitDays);
            long diffBwSettlementPlusProfitDaysAndDueDate = DateOperations.dateDifference(settlementDatePlusNoOfProfitDays, dueInstallmentDate);
            ProfitOfXDaysFromSettlementDate = (overallDueProfitAmount - overallDueProfitPaid) + ((undueProfitAmount / diffBwUndueDateAndDueDate) * diffBwSettlementPlusProfitDaysAndDueDate) - settlementDueProfitPayment;
            System.out.println("Profit of X days from settlement date : " + ProfitOfXDaysFromSettlementDate);
            EarlySettlementAmount = installment_principle_balance + settlementDueProfitPayment + ProfitOfXDaysFromSettlementDate + fees_balance;
            System.out.println("Early settlement Amount on current date - ESA : " + EarlySettlementAmount);

        }

    }

    public static void earlySettlementQuotation(String WalletResponse, String FacilityResponse, String InstallmentResponse, String SDResponse, boolean useSD, String SettlementDate) throws IOException, ParseException {
//        capture the current installment and the due date and profit details before that installment
//        Get the TRB and fee details from wallet.

        WalletDetails = DBValidation.jsonParseDBValues(WalletResponse);
        FacilityDetails = DBValidation.jsonParseDBValues(FacilityResponse);
        InstallmentDetails = DBValidation.jsonParseDBValues(InstallmentResponse);
        SDDetails = DBValidation.jsonParseDBValues(SDResponse);

        String dueDateVal;
        String dueDate;
        String undueDate;
        int count = 0;
        long undueProfitAmount = 0;
        long undueProfitPaid = 0;
        long dueProfitAmount = 0;
        long dueProfitPaid = 0;
        LocalDate undueInstallmentDate = null;
        LocalDate dueInstallmentDate = null;
        LocalDate due_date_val;

        long settlementDueProfitPayment;
        long overallDueProfitAmount = 0;
        long overallDueProfitPaid = 0;
        long ProfitOfXDaysFromSettlementDate;
        long EarlySettlementQuotationAmount;


        //Security deposit amount
        long security_deposit = SDDetails.get("remaining_security_deposit_amount").asLong();

        //wallet details
        long installment_principle_balance = WalletDetails.get("installment_principle_balance").asLong();
        long fees_balance = WalletDetails.get("fees_balance").asLong();
        LocalDate SettlementDateSelected = DateOperations.convertToSpecificDateFormat(SettlementDate, "dd-MM-yyyy");
        LocalDate settlementDate = SettlementDateSelected.plusDays(7);

        String productType = FacilityDetails.get("product").get("product_type").asText();
        long number_of_profit_days = FacilityDetails.get("product").get("number_of_profit_days").asLong();
        int noOfInstallments = InstallmentDetails.get("data").size();
        System.out.println(" No of installments : " + noOfInstallments);

        if (productType.equalsIgnoreCase("NON_MCA")) {
            for (int i = 0; i < noOfInstallments; i++) {

                dueDateVal = InstallmentDetails.get("data").get(i).get("due_date").asText();
                due_date_val = DateOperations.convertToSpecificDateFormat(dueDateVal, "yyyy-MM-dd'T'HH:mm:ss");
                System.out.println("Due date converted : " + due_date_val);
                count = count + 1;

                if (due_date_val.compareTo(settlementDate) > 0) {
//                  Due date > current date
                    System.out.println("Due date > current Date");
                    undueProfitAmount = InstallmentDetails.get("data").get(i).get("profit_amount").asLong();
                    dueProfitAmount = InstallmentDetails.get("data").get(i - 1).get("profit_amount").asLong();
                    undueProfitPaid = InstallmentDetails.get("data").get(i).get("profit_paid").asLong();
                    dueProfitPaid = InstallmentDetails.get("data").get(i - 1).get("profit_paid").asLong();
                    undueDate = InstallmentDetails.get("data").get(i).get("due_date").asText();
                    undueInstallmentDate = due_date_val;
                    dueDate = InstallmentDetails.get("data").get(i - 1).get("due_date").asText();
                    dueInstallmentDate = DateOperations.convertToSpecificDateFormat(dueDate, "yyyy-MM-dd'T'HH:mm:ss");
                    break;
                }

            }
            System.out.println("Count : " + count);
            System.out.println("Due profit Amount : " + dueProfitAmount);
            System.out.println("Due profit paid : " + dueProfitPaid);
            System.out.println("Undue profit Amount : " + undueProfitAmount);
            System.out.println("Undue profit paid : " + undueProfitPaid);
            System.out.println("Settlement Due date : " + settlementDate);
            System.out.println("Due date : " + dueInstallmentDate);
            System.out.println("UnDue date : " + undueInstallmentDate);
            for (int i = 0; i < count - 1; i++) {
                overallDueProfitAmount = overallDueProfitAmount + InstallmentDetails.get("data").get(i).get("profit_amount").asLong();
                overallDueProfitPaid = overallDueProfitPaid + InstallmentDetails.get("data").get(i).get("profit_paid").asLong();

            }
            System.out.println("Sum of profit amount till due date : " + overallDueProfitAmount);
            System.out.println("Sum of profit paid till due date : " + overallDueProfitPaid);
            long diffBwUndueDateAndDueDate = DateOperations.dateDifference(undueInstallmentDate, dueInstallmentDate);
            long diffBwSettlementDateAndDueDate = DateOperations.dateDifference(settlementDate, dueInstallmentDate);
            settlementDueProfitPayment = (overallDueProfitAmount - overallDueProfitPaid) + ((undueProfitAmount / diffBwUndueDateAndDueDate) * diffBwSettlementDateAndDueDate);
            System.out.println("SettlementDueProfitPayment : " + settlementDueProfitPayment);
            LocalDate settlementDatePlusNoOfProfitDays = settlementDate.plusDays(number_of_profit_days);
            System.out.println("X (number of days to collect profit from) : " + settlementDatePlusNoOfProfitDays);
            System.out.println("X + Settlement Date : " + settlementDatePlusNoOfProfitDays);
            long diffBwSettlementPlusProfitDaysAndDueDate = DateOperations.dateDifference(settlementDatePlusNoOfProfitDays, dueInstallmentDate);
            ProfitOfXDaysFromSettlementDate = (overallDueProfitAmount - overallDueProfitPaid) + ((undueProfitAmount / diffBwUndueDateAndDueDate) * diffBwSettlementPlusProfitDaysAndDueDate) - settlementDueProfitPayment;
            System.out.println("Profit of X days from settlement date : " + ProfitOfXDaysFromSettlementDate);
            if (useSD == true) {

                EarlySettlementQuotationAmount = installment_principle_balance + settlementDueProfitPayment + ProfitOfXDaysFromSettlementDate + fees_balance - security_deposit;
                System.out.println("Early settlement Quotation Amount using SD- ESQA : " + EarlySettlementQuotationAmount);

            } else {
                EarlySettlementQuotationAmount = installment_principle_balance + settlementDueProfitPayment + ProfitOfXDaysFromSettlementDate + fees_balance;
                System.out.println("Early settlement Quotation Amount without using SD - ESQA : " + EarlySettlementQuotationAmount);
            }


        }

    }

}

