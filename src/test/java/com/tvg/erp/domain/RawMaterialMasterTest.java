package com.tvg.erp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.tvg.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RawMaterialMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RawMaterialMaster.class);
        RawMaterialMaster rawMaterialMaster1 = new RawMaterialMaster();
        rawMaterialMaster1.setId(1L);
        RawMaterialMaster rawMaterialMaster2 = new RawMaterialMaster();
        rawMaterialMaster2.setId(rawMaterialMaster1.getId());
        assertThat(rawMaterialMaster1).isEqualTo(rawMaterialMaster2);
        rawMaterialMaster2.setId(2L);
        assertThat(rawMaterialMaster1).isNotEqualTo(rawMaterialMaster2);
        rawMaterialMaster1.setId(null);
        assertThat(rawMaterialMaster1).isNotEqualTo(rawMaterialMaster2);
    }
}
