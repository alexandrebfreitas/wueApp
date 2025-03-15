import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../apiassistencia.test-samples';

import { ApiassistenciaFormService } from './apiassistencia-form.service';

describe('Apiassistencia Form Service', () => {
  let service: ApiassistenciaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ApiassistenciaFormService);
  });

  describe('Service methods', () => {
    describe('createApiassistenciaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createApiassistenciaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            filtro: expect.any(Object),
            dadosassistencia: expect.any(Object),
            chave: expect.any(Object),
          }),
        );
      });

      it('passing IApiassistencia should create a new form with FormGroup', () => {
        const formGroup = service.createApiassistenciaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            filtro: expect.any(Object),
            dadosassistencia: expect.any(Object),
            chave: expect.any(Object),
          }),
        );
      });
    });

    describe('getApiassistencia', () => {
      it('should return NewApiassistencia for default Apiassistencia initial value', () => {
        const formGroup = service.createApiassistenciaFormGroup(sampleWithNewData);

        const apiassistencia = service.getApiassistencia(formGroup) as any;

        expect(apiassistencia).toMatchObject(sampleWithNewData);
      });

      it('should return NewApiassistencia for empty Apiassistencia initial value', () => {
        const formGroup = service.createApiassistenciaFormGroup();

        const apiassistencia = service.getApiassistencia(formGroup) as any;

        expect(apiassistencia).toMatchObject({});
      });

      it('should return IApiassistencia', () => {
        const formGroup = service.createApiassistenciaFormGroup(sampleWithRequiredData);

        const apiassistencia = service.getApiassistencia(formGroup) as any;

        expect(apiassistencia).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IApiassistencia should not enable id FormControl', () => {
        const formGroup = service.createApiassistenciaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewApiassistencia should disable id FormControl', () => {
        const formGroup = service.createApiassistenciaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
