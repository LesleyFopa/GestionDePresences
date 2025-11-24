package com.lesley.GestionPresences.auth.utils;

import lombok.Builder;

@Builder
public record MailBody(String to, String subject, String text) {
}
