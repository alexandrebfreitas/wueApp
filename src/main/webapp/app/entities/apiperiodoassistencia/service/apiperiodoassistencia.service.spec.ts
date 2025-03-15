import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IApiperiodoassistencia } from '../apiperiodoassistencia.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../apiperiodoassistencia.test-samples';

import { ApiperiodoassistenciaService } from './apiperiodoassistencia.service';

const requireRestSample: IApiperiodoassistencia = {
  ...sampleWithRequiredData,
};

describe('Apiperiodoassistencia Service', () => {
  let service: ApiperiodoassistenciaService;
  let httpMock: HttpTestingController;
  let expectedResult: IApiperiodoassistencia | IApiperiodoassistencia[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ApiperiodoassistenciaService);
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

    it('should create a Apiperiodoassistencia', () => {
      const apiperiodoassistencia = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(apiperiodoassistencia).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Apiperiodoassistencia', () => {
      const apiperiodoassistencia = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(apiperiodoassistencia).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Apiperiodoassistencia', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Apiperiodoassistencia', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Apiperiodoassistencia', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addApiperiodoassistenciaToCollectionIfMissing', () => {
      it('should add a Apiperiodoassistencia to an empty array', () => {
        const apiperiodoassistencia: IApiperiodoassistencia = sampleWithRequiredData;
        expectedResult = service.addApiperiodoassistenciaToCollectionIfMissing([], apiperiodoassistencia);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(apiperiodoassistencia);
      });

      it('should not add a Apiperiodoassistencia to an array that contains it', () => {
        const apiperiodoassistencia: IApiperiodoassistencia = sampleWithRequiredData;
        const apiperiodoassistenciaCollection: IApiperiodoassistencia[] = [
          {
            ...apiperiodoassistencia,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addApiperiodoassistenciaToCollectionIfMissing(apiperiodoassistenciaCollection, apiperiodoassistencia);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Apiperiodoassistencia to an array that doesn't contain it", () => {
        const apiperiodoassistencia: IApiperiodoassistencia = sampleWithRequiredData;
        const apiperiodoassistenciaCollection: IApiperiodoassistencia[] = [sampleWithPartialData];
        expectedResult = service.addApiperiodoassistenciaToCollectionIfMissing(apiperiodoassistenciaCollection, apiperiodoassistencia);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(apiperiodoassistencia);
      });

      it('should add only unique Apiperiodoassistencia to an array', () => {
        const apiperiodoassistenciaArray: IApiperiodoassistencia[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const apiperiodoassistenciaCollection: IApiperiodoassistencia[] = [sampleWithRequiredData];
        expectedResult = service.addApiperiodoassistenciaToCollectionIfMissing(
          apiperiodoassistenciaCollection,
          ...apiperiodoassistenciaArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const apiperiodoassistencia: IApiperiodoassistencia = sampleWithRequiredData;
        const apiperiodoassistencia2: IApiperiodoassistencia = sampleWithPartialData;
        expectedResult = service.addApiperiodoassistenciaToCollectionIfMissing([], apiperiodoassistencia, apiperiodoassistencia2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(apiperiodoassistencia);
        expect(expectedResult).toContain(apiperiodoassistencia2);
      });

      it('should accept null and undefined values', () => {
        const apiperiodoassistencia: IApiperiodoassistencia = sampleWithRequiredData;
        expectedResult = service.addApiperiodoassistenciaToCollectionIfMissing([], null, apiperiodoassistencia, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(apiperiodoassistencia);
      });

      it('should return initial array if no Apiperiodoassistencia is added', () => {
        const apiperiodoassistenciaCollection: IApiperiodoassistencia[] = [sampleWithRequiredData];
        expectedResult = service.addApiperiodoassistenciaToCollectionIfMissing(apiperiodoassistenciaCollection, undefined, null);
        expect(expectedResult).toEqual(apiperiodoassistenciaCollection);
      });
    });

    describe('compareApiperiodoassistencia', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareApiperiodoassistencia(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 24259 };
        const entity2 = null;

        const compareResult1 = service.compareApiperiodoassistencia(entity1, entity2);
        const compareResult2 = service.compareApiperiodoassistencia(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 24259 };
        const entity2 = { id: 5285 };

        const compareResult1 = service.compareApiperiodoassistencia(entity1, entity2);
        const compareResult2 = service.compareApiperiodoassistencia(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 24259 };
        const entity2 = { id: 24259 };

        const compareResult1 = service.compareApiperiodoassistencia(entity1, entity2);
        const compareResult2 = service.compareApiperiodoassistencia(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
