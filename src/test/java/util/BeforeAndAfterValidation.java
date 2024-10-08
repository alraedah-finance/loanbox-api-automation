package util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.Days;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BeforeAndAfterValidation {
    public static JsonNode beforeResponse;
    public static JsonNode currentResponse;

    public static void storeBeforeAndAfterValues(String beforeValue, String currentValue) {
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();

        try {
            JsonParser parser = factory.createParser(beforeValue);
            beforeResponse = mapper.readTree(parser);
            System.out.println("Before Operation - API response: " + beforeResponse);

            JsonParser parser2 = factory.createParser(currentValue);
            currentResponse = mapper.readTree(parser2);
            System.out.println("current operation - API response: " + currentResponse);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public static void walletDetailsValidation(String purpose, String amount) {

        double previousTRBValue;
        double currentTRBValue;
        double expectedTRB;

        double previousInstallmentBalance;
        double currentInstallmentBalance;
        double expectedInstallmentBalance;

        double previousInstallmentPrincipleBal;
        double currentInstallmentPrincipleBal;
        double expectedInstallmentPrincipleBal;

        double previousPrinciplePaid;
        double currentPrinciplePaid;
        double expectedPrinciplePaid;

        double previousWalletBalance;
        double currentWalletBalance;
        double expectedWalletBalance;

        double previousTotalFeesAmount;
        double currentTotalFeesAmount;
        double expectedTotalFeesAmount;

        double previous_fees_balance;
        double current_fees_balance;
        double expected_fees_balance;

        double previous_total_fees_paid;
        double current_total_fees_paid;
        double expected_total_fees_paid;

        double previousTotalRepaidAmount;
        double currentTotalRepaidAmount;
        double expectedTotalRepaidAmount;

        double previousInstallmentProfitBal;
        double currentInstallmentProfitBal;
        double expectedInstallmentProfitBal;

        double previous_profit_paid;
        double current_profit_paid;
        double expected_profit_paid;

        double paymentAmount = Double.parseDouble(amount);

        if (purpose.equalsIgnoreCase("add payment")) {

            //  check if total_remaining balance (TRB) has increased
            previousTRBValue = beforeResponse.get("total_remaining_balance").asDouble();
            currentTRBValue = currentResponse.get("total_remaining_balance").asDouble();
            expectedTRB = previousTRBValue - paymentAmount;
            assertEquals(expectedTRB, currentTRBValue);
            System.out.println("total_remaining_balance is updated as expected current adding payment");

//      check total_installment_balance
            previousInstallmentBalance = beforeResponse.get("total_installments_balance").asDouble();
            currentInstallmentBalance = currentResponse.get("total_installments_balance").asDouble();
            expectedInstallmentBalance = previousInstallmentBalance - paymentAmount;
            assertEquals(expectedInstallmentBalance, currentInstallmentBalance);
            System.out.println("total_installments_balance is updated as expected current adding payment");

//     the amount will be credited to principle when the profit balance is zero & all the profits are paid

            if (beforeResponse.get("installment_profit_balance").asInt() == 0) {
//            check Remaining principle balance
                previousInstallmentPrincipleBal = beforeResponse.get("installment_principle_balance").asDouble();
                currentInstallmentPrincipleBal = currentResponse.get("installment_principle_balance").asDouble();
                expectedInstallmentPrincipleBal = previousInstallmentPrincipleBal - paymentAmount;
                assertEquals(expectedInstallmentPrincipleBal, currentInstallmentPrincipleBal);
                System.out.println("installment_principle_balance is updated as expected current adding payment");

//            check principle amount paid
                previousPrinciplePaid = beforeResponse.get("principle_paid").asDouble();
                currentPrinciplePaid = currentResponse.get("principle_paid").asDouble();
                expectedPrinciplePaid = previousPrinciplePaid + paymentAmount;
                assertEquals(expectedPrinciplePaid, currentPrinciplePaid);
                System.out.println("principle_paid is updated as expected current adding payment");

            }


        } else if (purpose.equalsIgnoreCase("reverse")) {

            //  check total_remaining balance (TRB)
            previousTRBValue = beforeResponse.get("total_remaining_balance").asDouble();
            currentTRBValue = currentResponse.get("total_remaining_balance").asDouble();
            expectedTRB = previousTRBValue + paymentAmount;
            assertEquals(expectedTRB, currentTRBValue);
            System.out.println("total_remaining_balance is updated as expected current reversing payment");

            //      check total_installment_balance
            previousInstallmentBalance = beforeResponse.get("total_installments_balance").asDouble();
            currentInstallmentBalance = currentResponse.get("total_installments_balance").asDouble();
            expectedInstallmentBalance = previousInstallmentBalance + paymentAmount;
            assertEquals(expectedInstallmentBalance, currentInstallmentBalance);
            System.out.println("total_installments_balance is updated as expected current reversing payment");

            if (beforeResponse.get("installment_profit_balance").asInt() == 0) {

//            check Remaining principle balance
                previousInstallmentPrincipleBal = beforeResponse.get("installment_principle_balance").asDouble();
                currentInstallmentPrincipleBal = currentResponse.get("installment_principle_balance").asDouble();
                expectedInstallmentPrincipleBal = previousInstallmentPrincipleBal + paymentAmount;
                assertEquals(expectedInstallmentPrincipleBal, currentInstallmentPrincipleBal);
                System.out.println("installment_principle_balance is updated as expected current reversing payment");

//            check principle amount paid
                previousPrinciplePaid = beforeResponse.get("principle_paid").asDouble();
                currentPrinciplePaid = currentResponse.get("principle_paid").asDouble();
                expectedPrinciplePaid = previousPrinciplePaid - paymentAmount;
                assertEquals(expectedPrinciplePaid, currentPrinciplePaid);
                System.out.println("principle_paid is updated as expected current reversing payment");
            }
        } else if (purpose.equalsIgnoreCase("walletRefund")) {

            //  check wallet balance
            previousWalletBalance = beforeResponse.get("wallet_balance").asDouble();
            currentWalletBalance = currentResponse.get("wallet_balance").asDouble();
            expectedWalletBalance = previousWalletBalance - paymentAmount;
            System.out.println("Expected refund bal"+expectedWalletBalance);
            System.out.println("current refund bal"+currentWalletBalance);
            assertEquals(expectedWalletBalance, currentWalletBalance);
            System.out.println("wallet_balance is updated as expected current refund");

        } else if (purpose.equalsIgnoreCase("Wallet To Installment Redirect")) {

            //  check if  refund amount is deducted from wallet balance
            previousWalletBalance = beforeResponse.get("wallet_balance").asDouble();
            currentWalletBalance = currentResponse.get("wallet_balance").asDouble();
            expectedWalletBalance = previousWalletBalance - paymentAmount;
            assertEquals(expectedWalletBalance, currentWalletBalance);
            System.out.println("wallet_balance is reduced as expected current redirect");

            //  check  if total_remaining balance (TRB) has been reduced
            previousTRBValue = beforeResponse.get("total_remaining_balance").asDouble();
            currentTRBValue = currentResponse.get("total_remaining_balance").asDouble();
            expectedTRB = previousTRBValue - paymentAmount;
            assertEquals(expectedTRB, currentTRBValue);
            System.out.println("total_remaining_balance is reduced as expected current redirecting to installment");

//      check if total_installment_balance has reduced
            previousInstallmentBalance = beforeResponse.get("total_installments_balance").asDouble();
            currentInstallmentBalance = currentResponse.get("total_installments_balance").asDouble();
            expectedInstallmentBalance = previousInstallmentBalance - paymentAmount;
            assertEquals(expectedInstallmentBalance, currentInstallmentBalance);
            System.out.println("total_installments_balance is reduced as expected current redirecting to installment");

//     the amount will be credited to principle when the profit balance is zero & all the profits are paid
            if (beforeResponse.get("installment_profit_balance").asInt() == 0) {

//            check if Remaining principle balance has been reduced
                previousInstallmentPrincipleBal = beforeResponse.get("installment_principle_balance").asDouble();
                currentInstallmentPrincipleBal = currentResponse.get("installment_principle_balance").asDouble();
                expectedInstallmentPrincipleBal = previousInstallmentPrincipleBal - paymentAmount;
                assertEquals(expectedInstallmentPrincipleBal, currentInstallmentPrincipleBal);
                System.out.println("installment_principle_balance is updated as expected current redirecting from wallet ");

//            check if  principle amount paid has increased
                previousPrinciplePaid = beforeResponse.get("principle_paid").asDouble();
                currentPrinciplePaid = currentResponse.get("principle_paid").asDouble();
                expectedPrinciplePaid = previousPrinciplePaid + paymentAmount;
                assertEquals(expectedPrinciplePaid, currentPrinciplePaid);
                System.out.println("principle_paid is updated as expected current redirecting from wallet to installment");

            }


        } else if (purpose.equalsIgnoreCase("Loan Closure final check") || purpose.equalsIgnoreCase("Early Settlement final check")) {

            //  check if total_remaining balance (TRB) has been updated to zero
            currentTRBValue = currentResponse.get("total_remaining_balance").asDouble();

            if ((currentTRBValue != 0)) {
                System.out.println("total_remaining_balance value is not updated to ZERO");
            } else if ((currentTRBValue == 0)) {
                assertEquals(0, currentTRBValue);
                System.out.println("total_remaining_balance is updated as expected current Loan closure or ES");
            } else {
                System.out.println("Recheck total_remaining_balance value.");
            }

//      check total_installment_balance is updated to zero
            previousInstallmentBalance = beforeResponse.get("total_installments_balance").asDouble();
            currentInstallmentBalance = currentResponse.get("total_installments_balance").asDouble();
            assertEquals(0, currentInstallmentBalance);
            System.out.println("total_installments_balance is updated to zero");

            if (currentInstallmentBalance != 0) {
                System.out.println("total_installments_balance is NOT updated to ZERO");
            } else if (currentInstallmentBalance == 0) {
                assertEquals(0, currentInstallmentBalance);
                System.out.println("total_installments_balance is updated to ZERO");

            } else {
                System.out.println("Recheck total_installments_balance value. Issue present.");
            }

//            check if the total repaid amount value is same as total receivable amount
            currentTotalRepaidAmount = currentResponse.get("total_repaid_amount").asDouble();
           double currentTotalReceivableAmount = currentResponse.get("total_receivable_amount").asDouble();
           assertEquals(currentTotalReceivableAmount,currentTotalRepaidAmount);
            System.out.println("total_repaid_amount is same as total_receivable_amount");

//            Check is fees balance is updated to zero
            current_fees_balance = currentResponse.get("fees_balance").asDouble();
            if (current_fees_balance == 0) {
                System.out.println("fees_balance has been updated to ZERO");
            } else if (current_fees_balance > 0) {
                System.out.println("fees_balance is still present.Issue present.");
                System.out.println("Fees Balance present currently "+current_fees_balance);
            } else {
                System.out.println("Recheck fees_balance value");
            }
// check if the total installment balance is updated to zero
            currentInstallmentBalance = currentResponse.get("total_installments_balance").asDouble();
            if(currentInstallmentBalance ==0 ){
                System.out.println("Total Installment Balance is updated to Zero");
            }
            else if(currentInstallmentBalance !=0 ){
                System.out.println("Total Installment Balance is not updated to Zero. Issue present");
            }

// check if the installment principle balance is updated to zero
            currentInstallmentPrincipleBal = currentResponse.get("installment_principle_balance").asDouble();
            if(currentInstallmentPrincipleBal ==0 ){
                System.out.println("Installment Principle Balance is updated to Zero");
            }
            else if(currentInstallmentPrincipleBal !=0 ){
                System.out.println("Installment Principle Balance is not updated to Zero. Issue present");
            }

            // check if the installment profit balance is updated to zero
            currentInstallmentProfitBal = currentResponse.get("installment_profit_balance").asDouble();
            if(currentInstallmentProfitBal ==0 ){
                System.out.println("Installment Principle Balance is updated to Zero");
            }
            else if(currentInstallmentProfitBal !=0 ){
                System.out.println("Installment Principle Balance is not updated to Zero. Issue present");
            }

//             check principle paid amount
            currentPrinciplePaid = currentResponse.get("principle_paid").asDouble();
           double totalPrincipleAmount = currentResponse.get("principle_amount").asDouble();
           assertEquals(totalPrincipleAmount,currentPrinciplePaid);
           System.out.println("Total principle amount has been paid");

            //             check Profit paid amount
            double currentProfitPaid = currentResponse.get("profit_paid").asDouble();
            double totalProfitAmount = currentResponse.get("profit_amount").asDouble();
            assertEquals(totalProfitAmount,currentProfitPaid);
            System.out.println("Total principle amount has been paid");

//            check if Wallet balance is updated to zero
            currentWalletBalance = currentResponse.get("wallet_balance").asDouble();
            if(currentWalletBalance == 0){
                System.out.println("Wallet Balance is updated to ZERO");
            }
            else if(currentWalletBalance > 0){
                System.out.println("Wallet balance is greater than Zero. Need to check if the amount is refunded properly");
            }

        } else {
            System.out.println("Enter valid calculation purpose");
        }
    }

    public static void FeesInWalletValidation(String purpose, String fee_type, String amount) {

        double previousOverallFeesAmount;
        double currentOverAllFeesAmount;


        double previousFeesAmount;
        double currentFeesAmount;
        double expectedFeesAmount;

        double previousFeeAmount;
        double previousFeesBalance;
        double currentFeesBalance;
        double expectedFeesBalance;

        double previousFeesPaid;
        double currentFeesPaid;
        double expectedFeesPaid;

        double previousOverAllFeesPaid;
        double currentOverAllFeesPaid;
        double expectedOverAllFeesPaid;

        double feeAmount = Double.parseDouble(amount);

        if (purpose.equalsIgnoreCase("add fees")) {

//            check if this fees has already been added - If added already check if the total fees Amount of the specific fee type and total fees balance has been increased
            int no_of_feeTypes = beforeResponse.get("fees").size();
            if (no_of_feeTypes != 0) {
                for (int i = 0; i < no_of_feeTypes; i++) {

                    if (beforeResponse.get("fees").get(i).get("fee_type").asText().equalsIgnoreCase(fee_type)) {
                        previousFeeAmount = beforeResponse.get("fees").get(i).get("total_fees_amount").asDouble();
                        currentFeesAmount = currentResponse.get("fees").get(i).get("total_fees_amount").asDouble();
//                        expectedFeesAmount = previousFeeAmount + feeAmount;
                        assertEquals(previousFeeAmount, currentFeesAmount);
                        System.out.println("total_fees_amount is increased as expected current adding fees inside fee array");

                        previousFeesBalance = beforeResponse.get("fees").get(i).get("total_fees_balance").asDouble();
                        currentFeesBalance = currentResponse.get("fees").get(i).get("total_fees_balance").asDouble();
                        expectedFeesBalance = previousFeesBalance - feeAmount;
                        assertEquals(expectedFeesBalance, currentFeesBalance);
                        System.out.println("total_fees_balance has decreased as expected current adding fees inside fee array");

                        previousFeesPaid = beforeResponse.get("fees").get(i).get("total_fees_paid").asDouble();
                        currentFeesPaid = currentResponse.get("fees").get(i).get("total_fees_paid").asDouble();
                        expectedFeesPaid = previousFeesPaid + feeAmount;
                        assertEquals(expectedFeesPaid, currentFeesPaid);
                        System.out.println("total_fees_paid is increased as expected current adding fees inside fee array");


                        break;

                    }

                }
            } else if (no_of_feeTypes == 0) {

                currentFeesAmount = currentResponse.get("fees").get(0).get("total_fees_amount").asDouble();
                expectedFeesAmount = feeAmount;
                assertEquals(expectedFeesAmount, currentFeesAmount);
                System.out.println("total_fees_amount is updated as expected current adding new fees inside fee array");

                currentFeesBalance = currentResponse.get("fees").get(0).get("total_fees_balance").asDouble();
                expectedFeesBalance = feeAmount;
                assertEquals(expectedFeesBalance, currentFeesBalance);
                System.out.println("total_fees_balance updated as expected current adding new fees inside fee array");

            }

            //  check  if  overall fees_amount remains the same ( outside fee array)
            previousOverallFeesAmount = beforeResponse.get("total_fees_amount").asDouble();
            currentOverAllFeesAmount = currentResponse.get("total_fees_amount").asDouble();
            assertEquals(previousOverallFeesAmount, currentOverAllFeesAmount);
            System.out.println("overall total_fees_amount is increased as expected current adding fees");

            //  check  if overall fees_paid has been reduced ( outside fee array)
            previousOverAllFeesPaid = beforeResponse.get("total_fees_paid").asDouble();
            currentOverAllFeesPaid = currentResponse.get("total_fees_paid").asDouble();
            expectedOverAllFeesPaid = previousOverAllFeesPaid + feeAmount;
            assertEquals(expectedOverAllFeesPaid, currentOverAllFeesPaid);
            System.out.println("overall total_fees_paid is increased as expected current adding fees");

        }

    }

    public static void FetchFeeValidation(String purpose, String fee_type, String amount) {

        double previousFeePaid;
        double currentFeePaid;
        double expectedFeePaid;

        double previousFeeBalance;
        double currentFeeBalance;
        double expectedFeeBalance;

        double previousStatus;
        double currentStatus;

        double feeAmount = Double.parseDouble(amount);

        if (purpose.equalsIgnoreCase("post fees")) {

//            check if fee_paid value has increased
            previousFeePaid = beforeResponse.get("fee_paid").asDouble();
            currentFeePaid = currentResponse.get("fee_paid").asDouble();
            expectedFeePaid = previousFeePaid + feeAmount;
            assertEquals(expectedFeePaid, currentFeePaid);
            System.out.println(fee_type + "fee_paid has been increased as expected in fetch fee API");

//            check if fee_balance value has increased
            previousFeeBalance = beforeResponse.get("fee_balance").asDouble();
            currentFeeBalance = currentResponse.get("fee_balance").asDouble();
            expectedFeeBalance = previousFeeBalance - feeAmount;
            assertEquals(expectedFeeBalance, currentFeeBalance);
            System.out.println(fee_type + "fee_balance has been increased as expected in fetch fee API");

//            check for the fee status
            previousStatus = beforeResponse.get("status").asDouble();
            currentStatus = currentResponse.get("status").asDouble();
            if (previousFeePaid == 0 && currentFeePaid == 0) {
                System.out.println("No fees has been paid");
                assertEquals(previousStatus, currentStatus);
                System.out.println("Fees status is unpaid");
            }
            if (currentFeeBalance == 0) {
                assertEquals(currentStatus, "FULLY_PAID");
                System.out.println("Fee status has been updated to Fully paid");

            }
            if ((currentFeePaid != 0) && (currentFeePaid > 0)) {
                assertEquals(currentStatus, "PARTIALLY_PAID");
                System.out.println("Fee status has been updated to Partially paid");
            }
            System.out.println(fee_type + "status has been updated from" + previousStatus + " to " + currentStatus + " as expected in fetch fee API");


        }

    }

    public static void taskValidation(String purpose) {

        String previousTaskStatus;
        String currentTaskStatus;

        if (purpose.equalsIgnoreCase("approve task")) {
            previousTaskStatus = beforeResponse.get("data").get(0).get("status").asText();
            currentTaskStatus = currentResponse.get("data").get(0).get("status").asText();
            assertEquals("PENDING", previousTaskStatus);
            assertEquals("DONE", currentTaskStatus);
            System.out.println("Task status has been updated to Done");
        }
    }

    public static void flagValidation(String purpose) {

        boolean previousFlagStatus;
        boolean currentFlagStatus;

        if (purpose.equalsIgnoreCase("approve flag")) {
            previousFlagStatus = beforeResponse.get(0).get("is_set").asBoolean();
            currentFlagStatus = currentResponse.get(0).get("is_set").asBoolean();
            assertEquals(true, previousFlagStatus);
            assertEquals(false, currentFlagStatus);
            System.out.println("Flag status has been updated from true to false successfully");
        }

    }
    public static void SecurityDepositValidation(String purpose, String amount, String TRBValue) {

        double expectedRemainingSDAmount;

        double loanAmountPaid = Double.parseDouble(amount);
        double TRB = Double.parseDouble(TRBValue);
        double totalSDAmount = beforeResponse.get("security_deposit_amount").asDouble();

        String previousStatus = beforeResponse.get("security_deposit_status").asText();
        String currentStatus = currentResponse.get("security_deposit_status").asText();
        double previousSDPaid = beforeResponse.get("security_deposit_paid").asDouble();
        double currentSDPaid = currentResponse.get("security_deposit_paid").asDouble();
        double previousUsedSDAmount = beforeResponse.get("used_security_deposit_amount").asDouble();
       double currentUsedSDAmount = currentResponse.get("used_security_deposit_amount").asDouble();
       double previousRemainingSDAmount = beforeResponse.get("remaining_security_deposit_amount").asDouble();
        double currentRemainingSDAmount = currentResponse.get("remaining_security_deposit_amount").asDouble();

        if (purpose.equalsIgnoreCase("Loan closure using complete SD") || purpose.equalsIgnoreCase("Early settlement using complete SD")) {

            System.out.println("currentRemainingSDAmount " + currentRemainingSDAmount);
            System.out.println("TRB " + TRB);
            if (currentRemainingSDAmount == 0) {
                System.out.println("remaining_security_deposit_amount value is updated as expected");
                assertEquals("USED", currentStatus);
                assertEquals("CAPTURED", previousStatus);
                System.out.println("Security deposit status has been updated from CAPTURED ----> USED");
                System.out.println("No SD amount for refund - Final SD status achieved");
            }
            else if(currentRemainingSDAmount < 4000){
                System.out.println("remaining_security_deposit_amount value is present. refund must be done");
                assertEquals("PARTIALLY_USED", currentStatus);
                assertEquals("REFUNDED", previousStatus);

            }



        }
        else {
            System.out.println("Enter valid purpose for SD validation");
        }


    }

    public static void facilityStatusValidation(String transition) {

        String previous_status = beforeResponse.get("status").asText();
        String current_status = currentResponse.get("status").asText();


        if (transition.equalsIgnoreCase("ACTIVE to FINANCIALLY APPROVED FOR CLOSING")) {
            assertEquals("ACTIVE", previous_status);
            assertEquals("FINANCIALLY_APPROVED_FOR_CLOSING", current_status);
            System.out.println("Facility status has been updated : ACTIVE ---> FINANCIALLY_APPROVED_FOR_CLOSING");
        } else if (transition.equalsIgnoreCase("FINANCIALLY APPROVED FOR CLOSING to CLOSED")) {
            assertEquals("FINANCIALLY_APPROVED_FOR_CLOSING", previous_status);
            assertEquals("CLOSED", current_status);
            System.out.println("Facility status has been updated :FINANCIALLY_APPROVED_FOR_CLOSING ---> CLOSED");

        } else if (transition.equalsIgnoreCase("CLOSURE_INITIATED to FINANCIALLY APPROVED FOR CLOSING")) {
            assertEquals("CLOSURE_INITIATED", previous_status);
            assertEquals("FINANCIALLY_APPROVED_FOR_CLOSING", current_status);
            System.out.println("Facility status has been updated :CLOSURE_INITIATED ---> FINANCIALLY_APPROVED_FOR_CLOSING");

        } else if (transition.equalsIgnoreCase("ACTIVE to CLOSURE_INITIATED")) {
            assertEquals("ACTIVE", previous_status);
            assertEquals("CLOSURE_INITIATED", current_status);
            System.out.println("Facility status has been updated :ACTIVE ---> CLOSURE_INITIATED");

        }else if(transition.equalsIgnoreCase("NO STATUS CHANGE- REMAINS ACTIVE")){
            assertEquals("ACTIVE", previous_status);
            assertEquals("ACTIVE", current_status);
            System.out.println("Facility status remains the same: Status is ACTIVE");

        }else if(transition.equalsIgnoreCase("NO STATUS CHANGE- REMAINS CLOSED")){
            assertEquals("CLOSED", previous_status);
            assertEquals("CLOSED", current_status);
            System.out.println("Facility status remains the same: Status is CLOSED");

        }
        else {
            System.out.println("facility status is not updated as expected. Check the transition value again.");
        }


    }

    public static void LoanClosureRefundValidation(String WalletBalance) {

        double WB = Double.parseDouble(WalletBalance);
        double SDBalance = beforeResponse.get("remaining_security_deposit_amount").asDouble();
        double walletRefundAmount = currentResponse.get("refunded_amount").get("wallet_balance").asDouble();
        double SDRefundAmount = currentResponse.get("refunded_amount").get("security_deposit").asDouble();
        double totalRefundedAmountForClosing = currentResponse.get("refunded_amount").get("total_refunded_amount_for_closing").asDouble();
        double expectedTotalRefundAmount = SDBalance + WB;
        assertEquals(WalletBalance, walletRefundAmount);
        assertEquals(SDBalance, SDRefundAmount);
        assertEquals(expectedTotalRefundAmount, totalRefundedAmountForClosing);
        System.out.println("Loan closure refundable Wallet balance & Security deposit values are updated as expected");


    }


}
