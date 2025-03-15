import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../assistencias.test-samples';

import { AssistenciasFormService } from './assistencias-form.service';

describe('Assistencias Form Service', () => {
  let service: AssistenciasFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AssistenciasFormService);
  });

  describe('Service methods', () => {
    describe('createAssistenciasFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAssistenciasFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            value: expect.any(Object),
            apiassistencia: expect.any(Object),
          }),
        );
      });

      it('passing IAssistencias should create a new form with FormGroup', () => {
        const formGroup = service.createAssistenciasFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            value: expect.any(Object),
            apiassistencia: expect.any(Object),
          }),
        );
      });
    });

    describe('getAssistencias', () => {
      it('should return NewAssistencias for default Assistencias initial value', () => {
        const formGroup = service.createAssistenciasFormGroup(sampleWithNewData);

        const assistencias = service.getAssistencias(formGroup) as any;

        expect(assistencias).toMatchObject(sampleWithNewData);
      });

      it('should return NewAssistencias for empty Assistencias initial value', () => {
        const formGroup = service.createAssistenciasFormGroup();

        const assistencias = service.getAssistencias(formGroup) as any;

        expect(assistencias).toMatchObject({});
      });

      it('should return IAssistencias', () => {
        const formGroup = service.createAssistenciasFormGroup(sampleWithRequiredData);

        const assistencias = service.getAssistencias(formGroup) as any;

        expect(assistencias).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAssistencias should not enable id FormControl', () => {
        const formGroup = service.createAssistenciasFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAssistencias should disable id FormControl', () => {
        const formGroup = service.createAssistenciasFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
