import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRmInventory, RmInventory } from '../rm-inventory.model';
import { RmInventoryService } from '../service/rm-inventory.service';

@Injectable({ providedIn: 'root' })
export class RmInventoryRoutingResolveService implements Resolve<IRmInventory> {
  constructor(protected service: RmInventoryService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRmInventory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((rmInventory: HttpResponse<RmInventory>) => {
          if (rmInventory.body) {
            return of(rmInventory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RmInventory());
  }
}
