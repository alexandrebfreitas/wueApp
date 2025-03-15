import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAssistencias } from '../assistencias.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../assistencias.test-samples';

import { AssistenciasService } from './assistencias.service';

const requireRestSample: IAssistencias = {
  ...sampleWithRequiredData,
};

describe('Assistencias Service', () => {
  let service: AssistenciasService;
  let httpMock: HttpTestingController;
  let expectedResult: IAssistencias | IAssistencias[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AssistenciasService);
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

    it('should create a Assistencias', () => {
      const assistencias = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(assistencias).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Assistencias', () => {
      const assistencias = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(assistencias).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Assistencias', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Assistencias', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Assistencias', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAssistenciasToCollectionIfMissing', () => {
      it('should add a Assistencias to an empty array', () => {
        const assistencias: IAssistencias = sampleWithRequiredData;
        expectedResult = service.addAssistenciasToCollectionIfMissing([], assistencias);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(assistencias);
      });

      it('should not add a Assistencias to an array that contains it', () => {
        const assistencias: IAssistencias = sampleWithRequiredData;
        const assistenciasCollection: IAssistencias[] = [
          {
            ...assistencias,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAssistenciasToCollectionIfMissing(assistenciasCollection, assistencias);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Assistencias to an array that doesn't contain it", () => {
        const assistencias: IAssistencias = sampleWithRequiredData;
        const assistenciasCollection: IAssistencias[] = [sampleWithPartialData];
        expectedResult = service.addAssistenciasToCollectionIfMissing(assistenciasCollection, assistencias);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(assistencias);
      });

      it('should add only unique Assistencias to an array', () => {
        const assistenciasArray: IAssistencias[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const assistenciasCollection: IAssistencias[] = [sampleWithRequiredData];
        expectedResult = service.addAssistenciasToCollectionIfMissing(assistenciasCollection, ...assistenciasArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const assistencias: IAssistencias = sampleWithRequiredData;
        const assistencias2: IAssistencias = sampleWithPartialData;
        expectedResult = service.addAssistenciasToCollectionIfMissing([], assistencias, assistencias2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(assistencias);
        expect(expectedResult).toContain(assistencias2);
      });

      it('should accept null and undefined values', () => {
        const assistencias: IAssistencias = sampleWithRequiredData;
        expectedResult = service.addAssistenciasToCollectionIfMissing([], null, assistencias, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(assistencias);
      });

      it('should return initial array if no Assistencias is added', () => {
        const assistenciasCollection: IAssistencias[] = [sampleWithRequiredData];
        expectedResult = service.addAssistenciasToCollectionIfMissing(assistenciasCollection, undefined, null);
        expect(expectedResult).toEqual(assistenciasCollection);
      });
    });

    describe('compareAssistencias', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAssistencias(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 7841 };
        const entity2 = null;

        const compareResult1 = service.compareAssistencias(entity1, entity2);
        const compareResult2 = service.compareAssistencias(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 7841 };
        const entity2 = { id: 32442 };

        const compareResult1 = service.compareAssistencias(entity1, entity2);
        const compareResult2 = service.compareAssistencias(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 7841 };
        const entity2 = { id: 7841 };

        const compareResult1 = service.compareAssistencias(entity1, entity2);
        const compareResult2 = service.compareAssistencias(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
