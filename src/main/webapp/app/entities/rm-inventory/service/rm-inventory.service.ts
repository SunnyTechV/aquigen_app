import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRmInventory, getRmInventoryIdentifier } from '../rm-inventory.model';

export type EntityResponseType = HttpResponse<IRmInventory>;
export type EntityArrayResponseType = HttpResponse<IRmInventory[]>;

@Injectable({ providedIn: 'root' })
export class RmInventoryService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/rm-inventories');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(rmInventory: IRmInventory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rmInventory);
    return this.http
      .post<IRmInventory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(rmInventory: IRmInventory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rmInventory);
    return this.http
      .put<IRmInventory>(`${this.resourceUrl}/${getRmInventoryIdentifier(rmInventory) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(rmInventory: IRmInventory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rmInventory);
    return this.http
      .patch<IRmInventory>(`${this.resourceUrl}/${getRmInventoryIdentifier(rmInventory) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRmInventory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRmInventory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRmInventoryToCollectionIfMissing(
    rmInventoryCollection: IRmInventory[],
    ...rmInventoriesToCheck: (IRmInventory | null | undefined)[]
  ): IRmInventory[] {
    const rmInventories: IRmInventory[] = rmInventoriesToCheck.filter(isPresent);
    if (rmInventories.length > 0) {
      const rmInventoryCollectionIdentifiers = rmInventoryCollection.map(rmInventoryItem => getRmInventoryIdentifier(rmInventoryItem)!);
      const rmInventoriesToAdd = rmInventories.filter(rmInventoryItem => {
        const rmInventoryIdentifier = getRmInventoryIdentifier(rmInventoryItem);
        if (rmInventoryIdentifier == null || rmInventoryCollectionIdentifiers.includes(rmInventoryIdentifier)) {
          return false;
        }
        rmInventoryCollectionIdentifiers.push(rmInventoryIdentifier);
        return true;
      });
      return [...rmInventoriesToAdd, ...rmInventoryCollection];
    }
    return rmInventoryCollection;
  }

  protected convertDateFromClient(rmInventory: IRmInventory): IRmInventory {
    return Object.assign({}, rmInventory, {
      inwardDate: rmInventory.inwardDate?.isValid() ? rmInventory.inwardDate.toJSON() : undefined,
      outwardDate: rmInventory.outwardDate?.isValid() ? rmInventory.outwardDate.toJSON() : undefined,
      expiryDate: rmInventory.expiryDate?.isValid() ? rmInventory.expiryDate.toJSON() : undefined,
      lastModified: rmInventory.lastModified?.isValid() ? rmInventory.lastModified.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.inwardDate = res.body.inwardDate ? dayjs(res.body.inwardDate) : undefined;
      res.body.outwardDate = res.body.outwardDate ? dayjs(res.body.outwardDate) : undefined;
      res.body.expiryDate = res.body.expiryDate ? dayjs(res.body.expiryDate) : undefined;
      res.body.lastModified = res.body.lastModified ? dayjs(res.body.lastModified) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((rmInventory: IRmInventory) => {
        rmInventory.inwardDate = rmInventory.inwardDate ? dayjs(rmInventory.inwardDate) : undefined;
        rmInventory.outwardDate = rmInventory.outwardDate ? dayjs(rmInventory.outwardDate) : undefined;
        rmInventory.expiryDate = rmInventory.expiryDate ? dayjs(rmInventory.expiryDate) : undefined;
        rmInventory.lastModified = rmInventory.lastModified ? dayjs(rmInventory.lastModified) : undefined;
      });
    }
    return res;
  }
}
