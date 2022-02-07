import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRawMaterialMaster, RawMaterialMaster } from '../raw-material-master.model';
import { RawMaterialMasterService } from '../service/raw-material-master.service';

@Injectable({ providedIn: 'root' })
export class RawMaterialMasterRoutingResolveService implements Resolve<IRawMaterialMaster> {
  constructor(protected service: RawMaterialMasterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRawMaterialMaster> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((rawMaterialMaster: HttpResponse<RawMaterialMaster>) => {
          if (rawMaterialMaster.body) {
            return of(rawMaterialMaster.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RawMaterialMaster());
  }
}
