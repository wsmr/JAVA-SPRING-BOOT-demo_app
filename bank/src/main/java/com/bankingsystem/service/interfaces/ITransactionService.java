// File: src/main/java/com/bankingsystem/service/interfaces/ITransactionService.java
package com.bankingsystem.service.interfaces;

import com.bankingsystem.dto.request.account.DepositRequest;
import com.bankingsystem.dto.request.account.WithdrawRequest;
import com.bankingsystem.dto.request.account.TransferRequest;
import com.bankingsystem.dto.response.transaction.TransactionResultResponse;
import java.util.List;

/**
 * Interface defining contract for transaction processing operations.
 * This abstraction allows different implementations (sync, async, batch processing)
 * while maintaining the same contract for client code.
 */
public interface ITransactionService {

    /**
     * Process a deposit transaction
     * @param request Contains account number, amount, and deposit details
     * @return Transaction result with success/failure status
     */
    // TODO: Implement processDeposit() method
    TransactionResultResponse processDeposit(DepositRequest request);

    /**
     * Process a withdrawal transaction
     * @param request Contains account number, amount, and withdrawal details
     * @return Transaction result with updated balance
     */
    // TODO: Implement processWithdrawal() method
    TransactionResultResponse processWithdrawal(WithdrawRequest request);

    /**
     * Transfer money between accounts
     * @param request Contains from/to accounts, amount, and transfer details
     * @return Transaction result for the transfer operation
     */
    // TODO: Implement processTransfer() method
    TransactionResultResponse processTransfer(TransferRequest request);

    /**
     * Reverse a completed transaction
     * @param transactionId The transaction to reverse
     * @param reason Business reason for reversal
     * @return Result of the reversal operation
     */
    // TODO: Implement reverseTransaction() method
    TransactionResultResponse reverseTransaction(String transactionId, String reason);

    /**
     * Get transaction history for an account
     * @param accountNumber Account to query
     * @param limit Maximum number of transactions to return
     * @return List of recent transactions
     */
    List<TransactionResultResponse> getTransactionHistory(String accountNumber, int limit);
}