import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IRawMaterialMaster, RawMaterialMaster } from '../raw-material-master.model';

import { RawMaterialMasterService } from './raw-material-master.service';

describe('RawMaterialMaster Service', () => {
  let service: RawMaterialMasterService;
  let httpMock: HttpTestingController;
  let elemDefault: IRawMaterialMaster;
  let expectedResult: IRawMaterialMaster | IRawMaterialMaster[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RawMaterialMasterService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      materialName: 'AAAAAAA',
      shortName: 'AAAAAAA',
      chemicalFormula: 'AAAAAAA',
      hsnNo: 'AAAAAAA',
      barCode: 'AAAAAAA',
      qrCode: 'AAAAAAA',
      gstPercentage: 0,
      materialImage: 'AAAAAAA',
      alertUnits: 'AAAAAAA',
      casNumber: 'AAAAAAA',
      catlogNumber: 'AAAAAAA',
      description: 'AAAAAAA',
      lastModified: currentDate,
      lastModifiedBy: 'AAAAAAA',
      freeField1: 'AAAAAAA',
      freeField2: 'AAAAAAA',
      freeField3: 'AAAAAAA',
      freeField4: 'AAAAAAA',
      isDeleted: false,
      isActive: false,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a RawMaterialMaster', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.create(new RawMaterialMaster()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RawMaterialMaster', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          materialName: 'BBBBBB',
          shortName: 'BBBBBB',
          chemicalFormula: 'BBBBBB',
          hsnNo: 'BBBBBB',
          barCode: 'BBBBBB',
          qrCode: 'BBBBBB',
          gstPercentage: 1,
          materialImage: 'BBBBBB',
          alertUnits: 'BBBBBB',
          casNumber: 'BBBBBB',
          catlogNumber: 'BBBBBB',
          description: 'BBBBBB',
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
          freeField1: 'BBBBBB',
          freeField2: 'BBBBBB',
          freeField3: 'BBBBBB',
          freeField4: 'BBBBBB',
          isDeleted: true,
          isActive: true,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RawMaterialMaster', () => {
      const patchObject = Object.assign(
        {
          materialName: 'BBBBBB',
          shortName: 'BBBBBB',
          chemicalFormula: 'BBBBBB',
          catlogNumber: 'BBBBBB',
          freeField1: 'BBBBBB',
          freeField2: 'BBBBBB',
          freeField3: 'BBBBBB',
          freeField4: 'BBBBBB',
          isActive: true,
        },
        new RawMaterialMaster()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RawMaterialMaster', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          materialName: 'BBBBBB',
          shortName: 'BBBBBB',
          chemicalFormula: 'BBBBBB',
          hsnNo: 'BBBBBB',
          barCode: 'BBBBBB',
          qrCode: 'BBBBBB',
          gstPercentage: 1,
          materialImage: 'BBBBBB',
          alertUnits: 'BBBBBB',
          casNumber: 'BBBBBB',
          catlogNumber: 'BBBBBB',
          description: 'BBBBBB',
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
          freeField1: 'BBBBBB',
          freeField2: 'BBBBBB',
          freeField3: 'BBBBBB',
          freeField4: 'BBBBBB',
          isDeleted: true,
          isActive: true,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a RawMaterialMaster', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addRawMaterialMasterToCollectionIfMissing', () => {
      it('should add a RawMaterialMaster to an empty array', () => {
        const rawMaterialMaster: IRawMaterialMaster = { id: 123 };
        expectedResult = service.addRawMaterialMasterToCollectionIfMissing([], rawMaterialMaster);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(rawMaterialMaster);
      });

      it('should not add a RawMaterialMaster to an array that contains it', () => {
        const rawMaterialMaster: IRawMaterialMaster = { id: 123 };
        const rawMaterialMasterCollection: IRawMaterialMaster[] = [
          {
            ...rawMaterialMaster,
          },
          { id: 456 },
        ];
        expectedResult = service.addRawMaterialMasterToCollectionIfMissing(rawMaterialMasterCollection, rawMaterialMaster);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RawMaterialMaster to an array that doesn't contain it", () => {
        const rawMaterialMaster: IRawMaterialMaster = { id: 123 };
        const rawMaterialMasterCollection: IRawMaterialMaster[] = [{ id: 456 }];
        expectedResult = service.addRawMaterialMasterToCollectionIfMissing(rawMaterialMasterCollection, rawMaterialMaster);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(rawMaterialMaster);
      });

      it('should add only unique RawMaterialMaster to an array', () => {
        const rawMaterialMasterArray: IRawMaterialMaster[] = [{ id: 123 }, { id: 456 }, { id: 6400 }];
        const rawMaterialMasterCollection: IRawMaterialMaster[] = [{ id: 123 }];
        expectedResult = service.addRawMaterialMasterToCollectionIfMissing(rawMaterialMasterCollection, ...rawMaterialMasterArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const rawMaterialMaster: IRawMaterialMaster = { id: 123 };
        const rawMaterialMaster2: IRawMaterialMaster = { id: 456 };
        expectedResult = service.addRawMaterialMasterToCollectionIfMissing([], rawMaterialMaster, rawMaterialMaster2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(rawMaterialMaster);
        expect(expectedResult).toContain(rawMaterialMaster2);
      });

      it('should accept null and undefined values', () => {
        const rawMaterialMaster: IRawMaterialMaster = { id: 123 };
        expectedResult = service.addRawMaterialMasterToCollectionIfMissing([], null, rawMaterialMaster, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(rawMaterialMaster);
      });

      it('should return initial array if no RawMaterialMaster is added', () => {
        const rawMaterialMasterCollection: IRawMaterialMaster[] = [{ id: 123 }];
        expectedResult = service.addRawMaterialMasterToCollectionIfMissing(rawMaterialMasterCollection, undefined, null);
        expect(expectedResult).toEqual(rawMaterialMasterCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
