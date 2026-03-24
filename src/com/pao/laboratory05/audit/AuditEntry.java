package com.pao.laboratory05.audit;

/**
 * Record imutabil pentru log-ul de audit.
 */
public record AuditEntry(String action, String target, String timestamp) { }