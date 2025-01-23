package in.bushansirgur.restapi.service;

import in.bushansirgur.restapi.dto.ExpenseDTO;

import java.util.List;

/**
 * Service interface for Expense module
 * @author Fredrik Emilsson
 */
public interface ExpenseService {

    List<ExpenseDTO> getAllExpenses();
}
