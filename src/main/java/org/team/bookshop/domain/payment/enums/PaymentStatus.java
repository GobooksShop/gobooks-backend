package org.team.bookshop.domain.payment.enums;

import jakarta.persistence.Embeddable;

@Embeddable
public enum PaymentStatus {
  READY, COMPlETE;
}
