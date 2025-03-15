import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { ApiassistenciaService } from '../service/apiassistencia.service';
import { IApiassistencia } from '../apiassistencia.model';
import { ApiassistenciaFormService } from './apiassistencia-form.service';

import { ApiassistenciaUpdateComponent } from './apiassistencia-update.component';

describe('Apiassistencia Management Update Component', () => {
  let comp: ApiassistenciaUpdateComponent;
  let fixture: ComponentFixture<ApiassistenciaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let apiassistenciaFormService: ApiassistenciaFormService;
  let apiassistenciaService: ApiassistenciaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ApiassistenciaUpdateComponent],
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
      .overrideTemplate(ApiassistenciaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ApiassistenciaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    apiassistenciaFormService = TestBed.inject(ApiassistenciaFormService);
    apiassistenciaService = TestBed.inject(ApiassistenciaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const apiassistencia: IApiassistencia = { id: 9746 };

      activatedRoute.data = of({ apiassistencia });
      comp.ngOnInit();

      expect(comp.apiassistencia).toEqual(apiassistencia);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApiassistencia>>();
      const apiassistencia = { id: 17411 };
      jest.spyOn(apiassistenciaFormService, 'getApiassistencia').mockReturnValue(apiassistencia);
      jest.spyOn(apiassistenciaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ apiassistencia });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: apiassistencia }));
      saveSubject.complete();

      // THEN
      expect(apiassistenciaFormService.getApiassistencia).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(apiassistenciaService.update).toHaveBeenCalledWith(expect.objectContaining(apiassistencia));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApiassistencia>>();
      const apiassistencia = { id: 17411 };
      jest.spyOn(apiassistenciaFormService, 'getApiassistencia').mockReturnValue({ id: null });
      jest.spyOn(apiassistenciaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ apiassistencia: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: apiassistencia }));
      saveSubject.complete();

      // THEN
      expect(apiassistenciaFormService.getApiassistencia).toHaveBeenCalled();
      expect(apiassistenciaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApiassistencia>>();
      const apiassistencia = { id: 17411 };
      jest.spyOn(apiassistenciaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ apiassistencia });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(apiassistenciaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
