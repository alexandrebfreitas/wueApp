import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IApidiaexcepcional } from '../apidiaexcepcional.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../apidiaexcepcional.test-samples';

import { ApidiaexcepcionalService } from './apidiaexcepcional.service';

const requireRestSample: IApidiaexcepcional = {
  ...sampleWithRequiredData,
};

describe('Apidiaexcepcional Service', () => {
  let service: ApidiaexcepcionalService;
  let httpMock: HttpTestingController;
  let expectedResult: IApidiaexcepcional | IApidiaexcepcional[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ApidiaexcepcionalService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Apidiaexcepcional', () => {
      const apidiaexcepcional = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(apidiaexcepcional).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Apidiaexcepcional', () => {
      const apidiaexcepcional = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(apidiaexcepcional).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Apidiaexcepcional', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Apidiaexcepcional', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Apidiaexcepcional', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addApidiaexcepcionalToCollectionIfMissing', () => {
      it('should add a Apidiaexcepcional to an empty array', () => {
        const apidiaexcepcional: IApidiaexcepcional = sampleWithRequiredData;
        expectedResult = service.addApidiaexcepcionalToCollectionIfMissing([], apidiaexcepcional);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(apidiaexcepcional);
      });

      it('should not add a Apidiaexcepcional to an array that contains it', () => {
        const apidiaexcepcional: IApidiaexcepcional = sampleWithRequiredData;
        const apidiaexcepcionalCollection: IApidiaexcepcional[] = [
          {
            ...apidiaexcepcional,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addApidiaexcepcionalToCollectionIfMissing(apidiaexcepcionalCollection, apidiaexcepcional);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Apidiaexcepcional to an array that doesn't contain it", () => {
        const apidiaexcepcional: IApidiaexcepcional = sampleWithRequiredData;
        const apidiaexcepcionalCollection: IApidiaexcepcional[] = [sampleWithPartialData];
        expectedResult = service.addApidiaexcepcionalToCollectionIfMissing(apidiaexcepcionalCollection, apidiaexcepcional);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(apidiaexcepcional);
      });

      it('should add only unique Apidiaexcepcional to an array', () => {
        const apidiaexcepcionalArray: IApidiaexcepcional[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const apidiaexcepcionalCollection: IApidiaexcepcional[] = [sampleWithRequiredData];
        expectedResult = service.addApidiaexcepcionalToCollectionIfMissing(apidiaexcepcionalCollection, ...apidiaexcepcionalArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const apidiaexcepcional: IApidiaexcepcional = sampleWithRequiredData;
        const apidiaexcepcional2: IApidiaexcepcional = sampleWithPartialData;
        expectedResult = service.addApidiaexcepcionalToCollectionIfMissing([], apidiaexcepcional, apidiaexcepcional2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(apidiaexcepcional);
        expect(expectedResult).toContain(apidiaexcepcional2);
      });

      it('should accept null and undefined values', () => {
        const apidiaexcepcional: IApidiaexcepcional = sampleWithRequiredData;
        expectedResult = service.addApidiaexcepcionalToCollectionIfMissing([], null, apidiaexcepcional, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(apidiaexcepcional);
      });

      it('should return initial array if no Apidiaexcepcional is added', () => {
        const apidiaexcepcionalCollection: IApidiaexcepcional[] = [sampleWithRequiredData];
        expectedResult = service.addApidiaexcepcionalToCollectionIfMissing(apidiaexcepcionalCollection, undefined, null);
        expect(expectedResult).toEqual(apidiaexcepcionalCollection);
      });
    });

    describe('compareApidiaexcepcional', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareApidiaexcepcional(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 6594 };
        const entity2 = null;

        const compareResult1 = service.compareApidiaexcepcional(entity1, entity2);
        const compareResult2 = service.compareApidiaexcepcional(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 6594 };
        const entity2 = { id: 19018 };

        const compareResult1 = service.compareApidiaexcepcional(entity1, entity2);
        const compareResult2 = service.compareApidiaexcepcional(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 6594 };
        const entity2 = { id: 6594 };

        const compareResult1 = service.compareApidiaexcepcional(entity1, entity2);
        const compareResult2 = service.compareApidiaexcepcional(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
