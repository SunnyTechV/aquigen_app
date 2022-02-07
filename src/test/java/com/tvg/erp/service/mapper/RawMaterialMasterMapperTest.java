package com.tvg.erp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RawMaterialMasterMapperTest {

    private RawMaterialMasterMapper rawMaterialMasterMapper;

    @BeforeEach
    public void setUp() {
        rawMaterialMasterMapper = new RawMaterialMasterMapperImpl();
    }
}
