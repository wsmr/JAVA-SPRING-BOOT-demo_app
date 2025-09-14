// ========================================================================================
// SERVICE INTERFACES
// ========================================================================================

// File: src/main/java/com/bankingsystem/service/interfaces/IAccountService.java
package com.bankingsystem.service.interfaces;

import com.bankingsystem.dto.request.account.CreateAccountRequest;
import com.bankingsystem.dto.response.account.AccountResponse;
import com.bankingsystem.entity.account.Account;
import java.util.List;

public interface IAccountService {

    // TODO: Implement createAccount() method
    AccountResponse createAccount(CreateAccountRequest request);

    // TODO: Implement getAccountByNumber() method
    AccountResponse getAccountByNumber(String accountNumber);

    // TODO: Implement getAccountsByCustomer() method
    List<AccountResponse> getAccountsByCustomer(String customerId);

    // TODO: Implement updateAccountStatus() method
    AccountResponse updateAccountStatus(String accountNumber, String status);

    // TODO: Implement calculateInterestForAccount() method
    Double calculateInterestForAccount(String accountNumber);

    // TODO: Implement closeAccount() method
    boolean closeAccount(String accountNumber, String reason);
}