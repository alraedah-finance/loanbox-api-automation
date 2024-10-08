package util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DBValidation {

    public static void responseAndDBValidation(String APIResponse, String DBValues, String dbtable) {
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        JsonNode response;
        JsonNode dbVal;
        JsonNode triggerPropResponse;

        try {

            JsonParser parser = factory.createParser(APIResponse);
            response = mapper.readTree(parser);
            System.out.println("API response: " + response);

            JsonParser parser2 = factory.createParser(DBValues);
            dbVal = mapper.readTree(parser2);
            System.out.println("DB values: " + dbVal);


            if (dbtable.equalsIgnoreCase("payments")) {

                assertEquals(response.get("payment").get("id"), dbVal.get("id"));
                assertEquals(response.get("payment").get("facility_id"), dbVal.get("facility_id"));
                assertEquals(response.get("payment").get("type"), dbVal.get("type"));
                assertEquals(response.get("payment").get("method"), dbVal.get("method"));
                assertEquals(response.get("payment").get("amount"), dbVal.get("amount"));
                assertEquals(response.get("payment").get("trigger"), dbVal.get("trigger"));
                assertEquals(response.get("payment").get("is_archived"), dbVal.get("is_archived"));
//            assertEquals(response.get("payment").get("deposit_date").toString(),dbVal.get("deposit_date").toString().replace("T"," "));
                assertEquals(response.get("payment").get("is_reversed"), dbVal.get("is_reversed"));

//            trigger properties validation

                String text = dbVal.get("trigger_properties").asText();
                String trigPropVal = StringEscapeUtils.unescapeJson(text);
                System.out.println("text values " + text);
                System.out.println("removed escape characters for db val" + trigPropVal);

                triggerPropResponse = JsonParsing.parse(text);
//            System.out.println("trying Json parse "+ triggerPropResponse.get("paid_by").asText());

                assertEquals(response.get("payment").get("trigger_properties").get("paid_by"), triggerPropResponse.get("paid_by"));
                assertEquals(response.get("payment").get("trigger_properties").get("fee_type"), triggerPropResponse.get("fee_type"));
                assertEquals(response.get("payment").get("trigger_properties").get("reverses"), triggerPropResponse.get("reverses"));
                assertEquals(response.get("payment").get("trigger_properties").get("aggregator"), triggerPropResponse.get("aggregator"));
                assertEquals(response.get("payment").get("trigger_properties").get("cheque_date"), triggerPropResponse.get("cheque_date"));
//            assertEquals(response.get("payment").get("trigger_properties").get("record_date").toString(),triggerPropResponse.get("record_date").toString().replace("T"," "));
                assertEquals(response.get("payment").get("trigger_properties").get("refund_from"), triggerPropResponse.get("refund_from"));
                assertEquals(response.get("payment").get("trigger_properties").get("reversed_by"), triggerPropResponse.get("reversed_by"));
                assertEquals(response.get("payment").get("trigger_properties").get("customer_type"), triggerPropResponse.get("customer_type"));
                assertEquals(response.get("payment").get("trigger_properties").get("alraedah_banks"), triggerPropResponse.get("alraedah_banks"));
                assertEquals(response.get("payment").get("trigger_properties").get("customer_bank_name"), triggerPropResponse.get("customer_bank_name"));
                assertEquals(response.get("payment").get("refinance"), triggerPropResponse.get("refinance"));
                assertEquals(response.get("payment").get("new_facility_id"), triggerPropResponse.get("new_facility_id"));
                System.out.println("Payments API response and DB response ---> Assertion passed");

            } else if (dbtable.equalsIgnoreCase("transaction")) {

                assertEquals(response.get("data").get(0).get("id"), dbVal.get(0).get("id"));
                assertEquals(response.get("data").get(0).get("facility_id"), dbVal.get(0).get("facility_id"));
                assertEquals(response.get("data").get(0).get("outstanding_balance"), dbVal.get(0).get("outstanding_balance"));
                assertEquals(response.get("data").get(0).get("transaction_amount"), dbVal.get(0).get("transaction_amount"));
                assertEquals(response.get("data").get(0).get("transaction_type"), dbVal.get(0).get("transaction_type"));
                assertEquals(response.get("data").get(0).get("transaction_purpose"), dbVal.get(0).get("transaction_purpose"));
                assertEquals(response.get("data").get(0).get("fee_type"), dbVal.get(0).get("fee_type"));
                assertEquals(response.get("data").get(0).get("payment_id"), dbVal.get(0).get("payment_id"));
                assertEquals(response.get("data").get(0).get("transaction_destination"), dbVal.get(0).get("transaction_destination"));
                assertEquals(response.get("data").get(0).get("transaction_action"), dbVal.get(0).get("transaction_action"));
                assertEquals(response.get("data").get(0).get("redirected_transaction_reference"), dbVal.get(0).get("redirected_transaction_reference"));
                assertEquals(response.get("data").get(0).get("used_for"), dbVal.get(0).get("used_for"));
                assertEquals(response.get("data").get(0).get("action_source"), dbVal.get(0).get("action_source"));
//            assertEquals(response.get("data").get(0).get("created"),dbVal.get(0).get("created"));
                System.out.println("Transaction multi API response and DB response ---> Assertion passed");

            } else if (dbtable.equalsIgnoreCase("refund transaction") || dbtable.equalsIgnoreCase("redirect transaction")) {

                assertEquals(response.get("data").get(0).get("id"), dbVal.get("id"));
                assertEquals(response.get("data").get(0).get("facility_id"), dbVal.get("facility_id"));
                assertEquals(response.get("data").get(0).get("outstanding_balance"), dbVal.get("outstanding_balance"));
                assertEquals(response.get("data").get(0).get("transaction_amount"), dbVal.get("transaction_amount"));
                assertEquals(response.get("data").get(0).get("transaction_type"), dbVal.get("transaction_type"));
                assertEquals(response.get("data").get(0).get("transaction_purpose"), dbVal.get("transaction_purpose"));
                assertEquals(response.get("data").get(0).get("fee_type"), dbVal.get("fee_type"));
                assertEquals(response.get("data").get(0).get("payment_id"), dbVal.get("payment_id"));
                assertEquals(response.get("data").get(0).get("transaction_destination"), dbVal.get("transaction_destination"));
                assertEquals(response.get("data").get(0).get("transaction_action"), dbVal.get("transaction_action"));
                assertEquals(response.get("data").get(0).get("redirected_transaction_reference"), dbVal.get("redirected_transaction_reference"));
                assertEquals(response.get("data").get(0).get("used_for"), dbVal.get("used_for"));
                assertEquals(response.get("data").get(0).get("action_source"), dbVal.get("action_source"));
//            assertEquals(response.get("data").get(0).get("created"),dbVal.get("created"));
                System.out.println("Transaction multi API response and DB response ---> Assertion passed");

            } else if (dbtable.equalsIgnoreCase("refund multiple transaction")) {
                int refundCount = dbVal.size();
                for(int i=0;i<refundCount;i++){
                    assertEquals(response.get("data").get(i).get("id"), dbVal.get(i).get("id"));
                    assertEquals(response.get("data").get(i).get("facility_id"), dbVal.get(i).get("facility_id"));
                    assertEquals(response.get("data").get(i).get("outstanding_balance"), dbVal.get(i).get("outstanding_balance"));
                    assertEquals(response.get("data").get(i).get("transaction_amount"), dbVal.get(i).get("transaction_amount"));
                    assertEquals(response.get("data").get(i).get("transaction_type"), dbVal.get(i).get("transaction_type"));
                    assertEquals(response.get("data").get(i).get("transaction_purpose"), dbVal.get(i).get("transaction_purpose"));
                    assertEquals(response.get("data").get(i).get("fee_type"), dbVal.get(i).get("fee_type"));
                    assertEquals(response.get("data").get(i).get("payment_id"), dbVal.get(i).get("payment_id"));
                    assertEquals(response.get("data").get(i).get("transaction_destination"), dbVal.get(i).get("transaction_destination"));
                    assertEquals(response.get("data").get(i).get("transaction_action"), dbVal.get(i).get("transaction_action"));
                    assertEquals(response.get("data").get(i).get("redirected_transaction_reference"), dbVal.get(i).get("redirected_transaction_reference"));
                    assertEquals(response.get("data").get(i).get("used_for"), dbVal.get(i).get("used_for"));
                    assertEquals(response.get("data").get(i).get("action_source"), dbVal.get(i).get("action_source"));
//            assertEquals(response.get("data").get(i).get("created"),dbVal.get(i).get("created"));
                }

                System.out.println("Multiple refund transactions API response and DB response ---> Assertion passed");

            } else if (dbtable.equalsIgnoreCase("installment")) {
//            assertEquals(response.get("due_date"),dbVal.get("due_date"));
                assertEquals(response.get("id"), dbVal.get("id"));
                assertEquals(response.get("facility_id"), dbVal.get("facility_id"));
                assertEquals(response.get("wallet_id"), dbVal.get("wallet_id"));
                assertEquals(response.get("installment_number"), dbVal.get("installment_number"));
                assertEquals(response.get("installment_amount"), dbVal.get("installment_amount"));
                assertEquals(response.get("principal_amount"), dbVal.get("principal_amount"));
                assertEquals(response.get("profit_amount"), dbVal.get("profit_amount"));
                assertEquals(response.get("installment_paid"), dbVal.get("installment_paid"));
                assertEquals(response.get("principal_paid"), dbVal.get("principal_paid"));
                assertEquals(response.get("profit_paid"), dbVal.get("profit_paid"));
                assertEquals(response.get("remaining_principal"), dbVal.get("remaining_principal"));
                assertEquals(response.get("remaining_profit"), dbVal.get("remaining_profit"));
                assertEquals(response.get("installment_balance"), dbVal.get("installment_balance"));
                assertEquals(response.get("total_installments_balance"), dbVal.get("total_installments_balance"));
                assertEquals(response.get("installments_principal_balance"), dbVal.get("installments_principal_balance"));
                assertEquals(response.get("installments_profit_balance"), dbVal.get("installments_profit_balance"));
//            assertEquals(response.get("creation_date"),dbVal.get("creation_date"));
//            assertEquals(response.get("paid_date"),dbVal.get("paid_date"));
//            assertEquals(response.get("updated_date"),dbVal.get("updated_date"));

                System.out.println("Installment API response and DB response ---> Assertion passed");
            } else if (dbtable.equalsIgnoreCase("wallet")) {

                assertEquals(response.get("id"), dbVal.get("id"));
                assertEquals(response.get("facility_id"), dbVal.get("facility_id"));
                assertEquals(response.get("total_receivable_amount"), dbVal.get("total_receivable_amount"));
//            assertEquals(response.get("id"),dbVal.get("total_valuation_fees_amount"));
//            assertEquals(response.get("id"),dbVal.get("total_court_fees_amount"));
//            assertEquals(response.get("id"),dbVal.get("total_POS_fees_amount"));
//            assertEquals(response.get("id"),dbVal.get("total_extra_POS_fees_amount"));
                assertEquals(response.get("principle_amount"), dbVal.get("principle_amount"));
                assertEquals(response.get("profit_amount"), dbVal.get("profit_amount"));
                assertEquals(response.get("total_repaid_amount"), dbVal.get("total_repaid_amount"));
                assertEquals(response.get("total_fees_amount"), dbVal.get("total_fees_amount"));
                assertEquals(response.get("principle_paid"), dbVal.get("principle_paid"));
                assertEquals(response.get("profit_paid"), dbVal.get("profit_paid"));
                assertEquals(response.get("total_fees_paid"), dbVal.get("total_fees_paid"));
                assertEquals(response.get("total_remaining_balance"), dbVal.get("total_remaining_balance"));
                assertEquals(response.get("total_installments_balance"), dbVal.get("total_installments_balance"));
                assertEquals(response.get("installment_profit_balance"), dbVal.get("installment_profit_balance"));
                assertEquals(response.get("installment_principle_balance"), dbVal.get("installment_principle_balance"));
                assertEquals(response.get("fees_balance"), dbVal.get("fees_balance"));
                assertEquals(response.get("wallet_balance"), dbVal.get("wallet_balance"));
//            assertEquals(response.get("total_valuation_fees_paid"),dbVal.get("total_valuation_fees_paid"));
//            assertEquals(response.get("total_court_fees_paid"),dbVal.get("total_court_fees_paid"));
//            assertEquals(response.get("total_POS_fees_paid"),dbVal.get("total_POS_fees_paid"));
//            assertEquals(response.get("total_extra_POS_fees_paid"),dbVal.get("total_extra_POS_fees_paid"));
//            assertEquals(response.get("total_refunded_amount_for_closing"),dbVal.get("total_refunded_amount_for_closing"));
                System.out.println("Wallet API response and DB response ---> Assertion passed");

            } else if (dbtable.equalsIgnoreCase("multiple transaction")) {

                int transactionCount = response.get("transactions").get(0).size();
                int dBTxnCount = dbVal.size();
                System.out.println("Transaction count in API response : " + transactionCount);
                System.out.println("Transaction count in DB : " + dBTxnCount);
                double sumOfFeeTxns = 0;
                double SumOfOutStandingBal = 0;

                if (dBTxnCount > 0) {
                    System.out.println("Payment has been broken into " + dBTxnCount + " transactions");
                    for (int i = 0; i < transactionCount; i++) {
                        for (int j = 0; j < dBTxnCount; j++) {
                            if (response.get("transactions").get(i).get("id") == dbVal.get(j).get("id"))

                                assertEquals(response.get("transactions").get(i).get("id"), dbVal.get(j).get("id"));
                            assertEquals(response.get("transactions").get(i).get("facility_id"), dbVal.get(j).get("facility_id"));
//                        assertEquals(response.get("transactions").get(i).get("outstanding_balance"),dbVal.get(j).get("outstanding_balance"));
                            assertEquals(response.get("transactions").get(i).get("transaction_type"), dbVal.get(j).get("transaction_type"));
                            assertEquals(response.get("transactions").get(i).get("transaction_purpose"), dbVal.get(j).get("transaction_purpose"));
                            assertEquals(response.get("transactions").get(i).get("fee_type"), dbVal.get(j).get("fee_type"));
                            assertEquals(response.get("transactions").get(i).get("payment").get("id"), dbVal.get(j).get("payment_id"));
                            assertEquals(response.get("transactions").get(i).get("transaction_destination"), dbVal.get(j).get("transaction_destination"));
                            assertEquals(response.get("transactions").get(i).get("transaction_action"), dbVal.get(j).get("transaction_action"));
                            sumOfFeeTxns = sumOfFeeTxns + dbVal.get(j).get("transaction_amount").asDouble();
                            SumOfOutStandingBal = SumOfOutStandingBal + dbVal.get(j).get("outstanding_balance").asDouble();
                        }
                        System.out.println("amount in API response" + response.get("transactions").get(i).get("transaction_amount").asDouble());
                        System.out.println("Amount in DB response --->sumOfFeeTxns" + sumOfFeeTxns);
                        System.out.println("outstanding bal in API response" + response.get("transactions").get(i).get("outstanding_balance").asDouble());
                        System.out.println("outstanding bal in DB response --->SumOfOutStandingBal" + SumOfOutStandingBal);
//                    assertEquals(response.get("transactions").get(i).get("transaction_amount").asDouble(),sumOfFeeTxns);
                        break;
                    }
                } else {
                    System.out.println("Payment is not split into multiple transactions");
                    for (int i = 0; i < transactionCount; i++) {
                        if (response.get("transactions").get(i).get("id") == dbVal.get(0).get("id")) {

                            assertEquals(response.get("transactions").get(i).get("id"), dbVal.get(0).get("id"));
                            assertEquals(response.get("transactions").get(i).get("facility_id"), dbVal.get(0).get("facility_id"));
                            assertEquals(response.get("transactions").get(i).get("transaction_amount"), dbVal.get(0).get("transaction_amount"));
                            assertEquals(response.get("transactions").get(i).get("outstanding_balance"), dbVal.get(0).get("outstanding_balance"));
                            assertEquals(response.get("transactions").get(i).get("transaction_type"), dbVal.get(0).get("transaction_type"));
                            assertEquals(response.get("transactions").get(i).get("transaction_purpose"), dbVal.get(0).get("transaction_purpose"));
                            assertEquals(response.get("transactions").get(i).get("fee_type"), dbVal.get(0).get("fee_type"));
                            assertEquals(response.get("transactions").get(i).get("payment").get("id"), dbVal.get(0).get("payment_id"));
                            assertEquals(response.get("transactions").get(i).get("transaction_destination"), dbVal.get(0).get("transaction_destination"));
                            assertEquals(response.get("transactions").get(i).get("transaction_action"), dbVal.get(0).get("transaction_action"));

                        }
                        break;
                    }


                }


//            assertEquals(response.get("transactions").get(0).get("action_source"),dbVal.get(0).get("action_source"));
//            assertEquals(response.get("transactions").get(0).get("used_for"),dbVal.get(0).get("used_for"));
//            assertEquals(response.get("transactions").get(0).get("redirected_transaction_reference"),dbVal.get(0).get("redirected_transaction_reference"));
//            assertEquals(response.get("transactions").get(0).get("destination_type"),dbVal.get(0).get("destination_type"));
//            assertEquals(response.get("transactions").get(0).get("installment_number"),dbVal.get(0).get("installment_number"));
                System.out.println("Multi transaction API response and DB response ---> Assertion passed");

            } else if (dbtable.equalsIgnoreCase("loan metrics")) {


                assertEquals(response.get("metrics").get("due_fees_balance"), dbVal.get(0).get("due_fees_balance"));
                assertEquals(response.get("metrics").get("total_due_fees_amount"), dbVal.get(0).get("total_due_fees_amount"));
                assertEquals(response.get("metrics").get("total_due_fees_paid"), dbVal.get(0).get("total_due_fees_paid"));
                assertEquals(response.get("metrics").get("past_due_installment_payment"), dbVal.get(0).get("past_due_installment_payment"));
                assertEquals(response.get("metrics").get("past_due_payment"), dbVal.get(0).get("past_due_payment"));
                assertEquals(response.get("metrics").get("loan_status"), dbVal.get(0).get("loan_status"));
                assertEquals(response.get("metrics").get("months_since_origination"), dbVal.get(0).get("months_since_origination"));
                assertEquals(response.get("metrics").get("monthly_mandatory_payment_percentage"), dbVal.get(0).get("monthly_mandatory_payment_percentage"));
                assertEquals(response.get("metrics").get("monthly_minimum_charge"), dbVal.get(0).get("monthly_minimum_charge"));
                assertEquals(response.get("metrics").get("monthly_mandatory_payment_till_date"), dbVal.get(0).get("monthly_mandatory_payment_till_date"));
                assertEquals(response.get("metrics").get("daily_mandatory_payment_till_date"), dbVal.get(0).get("daily_mandatory_payment_till_date"));

                System.out.println("Loan metrics in wallet API response and DB response ---> Assertion passed");

            } else if (dbtable.equalsIgnoreCase("create fee")) {

                assertEquals(response.get(0).get("id"), dbVal.get("id"));
                assertEquals(response.get(0).get("facility_id"), dbVal.get("facility_id"));
                assertEquals(response.get(0).get("fee_type"), dbVal.get("fee_type"));
                assertEquals(response.get(0).get("wallet_id"), dbVal.get("wallet_id"));
                assertEquals(response.get(0).get("fee_amount"), dbVal.get("fee_amount"));
                assertEquals(response.get(0).get("fee_paid"), dbVal.get("fee_paid"));
                assertEquals(response.get(0).get("fee_balance"), dbVal.get("fee_balance"));
                assertEquals(response.get(0).get("status"), dbVal.get("status"));
                System.out.println("Create fee API response and DB response ---> Assertion passed");

            } else if (dbtable.equalsIgnoreCase("fetch fee")) {

                assertEquals(response.get("id"), dbVal.get("id"));
                assertEquals(response.get("facility_id"), dbVal.get("facility_id"));
                assertEquals(response.get("fee_type"), dbVal.get("fee_type"));
                assertEquals(response.get("wallet_id"), dbVal.get("wallet_id"));
                assertEquals(response.get("fee_amount"), dbVal.get("fee_amount"));
                assertEquals(response.get("fee_paid"), dbVal.get("fee_paid"));
                assertEquals(response.get("fee_balance"), dbVal.get("fee_balance"));
                assertEquals(response.get("status"), dbVal.get("status"));
                System.out.println("Fetch fee API response and DB response ---> Assertion passed");

            } else if (dbtable.equalsIgnoreCase("task")) {

                assertEquals(response.get("data").get(0).get("id"), dbVal.get("id"));
                assertEquals(response.get("data").get(0).get("flag_id"), dbVal.get("flag_id"));
                assertEquals(response.get("data").get(0).get("facility_id"), dbVal.get("facility_id"));
                assertEquals(response.get("data").get(0).get("payment_id"), dbVal.get("payment_id"));
                assertEquals(response.get("data").get(0).get("pip"), dbVal.get("pip"));
                assertEquals(response.get("data").get(0).get("facility_name"), dbVal.get("facility_name"));
                assertEquals(response.get("data").get(0).get("product_name"), dbVal.get("product_name"));
                assertEquals(response.get("data").get(0).get("flag_name"), dbVal.get("flag_name"));
                assertEquals(response.get("data").get(0).get("payment_amount"), dbVal.get("payment_amount"));
                assertEquals(response.get("data").get(0).get("status"), dbVal.get("status"));
                System.out.println("Get Task API response and DB response ---> Assertion passed");

            } else if (dbtable.equalsIgnoreCase("approve flag")) {

                assertEquals(response.get("id"), dbVal.get("id"));
                assertEquals(response.get("flag_id"), dbVal.get("flag_id"));
                assertEquals(response.get("facility_id"), dbVal.get("facility_id"));
                assertEquals(response.get("payment_id"), dbVal.get("payment_id"));
                assertEquals(response.get("pip"), dbVal.get("pip"));
//            assertEquals(response.get("facility_name"),dbVal.get("facility_name"));
//            assertEquals(response.get("product_name"),dbVal.get("product_name"));
                assertEquals(response.get("flag_name"), dbVal.get("flag_name"));
                assertEquals(response.get("payment_amount"), dbVal.get("payment_amount"));
                assertEquals(response.get("status"), dbVal.get("status"));
//            assertEquals(response.get("assignee"),dbVal.get("assignee"));

                System.out.println("Approve flag API response and DB response ---> Assertion passed");
            } else if (dbtable.equalsIgnoreCase("reject flag")) {

                assertEquals(response.get("id"), dbVal.get("id"));
                assertEquals(response.get("flag_id"), dbVal.get("flag_id"));
                assertEquals(response.get("facility_id"), dbVal.get("facility_id"));
                assertEquals(response.get("payment_id"), dbVal.get("payment_id"));
                assertEquals(response.get("pip"), dbVal.get("pip"));
//            assertEquals(response.get("facility_name"),dbVal.get("facility_name"));
//            assertEquals(response.get("product_name"),dbVal.get("product_name"));
                assertEquals(response.get("flag_name"), dbVal.get("flag_name"));
                assertEquals(response.get("payment_amount"), dbVal.get("payment_amount"));
                assertEquals(response.get("status"), dbVal.get("status"));
//            assertEquals(response.get("assignee"),dbVal.get("assignee"));

                System.out.println("Reject flag API response and DB response ---> Assertion passed");
            } else if (dbtable.equalsIgnoreCase("facility")) {

                assertEquals(response.get("id"), dbVal.get("id"));
                assertEquals(response.get("contact_authorized_person_key"), dbVal.get("contact_authorized_person_key"));
                assertEquals(response.get("contact_authorized_person_type"), dbVal.get("contact_authorized_person_type"));
                assertEquals(response.get("facility_grace_period_length"), dbVal.get("facility_grace_period_length"));
                assertEquals(response.get("contract_id"), dbVal.get("contract_id"));
                assertEquals(response.get("commodity_quantity"), dbVal.get("commodity_quantity"));
                assertEquals(response.get("product").get("id"), dbVal.get("product_key"));
                assertEquals(response.get("product_factor_rate"), dbVal.get("product_factor_rate"));
                assertEquals(response.get("product_minimum_charge"), dbVal.get("product_minimum_charge"));
                assertEquals(response.get("product_holdback_rate"), dbVal.get("product_holdback_rate"));
                assertEquals(response.get("payment_disbursement_amount"), dbVal.get("payment_disbursement_amount"));
                assertEquals(response.get("bank_iban"), dbVal.get("bank_iban"));
                assertEquals(response.get("arabic_bank_name"), dbVal.get("arabic_bank_name"));
                assertEquals(response.get("english_bank_name"), dbVal.get("english_bank_name"));
                assertEquals(response.get("documents_checked"), dbVal.get("documents_checked"));
                assertEquals(response.get("arabic_company_name"), dbVal.get("arabic_company_name"));
                assertEquals(response.get("arabic_company_name"), dbVal.get("arabic_company_name"));
                assertEquals(response.get("english_company_name"), dbVal.get("english_company_name"));
//                assertEquals(response.get("current_status").asText(), dbVal.get("current_status").asText());
//                assertEquals(response.get("closing_date"), dbVal.get("closing_date"));
                assertEquals(response.get("is_refinance"), dbVal.get("is_refinance"));
                assertEquals(response.get("product_properties"), dbVal.get("product_properties"));
                assertEquals(response.get("payment_frequency"), dbVal.get("payment_frequency"));

            }
            else if (dbtable.equalsIgnoreCase("wathq")) {
                if((response.get("commercialRegistrationInfo").get("businessType").get("id").asInt() == 101) || (response.get("commercialRegistrationInfo").get("businessType").get("id").asInt() == 102) ) {
                    assertEquals( "COMMERCIAL_REGISTRATION" , dbVal.get("type").asText());
                }
                System.out.println("wathq API response and DB response ---> Assertion passed");
            }else {
                System.out.println("Enter valid DB table name");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static JsonNode jsonParseDBValues(String text) throws IOException {


        String trigPropVal = StringEscapeUtils.unescapeJson(text);
        System.out.println("text values " + text);
        System.out.println("removed escape characters for db val" + trigPropVal);

        JsonNode jsonValue = JsonParsing.parse(text);

        return jsonValue;
    }

}

