import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IRmInventory, RmInventory } from '../rm-inventory.model';

import { RmInventoryService } from './rm-inventory.service';

describe('RmInventory Service', () => {
  let service: RmInventoryService;
  let httpMock: HttpTestingController;
  let elemDefault: IRmInventory;
  let expectedResult: IRmInventory | IRmInventory[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RmInventoryService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      inwardDate: currentDate,
      inwardQty: 'AAAAAAA',
      outwardQty: 'AAAAAAA',
      outwardDate: currentDate,
      totalQuanity: 'AAAAAAA',
      pricePerUnit: 0,
      lotNo: 'AAAAAAA',
      expiryDate: currentDate,
      freeField1: 'AAAAAAA',
      freeField2: 'AAAAAAA',
      freeField3: 'AAAAAAA',
      freeField4: 'AAAAAAA',
      lastModified: currentDate,
      lastModifiedBy: 'AAAAAAA',
      isDeleted: false,
      isActive: false,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          inwardDate: currentDate.format(DATE_TIME_FORMAT),
          outwardDate: currentDate.format(DATE_TIME_FORMAT),
          expiryDate: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a RmInventory', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          inwardDate: currentDate.format(DATE_TIME_FORMAT),
          outwardDate: currentDate.format(DATE_TIME_FORMAT),
          expiryDate: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          inwardDate: currentDate,
          outwardDate: currentDate,
          expiryDate: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.create(new RmInventory()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RmInventory', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          inwardDate: currentDate.format(DATE_TIME_FORMAT),
          inwardQty: 'BBBBBB',
          outwardQty: 'BBBBBB',
          outwardDate: currentDate.format(DATE_TIME_FORMAT),
          totalQuanity: 'BBBBBB',
          pricePerUnit: 1,
          lotNo: 'BBBBBB',
          expiryDate: currentDate.format(DATE_TIME_FORMAT),
          freeField1: 'BBBBBB',
          freeField2: 'BBBBBB',
          freeField3: 'BBBBBB',
          freeField4: 'BBBBBB',
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
          isDeleted: true,
          isActive: true,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          inwardDate: currentDate,
          outwardDate: currentDate,
          expiryDate: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RmInventory', () => {
      const patchObject = Object.assign(
        {
          totalQuanity: 'BBBBBB',
          pricePerUnit: 1,
          freeField2: 'BBBBBB',
          isActive: true,
        },
        new RmInventory()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          inwardDate: currentDate,
          outwardDate: currentDate,
          expiryDate: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RmInventory', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          inwardDate: currentDate.format(DATE_TIME_FORMAT),
          inwardQty: 'BBBBBB',
          outwardQty: 'BBBBBB',
          outwardDate: currentDate.format(DATE_TIME_FORMAT),
          totalQuanity: 'BBBBBB',
          pricePerUnit: 1,
          lotNo: 'BBBBBB',
          expiryDate: currentDate.format(DATE_TIME_FORMAT),
          freeField1: 'BBBBBB',
          freeField2: 'BBBBBB',
          freeField3: 'BBBBBB',
          freeField4: 'BBBBBB',
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
          isDeleted: true,
          isActive: true,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          inwardDate: currentDate,
          outwardDate: currentDate,
          expiryDate: currentDate,
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

    it('should delete a RmInventory', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addRmInventoryToCollectionIfMissing', () => {
      it('should add a RmInventory to an empty array', () => {
        const rmInventory: IRmInventory = { id: 123 };
        expectedResult = service.addRmInventoryToCollectionIfMissing([], rmInventory);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(rmInventory);
      });

      it('should not add a RmInventory to an array that contains it', () => {
        const rmInventory: IRmInventory = { id: 123 };
        const rmInventoryCollection: IRmInventory[] = [
          {
            ...rmInventory,
          },
          { id: 456 },
        ];
        expectedResult = service.addRmInventoryToCollectionIfMissing(rmInventoryCollection, rmInventory);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RmInventory to an array that doesn't contain it", () => {
        const rmInventory: IRmInventory = { id: 123 };
        const rmInventoryCollection: IRmInventory[] = [{ id: 456 }];
        expectedResult = service.addRmInventoryToCollectionIfMissing(rmInventoryCollection, rmInventory);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(rmInventory);
      });

      it('should add only unique RmInventory to an array', () => {
        const rmInventoryArray: IRmInventory[] = [{ id: 123 }, { id: 456 }, { id: 56756 }];
        const rmInventoryCollection: IRmInventory[] = [{ id: 123 }];
        expectedResult = service.addRmInventoryToCollectionIfMissing(rmInventoryCollection, ...rmInventoryArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const rmInventory: IRmInventory = { id: 123 };
        const rmInventory2: IRmInventory = { id: 456 };
        expectedResult = service.addRmInventoryToCollectionIfMissing([], rmInventory, rmInventory2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(rmInventory);
        expect(expectedResult).toContain(rmInventory2);
      });

      it('should accept null and undefined values', () => {
        const rmInventory: IRmInventory = { id: 123 };
        expectedResult = service.addRmInventoryToCollectionIfMissing([], null, rmInventory, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(rmInventory);
      });

      it('should return initial array if no RmInventory is added', () => {
        const rmInventoryCollection: IRmInventory[] = [{ id: 123 }];
        expectedResult = service.addRmInventoryToCollectionIfMissing(rmInventoryCollection, undefined, null);
        expect(expectedResult).toEqual(rmInventoryCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
