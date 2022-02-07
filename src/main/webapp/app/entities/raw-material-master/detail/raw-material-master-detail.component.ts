import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRawMaterialMaster } from '../raw-material-master.model';

@Component({
  selector: 'jhi-raw-material-master-detail',
  templateUrl: './raw-material-master-detail.component.html',
})
export class RawMaterialMasterDetailComponent implements OnInit {
  rawMaterialMaster: IRawMaterialMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rawMaterialMaster }) => {
      this.rawMaterialMaster = rawMaterialMaster;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
