import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../apiindicador.test-samples';

import { ApiindicadorFormService } from './apiindicador-form.service';

describe('Apiindicador Form Service', () => {
  let service: ApiindicadorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ApiindicadorFormService);
  });

  describe('Service methods', () => {
    describe('createApiindicadorFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createApiindicadorFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            filtro: expect.any(Object),
            dadosindicador: expect.any(Object),
            chave: expect.any(Object),
          }),
        );
      });

      it('passing IApiindicador should create a new form with FormGroup', () => {
        const formGroup = service.createApiindicadorFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            filtro: expect.any(Object),
            dadosindicador: expect.any(Object),
            chave: expect.any(Object),
          }),
        );
      });
    });

    describe('getApiindicador', () => {
      it('should return NewApiindicador for default Apiindicador initial value', () => {
        const formGroup = service.createApiindicadorFormGroup(sampleWithNewData);

        const apiindicador = service.getApiindicador(formGroup) as any;

        expect(apiindicador).toMatchObject(sampleWithNewData);
      });

      it('should return NewApiindicador for empty Apiindicador initial value', () => {
        const formGroup = service.createApiindicadorFormGroup();

        const apiindicador = service.getApiindicador(formGroup) as any;

        expect(apiindicador).toMatchObject({});
      });

      it('should return IApiindicador', () => {
        const formGroup = service.createApiindicadorFormGroup(sampleWithRequiredData);

        const apiindicador = service.getApiindicador(formGroup) as any;

        expect(apiindicador).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IApiindicador should not enable id FormControl', () => {
        const formGroup = service.createApiindicadorFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewApiindicador should disable id FormControl', () => {
        const formGroup = service.createApiindicadorFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
