package com.tvg.erp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RmInventoryMapperTest {

    private RmInventoryMapper rmInventoryMapper;

    @BeforeEach
    public void setUp() {
        rmInventoryMapper = new RmInventoryMapperImpl();
    }
}
