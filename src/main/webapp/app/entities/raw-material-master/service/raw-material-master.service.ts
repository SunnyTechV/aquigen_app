import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRawMaterialMaster, getRawMaterialMasterIdentifier } from '../raw-material-master.model';

export type EntityResponseType = HttpResponse<IRawMaterialMaster>;
export type EntityArrayResponseType = HttpResponse<IRawMaterialMaster[]>;

@Injectable({ providedIn: 'root' })
export class RawMaterialMasterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/raw-material-masters');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(rawMaterialMaster: IRawMaterialMaster): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rawMaterialMaster);
    return this.http
      .post<IRawMaterialMaster>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(rawMaterialMaster: IRawMaterialMaster): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rawMaterialMaster);
    return this.http
      .put<IRawMaterialMaster>(`${this.resourceUrl}/${getRawMaterialMasterIdentifier(rawMaterialMaster) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(rawMaterialMaster: IRawMaterialMaster): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rawMaterialMaster);
    return this.http
      .patch<IRawMaterialMaster>(`${this.resourceUrl}/${getRawMaterialMasterIdentifier(rawMaterialMaster) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRawMaterialMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRawMaterialMaster[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRawMaterialMasterToCollectionIfMissing(
    rawMaterialMasterCollection: IRawMaterialMaster[],
    ...rawMaterialMastersToCheck: (IRawMaterialMaster | null | undefined)[]
  ): IRawMaterialMaster[] {
    const rawMaterialMasters: IRawMaterialMaster[] = rawMaterialMastersToCheck.filter(isPresent);
    if (rawMaterialMasters.length > 0) {
      const rawMaterialMasterCollectionIdentifiers = rawMaterialMasterCollection.map(
        rawMaterialMasterItem => getRawMaterialMasterIdentifier(rawMaterialMasterItem)!
      );
      const rawMaterialMastersToAdd = rawMaterialMasters.filter(rawMaterialMasterItem => {
        const rawMaterialMasterIdentifier = getRawMaterialMasterIdentifier(rawMaterialMasterItem);
        if (rawMaterialMasterIdentifier == null || rawMaterialMasterCollectionIdentifiers.includes(rawMaterialMasterIdentifier)) {
          return false;
        }
        rawMaterialMasterCollectionIdentifiers.push(rawMaterialMasterIdentifier);
        return true;
      });
      return [...rawMaterialMastersToAdd, ...rawMaterialMasterCollection];
    }
    return rawMaterialMasterCollection;
  }

  protected convertDateFromClient(rawMaterialMaster: IRawMaterialMaster): IRawMaterialMaster {
    return Object.assign({}, rawMaterialMaster, {
      lastModified: rawMaterialMaster.lastModified?.isValid() ? rawMaterialMaster.lastModified.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.lastModified = res.body.lastModified ? dayjs(res.body.lastModified) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((rawMaterialMaster: IRawMaterialMaster) => {
        rawMaterialMaster.lastModified = rawMaterialMaster.lastModified ? dayjs(rawMaterialMaster.lastModified) : undefined;
      });
    }
    return res;
  }
}
