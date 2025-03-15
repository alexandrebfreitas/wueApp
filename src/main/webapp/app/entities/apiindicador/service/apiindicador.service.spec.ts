import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IApiindicador } from '../apiindicador.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../apiindicador.test-samples';

import { ApiindicadorService } from './apiindicador.service';

const requireRestSample: IApiindicador = {
  ...sampleWithRequiredData,
};

describe('Apiindicador Service', () => {
  let service: ApiindicadorService;
  let httpMock: HttpTestingController;
  let expectedResult: IApiindicador | IApiindicador[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ApiindicadorService);
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

    it('should create a Apiindicador', () => {
      const apiindicador = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(apiindicador).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Apiindicador', () => {
      const apiindicador = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(apiindicador).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Apiindicador', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Apiindicador', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Apiindicador', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addApiindicadorToCollectionIfMissing', () => {
      it('should add a Apiindicador to an empty array', () => {
        const apiindicador: IApiindicador = sampleWithRequiredData;
        expectedResult = service.addApiindicadorToCollectionIfMissing([], apiindicador);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(apiindicador);
      });

      it('should not add a Apiindicador to an array that contains it', () => {
        const apiindicador: IApiindicador = sampleWithRequiredData;
        const apiindicadorCollection: IApiindicador[] = [
          {
            ...apiindicador,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addApiindicadorToCollectionIfMissing(apiindicadorCollection, apiindicador);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Apiindicador to an array that doesn't contain it", () => {
        const apiindicador: IApiindicador = sampleWithRequiredData;
        const apiindicadorCollection: IApiindicador[] = [sampleWithPartialData];
        expectedResult = service.addApiindicadorToCollectionIfMissing(apiindicadorCollection, apiindicador);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(apiindicador);
      });

      it('should add only unique Apiindicador to an array', () => {
        const apiindicadorArray: IApiindicador[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const apiindicadorCollection: IApiindicador[] = [sampleWithRequiredData];
        expectedResult = service.addApiindicadorToCollectionIfMissing(apiindicadorCollection, ...apiindicadorArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const apiindicador: IApiindicador = sampleWithRequiredData;
        const apiindicador2: IApiindicador = sampleWithPartialData;
        expectedResult = service.addApiindicadorToCollectionIfMissing([], apiindicador, apiindicador2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(apiindicador);
        expect(expectedResult).toContain(apiindicador2);
      });

      it('should accept null and undefined values', () => {
        const apiindicador: IApiindicador = sampleWithRequiredData;
        expectedResult = service.addApiindicadorToCollectionIfMissing([], null, apiindicador, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(apiindicador);
      });

      it('should return initial array if no Apiindicador is added', () => {
        const apiindicadorCollection: IApiindicador[] = [sampleWithRequiredData];
        expectedResult = service.addApiindicadorToCollectionIfMissing(apiindicadorCollection, undefined, null);
        expect(expectedResult).toEqual(apiindicadorCollection);
      });
    });

    describe('compareApiindicador', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareApiindicador(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 12796 };
        const entity2 = null;

        const compareResult1 = service.compareApiindicador(entity1, entity2);
        const compareResult2 = service.compareApiindicador(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 12796 };
        const entity2 = { id: 26230 };

        const compareResult1 = service.compareApiindicador(entity1, entity2);
        const compareResult2 = service.compareApiindicador(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 12796 };
        const entity2 = { id: 12796 };

        const compareResult1 = service.compareApiindicador(entity1, entity2);
        const compareResult2 = service.compareApiindicador(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
