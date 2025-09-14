// File: src/main/java/com/bankingsystem/service/interfaces/ITransactionService.java
package com.bankingsystem.service.interfaces;

import com.bankingsystem.dto.request.account.DepositRequest;
import com.bankingsystem.dto.request.account.WithdrawRequest;
import com.bankingsystem.dto.request.account.TransferRequest;
import com.bankingsystem.dto.response.transaction.TransactionResultResponse;

public interface ITransactionService {

    // TODO: Implement processDeposit() method
    TransactionResultResponse processDeposit(DepositRequest request);

    // TODO: Implement processWithdrawal() method
    TransactionResultResponse processWithdrawal(WithdrawRequest request);

    // TODO: Implement processTransfer() method
    TransactionResultResponse processTransfer(TransferRequest request);

    // TODO: Implement reverseTransaction() method
    TransactionResultResponse reverseTransaction(String transactionId, String reason);
}