import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRmInventory } from '../rm-inventory.model';

@Component({
  selector: 'jhi-rm-inventory-detail',
  templateUrl: './rm-inventory-detail.component.html',
})
export class RmInventoryDetailComponent implements OnInit {
  rmInventory: IRmInventory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rmInventory }) => {
      this.rmInventory = rmInventory;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
