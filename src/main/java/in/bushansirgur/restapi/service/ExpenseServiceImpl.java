package in.bushansirgur.restapi.service;

import in.bushansirgur.restapi.dto.ExpenseDTO;
import in.bushansirgur.restapi.entity.ExpenseEntity;
import in.bushansirgur.restapi.exceptions.ResourceNotFoundException;
import in.bushansirgur.restapi.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ExpenseDTO> getAllExpenses() {
        // Call the repository method
        List<ExpenseEntity> list = expenseRepository.findAll();
        log.info("Printing the data from repository {}", list);

        // Convert the Entity object to DTO object and return the list
        return list.stream()
                .map(this::mapToExpenseDTO)
                .toList();
    }

    @Override
    public ExpenseDTO getExpenseByExpenseId(String expenseId) {
        ExpenseEntity expenseEntity = expenseRepository.findByExpenseId(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found for the expense id " + expenseId));
        log.info("Printing the expense entity details {}", expenseEntity);
        return mapToExpenseDTO(expenseEntity);
    }

    private ExpenseDTO mapToExpenseDTO(ExpenseEntity expenseEntity) {
        return modelMapper.map(expenseEntity, ExpenseDTO.class);
    }
}
