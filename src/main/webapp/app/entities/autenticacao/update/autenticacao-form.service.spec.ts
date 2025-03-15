import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../autenticacao.test-samples';

import { AutenticacaoFormService } from './autenticacao-form.service';

describe('Autenticacao Form Service', () => {
  let service: AutenticacaoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AutenticacaoFormService);
  });

  describe('Service methods', () => {
    describe('createAutenticacaoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAutenticacaoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            usuario: expect.any(Object),
            senha: expect.any(Object),
            accessToken: expect.any(Object),
            tokenType: expect.any(Object),
          }),
        );
      });

      it('passing IAutenticacao should create a new form with FormGroup', () => {
        const formGroup = service.createAutenticacaoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            usuario: expect.any(Object),
            senha: expect.any(Object),
            accessToken: expect.any(Object),
            tokenType: expect.any(Object),
          }),
        );
      });
    });

    describe('getAutenticacao', () => {
      it('should return NewAutenticacao for default Autenticacao initial value', () => {
        const formGroup = service.createAutenticacaoFormGroup(sampleWithNewData);

        const autenticacao = service.getAutenticacao(formGroup) as any;

        expect(autenticacao).toMatchObject(sampleWithNewData);
      });

      it('should return NewAutenticacao for empty Autenticacao initial value', () => {
        const formGroup = service.createAutenticacaoFormGroup();

        const autenticacao = service.getAutenticacao(formGroup) as any;

        expect(autenticacao).toMatchObject({});
      });

      it('should return IAutenticacao', () => {
        const formGroup = service.createAutenticacaoFormGroup(sampleWithRequiredData);

        const autenticacao = service.getAutenticacao(formGroup) as any;

        expect(autenticacao).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAutenticacao should not enable id FormControl', () => {
        const formGroup = service.createAutenticacaoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAutenticacao should disable id FormControl', () => {
        const formGroup = service.createAutenticacaoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
