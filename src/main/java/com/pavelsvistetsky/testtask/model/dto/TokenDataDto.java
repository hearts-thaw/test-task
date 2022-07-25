package com.pavelsvistetsky.testtask.model.dto;

import java.util.UUID;

public record TokenDataDto(UUID value, int ttl) {
}
