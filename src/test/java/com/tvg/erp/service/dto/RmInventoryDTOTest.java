package com.tvg.erp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.tvg.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RmInventoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RmInventoryDTO.class);
        RmInventoryDTO rmInventoryDTO1 = new RmInventoryDTO();
        rmInventoryDTO1.setId(1L);
        RmInventoryDTO rmInventoryDTO2 = new RmInventoryDTO();
        assertThat(rmInventoryDTO1).isNotEqualTo(rmInventoryDTO2);
        rmInventoryDTO2.setId(rmInventoryDTO1.getId());
        assertThat(rmInventoryDTO1).isEqualTo(rmInventoryDTO2);
        rmInventoryDTO2.setId(2L);
        assertThat(rmInventoryDTO1).isNotEqualTo(rmInventoryDTO2);
        rmInventoryDTO1.setId(null);
        assertThat(rmInventoryDTO1).isNotEqualTo(rmInventoryDTO2);
    }
}
