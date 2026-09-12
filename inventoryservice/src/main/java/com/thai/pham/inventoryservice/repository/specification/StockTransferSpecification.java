package com.thai.pham.inventoryservice.repository.specification;

import com.thai.pham.inventoryservice.entity.StockTransfer;
import com.thai.pham.inventoryservice.entity.TransferStatus;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class StockTransferSpecification {
    public Specification<StockTransfer> addStatusQuery(TransferStatus status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("transferStatus"), status);
        };
    }

    public Specification<StockTransfer> addLocationQuery(UUID fromLocationId, UUID toLocationId) {
        return (root, query, criteriaBuilder) -> {
            Predicate fromLocationPredicate = fromLocationId == null ?
                    criteriaBuilder.conjunction() :
                    criteriaBuilder.equal(root.join("fromLocation").get("id"), fromLocationId);
            Predicate toLocationPredicate = toLocationId == null ?
                    criteriaBuilder.conjunction() :
                    criteriaBuilder.equal(root.join("toLocation").get("id"), toLocationId);

            return criteriaBuilder.and(fromLocationPredicate, toLocationPredicate);
        };
    }

    public Specification<StockTransfer> addPeriodQuery(LocalDateTime fromDate, LocalDateTime toDate) {
        return (root, query, criteriaBuilder) -> {
            Join<StockTransfer, LocalDateTime> createdAtJoin = root.join("createdAt");
            Predicate fromDatePredicate = fromDate == null ?
                    criteriaBuilder.conjunction() :
                    criteriaBuilder.greaterThanOrEqualTo(createdAtJoin, fromDate);
            Predicate toDatePredicate = toDate == null ?
                    criteriaBuilder.conjunction() :
                    criteriaBuilder.lessThanOrEqualTo(createdAtJoin, toDate);
            return criteriaBuilder.and(
                    fromDatePredicate, toDatePredicate
            );
        };
    }
}
