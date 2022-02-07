package com.tvg.erp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.tvg.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RawMaterialMasterDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RawMaterialMasterDTO.class);
        RawMaterialMasterDTO rawMaterialMasterDTO1 = new RawMaterialMasterDTO();
        rawMaterialMasterDTO1.setId(1L);
        RawMaterialMasterDTO rawMaterialMasterDTO2 = new RawMaterialMasterDTO();
        assertThat(rawMaterialMasterDTO1).isNotEqualTo(rawMaterialMasterDTO2);
        rawMaterialMasterDTO2.setId(rawMaterialMasterDTO1.getId());
        assertThat(rawMaterialMasterDTO1).isEqualTo(rawMaterialMasterDTO2);
        rawMaterialMasterDTO2.setId(2L);
        assertThat(rawMaterialMasterDTO1).isNotEqualTo(rawMaterialMasterDTO2);
        rawMaterialMasterDTO1.setId(null);
        assertThat(rawMaterialMasterDTO1).isNotEqualTo(rawMaterialMasterDTO2);
    }
}
