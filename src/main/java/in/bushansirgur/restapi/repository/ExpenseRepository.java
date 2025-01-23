package in.bushansirgur.restapi.repository;

import in.bushansirgur.restapi.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
}
