package com.jerif.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class NotificationRequest {
    Integer toCustomerId;
    String toCustomerEmail;
    String message;
}
