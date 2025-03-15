import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IApiassistencia } from '../apiassistencia.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../apiassistencia.test-samples';

import { ApiassistenciaService } from './apiassistencia.service';

const requireRestSample: IApiassistencia = {
  ...sampleWithRequiredData,
};

describe('Apiassistencia Service', () => {
  let service: ApiassistenciaService;
  let httpMock: HttpTestingController;
  let expectedResult: IApiassistencia | IApiassistencia[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ApiassistenciaService);
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

    it('should create a Apiassistencia', () => {
      const apiassistencia = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(apiassistencia).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Apiassistencia', () => {
      const apiassistencia = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(apiassistencia).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Apiassistencia', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Apiassistencia', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Apiassistencia', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addApiassistenciaToCollectionIfMissing', () => {
      it('should add a Apiassistencia to an empty array', () => {
        const apiassistencia: IApiassistencia = sampleWithRequiredData;
        expectedResult = service.addApiassistenciaToCollectionIfMissing([], apiassistencia);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(apiassistencia);
      });

      it('should not add a Apiassistencia to an array that contains it', () => {
        const apiassistencia: IApiassistencia = sampleWithRequiredData;
        const apiassistenciaCollection: IApiassistencia[] = [
          {
            ...apiassistencia,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addApiassistenciaToCollectionIfMissing(apiassistenciaCollection, apiassistencia);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Apiassistencia to an array that doesn't contain it", () => {
        const apiassistencia: IApiassistencia = sampleWithRequiredData;
        const apiassistenciaCollection: IApiassistencia[] = [sampleWithPartialData];
        expectedResult = service.addApiassistenciaToCollectionIfMissing(apiassistenciaCollection, apiassistencia);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(apiassistencia);
      });

      it('should add only unique Apiassistencia to an array', () => {
        const apiassistenciaArray: IApiassistencia[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const apiassistenciaCollection: IApiassistencia[] = [sampleWithRequiredData];
        expectedResult = service.addApiassistenciaToCollectionIfMissing(apiassistenciaCollection, ...apiassistenciaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const apiassistencia: IApiassistencia = sampleWithRequiredData;
        const apiassistencia2: IApiassistencia = sampleWithPartialData;
        expectedResult = service.addApiassistenciaToCollectionIfMissing([], apiassistencia, apiassistencia2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(apiassistencia);
        expect(expectedResult).toContain(apiassistencia2);
      });

      it('should accept null and undefined values', () => {
        const apiassistencia: IApiassistencia = sampleWithRequiredData;
        expectedResult = service.addApiassistenciaToCollectionIfMissing([], null, apiassistencia, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(apiassistencia);
      });

      it('should return initial array if no Apiassistencia is added', () => {
        const apiassistenciaCollection: IApiassistencia[] = [sampleWithRequiredData];
        expectedResult = service.addApiassistenciaToCollectionIfMissing(apiassistenciaCollection, undefined, null);
        expect(expectedResult).toEqual(apiassistenciaCollection);
      });
    });

    describe('compareApiassistencia', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareApiassistencia(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 17411 };
        const entity2 = null;

        const compareResult1 = service.compareApiassistencia(entity1, entity2);
        const compareResult2 = service.compareApiassistencia(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 17411 };
        const entity2 = { id: 9746 };

        const compareResult1 = service.compareApiassistencia(entity1, entity2);
        const compareResult2 = service.compareApiassistencia(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 17411 };
        const entity2 = { id: 17411 };

        const compareResult1 = service.compareApiassistencia(entity1, entity2);
        const compareResult2 = service.compareApiassistencia(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
