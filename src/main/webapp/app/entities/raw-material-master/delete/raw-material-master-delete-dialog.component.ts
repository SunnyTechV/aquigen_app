import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRawMaterialMaster } from '../raw-material-master.model';
import { RawMaterialMasterService } from '../service/raw-material-master.service';

@Component({
  templateUrl: './raw-material-master-delete-dialog.component.html',
})
export class RawMaterialMasterDeleteDialogComponent {
  rawMaterialMaster?: IRawMaterialMaster;

  constructor(protected rawMaterialMasterService: RawMaterialMasterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.rawMaterialMasterService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
