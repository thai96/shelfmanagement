package com.thai.pham.ledgerservice.feature.read.domain;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import java.util.UUID;
import java.time.LocalDateTime;

import com.thai.pham.ledgerservice.common.data.entity.Ledger;

@Component
public class LedgerReadSpecification {
    public Specification<Ledger> hasLocation(UUID locationId) {
        return (root, query, criteriaBuilder) -> 
            locationId == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("locationId"), locationId);
    }

    public Specification<Ledger> hasProduct(UUID productId) {
        return (root, query, criteriaBuilder) -> 
            productId == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("productId"), productId);
    }

    public Specification<Ledger> hasUpdateReason(UpdateReason updateReason) {
        return (root, query, criteriaBuilder) -> 
            updateReason == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("updateReason"), updateReason);
    }

    public Specification<Ledger> checkBetweenDate(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, criteriaBuilder) -> {
            Predicate startDatePre = startDate == null ? criteriaBuilder.conjunction() :
                criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), startDate);
            Predicate endDatePre = endDate == null ? criteriaBuilder.conjunction() :
                criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), endDate);
            return criteriaBuilder.and(startDatePre, endDatePre);
        }
    }
}