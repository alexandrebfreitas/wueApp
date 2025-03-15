import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { ApiindicadorService } from '../service/apiindicador.service';
import { IApiindicador } from '../apiindicador.model';
import { ApiindicadorFormService } from './apiindicador-form.service';

import { ApiindicadorUpdateComponent } from './apiindicador-update.component';

describe('Apiindicador Management Update Component', () => {
  let comp: ApiindicadorUpdateComponent;
  let fixture: ComponentFixture<ApiindicadorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let apiindicadorFormService: ApiindicadorFormService;
  let apiindicadorService: ApiindicadorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ApiindicadorUpdateComponent],
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
      .overrideTemplate(ApiindicadorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ApiindicadorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    apiindicadorFormService = TestBed.inject(ApiindicadorFormService);
    apiindicadorService = TestBed.inject(ApiindicadorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const apiindicador: IApiindicador = { id: 26230 };

      activatedRoute.data = of({ apiindicador });
      comp.ngOnInit();

      expect(comp.apiindicador).toEqual(apiindicador);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApiindicador>>();
      const apiindicador = { id: 12796 };
      jest.spyOn(apiindicadorFormService, 'getApiindicador').mockReturnValue(apiindicador);
      jest.spyOn(apiindicadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ apiindicador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: apiindicador }));
      saveSubject.complete();

      // THEN
      expect(apiindicadorFormService.getApiindicador).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(apiindicadorService.update).toHaveBeenCalledWith(expect.objectContaining(apiindicador));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApiindicador>>();
      const apiindicador = { id: 12796 };
      jest.spyOn(apiindicadorFormService, 'getApiindicador').mockReturnValue({ id: null });
      jest.spyOn(apiindicadorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ apiindicador: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: apiindicador }));
      saveSubject.complete();

      // THEN
      expect(apiindicadorFormService.getApiindicador).toHaveBeenCalled();
      expect(apiindicadorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApiindicador>>();
      const apiindicador = { id: 12796 };
      jest.spyOn(apiindicadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ apiindicador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(apiindicadorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
