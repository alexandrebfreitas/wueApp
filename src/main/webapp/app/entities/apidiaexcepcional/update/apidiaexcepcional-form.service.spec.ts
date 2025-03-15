import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../apidiaexcepcional.test-samples';

import { ApidiaexcepcionalFormService } from './apidiaexcepcional-form.service';

describe('Apidiaexcepcional Form Service', () => {
  let service: ApidiaexcepcionalFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ApidiaexcepcionalFormService);
  });

  describe('Service methods', () => {
    describe('createApidiaexcepcionalFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createApidiaexcepcionalFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dadosdiaexcepcional: expect.any(Object),
            chave: expect.any(Object),
          }),
        );
      });

      it('passing IApidiaexcepcional should create a new form with FormGroup', () => {
        const formGroup = service.createApidiaexcepcionalFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dadosdiaexcepcional: expect.any(Object),
            chave: expect.any(Object),
          }),
        );
      });
    });

    describe('getApidiaexcepcional', () => {
      it('should return NewApidiaexcepcional for default Apidiaexcepcional initial value', () => {
        const formGroup = service.createApidiaexcepcionalFormGroup(sampleWithNewData);

        const apidiaexcepcional = service.getApidiaexcepcional(formGroup) as any;

        expect(apidiaexcepcional).toMatchObject(sampleWithNewData);
      });

      it('should return NewApidiaexcepcional for empty Apidiaexcepcional initial value', () => {
        const formGroup = service.createApidiaexcepcionalFormGroup();

        const apidiaexcepcional = service.getApidiaexcepcional(formGroup) as any;

        expect(apidiaexcepcional).toMatchObject({});
      });

      it('should return IApidiaexcepcional', () => {
        const formGroup = service.createApidiaexcepcionalFormGroup(sampleWithRequiredData);

        const apidiaexcepcional = service.getApidiaexcepcional(formGroup) as any;

        expect(apidiaexcepcional).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IApidiaexcepcional should not enable id FormControl', () => {
        const formGroup = service.createApidiaexcepcionalFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewApidiaexcepcional should disable id FormControl', () => {
        const formGroup = service.createApidiaexcepcionalFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
