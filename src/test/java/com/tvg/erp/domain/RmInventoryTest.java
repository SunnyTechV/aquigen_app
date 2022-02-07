package com.tvg.erp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.tvg.erp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RmInventoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RmInventory.class);
        RmInventory rmInventory1 = new RmInventory();
        rmInventory1.setId(1L);
        RmInventory rmInventory2 = new RmInventory();
        rmInventory2.setId(rmInventory1.getId());
        assertThat(rmInventory1).isEqualTo(rmInventory2);
        rmInventory2.setId(2L);
        assertThat(rmInventory1).isNotEqualTo(rmInventory2);
        rmInventory1.setId(null);
        assertThat(rmInventory1).isNotEqualTo(rmInventory2);
    }
}
