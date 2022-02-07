import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRmInventory } from '../rm-inventory.model';
import { RmInventoryService } from '../service/rm-inventory.service';

@Component({
  templateUrl: './rm-inventory-delete-dialog.component.html',
})
export class RmInventoryDeleteDialogComponent {
  rmInventory?: IRmInventory;

  constructor(protected rmInventoryService: RmInventoryService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.rmInventoryService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
