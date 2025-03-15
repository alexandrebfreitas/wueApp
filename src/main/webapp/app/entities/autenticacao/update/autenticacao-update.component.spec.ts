import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { AutenticacaoService } from '../service/autenticacao.service';
import { IAutenticacao } from '../autenticacao.model';
import { AutenticacaoFormService } from './autenticacao-form.service';

import { AutenticacaoUpdateComponent } from './autenticacao-update.component';

describe('Autenticacao Management Update Component', () => {
  let comp: AutenticacaoUpdateComponent;
  let fixture: ComponentFixture<AutenticacaoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let autenticacaoFormService: AutenticacaoFormService;
  let autenticacaoService: AutenticacaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AutenticacaoUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AutenticacaoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AutenticacaoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    autenticacaoFormService = TestBed.inject(AutenticacaoFormService);
    autenticacaoService = TestBed.inject(AutenticacaoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const autenticacao: IAutenticacao = { id: 24549 };

      activatedRoute.data = of({ autenticacao });
      comp.ngOnInit();

      expect(comp.autenticacao).toEqual(autenticacao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAutenticacao>>();
      const autenticacao = { id: 15328 };
      jest.spyOn(autenticacaoFormService, 'getAutenticacao').mockReturnValue(autenticacao);
      jest.spyOn(autenticacaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ autenticacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: autenticacao }));
      saveSubject.complete();

      // THEN
      expect(autenticacaoFormService.getAutenticacao).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(autenticacaoService.update).toHaveBeenCalledWith(expect.objectContaining(autenticacao));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAutenticacao>>();
      const autenticacao = { id: 15328 };
      jest.spyOn(autenticacaoFormService, 'getAutenticacao').mockReturnValue({ id: null });
      jest.spyOn(autenticacaoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ autenticacao: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: autenticacao }));
      saveSubject.complete();

      // THEN
      expect(autenticacaoFormService.getAutenticacao).toHaveBeenCalled();
      expect(autenticacaoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAutenticacao>>();
      const autenticacao = { id: 15328 };
      jest.spyOn(autenticacaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ autenticacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(autenticacaoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
