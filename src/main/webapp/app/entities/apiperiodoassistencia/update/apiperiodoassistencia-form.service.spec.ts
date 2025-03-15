import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../apiperiodoassistencia.test-samples';

import { ApiperiodoassistenciaFormService } from './apiperiodoassistencia-form.service';

describe('Apiperiodoassistencia Form Service', () => {
  let service: ApiperiodoassistenciaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ApiperiodoassistenciaFormService);
  });

  describe('Service methods', () => {
    describe('createApiperiodoassistenciaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createApiperiodoassistenciaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dadosperiodo: expect.any(Object),
            chave: expect.any(Object),
          }),
        );
      });

      it('passing IApiperiodoassistencia should create a new form with FormGroup', () => {
        const formGroup = service.createApiperiodoassistenciaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dadosperiodo: expect.any(Object),
            chave: expect.any(Object),
          }),
        );
      });
    });

    describe('getApiperiodoassistencia', () => {
      it('should return NewApiperiodoassistencia for default Apiperiodoassistencia initial value', () => {
        const formGroup = service.createApiperiodoassistenciaFormGroup(sampleWithNewData);

        const apiperiodoassistencia = service.getApiperiodoassistencia(formGroup) as any;

        expect(apiperiodoassistencia).toMatchObject(sampleWithNewData);
      });

      it('should return NewApiperiodoassistencia for empty Apiperiodoassistencia initial value', () => {
        const formGroup = service.createApiperiodoassistenciaFormGroup();

        const apiperiodoassistencia = service.getApiperiodoassistencia(formGroup) as any;

        expect(apiperiodoassistencia).toMatchObject({});
      });

      it('should return IApiperiodoassistencia', () => {
        const formGroup = service.createApiperiodoassistenciaFormGroup(sampleWithRequiredData);

        const apiperiodoassistencia = service.getApiperiodoassistencia(formGroup) as any;

        expect(apiperiodoassistencia).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IApiperiodoassistencia should not enable id FormControl', () => {
        const formGroup = service.createApiperiodoassistenciaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewApiperiodoassistencia should disable id FormControl', () => {
        const formGroup = service.createApiperiodoassistenciaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
